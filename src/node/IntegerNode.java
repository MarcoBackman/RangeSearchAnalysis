package src.node;

import java.util.ArrayList;

public class IntegerNode {
    IntegerNode next = null;
    ArrayList<Integer> list = new ArrayList<Integer>();

    public IntegerNode(ArrayList<Integer> list) {
        this.list = list;
    }

    private boolean checkLengthMatch(ArrayList<Integer> points) {
        if (list.size() != points.size()) {
        return false;
        } else {
        return true;
        }
    }

    //calculate based on euclidean distance
    public double calculateDistance(ArrayList<Integer> points) {
        double absoluteDistance = 0;
        if(!checkLengthMatch(points)) {
        //length mismatch error
        }
        for (int i = 0; i < points.size(); i++) {
        Integer tempListValue = list.get(i);
        Integer tempPointValue = points.get(i);
        try {
            absoluteDistance += (tempListValue - tempPointValue)
            * (tempListValue - tempPointValue);

            //will return negative value if overflowed
            if (absoluteDistance < 0) {
            throw new Exception();
            }
        } catch (Exception e) {
            System.out.println("Value overflow occoured: " + e);
        }
        absoluteDistance = Math.sqrt(absoluteDistance);
        }
        return absoluteDistance;
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
