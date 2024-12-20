package com.example.model;

/**
 * Класс {@code Parent} представляет родителя студента.
 * Родитель следит за успеваемостью студента и определяет своё настроение
 * на основе оценок студента. Родитель также может выдавать премиальные
 * в зависимости от результатов студента.
 */
public class Parent {
    private String name;
    private String mood;

    /**
     * Создает нового родителя с указанным именем.
     *
     * @param name имя родителя
     */
    public Parent(String name) {
        this.name = name;
        this.mood = "Удовлетворенный";
    }

    /**
     * Возвращает имя родителя.
     *
     * @return имя родителя
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает текущее настроение родителя, которое отражает его
     * удовлетворенность успеваемостью студента.
     *
     * @return настроение родителя ("хмурый", "удовлетворенный" или "радостный")
     */
    public String getMood() {
        return mood;
    }

    /**
     * Устанавливает настроение родителя на основе успеваемости студента.
     *
     * @param mood новое настроение родителя
     */
    public void setMood(String mood) {
        this.mood = mood;
    }

    /**
     * Устанавливает новое имя родителя.
     *
     * @param name новое имя родителя
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Выдает премиальные студенту на основе настроения родителя.
     * Если родитель "удовлетворенный", студент получает 5000. Если
     * родитель "радостный", студент получает 10000. Если родитель
     * "хмурый", премиальные не выдаются.
     *
     * @param student студент, получающий премиальные
     */
    public void giveBonus(Student student) {
        if (mood.equals("удовлетворенный")) {
            student.setBonus(5000);
        } else if (mood.equals("радостный")) {
            student.setBonus(10000);
        } else {
            student.setBonus(0);
        }
    }

    /**
     * Возвращает строковое представление данных родителя, включая
     * его имя и настроение.
     *
     * @return строковое представление информации о родителе
     */
    @Override
    public String toString() {
        return "Родитель: " + name + ", Настроение: " + mood;
    }
}
