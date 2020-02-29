package Model.Objects;

public class Obstacle {

    /**
     * @length obstacle's length
     * @height obstacle's height
     */

    private int length;
    private int height;

    private Runway obstacleRunway;

    public Obstacle(int length, int height) {
        this.length = length;
        this.height = height;
    }
    public Obstacle(int length, int height, Runway obstacleRunway) {
        this.length = length;
        this.height = height;
        this.obstacleRunway = obstacleRunway;
    }


    public Runway getObstacleRunway() {
        return obstacleRunway;
    }

    public void setObstacleRunway(Runway obstacleRunway) {
        this.obstacleRunway = obstacleRunway;
    }

}
