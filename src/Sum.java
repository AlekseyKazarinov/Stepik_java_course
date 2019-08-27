import java.io.IOException;
import java.util.Scanner;

public class Sum {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double sum = 0;
        while (scanner.hasNext()) {
            try {
                sum += Double.parseDouble(scanner.next());

            } catch (Exception exc) {
                // ignore
            }
        }
        System.out.printf("%.6f\n",sum);
    }
}
