package com.example.logger;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;

/**
 * Класс для записи сообщений в лог-файл.
 * <p>
 * Логирование выполняется в файл, указанный в параметрах конструктора. Сообщения записываются с отметкой времени.
 * </p>
 */
public class Logger {
    private String filePath;

    /**
     * Конструктор класса. Инициализирует объект логгера с заданным файлом для записи.
     *
     * @param filePath  путь к файлу для записи логов.
     */
    public Logger(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Записывает общее сообщение в лог-файл.
     *
     * @param message текст сообщения для записи.
     */
    public void log(String message) {
        try (FileWriter fileWriter = new FileWriter(filePath, true);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {
            printWriter.println(message);
        } catch (IOException e) {
            System.err.println("Ошибка при записи в лог-файл: " + e.getMessage());
        }
    }
}
