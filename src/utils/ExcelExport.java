package src.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

 /*
 * @author: Marco Backman
 * @email : roni2006@hanmail.net
 */


public class ExcelExport {
  
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
}
