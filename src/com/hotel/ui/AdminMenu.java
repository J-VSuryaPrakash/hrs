package com.hotel.ui;

import com.hotel.api.AdminResource;
import com.hotel.models.FreeRoom;
import com.hotel.models.IRoom;
import com.hotel.models.Room;
import com.hotel.models.RoomType;

import java.util.*;

public class AdminMenu {

    private static final AdminResource adminResource = AdminResource.getInstance();
    private static final Scanner scanner = new Scanner(System.in);

    public static void start() {
        boolean running = true;

        while (running) {
            printMenu();

            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    if (adminResource.getAllCustomers().isEmpty()) {
                        System.out.println("No data found");
                    } else {
                        adminResource.getAllCustomers().forEach(System.out::println);
                    }
                    break;

                case "2":
                    if (adminResource.getAllRooms().isEmpty()) {
                        System.out.println("No data found");
                    } else {
                        adminResource.getAllRooms().forEach(System.out::println);
                    }
                    break;

                case "3":
                    if (adminResource.getAllReservations().isEmpty()) {
                        System.out.println("No data found");
                    } else {
                        adminResource.getAllReservations().forEach(System.out::println);
                    }
                    break;

                case "4":
                    addRooms();
                    break;

                case "5":
                    addFreeRooms();
                    break;

                case "6":
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\nAdmin Menu");
        System.out.println("1. See all customers");
        System.out.println("2. See all rooms");
        System.out.println("3. See all reservations");
        System.out.println("4. Add a room");
        System.out.println("5. Add a free room");
        System.out.println("6. Back");
        System.out.print("Enter choice: ");
    }

    private static void addRooms() {
        boolean addingRooms = true;

        while (addingRooms) {
            try {
                System.out.print("Enter room number: ");
                String roomNumber = scanner.nextLine();

                System.out.print("Enter price: ");
                double price = Double.parseDouble(scanner.nextLine());

                System.out.print("Enter room type (1 for SINGLE, 2 for DOUBLE): ");
                int typeInput = Integer.parseInt(scanner.nextLine());

                RoomType roomType = (typeInput == 1) ? RoomType.SINGLE : RoomType.DOUBLE;

                IRoom room = new Room(roomNumber, price, roomType);

                while (true) {
                    System.out.print("Add another room? (y/n): ");
                    String choice = scanner.nextLine();

                    if ("y".equalsIgnoreCase(choice)) {
                        try {
                            adminResource.addRoom(room);
                            System.out.println("Room added successfully!");
                        } catch (Exception roomError) {
                            System.out.println("Error: " + roomError.getMessage());
                        }
                        break;
                    } else if ("n".equalsIgnoreCase(choice)) {
                        addingRooms = false;
                        try {
                            adminResource.addRoom(room);
                            System.out.println("Room added successfully!");
                        } catch (Exception roomError) {
                            System.out.println("Error: " + roomError.getMessage());
                        }
                        break;
                    }

                    System.out.println("Invalid input. Please enter only 'y' or 'n': ");
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void addFreeRooms() {
        boolean addingRooms = true;

        while (addingRooms) {
            try {
                System.out.print("Enter room number: ");
                String roomNumber = scanner.nextLine();

                System.out.print("Enter room type (1 for SINGLE, 2 for DOUBLE): ");
                int typeInput = Integer.parseInt(scanner.nextLine());

                RoomType roomType = (typeInput == 1) ? RoomType.SINGLE : RoomType.DOUBLE;

                IRoom room = new FreeRoom(roomNumber, roomType);

                while (true) {
                    System.out.print("Add another free room? (y/n): ");
                    String choice = scanner.nextLine();

                    if ("y".equalsIgnoreCase(choice)) {
                        try {
                            adminResource.addRoom(room);
                            System.out.println("Free room added successfully!");
                        } catch (Exception roomError) {
                            System.out.println("Error: " + roomError.getMessage());
                        }
                        break;
                    } else if ("n".equalsIgnoreCase(choice)) {
                        addingRooms = false;
                        try {
                            adminResource.addRoom(room);
                            System.out.println("Free room added successfully!");
                        } catch (Exception roomError) {
                            System.out.println("Error: " + roomError.getMessage());
                        }
                        break;
                    }

                    System.out.println("Invalid input. Please enter only 'y' or 'n': ");
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
