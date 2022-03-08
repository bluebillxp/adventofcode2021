import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Solution to solve Advent of Code 2021.
 * D1 - Sonor Sweep
 * 
 * @author bluebillxp
 */
public class SonarSweeper {
    
    public static void main(String[] args) {
        List<String> input = inputToList("input-sonarsweep");
        System.out.println("Sonar Sweep: " + input.size() + " depths loaded.");

        System.out.println("Sonar Sweep --- Part One ---");
        System.out.println("How many measurements are larger than the previous measurement?");
        final int answerOne = solutionPartOne(input);
        System.out.println("Answer: " + answerOne + " measurements.");

        System.out.println("\nSonar Sweep --- Part Two ---");
        System.out.println("How many sums are larger than the previous sum?");
        final int answerTwo = solutionPartTwo(input);
        System.out.println("Answer: " + answerTwo + " sums.");
    }

    /**
     * Calculates how many measurements are larger than the previous measurement.
     * 
     * @param input Defined input from the challenge.
     * 
     * @return answer
     */
    private static int solutionPartOne(List<String> input) {
        int incCnt = 0;
        for (int i = 1; i < input.size(); i++) {
            if (Integer.valueOf(input.get(i)) > Integer.valueOf(input.get(i - 1))) {
                incCnt++;
            }
        }
        return incCnt;
    }

    /**
     * Calculates how many sums are larger than the previous sum.
     * 
     * @param input Defined input from the challenge.
     * 
     * @return answer
     */
    private static int solutionPartTwo(List<String> input) {
        int incCnt = 0;
        for (int i = 0; i < input.size() - 3; i++) {
            int sumCurrent = Integer.valueOf(input.get(i)) + Integer.valueOf(input.get(i + 1)) + Integer.valueOf(input.get(i + 2));
            int sumNext = Integer.valueOf(input.get(i + 1)) + Integer.valueOf(input.get(i + 2)) + Integer.valueOf(input.get(i + 3));
            if (sumNext > sumCurrent) {
                incCnt++;
            }
        }
        return incCnt;
    }

    private static List<String> inputToList(String path) {
        List<String> list = new ArrayList<>();
        File input = new File("input-sonarsweep.txt");
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
