import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class UserDataApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите данные (Фамилия , Имя , Отчество , Дата Рождения , Номер Телефона , Пол):");
        String input = scanner.nextLine();
        scanner.close();

        try {
            String[] parts = input.split(" ");
            if (parts.length != 6) {
                throw new IllegalArgumentException("Неверное количество аргументов. Ожидается 6.");
            }

            String surname = parts[0];
            String name = parts[1];
            String middleName = parts[2];
            LocalDate dateOfBirth = validateDate(parts[3]);
            long phoneNumber = validatePhoneNumber(parts[4]);
            char gender = validateGender(parts[5]);

            saveToFile(surname, name, middleName, dateOfBirth, phoneNumber, gender);
        } catch (IllegalArgumentException | DateTimeParseException e) {
            System.err.println("Ошибка: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static LocalDate validateDate(String dateStr) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return LocalDate.parse(dateStr, formatter);
    }

    private static long validatePhoneNumber(String phoneNumberStr) {
        try {
            return Long.parseLong(phoneNumberStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Неверный формат номера телефона.");
        }
    }

    private static char validateGender(String genderStr) {
        if (genderStr.length() != 1 || !(genderStr.equalsIgnoreCase("f") || genderStr.equalsIgnoreCase("m"))) {
            throw new IllegalArgumentException("Пол должен быть 'f' или 'm'.");
        }
        return genderStr.toLowerCase().charAt(0);
    }

    private static void saveToFile(String surname, String name, String middleName, LocalDate dateOfBirth, long phoneNumber, char gender) throws IOException {
        String filename = surname + ".txt";
        String line = String.format("%s %s %s %s %d %c", surname, name, middleName, dateOfBirth, phoneNumber, gender);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write(line);
            writer.newLine();
        }
    }
}
