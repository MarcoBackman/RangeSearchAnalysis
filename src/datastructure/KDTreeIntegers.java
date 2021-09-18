package src.datastructure;

import java.util.ArrayList;
import java.util.Comparator;

/*
 * @author: Markco Backman
 * @email : roni2006@hanmail.net
 */

public class KDTreeIntegers {
  IntegerTreeNode root;
  int deepestDepth = 0;
  ArrayList<ArrayList<Integer>> entireMatrix;

  //initializes, but doesn't construct the tree
  public KDTreeIntegers(ArrayList<ArrayList<Integer>> entireMatrix) {
    this.entireMatrix = entireMatrix;
  }

  //constructs the tree
  public void buildKDTree() {
    System.out.println("Construction start");
    insert(0, this.entireMatrix, null);
    System.out.println("Done, deepest depth is: "
     + deepestDepth + " for array size: " + entireMatrix.size());
  }

  //Must know the construction time - measuring required
  public void insert(int depth,
                     ArrayList<ArrayList<Integer>> particalMatrix,
                     IntegerTreeNode parentNode) {
    //termination condition
    if (particalMatrix == null) {
      return;
    }
    if (particalMatrix.size() == 0) {
      return;
    }
    if (this.deepestDepth < depth) {
      this.deepestDepth = depth;
    }

    //initial case - root
    if (parentNode == null && depth == 0) {
      parentNode = new IntegerTreeNode();
      root = parentNode;
    }

    // Select axis based on depth so that axis cycles through all valid values
    int dimension = particalMatrix.get(0).size();
    int axis = depth % dimension; //axis

    //Sort point list and choose median as pivot element
    //  !!!!! must take account of the sorting time
    AxisSort comparator = new AxisSort(axis);
    particalMatrix.sort(comparator);
    int median = particalMatrix.size() / 2;
    ArrayList<Integer> pointSegment
     = particalMatrix.get(median); //get median
    parentNode.setPoints(pointSegment);

    //spliting matrix into two matrices !!!!! runs n times
    ArrayList<ArrayList<Integer>> leftList
     = new ArrayList<ArrayList<Integer>>();
    ArrayList<ArrayList<Integer>> rightList
     = new ArrayList<ArrayList<Integer>>();
    for (int i = 0; i < particalMatrix.size(); i++) {
      if (i < median) {
        leftList.add(particalMatrix.get(i));
      } else {
        rightList.add(particalMatrix.get(i));
      }
    }

    //make left child and connect recursively
    IntegerTreeNode leftChild = new IntegerTreeNode();
    leftChild.setDepth(depth + 1);
    parentNode.setLeftChild(leftChild);
    insert(depth + 1, leftList, leftChild);

    //make right child and connect recursively
    IntegerTreeNode rightChild = new IntegerTreeNode();
    rightChild.setDepth(depth + 1);
    parentNode.setRightChild(rightChild);
    insert(depth + 1, leftList, rightChild);
  }

  //sort by the dimension axis of the ArrayList
  public class AxisSort implements Comparator<ArrayList<Integer>> 
  {
    int axis;
    AxisSort(int axis) {
      this.axis = axis;
    }

    @Override
    public int compare(ArrayList<Integer> o1, ArrayList<Integer> o2) {
        return o2.get(axis).compareTo(o1.get(axis));
    }
  }

  public DataCarrier find(ArrayList points) {

    return null;
  }

  public class IntegerTreeNode {
    ArrayList<Integer> points;
    IntegerTreeNode leftChild;
    IntegerTreeNode rightChild;
    int axisIndex;
    int depth; //depth of the tree

    public void setPoints(ArrayList<Integer> points) {
      this.points = points;
    }

    public ArrayList<Integer> getPoints() {
      return this.points;
    }

    public void setDepth(int depth) {
      this.depth = depth;
    }

    public int getDepth() {
      return this.depth;
    }

    public void setAxis(int axisIndex) {
      this.axisIndex = axisIndex;
    }

    public int getAxis() {
      return this.axisIndex;
    }

    public void setLeftChild(IntegerTreeNode leftChild) {
      this.leftChild = leftChild;
    }

    public IntegerTreeNode getLeftChild() {
      return leftChild;
    }

    public void setRightChild(IntegerTreeNode rightChild) {
      this.rightChild = leftChild;
    }

    public IntegerTreeNode getRightChild() {
      return rightChild;
    }
  }
}
