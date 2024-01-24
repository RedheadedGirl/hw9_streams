package org.example;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Streams<T> {

    private final List<T> list;

    public Streams(List<T> list) {
        this.list = list;
    }

    /**
     * Создание нового объекта на основе коллекции
     * @param list коллекция
     * @return объект класса Streams
     * @param <T> тип
     */
    public static <T> Streams<T> of(List<T> list) {
        return new Streams<>(list);
    }

    /**
     * Оставляет в коллекции элементы, которые соответствуют предикату
     * @param predicate предикат
     * @return обновленный объект типа Streams
     */
    public Streams<T> filter(Predicate<? super T> predicate) {
        return of(list.stream()
                .filter(predicate)
                .collect(Collectors.toList()));
    }

    /**
     * Преобразует элемент
     * @param oldElement старый элемент, который надо заменить
     * @param newElement новый элемент
     * @return обновленный объект типа Streams
     */
    public Streams<T> transform(T oldElement, T newElement) {
        return of(
                list.stream()
                        .map(element -> element.equals(oldElement) ? newElement : element)
                        .collect(Collectors.toList())
        );
    }

    /**
     * Создание мапы
     * @param keyMapper     функция, которая указывает, что будет использоваться в качестве ключа
     * @param valueMapper   функция, которая указывает, что будет использоваться в качестве значения
     * @return созданная мапа
     */
    public <K, V> Map<K, V> toMap(Function<? super T, ? extends V> keyMapper,
                                  Function<? super T, ? extends V> valueMapper) {
        return (Map<K, V>) list.stream().collect(Collectors.toMap(keyMapper, valueMapper));
    }

    /**
     * Распечатать коллекцию
     */
    public void printCollection() {
        System.out.println(list);
    }

    /**
     * Вернуть коллекцию
     */
    public List<T> getCollection() {
        return list;
    }
}


