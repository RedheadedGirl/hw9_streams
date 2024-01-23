package org.example;

import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Streams<T> {

    private List<T> list;

    public Streams(List<T> list) {
        this.list = list;
    }

    public static Streams of(List list) {
        return new Streams<>(list);
    }

    public Streams<T> filter(Predicate<? super T> predicate) {
        return of(list.stream().filter(predicate).collect(Collectors.toList()));
    }

    /**
     * Преобразует элемент
     * @param oldElement
     * @param newElement
     * @return
     */
    public Streams transform(T oldElement, T newElement) {
        return of(
                list.stream()
                        .map(element -> element.equals(oldElement) ? newElement : element)
                        .collect(Collectors.toList())
        );
    }

    public Map toMap(Function keyMapper,
                     Function valueMapper) {
        return (Map) list.stream().collect(Collectors.toMap(keyMapper, valueMapper));
    }
}


