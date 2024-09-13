package ru.job4j.assertj;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class BoxTest {

    @Test
    void isThisSphere() {
        Box box = new Box(0, 10);
        String name = box.whatsThis();
        assertThat(name).isEqualTo("Sphere");
    }

    @Test
    void isThisTetrahedron() {
        Box box = new Box(4, 12);
        String name = box.whatsThis();
        assertThat(name).isEqualTo("Tetrahedron");
    }

    @Test
    void isThisCube() {
        Box box = new Box(8, 5);
        String name = box.whatsThis();
        assertThat(name).isEqualTo("Cube");
    }

    @Test
    void isGetNumberOfVerticesForSphere() {
        Box box = new Box(0, 5);
        int vertices = box.getNumberOfVertices();
        assertThat(vertices).isLessThan(10);
    }

    @Test
    void isGetNumberOfVerticesForTetrahedron() {
        Box box = new Box(4, 5);
        int vertices = box.getNumberOfVertices();
        assertThat(vertices).isGreaterThan(3);
    }

    @Test
    void isExistCube() {
        Box box = new Box(8, 5);
        boolean presence = box.isExist();
        assertThat(presence).isTrue();
    }

    @Test
    void isExistSphere() {
        Box box = new Box(1, 1);
        boolean presence = box.isExist();
        assertThat(presence).isFalse();
    }

    @Test
    void isAreaSphere() {
        Box box = new Box(0, 1);
        double area = box.getArea();
        assertThat(area).isEqualTo(12.56d, withPrecision(0.01d));
    }

    @Test
    void isAreaTetrahedron() {
        Box box = new Box(4, 3);
        double area = box.getArea();
        assertThat(area).isEqualTo(15.57d, withPrecision(0.1d));
    }

    @Test
    void isAreaCube() {
        Box box = new Box(8, 5);
        double area = box.getArea();
        assertThat(area).isEqualTo(150);
    }
}