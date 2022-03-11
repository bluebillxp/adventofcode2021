import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Solution to Day 9: Smoke Basin.
 * 
 * @author bluebillxp
 */
public class HeightmapReader {

    private static class Location {
        int x;
        int y;
        int value;

        Location(int x, int y, int value) {
            this.x = x;
            this.y = y;
            this.value = value;
        }

        Location(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    public static void main(String[] args) {
        List<String> input = AdventHelper.readInput("input-day9-smoke-basin.txt");
        System.out.println("Day 9: Smoke Basin " + input.size() + " lines loaded.");

        System.out.println("Day 9: Smoke Basin --- Part One ---");
        System.out.println("What is the sum of the risk levels of all low points on your heightmap?");
        List<Location> lowPoints = new ArrayList<>();
        final int answerOne = solutionPartOne(input, lowPoints);
        System.out.println("Answer: " + answerOne);

        System.out.println("\nDay 9: Smoke Basin --- Part Two ---");
        System.out.println("What do you get if you multiply together the sizes of the three largest basins?");
        final int answerTwo = solutionPartTwo(input, lowPoints);
        System.out.println("Answer: " + answerTwo);
    }

    /**
     * Calculates the sum of the risk levels of all low points on your heightmap.
     * 
     * @param input Defined input from the challenge.
     * @param lowPoints Output list of low points.
     * 
     * @return answer
     */
    private static int solutionPartOne(List<String> input, List<Location> lowPoints) {
        int riskLevel = 0;
        for (int i = 0; i < input.size(); i++) {
            String upperLine = null;
            String currentLine = input.get(i);
            String bottomLine = null;
            if (i != 0) {
                upperLine = input.get(i-1);
            }
            if (i != input.size() - 1) {
                bottomLine = input.get(i+1);
            }
            for (int j = 0; j < currentLine.length(); j++) {
                int center = currentLine.charAt(j) - '0';
                int left = center + 1;
                int right = center + 1;
                int up = center + 1;
                int down = center + 1;
                if (j != 0) {
                    left = currentLine.charAt(j -1) - '0';
                }
                if (j != currentLine.length() - 1) {
                    right = currentLine.charAt(j+1) - '0';
                }
                if (upperLine != null) {
                    up = upperLine.charAt(j) - '0';
                }
                if (bottomLine != null) {
                    down = bottomLine.charAt(j) - '0';
                }
                if (left > center && right > center && up > center && down > center) {
                    riskLevel += (center + 1);
                    Location lowPoint = new Location(j, i, center);
                    lowPoints.add(lowPoint);
                }
            }
        }
        return riskLevel;
    }

    /**
     * Calculates the multiplied sizes of the three largest basins.
     * 
     * @param input Defined input from the challenge.
     * @param lowPoints List of low points.
     * 
     * @return answer
     */
    private static int solutionPartTwo(List<String> input, List<Location> lowPoints) {
        ArrayList<Integer> basinValues = new ArrayList<>(lowPoints.size()); 
        for (Location lowPoint : lowPoints) {
            Set<String> visitedLocations = new HashSet<>();
            int basin = accmulateBasin(lowPoint, null, visitedLocations, input);
            basinValues.add(basin);
        }
        Collections.sort(basinValues);
        return basinValues.get(basinValues.size() - 1) * basinValues.get(basinValues.size() - 2) * basinValues.get(basinValues.size() - 3);
    }

    private static int accmulateBasin(
        Location center, Location adjacent, Set<String> visitedLocations, List<String> input) {
        center.value = (input.get(center.y).charAt(center.x) - '0');
        if (center.value == 9
            || visitedLocations.contains(center.x + "-" + center.y)
            || (adjacent != null && adjacent.value > center.value)) {
            return 0;
        }
        visitedLocations.add(center.x + "-" + center.y);
        int basin = 1;
        // Left
        if (center.x != 0) {
            int leftX = center.x - 1;
            int leftY = center.y;
            basin += accmulateBasin(new Location(leftX, leftY), center, visitedLocations, input);
        }
        // Right
        if (center.x != input.get(0).length() - 1) {
            int rightX = center.x + 1;
            int rightY = center.y;
            basin += accmulateBasin(new Location(rightX, rightY), center, visitedLocations, input);
        }
        // Up
        if (center.y != 0) {
            int upX = center.x;
            int upY = center.y - 1;
            basin += accmulateBasin(new Location(upX, upY), center, visitedLocations, input);
        }
        // Down
        if (center.y != input.size() - 1) {
            int downX = center.x;
            int downY = center.y + 1;
            basin += accmulateBasin(new Location(downX, downY), center, visitedLocations, input);
        }

        return basin;
    }
}
