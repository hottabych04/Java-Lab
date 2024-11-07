package com.example.controller;

import com.example.model.Student;
import com.example.model.Teacher;

/**
 * Класс {@code Controller} управляет взаимодействием между студентами,
 * преподавателями и родителями. Он обеспечивает процесс выставления
 * оценок студентам и обработку возможных исключений.
 */
public class Controller {
    /**
     * Выставляет оценки студенту от конкретного преподавателя и обрабатывает
     * возможные исключения, такие как неправильное количество оценок.
     *
     * @param teacher преподаватель, выставляющий оценки
     * @param student студент, получающий оценки
     * @param grades  массив оценок
     */
    public void assignGrades(Teacher teacher, Student student, int[] grades) {
        try {
            teacher.setGrades(student, grades);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}

