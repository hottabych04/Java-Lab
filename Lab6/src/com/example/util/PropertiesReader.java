package com.example.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Класс для чтения и управления данными из .properties файлов.
 * Использует класс {@link Properties} для загрузки пары ключ-значение.
 */
public class PropertiesReader {
    private Properties properties;

    /**
     * Создает новый экземпляр PropertiesReader и загружает свойства из указанного .properties файла.
     *
     * @param filePath путь к .properties файлу, из которого будут загружены данные.
     * @throws IllegalArgumentException если файл не найден или произошла ошибка при его чтении.
     */
    public PropertiesReader(String filePath) {
        properties = new Properties();
        try (InputStream input = new FileInputStream(filePath)) {
            properties.load(input);
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
        checkProperty();
    }

    private void checkProperty(){
        try {
            assert getProperty("login")!=null;
            assert getProperty("password")!=null;
            assert getProperty("group").equals("user") || getProperty("group").equals("root");
            assert getProperty("user")!=null;
            assert getProperty("user")!=null;
        } catch (Exception e){
            throw new RuntimeException();
        }
    }

    /**
     * Возвращает значение свойства, соответствующее указанному ключу.
     *
     * @param key ключ свойства.
     * @return значение свойства, связанное с указанным ключом, или {@code null}, если ключ не найден.
     */
    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * Задает значение свойства, соответствующее указанному ключу.
     */
    public void switchDebug(){
        switch (properties.getProperty("debug")){
            case "true" -> properties.setProperty("debug", "false");
            case "false" -> properties.setProperty("debug", "true");
            default -> throw new RuntimeException();
        }
    }

    /**
     * Возвращает значение свойства, соответствующее указанному ключу.
     * Если ключ не найден, возвращается значение по умолчанию.
     *
     * @param key          ключ свойства.
     * @param defaultValue значение по умолчанию, возвращаемое, если ключ не найден.
     * @return значение свойства, связанное с указанным ключом, или {@code defaultValue}, если ключ не найден.
     */
    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    /**
     * Проверяет, содержится ли указанный ключ в файле свойств.
     *
     * @param key ключ, наличие которого требуется проверить.
     * @return {@code true}, если указанный ключ присутствует; {@code false} в противном случае.
     */
    public boolean containsKey(String key) {
        return properties.containsKey(key);
    }
}

