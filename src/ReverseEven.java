import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Считывание из System.in. Удаление чисел на четных позициях. Обращает порядок.
 * Выводит получившуюся последовательность в System.out.
 */
public class ReverseEven {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Deque<Integer> deque = new LinkedList<>();
        int i = 0;
        Integer value;
        while (scanner.hasNext()) {
            try {
                value = Integer.parseInt(scanner.next());
                if (i % 2 == 1) {
                    deque.addFirst(value);
                }
                i++;
            } catch (NumberFormatException e) {
                // ignore
            }
        }
        PrintStream output = System.out;
        while (deque.size() > 0) {
            output.print(deque.removeFirst());
            output.print(' ');
        }
    }
}
