package src;

import src.utils.CoordinateGen;

class Main {

  private void generatePoints() {
    CoordinateGen pointGenerator = new CoordinateGen();
    pointGenerator.promptInput();
  }
  public static void main (String[] args) {
    Main mainInst = new Main();
    mainInst.generatePoints();
    //SequencialAlgorithm instance = new SequencialAlgorithm();
  
    //query test dataset

    //Sequential Scan - bruteforce

    //Projection

    //Cells

    //K-d tree

    //Range Trees

    //k-ranges
  }
}