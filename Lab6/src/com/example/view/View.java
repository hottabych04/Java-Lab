package com.example.view;

import com.example.controller.Controller;
import com.example.model.Parent;
import com.example.model.Student;
import com.example.model.Subject;
import com.example.model.Teacher;
import com.example.test.TestDb;
import com.example.util.DatabaseManager;
import com.example.util.Logger;
import com.example.util.PropertiesReader;

import java.util.List;
import java.util.Scanner;

public class View {
    private Scanner scanner = new Scanner(System.in);
    private DatabaseManager dbManager;
    private PropertiesReader property;
    private TestDb testDb;
    private Logger logger;

    public View(DatabaseManager dbManager, PropertiesReader property, TestDb testDb, Logger logger) {
        this.dbManager = dbManager;
        this.property = property;
        this.testDb = testDb;
        this.logger = logger;
        logger.logDebug("Инициализация View");
    }

    public void showStartMessage(){
        if (property.getProperty("autotests").equals("true")) {
            testDb.startTests();
        }
        logger.logInfo("Программа стартует с логином " + property.getProperty("login"));
        System.out.println("Привет " + property.getProperty("login") + "!");
    }

    public void showMainMenu() {
        while (true) {
            System.out.println("\nГлавное меню:");
            System.out.println("1. Управление предметами");
            System.out.println("2. Управление преподавателями");
            System.out.println("3. Управление родителями");
            System.out.println("4. Управление студентами");
            if (property.getProperty("group").equals("root")){
                System.out.println("5. Отладка");
                System.out.println("6. Автотесты");
            }
            System.out.println("0. Выход");

            System.out.print("Выберите действие: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    showSubjectMenu();
                    break;
                case "2":
                    showTeacherMenu();
                    break;
                case "3":
                    showParentMenu();
                    break;
                case "4":
                    showStudentMenu();
                    break;
                case "5":
                    if (property.getProperty("group").equals("root")){
                        property.switchDebug();
                    } else {
                        System.out.println("Неверный выбор. Попробуйте снова.");
                    }
                    break;
                case "6":
                    if (property.getProperty("group").equals("root")){
                        testDb.startTests();
                    } else {
                        System.out.println("Неверный выбор. Попробуйте снова.");
                    }
                    break;
                case "0":
                    logger.logInfo("Программа завершает свою работу");
                    return;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

    // Меню предметов
    private void showSubjectMenu() {
        while (true) {
            System.out.println("\nУправление предметами:");
            System.out.println("1. Показать все предметы");
            System.out.println("2. Добавить предмет");
            System.out.println("3. Изменить предмет");
            System.out.println("4. Удалить предмет");
            System.out.println("0. Назад");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    displayAllSubjects();
                    break;
                case "2":
                    addNewSubject();
                    break;
                case "3":
                    updateSubject();
                    break;
                case "4":
                    deleteSubject();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Неверный выбор.");
            }
        }
    }

    // Меню преподавателей
    private void showTeacherMenu() {
        while (true) {
            System.out.println("\nУправление преподавателями:");
            System.out.println("1. Показать всех преподавателей");
            System.out.println("2. Добавить преподавателя");
            System.out.println("3. Изменить данные преподавателя");
            System.out.println("4. Удалить преподавателя");
            System.out.println("0. Назад");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    displayAllTeachers();
                    break;
                case "2":
                    addNewTeacher();
                    break;
                case "3":
                    updateTeacher();
                    break;
                case "4":
                    deleteTeacher();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Неверный выбор.");
            }
        }
    }

    // Меню родителей
    private void showParentMenu() {
        while (true) {
            System.out.println("\nУправление родителями:");
            System.out.println("1. Показать всех родителей");
            System.out.println("2. Добавить родителя");
            System.out.println("3. Изменить данные родителя");
            System.out.println("4. Удалить родителя");
            System.out.println("0. Назад");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    displayAllParents();
                    break;
                case "2":
                    addNewParent();
                    break;
                case "3":
                    updateParent();
                    break;
                case "4":
                    deleteParent();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Неверный выбор.");
            }
        }
    }

