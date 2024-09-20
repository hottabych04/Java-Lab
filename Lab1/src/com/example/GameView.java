package com.example;

import java.util.Scanner;

/**
 * Класс GameView отвечает за взаимодействие с пользователем.
 */
public class GameView {
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Выводит сообщение пользователю.
     *
     * @param message сообщение для вывода.
     */
    public void displayMessage(String message) {
        System.out.println(message);
    }

    /**
     * Получает ответ пользователя о том, угадала программа число или нет.
     *
     * @return строка с ответом пользователя ('y' для да или 'n' для нет).
     */
    public String getUserResponse() {
        return scanner.nextLine();
    }

    /**
     * Запрашивает у пользователя подтверждение, является ли число больше или меньше.
     *
     * @return true, если пользователь сказал, что число больше, false, если меньше.
     */
    public boolean askIsNumberHigher() {
        displayMessage("Ваше число больше (введите 'y' для Да или 'n' для Нет)?");
        while (true) {
            String input = scanner.nextLine();
            if ("y".equalsIgnoreCase(input)) {
                return true;
            } else if ("n".equalsIgnoreCase(input)) {
                return false;
            } else {
                displayMessage("Ошибка: введите 'y' или 'n'.");
            }
        }
    }
}

