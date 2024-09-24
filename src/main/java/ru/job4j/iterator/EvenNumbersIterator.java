package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class EvenNumbersIterator implements Iterator<Integer> {

    private int[] data;
    private int index;

    public EvenNumbersIterator(int[] data) {
        this.data = data;
    }

    @Override
    public boolean hasNext() {
        while (index < data.length) {
            if (data[index] % 2 == 0) {
                break;
            } else {
                index++;
            }
        }
        return index < data.length;
    }

    @Override
    public Integer next() {
        if (hasNext()) {
            return data[index++];
        }
        throw new NoSuchElementException();
    }
}
