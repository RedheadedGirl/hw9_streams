package org.example;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Streams<Integer> streams = Streams.of(List.of(3, 2, 4));
        Streams<Integer> filter = streams.filter(a -> a > 2);
        Streams<Integer> transform = streams.transform(3, 5);
        Map map = streams.toMap(d -> d.toString(), v -> "lol" + v.toString());
        System.out.println(map);
    }
}