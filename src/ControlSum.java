import java.io.*;

public class ControlSum {

    public static void main(String[] args) throws IOException {
        InputStream stream;
        int result;
        stream = getStream( new byte[] { 0x33, 0x45, 0x01});

        result = checkSumOfStream(stream);
        System.out.print(result);
    }

    public static InputStream getStream(byte [] data)  {
        return new ByteArrayInputStream (data);
    }

    public static int checkSumOfStream(InputStream inputStream) throws IOException {
        int c = 0;
        try (DataInputStream data = new DataInputStream(inputStream)) {
            int read;
            boolean stop = false;
            while (!stop) {
                try {
                    read = data.readByte();
                    c = Integer.rotateLeft(c, 1) ^ read;
                } catch (java.io.EOFException eof) {
                    stop = true;
                    return c;
                }
            }
        }
        return c;
    }

}
