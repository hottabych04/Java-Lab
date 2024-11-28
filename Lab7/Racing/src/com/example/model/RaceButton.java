package com.example.model;

import javax.swing.*;

// Кастомный элемент для гонки
public class RaceButton extends JButton {
    private int position;

    public RaceButton(String name) {
        super(name);
        position = 0;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}