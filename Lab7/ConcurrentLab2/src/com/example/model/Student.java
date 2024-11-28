package com.example.model;

/**
 * Класс {@code Student} представляет студента в образовательной системе.
 * Хранит личную информацию о студенте, включая имя, возраст,
 * пол, среднюю оценку и информацию о родителе.
 * Студент может получать премиальные в зависимости от успеваемости,
 * которые управляются родителем.
 */
public class Student {
    private String name;
    private int age;
    private String gender;
    private double averageGrade;
    private com.example.model.Parent parent;
    private int bonus;

    /**
     * Создает нового студента с указанными данными.
     *
     * @param name   имя студента
     * @param age    возраст студента
     * @param gender пол студента (например, "Мужской", "Женский")
     * @param parent родитель студента
     */
    public Student(String name, int age, String gender, com.example.model.Parent parent) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.parent = parent;
    }

    /**
     * Возвращает имя студента.
     *
     * @return имя студента
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает возраст студента.
     *
     * @return возраст студента
     */
    public int getAge() {
        return age;
    }

    /**
     * Возвращает пол студента.
     *
     * @return пол студента
     */
    public String getGender() {
        return gender;
    }

    /**
     * Возвращает родителя студента.
     *
     * @return родитель студента
     */
    public Parent getParent() {
        return parent;
    }

    /**
     * Устанавливает среднюю оценку студента на основе выставленных оценок.
     *
     * @param averageGrade средняя оценка студента
     */
    public void setAverageGrade(double averageGrade) {
        this.averageGrade = averageGrade;
    }

    /**
     * Возвращает среднюю оценку студента.
     *
     * @return средняя оценка студента
     */
    public double getAverageGrade() {
        return averageGrade;
    }

    /**
     * Устанавливает премиальные, которые студент получает от родителя.
     *
     * @param bonus сумма премиальных для студента
     */
    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    /**
     * Возвращает сумму премиальных студента.
     *
     * @return сумма премиальных
     */
    public int getBonus() {
        return bonus;
    }

    /**
     * Возвращает строковое представление данных студента, включая
     * имя, возраст, пол, среднюю оценку и сумму премиальных.
     *
     * @return строковое представление информации о студенте
     */
    @Override
    public String toString() {
        return "Студент: " + name + ", Возраст: " + age + ", Пол: " + gender +
                ", Средняя оценка: " + averageGrade + ", Премиальные: " + bonus;
    }
}

