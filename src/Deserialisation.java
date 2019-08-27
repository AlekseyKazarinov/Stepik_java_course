import java.io.*;
import java.util.Objects;

public class Deserialisation {
    public static void main(String[] args) {

    }

    public static Animal[] deserializeAnimalArray(byte[] data) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        //int numberObjects = inputStream.read();
        try (ObjectInputStream ois = new ObjectInputStream(inputStream)) {
            int numberObjects = ois.readInt();
            if (numberObjects < 0) throw new IllegalArgumentException();
            Animal[] animals = new Animal[numberObjects];
            for (int i = 0; i < numberObjects; i++) {
                try {
                    animals[i] = (Animal) ois.readObject();
                } catch (ClassCastException cce) {
                    throw new IllegalArgumentException();
                }
            }
            return animals;
        } catch (IOException | ClassNotFoundException exc ) {
            throw new IllegalArgumentException();
        }
    }

}

class Animal implements Serializable {
    private final String name;

    public Animal(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Animal) {
            return Objects.equals(name, ((Animal) obj).name);
        }
        return false;
    }
}