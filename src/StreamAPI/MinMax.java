package StreamAPI;

import java.util.Comparator;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

/**
 *  Метод, находящий в стриме минимальный и максимальный элементы в соответствии порядком, заданным Comparator'ом.
 *  Найденные минимальный и максимальный элементы передайте в minMaxConsumer следующим образом:
 *  <code> minMaxConsumer.accept(min, max); </code>
 *  Если стрим не содержит элементов, то вызовите:
 *  <code> minMaxConsumer.accept(null, null); </code>
 */

public class MinMax {
    public static <T> void findMinMax(
            Stream<? extends T> stream,
            Comparator<? super T> order,
            BiConsumer<? super T, ? super T> minMaxConsumer) {

        Object[] arr = stream.sorted(order).toArray();
        if (arr.length == 0) {
            minMaxConsumer.accept(null, null);
        } else {
            minMaxConsumer.accept((T)arr[0], (T) arr[arr.length-1]);
        }
    }
}


/*
// Эталлонное решение без использования промежуточного хранилища и небезопасного преобразования типов.
// Делает всё за один проход.

private static class MinMaxFinder<T> implements Consumer<T> {

    private final Comparator<? super T> order;
    T min;
    T max;

    private MinMaxFinder(Comparator<? super T> order) {
        this.order = order;
    }

    @Override
    public void accept(T t) {
        if (min == null || order.compare(t, min) < 0) {
            min = t;
        }
        if (max == null || order.compare(max, t) < 0) {
            max = t;
        }
    }
}*/
