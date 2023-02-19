package p1406;

import java.util.ArrayList;

public class ArrayListEditor implements Editor{

    private ArrayList<Character> list = new ArrayList<>();
    private int cs = 0;

    public ArrayListEditor(String initStr) {
        if(initStr == null) initStr = "";
        for (char ch : initStr.toCharArray()) {
            add(ch);
        }
    }

    @Override
    public void add(char ch) {
           list.add(cs, ch);
           moveRight();
    }

    @Override
    public void moveLeft() {
        cs--;
        if(cs < 0) cs =0;
    }

    @Override
    public void moveRight() {
        cs++;
        if(cs > list.size()) cs = list.size();
    }

    @Override
    public void remove() {
        if(cs == 0)return;
        list.remove(cs-1);
        moveLeft();
    }

    @Override
    public String print() {
        StringBuffer sb = new StringBuffer();
        for (char c : list ) {
            sb.append(c);
        }
        return sb.toString();
    }
}
