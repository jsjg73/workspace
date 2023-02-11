import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HowToUseInputHelper extends InputHelper{

    // run main method
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(fis()));
        recursive(br);
    }

    public static void recursive(BufferedReader br) throws IOException {
        String input = br.readLine();
        if(input == null){
            return;
        }
        System.out.println(input);
        recursive(br);
    }
}
