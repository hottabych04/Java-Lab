package com.example.controller;


import com.example.model.CustomArrayList;
import com.example.model.Subject;
import com.example.model.Teacher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * Контроллер для многопоточного тестирования различных реализаций списков.
 * Выполняет параллельное добавление и удаление объектов типа {@link Teacher} в списках.
 */
public class TestController {

    /**
     * Список типа ArrayList для хранения объектов {@link Teacher}.
     */
    private final List<Teacher> teachersArrayList = Collections.synchronizedList(new ArrayList<>());

    /**
     * Список типа CustomArrayList для хранения объектов {@link Teacher}.
     */
    private final List<Teacher> customArrayList = Collections.synchronizedList(new CustomArrayList<>());

    /**
     * Список типа LinkedList для хранения объектов {@link Teacher}.
     */
    private final List<Teacher> teachersLinkedList = Collections.synchronizedList(new LinkedList<>());

    /**
     * ExecutorService для параллельного выполнения задач.
     */
    private final ExecutorService executorService;

    /**
     * Конструктор контроллера с инициализацией ExecutorService.
     */
    public TestController() {
        // Создаем пул потоков с количеством потоков, равным количеству доступных процессоров
        int processorCount = Runtime.getRuntime().availableProcessors();
        this.executorService = Executors.newFixedThreadPool(processorCount);
    }

    /**
     * Тестирует многопоточное добавление и удаление элементов в {@link ArrayList}.
     *
     * @param num Количество элементов для добавления и удаления.
     */
    public void testArrayList(int num) throws InterruptedException {
        System.out.println("\nArrayList (Concurrent)");
        concurrentFillAndRemove(teachersArrayList, num);
    }

    /**
     * Тестирует многопоточное добавление и удаление элементов в {@link LinkedList}.
     *
     * @param num Количество элементов для добавления и удаления.
     */
    public void testLinkedList(int num) throws InterruptedException {
        System.out.println("\nLinkedList (Concurrent)");
        concurrentFillAndRemove(teachersLinkedList, num);
    }

    /**
     * Тестирует многопоточное добавление и удаление элементов в {@link CustomArrayList}.
     *
     * @param num Количество элементов для добавления и удаления.
     */
    public void testCustomArrayList(int num) throws InterruptedException {
        System.out.println("\nCustomArrayList (Concurrent)");
        concurrentFillAndRemove(customArrayList, num);
    }

    /**
     * Многопоточное заполнение и удаление элементов из списка.
     *
     * @param list Список для заполнения и удаления.
     * @param num  Количество элементов для добавления и удаления.
     */
    private void concurrentFillAndRemove(List<Teacher> list, int num) throws InterruptedException {
        // Очистка списка перед тестированием
        list.clear();

        // Замер времени добавления
        long fillStartTime = System.nanoTime();
        List<Future<?>> fillFutures = new ArrayList<>();

        // Многопоточное заполнение списка
        IntStream.range(0, num).forEach(i ->
                fillFutures.add(executorService.submit(() -> {
                    try {
                        synchronized (list) {
                            list.add(generateTeacher(i));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }))
        );

        // Ожидание завершения всех задач добавления
        fillFutures.forEach(future -> {
            try {
                future.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        long fillEndTime = System.nanoTime();
        long fillTotalTime = fillEndTime - fillStartTime;

        System.out.printf("addTotalCount=%d\n", num);
        System.out.printf("addTotalTime=%d (ns), %d (ms)\n", fillTotalTime, fillTotalTime / 1_000_000);
        System.out.printf("addMedianTime=%d (ns)\n", fillTotalTime / num);

        // Замер времени удаления
        long removeStartTime = System.nanoTime();
        List<Future<?>> removeFutures = new ArrayList<>();

        // Многопоточное удаление элементов
        IntStream.range(0, num).forEach(i ->
                removeFutures.add(executorService.submit(() -> {
                    try {
                        synchronized (list) {
                            if (!list.isEmpty()) {
                                list.removeFirst();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }))
        );

        // Ожидание завершения всех задач удаления
        removeFutures.forEach(future -> {
            try {
                future.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        long removeEndTime = System.nanoTime();
        long removeTotalTime = removeEndTime - removeStartTime;

        System.out.printf("removeTotalCount=%d\n", num);
        System.out.printf("removeTotalTime=%d (ns), %d (ms)\n", removeTotalTime, removeTotalTime / 1_000_000);
        System.out.printf("removeMedianTime=%d (ns)\n", removeTotalTime / num);
    }

    /**
     * Генерирует объект {@link Teacher} с заданным идентификатором и случайными параметрами.
     *
     * @param i Идентификатор учителя.
     * @return Объект типа {@link Teacher}.
     */
    private Teacher generateTeacher(Integer i) {
        return new Teacher(
                "Name-".concat(String.valueOf(i)),
                new Subject("subject-".concat(String.valueOf(i))));
    }

    /**
     * Закрытие ExecutorService при завершении работы.
     */
    public void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }
}