import java.io.ObjectInputStream;
import java.util.Optional;
import java.util.OptionalInt;

public class UsePair {
    public static void main(String[] args) {
        Pair<Integer, String> pair = Pair.of(1, "hello");
        Integer i = pair.getFirst(); // 1
        String s = pair.getSecond(); // "hello"

        Pair<Integer, String> pair2 = Pair.of(1, "hello");
        boolean mustBeTrue = pair.equals(pair2); // true!
        boolean mustAlsoBeTrue = pair.hashCode() == pair2.hashCode(); // true!
    }
}

class Pair<M, N> {
    private final M firstValue;
    private final N secondValue;

    private Pair(M firstValue, N secondValue) {
        this.firstValue = firstValue;
        this.secondValue = secondValue;
    }

    /**
     * Статический фабричный метод для класса Pair
     * @param firstValue первый элемент
     * @param secondValue второй элемент
     * @param <M> тип первого элемента
     * @param <N> тип второго элемента
     * @return
     */
    public static <M,N> Pair<M,N> of (M firstValue, N secondValue) {
        return new Pair<>(firstValue, secondValue);
    }

    public M getFirst() {
        return this.firstValue;
    }


    public N getSecond() {
        return this.secondValue;
    }

    @Override
    public boolean equals(Object others) {
        if (this == others) return true;
        if (others == null) return false;

        int matches = 0;
        if (others instanceof Pair) {
            Pair oth = (Pair) others;
            if (this.getFirst() != null) {
                if (this.getFirst().equals(oth.getFirst())) {
                    matches++;
                }
            } else {
                if (oth.getFirst() == null) {
                    matches++;
                }
            }

            if (this.getSecond() != null) {
                if (this.getSecond().equals(oth.getSecond())) {
                    matches++;
                }
            } else {
                if (oth.getSecond() == null) {
                    matches++;
                }
            }
        }
        return matches==2;
    }


    @Override
    public int hashCode(){
        return this.getFirst().hashCode() + this.getSecond().hashCode();
    }

}
