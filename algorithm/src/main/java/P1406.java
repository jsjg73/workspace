import p1406.CustomEditor;
import p1406.Editor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class P1406 extends InputHelper{
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader br = new BufferedReader(new InputStreamReader(fis()));
        Editor editor = new CustomEditor(br.readLine());
        int n = Integer.parseInt(br.readLine());
        while(n-- > 0) {
            String command = br.readLine();
            char charAt = command.charAt(0);
            if(charAt == 'L'){
                editor.moveLeft();
            }else if(charAt == 'D') {
                editor.moveRight();
            }else if(charAt == 'B'){
                editor.remove();
            }else{
                char ch = command.charAt(2);
                editor.add(ch);
            }
        }
        System.out.println(editor.print());
        long end = System.currentTimeMillis();
        System.out.println();
        System.out.println();
        System.out.println(end - start);
    }
}
