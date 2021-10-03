package src.utils;

import java.util.ArrayList;

 /*
 * @author: Marco Backman
 * @email : roni2006@hanmail.net
 */

//single case data container
public class ProfileContainer {
    // common data
    int caseNumber = 0;
    int numberRange = 0;
    int matrixLength = 0;
    ArrayList<Integer> targetPoints = null;

    // sequential data
    ArrayList<Integer> seqResultPoints = null;
    double seqDistance = 0;
    double seqBuildTime = 0;
    double seqSearchTime = 0;

    // kdTree data
    ArrayList<Integer> kdResultPoints = null;
    double kdDistance = 0;
    double kdBuildTime = 0;
    double kdSearchTime = 0;

    // KNN data
    ArrayList<Integer> knnResultPoints = null;
    double knnDistance = 0;
    double knnSearchTime = 0;

    //constructor
    public ProfileContainer(int caseNumber,
                         int numberRange,
                         int matrixLength,
                         ArrayList<Integer> targetPoints) {
        this.caseNumber = caseNumber;
        this.numberRange = numberRange;
        this.matrixLength = matrixLength;
        this.targetPoints = targetPoints;
    }

    public int getCaseNumber() {
        return this.caseNumber;
    }

    public int getNumberRange() {
        return this.numberRange;
    }

    public int getMatrixLength() {
        return this.matrixLength;
    }

    public ArrayList<Integer> getTargetPoints() {
        return this.targetPoints;
    }

    // sequential data getter and setter
    public void setSeqResultPoints(ArrayList<Integer> seqResultPoints) {
        this.seqResultPoints = seqResultPoints;
    }

    public void setSeqDistance(double seqDistance) {
        this.seqDistance = seqDistance;
    }

    public void setSeqBuildTime(double seqBuildTime) {
        this.seqBuildTime = seqBuildTime;
    }

    public void setSeqSearchTime(double seqSearchTime) {
        this.seqSearchTime = seqSearchTime;
    }
    
    public ArrayList<Integer> getSeqResultPoints() {
        return this.seqResultPoints;
    }

    public double getSeqDistance() {
        return this.seqDistance;
    }

    public double getSeqBuildTime() {
        return this.seqBuildTime;
    }

    public double getSeqSearchTime() {
        return this.seqSearchTime;
    }

    // kd data getter and setter
    public void setKdResultPoints(ArrayList<Integer> kdResultPoints) {
        this.kdResultPoints = kdResultPoints;
    }

    public void setKdDistance(double kdDistance) {
        this.kdDistance = kdDistance;
    }

    public void setKdBuildTime(double kdBuildTime) {
        this.kdBuildTime = kdBuildTime;
    }

    public void setKdSearchTime(double kdSearchTime) {
        this.kdSearchTime = kdSearchTime;
    }
    
    public ArrayList<Integer> getKdResultPoints() {
        return this.kdResultPoints;
    }

    public double getKdDistance() {
        return this.kdDistance;
    }

    public double getKdBuildTime() {
        return this.kdBuildTime;
    }

    public double getKdSearchTime() {
        return this.kdSearchTime;
    }

    // knn data getter and setter
    public void setKnnResultPoints(ArrayList<Integer> knnResultPoints) {
        this.knnResultPoints = knnResultPoints;
    }

    public void setKnnDistance(double knnDistance) {
        this.knnDistance = knnDistance;
    }

    public void setKnnSearchTime(double knnSearchTime) {
        this.knnSearchTime = knnSearchTime;
    }
    
    public ArrayList<Integer> getKnnResultPoints() {
        return this.knnResultPoints;
    }

    public double getKnnDistance() {
        return this.knnDistance;
    }

    public double getKnnSearchTime() {
        return this.knnSearchTime;
    }
}
