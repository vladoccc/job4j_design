package ru.job4j.collection;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class SimpleLinkedList<E> implements SimpleLinked<E> {

    private int size;

    private int modCount;

    private Node<E> head;

    /**
     * Сначала в методе устанавливается текущий элемент, который равен первому(головному) элементу в списке.
     * Далее создаем новую Ноду, в значение указываем наше value, а ссылка на след. элемент равна null.
     * Т.к мы вставляем новую Ноду в конец списка, а дальше элемента нет.
     * <p>
     * После чего проверяем, если первый (головной) элемент равен ноль, то созданная Нода равна первому элементу.
     * Если не равна ноль, то проверяем все элементы по порядку на ноль, до тех пор пока не дойдём до конца.
     * Как дошли до последнего элемента, у данного элемента ссылка на след. элемент равну нулю.
     * Значит приравнимаем созданную Ноду к этому элементу.
     */
    @Override
    public void add(E value) {
        Node<E> current = head;
        Node<E> newNode = new Node<>(value, null);
        if (head == null) {
            head = newNode;
        } else {
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
        modCount++;
    }

    /**
     * для начала проверяем, чтобы индекс элементов находился в диапазоне добавленных элементов.
     * После чего текущий элемент приравниваем к первому (головному) элементу в списке.
     * Перебираем элементы в цикле фор, до заданного индекса.
     * В теле цикла при каждой итерации передвигаем текущий элемент на следующую позицию current = current.next;
     * <p>
     * Как только дошли до элемента, который стоит перед index, значит следующим элементом current = current.next
     * будет именно значение под заданным index.
     * <p>
     * После чего возвращаем значение под заданным index: return current.item
     */
    @Override
    public E get(int index) {
        Objects.checkIndex(index, size);
        Node<E> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.item;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            /**
             * if (expectedModCount != modCount), если условие выполнено,
             *      * значит на момент итерирования была изменена коллекция, поэтому вылетает исключение.
             *      * Это fail-fast поведение
             */
            final int expectedModCount = modCount;

            /**
             * Текущий элемент перебирается с начала списка, т.е с первого элемента (головного).
             */
            Node<E> current = head;

            /** В методе next
             * item - переменная, которая сохранит текущее значение узла/нода (оно же поле item класса Node<E>),
             * это нужно чтобы получить первый элемент (головной);
             */

            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                return current != null;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                E item = current.item;
                current = current.next;
                return item;
            }
        };
    }

    private static class Node<E> {
        private E item;

        private Node<E> next;

        public Node(E item, Node<E> next) {
            this.item = item;
            this.next = next;
        }
    }
}
