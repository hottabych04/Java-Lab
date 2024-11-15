package com.example.util;

import com.example.model.Parent;
import com.example.model.Student;
import com.example.model.Subject;
import com.example.model.Teacher;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс для управления базой данных школьной системы, включающей студентов, учителей, родителей и предметы.
 * Позволяет выполнять операции CRUD (создание, чтение, обновление, удаление) и сохранять данные в файл.
 * <p>
 * Данные хранятся в текстовом файле в формате, определенном для каждого типа сущности:
 * <ul>
 *     <li>STUDENT: {@code STUDENT|ID|name|age|gender|parentId|averageGrade|bonus}</li>
 *     <li>TEACHER: {@code TEACHER|ID|name|subjectId}</li>
 *     <li>PARENT: {@code PARENT|ID|name|mood}</li>
 *     <li>SUBJECT: {@code SUBJECT|ID|name}</li>
 * </ul>
 * </p>
 */
public class DatabaseManager {
    private Logger logger;

    private static final String DB_FILE = "school_database.txt";
    private List<Student> students;
    private List<Teacher> teachers;
    private List<Parent> parents;
    private Map<Integer, Subject> subjects;
    private int nextId = 1;

    /**
     * Конструктор класса. Выполняет инициализацию списков данных и загрузку существующих данных из файла.
     *
     * @param logger логгер для записи информации о действиях и ошибках.
     */
    public DatabaseManager(Logger logger) {
        this.logger = logger;
        logger.logDebug("Инициализация базы данных");
        students = new ArrayList<>();
        teachers = new ArrayList<>();
        parents = new ArrayList<>();
        subjects = new HashMap<>();
        loadData();
    }

    // Методы для работы с предметами (Subject)
    /**
     * Добавляет новый предмет в базу данных.
     *
     * @param subject объект предмета для добавления.
     */
    public void addSubject(Subject subject) {
        subjects.put(nextId++, subject);
        logger.logInfo("Добавлен новый предмет: " + subject);
        saveData();
    }

    /**
     * Удаляет предмет по ID.
     *
     * @param id идентификатор предмета.
     */
    public void removeSubject(int id) {
        subjects.remove(id);
        logger.logInfo("Удален предмет с айди: " + id);
        saveData();
    }

    /**
     * Обновляет данные предмета по ID.
     *
     * @param id      идентификатор предмета.
     * @param subject обновленные данные предмета.
     */
    public void updateSubject(int id, Subject subject) {
        if (subjects.containsKey(id)) {
            subjects.put(id, subject);
            logger.logInfo("Обновлен предмет: " + subject);
            saveData();
        }
    }

    /**
     * Возвращает список всех предметов.
     *
     * @return список объектов {@link Subject}.
     */
    public List<Subject> getAllSubjects() {
        logger.logInfo("Произведено получение всех предметов");
        return new ArrayList<>(subjects.values());
    }

    /**
     * Возвращает предмет по его ID.
     *
     * @param id идентификатор предмета.
     * @return объект {@link Subject} или {@code null}, если предмет не найден.
     */
    public Subject getSubjectById(int id) {
        logger.logInfo("Произведено получение предмета по айди = " + id);
        return subjects.get(id);
    }

    // Методы для работы с учителями (Teacher)
    /**
     * Добавляет нового учителя в базу данных.
     *
     * @param teacher объект учителя для добавления.
     */
    public void addTeacher(Teacher teacher) {
        teachers.add(teacher);
        logger.logInfo("Добавлен новый учитель: " + teacher);
        saveData();
    }

    /**
     * Удаляет учителя по ID.
     *
     * @param id идентификатор учителя.
     */
    public void removeTeacher(int id) {
        if (id > 0 && id <= teachers.size()) {
            teachers.remove(id - 1);
            logger.logInfo("Удален учитель с айди: " + id);
            saveData();
        }
    }

    /**
     * Обновляет данные учителя по ID.
     *
     * @param id      идентификатор учителя.
     * @param teacher обновленные данные учителя.
     */
    public void updateTeacher(int id, Teacher teacher) {
        if (id > 0 && id <= teachers.size()) {
            teachers.set(id - 1, teacher);
            logger.logInfo("Обновлен учитель: " + teacher);
            saveData();
        }
    }

    /**
     * Возвращает список всех учителей.
     *
     * @return список объектов {@link Teacher}.
     */
    public List<Teacher> getAllTeachers() {
        logger.logInfo("Произведено получение всех учителей");
        return new ArrayList<>(teachers);
    }

