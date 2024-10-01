package ru.job4j.generics;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class RoleStoreTest {

    @Test
    void whenAddAndFindId() {
        RoleStore store = new RoleStore();
        store.add(new User("4", "Admin"));
        User result = store.findById("4");
        assertThat(result.getUsername()).isEqualTo("Admin");
    }

    @Test
    void whenReplaceAndFindId() {
        RoleStore store = new RoleStore();
        store.add(new User("12", "Den"));
        store.replace("12", new User("12", "Sasha"));
        User result = store.findById("12");
        assertThat(result.getUsername()).isEqualTo("Sasha");
    }

    @Test
    void whenDeleteAndFindId() {
        UserStore store = new UserStore();
        store.add(new User("12", "Den"));
        store.delete("12");
        User result = store.findById("12");
        assertThat(result).isNull();
    }
}