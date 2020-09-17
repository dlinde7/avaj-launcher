package simulator;
import aircrafts.AircraftFactory;
import weather.WeatherProvider;

import java.io.*;

public class Simulator {

    public static PrintWriter writer;
    public static int cycles;

    public static void main(String[] arg){
        if (arg.length < 1){
            return;
        } 
        else if (arg.length > 1){
            System.out.println("To many arguments");
            return;
        }
        String scenario = arg[0];

        File simulationFile = new File("simulation.txt");
        try {
            writer = new PrintWriter(simulationFile);
        } catch (FileNotFoundException fne) {
            System.out.println("Error: " + fne.getMessage());
            return;
        }

        AircraftFactory aircraftFactory = new AircraftFactory();
        WeatherTower weatherTower = new WeatherTower();
        try {
            FileInputStream fstream = new FileInputStream(scenario);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            int line = 1;
            String[] splitstr;

            while ((strLine = br.readLine()) != null)
            {
                if (line == 1){
                    try {
                        cycles = Integer.parseInt(strLine);
                        if (cycles < 0)
                        {
                            System.out.println("Error: first line of scenario file must be a POSITIVE integer.");
                            return;
                        }
                    } catch (NumberFormatException nfe) {
                        System.out.println("Error: first line of scenario file must be an integer.");
                        return;
                    }
                }
                else {
                    splitstr = strLine.split(" ");
                    if (splitstr.length == 1 && splitstr[0].isEmpty()){
                        continue;
                    }
                    if (splitstr.length != 5){
                        throw new Exception("Error: line " + line + ": there must be 5 parameters.");
                    }

                    try {
                        aircraftFactory.newAircraft(
                                splitstr[0],
                                splitstr[1],
                                Integer.parseInt(splitstr[2]),
                                Integer.parseInt(splitstr[3]),
                                Integer.parseInt(splitstr[4])
                        ).registerTower(weatherTower);
                    } catch (NumberFormatException nfe) {
                        System.out.println("Error: line " + line + ": parameter 3 to 5 must be integers.");
                        return;
                    } catch (Exception ex) {
                        System.out.println("Error: " + ex.getMessage());
                        return;
                    }
                }
                line++;
            }

            br.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        while (cycles > 0)
        {
            weatherTower.changeWeather();
            writer.println("#----------#");
            cycles--;
        }
        writer.close();
    }
}