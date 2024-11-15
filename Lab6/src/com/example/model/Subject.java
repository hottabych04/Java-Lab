package com.example.model;

/**
 * Класс {@code Subject} представляет учебный предмет.
 * Каждый преподаватель связан с определенным предметом, который он преподает.
 */
public class Subject {
    private String name;

    /**
     * Создает новый предмет с указанным названием.
     *
     * @param name название предмета
     */
    public Subject(String name) {
        this.name = name;
    }

    /**
     * Возвращает название предмета.
     *
     * @return название предмета
     */
    public String getName() {
        return name;
    }

    public void setName(String name) { this.name = name; }

    /**
     * Возвращает строковое представление данных предмета.
     *
     * @return строковое представление информации о предмете
     */
    @Override
    public String toString() {
        return "Предмет: " + name;
    }
}

