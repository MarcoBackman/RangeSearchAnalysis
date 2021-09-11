/*
package src.node;

import java.util.ArrayList;
//T for the dimenstion or type
public class Node<T extends Integer, V extends ArrayList> {
  //primary content
  private T type;
  private ArrayList<V> values;
  //Node connection for tree - can be formed into a linked list if every node has one child
  private Node<T, V> parent;
  private ArrayList<Node<T, V>> children;

  //constructors
  public Node(ArrayList<V> values) {
    System.out.println(this.type);
    this.parent = null;
    this.values = (ArrayList<V>)values;
  }

  Node(T type, ArrayList<V> values, Node<T, V> parent) {
    this.parent = parent;
    this.type = type;
    this.values = values;
  }

  //getter

  public T getType() {
    return this.type;
  }

  public ArrayList<V> getValues() {
    return this.values;
  }

  public Node<T, V> getParent() {
    return parent;
  }

  //get children who has the same value
  public ArrayList<Node<T, V>> getChild(V value) {
    return children;
  }

  public Node<T, V> getFirstChild() {
    if (children.isEmpty())
      return null;
    return children.get(0);
  }

  public ArrayList<Node<T, V>> getChildren() {
    return children;
  }

  //setter

  public void setType(T type) {
    this.type = type;
  }

  public void setValues(ArrayList<V> values) {
    this.values = values;
  }
  
  public void setParent(Node<T, V> parent) {
    this.parent = parent;
  }

  public void setChildren(ArrayList<Node<T, V>> children) {
    this.children = children;
  }

  public void setChild(Node<T, V> child) {
    this.children.add(child);
  }
}

*/