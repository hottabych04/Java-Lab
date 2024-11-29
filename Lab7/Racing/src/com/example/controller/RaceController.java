package com.example.controller;

import com.example.model.RaceButton;
import com.example.model.RaceModel;
import com.example.view.RaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * Контроллер гонки, управляющий логикой многопоточного соревнования.
 * Координирует взаимодействие между моделью и представлением.
 */
public class RaceController {
    /** Модель гонки */
    private RaceModel model;
    /** Представление гонки */
    private RaceView view;
    /** Список потоков для каждой кнопки */
    private List<Thread> raceThreads;
    /** Синхронизатор для одновременного старта */
    private CountDownLatch startLatch;

    /**
     * Создает контроллер гонки.
     *
     * @param model модель гонки
     * @param view представление гонки
     */
    public RaceController(RaceModel model, RaceView view) {
        this.model = model;
        this.view = view;
        raceThreads = new ArrayList<>();
    }

    /**
     * Запускает новую гонку, создавая потоки для каждой кнопки.
     * Обеспечивает синхронный старт всех участников.
     */
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

    /**
     * Создает поток для отдельной кнопки-гонщика.
     * Реализует логику движения с случайной скоростью.
     *
     * @param button кнопка-участник гонки
     * @return поток для данной кнопки
     */
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

    /**
     * Останавливает все потоки гонки принудительным прерыванием.
     */
    public void stopRace() {
        // Принудительная остановка всех потоков
        raceThreads.forEach(Thread::interrupt);
    }
}
