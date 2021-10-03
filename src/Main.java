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
import src.utils.ProfileContainer;

 /*
 * @author: Marco Backman
 * @email : roni2006@hanmail.net
 */
 
class Main {
  //Range over 4 produces integer representation overflow
  final int CASE_NUMBER = 1;
  final int POINT_RANGE_LIMIT = 80;

  final String EXCEL_FILE_NAME = "./data/dataProfile.cell";
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
    try {
      mainInst.excelExport.resetContnet(mainInst.EXCEL_FILE);
    } catch(Exception e) {
      e.printStackTrace();
    }
    for (int i = 1; i <= 12; i++) {
      //small cases
      if (i < 5) {
        int maxRange = i * 10;
        int numberOfPoints = i * 10;
        String fileName = "./data/SMALL_DATA_CASE"
           + maxRange + "_" + (numberOfPoints);
        for (int numTries= 0 ; numTries < 5; numTries++) {
          ProfileContainer singleCaseResult
           = mainInst.executeAlgorithm(inputList, fileName, maxRange);
          //export data to Excel file
          mainInst.exportDataToExcel(singleCaseResult);
        }
      }

      //large cases
      else {
        int maxRange = (i - 4) * 10;
        int powerBaseTwo = (int)Math.pow(2, i-4);
        int numberOfPoints = powerBaseTwo * 10;
        String fileName = "./data/BIG_DATA_CASE"
         + maxRange + "_" + (numberOfPoints);
        for (int numTries= 0 ; numTries < 5; numTries++) {
          ProfileContainer singleCaseResult
           = mainInst.executeAlgorithm(inputList, fileName, maxRange);
          //export data to Excel file
          mainInst.exportDataToExcel(singleCaseResult);
        }
      }
    }
  }

  public ProfileContainer executeAlgorithm(ArrayList<Integer> targetPoints, String fileName, int range) {
    //read input files
    CoordinateReader coordinateReader
      = new CoordinateReader();
    coordinateReader.readPointsFromFile(fileName);
    ArrayList<ArrayList<Integer>> matrix
      = coordinateReader.getEntireMatrix();
    ProfileContainer singleProfile
     = new ProfileContainer(CASE_NUMBER,
                            range,
                            matrix.size(),
                            targetPoints);

    //construct sequential
    SequentialAlgorithm sequentialAlgorithm = new SequentialAlgorithm();
    sequentialAlgorithm.buildList(matrix);
    //import construction time
    singleProfile.setSeqBuildTime(ProcessTimeRecorder.getInNanoSeconds(1));

    //search nearest distance
    DataCarrier result = sequentialAlgorithm.findCloestDistance(targetPoints);
    String seqResultInString = result.toString();
    singleProfile.setSeqSearchTime(ProcessTimeRecorder.getInNanoSeconds(2));
    singleProfile.setSeqResultPoints(result.getPoints());
    singleProfile.setSeqDistance(result.getDistance());
    System.out.println("Sequential search: " + seqResultInString.toString());

    //construct kd tree
    KDTreeIntegers kdTreeInt = new KDTreeIntegers(matrix);
    long kdConstructionTimeStart = System.nanoTime();
    kdTreeInt.buildKDTree();
    ProcessTimeRecorder.KDTreeConstructionTime += System.nanoTime() - kdConstructionTimeStart;
    singleProfile.setKdBuildTime(ProcessTimeRecorder.getInNanoSeconds(3));

    //find point in kd tree
    long kdSearchTimeStart = System.nanoTime();
    DataCarrier kdResult = kdTreeInt.findKDTreeNormal(targetPoints);
    ProcessTimeRecorder.KDTreeSearchTime += System.nanoTime() - kdSearchTimeStart;
    System.out.println("KDTree search: " + kdResult.toString());
    singleProfile.setKdSearchTime(ProcessTimeRecorder.getInNanoSeconds(4));
    singleProfile.setKdResultPoints(kdResult.getPoints());
    singleProfile.setKdDistance(kdResult.getDistance());

    //KNN algorithm
    long knnSearchTimeStart = System.nanoTime();
    kdTreeInt.findKNNAlgorithm(targetPoints,
                               kdResult.getTreeNode());
    ProcessTimeRecorder.KDTreeKNNSearchTime += System.nanoTime() - knnSearchTimeStart;
    singleProfile.setKnnSearchTime(ProcessTimeRecorder.getInNanoSeconds(5));
    singleProfile.setKnnResultPoints(kdTreeInt.KNN_result.getPoints());
    singleProfile.setKnnDistance(kdTreeInt.KNN_result.getDistance());
    System.out.println("KDTree with KNN search: " + kdTreeInt.KNN_result.toString());
    ProcessTimeRecorder.reset();
    //kdTreeInt.printTree();
    return singleProfile;
  }

  private void exportDataToExcel(ProfileContainer data) {
    try { //reset first
      String content = excelExport.generateSingleLine(data);
      excelExport.appendContent(content, EXCEL_FILE);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}