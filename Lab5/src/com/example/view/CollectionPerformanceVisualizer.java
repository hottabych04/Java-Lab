package com.example.view;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс для визуализации производительности коллекций ArrayList и LinkedList.
 * Создает графическое отображение времени выполнения операций добавления и удаления
 * элементов для различных размеров коллекций.
 *
 * <p>Приложение создает окно с четырьмя графиками, показывающими:
 * <ul>
 *   <li>Среднее время добавления элементов</li>
 *   <li>Среднее время удаления элементов</li>
 *   <li>Общее время добавления элементов</li>
 *   <li>Общее время удаления элементов</li>
 * </ul>
 * </p>
 *
 * @author [Ваше имя]
 * @version 1.0
 */
public class CollectionPerformanceVisualizer extends JFrame {
    /**
     * Ширина окна приложения в пикселях
     */
    private static final int WIDTH = 1200;

    /**
     * Высота окна приложения в пикселях
     */
    private static final int HEIGHT = 800;

    /**
     * Отступ от краев графика в пикселях
     */
    private static final int PADDING = 50;

    /**
     * Массив количества элементов, для которых проводится тестирование
     */
    private static final int[] ELEMENT_COUNTS = {10, 100, 1000, 10000, 100000};

    /**
     * Хранит данные о производительности ArrayList для разных размеров коллекции
     */
    private Map<Integer, PerformanceData> arrayListData = new HashMap<>();

    /**
     * Хранит данные о производительности LinkedList для разных размеров коллекции
     */
    private Map<Integer, PerformanceData> linkedListData = new HashMap<>();

    /**
     * Конструктор класса. Создает основное окно приложения и инициализирует
     * графические компоненты.
     */
    public CollectionPerformanceVisualizer() {
        setTitle("Collection Performance Visualization");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new GridLayout(2, 2));
        mainPanel.add(createChartPanel("Average Add Time", true, true));
        mainPanel.add(createChartPanel("Average Remove Time", true, false));
        mainPanel.add(createChartPanel("Total Add Time", false, true));
        mainPanel.add(createChartPanel("Total Remove Time", false, false));

