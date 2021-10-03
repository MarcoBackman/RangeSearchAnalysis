package src.datastructure;

import java.util.ArrayList;

import src.datastructure.KDTreeIntegers.IntegerTreeNode;
import src.node.IntegerNode;

 /*
 * @author: Marco Backman
 * @email : roni2006@hanmail.net
 */

public class DataCarrier {
    IntegerTreeNode treeNode;
    IntegerNode resultNode;
    double distance;
    int depth; //depth is 0 on Sequential list

    public DataCarrier(IntegerNode node, double distance) {
        this.resultNode = node;
        this.distance = distance;
    }

    public DataCarrier(IntegerTreeNode node, double distance) {
        //make tree node to node
        IntegerNode newIntegerNode = new IntegerNode(node.points);
        this.depth = node.getDepth();
        this.treeNode = node;
        this.resultNode = newIntegerNode;
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
        return this.resultNode.toString() + " in distance " + this.distance + "\n";
    }

    public void setIntegerNode(IntegerNode resultNode) {
        this.resultNode = resultNode;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public IntegerTreeNode getTreeNode() {
        return this.treeNode;
    }

    public ArrayList<Integer> getPoints() {
        return this.resultNode.getPoints();
    }

    public IntegerTreeNode getParentNode() {
        return this.treeNode.getParentNode();
    }
}
