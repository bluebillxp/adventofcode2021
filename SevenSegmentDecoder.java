import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Solution to Day 8: Seven Segment Search.
 * 
 * @author bluebillxp
 */
public class SevenSegmentDecoder {

    private static class Digit {
        final int value;
        final String pattern;
        final boolean uniqueSegment;
        final int uniqueWeight;
        
        Digit(int value, String pattern, boolean uniqueSegment, int uniqueWeight) {
            this.value = value;
            this.pattern = pattern;
            this.uniqueSegment = uniqueSegment;
            this.uniqueWeight = uniqueWeight;
        }
    }

    private static class SevenSegmentDisplay {
        final Digit[] digits;
        final Map<Integer, Digit> uniqueSegmemtDigits;
        final Map<Integer, Digit> uniqueWeightMap;

        SevenSegmentDisplay(Digit[] digits) {
            this.digits = digits;
            uniqueSegmemtDigits = new HashMap<>();
            uniqueWeightMap = new HashMap<>(10);
            for (Digit digit : digits) {
                uniqueSegmemtDigits.put(digit.pattern.length(), digit);
                uniqueWeightMap.put(digit.uniqueWeight, digit);
            }
        }

        SevenSegmentDisplay() {
            this(new Digit[]{
                new Digit(0, "abcefg", false, 29),
                new Digit(1, "cf", true, 2),
                new Digit(2, "acdeg", false, 28),
                new Digit(3, "acdfg", false, 21),
                new Digit(4, "bcdf", true, 10),
                new Digit(5, "abdfg", false, 24),
                new Digit(6, "abdefg", false, 32),
                new Digit(7, "acf", true, 9),
                new Digit(8, "abcdefg", true, 33),
                new Digit(9, "abcdfg", false, 25)
            });
        }
    }

    public static void main(String[] args) {
        List<String> input = AdventHelper.readInput("input-day8-seven-segment-search.txt");
        System.out.println("Day 8: Seven Segment Search. " + input.size() + " signal lines loaded.");

        System.out.println("Day 8: Seven Segment Search. --- Part One ---");
        System.out.println("In the output values, how many times do digits 1, 4, 7, or 8 appear?");
        final int answerOne = solutionPartOne(input);
        System.out.println("Answer: " + answerOne);

        System.out.println("\nDay 8: Seven Segment Search. --- Part Two ---");
        System.out.println("What do you get if you add up all of the output values?");
        final long answerTwo = solutionPartTwo(input);
        System.out.println("Answer: " + answerTwo);
    }

    private static int solutionPartOne(List<String> input) {
        SevenSegmentDisplay display = new SevenSegmentDisplay();
        int totalCount = 0;
        for (String line : input) {
            String[] signals = line.split("\\|");
            totalCount += countUniqueSegments(display, signals[1].trim());
        }
        return totalCount;
    }

    private static long solutionPartTwo(List<String> input) {
        long total = 0;
        SevenSegmentDisplay display = new SevenSegmentDisplay();
        for (String line : input) {
            total += decode(display, line);
        }
        return total;
    }

    private static int decode(SevenSegmentDisplay display, String line) {
        String[] signals = line.split("\\|");
        String[] sources = signals[0].trim().split(" ");
        HashMap<Integer, List<String>> lengthMap = new HashMap<>();
        for (String source : sources) {
            int length = source.length();
            if (lengthMap.containsKey(length)) {
                lengthMap.get(length).add(source);
            } else {
                ArrayList<String> list = new ArrayList<>();
                list.add(source);
                lengthMap.put(length, list);
            }
        }

        // The first seen unique segments from 1, 4, 7, 8 will be assigned 
        HashMap<Character, Integer> charWeightMap = new HashMap<>();
        for (Digit digit : display.digits) {
            if (digit.uniqueSegment) {
                String source = lengthMap.get(digit.pattern.length()).get(0);
                for (int i = 0; i < source.length(); i++) {
                    Character ch = Character.valueOf(source.charAt(i));
                    if (!charWeightMap.containsKey(ch)) {
                        charWeightMap.put(ch, digit.value);
                    }
                }
            }
        }

        String[] codes = signals[1].trim().split(" ");
        int value = 0;
        for (int i = 0; i < codes.length; i++) {
            String code = codes[i];
            int weight = 0;
            for (int j = 0; j < code.length(); j++) {
                Character ch = Character.valueOf(code.charAt(j));
                weight += charWeightMap.get(ch);
            }
            int output = display.uniqueWeightMap.get(weight).value;
            value += (Math.pow(10, codes.length -1 -i) * output);
        }
        return value;
    }

    private static int countUniqueSegments(
        SevenSegmentDisplay display,
        String signal) {
        String[] codes = signal.split(" ");
        int totalCount = 0;
        for (String code : codes) {
            Digit digit = display.uniqueSegmemtDigits.get(code.length());
            if (digit.uniqueSegment) {
                totalCount++;
            }
        }
        return totalCount;
    }
}