    /**
     * Возвращает учителя по его ID.
     *
     * @param id идентификатор учителя.
     * @return объект {@link Teacher} или {@code null}, если учитель не найден.
     */
    public Teacher getTeacherById(int id) {
        logger.logInfo("Произведено получение учителя по айди = " + id);
        return (id > 0 && id <= teachers.size()) ? teachers.get(id - 1) : null;
    }

    // Методы для работы с родителями (Parent)
    /**
     * Добавляет нового родителя в базу данных.
     *
     * @param parent объект родителя для добавления.
     */
    public void addParent(Parent parent) {
        parents.add(parent);
        logger.logInfo("Добавлен новый родитель: " + parent);
        saveData();
    }

    /**
     * Удаляет родителя по ID.
     *
     * @param id идентификатор родителя.
     */
    public void removeParent(int id) {
        if (id > 0 && id <= parents.size()) {
            parents.remove(id - 1);
            logger.logInfo("Удален родитель с айди: " + id);
            saveData();
        }
    }

    /**
     * Обновляет данные родителя по ID.
     *
     * @param id     идентификатор родителя.
     * @param parent обновленные данные родителя.
     */
    public void updateParent(int id, Parent parent) {
        if (id > 0 && id <= parents.size()) {
            parents.set(id - 1, parent);
            logger.logInfo("Обновлен родитель: " + parent);
            saveData();
        }
    }

    /**
     * Возвращает список всех родителей.
     *
     * @return список объектов {@link Parent}.
     */
    public List<Parent> getAllParents() {
        logger.logInfo("Произведено получение всех родителей");
        return new ArrayList<>(parents);
    }

    /**
     * Возвращает родителя по его ID.
     *
     * @param id идентификатор родителя.
     * @return объект {@link Parent} или {@code null}, если родитель не найден.
     */
    public Parent getParentById(int id) {
        logger.logInfo("Произведено получение родителя по айди = " + id);
        return (id > 0 && id <= parents.size()) ? parents.get(id - 1) : null;
    }

    // Методы для работы со студентами (Student)
    /**
     * Добавляет нового студента в базу данных.
     *
     * @param student объект студента для добавления.
     */
    public void addStudent(Student student) {
        students.add(student);
        logger.logInfo("Добавлен новый студент: " + student);
        saveData();
    }

    /**
     * Удаляет студента по ID.
     *
     * @param id идентификатор студента.
     */
    public void removeStudent(int id) {
        if (id > 0 && id <= students.size()) {
            students.remove(id - 1);
            logger.logInfo("Удален студент с айди: " + id);
            saveData();
        }
    }

    /**
     * Обновляет данные студента по ID.
     *
     * @param id      идентификатор студента.
     * @param student обновленные данные студента.
     */
    public void updateStudent(int id, Student student) {
        if (id > 0 && id <= students.size()) {
            students.set(id - 1, student);
            logger.logInfo("Обновлен студент: " + student);
            saveData();
        }
    }

    /**
     * Возвращает список всех студентов.
     *
     * @return список объектов {@link Student}.
     */
    public List<Student> getAllStudents() {
        logger.logInfo("Произведено получение всех студентов");
        return new ArrayList<>(students);
    }

    /**
     * Возвращает студента по его ID.
     *
     * @param id идентификатор студента.
     * @return объект {@link Student} или {@code null}, если студент не найден.
     */
    public Student getStudentById(int id) {
        logger.logInfo("Произведено получение студента по айди = " + id);
        return (id > 0 && id <= students.size()) ? students.get(id - 1) : null;
    }

    // Внутренние методы работы с данными
    /**
     * Загружает данные из файла базы данных.
     */
    private void loadData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(DB_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length > 0) {
                    switch (parts[0]) {
                        case "STUDENT":
                            loadStudent(parts);
                            break;
                        case "TEACHER":
                            loadTeacher(parts);
                            break;
                        case "PARENT":
                            loadParent(parts);
                            break;
                        case "SUBJECT":
                            loadSubject(parts);
                            break;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("База данных не найдена. Создаем новую.");
            logger.logInfo("База данных не найдена. Создаем новую.");
        }
    }

