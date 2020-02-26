package Model.Objects;

import java.util.ArrayList;
import java.util.List;

public class Airport {

    /**
     * @airportName
     * @airportRunways list that contains the runways belonging to that airport
     *
     * @ascentAngle
     * @descentAngle
     */

    private String airportName;
    private List<Runway> airportRunways;

    private double ascentAngle;
    private double descentAngle;

    public Airport(String airportName) {
        this.airportName = airportName;
        this.airportRunways = new ArrayList<>();
    }

    public String getAirportName() {
        return airportName;
    }

    public void addRunway(Runway runway) {
        this.airportRunways.add(runway);
    }

    public List<Runway> getAirportRunways() {
        return this.airportRunways;
    }

    public double getAscentAngle() {
        return ascentAngle;
    }

    public void setAscentAngle(double ascentAngle) {
        this.ascentAngle = ascentAngle;
    }

    public double getDescentAngle() {
        return descentAngle;
    }

    public void setDescentAngle(double descentAngle) {
        this.descentAngle = descentAngle;
    }
}
