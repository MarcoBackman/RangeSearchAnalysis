package src.datastructure;

import java.util.ArrayList;

import src.utils.ProcessTimeRecorder;

/*
 * @author: Marco Backman
 * @email : roni2006@hanmail.net
 */

public class SequentialAlgorithm {
  IntegerNode start;
  IntegerNode end;
  int size = 0;

  public int getSize() {
    return size;
  }

  public void insert(ArrayList<Integer> list) {
    long insertTimeStart = System.nanoTime();
    IntegerNode newNode = new IntegerNode(list);
    if (size == 0) {
      start = newNode;
      end = newNode;
      ++size;
    } else if (size == 1) {
      end = newNode;
      start.setNext(newNode);
      ++size;
    } else {
      end.setNext(newNode);
      end = newNode;
      ++size;
    }
    ProcessTimeRecorder.sequentialConstructionTime += System.nanoTime() - insertTimeStart;
  }

  public void iterateAndPrint() {
    IntegerNode walk = start;
    while (walk != null) {
      System.out.println(walk.getPoints().toString());
      walk = walk.getNext();
    }
  }

  public DataCarrier findCloestDistance(ArrayList<Integer> targetPoint) {
    long searchTimeStart = System.nanoTime();
    double closestDistance = -1;
    IntegerNode resultNode = null;
    IntegerNode walk = start;
    while (walk != null) {
      double retreivedDistance = -1;
      try {
        retreivedDistance = walk.calculateDistance(targetPoint);
      } catch (Exception e) {
        e.printStackTrace();
      }
      if (closestDistance == -1) { //initial swap
        closestDistance = retreivedDistance;
        resultNode = walk;
      } else if (closestDistance > retreivedDistance) {
        closestDistance = retreivedDistance;
        resultNode = walk;
      }
      walk = walk.getNext();
    }
    ProcessTimeRecorder.sequentialSearchTime += System.nanoTime() - searchTimeStart;

    DataCarrier carrier
      = new DataCarrier(resultNode, closestDistance);
    if (resultNode == null) {
      System.out.println("Unexpected error occured");
    } else {
      System.out.println("Closest distance is: " + closestDistance);
      System.out.println("Node info: \n" + resultNode.getPoints().toString());
    }

    return carrier;
  }
}