    private void loadStudent(String[] parts) {
        // Format: STUDENT|ID|name|age|gender|parentId|averageGrade|bonus
        int id = Integer.parseInt(parts[1]);
        String name = parts[2];
        int age = Integer.parseInt(parts[3]);
        String gender = parts[4];
        int parentId = Integer.parseInt(parts[5]);
        double averageGrade = Double.parseDouble(parts[6].replace(",", "."));
        int bonus = Integer.parseInt(parts[7]);

        Parent parent = findParentById(parentId);
        if (parent != null) {
            Student student = new Student(name, age, gender, parent);
            student.setAverageGrade(averageGrade);
            student.setBonus(bonus);
            students.add(student);
            nextId = Math.max(nextId, id + 1);
        }
    }

    private void loadTeacher(String[] parts) {
        // Format: TEACHER|ID|name|subjectId
        int id = Integer.parseInt(parts[1]);
        String name = parts[2];
        int subjectId = Integer.parseInt(parts[3]);

        Subject subject = subjects.get(subjectId);
        if (subject != null) {
            teachers.add(new Teacher(name, subject));
        }
        nextId = Math.max(nextId, id + 1);
    }

    private void loadParent(String[] parts) {
        // Format: PARENT|ID|name|mood
        int id = Integer.parseInt(parts[1]);
        String name = parts[2];
        String mood = parts[3];

        Parent parent = new Parent(name);
        parent.setMood(mood);
        parents.add(parent);
        nextId = Math.max(nextId, id + 1);
    }

    private void loadSubject(String[] parts) {
        // Format: SUBJECT|ID|name
        int id = Integer.parseInt(parts[1]);
        String name = parts[2];

        subjects.put(id, new Subject(name));
        nextId = Math.max(nextId, id + 1);
    }

    /**
     * Сохраняет текущие данные в файл базы данных.
     */
    public void saveData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DB_FILE))) {
            // Save subjects
            for (Map.Entry<Integer, Subject> entry : subjects.entrySet()) {
                writer.write(String.format("SUBJECT|%d|%s%n",
                        entry.getKey(), entry.getValue().getName()));
            }

            // Save parents
            for (int i = 0; i < parents.size(); i++) {
                Parent parent = parents.get(i);
                writer.write(String.format("PARENT|%d|%s|%s%n",
                        i + 1, parent.getName(), parent.getMood()));
            }

            // Save teachers
            for (int i = 0; i < teachers.size(); i++) {
                Teacher teacher = teachers.get(i);
                writer.write(String.format("TEACHER|%d|%s|%d%n",
                        i + 1, teacher.getName(),
                        getSubjectId(teacher.getSubject())));
            }

            // Save students
            for (int i = 0; i < students.size(); i++) {
                Student student = students.get(i);
                DecimalFormat df = new DecimalFormat("#.##");
                writer.write(String.format("STUDENT|%d|%s|%d|%s|%d|%.2f|%d%n",
                        i + 1, student.getName(), student.getAge(),
                        student.getGender(), getParentId(student.getParent()),
                        student.getAverageGrade(), student.getBonus()));
            }
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении данных: " + e.getMessage());
            logger.logError("Ошибка при сохранении данных: " + e.getMessage());
        }
    }

    // Методы поиска и вспомогательные
    /**
     * Находит ID студента.
     *
     * @param student объект студента.
     * @return ID студента или -1, если студент не найден.
     */
    public int getStudentId(Student student) {
        return students.indexOf(student) + 1;
    }

    /**
     * Находит ID учителя.
     *
     * @param teacher объект учителя.
     * @return ID учителя или -1, если учитель не найден.
     */
    public int getTeacherId(Teacher teacher) {
        return teachers.indexOf(teacher) + 1;
    }

    /**
     * Находит ID родителя.
     *
     * @param parent объект родителя.
     * @return ID родителя или -1, если родитель не найден.
     */
    public int getParentId(Parent parent) {
        return parents.indexOf(parent) + 1;
    }

    /**
     * Находит ID предмета.
     *
     * @param subject объект предмета.
     * @return ID предмета или -1, если предмет не найден.
     */
    public int getSubjectId(Subject subject) {
        for (Map.Entry<Integer, Subject> entry : subjects.entrySet()) {
            if (entry.getValue().equals(subject)) {
                return entry.getKey();
            }
        }
        return -1;
    }

    private Parent findParentById(int id) {
        if (id > 0 && id <= parents.size()) {
            return parents.get(id - 1);
        }
        return null;
    }
}
