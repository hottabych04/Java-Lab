package com.example.view;

import com.example.controller.RaceController;
import com.example.model.RaceButton;
import com.example.model.RaceModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Графическое представление гонки с использованием Swing.
 * Создает интерфейс с кнопками-гонщиками и элементами управления.
 */
public class RaceView extends JFrame {
    /** Модель гонки */
    private RaceModel model;
    /** Контроллер гонки */
    private RaceController controller;
    /** Панель для размещения кнопок */
    private JPanel buttonPanel;
    /** Кнопка перезапуска гонки */
    private JButton restartButton;

    /**
     * Создает представление гонки на основе заданной модели.
     *
     * @param model модель гонки
     */
    public RaceView(RaceModel model) {
        this.model = model;
        initComponents();
    }

    /**
     * Инициализирует графические компоненты интерфейса.
     * Настраивает расположение кнопок и элементов управления.
     */
    private void initComponents() {
        setTitle("Гонки кнопок");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        buttonPanel = new JPanel();
        buttonPanel.setLayout(null);
        buttonPanel.setPreferredSize(new Dimension(800, 400));

        // Добавление кнопок
        for (RaceButton button : model.getButtons()) {
            button.setBounds(0, 50 * model.getButtons().indexOf(button), 100, 40);
            buttonPanel.add(button);
        }

        restartButton = new JButton("Рестарт");
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.startRace();
            }
        });

        add(buttonPanel, BorderLayout.CENTER);
        add(restartButton, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Устанавливает контроллер для текущего представления.
     *
     * @param controller контроллер гонки
     */
    public void setController(RaceController controller) {
        this.controller = controller;
    }

    /**
     * Обновляет позицию кнопки на графическом интерфейсе.
     *
     * @param button кнопка, позицию которой необходимо обновить
     */
    public void updateButtonPosition(RaceButton button) {
        SwingUtilities.invokeLater(() -> {
            button.setBounds(button.getPosition(), button.getY(), button.getWidth(), button.getHeight());
            buttonPanel.repaint();
        });
    }

    /**
     * Сбрасывает позиции всех кнопок в начальное положение.
     */
    public void resetButtonPositions() {
        for (RaceButton button : model.getButtons()) {
            button.setBackground(Color.WHITE);
            button.setPosition(0);
            button.setBounds(0, button.getY(), button.getWidth(), button.getHeight());
        }
        buttonPanel.repaint();
    }

    /**
     * Отображает победителя гонки, меняя цвет кнопки и показывая диалоговое окно.
     *
     * @param winner кнопка-победитель
     */
    public void showWinner(RaceButton winner) {
        SwingUtilities.invokeLater(() -> {
            winner.setBackground(Color.GREEN);
            JOptionPane.showMessageDialog(this,
                    "Победитель: " + winner.getText(),
                    "Гонка окончена",
                    JOptionPane.INFORMATION_MESSAGE);
        });
    }
}
