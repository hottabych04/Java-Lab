package com.example.view;

import com.example.model.Student;

import java.util.Scanner;

/**
 * Класс {@code View} управляет взаимодействием с пользователем.
 * Он собирает ввод пользователя, например, оценки для студентов,
 * и выводит информацию, такую как результаты студента.
 */
public class View {
    private Scanner scanner = new Scanner(System.in);

    /**
     * Запрашивает у пользователя ввод 5 оценок для студента.
     * Если ввод недействителен (не числовой или вне диапазона),
     * программа повторно запрашивает ввод.
     *
     * @return массив из 5 корректных оценок
     */
    public int[] getGradesInput() {
        int[] grades = new int[5];
        System.out.println("Введите 5 оценок:");
        for (int i = 0; i < 5; i++) {
            while (true) {
                try {
                    System.out.print("Оценка " + (i + 1) + ": ");
                    grades[i] = Integer.parseInt(scanner.nextLine());
                    if (grades[i] < 1 || grades[i] > 5) {
                        throw new IllegalArgumentException("Оценка должна быть в диапазоне от 1 до 5.");
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Неверный ввод. Введите число.");
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return grades;
    }

    /**
     * Выводит информацию о студенте, включая имя,
     * возраст, пол, среднюю оценку и сумму премиальных.
     *
     * @param student студент, чья информация будет выведена
     */
    public void displayStudentInfo(Student student) {
        System.out.println(student);
    }
}

