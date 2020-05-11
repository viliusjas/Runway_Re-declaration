package Model.Objects;

public class Obstacle {

    /**
     * @length obstacle's length
     * @height obstacle's height
     */

    private String name;

    private int length;
    private int height;
    private int leftPos;
    private int rightPos;

    private int leftThreshold;
    private int rightThreshold;

    private Runway obstacleRunway;

    public Obstacle(int length, int height, int leftThreshold, int rightThreshold) {
        this.length = length;
        this.height = height;
        this.leftThreshold = leftThreshold;
        this.rightThreshold = rightThreshold;
    }

    /*
    public Obstacle(int length, int height, Runway obstacleRunway, int obstaclePos) {
        this.length = length;
        this.height = height;
        this.obstacleRunway = obstacleRunway;
        this.leftPos = obstaclePos;
        this.rightPos = obstacleRunway.getTakeOffRunAvail() - obstaclePos;
    }

     */


    public int getObstacleLeftPos(){
        return leftThreshold;
    }
    public int getObstacleRightPos(){
        return rightThreshold;
    }
    public int getObstacleHeight(){return height;}

    public Runway getObstacleRunway() {
        return obstacleRunway;
    }

    public int getObstacleLength(){
        return length;
    }

    public void setObstacleRunway(Runway obstacleRunway) {
        this.obstacleRunway = obstacleRunway;
    }

    public String getName() {
        return this.name;
    }
    public void setLeftThreshold(int leftThreshold) {
        this.leftThreshold = leftThreshold;
    }

    public void setRightThreshold(int rightThreshold) {
        this.rightThreshold = rightThreshold;
    }


    public void setName(String name) {
        this.name = name;
    }


}
