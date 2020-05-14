package Model.Data;

import Model.Objects.Airport;
import Model.Objects.Runway;
import Desktop.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class DataPrint {

    /**
     * Export data to a .txt/.jpeg/.png file
     * @param file to be written to
     * @param airport presents the current airport and data to be written to the file
     * @return true if write is successful
     */


    public static boolean exportTxtData(File file, Airport airport) {

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

    public static boolean exportJpegData(File file){
        try{ WritableImage jpegimg = new WritableImage(740, 620);
            Controller.scene.snapshot(jpegimg);
            BufferedImage jpegImage = SwingFXUtils.fromFXImage(jpegimg, null);
            BufferedImage newBuffImg = new BufferedImage(740,620, BufferedImage.TYPE_INT_RGB);
            newBuffImg.createGraphics().drawImage(jpegImage, 0, 0, Color.WHITE, null);
            jpegImage.flush();
            ImageIO.write(newBuffImg,"jpeg",new FileOutputStream(file));
            return true;
        } catch (Exception ex) { }
        return false;
    }

    public static boolean exportPngData(File file){
        try{
            WritableImage TDVimg = new WritableImage(740, 620);
            Controller.scene.snapshot(TDVimg);
            RenderedImage TDVImage = SwingFXUtils.fromFXImage(TDVimg, null);
            ImageIO.write(TDVImage,"png",new FileOutputStream(file));
            return true;
        } catch (Exception ex) { }
        return false;
    }
}
