package com.example.test;

import com.example.model.Parent;
import com.example.model.Student;
import com.example.model.Subject;
import com.example.model.Teacher;
import com.example.util.DatabaseManager;
import com.example.util.Logger;

public class TestDb {
    private DatabaseManager dbManager;
    private Logger logger;

    public TestDb(Logger logger, DatabaseManager dbManager){
        this.dbManager = dbManager;
        logger.logDebug("Инициализация класса для тестов");
        this.logger = logger;
    }

    public void startTests() {
        logger.logInfo("Запуск автотестов");

        this.testAddSubject();
        this.testUpdateSubject();
        this.testRemoveSubject();

        this.testAddTeacher();
        this.testUpdateTeacher();
        this.testRemoveTeacher();

        this.testAddParent();
        this.testUpdateParent();
        this.testRemoveParent();

        this.testAddStudent();
        this.testUpdateStudent();
        this.testRemoveStudent();

        logger.logInfo("Автотесты завершены");
    }

    public void testAddSubject() {
        Subject subject = new Subject("Математика");
        dbManager.addSubject(subject);

        int subjectId = dbManager.getSubjectId(subject);

        if (dbManager.getAllSubjects().contains(subject)) {
            System.out.println("testAddSubject PASSED");
            logger.logInfo("testAddSubject PASSED");
            dbManager.removeSubject(subjectId);
        } else {
            System.out.println("testAddSubject FAILED");
            logger.logInfo("testAddSubject FAILED");
        }
    }

    public void testRemoveSubject() {
        Subject subject = new Subject("Физика");
        dbManager.addSubject(subject);
        int subjectId = dbManager.getSubjectId(subject);

        dbManager.removeSubject(subjectId);

        if (dbManager.getSubjectById(subjectId) == null) {
            System.out.println("testRemoveSubject PASSED");
            logger.logInfo("testRemoveSubject PASSED");
        } else {
            System.out.println("testRemoveSubject FAILED");
            logger.logInfo("testRemoveSubject FAILED");
        }
    }

    public void testUpdateSubject() {
        Subject subject = new Subject("Биология");
        dbManager.addSubject(subject);
        int subjectId = dbManager.getSubjectId(subject);

        Subject updatedSubject = new Subject("Углубленная биология");
        dbManager.updateSubject(subjectId, updatedSubject);

        if ("Углубленная биология".equals(dbManager.getSubjectById(subjectId).getName())) {
            System.out.println("testUpdateSubject PASSED");
            logger.logInfo("testUpdateSubject PASSED");
            dbManager.removeSubject(subjectId);
        } else {
            System.out.println("testUpdateSubject FAILED");
            logger.logInfo("testUpdateSubject FAILED");
        }
    }

    public void testAddTeacher() {
        Subject subject = new Subject("История");
        dbManager.addSubject(subject);
        Teacher teacher = new Teacher("Ярослав Мудрый", subject);

        dbManager.addTeacher(teacher);

        int subjectId = dbManager.getSubjectId(subject);
        int teacherId = dbManager.getTeacherId(teacher);

        if (dbManager.getAllTeachers().contains(teacher)) {
            System.out.println("testAddTeacher PASSED");
            logger.logInfo("testAddTeacher PASSED");
            dbManager.removeTeacher(teacherId);
            dbManager.removeSubject(subjectId);
        } else {
            System.out.println("testAddTeacher FAILED");
            logger.logInfo("testAddTeacher FAILED");
        }
    }

    public void testRemoveTeacher() {
        Teacher teacher = new Teacher("Тайлер Дерден", new Subject("Химия"));
        dbManager.addTeacher(teacher);
        int teacherId = dbManager.getTeacherId(teacher);

        dbManager.removeTeacher(teacherId);

        if (dbManager.getTeacherById(teacherId) == null) {
            System.out.println("testRemoveTeacher PASSED");
            logger.logInfo("testRemoveTeacher PASSED");
        } else {
            System.out.println("testRemoveTeacher FAILED");
            logger.logInfo("testRemoveTeacher FAILED");
        }
    }

