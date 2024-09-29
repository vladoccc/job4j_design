package ru.job4j.iterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Balancer {

    public static void split(List<ArrayList<Integer>> nodes, Iterator<Integer> source) {
        while (source.hasNext()) {
            int value = source.next();
            ArrayList<Integer> list = nodes.get(value % nodes.size());
            list.add(value);
        }
    }
}



