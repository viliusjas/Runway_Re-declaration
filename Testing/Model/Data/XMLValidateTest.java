package Model.Data;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class XMLValidateTest {

    private static File aircraftValidXML = new File("src/aircraft.xml");
    private static File obstacleValidXML = new File("src/obstacle.xml");
    private static File airportValidXML = new File("src/airport.xml");

    private static File aircraftSchema = new File("src/Model/Data/XMLSchema/aircraft.xsd");
    private static File airportSchema = new File("src/Model/Data/XMLSchema/airport.xsd");
    private static File obstacleSchema = new File("src/Model/Data/XMLSchema/obstacle.xsd");

    @Test
    void testAircraftValidXML() {
        boolean valid = XMLValidate.validate(aircraftValidXML, aircraftSchema);

        assertEquals(true, valid);
    }

    @Test
    void testAirportValidXML() {
        boolean valid = XMLValidate.validate(airportValidXML, airportSchema);

        assertEquals(true, valid);
    }


    @Test
    void testObstacleValidXML() {
        boolean valid = XMLValidate.validate(obstacleValidXML, obstacleSchema);

        assertEquals(true, valid);
    }

    @Test
    void testAircraftInvalidXML() {
        boolean invalid = XMLValidate.validate(obstacleValidXML, aircraftSchema);

        assertEquals(false, invalid);
    }

    @Test
    void testAirportInvalidXML() {
        boolean invalid = XMLValidate.validate(aircraftValidXML, airportSchema);

        assertEquals(false, invalid);
    }

    @Test
    void testObstacleInvalidXML() {
        boolean invalid = XMLValidate.validate(airportValidXML, obstacleSchema);

        assertEquals(false, invalid);
    }

    @Test
    void testNullXMLFiles() {
        boolean invalid = XMLValidate.validate(null, airportSchema);

        assertEquals(false, invalid);
    }


}