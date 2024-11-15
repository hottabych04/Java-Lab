package com.example.view;
import com.example.util.DatabaseManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Главное окно приложения "School Management System".
 * Предоставляет интерфейс для перехода к различным разделам управления.
 */
public class MainWindow extends JFrame {
    private DatabaseManager dbManager;

    /**
     * Создает главное окно приложения.
     *
     * @param dbManager объект {@link DatabaseManager} для взаимодействия с базой данных.
     */
    public MainWindow(DatabaseManager dbManager) {
        this.dbManager = dbManager;
        setTitle("School Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton studentsBtn = new JButton("Students");
        JButton teachersBtn = new JButton("Teachers");
        JButton parentsBtn = new JButton("Parents");
        JButton subjectsBtn = new JButton("Subjects");

        studentsBtn.addActionListener(e -> new StudentManagementWindow(dbManager, this));
        teachersBtn.addActionListener(e -> new TeacherManagementWindow(dbManager, this));
        parentsBtn.addActionListener(e -> new ParentManagementWindow(dbManager, this));
        subjectsBtn.addActionListener(e -> new SubjectManagementWindow(dbManager, this));

        mainPanel.add(studentsBtn);
        mainPanel.add(teachersBtn);
        mainPanel.add(parentsBtn);
        mainPanel.add(subjectsBtn);

        add(mainPanel);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dbManager.saveData();
                System.exit(0);
            }
        });
    }
}
