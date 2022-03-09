import java.util.Arrays;
import java.util.List;

/**
 * Solution to Day 7: The Treachery of Whales.
 * 
 * @author bluebillxp
 */
public class CrabSubmarineFuelSaver {
    public static void main(String[] args) {
        List<String> input = AdventHelper.readInput("input-day7-the-treachery-of-whales.txt");
        String[] strValues = input.get(0).split(",");
        int[] crabPositions = new int[strValues.length];
        for (int i = 0; i < strValues.length; i++) {
            crabPositions[i] = Integer.valueOf(strValues[i]);
        }
        Arrays.sort(crabPositions);
        System.out.println("Day 7: The Treachery of Whales " + crabPositions.length + " crabs located.");

        System.out.println("Day 7: The Treachery of Whales --- Part One ---");
        System.out.println("How much fuel must they spend to align to that position?");
        final int answerOne = solutionPartOne(crabPositions);
        System.out.println("Answer: " + answerOne);

        System.out.println("\nDay 7: The Treachery of Whales --- Part Two ---");
        System.out.println("How much fuel must they spend to align to that position?");
        final long answerTwo = solutionPartTwo(crabPositions);
        System.out.println("Answer: " + answerTwo);
    }

    /**
     * Calculates how much fuel must they spend to align to that position.
     * 
     * @param crabPositions Defined positions of crabs from the challenge.
     * 
     * @return answer
     */
    private static int solutionPartOne(int[] crabPositions) {
        int median = 0;
        if (crabPositions.length % 2 == 0) {
            median = ((crabPositions[crabPositions.length/2] + crabPositions[((crabPositions.length/2) - 1)]) / 2);
        } else {
            median = crabPositions[crabPositions.length/2];
        }
        int fuelSpent = 0;
        for (int crabPos : crabPositions) {
            fuelSpent += Math.abs(crabPos - median);
        }
        return fuelSpent;
    }

    /**
     * Calculates how much fuel must they spend to align to that position.
     * 
     * @param crabPositions Defined positions of crabs from the challenge.
     * 
     * @return answer
     */
    private static long solutionPartTwo(int[] crabPositions) {
        int totalSum = 0;
        for (int crabPos : crabPositions) {
            totalSum += crabPos;
        }
        double avg = Math.floor((double) totalSum / (double) crabPositions.length);
        int fuelSpent = 0;
        for (int crabPos : crabPositions) {
            for (int crabFuel = 1; crabFuel <= Math.abs(crabPos - avg); crabFuel++) {
                fuelSpent += crabFuel;
            }
        }
        return fuelSpent;
    }
}
