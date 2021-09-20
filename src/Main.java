package src;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import src.datastructure.DataCarrier;
import src.datastructure.KDTreeIntegers;
import src.datastructure.SequentialAlgorithm;
import src.utils.CoordinateGen;
import src.utils.CoordinateReader;
import src.utils.ExcelExport;
import src.utils.ProcessTimeRecorder;

 /*
 * @author: Marco Backman
 * @email : roni2006@hanmail.net
 */


 
class Main {
  //Range over 4 produces integer representation overflow
  final int REPEAT_CASE = 2;
  final int RANGE_MULTIPLIER = 4;
  final int POINT_MULTIPLIER = 6;
  final String EXCEL_FILE_NAME = "./data/dataProfile.txt";
  File file = new File(EXCEL_FILE_NAME);
  ExcelExport excelExport = new ExcelExport();
  SequentialAlgorithm sequential;

  public static void main (String[] args) {
    Scanner scanner = new Scanner(System.in);
    Main mainInst = new Main();
    ArrayList<Integer> inputList
     = mainInst.userInputPoints(scanner);
    //run evalutation based on the given point
    System.out.println("Number of repeatitive tries: " + mainInst.REPEAT_CASE);
    for (int i = 0; i < mainInst.REPEAT_CASE; i++) {
      mainInst.runMultipleTimes(inputList);
    }
    scanner.close();
  }

  private ArrayList<Integer> userInputPoints(Scanner sc) {
    //user input - fixed - number of points refer to the dimesion
    System.out.println("Enter desired points");
    String inputLine = sc.nextLine();
    String[] inputStringArr = inputLine.split(" ");
    ArrayList<Integer> inputPoints = new ArrayList<Integer>();
    //
    for (int i = 0; i < inputStringArr.length; i++) {
      inputPoints.add(Integer.parseInt(inputStringArr[i]));
    }
    return inputPoints;
  }

  private void createNewExcelFile(ExcelExport excelExport) {
    //Create new excel file
    try {
      //clear excel files
      excelExport.resetContnet(file);
    } catch(Exception e) {
      System.out.println("Excel file creation failed");
    }
  }

  private void runMultipleTimes(ArrayList<Integer> inputPoints) {
    int dimension = inputPoints.size();
    int maximumRange = (int)Math.pow(10, RANGE_MULTIPLIER);
    createNewExcelFile(excelExport);

    for (int numOfPoints = 100; numOfPoints <= maximumRange; numOfPoints *= 2) {
      //reset time data in the static class
      ProcessTimeRecorder.reset();
      System.out.println("Number of total points: " + numOfPoints);
      if (dimension < 2) {
        runOneCase(dimension, 1, numOfPoints,
         numOfPoints / 2, inputPoints, numOfPoints);
      } else {
        runOneCase(dimension, 1, numOfPoints,
         numOfPoints * POINT_MULTIPLIER, inputPoints, numOfPoints);
      }

      //form output string data <- consider refactoring this.
      StringBuilder sb = new StringBuilder();
      String data1 = "---------------------------\n" 
       + "Number of points " + numOfPoints + "\n";
      String data2 = "\nSequential\n";
      String data3 = "Construction time(ns): "
       + ProcessTimeRecorder.sequentialConstructionTime + "\n";
      String data4 = "Search time(ns): "
       + ProcessTimeRecorder.sequentialConstructionTime + "\n";
      String data5 = "\nKDTree\n";
      String data6 = "Construction time(ns): "
       + ProcessTimeRecorder.KDTreeConstructionTime + "\n";
      String data7 = "Search time(ns): "
       + ProcessTimeRecorder.KDTreeSearchTime + "\n\n";
      sb.append(data1);
      sb.append(data2);
      sb.append(data3);
      sb.append(data4);
      sb.append(data5);
      sb.append(data6);
      sb.append(data7);

      //append measured time to Excel
      try {
        System.out.println(ProcessTimeRecorder.sequentialConstructionTime);
        excelExport.appendContent(sb.toString(), file);
      } catch (Exception e) {
        System.out.println("Excel append error");
      }
      ProcessTimeRecorder.reset();
    }
  }

  public void runOneCase(int dimension,
                          int from,
                          int to,
                          int points, //<- naming confusion with ArrayList points from other classes
                          ArrayList<Integer> inputPoints, //<- rename this as well - confusion with points
                          int fileIndexName) {
    //generate point -> export to data file
    generatePoints(dimension, from, to, points);
    sequential = new SequentialAlgorithm();
    CoordinateReader coordinateReader
      = new CoordinateReader(sequential);

    //read and construct datastructure at the same time
    sequential = coordinateReader.extractAndInsterPoints("./data/POINTS_"
     + to + "_" + points + ".dat");
    //construct sequential and search at the same time
    DataCarrier result = sequential.findCloestDistance(inputPoints);
    String resultInString = result.toString();
    try {
      excelExport.appendContent("---------------------------\n"
       + "Target point: "
       + inputPoints.toString(), file);
      excelExport.appendContent("Sequential:" + resultInString, file);
    } catch(Exception e) {
      System.out.println("Data export error");
    }


    //construct kd tree
    ArrayList<ArrayList<Integer>> entireMatrix
      = coordinateReader.getEntireMatrix();
    KDTreeIntegers kdTreeInt = new KDTreeIntegers(entireMatrix);
    long constructionTimeStart = System.nanoTime();
    kdTreeInt.buildKDTree();
    ProcessTimeRecorder.KDTreeConstructionTime += System.nanoTime() - constructionTimeStart;


    //find closest point in kd tree
    long searchTimeStart = System.nanoTime();
    result = kdTreeInt.find(inputPoints);
    ProcessTimeRecorder.KDTreeSearchTime += System.nanoTime() - searchTimeStart;
    resultInString.toString();
    try {
      excelExport.appendContent("KDTree:" + resultInString, file);
    } catch(Exception e) {
      System.out.println("Data export error");
    }
    
    //find closest point in kd tree - advanced search mode

    //Export data to Excel (Do not override)

  }
  
  private void generatePoints(int dimension,
                              int rangeFrom,
                              int rangeTo,
                              int point_amount) {
    CoordinateGen pointGenerator
     = new CoordinateGen("./data/POINTS_" + rangeTo  + "_"
      + point_amount + ".dat");
    pointGenerator.automatedGenerator(dimension, rangeFrom, rangeTo, point_amount);
  }
}