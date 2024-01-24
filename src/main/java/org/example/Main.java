package org.example;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Streams<Integer> streams = Streams.of(List.of(3, 2, 4));
        Streams<Integer> filter = streams.filter(a -> a > 2);
        Streams<Integer> transform = streams.transform(3, 5);
        Map<String, String> map = streams.toMap(Object::toString, v -> "value" + v.toString());
        Map<Integer, ?> mapInt = streams.toMap(k -> k + 8, v -> "value" + v.toString());

        streams.printCollection();
        filter.printCollection();
        transform.printCollection();
        System.out.println(map);
        System.out.println(mapInt);

        System.out.println("После операций коллекция не изменилась:");
        streams.printCollection();
    }

}