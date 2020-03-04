package Model.Data;

import Model.Objects.Airport;
import Model.Objects.Runway;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import Model.Objects.*;

public class XMLImport {

    /**
     * @aircraftSchema the xsd schema for aircraft XML files
     * @airportSchema the xsd schema for airport XML files
     * @obstacleSchema the xsd schema for obstacle XML files
     */

    private static File aircraftSchema = new File("src/Model/Data/aircraft.xsd");
    private static File airportSchema = new File("src/Model/Data/airport.xsd");
    private static File obstacleSchema = new File("src/Model/Data/obstacle.xsd");

    /**
     * Parse an XML file to import airport and its runways / list of obstacles / list of aircraft
     * @param file to be parsed
     * @return true if parsing is successful
     */

    public static Airport importAirportXML(File file) throws Exception {

        if(!(XMLValidate.validate(file, airportSchema))) {
            throw new Exception("Invalid XML file");
        }

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);

        doc.getDocumentElement().normalize();
        System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

        String name = doc.getElementsByTagName("name").item(0).getTextContent();
        System.out.println(name);

        String descentAngle = doc.getElementsByTagName("descent").item(0).getTextContent();
        System.out.println(descentAngle);

        String ascentAngle = doc.getElementsByTagName("ascent").item(0).getTextContent();
        System.out.println(ascentAngle);

        Airport airport = new Airport(name);
        airport.setAscentAngle(Double.parseDouble(ascentAngle));
        airport.setDescentAngle(Double.parseDouble(descentAngle));


        NodeList nList = doc.getElementsByTagName("runway");
        System.out.println("----------------------------");

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            System.out.println("\nCurrent Element :" + nNode.getNodeName());

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) nNode;

                int runwayNumber = Integer.parseInt(element.getElementsByTagName("number").item(0).getTextContent());
                String position = element.getElementsByTagName("position").item(0).getTextContent();
                RunwayPosition runwayPosition = RunwayPosition.NONE; // Default

                if(position.equals("L")) {
                    runwayPosition = RunwayPosition.LEFT;
                } else if(position.equals("R")) {
                    runwayPosition = RunwayPosition.RIGHT;
                }

                Runway runway = new Runway(runwayNumber, runwayPosition);

                int tora = Integer.parseInt(element.getElementsByTagName("tora").item(0).getTextContent());
                int toda = Integer.parseInt(element.getElementsByTagName("toda").item(0).getTextContent());
                int asda = Integer.parseInt(element.getElementsByTagName("asda").item(0).getTextContent());
                int lsd = Integer.parseInt(element.getElementsByTagName("lsd").item(0).getTextContent());
                int stopway = Integer.parseInt(element.getElementsByTagName("stopway").item(0).getTextContent());
                int clearway = Integer.parseInt(element.getElementsByTagName("clearway").item(0).getTextContent());
                int runwayEnd = Integer.parseInt(element.getElementsByTagName("runwayEnd").item(0).getTextContent());
                int stripEnd = Integer.parseInt(element.getElementsByTagName("stripEnd").item(0).getTextContent());
                int approachLandSurf = Integer.parseInt(element.getElementsByTagName("approachLandSurf").item(0).getTextContent());
                int takeoffClimbSurf = Integer.parseInt(element.getElementsByTagName("takeoffClimbSurf").item(0).getTextContent());
                int visualStripWidth = Integer.parseInt(element.getElementsByTagName("visualStripWidth").item(0).getTextContent());


                runway.setTakeOffRunAvail(tora);
                runway.setTakeOffDistAvail(toda);
                runway.setAccStopDistAvail(asda);
                runway.setLandDistAvail(lsd);
                runway.setStopwayLength(stopway);
                runway.setClearwayLength(clearway);
                runway.setRunwayEndSafeArea(runwayEnd);
                runway.setStripEnd(stripEnd);
                runway.setApprochLandSurf(approachLandSurf);
                runway.setTakeoffClimbSurf(takeoffClimbSurf);
                runway.setVisualStripWidth(visualStripWidth);

                airport.addRunway(runway);

            }
        }

        return airport;
    }

    public static List<Obstacle> importObstaclesXML (File file) throws Exception {

        if(!(XMLValidate.validate(file, obstacleSchema))) {
            throw new Exception("Invalid XML file");
        }

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);

        doc.getDocumentElement().normalize();
        System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

        List<Obstacle> obstacles = new ArrayList<>();

        NodeList nList = doc.getElementsByTagName("obstacle");
        System.out.println("----------------------------");

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            System.out.println("\nCurrent Element :" + nNode.getNodeName());

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) nNode;

                int height = Integer.parseInt(element.getElementsByTagName("height").item(0).getTextContent());
                int length = Integer.parseInt(element.getElementsByTagName("length").item(0).getTextContent());
                int leftThreshold = Integer.parseInt(element.getElementsByTagName("leftThreshold").item(0).getTextContent());
                int rightThreshold = Integer.parseInt(element.getElementsByTagName("rightThreshold").item(0).getTextContent());
                String name = element.getElementsByTagName("name").item(0).getTextContent();


                Obstacle obstacle = new Obstacle(length, height, leftThreshold, rightThreshold);
                obstacle.setName(name);
                obstacles.add(obstacle);
            }
        }

        return obstacles;

    }

    public static List<Aircraft> importAircraftXML (File file) throws Exception {

        if(!(XMLValidate.validate(file, aircraftSchema))) {
            throw new Exception("Invalid XML file");
        }

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);

        doc.getDocumentElement().normalize();
        System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

        List<Aircraft> aircrafts = new ArrayList<>();

        NodeList nList = doc.getElementsByTagName("aircraft");
        System.out.println("----------------------------");

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            System.out.println("\nCurrent Element :" + nNode.getNodeName());

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) nNode;

                String model = element.getElementsByTagName("model").item(0).getTextContent();
                int engineBlast = Integer.parseInt(element.getElementsByTagName("engineBlast").item(0).getTextContent());
                Aircraft aircraft = new Aircraft(model);
                aircraft.setEngineBlast(engineBlast);
                aircrafts.add(aircraft);
            }
        }

        return aircrafts;
    }
}
