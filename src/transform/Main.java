package transform;

import java.io.*;

/**
 * перевод текстовых файлов из формата Windows в Unix.
 * заменяются "\r\n" -> '\n'
 */
public class Main {
    public static void main(String[] args) {
        //byte[] arr = {65, 66, 13, 13, 10, 10, 13, 67, 13, 13};
        //ByteArrayInputStream inputStream = new ByteArrayInputStream(arr);
        //System.setIn(inputStream);
        byte read;
        try {
            try (DataInputStream dataIn = new DataInputStream(System.in);
                 DataOutputStream dataOut = new DataOutputStream(System.out)) {
                try {
                    while (true) {
                        read = dataIn.readByte();
                        if (read == (byte) 13) {
                            //System.out.println("\\r");
                            read = dataIn.readByte();
                            if (read == (byte) 10) {
                                dataOut.writeByte((byte) 10);
                            } else {
                                dataOut.writeByte((byte) 13);
                                dataOut.writeByte(read);
                            }
                        } else {
                            dataOut.writeByte(read);
                        }
                    }
                } catch (EOFException eof) {
                    System.out.flush();  // чтобы проверяющая система корректно считать смогла
                    //System.out.println("eof");
                }
            }
        } catch (IOException exc) {
            // ignore
        }
    }
}
