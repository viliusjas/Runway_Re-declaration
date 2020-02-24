package Model.Objects;

public class Aircraft {

    /**
     * @model the aircraft's model
     * @engineBlast safe distance behind the aircraft
     */

    private String model;
    private int engineBlast;

    public Aircraft(String model) {
        this.model = model;
    }

    public void setEngineBlast(int engineBlast) {
        this.engineBlast = engineBlast;
    }

    public int getEngineBlast() {
        return this.engineBlast;
    }

}
