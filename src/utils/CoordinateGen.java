package src.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/*
 * Generates dimensional data
 *   - Saves data into a file. 
 *   - for 2d dimentioanl data - ex) "./data/coordinate2D.dat"
 */
public class CoordinateGen {
  final int MAX_DIMENSION = 4;
  final String FILE_PATH = "./data/auto_generated_coord.dat";
  final File file = new File(FILE_PATH);
  final Scanner scanner = new Scanner(System.in);

  private void destructor() {
    scanner.close();
  }

  //param select dimential cooridination from 1 to 4
  private int selectDimension() {
    System.out.println("Enter dimension index from 1 to 4");
    String line = scanner.nextLine();
    String[] line_arr = line.split(" ");

    if (line_arr.length > 1) {
      selectDimension();
    }

    int dimension = -1;

    try {
      dimension = Integer.parseInt(line_arr[0]);
    } catch(Error err) {
      System.out.println("Parse error: " + err);
      return -1;
    }

    if (dimension > MAX_DIMENSION) {
      selectDimension();
    }

    return dimension;
  }

  //all ranges are identical
  private int[] selectRange() {
    System.out.println("Enter number range: from(integer): to(integer)");
    String line = scanner.nextLine();
    String[] line_arr = line.split(" ");

    if (line_arr.length != 2) {
      System.out.println("array length not matched: " + line_arr[0] + line_arr.length);
      selectRange();
    }

    int[] dimension = new int[2];

    try {
      dimension[0] = Integer.parseInt(line_arr[0]);
      dimension[1] = Integer.parseInt(line_arr[1]);
    } catch(Exception err) {
      System.out.println("String to integer parse error: " + err);
      return null;
    }

    return dimension;
  }

  private int selectNumPoints() {
    System.out.println("Enter number of points: (integer)points");
    String line = scanner.nextLine();
    String[] line_arr = line.split(" ", 1);

    if (line_arr.length > 1) {
      selectNumPoints();
    }

    int point_amount = -1;

    try {
      point_amount = Integer.parseInt(line_arr[0]);
    } catch(Error err) {
      System.out.println("Parse error: " + err);
      return -1;
    }

    return point_amount;

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
      FileWriter fw = new FileWriter(file, StandardCharsets.UTF_8); //true for appending
      BufferedWriter writer = new BufferedWriter(fw);
      writer.write("");
          //generate points and save
      for (int i = 0; i < point_amount; i++) {
        int[] points = generatePoint(dimension, range);
        savePointsToFile(points, writer);
      }

      writer.close();
      fw.close();
    } catch (Exception e) {
      System.out.println("File locating failed: " + e);
    }
    
  }

  //main method being called6
  public void promptInput() {
    //dimension 1 ~ 4
    int dimension = selectDimension();
    errorCheck(dimension);
    //from [0] to [1]
    int[] range = selectRange();
    errorCheck(range);
    //number of points
    int point_amount = selectNumPoints();
    errorCheck(point_amount);
    //file check

    //generate and save data to a file
    generatePointsAndSave(point_amount, dimension, range);
    destructor();
  }
}
