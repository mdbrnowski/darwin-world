package agh.ics.oop.model.util;

import javafx.util.Pair;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PopularityCounter<T> {
    public List<Pair<T, Integer>> getMostPopular(List<T> list, int n) {
        Map<T, Integer> counter = new HashMap<>();
        for (T x : list)
            counter.put(x, counter.getOrDefault(x, 0) + 1);
        return counter.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(n)
                .map(e -> new Pair<>(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }

    public String getMostPopularAsString(List<T> list, int n) {
        List<Pair<T, Integer>> mostPopular = getMostPopular(list, n);
        return mostPopular.stream()
                .map(e -> "%s   [%d]".formatted(e.getKey(), e.getValue()))
                .collect(Collectors.joining("\n"));
    }
}
