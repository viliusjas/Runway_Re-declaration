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
                    takeOffAwaylandingOverDisplaced(obstacle, obstacle.getObstacleLeftPos(), runway);
                }else{
                    takeOffAwaylandingOver(obstacle, obstacle.getObstacleLeftPos(), runway);
                }
            }else{
                if(runwayDisplaced > 0){
                    takeOffTowardslandingTowardsDisplaced(obstacle, obstacle.getObstacleLeftPos(), runway);
                }else{
                    takeOffTowardslandingTowards(obstacle,obstacle.getObstacleLeftPos(), runway);
                }
            }
        }
        //Position "Right"
        else{
            if(obstacle.getObstacleLeftPos() > obstacle.getObstacleRightPos()){
                if(runwayDisplaced > 0){
                    takeOffAwaylandingOverDisplaced(obstacle, obstacle.getObstacleRightPos(), runway);
                }else{
                    takeOffAwaylandingOver(obstacle, obstacle.getObstacleRightPos(), runway);
                }
            }else{
                if(runwayDisplaced > 0){
                    takeOffTowardslandingTowardsDisplaced(obstacle, obstacle.getObstacleRightPos(), runway);
                }else{
                    takeOffTowardslandingTowards(obstacle, obstacle.getObstacleRightPos(), runway);
                }
            }
        }
    }

    public void takeOffAwaylandingOverDisplaced(Obstacle obstacle, int distanceFromTSH, Runway runway){
        int displacedTSH = runway.getTakeOffRunAvail() - runway.getLandDistAvail();
        int clearway = runway.getTakeOffDistAvail() - runway.getTakeOffRunAvail();
        int stopway = runway.getAccStopDistAvail() - runway.getTakeOffRunAvail();
        int slopeCalc = obstacle.getObstacleHeight() * 50;
        if (slopeCalc < resa) {
            slopeCalc = resa;
        }
        int recalculatedTORA = runway.getTakeOffRunAvail() - blastProtection - distanceFromTSH - displacedTSH;
        int recalculatedTODA = recalculatedTORA + clearway;
        int recalculatedASDA = recalculatedTORA + stopway;
        int recalculatedLDA = runway.getLandDistAvail() - distanceFromTSH - visualStripEnd - slopeCalc;
    }

    public void takeOffTowardslandingTowardsDisplaced(Obstacle obstacle, int distanceFromTSH, Runway runway){
        int slopeCalc = obstacle.getObstacleHeight() * 50;
        if (slopeCalc < resa) {
            slopeCalc = resa;
        }
        int recalculatedTORA = distanceFromTSH - slopeCalc - visualStripEnd;
        int recalculatedTODA = recalculatedTORA;
        int recalculatedASDA = recalculatedTORA;
        int recalculatedLDA = distanceFromTSH - resa - visualStripEnd;
    }

    public void takeOffAwaylandingOver(Obstacle obstacle, int distanceFromTSH, Runway runway){
        int clearway = runway.getTakeOffDistAvail() - runway.getTakeOffRunAvail();
        int stopway = runway.getAccStopDistAvail() - runway.getTakeOffRunAvail();
        int slopeCalc = obstacle.getObstacleHeight() * 50;
        if (slopeCalc < resa) {
            slopeCalc = resa;
        }
        int recalculatedTORA = runway.getTakeOffRunAvail() - visualStripEnd - resa - distanceFromTSH;
        int recalculaedTODA = recalculatedTORA + clearway;
        int recalculatedASDA = recalculatedTORA + stopway;
        int recalculatedLDA = runway.getLandDistAvail() - slopeCalc - distanceFromTSH - visualStripEnd;
    }
    public void takeOffTowardslandingTowards(Obstacle obstacle, int distanceFromTSH, Runway runway){
        int slopeCalc = obstacle.getObstacleHeight() * 50;
        if (slopeCalc < resa) {
            slopeCalc = resa;
        }
        int displacedTSH = runway.getTakeOffRunAvail() - runway.getLandDistAvail();

        int recalculatedTORA = distanceFromTSH + displacedTSH - slopeCalc - visualStripEnd;
        int recalculatedTODA = recalculatedTORA;
        int recalculatedASDA = recalculatedTORA;
        int recalculatedLDA = distanceFromTSH - visualStripEnd - resa;
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