    public void testUpdateTeacher() {
        Subject subject = new Subject("Английский");
        dbManager.addSubject(subject);
        Teacher teacher = new Teacher("Джо Байден", subject);
        dbManager.addTeacher(teacher);

        int subjectId = dbManager.getSubjectId(subject);
        int teacherId = dbManager.getTeacherId(teacher);
        Teacher updatedTeacher = new Teacher("Дональд Трамп", subject);
        dbManager.updateTeacher(teacherId, updatedTeacher);

        if ("Дональд Трамп".equals(dbManager.getTeacherById(teacherId).getName())) {
            System.out.println("testUpdateTeacher PASSED");
            logger.logInfo("testUpdateTeacher PASSED");
            dbManager.removeTeacher(teacherId);
            dbManager.removeSubject(subjectId);
        } else {
            System.out.println("testUpdateTeacher FAILED");
            logger.logInfo("testUpdateTeacher FAILED");
        }
    }

    public void testAddParent() {
        Parent parent = new Parent("Родитель");
        dbManager.addParent(parent);

        int parentId = dbManager.getParentId(parent);

        if (dbManager.getAllParents().contains(parent)) {
            System.out.println("testAddParent PASSED");
            logger.logInfo("testAddParent PASSED");
            dbManager.removeParent(parentId);
        } else {
            System.out.println("testAddParent FAILED");
            logger.logInfo("testAddParent FAILED");
        }
    }

    public void testRemoveParent() {
        Parent parent = new Parent("Родитель1");
        dbManager.addParent(parent);
        int parentId = dbManager.getParentId(parent);

        dbManager.removeParent(parentId);

        if (dbManager.getParentById(parentId) == null) {
            System.out.println("testRemoveParent PASSED");
            logger.logInfo("testRemoveParent PASSED");
        } else {
            System.out.println("testRemoveParent FAILED");
            logger.logInfo("testRemoveParent FAILED");
        }
    }

    public void testUpdateParent() {
        Parent parent = new Parent("Родитель2");
        dbManager.addParent(parent);
        int parentId = dbManager.getParentId(parent);

        Parent updatedParent = new Parent("Родитель21");
        dbManager.updateParent(parentId, updatedParent);

        if ("Родитель21".equals(dbManager.getParentById(parentId).getName())) {
            System.out.println("testUpdateParent PASSED");
            logger.logInfo("testUpdateParent PASSED");
            dbManager.removeParent(parentId);
        } else {
            System.out.println("testUpdateParent FAILED");
            logger.logInfo("testUpdateParent FAILED");
        }
    }

    public void testAddStudent() {
        Parent parent = new Parent("Родитель3");
        dbManager.addParent(parent);
        Student student = new Student("Иван Дурачек", 15, "М", parent);

        dbManager.addStudent(student);

        int studentId = dbManager.getStudentId(student);
        int parentId = dbManager.getParentId(parent);

        if (dbManager.getAllStudents().contains(student)) {
            System.out.println("testAddStudent PASSED");
            logger.logInfo("testAddStudent PASSED");
            dbManager.removeStudent(studentId);
            dbManager.removeParent(parentId);
        } else {
            System.out.println("testAddStudent FAILED");
            logger.logInfo("testAddStudent FAILED");
        }
    }

    public void testRemoveStudent() {
        Parent parent = new Parent("Родитель4");
        dbManager.addParent(parent);
        Student student = new Student("Дурачек Иван", 16, "М", parent);

        dbManager.addStudent(student);
        int studentId = dbManager.getStudentId(student);
        int parentId = dbManager.getParentId(parent);

        dbManager.removeStudent(studentId);

        if (dbManager.getStudentById(studentId) == null) {
            System.out.println("testRemoveStudent PASSED");
            logger.logInfo("testRemoveStudent PASSED");
            dbManager.removeParent(parentId);
        } else {
            System.out.println("testRemoveStudent FAILED");
            logger.logInfo("testRemoveStudent FAILED");
        }
    }

    public void testUpdateStudent() {
        Parent parent = new Parent("Майкл Джордан");
        dbManager.addParent(parent);
        Student student = new Student("Его сын", 17, "М", parent);
        dbManager.addStudent(student);
        int studentId = dbManager.getStudentId(student);
        int parentId = dbManager.getParentId(parent);

        Student updatedStudent = new Student("Его дочь", 17, "Ж", parent);
        dbManager.updateStudent(studentId, updatedStudent);

        if ("Его дочь".equals(dbManager.getStudentById(studentId).getName())) {
            System.out.println("testUpdateStudent PASSED");
            logger.logInfo("testUpdateStudent PASSED");
            dbManager.removeStudent(studentId);
            dbManager.removeParent(parentId);
        } else {
            System.out.println("testUpdateStudent FAILED");
            logger.logInfo("testUpdateStudent FAILED");
        }
    }
}
