import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper class.
 * 
 * @author bluebillxp
 */
public class AdventHelper {
    
    public static List<String> readInput(String path) {
        List<String> list = new ArrayList<>();
        File input = new File(path);
        try (BufferedReader br
                = new BufferedReader(new FileReader(input))) {
            String line;
            while ((line = br.readLine()) != null) {
                list.add(line);
                // System.out.println("Input:  " + line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
