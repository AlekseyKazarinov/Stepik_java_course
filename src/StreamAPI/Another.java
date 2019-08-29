package StreamAPI;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Another {
    public static void main(String[] args) {
        /*Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8)
                .useDelimiter("[^a-zа-я0-9]+");
        Stream<String> stream = Stream.generate(scanner::next);
        Map<String, Integer> map = new HashMap<>();
        Map<String, Integer> finalMap = map;
        map = stream.collect(Collectors.toMap(s -> s, ));
        System.out.println(map.toString());*/

        String input = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed sodales consectetur purus at faucibus. Donec mi quam, tempor vel ipsum non, faucibus suscipit massa. Morbi lacinia velit blandit tincidunt efficitur. Vestibulum eget metus imperdiet sapien laoreet faucibus. Nunc eget vehicula mauris, ac auctor lorem. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer vel odio nec mi tempor dignissim.";

        Scanner scanner = new Scanner(input)
                .useDelimiter("[^A-Za-zА-Яа-я0-9]+");

        List<String> list = new ArrayList<>();
        while (scanner.hasNext()) {
            list.add(scanner.next().toLowerCase());
        }

        Map<String, Long> result =
                list.stream().collect(
                        Collectors.groupingBy(
                                Function.identity(), Collectors.counting()
                        )
                );

        Map<String, Long> finalMap = new LinkedHashMap<>();

        //Sort a map and add to finalMap
        result.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByKey())
                .sorted(Map.Entry.<String, Long>comparingByValue()
                        .reversed()).forEachOrdered(e -> finalMap.put(e.getKey(), e.getValue()));


        //System.out.println(finalMap);
        int i = 0;
        for (String key : finalMap.keySet()) {
            if (i == 10) break;
            System.out.println(key);
            i++;
        }
    }
}

/*
 Эталонное решение

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        new BufferedReader(new InputStreamReader(System.in))
            .lines()
            .flatMap(s -> Stream.of(s.split("[^a-zA-Zа-яА-Я0-9]")))
            .filter(s -> !s.isEmpty())
            .map(String::toLowerCase)
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
            .entrySet()
            .stream()
            .sorted(Map.Entry.<String, Long>comparingByValue()
                    .reversed()
                    .thenComparing(Map.Entry.comparingByKey()))
            .limit(10)
            .map(Map.Entry::getKey)
            .forEachOrdered(System.out::println);
    }
}

 */