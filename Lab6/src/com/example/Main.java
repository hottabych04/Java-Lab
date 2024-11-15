package com.example;

import com.example.test.TestDb;
import com.example.util.DatabaseManager;
import com.example.util.Logger;
import com.example.util.PropertiesReader;
import com.example.view.MainWindow;
import com.example.view.View;

import javax.swing.*;

/**
 * Класс {@code Main} является точкой входа в приложение.
 */
public class Main {
    public static void main(String[] args) {
        PropertiesReader propertiesReader = new PropertiesReader("settings.properties");
        Logger logger = new Logger("app.log", propertiesReader);
        DatabaseManager dbManager = new DatabaseManager(logger);
        TestDb testDb = new TestDb(logger, dbManager);
        SwingUtilities.invokeLater(() -> {
            MainWindow mainWindow = new MainWindow(dbManager);
            mainWindow.setVisible(true);
        });
    }
}

