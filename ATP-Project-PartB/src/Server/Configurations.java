package Server;

import java.io.*;
import java.util.Properties;

public  class Configurations {





    public static String getSolveAlgorithm() {
        String algo="";
        try (InputStream input = new FileInputStream("resources/config.properties")) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);
            algo=(prop.getProperty("solveAlgorithm"));
            // get the property value

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return algo;

    }

    public static void setConfig(int max,String algorithm) throws FileNotFoundException {


        try (OutputStream output = new FileOutputStream("resources/config.properties")) {
            Properties prop = new Properties();


            // set the properties value
            prop.setProperty("MaxThreadPool", String.valueOf(max));
            prop.setProperty("solveAlgorithm", algorithm);

            // save properties to project root folder
            prop.store(output, null);

//            System.out.println(prop);//debug option

        } catch ( IOException io) {
            io.printStackTrace();
        }
    }

    public static int getMaxPool() {
        int max=0;
        try (InputStream input = new FileInputStream("resources/config.properties")) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);
            max=Integer.parseInt(prop.getProperty("MaxThreadPool"));
            // get the property value

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return max;

    }
}
