import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Solution to Day 13: Transparent Origami.
 * 
 * @author bluebillxp
 */
public class ManualDecoder {

    private static class Point {
        int x;
        int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    public static void main(String[] args) {
        List<String> input = AdventHelper.readInput("input-day13-transparent-origami.txt");
        // List<String> input = AdventHelper.readInput("input-day13-test.txt");
        System.out.println("Day 13: Transparent Origami: " + input.size() + " points loaded.");

        System.out.println("Day 13: Transparent Origami --- Part One ---");
        System.out.println("How many dots are visible after completing just the first fold instruction on your transparent paper?");
        final int answerOne = solutionPartOne(input);
        System.out.println("Answer: " + answerOne);

        System.out.println("\nDay 13: Transparent Origami --- Part Two ---");
        System.out.println("What code do you use to activate the infrared thermal imaging camera system?");
        final int answerTwo = solutionPartTwo(input);
        System.out.println("Answer: " + answerTwo);
    }

    private static int solutionPartOne(List<String> input) {
        Set<String> uniquePoints = new HashSet<>();
        for (String line : input) {
            if (line.isEmpty()) {
                break;
            }
            String[] xy = line.split(",");
            Point point = new Point(Integer.valueOf(xy[0]), Integer.valueOf(xy[1]));
            Point newPoint = projectFirstFold(point);
            if (newPoint != null) {
                uniquePoints.add(newPoint.x + "," + newPoint.y);
            }
        }
        return uniquePoints.size();
    }

    private static Point projectFirstFold(Point point) {
        // fold along x=655
        int firstFoldX = 655;
        Point newPoint = new Point(point.x, point.y);
        if (newPoint.x < firstFoldX) {
            return newPoint;
        }
        // Dots will never appear exactly on a fold line.
        if (newPoint.x == firstFoldX) {
            return null;
        }
        newPoint.x = firstFoldX * 2 - newPoint.x;
        return newPoint;
    }

    private static int solutionPartTwo(List<String> input) {
        Set<String> uniquePoints = new HashSet<>();
        for (String line : input) {
            if (line.isEmpty()) {
                break;
            }
            String[] xy = line.split(",");
            Point point = new Point(Integer.valueOf(xy[0]), Integer.valueOf(xy[1]));
            Point newPoint = projectAll(point);
            if (newPoint != null) {
                uniquePoints.add(newPoint.x + "," + newPoint.y);
            }
        }

        int totalX = 81;
        int totalY = 6;
        for (int y = 0; y < totalY; y++) {
            char[] line = new char[totalX];
            for (int x = 0; x < totalX; x++) {
                if (uniquePoints.contains(x + "," + y)) {
                    line[x] = '*';
                } else {
                    line[x] = ' ';
                }
            }
            System.out.println(new String(line));
        }
        return uniquePoints.size();
    }

    private static Point projectAll(Point point) {
        // fold along x=655
        // fold along y=447
        // fold along x=327
        // fold along y=223
        // fold along x=163
        // fold along y=111
        // fold along x=81
        // fold along y=55
        // fold along x=40
        // fold along y=27
        // fold along y=13
        // fold along y=6
        int[] foldXTimes = new int[] {655, 327, 163, 81, 40};
        int[] foldYTimes = new int[] {447, 223, 111, 55, 27, 13, 6};
        Point newPoint = new Point(point.x, point.y);
        for (int foldX : foldXTimes) {
            if (newPoint.x < foldX) {
                continue;
            }
            // Dots will never appear exactly on a fold line.
            if (newPoint.x == foldX) {
                return null;
            }
            newPoint.x = foldX * 2 - newPoint.x;
        }
        for (int foldY : foldYTimes) {
            if (newPoint.y < foldY) {
                continue;
            }
            // Dots will never appear exactly on a fold line.
            if (newPoint.y == foldY) {
                return null;
            }
            newPoint.y = foldY * 2 - newPoint.y;
        }
        return newPoint;
    }
}
