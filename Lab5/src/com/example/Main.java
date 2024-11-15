package com.example;

import com.example.controller.TestController;
import com.example.logger.Logger;
import com.example.view.CollectionPerformanceVisualizer;

import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.IntStream;

/**
 * Класс {@code Main} является точкой входа в приложение.
 */
public class Main {
    public static void main(String[] args) {
        var formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        Logger logger = new Logger("performance_logs.txt");
        var testController = new TestController(logger);

        System.out.printf("Start program: %s\n", LocalDateTime.now().format(formatter));

        IntStream.range(1, 6).forEach(i -> {
            var numOfElements = (int) Math.pow(10, i);
            System.out.printf("\n---------- TEST WITH %d ELEMENTS----------\n", numOfElements);
            logger.log(String.format("\n---------- TEST WITH %d ELEMENTS----------\n",  numOfElements));
            testController.testArrayList(numOfElements);
            testController.testLinkedList(numOfElements);
        });

        SwingUtilities.invokeLater(() -> {
            CollectionPerformanceVisualizer visualizer = new CollectionPerformanceVisualizer();
            visualizer.parseLogFile("performance_logs.txt");
            visualizer.setVisible(true);
        });

        System.out.printf("\nFinish program: %s\n", LocalDateTime.now().format(formatter));
    }
}

