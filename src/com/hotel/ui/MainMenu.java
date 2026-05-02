package com.hotel.ui;

import com.hotel.api.HotelResource;
import com.hotel.models.IRoom;
import com.hotel.models.Reservation;
import com.hotel.util.ValidateDate;

import java.util.*;

public class MainMenu {

    private static final HotelResource hotelResource = HotelResource.getInstance();
    private static final Scanner scanner = new Scanner(System.in);

    public static void start() {
        boolean running = true;

        while (running) {
            printMenu();

            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    findAndReserveRoom();
                    break;
                case "2":
                    seeReservations();
                    break;
                case "3":
                    createAccount();
                    break;
                case "4":
                    AdminMenu.start();
                    break;
                case "5":
                    running = false;
                    System.out.println("Exiting application...");
                    break;
                default:
                    System.out.println("Invalid option");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\nMain Menu");
        System.out.println("1. Find and reserve a room");
        System.out.println("2. See my reservations");
        System.out.println("3. Create an account");
        System.out.println("4. Admin");
        System.out.println("5. Exit");
        System.out.print("Enter choice: ");
    }

    // -------------------- FEATURES --------------------

    private static void findAndReserveRoom() {
        try {
            System.out.print("Enter Check-In Date (dd/MM/yyyy): ");
            String checkIn = scanner.nextLine();

            System.out.print("Enter Check-Out Date (dd/MM/yyyy): ");
            String checkOut = scanner.nextLine();

            if (!ValidateDate.validateDate(checkIn, checkOut)) {
                throw new IllegalArgumentException("Invalid reservation dates. Use dd/MM/yyyy and ensure check-out is after check-in.");
            }


            Collection<IRoom> rooms = hotelResource.findARoom(checkIn, checkOut);

            if (rooms.isEmpty()) {
                System.out.println("No rooms available");
                return;
            }

            Collection<IRoom> filteredRooms = filterRoomsByType(rooms);

            if (filteredRooms.isEmpty()) {
                System.out.println("No rooms available for selected filter");
                return;
            }

            System.out.println("\nAvailable Rooms:");
            filteredRooms.forEach(System.out::println);

            System.out.print("Enter room number to book: ");
            String roomNumber = scanner.nextLine();

            IRoom selectedRoom = filteredRooms.stream()
                    .filter(r -> r.getRoomNumber().equals(roomNumber))
                    .findFirst()
                    .orElse(null);

            if (selectedRoom == null) {
                System.out.println("Invalid room selection");
                return;
            }

            System.out.print("Enter your email: ");
            String email = scanner.nextLine();

            String finalCheckIn = hotelResource.getLastSearchCheckIn();
            String finalCheckOut = hotelResource.getLastSearchCheckOut();
            boolean isRecommended = !finalCheckIn.equals(checkIn) || !finalCheckOut.equals(checkOut);

            if (isRecommended) {
                System.out.println("Rooms are not available for the selected dates.");
                System.out.println("Recommended rooms are available for Check-In: " + finalCheckIn + " and Check-Out: " + finalCheckOut);

                while (true) {
                    System.out.print("Do you want to book for recommended dates? (y/n): ");
                    String choice = scanner.nextLine().trim().toLowerCase();

                    if ("y".equals(choice)) {
                        break; // Proceed with booking
                    } else if ("n".equals(choice)) {
                        System.out.println("Booking canceled.");
                        return; // Return to main menu
                    } else {
                        System.out.println("Invalid input. Please enter 'y' or 'n'.");
                    }
                }
            }

            Reservation reservation = hotelResource.bookARoom(email, selectedRoom, finalCheckIn, finalCheckOut);

            System.out.println("Reservation successful!");
            System.out.println(reservation);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static Collection<IRoom> filterRoomsByType(Collection<IRoom> rooms) {
        while (true) {
            System.out.println("\nFilter Rooms:");
            System.out.println("1. Paid rooms only");
            System.out.println("2. Free rooms only");
            System.out.println("3. All available rooms");
            System.out.print("Enter choice: ");

            String choice = scanner.nextLine();

            if ("1".equals(choice)) {
                return rooms.stream()
                        .filter(r -> !r.isFree())
                        .collect(java.util.stream.Collectors.toList());
            } else if ("2".equals(choice)) {
                return rooms.stream()
                        .filter(IRoom::isFree)
                        .collect(java.util.stream.Collectors.toList());
            } else if ("3".equals(choice)) {
                return rooms;
            }

            System.out.println("Invalid option. Please enter 1, 2, or 3.");
        }
    }

    private static void seeReservations() {
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        try {
            Collection<Reservation> reservations = hotelResource.getCustomersReservations(email);

            if (reservations.isEmpty()) {
                System.out.println("No reservations found");
            } else {
                reservations.forEach(System.out::println);
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void createAccount() {
        try {
            System.out.print("First Name: ");
            String firstName = scanner.nextLine();

            System.out.print("Last Name: ");
            String lastName = scanner.nextLine();

            System.out.print("Email: ");
            String email = scanner.nextLine();

            hotelResource.createACustomer(email, firstName, lastName);

            System.out.println("Account created successfully!");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
