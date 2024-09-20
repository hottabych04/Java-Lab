package com.example;

/**
 * Класс com.example.GameModel содержит логику игры, которая пытается угадать число,
 * загаданное пользователем, с использованием бинарного поиска.
 */
public class GameModel {
    private int lowerBound = 1;
    private int upperBound = 100;
    private int currentGuess;

    /**
     * Делает новое предположение на основе текущих границ.
     *
     * @return текущее предположение.
     */
    public int makeGuess() {
        currentGuess = (lowerBound + upperBound) / 2;
        return currentGuess;
    }

    /**
     * Устанавливает новые границы диапазона на основе ответа пользователя.
     *
     * @param isHigher true, если число больше текущего предположения, false, если меньше.
     */
    public void updateBounds(boolean isHigher) {
        if (isHigher) {
            lowerBound = currentGuess + 1;
        } else {
            upperBound = currentGuess - 1;
        }
    }

    /**
     * Проверяет, угадано ли число.
     *
     * @return true, если предположение совпадает с загаданным числом, иначе false.
     */
    public boolean isCorrectGuess() {
        return lowerBound == upperBound;
    }

    /**
     * Проверяет, не нарушил ли пользователь правила (если границы неверны).
     *
     * @return true, если границы нарушены (обман), иначе false.
     */
    public boolean isCheating() {
        return lowerBound > upperBound;
    }
}
