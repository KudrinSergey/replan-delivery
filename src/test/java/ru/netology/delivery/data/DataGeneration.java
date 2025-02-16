package ru.netology.delivery.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataGeneration {
    private DataGeneration() {
    }

    // Метод для создания Faker с заданной локалью
    private static Faker createFaker(String locale) {
        return new Faker(new Locale(locale));
    }

    public static String generateDate(int shift) {
        return LocalDate.now().plusDays(shift).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static String generateCity(String locale) {
        return createFaker(locale).address().cityName();
    }

    public static String generateName(String locale) {
        return createFaker(locale).name().fullName();
    }

    public static String generatePhone(String locale) {
        return createFaker(locale).phoneNumber().cellPhone(); // Используем метод для генерации номера телефона
    }

    public static class Registration {
        private Registration() {
        }

        public static UserInfo generateUser (String locale) {
            String city = DataGeneration.generateCity(locale);
            String name = DataGeneration.generateName(locale);
            String phone = DataGeneration.generatePhone(locale);
            return new UserInfo(city, name, phone);
        }
    }

    @Value
    public static class UserInfo {
        String city; // Город
        String name; // Имя
        String phone; // Номер телефона
    }
}
