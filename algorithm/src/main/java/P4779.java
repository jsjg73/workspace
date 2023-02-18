import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class P4779 extends InputHelper{
    public static void main(String[] args) throws IOException {
        int[] arr = new int[13];
        arr[0] = 1;
        for (int i = 1; i < arr.length; i++) {
            arr[i] = arr[i-1]*3;
        }

        StringBuffer sb = new StringBuffer();

        BufferedReader br = new BufferedReader(new InputStreamReader(fis()));
        String input = null;
        while((input = br.readLine()) != null) {
            int n = Integer.parseInt(input);
            boolean[] flag = new boolean[arr[n]];
            cantor(flag,0, arr[n]);
            printFlag(flag, sb);
        }
        System.out.println(sb.toString());
    }

    private static void printFlag(boolean[] flag, StringBuffer sb) {
        for(boolean f : flag){
            if(f){
                sb.append(" ");
            }else{
                sb.append("-");
            }
        }
        sb.append("\n");
    }

    private static void cantor(boolean[] flag, int offset, int n) {
        if(n == 1) {
            return;
        }
        int len = n/3;

        cantor(flag, offset, len);
        for(int i =0; i<len; i++) {
            flag[offset + len + i] = true;
        }
        cantor(flag, offset + 2*len, len);
    }
}