    // Меню студентов
    private void showStudentMenu() {
        while (true) {
            System.out.println("\nУправление студентами:");
            System.out.println("1. Показать всех студентов");
            System.out.println("2. Добавить студента");
            System.out.println("3. Изменить данные студента");
            System.out.println("4. Удалить студента");
            System.out.println("5. Выставить оценки студенту");
            System.out.println("0. Назад");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    displayAllStudents();
                    break;
                case "2":
                    addNewStudent();
                    break;
                case "3":
                    updateStudent();
                    break;
                case "4":
                    deleteStudent();
                    break;
                case "5":
                    gradeStudent();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Неверный выбор.");
            }
        }
    }

    // Методы для работы с предметами
    private void displayAllSubjects() {
        List<Subject> subjects = dbManager.getAllSubjects();
        if (subjects.isEmpty()) {
            System.out.println("Список предметов пуст.");
            return;
        }
        for (Subject subject : subjects) {
            System.out.println(subject);
        }
    }

    private void addNewSubject() {
        System.out.print("Введите название предмета: ");
        String name = scanner.nextLine();
        dbManager.addSubject(new Subject(name));
        System.out.println("Предмет успешно добавлен.");
    }

    private void updateSubject() {
        System.out.print("Введите ID предмета для обновления: ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.print("Введите новое название предмета: ");
        String name = scanner.nextLine();

        dbManager.updateSubject(id, new Subject(name));
        System.out.println("Предмет успешно обновлен.");
    }

    private void deleteSubject() {
        System.out.print("Введите ID предмета для удаления: ");
        int id = Integer.parseInt(scanner.nextLine());
        dbManager.removeSubject(id);
        System.out.println("Предмет успешно удален.");
    }

    // Методы для работы с преподавателями
    private void displayAllTeachers() {
        List<Teacher> teachers = dbManager.getAllTeachers();
        if (teachers.isEmpty()) {
            System.out.println("Список преподавателей пуст.");
            return;
        }
        for (Teacher teacher : teachers) {
            System.out.println(teacher);
        }
    }

    private void addNewTeacher() {
        System.out.print("Введите имя преподавателя: ");
        String name = scanner.nextLine();

        // Выбор предмета
        displayAllSubjects();
        System.out.print("Введите ID предмета: ");
        int subjectId = Integer.parseInt(scanner.nextLine());

        Subject subject = dbManager.getSubjectById(subjectId);
        if (subject != null) {
            dbManager.addTeacher(new Teacher(name, subject));
            System.out.println("Преподаватель успешно добавлен.");
        } else {
            System.out.println("Предмет не найден.");
        }
    }

    private void updateTeacher() {
        System.out.print("Введите ID преподавателя для обновления: ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.print("Введите новое имя преподавателя: ");
        String name = scanner.nextLine();

        // Выбор предмета
        displayAllSubjects();
        System.out.print("Введите ID предмета: ");
        int subjectId = Integer.parseInt(scanner.nextLine());

        Subject subject = dbManager.getSubjectById(subjectId);
        if (subject != null) {
            dbManager.updateTeacher(id, new Teacher(name, subject));
            System.out.println("Данные преподавателя успешно обновлены.");
        } else {
            System.out.println("Предмет не найден.");
        }
    }

    private void deleteTeacher() {
        System.out.print("Введите ID преподавателя для удаления: ");
        int id = Integer.parseInt(scanner.nextLine());
        dbManager.removeTeacher(id);
        System.out.println("Преподаватель успешно удален.");
    }

    // Методы для работы с родителями
    private void displayAllParents() {
        List<Parent> parents = dbManager.getAllParents();
        if (parents.isEmpty()) {
            System.out.println("Список родителей пуст.");
            return;
        }
        for (Parent parent : parents) {
            System.out.println(parent);
        }
    }

    private void addNewParent() {
        System.out.print("Введите имя родителя: ");
        String name = scanner.nextLine();

        Parent parent = new Parent(name);
        parent.setMood("нейтральный"); // Начальное настроение

        dbManager.addParent(parent);
        System.out.println("Родитель успешно добавлен.");
    }

    private void updateParent() {
        System.out.print("Введите ID родителя для обновления: ");
        int id = Integer.parseInt(scanner.nextLine());

        Parent currentParent = dbManager.getParentById(id);
        if (currentParent == null) {
            System.out.println("Родитель не найден.");
            return;
        }

        System.out.print("Введите новое имя родителя: ");
        String name = scanner.nextLine();

        System.out.println("Выберите настроение родителя:");
        System.out.println("1. Хмурый");
        System.out.println("2. Удовлетворенный");
        System.out.println("3. Радостный");
        System.out.println("4. Нейтральный");

        String mood;
        switch (scanner.nextLine()) {
            case "1":
                mood = "хмурый";
                break;
            case "2":
                mood = "удовлетворенный";
                break;
            case "3":
                mood = "радостный";
                break;
            default:
                mood = "нейтральный";
                break;
        }

        Parent updatedParent = new Parent(name);
        updatedParent.setMood(mood);

        dbManager.updateParent(id, updatedParent);
        System.out.println("Данные родителя успешно обновлены.");
    }

    private void deleteParent() {
        System.out.print("Введите ID родителя для удаления: ");
        int id = Integer.parseInt(scanner.nextLine());
        dbManager.removeParent(id);
        System.out.println("Родитель успешно удален.");
    }

    // Методы для работы со студентами
    private void displayAllStudents() {
        List<Student> students = dbManager.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("Список студентов пуст.");
            return;
        }
        for (Student student : students) {
            System.out.println(student);
        }
    }

    private void addNewStudent() {
        try {
            System.out.print("Введите имя студента: ");
            String name = scanner.nextLine();

            System.out.print("Введите возраст: ");
            int age = Integer.parseInt(scanner.nextLine());

            System.out.print("Введите пол (М/Ж): ");
            String gender = scanner.nextLine();

            // Выбор родителя
            displayAllParents();
            System.out.print("Введите ID родителя: ");
            int parentId = Integer.parseInt(scanner.nextLine());

            Parent parent = dbManager.getParentById(parentId);
            if (parent == null) {
                System.out.println("Родитель не найден. Сначала добавьте родителя.");
                return;
            }

            Student student = new Student(name, age, gender, parent);
            dbManager.addStudent(student);
            System.out.println("Студент успешно добавлен.");
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: Введите корректное число.");
        } catch (Exception e) {
            System.out.println("Ошибка при добавлении студента: " + e.getMessage());
        }
    }

    private void updateStudent() {
        try {
            System.out.print("Введите ID студента для обновления: ");
            int id = Integer.parseInt(scanner.nextLine());

            Student currentStudent = dbManager.getStudentById(id);
            if (currentStudent == null) {
                System.out.println("Студент не найден.");
                return;
            }

            System.out.print("Введите новое имя студента: ");
            String name = scanner.nextLine();

            System.out.print("Введите новый возраст: ");
            int age = Integer.parseInt(scanner.nextLine());

            System.out.print("Введите новый пол (М/Ж): ");
            String gender = scanner.nextLine();

            // Выбор родителя
            displayAllParents();
            System.out.print("Введите ID родителя: ");
            int parentId = Integer.parseInt(scanner.nextLine());

            Parent parent = dbManager.getParentById(parentId);
            if (parent == null) {
                System.out.println("Родитель не найден.");
                return;
            }

            Student updatedStudent = new Student(name, age, gender, parent);
            updatedStudent.setAverageGrade(currentStudent.getAverageGrade());
            updatedStudent.setBonus(currentStudent.getBonus());

            dbManager.updateStudent(id, updatedStudent);
            System.out.println("Данные студента успешно обновлены.");
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: Введите корректное число.");
        } catch (Exception e) {
            System.out.println("Ошибка при обновлении данных студента: " + e.getMessage());
        }
    }

    private void deleteStudent() {
        try {
            System.out.print("Введите ID студента для удаления: ");
            int id = Integer.parseInt(scanner.nextLine());
            dbManager.removeStudent(id);
            System.out.println("Студент успешно удален.");
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: Введите корректное число.");
        }
    }

    private void gradeStudent() {
        try {
            System.out.print("Введите ID студента для выставления оценок: ");
            int studentId = Integer.parseInt(scanner.nextLine());

            Student student = dbManager.getStudentById(studentId);
            if (student == null) {
                System.out.println("Студент не найден.");
                return;
            }

            // Выбор преподавателя
            displayAllTeachers();
            System.out.print("Введите ID преподавателя: ");
            int teacherId = Integer.parseInt(scanner.nextLine());

            Teacher teacher = dbManager.getTeacherById(teacherId);
            if (teacher == null) {
                System.out.println("Преподаватель не найден.");
                return;
            }

            int[] grades = getGradesInput();

            Controller controller = new Controller(logger);
            controller.assignGrades(teacher, student, grades);

            dbManager.updateStudent(studentId, student);
            System.out.println("Оценки успешно выставлены.");
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: Введите корректное число.");
        } catch (Exception e) {
            System.out.println("Ошибка при выставлении оценок: " + e.getMessage());
        }
    }

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
}

