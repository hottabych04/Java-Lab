package com.example;

import com.example.test.TestDb;
import com.example.util.DatabaseManager;
import com.example.util.Logger;
import com.example.util.PropertiesReader;
import com.example.view.View;

/**
 * Класс {@code Main} является точкой входа в приложение.
 * Он создает необходимые зависимости и демонстрирует работу программы.
 */
public class Main {
    public static void main(String[] args) {
        PropertiesReader propertiesReader = new PropertiesReader("settings.properties");
        Logger logger = new Logger("app.log", propertiesReader);
        DatabaseManager dbManager = new DatabaseManager(logger);
        TestDb testDb = new TestDb(logger, dbManager);
        View view = new View(dbManager, propertiesReader, testDb, logger);
        view.showStartMessage();
        view.showMainMenu();
    }
}

