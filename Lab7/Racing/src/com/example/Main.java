package com.example;

import com.example.controller.RaceController;
import com.example.model.RaceModel;
import com.example.view.RaceView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RaceModel model = new RaceModel(5, 700); // 5 кнопок, трек шириной 700
            RaceView view = new RaceView(model);
            RaceController controller = new RaceController(model, view);

            view.setController(controller);
            view.setVisible(true);

            // Автоматический старт первой гонки
            controller.startRace();
        });
    }
}