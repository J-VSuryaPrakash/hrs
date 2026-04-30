# Hotel Reservation System

A Java-based command-line hotel reservation system that allows customers to book rooms and enables administrators to manage customers, rooms, and reservations.

## Features

- **Customer Management**: Create customer accounts with email validation
- **Room Management**: Add paid or free rooms with SINGLE/DOUBLE types
- **Reservations**: Book rooms with date conflict prevention
- **Admin Panel**: View all customers, rooms, and reservations
- **Room Filtering**: Customers can filter available rooms by paid, free, or all types
- **Date Validation**: Enforced dd/MM/yyyy format with past-date rejection
- **Input Validation**: Email format (with TLD), name format (letters only), unique room numbers

## Project Structure

```
src/com/hotel/
├── Main.java                 # Application entry point
├── models/                   # Data models
│   ├── Customer.java
│   ├── IRoom.java
│   ├── Room.java
│   ├── FreeRoom.java
│   ├── Reservation.java
│   ├── RoomType.java
├── services/                 # Business logic
│   ├── CustomerService.java
│   ├── ReservationService.java
├── api/                      # Resource layer
│   ├── AdminResource.java
│   ├── HotelResource.java
├── ui/                       # User interface
│   ├── MainMenu.java
│   ├── AdminMenu.java
└── util/                     # Utility functions
    └── ValidateDate.java
```

## Compilation

From the project root directory:

```bash
javac -d out -sourcepath src src/com/hotel/Main.java
```

This compiles all Java source files and places compiled classes in the `out/` directory.

## Running the Application

After compilation:

```bash
java -cp out com.hotel.Main
```

## Usage

### Main Menu
1. **Find and reserve a room** - Search for available rooms by check-in/check-out dates and book
2. **See my reservations** - View your bookings by email
3. **Create an account** - Register a new customer account
4. **Admin** - Access admin panel (no authentication required)
5. **Exit** - Quit the application

### Admin Menu
1. **See all customers** - Display all registered customers
2. **See all rooms** - Display all rooms (paid and free)
3. **See all reservations** - Display all reservations
4. **Add a room** - Create a paid room (price > 0)
5. **Add a free room** - Create a free room (price = 0)
6. **Back** - Return to main menu

## Date Format

All dates must be entered in **dd/MM/yyyy** format (e.g., 25/12/2026).

- Check-in date must not be in the past
- Check-out date must be after check-in date

## Input Validation

- **Email**: Must contain `@` and a valid TLD (e.g., user@domain.com)
- **Names**: Letters only (no numbers or special characters)
- **Room Number**: Must be unique and non-empty
- **Price**: Must be non-negative
- **Room Type**: 1 for SINGLE, 2 for DOUBLE

## Error Handling

The system provides clear error messages for:
- Invalid date formats or past dates
- Duplicate room numbers
- Invalid email or name formats
- Room booking conflicts (already reserved for selected dates)
- Missing customer records

## Notes

- Rooms are stored in memory; data is lost when the application exits
- No authentication required for admin access
- Reservations prevent double-booking for overlapping date ranges
