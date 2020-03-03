package Model.Objects;

public class Obstacle {

    /**
     * @length obstacle's length
     * @height obstacle's height
     */

    private int length;
    private int height;

    private int leftThreshold;
    private int rightThreshold;

    private Runway obstacleRunway;

    public Obstacle(int length, int height, int leftThreshold, int rightThreshold) {
        this.length = length;
        this.height = height;
        this.leftThreshold = leftThreshold;
        this.rightThreshold = rightThreshold;
    }

    public Runway getObstacleRunway() {
        return obstacleRunway;
    }

    public void setObstacleRunway(Runway obstacleRunway) {
        this.obstacleRunway = obstacleRunway;
    }


}
