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

    private Queue<Calculations> calculations;


    public void testCalc(Runway runway, Obstacle obstacle, int tora, int toda, int asda, int lda){

    }

    public static Runway calculate(Runway runway, List<Obstacle> obstacles) {

        return null;
    }

    public static boolean runwayIsSafe(Runway runway, Aircraft aircraft, boolean takeoff) {
        return false;
    }




}
