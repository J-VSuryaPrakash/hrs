package com.hotel.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ValidateDate {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static boolean validateDate(String checkIn, String checkOut) {

        int[] checkInParts = parseDateParts(checkIn);
        int[] checkOutParts = parseDateParts(checkOut);

        if (checkInParts == null || checkOutParts == null) {
            return false;
        }

        LocalDate today = LocalDate.now();
        int[] todayParts = {
            today.getDayOfMonth(),
                today.getMonthValue(),
            today.getYear()
        };

        return compareDateParts(checkInParts, todayParts) >= 0
                && compareDateParts(checkOutParts, checkInParts) > 0;
    }

    public static int compareDates(String firstDate, String secondDate) {
        int[] firstParts = parseDateParts(firstDate);
        int[] secondParts = parseDateParts(secondDate);

        if (firstParts == null || secondParts == null) {
            throw new IllegalArgumentException("Invalid date format");
        }

        return compareDateParts(firstParts, secondParts);
    }

    public static String addDays(String date, int days) {
        int[] parts = parseDateParts(date);

        if (parts == null) {
            throw new IllegalArgumentException("Invalid date format");
        }

        LocalDate parsedDate = LocalDate.of(parts[2], parts[1], parts[0]);
        return parsedDate.plusDays(days).format(DATE_FORMAT);
    }

    private static int[] parseDateParts(String date) {
        if (date == null) {
            return null;
        }

        try {
            LocalDate parsedDate = LocalDate.parse(date, DATE_FORMAT);
            return new int[]{
                    parsedDate.getDayOfMonth(),
                    parsedDate.getMonthValue(),
                    parsedDate.getYear()
            };
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    private static int compareDateParts(int[] leftParts, int[] rightParts) {
        if (leftParts[2] != rightParts[2]) {
            return Integer.compare(leftParts[2], rightParts[2]);
        }

        if (leftParts[1] != rightParts[1]) {
            return Integer.compare(leftParts[1], rightParts[1]);
        }

        return Integer.compare(leftParts[0], rightParts[0]);
    }
}
