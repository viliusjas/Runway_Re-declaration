package Model.Objects;

public class Obstacle {

    /**
     * @length obstacle's length
     * @height obstacle's height
     */

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

    public Obstacle(int length, int height, Runway obstacleRunway, int obstaclePos) {
        this.length = length;
        this.height = height;
        this.obstacleRunway = obstacleRunway;
        this.leftPos = obstaclePos;
        this.rightPos = obstacleRunway.getTakeOffRunAvail() - obstaclePos;
    }


    public int getObstacleLeftPos(){
        return leftPos;
    }
    public int getObstacleRightPos(){
        return rightPos;
    }


    public Runway getObstacleRunway() {
        return obstacleRunway;
    }

    public void setObstacleRunway(Runway obstacleRunway) {
        this.obstacleRunway = obstacleRunway;
    }


}
