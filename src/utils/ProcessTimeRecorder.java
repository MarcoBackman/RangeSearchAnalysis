package src.utils;

public class ProcessTimeRecorder {
  //in milliseconds
  public static long sequentialConstructionTime = 0;
  public static long sequentialSearchTime = 0;
  public static long KDTreeConstructionTime = 0;
  public static long KDTreeSearchTime = 0;
  //in input size?
  public static double spaceMeasurement = 0;

  /*
   * 1: sequential construction, 2: sequential search,
   * 3: kdtree construction, 4: kdtree search
   */
  public static double getInSeconds(int index) {
    switch(index) {
      case 1: return sequentialConstructionTime * Math.pow(10, 3);

      case 2: return sequentialSearchTime * Math.pow(10, 3);

      case 3: return KDTreeConstructionTime * Math.pow(10, 3);

      case 4: return KDTreeSearchTime * Math.pow(10, 3);

      default: return 0;
    }
  }

  public static void reset() {
    sequentialConstructionTime = 0;
    sequentialSearchTime = 0;
    KDTreeConstructionTime = 0;
    KDTreeSearchTime = 0;
  }
}
