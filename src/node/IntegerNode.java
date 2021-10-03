package src.node;

import java.util.ArrayList;

 /*
 * @author: Marco Backman
 * @email : roni2006@hanmail.net
 */

public class IntegerNode {
    IntegerNode next = null;
    ArrayList<Integer> list = new ArrayList<Integer>();

    public IntegerNode(ArrayList<Integer> list) {
        this.list = list;
    }

    public void setNext(IntegerNode next) {
        this.next = next;
    }

    public IntegerNode getNext() {
        return next;
    }

    public ArrayList<Integer> getPoints() {
        return this.list;
    }

    @Override
    public String toString() {
        return this.list.toString();
    }
}
