package com.example.util;

import javax.swing.*;

/**
 * Класс для проверки корректности ввода имени в текстовые поля.
 * Использует методы утилитного класса {@link ValidationUtils}.
 */
public class NameVerifier extends InputVerifier {

    /**
     * Проверяет, является ли текст в компоненте допустимым именем.
     *
     * @param input компонент {@link JComponent}, содержащий текст для проверки.
     * @return {@code true}, если текст является допустимым именем, иначе {@code false}.
     */
    @Override
    public boolean verify(JComponent input) {
        return ValidationUtils.isValidName(((JTextField) input).getText());
    }
}
