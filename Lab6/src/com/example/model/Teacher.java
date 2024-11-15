package com.example.model;

/**
 * Класс {@code Teacher} представляет преподавателя.
 * Преподаватель связан с определенным предметом и может выставлять
 * оценки студентам. В зависимости от этих оценок настроение родителя
 * студента может изменяться, что влияет на премиальные студента.
 */
public class Teacher {
    private String name;
    private Subject subject;

    /**
     * Создает нового преподавателя с указанными именем и предметом.
     *
     * @param name    имя преподавателя
     * @param subject предмет, который преподает преподаватель
     */
    public Teacher(String name, Subject subject) {
        this.name = name;
        this.subject = subject;
    }

    /**
     * Возвращает имя преподавателя.
     *
     * @return имя преподавателя
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает предмет, который преподает преподаватель.
     *
     * @return предмет преподавателя
     */
    public Subject getSubject() {
        return subject;
    }

    /**
     * Выставляет оценки студенту и рассчитывает среднюю оценку.
     * В зависимости от средней оценки, настроение родителя изменяется,
     * что влияет на премиальные студента.
     *
     * @param student студент, которому выставляются оценки
     * @param grades  массив из 5 оценок
     * @throws IllegalArgumentException если количество оценок не равно 5
     */
    public void setGrades(Student student, int[] grades) {
        if (grades.length != 5) {
            throw new IllegalArgumentException("Должно быть ровно 5 оценок.");
        }

        double sum = 0;
        for (int grade : grades) {
            sum += grade;
        }
        double average = sum / grades.length;
        student.setAverageGrade(average);
        adjustParentMood(student);
    }

    /**
     * Регулирует настроение родителя на основе средней оценки студента.
     * - 3.0 <= средняя оценка < 4.0: Родитель "хмурый".
     * - 4.0 <= средняя оценка <= 4.5: Родитель "удовлетворенный".
     * - средняя оценка > 4.5: Родитель "радостный".
     *
     * @param student студент, чьи оценки оцениваются
     */
    private void adjustParentMood(Student student) {
        double avgGrade = student.getAverageGrade();
        Parent parent = student.getParent();

        if (avgGrade >= 3.0 && avgGrade < 4.0) {
            parent.setMood("хмурый");
        } else if (avgGrade >= 4.0 && avgGrade <= 4.5) {
            parent.setMood("удовлетворенный");
        } else if (avgGrade > 4.5) {
            parent.setMood("радостный");
        }

        parent.giveBonus(student);
    }

    /**
     * Возвращает строковое представление данных преподавателя, включая
     * его имя и предмет, который он преподает.
     *
     * @return строковое представление информации о преподавателе
     */
    @Override
    public String toString() {
        return "Преподаватель: " + name + ", Предмет: " + subject.getName();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
