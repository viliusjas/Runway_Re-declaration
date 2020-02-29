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


    public Calculator(){}

    public Calculator(int visualStripEnd, int visualStripWidth, int resa){
        this.visualStripEnd = visualStripEnd;
        this.visualStripWidth = visualStripWidth;
        this.resa = resa;
    }

    private Queue<Calculations> calculations;


    public void testCalc(Runway runway, Obstacle obstacle, int tora, int toda, int asda, int lda){

    }

    public static Runway calculate(Runway runway, List<Obstacle> obstacles) {

        return null;
    }

    public static boolean runwayIsSafe(Runway runway, Aircraft aircraft, boolean takeoff) {
        return false;
    }

    class Calculations {
        Runway runway;
        int tora;
        int toda;
        int asda;
        int lda;

        public Calculations(Runway runway, int tora, int toda, int asda, int lda) {
            this.runway = runway;
            this.tora = tora;
            this.toda = toda;
            this.asda = asda;
            this.lda = lda;
        }
    }



}
