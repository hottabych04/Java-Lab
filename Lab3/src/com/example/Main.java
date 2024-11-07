package com.example;

import com.example.controller.Controller;
import com.example.model.Parent;
import com.example.model.Student;
import com.example.model.Subject;
import com.example.model.Teacher;
import com.example.view.View;

/**
 * Класс {@code Main} является точкой входа в приложение.
 * Он создает студентов, родителей, преподавателей и предметы, и демонстрирует
 * процесс выставления оценок и расчета премиальных.
 */
public class Main {
    public static void main(String[] args) {
        // Создание родителей
        Parent parent1 = new Parent("Родитель Джона");
        Parent parent2 = new Parent("Родитель Алекса");

        // Создание студентов
        Student student1 = new Student("Джон", 20, "Мужской", parent1);
        Student student2 = new Student("Алекс", 19, "Женский", parent2);

        // Создание предметов и преподавателей
        Subject math = new Subject("Математика");
        Subject science = new Subject("Наука");
        Teacher teacher1 = new Teacher("Мистер Смит", math);
        Teacher teacher2 = new Teacher("Миссис Джонсон", science);

        // Создание контроллера и вида
        Controller controller = new Controller();
        View view = new View();

        // Пример: Выставление оценок
        int[] gradesForJohn = view.getGradesInput();
        controller.assignGrades(teacher1, student1, gradesForJohn);
        view.displayStudentInfo(student1);

        int[] gradesForAlex = view.getGradesInput();
        controller.assignGrades(teacher2, student2, gradesForAlex);
        view.displayStudentInfo(student2);
    }
}

