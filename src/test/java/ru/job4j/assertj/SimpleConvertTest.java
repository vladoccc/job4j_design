package ru.job4j.assertj;

import org.assertj.core.data.Index;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleConvertTest {

    @Test
    void checkArray() {
        SimpleConvert simpleConvert = new SimpleConvert();
        String[] array = simpleConvert.toArray("first", "second", "three", "four", "five");
        assertThat(array).hasSize(5)
                .contains("second")
                .contains("first", Index.atIndex(0))
                .containsAnyOf("zero", "second", "six")
                .doesNotContain("first", Index.atIndex(1));
    }

    @Test
    void checkList() {
        SimpleConvert simpleConvert = new SimpleConvert();
        List<String> list = simpleConvert.toList("one", "two", "three", "four");
        assertThat(list).hasSize(4)
                .contains("one")
                .contains("two", Index.atIndex(1))
                .containsAnyOf("nine", "six", "four")
                .doesNotContain("first", Index.atIndex(0))
                .endsWith("four")
                .containsSequence("two", "three");
    }

    @Test
    void checkSet() {
        SimpleConvert simpleConvert = new SimpleConvert();
        Set<String> set = simpleConvert.toSet("one", "one", "six", "seven", "ten");
        assertThat(set).hasSize(4)
                .containsOnly("six", "one", "ten", "seven")
                .doesNotContain("two", "three", "four")
                .anySatisfy(e -> {
                    assertThat(e).isEqualTo("ten");
                })
                .endsWith("ten");
    }

    @Test
    void checkMap() {
        SimpleConvert simpleConvert = new SimpleConvert();
        Map<String, Integer> map = simpleConvert.toMap("one", "six", "seven");
        assertThat(map).hasSize(3)
                .containsKey("six")
                .containsValue(1)
                .doesNotContainKey("two")
                .containsEntry("seven", 2);
    }
}