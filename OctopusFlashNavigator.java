import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * Solution to Day 11: Dumbo Octopus.
 * 
 * @author bluebillxp
 */
public class OctopusFlashNavigator {

    private static class Point {
        int x;
        int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    public static void main(String[] args) {
        List<String> input = AdventHelper.readInput("input-day11-dumbo-octopus.txt");
        System.out.println("Day 11: Dumbo Octopus. " + input.size() + " lines loaded.");
        int[][] map = loadMap(input);

        System.out.println("Day 11: Dumbo Octopus. --- Part One ---");
        System.out.println("How many total flashes are there after 100 steps?");
        final int answerOne = solutionPartOne(map);
        System.out.println("Answer: " + answerOne);

        System.out.println("\nDay 11: Dumbo Octopus. --- Part Two ---");
        System.out.println("What is the first step during which all octopuses flash?");
        final int answerTwo = solutionPartTwo(map);
        System.out.println("Answer: " + answerTwo);
    }

    /**
     * Calculates how many total flashes are there after 100 steps.
     * 
     * @param map Defined input from the challenge.
     * 
     * @return answer
     */
    private static int solutionPartOne(int[][] map) {
        int totalFlashes = 0;
        for (int i = 0; i < 100; i++) {
            totalFlashes += flash(map, null, new HashSet<>());
        }
        return totalFlashes;
    }

    /**
     * Calculates the first step during which all octopuses flash.
     * 
     * @param map Defined input from the challenge.
     * 
     * @return answer
     */
    private static int solutionPartTwo(int[][] map) {
        int steps = 100; // Continued from part 1.
        int allFlashes = map.length * map.length;
        do {
            steps++;
        } while (flash(map, null, new HashSet<>()) != allFlashes);
        return steps;
    }

    private static int flash(int[][] map, Queue<Point> flashPoints, Set<String> flashedPoints) {
        Queue<Point> newFlashPoints = new LinkedList<>();
        if (flashPoints == null) {
            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map[i].length; j++) {
                    chargeFlash(i, j, map, newFlashPoints, flashedPoints);
                }
            }
        } else {
            for (Point point : flashPoints) {
                chargeFlash(point.x - 1, point.y - 1, map, newFlashPoints, flashedPoints);
                chargeFlash(point.x, point.y - 1, map, newFlashPoints, flashedPoints);
                chargeFlash(point.x + 1, point.y - 1, map, newFlashPoints, flashedPoints);
                chargeFlash(point.x - 1, point.y, map, newFlashPoints, flashedPoints);
                chargeFlash(point.x + 1, point.y, map, newFlashPoints, flashedPoints);
                chargeFlash(point.x - 1, point.y + 1, map, newFlashPoints, flashedPoints);
                chargeFlash(point.x, point.y + 1, map, newFlashPoints, flashedPoints);
                chargeFlash(point.x + 1, point.y + 1, map, newFlashPoints, flashedPoints);
            }
        }

        if (newFlashPoints.isEmpty()) {
            return 0;
        }

        return newFlashPoints.size() + flash(map, newFlashPoints, flashedPoints);
    }

    private static void chargeFlash(
        int x, int y, int[][] map, Queue<Point> flashPoints, Set<String> flashedPoints) {
        if (x < 0 || x == map.length || y < 0 || y == map.length) {
            return;
        }
        String flashTag = x + "-" + y;
        if (flashedPoints.contains(flashTag)) {
            return;
        }
        map[x][y]++;
        if (map[x][y] % 10 == 0) {
            map[x][y] = 0;
            flashPoints.add(new Point(x, y));
            flashedPoints.add(flashTag);
        }
    }

    private static int[][] loadMap(List<String> input) {
        int[][] map = new int[input.size()][input.size()];
        for (int i = 0; i < input.size(); i++) {
            String line = input.get(i);
            for (int j = 0; j < line.length(); j++) {
                map[i][j] = (line.charAt(j) - '0');
                System.out.print(map[i][j] + " ");
            }
            System.out.print("\n");
        }
        return map;
    }
}
