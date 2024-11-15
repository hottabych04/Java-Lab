package com.example.model;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Пользовательская реализация списка на основе массива.
 * Позволяет хранить и управлять элементами.
 *
 * @param <E> Тип элементов, которые будут храниться в списке.
 */
public class CustomArrayList<E> extends AbstractList<E> implements List<E> {

    /**
     * Значение по умолчанию для начальной емкости списка.
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * Пустой массив, используемый при инициализации списка с нулевой емкостью.
     */
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

    /**
     * Массив для хранения элементов списка.
     */
    transient Object[] elementData;

    /**
     * Текущий размер списка.
     */
    private int size;

    /**
     * Создает новый пустой список.
     */
    public CustomArrayList() {
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }

    /**
     * Добавляет элемент в конец списка.
     *
     * @param e Элемент для добавления.
     * @return Всегда возвращает {@code true}.
     */
    public boolean add(E e) {
        ensureCapacityInternal(size + 1);
        elementData[size++] = e;
        return true;
    }

    /**
     * Возвращает элемент по индексу.
     *
     * @param index Индекс элемента.
     * @return Элемент по указанному индексу.
     * @throws IndexOutOfBoundsException Если индекс вне допустимого диапазона.
     */
    @Override
    public E get(int index) {
        rangeCheck(index);
        return elementData(index);
    }

    /**
     * Проверяет, что индекс находится в допустимом диапазоне.
     *
     * @param index Индекс для проверки.
     * @throws IndexOutOfBoundsException Если индекс больше или равен размеру списка.
     */
    private void rangeCheck(int index) {
        if (index >= size)
            throw new IndexOutOfBoundsException("Index: %d, Size: %d".formatted(index, size));
    }

    /**
     * Возвращает элемент массива по индексу с приведением типа.
     *
     * @param index Индекс элемента.
     * @return Элемент по указанному индексу.
     */
    @SuppressWarnings("unchecked")
    private E elementData(int index) {
        return (E) elementData[index];
    }

    /**
     * Обеспечивает наличие достаточной емкости для добавления новых элементов.
     *
     * @param minCapacity Минимальная необходимая емкость.
     */
    private void ensureCapacityInternal(int minCapacity) {
        if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
            minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
        }
        ensureExplicitCapacity(minCapacity);
    }

    /**
     * Увеличивает емкость массива, если она недостаточна.
     *
     * @param minCapacity Минимальная необходимая емкость.
     */
    private void ensureExplicitCapacity(int minCapacity) {
        if (minCapacity - elementData.length > 0) {
            grow();
        }
    }

    /**
     * Увеличивает емкость массива по формуле (старый размер * 3 / 2 + 1).
     */
    private void grow() {
        int oldCapacity = elementData.length;
        int newCapacity = (oldCapacity * 3) / 2 + 1;
        elementData = Arrays.copyOf(elementData, newCapacity);
    }

    /**
     * Возвращает текущий размер списка.
     *
     * @return Размер списка.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Удаляет элемент по индексу.
     *
     * @param index Индекс элемента для удаления.
     * @return Удаленный элемент.
     * @throws IndexOutOfBoundsException Если индекс вне допустимого диапазона.
     */
    @Override
    public E remove(int index) {
        rangeCheck(index);

        E oldValue = elementData(index);

        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(elementData, index + 1, elementData, index, numMoved);
        }
        elementData[--size] = null;

        return oldValue;
    }

    /**
     * Удаляет первый элемент списка.
     *
     * @return Удаленный первый элемент.
     * @throws NoSuchElementException Если список пуст.
     */
    @Override
    public E removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return remove(0);
    }

    /**
     * Проверяет, является ли список пустым.
     *
     * @return {@code true}, если список пуст; {@code false} в противном случае.
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}