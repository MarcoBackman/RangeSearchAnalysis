package src.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/*
 * @author      : Marco Backman
 * @email       : roni2006@hanmail.net
 * @Description : Generates dimensional data
 *   - Saves data into a file. 
 *       for 2d dimentioanl data - ex) "./data/coordinate2D.dat"
 *   - This will override every existing content.
 */
public class CoordinateGen {
  final int MAX_DIMENSION = 4;
  File file;

  public CoordinateGen(String fileDirectory) {
    file = new File(fileDirectory);
  }

  private void createFile (File file) throws IOException {
    try {
      if (!file.getParentFile().exists()) {
        file.getParentFile().mkdirs();
        System.out.println("Creating path");
      }
      if (!file.exists()){
        System.out.println("Creating file");
        file.createNewFile();
      }
    } catch (Exception e) {
      System.out.println("Error on file/path creation" + e);
    }
  }

  private void errorCheck(Object element) {
    if (element == null) {
      System.out.print("System aborting\n");
      System.exit(0);
    }
  }

  private void errorCheck(int index) {
    if (index == -1) {
      System.out.print("System aborting\n");
      System.exit(0);
    }
  }

  //duplication not concerned
  private int[] generatePoint(int dimension, int[] range) {
    int from = range[0];
    int to = range[1];
    int[] points = new int[dimension];
    for (int i = 0; i < dimension; i++) {
      points[i] = ((int)(Math.random() * (to - from)) + from);
    }
    return points;
  }

  private void savePointsToFile(int[] points, BufferedWriter writer) {
    try {
      for (int index = 0; index < points.length; index++) {
        if (index == points.length - 1) {
          writer.append("" + points[index]);
        } else {
          writer.append("" + points[index] + " ");
        }
      }
      writer.newLine();
    } catch(Exception e) {
      System.out.println("File writer instanciation error: " + e);
      return;
    }
  }

  private void generatePointsAndSave(int point_amount,
                                     int dimension,
                                     int[] range) {
    //false for not appending
    try {
      createFile(this.file);
       //true for appending
      FileWriter fw = new FileWriter(file, StandardCharsets.UTF_8);
      BufferedWriter writer = new BufferedWriter(fw);
      writer.write("");
      //generate points and save
      for (int i = 0; i < point_amount; i++) {
        // ex) generates [12, 24] for 2 dimension
        int[] points = generatePoint(dimension, range);
        savePointsToFile(points, writer);
      }

      writer.close();
      fw.close();
    } catch (Exception e) {
      System.out.println("File locating failed: " + e);
    }
    
  }

  public void automatedGenerator(int dimension,
                                 int rangeFrom,
                                 int rangeTo,
                                 int point_amount) {
    //dimension 1 ~
    errorCheck(dimension);
    //from [0] to [1]
    int[] range = {rangeFrom, rangeTo};
    errorCheck(range);
    //number of points
    errorCheck(point_amount);


    //generate and save data to a file
    generatePointsAndSave(point_amount, dimension, range);
  }
}
