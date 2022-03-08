import java.util.ArrayList;
import java.util.List;

/**
 * Solution to Day 3: Binary Diagnostic.
 * 
 * @author bluebillxp
 */
public class SubmarineDiagnostor {
    
    private static class Bit {
        int zeros;
        int ones;
    }

    public static void main(String[] args) {
        List<String> input = AdventHelper.readInput("input-day3-binary-diagnostic.txt");
        System.out.println("Day 3: Binary Diagnostic: " + input.size() + " binaries loaded.");

        System.out.println("Day 3: Binary Diagnostic --- Part One ---");
        System.out.println("What is the power consumption of the submarine??");
        final int answerOne = solutionPartOne(input);
        System.out.println("Answer: " + answerOne);

        System.out.println("\nDay 3: Binary Diagnostic --- Part Two ---");
        System.out.println("What is the life support rating of the submarine?");
        final int answerTwo = solutionPartTwo(input);
        System.out.println("Answer: " + answerTwo);
    }

    private static Bit[] binarySummary(List<String> input) {
        Bit[] bits = new Bit[12];
        for (String binary : input) {
            for (int i = 0; i < binary.length(); i++) {
                if (bits[i] == null) {
                    bits[i] = new Bit();
                }
                char bin = binary.charAt(i);
                if (bin == '0') {
                    bits[i].zeros++;
                } else {
                    bits[i].ones++;
                }
            }
        }
        return bits;
    }

    /**
     * Calculates the power consumption of the submarine.
     * 
     * @param input Defined input from the challenge.
     * 
     * @return answer
     */
    private static int solutionPartOne(List<String> input) {
        Bit[] bits = binarySummary(input);
        int gamma = 0;
        int epsilon = 0;

        for (int i = 0; i < bits.length; i++) {
            Bit bit = bits[i];
            if (bit.ones > bit.zeros) {
                gamma += Math.pow(2, bits.length - 1 - i);
            } else {
                epsilon += Math.pow(2, bits.length - 1 - i);
            }
        }
        return gamma * epsilon;
    }

    /**
     * Calculates the life support rating of the submarine.
     * 
     * @param input Defined input from the challenge.
     * 
     * @return answer
     */
    private static int solutionPartTwo(List<String> input) {
        Bit[] bits = binarySummary(input);
        String oxygenGenRating = locateOxygenGenRating(input, bits, 0);
        String co2ScrubRating = locateCo2ScrubRating(input, bits, 0);

        int oxygenRating = 0;
        int co2Rating = 0;
        for (int i = 0; i < bits.length; i++) {
            if (oxygenGenRating.charAt(i) == '1') {
                oxygenRating += Math.pow(2, bits.length - 1 - i);
            }
            if (co2ScrubRating.charAt(i) == '1') {
                co2Rating += Math.pow(2, bits.length - 1 - i);
            }
        }

        return oxygenRating * co2Rating;
    }

    private static String locateOxygenGenRating(List<String> binaries, Bit[] bits, int pos) {
        if (binaries.size() == 1) {
            return binaries.get(0);
        }

        List<String> nextBinaries = new ArrayList<>();
        for (String binary : binaries) {
            if (bits[pos].ones >= bits[pos].zeros) {
                if (binary.charAt(pos) == '1') {
                    nextBinaries.add(binary);
                }
            } else {
                if (binary.charAt(pos) == '0') {
                    nextBinaries.add(binary);
                }
            }
        }
        Bit[] nextBits = binarySummary(nextBinaries);
        return locateOxygenGenRating(nextBinaries, nextBits, ++pos);
    }

    private static String locateCo2ScrubRating(List<String> binaries, Bit[] bits, int pos) {
        if (binaries.size() == 1) {
            return binaries.get(0);
        }

        List<String> nextBinaries = new ArrayList<>();
        for (String binary : binaries) {
            if (bits[pos].ones >= bits[pos].zeros) {
                if (binary.charAt(pos) == '0') {
                    nextBinaries.add(binary);
                }
            } else {
                if (binary.charAt(pos) == '1') {
                    nextBinaries.add(binary);
                }
            }
        }
        Bit[] nextBits = binarySummary(nextBinaries);
        return locateCo2ScrubRating(nextBinaries, nextBits, ++pos);
    }
}
