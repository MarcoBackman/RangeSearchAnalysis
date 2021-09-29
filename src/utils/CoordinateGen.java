package src.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/*
 * @author      : Marco Backman
 * @email       : roni2006@hanmail.net
 * @Description : Generates dimensional data
 *   - Saves data into a file. 
 */
public class CoordinateGen {
  final int SMALL_RANGE_MULTIPLIER = 2;
  final int SMALL_TOTAL_CASE_TRIES = 5;
  final int SMALL_POINT_MULTIPLIER = 2;

  final int BIG_LENGTH_MULTIPLIER = 2;
  final int BIG_POINT_MULTIPLIER = 2;
  final int BIG_INITIAL_NUMBER_OF_POINTS = 10;
  final int BIG_TOTAL_CASE_TRIES = 7;

  boolean inOrder;
  boolean smallTestCase;
  File file;
  BufferedWriter writer;
  FileWriter fw;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    CoordinateGen coordGenInst = new CoordinateGen();
    //prompt user input
    coordGenInst.userInputPoints(sc);
    sc.close();
  }

  private void userInputPoints(Scanner sc) {
    //user input - fixed - number of points refer to the dimesion
    System.out.println("Enter dimension");
    String inputLine = sc.nextLine();
    String[] strArr = inputLine.split(" ");
    if (strArr.length > 1) {
      System.out.println("Enter one integer");
      userInputPoints(sc);
    }
    Integer dimension = Integer.parseInt(strArr[0]);

    //choose small/big caese
    System.out.println("Small test case? Yes: 1, No : 0");
    inputLine = sc.nextLine();
    strArr = inputLine.split(" ");
    if (strArr.length > 1) {
      System.out.println("Enter one integer");
      userInputPoints(sc);
    }
    Integer caseSize = Integer.parseInt(strArr[0]);
    if (caseSize == 0) { //big test case
      generateSmallCases(dimension);
    } else if (caseSize == 1) { //small test case
      generateHugeCases(dimension);
    } else {
      System.out.println("Wrong Input");
      userInputPoints(sc);
    }
    return;
  }

  //range [to, from]
  private int[] generatePoint(int dimension, int from, int to) {
    int[] points = new int[dimension];
    for (int i = 0; i < dimension; i++) {
      points[i] = ((int)(Math.random() * (to - from)) + from);
    }
    return points;
  }

  private void generateSmallCases(int dimension) {
    for (int caseNumber = 1; caseNumber <= SMALL_TOTAL_CASE_TRIES; caseNumber++) {
      file = new File("./data/SMALL_DATA_CASE" + (caseNumber + 1)
        + "_" + (10 * caseNumber) + ".txt");
      try {
        createFile(file);

        fw = new FileWriter(file, StandardCharsets.UTF_8);
        BufferedWriter writer = new BufferedWriter(fw);
        writer.write(""); //empty file

        for (int points = 0; points < 10 * caseNumber; points++) {
          savePointsToFile(generatePoint(dimension, 1, 10 * caseNumber), writer);
        }
      } catch(Exception e) {
        e.printStackTrace();
      }
    }
  }

  private void generateHugeCases(int dimension) {
    for (int caseNumber = 1; caseNumber <= BIG_TOTAL_CASE_TRIES; caseNumber++) {
      file = new File("./data/SMALL_DATA_CASE" + (caseNumber + 1)
        + "_" + (10 * caseNumber));
      try {
        createFile(file);
        fw = new FileWriter(file, StandardCharsets.UTF_8);
        BufferedWriter writer = new BufferedWriter(fw);
        writer.write(""); //empty file
        fw = new FileWriter(file, true);
        writer = new BufferedWriter(fw);
        int multiplier = (int) Math.pow(2, caseNumber);
        for (int points = 0; points <  multiplier * 10; points++) {
          savePointsToFile(generatePoint(dimension, 1, 10 * caseNumber), writer);
        }
        writer.flush();
      } catch(Exception e) {
        System.out.println(e);
      }
    }
  }

  // Only creates the file if not exist. Overrides the data when file exist
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
}
