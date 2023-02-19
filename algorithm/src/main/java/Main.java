import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        File file = new File("hello");

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
        bw.append("x\n");
        bw.append("500000\n");
        for (int i = 0; i < 250000; i++) {
            bw.append("P a\n");
            bw.append("L\n");
        }
        bw.flush();
        bw.close();
    }
}
