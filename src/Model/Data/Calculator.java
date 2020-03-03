package Model.Data;

import Model.Objects.Aircraft;
import Model.Objects.Obstacle;
import Model.Objects.Runway;

import java.util.List;
import java.util.Queue;

public class Calculator {
    //Runway runway;
    //int tora;
    //int toda;
    //int asda;
    //int lda;
    int visualStripEnd = 60;
    int visualStripWidth = 75;
    int resa = 240;
    int blastProtection = 300;


    public Calculator(){}

    public Calculator(int visualStripEnd, int visualStripWidth, int resa){
        this.visualStripEnd = visualStripEnd;
        this.visualStripWidth = visualStripWidth;
        this.resa = resa;
    }

    private Queue<Calculations> calculations;

    //public static Runway calculate(Runway runway, List<Obstacle> obstacles) {

      //  return null;
    //}

    public void calculate(Obstacle obstacle, Runway runway){
        if(obstacle.getObstacleLeftPos() < -visualStripEnd
        || obstacle.getObstacleRightPos() < -visualStripEnd){
            System.out.println("No re-declaration needed");
        }else{
            decideRedeclarationCase(obstacle, runway);
        }
    }
    public void decideRedeclarationCase(Obstacle obstacle, Runway runway){
        int runwayDisplaced = runway.getTakeOffRunAvail() - runway.getLandDistAvail();
        if(runway.getRunwayPos().equals("Left")){
            if(obstacle.getObstacleLeftPos() < obstacle.getObstacleRightPos()){
                if(runwayDisplaced > 0){
                    takeOffAwaylandingOverDisplaced(obstacle, runway);
                }else{
                    takeOffAwaylandingOver(obstacle, runway);
                }
            }else{
                if(runwayDisplaced > 0){
                    takeOffTowardslandingTowardsDisplaced(obstacle, runway);
                }else{
                    takeOffTowardslandingTowards(obstacle, runway);
                }
            }
        }
        //Position "Right"
        else{
            if(obstacle.getObstacleLeftPos() > obstacle.getObstacleRightPos()){
                if(runwayDisplaced > 0){
                    takeOffAwaylandingOverDisplaced(obstacle, runway);
                }else{
                    takeOffAwaylandingOver(obstacle, runway);
                }
            }else{
                if(runwayDisplaced > 0){
                    takeOffTowardslandingTowardsDisplaced(obstacle, runway);
                }else{
                    takeOffTowardslandingTowards(obstacle, runway);
                }
            }
        }
    }

    public void takeOffAwaylandingOverDisplaced(Obstacle obstacle, Runway runway){

    }
    public void takeOffTowardslandingTowardsDisplaced(Obstacle obstacle, Runway runway){

    }
    public void takeOffAwaylandingOver(Obstacle obstacle, Runway runway){

    }
    public void takeOffTowardslandingTowards(Obstacle obstacle, Runway runway){

    }




    public static boolean runwayIsSafe(Runway runway, Aircraft aircraft, boolean takeoff) {
        return false;
    }

    class Calculations {
        Runway runway;
        Obstacle o;
        int tora;
        int toda;
        int asda;
        int lda;

        public Calculations(Runway runway, Obstacle o, int tora, int toda, int asda, int lda) {
            this.o = o;
            this.runway = runway;
            this.tora = tora;
            this.toda = toda;
            this.asda = asda;
            this.lda = lda;
        }
    }



}
