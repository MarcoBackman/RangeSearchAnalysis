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
  final String INPUT_FILE_NAME = "./data/SMALL_DATA_CASE5_40";
  final String EXCEL_FILE_NAME = "./data/dataProfile.xlsx";
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
    ProfileContainer singleCaseResult = mainInst.executeAlgorithm(inputList);
    //export data to Excel file
    mainInst.exportDataToExcel(singleCaseResult);
  }

  public ProfileContainer executeAlgorithm(ArrayList<Integer> targetPoints) {
    //read input files
    CoordinateReader coordinateReader
      = new CoordinateReader();
    coordinateReader.readPointsFromFile(INPUT_FILE_NAME);
    ArrayList<ArrayList<Integer>> matrix
      = coordinateReader.getEntireMatrix();
    ProfileContainer singleProfile = new ProfileContainer(1, 10, 20, targetPoints);

    //construct sequential
    SequentialAlgorithm sequentialAlgorithm = new SequentialAlgorithm();
    sequentialAlgorithm.buildList(matrix);
    //import construction time
    singleProfile.setSeqBuildTime(ProcessTimeRecorder.getInSeconds(1));

    //search nearest distance
    DataCarrier result = sequentialAlgorithm.findCloestDistance(targetPoints);
    String seqResultInString = result.toString();
    singleProfile.setSeqSearchTime(ProcessTimeRecorder.getInSeconds(2));
    singleProfile.setSeqResultPoints(result.getPoints());
    singleProfile.setSeqDistance((long)result.getDistance());
    System.out.println("Sequential search: " + seqResultInString.toString());

    //construct kd tree
    KDTreeIntegers kdTreeInt = new KDTreeIntegers(matrix);
    long kdConstructionTimeStart = System.nanoTime();
    kdTreeInt.buildKDTree();
    ProcessTimeRecorder.KDTreeConstructionTime += System.nanoTime() - kdConstructionTimeStart;
    singleProfile.setKdBuildTime(ProcessTimeRecorder.getInSeconds(3));

    //find point in kd tree
    long kdSearchTimeStart = System.nanoTime();
    DataCarrier kdResult = kdTreeInt.findKDTreeNormal(targetPoints);
    ProcessTimeRecorder.KDTreeSearchTime += System.nanoTime() - kdSearchTimeStart;
    System.out.println("KDTree search: " + kdResult.toString());
    singleProfile.setKdSearchTime(ProcessTimeRecorder.getInSeconds(4));
    singleProfile.setKdResultPoints(kdResult.getPoints());
    singleProfile.setKdDistance((long)kdResult.getDistance());

    //KNN algorithm
    long knnSearchTimeStart = System.nanoTime();
    kdTreeInt.findKNNAlgorithm(targetPoints,
                               kdResult.getTreeNode());
    ProcessTimeRecorder.KDTreeKNNSearchTime += System.nanoTime() - knnSearchTimeStart;
    singleProfile.setKnnSearchTime(ProcessTimeRecorder.getInSeconds(5));
    singleProfile.setKnnResultPoints(kdResult.getPoints());
    singleProfile.setKnnDistance((long)kdResult.getDistance());
    System.out.println("KDTree with KNN search: " + kdTreeInt.KNN_result.toString());

    //kdTreeInt.printTree();
    return singleProfile;
  }

  private void exportDataToExcel(ProfileContainer data) {
    try { //reset first
      excelExport.resetContnet(EXCEL_FILE);
      String content = excelExport.generateSingleLine(data);
      excelExport.appendContent(content, EXCEL_FILE);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}