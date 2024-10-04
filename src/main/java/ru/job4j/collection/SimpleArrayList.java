package ru.job4j.collection;

import java.lang.reflect.Array;
import java.util.*;

public class SimpleArrayList<T> implements SimpleList<T> {

    private T[] container;

    private int size;

    private int modCount;

    public SimpleArrayList(int capacity) {
        container = (T[]) new Object[capacity];
    }

    /**
     * Метод добавляет элемент в коллекцию
     * Проверка размера вынесена в отдельный метод growArray
     */
    @Override
    public void add(T value) {
        container = growArray();
        container[size++] = value;
        modCount++;
    }

    /**
     * Увеличиваем исходный массив в 2 раза
     */
    private T[] growArray() {
        if (container.length == 0) {
            container = (T[]) new Object[1];
        } else {
            container = Arrays.copyOf(container, container.length * 2);
        }
        return container;
    }

    /**
     * @param index    - Метод используется для присвоения указанного значения
     *                 указанному индексу данного массива.
     *                 Для проверки индекса - метод Objects.checkIndex()
     * @param newValue
     * @return  - Возвращает значение, старое значение, которое было изменено
     */
    @Override
    public T set(int index, T newValue) {
        Objects.checkIndex(index, size);
        T oldValue = container[index];
        Array.set(container, index, newValue);
        return oldValue;
    }

    /**
     * @param index - Т.к удалить элемент в массиве нельзя,
     *              мы под удалением подразумеваем сдвиг влево части элементов,
     *              расположенных правее индекса удаляемого элемента.
     *              Последний элемент массива зануляем
     * @return - возвращаем удаленный элемент.
     */
    @Override
    public T remove(int index) {
        Objects.checkIndex(index, size);
        T oldValue = container[index];
        System.arraycopy(container,
                index + 1,
                container,
                index,
                size - index - 1);
        container[size - 1] = null;
        size--;
        modCount++;
        return oldValue;
    }

    /**
     * @param index Получаем элемент массива по индексу
     * @return - возвращаем полученный элемент
     */
    @Override
    public T get(int index) {
        Objects.checkIndex(index, size);
        return container[index];
    }

    /**
     * @return Метод возвращает кол-во элементов в коллекции
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Чтобы реализовать итератор  нужны два поля. Каждое отвечает за свой показатель.
     * cursor - указатель на каком элементе находится итератор
     * <p>
     * Чтобы кинуть ConcurrentModificationException исключение заводят отдельную переменную
     * в итераторе expectedModCount = modCount и проверяют условие
     * if (expectedModCount != modCount). Если условие выполнено,
     * значит на момент итерирования была изменена коллекция, поэтому вылетает исключение.
     * Это называется fail-fast поведение
     * <p>
     * <p>
     * NoSuchElementException. Относится к первому показателю - количеству элементов.
     * Если итератор "уперся", т.е. нет больше элементов,
     * а клиент вызвал этот метод, то этим исключение мы ему подчеркиваем,
     * что элементов больше нет.
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            final int expectedModCount = modCount;
            int cursor = 0;

            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                return cursor < size;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return container[cursor++];
            }
        };
    }
}
