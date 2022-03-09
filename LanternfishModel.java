import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Solution to Day 6: Lanternfish.
 * 
 * @author bluebillxp
 */
public class LanternfishModel {
    public static void main(String[] args) {
        List<String> input = AdventHelper.readInput("input-day6-lanternfish.txt");
        System.out.println("Day 6: Lanternfish. " + input.size() + " lines loaded.");

        System.out.println("Day 6: Lanternfish. --- Part One ---");
        System.out.println("How many lanternfish would there be after 80 days?");
        final int answerOne = solutionPartOne(input);
        System.out.println("Answer: " + answerOne);

        System.out.println("\nDay 6: Lanternfish. --- Part Two ---");
        System.out.println("How many lanternfish would there be after 256 days?");
        final long answerTwo = solutionPartTwo(input);
        System.out.println("Answer: " + answerTwo);
    }

    /**
     * Calculates how many lanternfish would there be after 80 days.
     * 
     * @param input Defined input from the challenge.
     * 
     * @return answer
     */
    private static int solutionPartOne(List<String> input) {
        List<Integer> fishes = parseFishes(input.get(0));
        for (int i = 0; i < 80; i++) {
            List<Integer> newFishes = new ArrayList<>(fishes.size());
            for (Integer fish : fishes) {
                if (fish == 0) {
                    newFishes.add(6);
                    newFishes.add(8);
                } else {
                    newFishes.add(--fish);
                }
            }
            fishes = newFishes;
        }
        return fishes.size();
    }

    /**
     * Calculates how many lanternfish would there be after 256 days.
     * 
     * @param input Defined input from the challenge.
     * 
     * @return answer
     */
    private static long solutionPartTwo(List<String> input) {
        List<Integer> fishes = parseFishes(input.get(0));
        Map<Integer, Long> fishMap = new HashMap<>();
        for (Integer fish : fishes) {
            increaseMapValue(fishMap, fish, 1L);
        }
        for (int i = 0; i < 256; i++) {
            Map<Integer, Long> newFishMap = new HashMap<>();
            for (Integer fish : fishMap.keySet()) {
                Long value = fishMap.get(fish);
                if (fish == 0) {
                    increaseMapValue(newFishMap, 6, value);
                    increaseMapValue(newFishMap, 8, value);
                } else {
                    increaseMapValue(newFishMap, --fish, value);
                }
            }
            fishMap = newFishMap;
        }

        long totalFishes = 0;
        for (Long value : fishMap.values()) {
            totalFishes += value;
        }
        return totalFishes;
    }

    private static void increaseMapValue(
        Map<Integer, Long> map, Integer key, Long vlaueToIncrease) {
        Long keyValue = map.get(key);
        if (keyValue != null) {
            map.put(key, keyValue + vlaueToIncrease);
        } else {
            map.put(key, vlaueToIncrease);
        }
    }

    private static List<Integer> parseFishes(String input) {
        String[] fishes = input.split(",");
        List<Integer> fishList = new ArrayList<>(fishes.length);
        for (String fish : fishes) {
            fishList.add(Integer.valueOf(fish));
        }
        return fishList;
    }
}
