package src.node;

import java.util.ArrayList;

/*
 * @author: Markco Backman
 * @email : roni2006@hanmail.net
 * Not fully implemented yet
 */

//A current node's generic type and children's generic type may mismatch
public class Node<E> {
  private E axis; //Specific type - this represents the axis
  private ArrayList<Object> originalData; //Original value - list of types may vary by element
  private boolean isRoot = false;
  private Node<Object> left = null; //Unknown generic type
  private Node<Object> right = null;//Unknown generic type
  private int degree;

  //list of types may vary by elements
  @SuppressWarnings("unchecked")
  Node(ArrayList<Object> originalData, int axisIndex) {
    this.originalData = originalData;
    originalData.get(axisIndex);
    this.axis = (E)originalData.get(axisIndex);
  }

  public void setRoot() {
    this.isRoot = true;
  }

  //Unknown generic type
  public void setLeftNode(Node<Object> leftNode) {
    this.left = leftNode;
  }

  //Unknown generic type
  public void setRightNode(Node<Object> rightNode) {
    this.right = rightNode;
  }

  public boolean isRoot() {
    return isRoot;
  }

  public boolean isLeaf() {
    if (left == null && right == null) {
      return true;
    } else {
      return false;
    }
  }

  public void setDegree(int degree) {
    this.degree = degree;
  }

  //list of types may vary by elements
  public ArrayList<Object> getOriginalData() {
    return originalData;
  }

   //Unknown generic type
  public Node<Object> getLeftNode() {
    return left;
  }

   //Unknown generic type
  public Node<Object> getRightNode() {
    return right;
  }

  public int getDegree() {
    return degree;
  }

  //used for main comparison
  public E getAxis() {
    return this.axis;
  }
}
