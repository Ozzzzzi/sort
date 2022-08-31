import java.io.FileWriter;
import java.io.IOException;

public class WriteOnFile {
    public static void write(String path, String s){
        try (FileWriter fileWriter = new FileWriter(path,true)){
            fileWriter.write(s);
            fileWriter.write("\n");
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
