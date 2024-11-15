package com.example.view;

import com.example.model.Parent;
import com.example.model.Student;
import com.example.util.DatabaseManager;

import javax.swing.*;
import java.awt.*;

/**
 * Окно для управления студентами в системе.
 * Предоставляет функции добавления, обновления, удаления и просмотра данных о студентах.
 */
public class StudentManagementWindow extends JDialog {
    private DatabaseManager dbManager;
    private JTextField nameField;
    private JTextField ageField;
    private JComboBox<String> genderCombo;
    private JComboBox<Parent> parentCombo;
    private JList<Student> studentList;
    private DefaultListModel<Student> listModel;

    /**
     * Создает новое окно для управления студентами.
     *
     * @param dbManager объект {@link DatabaseManager} для взаимодействия с базой данных.
     * @param parent    родительское окно {@link JFrame}, из которого вызывается это окно.
     */
    public StudentManagementWindow(DatabaseManager dbManager, JFrame parent) {
        super(parent, "Student Management", true);
        this.dbManager = dbManager;

        setSize(500, 600);
        setLocationRelativeTo(parent);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(7, 2, 5, 5));

        nameField = new JTextField(20);
        ageField = new JTextField(20);
        genderCombo = new JComboBox<>(new String[]{"М", "Ж"});
        parentCombo = new JComboBox<>(dbManager.getAllParents().toArray(new Parent[0]));

        formPanel.add(new JLabel("Имя:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Возраст:"));
        formPanel.add(ageField);
        formPanel.add(new JLabel("Пол:"));
        formPanel.add(genderCombo);
        formPanel.add(new JLabel("Родитель:"));
        formPanel.add(parentCombo);

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
        dbManager.getAllStudents().forEach(listModel::addElement);
        studentList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(studentList);

        // Add components to main panel
        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);

        // Add action listeners
        addButton.addActionListener(e -> addStudent());
        updateButton.addActionListener(e -> updateStudent());
        deleteButton.addActionListener(e -> deleteStudent());
        clearButton.addActionListener(e -> clearFields());

        studentList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Student selected = studentList.getSelectedValue();
                if (selected != null) {
                    populateFields(selected);
                }
            }
        });

        add(mainPanel);
        setVisible(true);
    }

    /**
     * Добавляет нового студента в базу данных и список.
     */
    private void addStudent() {
        if (validateFields()) {
            try {
                Student student = new Student(
                        nameField.getText(),
                        Integer.parseInt(ageField.getText()),
                        genderCombo.getSelectedItem().toString(),
                        (Parent) parentCombo.getSelectedItem()
                );

                dbManager.addStudent(student);
                listModel.addElement(student);
                clearFields();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Пожалуйста, проверьте все поля с числами на корректный ввод",
                        "Некорректный ввод",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Проверяет корректность введенных данных.
     *
     * @return {@code true}, если все поля валидны, иначе {@code false}.
     */
    private boolean validateFields() {
        if (nameField.getText().trim().isEmpty()) {
            showError("Имя не может быть пустым");
            return false;
        }

        try {
            int age = Integer.parseInt(ageField.getText());
            if (age < 6 || age > 20) {
                showError("Возраст должен быть от 6 до 20");
                return false;
            }
        } catch (NumberFormatException e) {
            showError("Возраст должен быть валидным числом");
            return false;
        }

        if (parentCombo.getSelectedItem() == null) {
            showError("родитель должен быть выбран");
            return false;
        }

        return true;
    }

    /**
     * Отображает сообщение об ошибке.
     *
     * @param message текст сообщения об ошибке.
     */
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Ошибка", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Обновляет данные о выбранном студенте.
     */
    private void updateStudent() {
        Student selected = studentList.getSelectedValue();
        if (selected != null && validateFields()) {
            selected.setName(nameField.getText());
            selected.setAge(Integer.parseInt(ageField.getText()));
            selected.setGender(genderCombo.getSelectedItem().toString());
            selected.setParent((Parent) parentCombo.getSelectedItem());

            studentList.repaint();
            dbManager.saveData();
        }
    }

    /**
     * Удаляет выбранного студента из базы данных и списка.
     */
    private void deleteStudent() {
        Student selected = studentList.getSelectedValue();
        if (selected != null) {
            int studentId = dbManager.getStudentId(selected);
            dbManager.removeStudent(studentId);
            listModel.removeElement(selected);
            clearFields();
        }
    }

    /**
     * Очищает все поля формы и снимает выделение со списка.
     */
    private void clearFields() {
        nameField.setText("");
        ageField.setText("");
        genderCombo.setSelectedIndex(0);
        try {
            parentCombo.setSelectedIndex(0);
        } catch (Exception e) {
            parentCombo.setSelectedIndex(-1);
        }
        studentList.clearSelection();
    }

    /**
     * Заполняет поля формы данными выбранного студента.
     *
     * @param student объект {@link Student}, данные которого нужно отобразить.
     */
    private void populateFields(Student student) {
        nameField.setText(student.getName());
        ageField.setText(String.valueOf(student.getAge()));
        genderCombo.setSelectedItem(student.getGender());
        parentCombo.setSelectedItem(student.getParent());
    }
}
