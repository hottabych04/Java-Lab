package com.example.util;

/**
 * Утилитный класс для проверки валидности различных данных.
 */
public class ValidationUtils {

    /**
     * Проверяет, является ли имя допустимым.
     * Имя считается валидным, если:
     * - оно не равно {@code null};
     * - оно не является пустой строкой или строкой, состоящей только из пробелов;
     * - оно содержит только буквы (латиница, кириллица) и пробелы.
     *
     * @param name строка, представляющая имя для проверки.
     * @return {@code true}, если имя валидно, иначе {@code false}.
     */
    public static boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty() && name.matches("[a-zA-Zа-яА-я\\s]+");
    }

    /**
     * Проверяет, является ли возраст допустимым.
     * Возраст считается валидным, если:
     * - это число в диапазоне от 0 до 120 включительно;
     * - строка корректно преобразуется в целое число.
     *
     * @param age строка, представляющая возраст для проверки.
     * @return {@code true}, если возраст валиден, иначе {@code false}.
     */
    public static boolean isValidAge(String age) {
        try {
            int value = Integer.parseInt(age);
            return value >= 0 && value <= 120;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
