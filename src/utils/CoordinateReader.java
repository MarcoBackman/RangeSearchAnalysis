package src.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

/*
 * @author: Marco Backman
 * @email : roni2006@hanmail.net
 */

public class CoordinateReader {
  int dimension = -1;
  ArrayList<ArrayList<Integer>> entireMatrix;

  //returns the entire matrix of the points
  public void readPointsFromFile(String fileDirectory) {
    try {
      entireMatrix = new ArrayList<ArrayList<Integer>>();
      File file = new File(fileDirectory);
      FileReader fr = new FileReader(file);
      
      try(BufferedReader br = new BufferedReader(fr)) {
        for(String line; (line = br.readLine()) != null;) {
          ArrayList<Integer> points = new ArrayList<Integer>();
          String[] values = line.split(" ");
          if (dimension == -1) {
            dimension = values.length;
          } else if (dimension != values.length) {
            System.out.println("Warning, point mistach: " + values.toString());
          }
          //sumup points
          for (int i = 0; i < values.length; i++) {
            points.add(Integer.parseInt(values[i]));
          }
          entireMatrix.add(points);
        }
        // line is not visible here.
      } catch (Exception e) {
        e.printStackTrace();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public int getDimension() {
    return this.dimension;
  }

  public ArrayList<ArrayList<Integer>> getEntireMatrix() {
    return this.entireMatrix;
  }
}
