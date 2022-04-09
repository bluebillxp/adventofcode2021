import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Solution to Day 14: Extended Polymerization.
 * 
 * @author bluebillxp
 */
public class PolymerFactory {
    public static void main(String[] args) {
        List<String> input = AdventHelper.readInput("input-day14-extended-polymerization.txt");
        // List<String> input = AdventHelper.readInput("input-day14-test.txt");
        System.out.println("Day 14: Extended Polymerization: " + input.size() + " lines loaded.");

        System.out.println("Day 14: Extended Polymerization --- Part One ---");
        System.out.println("10 steps - What do you get if you take the quantity of the most common element and subtract the quantity of the least common element?");
        final int answerOne = solutionPartOne(input);
        System.out.println("Answer: " + answerOne);

        System.out.println("\nDay 14: Extended Polymerization --- Part Two ---");
        System.out.println("40 steps - What do you get if you take the quantity of the most common element and subtract the quantity of the least common element?");
        final int answerTwo = solutionPartTwo(input);
        System.out.println("Answer: " + answerTwo);
    }

    private static int solutionPartOne(List<String> input) {
        Map<String, Integer> pairMap = new HashMap<>();
        for (int i = 2; i < input.size(); i++) {
            String[] pair = input.get(i).split(" -> ");
            pairMap.put(pair[0].trim(), pair[1].trim().charAt(0) - 'A');
        }

        char[] template = input.get(0).toCharArray();
        for (int step = 0; step < 10; step++) {
            char[] newTemplate = new char[template.length * 2 -1];
            int index = 0;
            for (int c = 0; c < template.length - 1; c++) {
                Integer insertion = pairMap.get(String.format("%c%c", template[c], template[c+1]));
                if (insertion != null) {
                    newTemplate[index] = template[c];
                    newTemplate[index + 1] = (char) ('A' + insertion.intValue());
                    index += 2;
                } else {
                    newTemplate[index] = template[c];
                    index += 1;
                }
            }
            newTemplate[index] = template[template.length - 1];
            template = newTemplate;
        }

        int[] elementMap = new int[26];
        for (char ch : new String(template).toCharArray()) {
            if (ch - 'A' < 0) {
                break;
            }
            elementMap[ch - 'A']++;
        }
        int maxElement = 0;
        int minElement = 0;
        for (int i = 0; i < elementMap.length; i++) {
            if (elementMap[i] > elementMap[maxElement]) {
                maxElement = i;
            }
            if ((elementMap[i] != 0 && elementMap[i] < elementMap[minElement])
                || elementMap[minElement] == 0) {
                minElement = i;
            }
        }
        return elementMap[maxElement] - elementMap[minElement];
    }

    private static int solutionPartTwo(List<String> input) {

        return 0;
    }
}
