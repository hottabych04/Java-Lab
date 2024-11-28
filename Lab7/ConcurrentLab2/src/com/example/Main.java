package com.example;

import com.example.controller.TestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.IntStream;

/**
 * Класс {@code Main} является точкой входа в приложение.
 * Он создает студентов, родителей, преподавателей и предметы, и демонстрирует
 * процесс выставления оценок и расчета премиальных.
 */
public class Main {
    public static void main(String[] args) {
        var formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        var testController = new TestController();

        System.out.printf("Start program: %s\n", LocalDateTime.now().format(formatter));

        IntStream.range(1, 6).forEach(i -> {
            var numOfElements = (int) Math.pow(10, i);
            System.out.printf("\n---------- TEST WITH %d ELEMENTS----------\n", numOfElements);
            try {
                testController.testArrayList(numOfElements);
                testController.testLinkedList(numOfElements);
                testController.testCustomArrayList(numOfElements);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        System.out.printf("\nFinish program: %s\n", LocalDateTime.now().format(formatter));
    }
}

