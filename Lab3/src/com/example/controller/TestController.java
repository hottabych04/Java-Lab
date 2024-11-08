package com.example.controller;


import com.example.logger.ErrMsgLog;
import com.example.model.CustomArrayList;
import com.example.model.Subject;
import com.example.model.Teacher;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

/**
 * Контроллер для тестирования различных реализаций списков (ArrayList, LinkedList, CustomArrayList).
 * Выполняет добавление и удаление объектов типа {@link Teacher} в списках и выводит результаты с замером времени.
 */
public class TestController {

    /**
     * Логгер для записи сообщений о работе и ошибках контроллера.
     */
    private static final ErrMsgLog errLogger = new ErrMsgLog();

    /**
     * Список типа ArrayList для хранения объектов {@link Teacher}.
     */
    private final List<Teacher> teachersArrayList = new ArrayList<>();

    /**
     * Список типа CustomArrayList для хранения объектов {@link Teacher}.
     */
    private final List<Teacher> customArrayList = new CustomArrayList<>();

    /**
     * Список типа LinkedList для хранения объектов {@link Teacher}.
     */
    private final List<Teacher> teachersLinkedList = new LinkedList<>();

    /**
     * Тестирует добавление и удаление элементов в {@link ArrayList}.
     *
     * @param num Количество элементов для добавления и удаления.
     */
    public void testArrayList(int num) {
        System.out.println("\nArrayList");
        fillWithGeneratedList(teachersArrayList, num);
        removeElementsOfList(teachersArrayList, num);
    }

    /**
     * Тестирует добавление и удаление элементов в {@link LinkedList}.
     *
     * @param num Количество элементов для добавления и удаления.
     */
    public void testLinkedList(int num) {
        System.out.println("\nLinkedList");
        fillWithGeneratedList(teachersLinkedList, num);
        removeElementsOfList(teachersLinkedList, num);
    }

    /**
     * Тестирует добавление и удаление элементов в {@link CustomArrayList}.
     *
     * @param num Количество элементов для добавления и удаления.
     */
    public void testCustomArrayList(int num) {
        System.out.println("\nCustomArrayList");
        fillWithGeneratedList(customArrayList, num);
        removeElementsOfList(customArrayList, num);
    }

    /**
     * Заполняет список случайно сгенерированными объектами {@link Teacher} и измеряет время выполнения операции.
     *
     * @param list Список для заполнения.
     * @param num  Количество элементов для добавления.
     */
    private void fillWithGeneratedList(List<Teacher> list, int num) {
        var sum = new AtomicLong();
        IntStream.range(0, num).forEach(
                i -> {
                    try {
                        var startTime = System.nanoTime();
                        list.add(generateTeacher(i));
                        var stopTime = System.nanoTime();

                        var diff = stopTime - startTime;
                        sum.addAndGet(diff);
                        if (i < 2 || i >= num - 2) {
                            System.out.printf("add, ID=%d, %d (ns)\n", i, diff);
                        }
                    } catch (Exception e) {
                        errLogger.addErrWithLog(e);
                        errLogger.showErrText(e);
                    }
                }
        );
        System.out.printf("addTotalCount=%d\n", num);
        System.out.printf("addTotalTime=%d (ns), %d (ms)\n", sum.get(), sum.get() / 1_000_000);
        System.out.printf("addMedianTime=%d (ns)\n", sum.get() / num);
    }

    /**
     * Удаляет элементы из начала списка и измеряет время выполнения операции.
     *
     * @param list Список, из которого будут удаляться элементы.
     * @param num  Количество элементов для удаления.
     */
    private void removeElementsOfList(List<Teacher> list, int num) {
        AtomicLong sum = new AtomicLong();
        IntStream.range(0, num).forEach(
                i -> {
                    try {
                        var startTime = System.nanoTime();
                        list.removeFirst();
                        var stopTime = System.nanoTime();

                        var diff = stopTime - startTime;
                        sum.addAndGet(diff);
                        if (i < 2 || i >= num - 2) {
                            System.out.printf("remove, ID=%d, %d (ns)\n", i, diff);
                        }
                    } catch (Exception e) {
                        errLogger.addErrWithLog(e);
                        errLogger.showErrText(e);
                    }
                }
        );
        System.out.printf("removeTotalCount=%d\n", num);
        System.out.printf("removeTotalTime=%d (ns), %d (ms)\n", sum.get(), sum.get() / 1_000_000);
        System.out.printf("removeMedianTime=%d (ns)\n", sum.get() / num);
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
}
