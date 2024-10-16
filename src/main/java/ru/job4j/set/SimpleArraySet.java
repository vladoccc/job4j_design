package ru.job4j.set;

import ru.job4j.collection.SimpleArrayList;

import java.util.Iterator;
import java.util.Objects;

public class SimpleArraySet<T> implements SimpleSet<T> {

    private SimpleArrayList<T> set = new SimpleArrayList<>(0);

    @Override
    public boolean add(T value) {
        boolean result = false;
        if (!contains(value)) {
            set.add(value);
            result = true;
        }
        return result;
    }

    /**
     * Создаем переменную и присваиваем ей итератор.
     * Для того чтобы каждый раз не вызывать новый итератор.
     * <p>
     * <p>
     * Тоже можно через итератор сделать.
     * for (T number : set) {
     * result = Objects.equals(number, value);
     * }
     */
    @Override
    public boolean contains(T value) {
        boolean result = false;
        Iterator<T> iter = iterator();
        while (iter.hasNext()) {
            result = Objects.equals(iter.next(), value);
        }
        return result;
    }

    @Override
    public Iterator<T> iterator() {
        return set.iterator();
    }
}
