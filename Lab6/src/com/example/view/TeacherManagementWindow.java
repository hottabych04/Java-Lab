package com.example.view;

import com.example.model.Subject;
import com.example.model.Teacher;
import com.example.util.DatabaseManager;
import com.example.util.NameVerifier;
import com.example.util.ValidationUtils;

import javax.swing.*;
import java.awt.*;

/**
 * Окно для управления преподавателями.
 * Предоставляет функции добавления, обновления, удаления и просмотра данных о преподавателях.
 */
public class TeacherManagementWindow extends JDialog {
    private DatabaseManager dbManager;
    private JTextField nameField;
    private JComboBox<Subject> subjectCombo;
    private JList<Teacher> teacherList;
    private DefaultListModel<Teacher> listModel;

    /**
     * Создает новое окно для управления преподавателями.
     *
     * @param dbManager объект {@link DatabaseManager} для взаимодействия с базой данных.
     * @param parent    родительское окно {@link JFrame}, из которого вызывается это окно.
     */
    public TeacherManagementWindow(DatabaseManager dbManager, JFrame parent) {
        super(parent, "Teacher Management", true);
        this.dbManager = dbManager;

        setSize(400, 500);
        setLocationRelativeTo(parent);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 5, 5));

        nameField = new JTextField(20);
        nameField.setInputVerifier(new NameVerifier());
        subjectCombo = new JComboBox<>(dbManager.getAllSubjects().toArray(new Subject[0]));

        formPanel.add(new JLabel("Имя:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Предмет:"));
        formPanel.add(subjectCombo);

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
        dbManager.getAllTeachers().forEach(listModel::addElement);
        teacherList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(teacherList);

        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);

        // Add action listeners
        addButton.addActionListener(e -> addTeacher());
        updateButton.addActionListener(e -> updateTeacher());
        deleteButton.addActionListener(e -> deleteTeacher());
        clearButton.addActionListener(e -> clearFields());

        teacherList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Teacher selected = teacherList.getSelectedValue();
                if (selected != null) {
                    populateFields(selected);
                }
            }
        });

        add(mainPanel);
        setVisible(true);
    }

    /**
     * Добавляет нового преподавателя в базу данных и список.
     */
    private void addTeacher() {
        if (validateFields()) {
            Teacher teacher = new Teacher(
                    nameField.getText(),
                    (Subject) subjectCombo.getSelectedItem()
            );

            dbManager.addTeacher(teacher);
            listModel.addElement(teacher);
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
            showError("Пожалуйста, введите правильное имя");
            return false;
        }

        if (subjectCombo.getSelectedItem() == null) {
            showError("Пожалуйста, выберите предмет");
            return false;
        }

        return true;
    }

    /**
     * Обновляет данные о выбранном преподавателе.
     */
    private void updateTeacher() {
        Teacher selected = teacherList.getSelectedValue();
        if (selected != null && validateFields()) {
            selected.setName(nameField.getText());
            selected.setSubject((Subject) subjectCombo.getSelectedItem());

            teacherList.repaint();
            dbManager.saveData();
        }
    }

    /**
     * Удаляет выбранного преподавателя из базы данных и списка.
     */
    private void deleteTeacher() {
        Teacher selected = teacherList.getSelectedValue();
        if (selected != null) {
            int teacherId = dbManager.getTeacherId(selected);
            dbManager.removeTeacher(teacherId);
            listModel.removeElement(selected);
            clearFields();
        }
    }

    /**
     * Очищает поля формы и снимает выделение со списка.
     */
    private void clearFields() {
        nameField.setText("");
        try {
            subjectCombo.setSelectedIndex(0);
        } catch (Exception e) {
            subjectCombo.setSelectedIndex(-1);
        }
        teacherList.clearSelection();
    }

    /**
     * Заполняет поля формы данными выбранного преподавателя.
     *
     * @param teacher объект {@link Teacher}, данные которого нужно отобразить.
     */
    private void populateFields(Teacher teacher) {
        nameField.setText(teacher.getName());
        subjectCombo.setSelectedItem(teacher.getSubject());
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