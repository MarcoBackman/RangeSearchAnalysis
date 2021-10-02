package src.datastructure;

import java.util.ArrayList;

import src.node.IntegerNode;
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

  public void buildList (ArrayList<ArrayList<Integer>> matrix) {
    for (ArrayList<Integer> list : matrix) {
      insert(list);
    }
  }

  public void insert(ArrayList<Integer> list) {
    Runtime runtime = Runtime.getRuntime();
    long insertTimeStart = System.nanoTime();
    long usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();
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
    long usedMemoryAfter = runtime.totalMemory() - runtime.freeMemory();
    ProcessTimeRecorder.sequentialSize += usedMemoryAfter - usedMemoryBefore;
  }

  public void iterateAndPrint() {
    IntegerNode walk = start;
    while (walk != null) {
      System.out.println(walk.getPoints().toString());
      walk = walk.getNext();
    }
  }

  public boolean compareTwoPoints(ArrayList<Integer> a, ArrayList<Integer> b) {
    for (int i = 0; i < a.size(); i++) {
      if (!a.get(i).equals(b.get(i))) {
        return false;
      }
    }
    return true;
  }

  public IntegerNode findMatch(ArrayList<Integer> targetPoints) {
    IntegerNode resultNode = null;
    IntegerNode walk = start;
    //dimension mismatch!
    if (targetPoints.size() != walk.getPoints().size()) {
      System.out.println("Dimension error!");
      return null;
    }

    while (walk != null) {
      //compares all points
      if (compareTwoPoints(targetPoints, walk.getPoints())) {
        resultNode = walk;
        break;
      }
      walk = walk.getNext();
    }
    return resultNode;
  }

  public DataCarrier findCloestDistance(ArrayList<Integer> targetPoints) {
    long searchTimeStart = System.nanoTime();
    double closestDistance = -1;
    IntegerNode resultNode = null;
    IntegerNode walk = start;
    while (walk != null) {
      double retreivedDistance = -1;
      retreivedDistance = calculateDistance(walk.getPoints(), targetPoints);
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
    return carrier;
  }

  public double calculateDistance (ArrayList<Integer> desiredPoint,
                                   ArrayList<Integer> comparingPoint) {
    double absoluteDistance = 0;
    if(!checkLengthMatch(desiredPoint, comparingPoint)) {
      //length mismatch error
    }
    for (int i = 0; i < desiredPoint.size(); i++) {
      Integer tempListValue = desiredPoint.get(i);
      Integer tempPointValue = comparingPoint.get(i);
      try {
          int subtractedValue = tempListValue - tempPointValue;
          absoluteDistance += subtractedValue * subtractedValue;
          //will return negative value if overflowed
          if (absoluteDistance < 0) {
            throw new Exception();
          }
      } catch (Exception e) {
        System.out.println("Value overflow occoured: " + e);
      }
    }
    absoluteDistance = Math.sqrt(absoluteDistance);
    return absoluteDistance;
  }

  private boolean checkLengthMatch(ArrayList<Integer> first_points,
                                   ArrayList<Integer> second_points) {
    if (first_points.size() != second_points.size()) {
    return false;
    }
    return true;
  }

  public void print() {
    IntegerNode walk = start;
    while (walk != null) {
      //compares all points
      System.out.print(walk.getPoints().toString() + ", ");
      walk = walk.getNext();
    }
    System.out.println("");
  }

  public void print(ArrayList<Integer> points) {
    IntegerNode walk = start;
    //dimension mismatch!
    if (points.size() != walk.getPoints().size()) {
      System.out.println("Dimension error!");
      return;
    }

    while (walk != null) {
      //compares all points
      if (compareTwoPoints(points, walk.getPoints())) {
        System.out.print("---" + walk.getPoints().toString() + "---, ");
      } else {
        System.out.print(walk.getPoints().toString() + ", ");
      }
      walk = walk.getNext();
    }
    System.out.println("");
  }
}
