package Model.Data;

import Model.Objects.Aircraft;
import Model.Objects.Obstacle;
import Model.Objects.Runway;

import java.util.List;
import java.util.Queue;

public class Calculator {
    //Runway runway;
    int toraOG = 0;
    int todaOG = 0;
    int asdaOG = 0;
    int ldaOG = 0;
    int visualStripEnd = 60;
    int visualStripWidth = 75;
    int resa = 240;
    int blastProtection = 300;
    String calcB = "";
    String calcHelp1 = "";
    String calcHelp2 = "";
    String calcHelp3 = "";


    public Calculator(){}

    public Calculator(int visualStripEnd, int visualStripWidth, int resa){
        this.visualStripEnd = visualStripEnd;
        this.visualStripWidth = visualStripWidth;
        this.resa = resa;
    }

    private Queue<Calculations> calculations;

    public void calculate(Obstacle obstacle, Runway runway){
        if(obstacle.getObstacleLeftPos() < -visualStripEnd
        || obstacle.getObstacleRightPos() < -visualStripEnd){
            System.out.println("No re-declaration needed");
        }else{
            runway.setOGValues(runway.getTakeOffRunAvail(), runway.getTakeOffDistAvail(), runway.getAccStopDistAvail(), runway.getLandDistAvail());
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
                runway.setDirection("right");
                if(runwayDisplaced > 0){
                    takeOffAwaylandingOverDisplaced(obstacle, obstacle.getObstacleRightPos(), runway);
                }else{
                    takeOffAwaylandingOver(obstacle, obstacle.getObstacleRightPos(), runway);
                }
            }else{
                runway.setDirection("left");
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
            calcHelp1 = "" +resa;
        }else {calcHelp1 = obstacle.getObstacleHeight() + "*50";}
        int recalculatedTORA = runway.getTakeOffRunAvail() - blastProtection - distanceFromTSH - displacedTSH;
        int recalculatedTODA = recalculatedTORA + clearway;
        int recalculatedASDA = recalculatedTORA + stopway;
        int recalculatedLDA = runway.getLandDistAvail() - distanceFromTSH - visualStripEnd - slopeCalc;

        calcB = "TORA = Original TORA - Blast Protection - \n Distance from Threshold - Displaced Threshold" +"\n" +
                "TORA = " + runway.getTakeOffRunAvail() + " - " + blastProtection + " - " + distanceFromTSH +
                " - " + displacedTSH + " = " + recalculatedTORA + "\n" +
                "TODA = (R) TORA + CLEARWAY" + "\n" +
                "TODA = " + recalculatedTORA + " + " + clearway + " = " + recalculatedTODA + "\n" +
                "ASDA = (R) TORA + STOPWAY" + "\n" +
                "ASDA = " + recalculatedTORA + " + " + stopway + " = " + recalculatedASDA + "\n" +
                "LDA = Original LDA - Distance from Threshold - \n Slope Calculation - Strip End " + "\n" +
                "LDA = " + runway.getLandDistAvail() + " - " + distanceFromTSH + " - " + calcHelp1 + " - " +
                visualStripEnd + " = " + recalculatedLDA;

        runway.setTakeOffRunAvail(recalculatedTORA);
        runway.setTakeOffDistAvail(recalculatedTODA);
        runway.setAccStopDistAvail(recalculatedASDA);
        runway.setLandDistAvail(recalculatedLDA);
        runway.setClearwayLength(clearway);
        runway.setStopwayLength(stopway);
    }

    public void takeOffTowardslandingTowardsDisplaced(Obstacle obstacle, int distanceFromTSH, Runway runway){
        int clearway = runway.getTakeOffDistAvail() - runway.getTakeOffRunAvail();
        int stopway = runway.getAccStopDistAvail() - runway.getTakeOffRunAvail();
        int slopeCalc = obstacle.getObstacleHeight() * 50;
        if (slopeCalc < resa) {
            slopeCalc = resa;
            calcHelp1 = "" +resa;
        }else {calcHelp1 = obstacle.getObstacleHeight() + "*50";}
        int recalculatedTORA = distanceFromTSH - slopeCalc - visualStripEnd;
        int recalculatedTODA = recalculatedTORA;
        int recalculatedASDA = recalculatedTORA;
        int recalculatedLDA = distanceFromTSH - resa - visualStripEnd;

        calcB = "TORA = Distance from Threshold - Slope \n Calculation - Strip End" +"\n" +
                "TORA = " + distanceFromTSH + " - " + calcHelp1 + " - " + visualStripEnd + " = " + recalculatedTORA + "\n" +
                "TODA = (R) TORA" + "\n" +
                "TODA = " + recalculatedTORA + " = " + recalculatedTODA + "\n" +
                "ASDA = (R) TORA" + "\n" +
                "ASDA = " + recalculatedTORA + " = " + recalculatedASDA + "\n" +
                "LDA = Original LDA - RESA - Strip End" + "\n" +
                "LDA = " + distanceFromTSH + " - " + resa + " - " + visualStripEnd + " = " + recalculatedLDA;

        runway.setTakeOffRunAvail(recalculatedTORA);
        runway.setTakeOffDistAvail(recalculatedTODA);
        runway.setAccStopDistAvail(recalculatedASDA);
        runway.setLandDistAvail(recalculatedLDA);
        runway.setClearwayLength(clearway);
        runway.setStopwayLength(stopway);
    }

