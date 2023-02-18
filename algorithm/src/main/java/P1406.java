import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class P1406 extends InputHelper{
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(fis()));
        Editor editor = new Editor(br.readLine());
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
    }

    static class Editor {
        private CharNode cursor;
        private CharNode root;


        public Editor(String initStr) {
            if(initStr == null) initStr = "";
            for (char ch : initStr.toCharArray()) {
                add(ch);
            }
        }

        public void add(char ch) {
            if(cursor == null) {
                cursor = new CharNode(ch);
                if(root != null){
                    cursor.addNext(root);
                }
                root = cursor;
            }else {
                CharNode next = new CharNode(ch);
                cursor.addNext(next);
                cursor = next;
            }
        }

        public void moveLeft(){
            if(cursor == null)return;
            cursor = cursor.left;
        }

        public void moveRight(){
            if(cursor == null) {
                cursor = root;
            }else if(cursor.right != null){
                cursor = cursor.right;
            }
        }

        public void remove(){
            if(cursor == null)return;
            cursor.remove();
            if(cursor.left == null) {
                this.root = cursor.right;
            }
            cursor = cursor.left;

        }

        public String print(){
            CharNode curr = root;
            StringBuffer sb = new StringBuffer();
            while(curr != null){
                sb.append(curr.getC());
                curr = curr.right;
            }
            return sb.toString();
        }
    }

    static class CharNode {
        private char c;
        private CharNode left;
        private CharNode right;

        public CharNode(char c){
            this.c = c;
        }

        public void addNext(CharNode next){
            if(next == null) {
                throw new IllegalArgumentException("not null");
            }
            if(this.right != null) {
                right.left = next;
                next.right = right;
            }
            next.left = this;
            this.right = next;
        }

        public void remove(){
            if(left != null) {
                left.right = right;
            }
            if(right!= null) {
                right.left = left;
            }
        }

        public char getC() {
            return c;
        }

        public boolean isRoot(){
            return left == null;
        }
    }
}
