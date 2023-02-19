package p1406;

public class CharNode {
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
