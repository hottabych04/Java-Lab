package com.example.model;

import javax.swing.*;

/**
 * Кастомный компонент кнопки для гоночного симулятора.
 * Расширяет стандартную кнопку Swing с добавлением координаты позиции.
 */
public class RaceButton extends JButton {
    /** Текущая позиция кнопки на треке */
    private int position;

    /**
     * Создает кнопку-гонщика с заданным именем.
     *
     * @param name имя гонщика
     */
    public RaceButton(String name) {
        super(name);
        position = 0;
    }

    /**
     * Возвращает текущую позицию кнопки.
     *
     * @return координата позиции
     */
    public int getPosition() {
        return position;
    }

    /**
     * Устанавливает новую позицию кнопки.
     *
     * @param position новая координата позиции
     */
    public void setPosition(int position) {
        this.position = position;
    }
}