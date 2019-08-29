package StreamAPI;


import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MostFrequentWords {
    public static void main(String[] args) {
        String input = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed sodales consectetur purus at faucibus. Donec mi quam, tempor vel ipsum non, faucibus suscipit massa. Morbi lacinia velit blandit tincidunt efficitur. Vestibulum eget metus imperdiet sapien laoreet faucibus. Nunc eget vehicula mauris, ac auctor lorem. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer vel odio nec mi tempor dignissim.";
        Scanner scanner = new Scanner(input)
                .useDelimiter("[^A-Za-zА-Яа-я0-9]+");

        List<String> list = new ArrayList<>();
        while (scanner.hasNext()) {
            list.add(scanner.next().toLowerCase());
        }

        Stream<String> stream = list.stream(); //.peek(System.out::println);
        /*Stream<String> stream = Stream.generate(new Scanner(System.in, StandardCharsets.UTF_8)
                .useDelimiter("[^A-Za-zА-Яа-я0-9]+")::next);*/
        Map<String, Long> map = stream.collect(
                        Collectors.groupingBy(
                                Function.identity(), Collectors.counting()
                        )
        );

        Map<String, Long> finalMap = new LinkedHashMap<>();


        map.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue()
                        .reversed()).forEachOrdered(e -> finalMap.put(e.getKey(), e.getValue()));

        for(Map.Entry<String, Long> item : map.entrySet()){

            System.out.printf("Key: %s  Value: %d \n", item.getKey(), item.getValue());
        }
        List<String> stringList = new ArrayList<String>(finalMap.keySet());
    }
}
