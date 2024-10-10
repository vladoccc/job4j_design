package ru.job4j.collection;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class ForwardLinked<T> implements Iterable<T> {

    private int size;

    private int modCount;

    private Node<T> head;

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
    public void add(T value) {
        Node<T> current = head;
        Node<T> newNode = new Node<>(value, null);
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
     * Сразу создаем нову Ноду, в value записывается значение переданное в методе, а ссылаться будет
     * на наш текущий элемент head. Если head = null, то значит у первого элемента нет ссылки на следующий.
     */
    public void addFirst(T value) {
        head = new Node<>(value, head);
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
    public T get(int index) {
        Objects.checkIndex(index, size);
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.item;
    }

    /**
     * Делаем проверку, что head != null.
     * <p>
     * Создаем новую Ноду со ссылкой на следующий элемент head.next. Т.к. после удаления первого элемента,
     * head сдвинется вправо на позицию.
     * <p>
     * Значение первого элемента сохраняем в отдельную переменную.
     * Обнуляем значения  элемента head.item  и ссылку на следующий элемент head.next.
     * <p>
     * Теперь приравниваем head(головной элемент) к созданной ноде node (node = head.next). Т.к первым элементом теперь
     * будет элемент, расположенный справа за удаленным элементом.
     * <p>
     * Уменьшаем размер и инкрементируем операцию modCount
     *
     * @return Возвращаем удаленное значение (result = head.item)
     */
    public T deleteFirst() {
        if (head == null) {
            throw new NoSuchElementException();
        }
        Node<T> node = head.next;
        T result = head.item;
        head.item = null;
        head.next = null;
        head = node;
        size--;
        modCount++;
        return result;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            /**
             * if (expectedModCount != modCount), если условие выполнено,
             *      * значит на момент итерирования была изменена коллекция, поэтому вылетает исключение.
             *      * Это fail-fast поведение
             */
            final int expectedModCount = modCount;

            /**
             * Текущий элемент перебирается с начала списка, т.е с первого элемента (головного).
             */
            Node<T> current = head;

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
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                T item = current.item;
                current = current.next;
                return item;
            }
        };
    }

    private static class Node<T> {
        private T item;

        private Node<T> next;

        public Node(T item, Node<T> next) {
            this.item = item;
            this.next = next;
        }
    }
}
