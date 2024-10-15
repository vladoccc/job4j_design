package ru.job4j.collection;

import java.util.NoSuchElementException;

public class SimpleQueue<T> {

    private final SimpleStack<T> input = new SimpleStack<>();
    private final SimpleStack<T> output = new SimpleStack<>();
    private int sizeIn;
    private int sizeOut;

    /**
     * Метод возвращает первое значение и удаляет его.
     */
    public T poll() {
        if (sizeOut == 0 && sizeIn == 0) {
            throw new NoSuchElementException("Queue is empty");
        }
        if (sizeOut == 0) {
            while (sizeIn != 0) {
                output.push(input.pop());
                sizeOut++;
                sizeIn--;
            }
        }
        sizeOut--;
        return output.pop();
    }

    /**
     * Метод помещает значение value в конец.
     */
    public void push(T value) {
        input.push(value);
        sizeIn++;
    }
}