    public void takeOffAwaylandingOver(Obstacle obstacle, int distanceFromTSH, Runway runway){
        int clearway = runway.getTakeOffDistAvail() - runway.getTakeOffRunAvail();
        int stopway = runway.getAccStopDistAvail() - runway.getTakeOffRunAvail();
        int slopeCalc = obstacle.getObstacleHeight() * 50;
        if (slopeCalc < resa) {
            slopeCalc = resa;
            calcHelp1 = "" +resa;
        }else {calcHelp1 = obstacle.getObstacleHeight() + "*50";}
        int recalculatedTORA = runway.getTakeOffRunAvail() - visualStripEnd - resa - distanceFromTSH;
        int recalculatedTODA = recalculatedTORA + clearway;
        int recalculatedASDA = recalculatedTORA + stopway;
        int recalculatedLDA = runway.getLandDistAvail() - slopeCalc - distanceFromTSH - visualStripEnd;

        calcB = "TORA = Original TORA - Strip End - \n RESA - Distance from Threshold" +"\n" +
                "TORA = " + runway.getTakeOffRunAvail() + " - " + visualStripEnd + " - " + resa + " - " +
                distanceFromTSH + " = " + recalculatedTORA + "\n" +
                "TODA = (R) TORA + CLEARWAY" + "\n" +
                "TODA = " + recalculatedTORA + " + " + clearway + " = " + recalculatedTODA + "\n" +
                "ASDA = (R) TORA + STOPWAY" + "\n" +
                "ASDA = " + recalculatedTORA + " + " + stopway + " = " + recalculatedASDA + "\n" +
                "LDA = Original LDA - Slope Calculation - \n Distance from Threshold - Strip End" + "\n" +
                "LDA = " + runway.getLandDistAvail() + " - " + calcHelp1 + " - " + distanceFromTSH + " - " +
                visualStripEnd + " = " + recalculatedLDA;

        runway.setTakeOffRunAvail(recalculatedTORA);
        runway.setTakeOffDistAvail(recalculatedTODA);
        runway.setAccStopDistAvail(recalculatedASDA);
        runway.setLandDistAvail(recalculatedLDA);
        runway.setClearwayLength(clearway);
        runway.setStopwayLength(stopway);
    }

    public void takeOffTowardslandingTowards(Obstacle obstacle, int distanceFromTSH, Runway runway){
        int clearway = runway.getTakeOffDistAvail() - runway.getTakeOffRunAvail();
        int stopway = runway.getAccStopDistAvail() - runway.getTakeOffRunAvail();
        int slopeCalc = obstacle.getObstacleHeight() * 50;
        if (slopeCalc < resa) {
            slopeCalc = resa;
            calcHelp1 = "" +resa;
        }else {calcHelp1 = obstacle.getObstacleHeight() + "*50";}
        int displacedTSH = runway.getTakeOffRunAvail() - runway.getLandDistAvail();

        int recalculatedTORA = distanceFromTSH + displacedTSH - slopeCalc - visualStripEnd;
        int recalculatedTODA = recalculatedTORA;
        int recalculatedASDA = recalculatedTORA;
        int recalculatedLDA = distanceFromTSH - visualStripEnd - resa;


        calcB = "TORA = Distance from Threshold + Displaced Threshold - \n Slope Calculation - Strip End" +"\n" +
                "TORA = " + distanceFromTSH + " + " + displacedTSH + " - " + calcHelp1 + " - " + visualStripEnd +
                " = " + recalculatedTORA + "\n" +
                "TODA = (R) TORA" + "\n" +
                "TODA = " + recalculatedTODA + "\n" +
                "ASDA = (R) TORA" + "\n" +
                "ASDA = " + recalculatedASDA + "\n" +
                "LDA = Distance from Threshold - \n Strip End - RESA" + "\n" +
                "LDA = " + distanceFromTSH + " - " + visualStripEnd + " - " + resa + " = " + recalculatedLDA;

        runway.setTakeOffRunAvail(recalculatedTORA);
        runway.setTakeOffDistAvail(recalculatedTODA);
        runway.setAccStopDistAvail(recalculatedASDA);
        runway.setLandDistAvail(recalculatedLDA);
        runway.setClearwayLength(clearway);
        runway.setStopwayLength(stopway);

    }



    public String getCalculationBreakdown(){
        return calcB;
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

/*
    public void setOGValues(int tora, int toda, int asda, int lda){
        toraOG = tora;
        todaOG = toda;
        asdaOG = asda;
        ldaOG = lda;
    }

    public void setOGValuesToRunway(Runway runway){
        runway.setTakeOffRunAvail(toraOG);
        runway.setTakeOffDistAvail(todaOG);
        runway.setAccStopDistAvail(asdaOG);
        runway.setLandDistAvail(ldaOG);

        //System.out.println(toraOG + " " + todaOG + " " + asdaOG + " " + ldaOG);
    }

    //ah this is cancer lines but i dont want to have the bug anymore, will fix lateer

    public int getToraOG(){
        return toraOG;
    }
    public int getTodaOG(){
        return todaOG;
    }
*/


}
