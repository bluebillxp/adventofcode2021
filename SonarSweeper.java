import java.util.List;

/**
 * Solution to Day 1 - Sonar Sweep.
 * 
 * @author bluebillxp
 */
public class SonarSweeper {
    
    public static void main(String[] args) {
        List<String> input = AdventHelper.readInput("input-day1-sonar-sweep.txt");
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
}
