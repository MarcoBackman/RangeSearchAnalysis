package src.datastructure;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;

import src.utils.ProcessTimeRecorder;

/*
 * @author: Markco Backman
 * @email : roni2006@hanmail.net
 */

public class KDTreeIntegers {
  IntegerTreeNode root;
  int deepestDepth = 0;
  ArrayList<ArrayList<Integer>> entireMatrix;
  public DataCarrier KNN_result;
  IntegerTreeNode shortestNode;
  double shortestDistance;

  //initializes, but doesn't construct the tree
  public KDTreeIntegers(ArrayList<ArrayList<Integer>> entireMatrix) {
    this.entireMatrix = new ArrayList<ArrayList<Integer>>(entireMatrix);
  }

  //constructs the tree
  public void buildKDTree() {
    insert(0, this.entireMatrix, null, null);
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
        return o1.get(axis).compareTo(o2.get(axis));
    }
  }

  //Must know the construction time - measuring required
  public void insert(int depth,
                     ArrayList<ArrayList<Integer>> particalMatrix,
                     IntegerTreeNode currentNode,
                     IntegerTreeNode parentNode) {

    Runtime runtime = Runtime.getRuntime();
    long usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();
    //initial case - root
    if (depth == 0) {
      currentNode = new IntegerTreeNode();
      root = currentNode;
    }

    //set deepest depth
    if (this.deepestDepth < depth) {
      this.deepestDepth = depth;
    }

    // Select axis based on depth so that axis cycles through all valid values
    int dimension = particalMatrix.get(0).size();
    int axis = depth % dimension; //axis

    //Sort point list and choose median as pivot element
    AxisSort comparator = new AxisSort(axis);
    particalMatrix.sort(comparator);
    int median = particalMatrix.size() / 2;

    //set parent value and omit from the list
    ArrayList<Integer> pointSegment
     = particalMatrix.get(median); //get median


    median = particalMatrix.size() / 2;

    currentNode.setParentNode(parentNode);
    currentNode.setAxis(axis);
    currentNode.setDepth(depth);
    currentNode.setPoints(pointSegment);
    
    //spliting matrix into two matrices !!!!! runs n times
    ArrayList<ArrayList<Integer>> leftList
      = new ArrayList<ArrayList<Integer>>
        (particalMatrix.subList(0, median));

    ArrayList<ArrayList<Integer>> rightList
      = new ArrayList<ArrayList<Integer>>
        (particalMatrix.subList(median + 1, particalMatrix.size()));

    if (leftList.size() != 0) {
      IntegerTreeNode leftChild = new IntegerTreeNode();
      currentNode.setLeftChild(leftChild);
      insert(depth + 1, leftList, leftChild, currentNode);
    }

    if (rightList.size() != 0) {
      IntegerTreeNode rightChild = new IntegerTreeNode();
      currentNode.setRightChild(rightChild);
      insert(depth + 1, rightList, rightChild, currentNode);
    }
    long usedMemoryAfter = runtime.totalMemory() - runtime.freeMemory();
    ProcessTimeRecorder.KDTreeSize += usedMemoryAfter-usedMemoryBefore;
  }

  public DataCarrier findKDTreeNormal(ArrayList<Integer> points) {
    //start from the root
    IntegerTreeNode traverse = root;
    IntegerTreeNode closestNode = root;
    double closestDistance = calculateDistance(points, traverse.getPoints());
    while (traverse.hasChild()) {
      //compare value by axis
      double distance = calculateDistance(points, traverse.getPoints());
      if (closestDistance > distance) {
        closestDistance = distance;
        closestNode = traverse;
      }
      int axis = traverse.getAxis();
      Integer valueToCompare = traverse.points.get(axis);
      if (points.get(axis) < valueToCompare) {
        if (traverse.leftChild == null)
          break;
        traverse = traverse.leftChild;
      } else {
        if (traverse.rightChild == null)
          break;
         traverse = traverse.rightChild;
      }
    }
    shortestNode = closestNode;
    shortestDistance = closestDistance;
    DataCarrier carrier = new DataCarrier(closestNode, closestDistance);
    KNN_result = carrier;
    return carrier;
  }


  //shortest node with current walking node
  //parameters must be the value from kdtree search
  //Must run this after running KD tree algorithm
  public void findKNNAlgorithm(ArrayList<Integer> targetPoints,
                               IntegerTreeNode walkingNode) {
    //check current node
    if (!walkingNode.isVisited()) {
      double currentDistance
      = calculateDistance(walkingNode.getPoints(), targetPoints);
      if(currentDistance < this.shortestDistance) {
        DataCarrier newDataCarrier = new DataCarrier(walkingNode, currentDistance);
        KNN_result = newDataCarrier;
        this.shortestNode = walkingNode;
        this.shortestDistance = currentDistance;
      }
    }
   
    //visitation set
    int comparingAxis = walkingNode.getAxis();
    walkingNode.setVisited();

    //left child check
    if (walkingNode.leftChild != null) {
      if (!walkingNode.leftChild.isVisited()) {
        findKNNAlgorithm(targetPoints, walkingNode.leftChild);
      }
    }

    //right child check
    if (walkingNode.rightChild != null) {
      if (!walkingNode.rightChild.isVisited()) {
        findKNNAlgorithm(targetPoints, walkingNode.rightChild);
      }
    }
    
    //root check before going into the parent
    if (walkingNode.parent != null) {
      Integer currentPointByAxis = walkingNode.getPoints().get(comparingAxis);
      Integer parentPointByAxis = walkingNode.parent.getPoints().get(comparingAxis);
      Integer targetPointByAxis = targetPoints.get(comparingAxis);
      double shortest_parent = getRSqured(currentPointByAxis, parentPointByAxis);
      double shortest_target = getRSqured(currentPointByAxis, targetPointByAxis);
      //check distance by axis
      if (shortest_target >= shortest_parent) {
        findKNNAlgorithm(targetPoints, walkingNode.parent);
      }
    }
  }

  private double calculateDistance(ArrayList<Integer> given,
                                   ArrayList<Integer> target) {
    Integer total = 0;
    for (int i = 0; i < given.size(); i++) {
      Integer givenElement = given.get(i);
      Integer targetElement = target.get(i);
      total += (targetElement - givenElement) * (targetElement - givenElement);
    }
    return Math.sqrt(total);
  }

  private double getRSqured(Integer a, Integer b) {
    int diff = a - b;
    double squared = diff * diff;
    double squareRoot = Math.sqrt(squared);
    return squareRoot;
  }

  public void printTree() {
    StringBuilder sb = new StringBuilder();
    printDFS(root, sb);
    System.out.println("Tree in DFS:\n" + sb.toString() + "\n");
    printBFS();
  }

  private void printDFS(IntegerTreeNode node, StringBuilder sb) {
    sb.append("{");
    if (node == null) {
      return;
    }
    sb.append(node.getDepth());
    sb.append(":");
    sb.append(node.getPoints().toString());
    printDFS(node.leftChild, sb);
    printDFS(node.rightChild, sb);
    sb.append("}");
    return;
  }

  private void printBFS() {
    Deque<IntegerTreeNode> queue = new ArrayDeque<IntegerTreeNode>();
    Deque<IntegerTreeNode> bfsQueue = new ArrayDeque<IntegerTreeNode>();
    queue.add(root);
    StringBuilder sb = new StringBuilder();
    sb.append("Tree in BFS:\n");
    while(!queue.isEmpty()) {
      IntegerTreeNode tempNode = queue.removeLast();
      bfsQueue.addLast(tempNode);
      sb.append(tempNode.depth);
      sb.append(":");
      sb.append(tempNode.getPoints().toString());
      sb.append(" ");
      if (tempNode.leftChild != null) {
        queue.addFirst(tempNode.leftChild);
      }
      if (tempNode.rightChild != null) {
        queue.addFirst(tempNode.rightChild);
      }
    }
    //System.out.println(sb.toString());
    printBFSTree(bfsQueue);
  }

  private void printBFSTree(Deque<IntegerTreeNode> list) {
    StringBuilder sb = new StringBuilder();
    int currentDepth = 0;
    while(!list.isEmpty()) {
      IntegerTreeNode tempNode = list.removeFirst();
      if (currentDepth != tempNode.getDepth()) {
        sb.append("\n");
        currentDepth = tempNode.getDepth();
      }
      if (!tempNode.hasChild()) {
        sb.append("parent:");
        sb.append(tempNode.parent.getPoints().toString());
        sb.append("/");
      } 
      sb.append(tempNode.getPoints().toString());
      sb.append(" - ");
    }
    System.out.println(sb.toString() + "\n");
  }

  public class IntegerTreeNode {
    IntegerTreeNode parent;
    IntegerTreeNode current;
    ArrayList<Integer> points;
    IntegerTreeNode leftChild;
    IntegerTreeNode rightChild;
    int axisIndex;
    int depth; //depth of the tree
    boolean visited;

    public void setPoints(ArrayList<Integer> points) {
      this.points = points;
    }

    public ArrayList<Integer> getPoints() {
      return this.points;
    }

    public IntegerTreeNode getParentNode() {
      return this.parent;
    }

    public void setDepth(int depth) {
      this.depth = depth;
    }

    public int getDepth() {
      return this.depth;
    }

    public boolean hasChild() {
      if (leftChild == null && rightChild == null) {
        return false;
      }
      return true;
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
      this.rightChild = rightChild;
    }

    public IntegerTreeNode getRightChild() {
      return rightChild;
    }

    public void setParentNode(IntegerTreeNode parent) {
      this.parent = parent;
    }

    public void setVisited() {
      this.visited = true;
    }

    public boolean isVisited() {
      return this.visited;
    }

  }
}
