package com.example.view;

import com.example.controller.RaceController;
import com.example.model.RaceButton;
import com.example.model.RaceModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Представление (View)
public class RaceView extends JFrame {
    private RaceModel model;
    private RaceController controller;
    private JPanel buttonPanel;
    private JButton restartButton;

    public RaceView(RaceModel model) {
        this.model = model;
        initComponents();
    }

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

    public void setController(RaceController controller) {
        this.controller = controller;
    }

    public void updateButtonPosition(RaceButton button) {
        SwingUtilities.invokeLater(() -> {
            button.setBounds(button.getPosition(), button.getY(), button.getWidth(), button.getHeight());
            buttonPanel.repaint();
        });
    }

    public void resetButtonPositions() {
        for (RaceButton button : model.getButtons()) {
            button.setPosition(0);
            button.setBounds(0, button.getY(), button.getWidth(), button.getHeight());
        }
        buttonPanel.repaint();
    }

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
