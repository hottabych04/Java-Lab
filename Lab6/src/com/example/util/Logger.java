package com.example.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Класс для записи сообщений в лог-файл. Поддерживает различные уровни логирования: информационные сообщения,
 * ошибки и отладочные сообщения.
 * <p>
 * Логирование выполняется в файл, указанный в параметрах конструктора. Сообщения записываются с отметкой времени.
 * Уровень логирования управляется через объект {@link PropertiesReader}, например, настройкой "debug".
 * </p>
 */
public class Logger {
    private String filePath;
    private DateTimeFormatter dateTimeFormatter;
    private PropertiesReader properties;

    /**
     * Конструктор класса. Инициализирует объект логгера с заданным файлом для записи и параметрами конфигурации.
     *
     * @param filePath  путь к файлу для записи логов.
     * @param properties объект {@link PropertiesReader}, содержащий настройки логгера (например, включение отладки).
     */
    public Logger(String filePath, PropertiesReader properties) {
        this.filePath = filePath;
        this.properties = properties;
        this.dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * Записывает общее сообщение в лог-файл.
     * <p>
     * Сообщение записывается только в том случае, если в настройках {@link PropertiesReader}
     * свойство "debug" установлено в значение "true".
     * </p>
     *
     * @param message текст сообщения для записи.
     */
    public void log(String message) {
        if (!Objects.equals(properties.getProperty("debug"), "true")){
            return;
        }
        try (FileWriter fileWriter = new FileWriter(filePath, true);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {
            // Получаем текущее время в нужном формате
            String timestamp = LocalDateTime.now().format(dateTimeFormatter);
            // Записываем сообщение в формате "время - сообщение"
            printWriter.println(timestamp + " - " + message);
        } catch (IOException e) {
            System.err.println("Ошибка при записи в лог-файл: " + e.getMessage());
        }
    }

    /**
     * Записывает сообщение об ошибке в лог-файл.
     * <p>
     * Сообщение предваряется префиксом "ERROR: ".
     * </p>
     *
     * @param message текст сообщения об ошибке.
     */
    public void logError(String message) {
        log("ERROR: " + message);
    }

    /**
     * Записывает информационное сообщение в лог-файл.
     * <p>
     * Сообщение предваряется префиксом "INFO: ".
     * </p>
     *
     * @param message текст информационного сообщения.
     */
    public void logInfo(String message) {
        log("INFO: " + message);
    }

    /**
     * Записывает отладочное сообщение в лог-файл.
     * <p>
     * Сообщение предваряется префиксом "DEBUG: ".
     * </p>
     *
     * @param message текст отладочного сообщения.
     */
    public void logDebug(String message) {
        log("DEBUG: " + message);
    }
}

