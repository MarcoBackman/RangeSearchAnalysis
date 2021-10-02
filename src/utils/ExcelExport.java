package src.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

 /*
 * @author: Marco Backman
 * @email : roni2006@hanmail.net
 * @Todo: consider implementing Apache POI API for better functionalities
 */


public class ExcelExport {
  
  final char COMMA = 47;

  public void appendContent(String contents, File file) throws IOException { 
    FileWriter fw = new FileWriter(file, true); //true - append
    BufferedWriter bw = new BufferedWriter(fw);
    bw.write(contents);
    bw.newLine();
    bw.close();
  }

  public void resetContnet(File file) throws IOException {
    //only creates on empty path/file
    createPath(file);
    createFile(file);
    FileWriter fw = new FileWriter(file, false); //false - no append
    BufferedWriter bw = new BufferedWriter(fw);
    bw.close();
  }

  private boolean checkPath(File file) {
    try {
      if (!file.getParentFile().exists()) {
        return false;
      } else {
        return true;
      }
    } catch (Exception e) {
      System.out.println("Error on file/path creation" + e);
    }
    return false;
  }

  private boolean checkFile(File file) {
    try {
      if (!file.exists()){
        return false;
      } else {
        return true;
      }
    } catch (Exception e) {
      System.out.println("Error on file/path creation" + e);
    }
    return false;
  }

  private void createPath (File file) {
    try {
      if (checkPath(file)) {
        file.getParentFile().mkdirs();
        System.out.println("Creating path");
      }
    } catch (Exception e) {
      System.out.println("Error on path creation" + e);
    }
  }

  private void createFile (File file) {
    try {
      if (checkFile(file)){
        System.out.println("Creating file");
        file.createNewFile();
      }
    } catch (Exception e) {
      System.out.println("Error on file creation" + e);
    }
  }

  /*
   * 1: number range specs,
   * 2: matrix length
   * 3: user's desired point
   * 4: sequential algorithm
   *  4-1: point found
   *  4-2: shortest distance found
   *  4-3: construction time
   *  4-4: search time
   * 5: kd-tree algorithm
   *  5-1: point found
   *  5-2: shortest distance found
   *  5-3: construction time
   *  5-4: search time
   * 6: knn algorithm
   *  6-1: point found
   *  6-2: shortest distance found
   *  6-3: construction time - same as 5-3
   *  6-4: search time
   */
  public String generateSingleLine(ProfileContainer dataSet) {
    StringBuilder sb = new StringBuilder();

    //common data
    sb.append(dataSet.getNumberRange());
    sb.append(COMMA);
    sb.append(dataSet.getMatrixLength());
    sb.append(COMMA);
    sb.append(dataSet.getTargetPoints().toString().trim());
    sb.append(COMMA);

    //sequential algorithm
    sb.append(dataSet.getSeqResultPoints().toString().trim());
    sb.append(COMMA);
    sb.append(dataSet.getSeqDistance());
    sb.append(COMMA);
    sb.append(dataSet.getSeqBuildTime());
    sb.append(COMMA);
    sb.append(dataSet.getSeqSearchTime());
    sb.append(COMMA);
    //kd-tree algorithm
    sb.append(dataSet.getKdResultPoints()).toString().trim();
    sb.append(COMMA);
    sb.append(dataSet.getKdDistance());
    sb.append(COMMA);
    sb.append(dataSet.getKdBuildTime());
    sb.append(COMMA);
    sb.append(dataSet.getKdSearchTime());
    sb.append(COMMA);
    //knn algorithm
    sb.append(dataSet.getKnnResultPoints().toString().trim());
    sb.append(COMMA);
    sb.append(dataSet.getKnnDistance());
    sb.append(COMMA);
    sb.append(dataSet.getKdBuildTime());
    sb.append(COMMA);
    sb.append(dataSet.getKnnSearchTime());
    sb.append(COMMA);
    return sb.toString();
  }

  /*
   * Lists of datasets
   */
  public String returnEntireExcelContent(ArrayList<ProfileContainer> dataSetLists) {
    if (dataSetLists == null) {
      return "";
    }
    StringBuilder sb = new StringBuilder();
    sb.append("\n");
    sb.append(dataSetLists.get(0).caseNumber);
    sb.append("\n");
    for (ProfileContainer dataSet : dataSetLists) {
      sb.append(generateSingleLine(dataSet));
      sb.append("\n");
    }
    return sb.toString();
  }

}
