package src;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import src.datastructure.DataCarrier;
import src.datastructure.KDTreeIntegers;
import src.datastructure.SequentialAlgorithm;
import src.utils.CoordinateReader;
import src.utils.ExcelExport;
import src.utils.ProcessTimeRecorder;

 /*
 * @author: Marco Backman
 * @email : roni2006@hanmail.net
 */
 
class Main {
  //Range over 4 produces integer representation overflow
  final String INPUT_FILE_NAME = "./data/SMALL_DATA_CASE5_40";
  final String EXCEL_FILE_NAME = "./data/dataProfile.txt";
  File EXCEL_FILE = new File(EXCEL_FILE_NAME);
  ExcelExport excelExport = new ExcelExport();

  public static void main (String[] args) {
    Scanner sc = new Scanner(System.in);
    Main mainInst = new Main();
    //prompt user input
    System.out.println("Enter points");
    String inputLine = sc.nextLine();
    String[] strArr = inputLine.split(" ");
    ArrayList<Integer> inputList = new ArrayList<Integer>();
    for (int i = 0; i < strArr.length; i++) {
      inputList.add(Integer.parseInt(strArr[i]));
    }
    sc.close();
    //
    mainInst.executeAlgorithm(inputList);
  }

  public void executeAlgorithm(ArrayList<Integer> targetPoints) {
    //read input files
    CoordinateReader coordinateReader
      = new CoordinateReader();
    coordinateReader.readPointsFromFile(INPUT_FILE_NAME);
    ArrayList<ArrayList<Integer>> matrix
      = coordinateReader.getEntireMatrix();


    //construct sequential
    SequentialAlgorithm sequentialAlgorithm = new SequentialAlgorithm();
    sequentialAlgorithm.buildList(matrix);

    //sequential search - match points
    /*
    IntegerNode node = sequentialAlgorithm.findMatch(targetPoints);
    if (node == null) {
      System.out.println("No match found");
    } else {
      System.out.println("Match found");
    }
    sequentialAlgorithm.print(targetPoints);
    */

    //nearest distance
    DataCarrier result = sequentialAlgorithm.findCloestDistance(targetPoints);
    String seqResultInString = result.toString();
    System.out.println("Sequential search: " + seqResultInString.toString());

    //construct kd tree
    KDTreeIntegers kdTreeInt = new KDTreeIntegers(matrix);
    long constructionTimeStart = System.nanoTime();
    kdTreeInt.buildKDTree();
    ProcessTimeRecorder.KDTreeConstructionTime += System.nanoTime() - constructionTimeStart;

    //find point in kd tree
    long searchTimeStart = System.nanoTime();
    DataCarrier kdResult = kdTreeInt.findKDTreeNormal(targetPoints);
    ProcessTimeRecorder.KDTreeSearchTime += System.nanoTime() - searchTimeStart;
    System.out.println("KDTree search: " + kdResult.toString());

    //KNN algorithm
    kdTreeInt.findKNNAlgorithm(targetPoints,
                               kdResult.getTreeNode());

    System.out.println("KDTree with KNN search: " + kdTreeInt.KNN_result.toString());

    //kdTreeInt.printTree();
    //export data to Excel file
    try { //reset first
      excelExport.resetContnet(EXCEL_FILE);
    } catch (Exception e) {
      e.printStackTrace();
    }

    //append data
    exportDataToExcel("data!");
  }

  private void exportDataToExcel(String data) {
    try {
      excelExport.appendContent(data, EXCEL_FILE);
    } catch(Exception e) {
      System.out.println("Data export error");
    }
  }
}