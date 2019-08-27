
import mail_logging.Main;

import java.io.*;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class convert {
    public static void main(String[] args) {
        byte[] arr = {48, 49, 50, 51};
        ByteArrayInputStream inputStream = new ByteArrayInputStream(arr);
        try {
            String s = readAsString(inputStream, StandardCharsets.US_ASCII);
            System.out.println(s);
        } catch (IOException exc) {
            // ignore
            System.out.println("An IOException occured.");
        }
    }

    public static String readAsString(InputStream inputStream, Charset charset) throws IOException {
        StringBuffer sb = new StringBuffer();
        try (InputStreamReader isr = new InputStreamReader(inputStream, charset)) {
            int i = isr.read();
            while (i > 0 ) {
                //System.out.println(i);
                //System.out.println((char) i);
                sb.append((char) i);
                i = isr.read();
            }

        } catch (IOException exc) {
            // ignore
        }
        return sb.toString();
    }
}
