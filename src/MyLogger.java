import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class MyLogger {


    static public void logger(String log){



        FileWriter writer = null;
        try {
            writer = new FileWriter(Controller.saveRes + Controller.separ + "result.txt", true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Date date = new Date();
        String text = log ;
        try {
            writer.write(text);
            writer.append('\n');
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    static public void loggerBrand(String log){

        FileWriter writer = null;
        try {
            writer = new FileWriter(Controller.saveRes+Controller.separ+ "resultBrand.txt", true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Date date = new Date();
        String text = log ;
        try {
            writer.write(text);
            writer.append('\n');
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
