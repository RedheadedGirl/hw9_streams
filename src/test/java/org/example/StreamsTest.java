package org.example;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StreamsTest {

    @Nested
    class Create {

        private static Stream<List> provideLists() {
            return Stream.of(
                    List.of("s1", "s2"),
                    List.of(1, 2, 3, 4),
                    List.of(10d, 20d, 30d)
            );
        }

        @ParameterizedTest
        @MethodSource("provideLists")
        void givenList_whenOfIsCalled_shouldCreateNewStreams(List list) {
            Streams<String> stringStreams = Streams.of(list);
            assertEquals(list, stringStreams.getCollection());
        }
    }

    @Nested
    class Filter {

        @Test
        void givenList_whenFilterByPredicate_shouldNotChangeOldCollection() {
            Streams<String> stringStreams = Streams.of(List.of("dog", "acde", "cat"));

            Streams<String> filtered = stringStreams.filter(element -> element.length() == 3);

            assertEquals(2, filtered.getCollection().size());
            assertEquals(List.of("dog", "acde", "cat"), stringStreams.getCollection());
        }

        @Test
        void givenList_whenFilterByPredicateHasMatches_shouldDo() {
            Streams<String> stringStreams = Streams.of(List.of("abcde", "acd", "cat"));

            Streams<String> filtered = stringStreams.filter(element -> element.contains("b"));

            assertEquals(1, filtered.getCollection().size());
            assertEquals("abcde", filtered.getCollection().getFirst());
        }

        @Test
        void givenList_whenFilterByPredicateHasNoMatches_shouldDo() {
            Streams<Integer> stringStreams = Streams.of(List.of(1, 12, 23));

            Streams<Integer> filtered = stringStreams.filter(element -> element > 30);

            assertTrue(filtered.getCollection().isEmpty());
        }

    }

    @Nested
    class Transform {

        private static Stream<Arguments> provideArgs() {
            return Stream.of(
                    Arguments.of(List.of("s1", "s2", "s3"), List.of("s1", "s100", "s3"), "s2", "s100"),
                    Arguments.of(List.of(1, 20, 5, 4), (List.of(1, 10, 5, 4)), 20, 10),
                    Arguments.of(List.of(10d, 40d, 30d), List.of(10d, 20d, 30d), 40d, 20d)
            );
        }

        @ParameterizedTest
        @MethodSource("provideArgs")
        void givenStreams_whenTransform_thenCorrect(List original, List result, Object old, Object newObj) {
            Streams transformed = Streams.of(original).transform(old, newObj);

            assertEquals(result, transformed.getCollection());
        }

        @Test
        void givenStreams_whenTransform_thenNotChangeOldCollection() {
            List<Integer> list = List.of(10, 20);

            Streams.of(list).transform(20, 50).transform(800, 80);

            assertEquals(List.of(10, 20), list);
        }

        @Test
        void givenStreams_whenTransformAndNoSuchElement_thenOkDoNothing() {
            List<Integer> list = List.of(10, 20);

            Streams transformed = Streams.of(list).transform(200, 50).transform(800, 80);

            assertEquals(list, transformed.getCollection());
        }
    }

    @Nested
    class Map {

        @Test
        void givenStream_whenMapCalled_ThenReturnMap() {
            Streams<Integer> streams = Streams.of(List.of(10, 50, 90));

            java.util.Map<String, String> map = streams.toMap(Object::toString, v -> v.toString() + "value");

            assertEquals("10value", map.get("10"));
            assertEquals("50value", map.get("50"));
            assertEquals("90value", map.get("90"));
        }

        @Test
        void givenStream_whenMapCalled_ThenNotChangeOldCollection() {
            List<Integer> list = List.of(10, 50);
            Streams<Integer> streams = Streams.of(list);

            java.util.Map<Integer, Integer> map = streams.toMap(k -> k*10, v -> v*100);

            assertEquals(1000, map.get(100));
            assertEquals(5000, map.get(500));
            assertEquals(List.of(10, 50), list);
        }

    }

}