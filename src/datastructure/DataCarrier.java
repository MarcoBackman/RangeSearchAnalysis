package src.datastructure;

public class DataCarrier {
    IntegerNode resultNode;
    double distance;

    public DataCarrier(IntegerNode node, double distance) {
        this.resultNode = node;
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
