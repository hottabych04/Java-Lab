package com.example.view;

import com.example.model.Parent;
import com.example.util.DatabaseManager;
import com.example.util.NameVerifier;
import com.example.util.ValidationUtils;

import javax.swing.*;
import java.awt.*;

/**
 * Окно для управления родителями в системе.
 * Предоставляет функции добавления, обновления, удаления и просмотра данных о родителях.
 */
public class ParentManagementWindow extends JDialog {
    private DatabaseManager dbManager;
    private JTextField nameField;
    private JComboBox<String> moodCombo;
    private JList<Parent> parentList;
    private DefaultListModel<Parent> listModel;

    /**
     * Создает новое окно для управления родителями.
     *
     * @param dbManager объект {@link DatabaseManager} для взаимодействия с базой данных.
     * @param parent    родительское окно {@link JFrame}, из которого вызывается это окно.
     */
    public ParentManagementWindow(DatabaseManager dbManager, JFrame parent) {
        super(parent, "Parent Management", true);
        this.dbManager = dbManager;

        setSize(400, 500);
        setLocationRelativeTo(parent);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 5, 5));

        nameField = new JTextField(20);
        nameField.setInputVerifier(new NameVerifier());
        moodCombo = new JComboBox<>(new String[]{"Радостный", "Удовлетворенный", "Хмурый"});

        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
//        formPanel.add(new JLabel("Mood:"));
//        formPanel.add(moodCombo);

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
        dbManager.getAllParents().forEach(listModel::addElement);
        parentList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(parentList);

        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);

        // Add action listeners
        addButton.addActionListener(e -> addParent());
        updateButton.addActionListener(e -> updateParent());
        deleteButton.addActionListener(e -> deleteParent());
        clearButton.addActionListener(e -> clearFields());

        parentList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Parent selected = parentList.getSelectedValue();
                if (selected != null) {
                    populateFields(selected);
                }
            }
        });

        add(mainPanel);
        setVisible(true);
    }

    /**
     * Добавляет нового родителя в базу данных и список.
     */
    private void addParent() {
        if (validateFields()) {
            Parent parent = new Parent(nameField.getText());

            dbManager.addParent(parent);
            listModel.addElement(parent);
            clearFields();
        }
    }

    /**
     * Проверяет корректность введенных данных.
     *
     * @return {@code true}, если все поля валидны, иначе {@code false}.
     */
    private boolean validateFields() {
        if (!ValidationUtils.isValidName(nameField.getText())) {
            showError("Введите правильное имя");
            return false;
        }
        return true;
    }

    /**
     * Обновляет данные о выбранном родителе.
     */
    private void updateParent() {
        Parent selected = parentList.getSelectedValue();
        if (selected != null && validateFields()) {
            selected.setName(nameField.getText());
            selected.setMood(moodCombo.getSelectedItem().toString());

            parentList.repaint();
            dbManager.saveData();
        }
    }

    /**
     * Удаляет выбранного родителя из базы данных и списка.
     */
    private void deleteParent() {
        Parent selected = parentList.getSelectedValue();
        if (selected != null) {
            int parentId = dbManager.getParentId(selected);
            // Check if parent has associated students
            if (dbManager.getParentId(selected) == -1) {
                showError("Нельзя удалить родителя который ассоциирован со студентами");
                return;
            }

            dbManager.removeParent(parentId);
            listModel.removeElement(selected);
            clearFields();
        }
    }

    /**
     * Очищает все поля формы и снимает выделение со списка.
     */
    private void clearFields() {
        nameField.setText("");
        moodCombo.setSelectedIndex(0);
        parentList.clearSelection();
    }

    /**
     * Заполняет поля формы данными выбранного родителя.
     *
     * @param parent объект {@link Parent}, данные которого нужно отобразить.
     */
    private void populateFields(Parent parent) {
        nameField.setText(parent.getName());
        moodCombo.setSelectedItem(parent.getMood());
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
