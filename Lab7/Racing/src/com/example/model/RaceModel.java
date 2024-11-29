package com.example.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Модель гонки, хранящая состояние и параметры соревнования.
 * Управляет списком кнопок-гонщиков и состоянием трека.
 */
public class RaceModel {
    /** Список кнопок-участников гонки */
    private List<RaceButton> buttons;
    /** Ширина гоночной трассы */
    private int raceTrackWidth;
    /** Флаг завершения гонки */
    private boolean raceFinished;

    /**
     * Создает модель гонки с заданным количеством кнопок и шириной трассы.
     *
     * @param buttonCount количество кнопок-участников
     * @param trackWidth ширина гоночной трассы
     */
    public RaceModel(int buttonCount, int trackWidth) {
        this.raceTrackWidth = trackWidth;
        buttons = new ArrayList<>();
        for (int i = 0; i < buttonCount; i++) {
            buttons.add(new RaceButton("Гонщик " + (i + 1)));
        }
        raceFinished = false;
    }

    /**
     * Возвращает список кнопок-участников.
     *
     * @return список гоночных кнопок
     */
    public List<RaceButton> getButtons() {
        return buttons;
    }

    /**
     * Возвращает ширину гоночной трассы.
     *
     * @return ширина трассы в пикселях
     */
    public int getRaceTrackWidth() {
        return raceTrackWidth;
    }

    /**
     * Проверяет, завершена ли гонка.
     *
     * @return true, если гонка завершена, иначе false
     */
    public synchronized boolean isRaceFinished() {
        return raceFinished;
    }

    /**
     * Проверяет, завершена ли гонка.
     *
     * @return true, если гонка завершена, иначе false
     */
    public synchronized void setRaceFinished(boolean finished) {
        this.raceFinished = finished;
    }
}