package com.example.model;

import java.util.ArrayList;
import java.util.List;

public class RaceModel {
    private List<RaceButton> buttons;
    private int raceTrackWidth;
    private boolean raceFinished;

    public RaceModel(int buttonCount, int trackWidth) {
        this.raceTrackWidth = trackWidth;
        buttons = new ArrayList<>();
        for (int i = 0; i < buttonCount; i++) {
            buttons.add(new RaceButton("Гонщик " + (i + 1)));
        }
        raceFinished = false;
    }

    public List<RaceButton> getButtons() {
        return buttons;
    }

    public int getRaceTrackWidth() {
        return raceTrackWidth;
    }

    public synchronized boolean isRaceFinished() {
        return raceFinished;
    }

    public synchronized void setRaceFinished(boolean finished) {
        this.raceFinished = finished;
    }
}