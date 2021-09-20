package src.datastructure;

import src.datastructure.KDTreeIntegers.IntegerTreeNode;

public class DataCarrier {
    IntegerNode resultNode;
    double distance;
    int depth; //depth is 0 on Sequential list

    public DataCarrier(IntegerNode node, double distance) {
        this.resultNode = node;
        this.distance = distance;
    }

    public DataCarrier(IntegerTreeNode node, double distance) {
        //make tree node to node
        IntegerNode newNode = new IntegerNode(node.points);
        this.depth = node.getDepth();
        this.resultNode = newNode;
        this.distance = distance;
    }

    public IntegerNode getNode() {
        return resultNode;
    }

    public double getDistance() {
        return distance;
    }

    public String getNodeToString() {
        return resultNode.toString();
    }

    public String toString() {
        return "Closest node: " + this.resultNode.toString() + " in distance " + this.distance + "\n";
    }

    public void setIntegerNode(IntegerNode resultNode) {
        this.resultNode = resultNode;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
