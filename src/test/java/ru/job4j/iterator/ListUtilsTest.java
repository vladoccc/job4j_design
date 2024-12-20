package ru.job4j.iterator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Predicate;

class ListUtilsTest {

    private List<Integer> input;

    @BeforeEach
    void setUp() {
        input = new ArrayList<>(Arrays.asList(1, 3));
    }

    @Test
    void whenAddBefore() {
        ListUtils.addBefore(input, 1, 2);
        assertThat(input).hasSize(3).containsSequence(1, 2, 3);
    }

    @Test
    void whenAddBeforeWithInvalidIndex() {
        assertThatThrownBy(() -> ListUtils.addBefore(input, 3, 2))
                .isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    void whenAddAfter() {
        ListUtils.addAfter(input, 0, 2);
        assertThat(input).hasSize(3).containsSequence(1, 2, 3);
    }

    @Test
    void whenRemoveIf() {
        input.add(2, 7);
        input.add(3, 8);
        Predicate<Integer> filter = s -> s <= 1;
        ListUtils.removeIf(input, filter);
        assertThat(input).containsSequence(3, 7, 8);
    }

    @Test
    void whenReplaceIf() {
        Predicate<Integer> filter = s -> s == 1;
        ListUtils.replaceIf(input, filter, 10);
        assertThat(input).containsSequence(10, 3);
    }

    @Test
    void whenRemoveAll() {
        List<Integer> elements = new ArrayList<>(Arrays.asList(1, 3));
        ListUtils.removeAll(input, elements);
        assertThat(input).isEmpty();
    }

    @Test
    void whenAddAndRemoveAll() {
        input.add(2, 4);
        input.add(3, 8);
        List<Integer> elements = new ArrayList<>(Arrays.asList(1, 3));
        ListUtils.removeAll(input, elements);
        assertThat(input).containsSequence(4, 8);
    }
}