package Model.Data;

import Model.Objects.Airport;
import Model.Objects.Runway;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class DataPrint {

    /**
     * Export data to a .txt file
     * @param file to be written to
     * @param airport presents the current airport and data to be written to the file
     * @return true if write is successful
     */

    public static boolean exportData(File file, Airport airport) {

        try {
            PrintWriter writer;
            writer = new PrintWriter(file);

            writer.println("Airport: " + airport.getAirportName());
            writer.println("Number of runways: " + airport.getAirportRunways().size());

            writer.println("------------------------------------");

            for(Runway runway : airport.getAirportRunways()) {
                writer.println("Runway : " + runway.getRunwayNumber());
                writer.println("TORA: " + runway.getTakeOffRunAvail());
                writer.println("TODA: " + runway.getTakeOffDistAvail());
                writer.println("ASDA: " + runway.getAccStopDistAvail());
                writer.println("LDS: " + runway.getLandDistAvail());
                writer.println("Stopway: " + runway.getStopwayLength());
                writer.println("Clearway: " + runway.getClearwayLength());
                writer.println("Runway end: " + runway.getRunwayEndSafeArea());
                writer.println("Strip end: " + runway.getStripEnd());
                writer.println("Approach landing surface: " + runway.getApprochLandSurf());
                writer.println("Takeoff climbing surface: " + runway.getTakeoffClimbSurf());
                writer.println("------------------------------------");
            }

            writer.close();
            return true;
        } catch (IOException ex) { }

        return false;
    }
}
