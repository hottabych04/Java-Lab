package com.example;

/**
 * Класс com.example.Main запускает игру.
 */
public class Main {
    public static void main(String[] args) {
        GameModel model = new GameModel();
        GameView view = new GameView();
        GameController controller = new GameController(model, view);

        controller.startGame();
    }
}
