package com.example.view;

import com.example.model.Subject;
import com.example.util.DatabaseManager;
import com.example.util.NameVerifier;
import com.example.util.ValidationUtils;

import javax.swing.*;
import java.awt.*;

/**
 * Окно для управления предметами.
 * Предоставляет функции добавления, обновления, удаления и просмотра данных о предметах.
 */
public class SubjectManagementWindow extends JDialog {
    private DatabaseManager dbManager;
    private JTextField nameField;
    private JList<Subject> subjectList;
    private DefaultListModel<Subject> listModel;

    /**
     * Создает новое окно для управления предметами.
     *
     * @param dbManager объект {@link DatabaseManager} для взаимодействия с базой данных.
     * @param parent    родительское окно {@link JFrame}, из которого вызывается это окно.
     */
    public SubjectManagementWindow(DatabaseManager dbManager, JFrame parent) {
        super(parent, "Subject Management", true);
        this.dbManager = dbManager;

        setSize(350, 400);
        setLocationRelativeTo(parent);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(1, 2, 5, 5));

        nameField = new JTextField(20);
        nameField.setInputVerifier(new NameVerifier());

        formPanel.add(new JLabel("Название:"));
        formPanel.add(nameField);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton addButton = new JButton("Добавить");
        JButton updateButton = new JButton("Обновить");
        JButton deleteButton = new JButton("Удалить");
        JButton clearButton = new JButton("Очистить");

        buttonsPanel.add(addButton);
        buttonsPanel.add(updateButton);
        buttonsPanel.add(deleteButton);
        buttonsPanel.add(clearButton);

        // List Panel
        listModel = new DefaultListModel<>();
        dbManager.getAllSubjects().forEach(listModel::addElement);
        subjectList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(subjectList);

        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);

        // Add action listeners
        addButton.addActionListener(e -> addSubject());
        updateButton.addActionListener(e -> updateSubject());
        deleteButton.addActionListener(e -> deleteSubject());
        clearButton.addActionListener(e -> clearFields());

        subjectList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Subject selected = subjectList.getSelectedValue();
                if (selected != null) {
                    populateFields(selected);
                }
            }
        });

        add(mainPanel);
        setVisible(true);
    }

    /**
     * Добавляет новый предмет в базу данных и список.
     */
    private void addSubject() {
        if (validateFields()) {
            Subject subject = new Subject(nameField.getText());
            dbManager.addSubject(subject);
            listModel.addElement(subject);
            clearFields();
        }
    }

    /**
     * Проверяет корректность введенных данных.
     *
     * @return {@code true}, если данные валидны, иначе {@code false}.
     */
    private boolean validateFields() {
        if (!ValidationUtils.isValidName(nameField.getText())) {
            showError("Пожалуйста, введите корректное название предмета");
            return false;
        }
        return true;
    }

    /**
     * Обновляет данные о выбранном предмете.
     */
    private void updateSubject() {
        Subject selected = subjectList.getSelectedValue();
        if (selected != null && validateFields()) {
            selected.setName(nameField.getText());

            subjectList.repaint();
            dbManager.saveData();
        }
    }

    /**
     * Удаляет выбранный предмет из базы данных и списка.
     */
    private void deleteSubject() {
        Subject selected = subjectList.getSelectedValue();
        if (selected != null) {
            int subjectId = dbManager.getSubjectId(selected);
            dbManager.removeSubject(subjectId);
            listModel.removeElement(selected);
            clearFields();
        }
    }

    /**
     * Очищает поля формы и снимает выделение со списка.
     */
    private void clearFields() {
        nameField.setText("");
        subjectList.clearSelection();
    }

    /**
     * Заполняет поля формы данными выбранного предмета.
     *
     * @param subject объект {@link Subject}, данные которого нужно отобразить.
     */
    private void populateFields(Subject subject) {
        nameField.setText(subject.getName());
    }

    /**
     * Отображает сообщение об ошибке.
     *
     * @param message текст сообщения об ошибке.
     */
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Ошибка", JOptionPane.ERROR_MESSAGE);
    }
}