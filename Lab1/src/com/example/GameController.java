package com.example;

/**
 * Класс GameController управляет взаимодействием между моделью (GameModel) и представлением (GameView),
 * обрабатывая исключения и обеспечивая корректное выполнение игры.
 */
public class GameController {
    private final GameModel model;
    private final GameView view;

    /**
     * Конструктор для создания контроллера игры.
     *
     * @param model модель игры.
     * @param view представление игры.
     */
    public GameController(GameModel model, GameView view) {
        this.model = model;
        this.view = view;
    }

    /**
     * Основной цикл игры, управляющий процессом угадывания.
     */
    public void startGame() {
        view.displayMessage("Загадайте число от 1 до 100, и программа попробует его угадать.");

        while (true) {
            int guess = model.makeGuess();
            view.displayMessage("Ваше число " + guess + "? (введите 'y' для Да или 'n' для Нет)");

            // Проверяем, угадано ли число
            String response = view.getUserResponse();
            if ("y".equalsIgnoreCase(response)) {
                view.displayMessage("Программа угадала ваше число: " + guess + "!");
                break;
            } else if (!"n".equalsIgnoreCase(response)) {
                view.displayMessage("Ошибка: введите 'y' или 'n'.");
                continue;
            }

            // Если число не угадано, спрашиваем больше или меньше
            boolean isHigher = view.askIsNumberHigher();
            model.updateBounds(isHigher);

            if (model.isCheating()) {
                view.displayMessage("Обман обнаружен! Попробуйте снова.");
                break;
            }
        }
    }
}
