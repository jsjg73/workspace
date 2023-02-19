package p1406;

public class CustomEditor implements Editor{
    private CharNode cursor;
    private CharNode root;

    public CustomEditor(String initStr) {
        CharNode head = CharNode.head();
        CharNode tail = CharNode.tail();
        head.right = tail;
        tail.left = head;
        cursor = root = head;
        if(initStr == null) initStr = "";
        for (char ch : initStr.toCharArray()) {
            add(ch);
        }
    }

    @Override
    public void add(char ch) {
        cursor.addNext(new CharNode(ch));
        moveRight();
    }

    @Override
    public void moveLeft() {
        if(!cursor.isHead()){
            cursor = cursor.left;
        }
    }

    @Override
    public void moveRight() {
        if(!cursor.right.isTail()){
            cursor = cursor.right;
        }
    }

    @Override
    public void remove() {
        if(!cursor.isHead()){
            cursor.remove();
            moveLeft();
        }
    }

    @Override
    public String print() {
        CharNode curr = root.right;
        StringBuffer sb = new StringBuffer();
        while(!curr.isTail()){
            sb.append(curr.getC());
            curr = curr.right;
        }
        return sb.toString();
    }

    static class CharNode {
        private char c;
        private CharNode left;
        private CharNode right;

        public CharNode(char c){
            this.c = c;
        }

        public static CharNode head() {
            return new CharNode('^');
        }

        public static CharNode tail() {
            return new CharNode('$');
        }

        public void addNext(CharNode next){
            if(next == null) {
                throw new IllegalArgumentException("not null");
            }
            right.left = next;
            next.right = right;

            next.left = this;
            this.right = next;
        }

        public void remove(){
            if(isHead()){
                throw new IllegalStateException("you can not remove head");
            }
            left.right = right;
            right.left = left;
        }

        public char getC() {
            return c;
        }

        public boolean isHead(){
            return c == '^';
        }

        public boolean isTail() {
            return c == '$';
        }
    }
}
