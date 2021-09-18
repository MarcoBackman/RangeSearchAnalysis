package src.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import src.datastructure.SequentialAlgorithm;

/*
 * @author: Marco Backman
 * @email : roni2006@hanmail.net
 */

public class CoordinateReader {
  int dimension = 0;
  SequentialAlgorithm sequence;
  ArrayList<ArrayList<Integer>> entireMatrix;

  public CoordinateReader(SequentialAlgorithm sequence) {
    this.sequence = sequence;
  }

  public void createFile (File file) throws IOException { 
    file.createNewFile();
  }

  public SequentialAlgorithm extractAndInsterPoints(String fileDirectory) {
    try {
      entireMatrix = new ArrayList<ArrayList<Integer>>();
      File file = new File(fileDirectory);
      if (!file.exists()) {
        System.out.println("Making files... " + file);
        createFile(file);
      }
      FileReader fr = new FileReader(file);
      
      try(BufferedReader br = new BufferedReader(fr)) {
        for(String line; (line = br.readLine()) != null;) {
          ArrayList<Integer> points = new ArrayList<Integer>();
          String[] values = line.split(" ");
          if (dimension == 0) {
            dimension = values.length;
          } else if (dimension != values.length) {
            System.out.println("Warning, point mistach: " + values.toString());
          }
          //sumup points
          for (int i = 0; i < values.length; i++) {
            points.add(Integer.parseInt(values[i]));
          }
          entireMatrix.add(points);
          sequence.insert(points);
        }
        // line is not visible here.
      } catch (Exception e) {
        e.printStackTrace();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return sequence;
  }

  public int getDimension() {
    return this.dimension;
  }

  public ArrayList<ArrayList<Integer>> getEntireMatrix() {
    return this.entireMatrix;
  }
}