        add(mainPanel);
    }

    /**
     * Создает панель с графиком для отображения определенного типа данных.
     *
     * @param title название графика
     * @param isAverage true для отображения среднего времени, false для общего времени
     * @param isAdd true для операций добавления, false для операций удаления
     * @return панель с графиком
     */
    private JPanel createChartPanel(String title, boolean isAverage, boolean isAdd) {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int width = getWidth() - 2 * PADDING;
                int height = getHeight() - 2 * PADDING;

                // Draw axes
                g2d.drawLine(PADDING, getHeight() - PADDING, PADDING, PADDING);
                g2d.drawLine(PADDING, getHeight() - PADDING, getWidth() - PADDING, getHeight() - PADDING);

                // Draw title
                g2d.drawString(title, getWidth() / 2 - 50, 20);

                // Find max value for scaling
                double maxValue = 0;
                for (int count : ELEMENT_COUNTS) {
                    if (arrayListData.containsKey(count)) {
                        PerformanceData data = arrayListData.get(count);
                        maxValue = Math.max(maxValue, isAdd ?
                                (isAverage ? data.addMedianTime : data.addTotalTime) :
                                (isAverage ? data.removeMedianTime : data.removeTotalTime));
                    }
                    if (linkedListData.containsKey(count)) {
                        PerformanceData data = linkedListData.get(count);
                        maxValue = Math.max(maxValue, isAdd ?
                                (isAverage ? data.addMedianTime : data.addTotalTime) :
                                (isAverage ? data.removeMedianTime : data.removeTotalTime));
                    }
                }

                // Draw scale
                for (int i = 0; i <= 5; i++) {
                    int y = getHeight() - PADDING - (i * height / 5);
                    g2d.drawString(String.format("%.2f", maxValue * i / 5), 5, y);
                }

                // Draw data points and lines
                drawDataLine(g2d, arrayListData, Color.BLUE, width, height, maxValue, isAverage, isAdd);
                drawDataLine(g2d, linkedListData, Color.RED, width, height, maxValue, isAverage, isAdd);

                // Draw legend
                g2d.setColor(Color.BLUE);
                g2d.fillRect(getWidth() - 120, 20, 20, 10);
                g2d.drawString("ArrayList", getWidth() - 90, 30);
                g2d.setColor(Color.RED);
                g2d.fillRect(getWidth() - 120, 40, 20, 10);
                g2d.drawString("LinkedList", getWidth() - 90, 50);
            }

            private void drawDataLine(Graphics2D g2d, Map<Integer, PerformanceData> data,
                                      Color color, int width, int height, double maxValue,
                                      boolean isAverage, boolean isAdd) {
                g2d.setColor(color);
                int prevX = 0, prevY = 0;
                boolean first = true;

                for (int i = 0; i < ELEMENT_COUNTS.length; i++) {
                    int count = ELEMENT_COUNTS[i];
                    if (data.containsKey(count)) {
                        PerformanceData perfData = data.get(count);
                        double value = isAdd ?
                                (isAverage ? perfData.addMedianTime : perfData.addTotalTime) :
                                (isAverage ? perfData.removeMedianTime : perfData.removeTotalTime);

                        int x = PADDING + (i * width / (ELEMENT_COUNTS.length - 1));
                        int y = getHeight() - PADDING - (int)((value / maxValue) * height);

                        g2d.fillOval(x - 3, y - 3, 6, 6);
                        if (!first) {
                            g2d.drawLine(prevX, prevY, x, y);
                        }
                        first = false;
                        prevX = x;
                        prevY = y;

                        // Draw x-axis labels
                        g2d.drawString(String.valueOf(count), x - 20, getHeight() - PADDING + 20);
                    }
                }
            }
        };
    }

    /**
     * Читает и парсит файл логов с результатами тестирования производительности.
     *
     * @param filePath путь к файлу с логами
     * @throws IOException если возникла ошибка при чтении файла
     */
    public void parseLogFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            String currentList = null;
            PerformanceData currentData = null;
            int currentElements = 0;

            Pattern testPattern = Pattern.compile("---------- TEST WITH (\\d+) ELEMENTS----------");

            while ((line = reader.readLine()) != null) {
                Matcher matcher = testPattern.matcher(line);
                if (matcher.find()) {
                    currentElements = Integer.parseInt(matcher.group(1));
                    continue;
                }

                if (line.equals("ArrayList")) {
                    currentList = "ArrayList";
                    currentData = new PerformanceData();
                    arrayListData.put(currentElements, currentData);
                    continue;
                } else if (line.equals("LinkedList")) {
                    currentList = "LinkedList";
                    currentData = new PerformanceData();
                    linkedListData.put(currentElements, currentData);
                    continue;
                }

                if (currentData != null) {
                    if (line.contains("addMedianTime=")) {
                        currentData.addMedianTime = extractNumber(line);
                    } else if (line.contains("removeMedianTime=")) {
                        currentData.removeMedianTime = extractNumber(line);
                    } else if (line.contains("addTotalTime=")) {
                        currentData.addTotalTime = extractNumber(line);
                    } else if (line.contains("removeTotalTime=")) {
                        currentData.removeTotalTime = extractNumber(line);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Извлекает числовое значение из строки лога.
     *
     * @param line строка лога, содержащая числовое значение
     * @return извлеченное числовое значение или 0, если значение не найдено
     */
    private double extractNumber(String line) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            return Double.parseDouble(matcher.group());
        }
        return 0;
    }

    /**
     * Внутренний класс для хранения данных о производительности коллекции.
     */
    private static class PerformanceData {
        double addMedianTime;
        double removeMedianTime;
        double addTotalTime;
        double removeTotalTime;
    }
}