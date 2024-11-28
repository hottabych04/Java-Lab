package com.example.controller;

import com.example.model.RaceButton;
import com.example.model.RaceModel;
import com.example.view.RaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

// Контроллер (Controller)
public class RaceController {
    private RaceModel model;
    private RaceView view;
    private List<Thread> raceThreads;
    private CountDownLatch startLatch;

    public RaceController(RaceModel model, RaceView view) {
        this.model = model;
        this.view = view;
        raceThreads = new ArrayList<>();
    }

    public void startRace() {
        // Сброс состояния перед новой гонкой
        model.setRaceFinished(false);
        view.resetButtonPositions();

        startLatch = new CountDownLatch(1);
        raceThreads.clear();

        for (RaceButton button : model.getButtons()) {
            Thread raceThread = createRaceThread(button);
            raceThreads.add(raceThread);
        }

        // Одновременный старт всех потоков
        startLatch.countDown();
        raceThreads.forEach(Thread::start);
    }

    private Thread createRaceThread(RaceButton button) {
        return new Thread(() -> {
            try {
                startLatch.await(); // Синхронизация старта
                Random random = new Random();

                while (!model.isRaceFinished()) {
                    int speed = random.nextInt(5) + 1; // Случайная скорость

                    synchronized (model) {
                        if (model.isRaceFinished()) break;

                        button.setPosition(button.getPosition() + speed);
                        view.updateButtonPosition(button);

                        if (button.getPosition() >= model.getRaceTrackWidth()) {
                            model.setRaceFinished(true);
                            view.showWinner(button);
                        }
                    }

                    Thread.sleep(50); // Небольшая задержка для визуализации
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }

    public void stopRace() {
        // Принудительная остановка всех потоков
        raceThreads.forEach(Thread::interrupt);
    }
}
