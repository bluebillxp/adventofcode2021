import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Solution to Day 5: Hydrothermal Venture.
 * 
 * @author bluebillxp
 */
public class HydrothermalVentSaver {

    private static class Coordinate {
        int x;
        int y;
        int overlaps;

        Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
            overlaps = 1;
        }
    }

    private static class Line {
        Coordinate start;
        Coordinate end;

        Line(Coordinate start, Coordinate end) {
            this.start = start;
            this.end = end;
        }
    }

    private static class Route {
        Coordinate[][] coordinates = new Coordinate[1000][1000];
        Set<Coordinate> overlappedCoordinates = new HashSet<>();
    }

    public static void main(String[] args) {
        List<String> input = AdventHelper.readInput("input-day5-hydrothermal-venture.txt");
        List<Line> lines = parseLines(input);
        System.out.println("Day 5: Hydrothermal Venture. " + lines.size() + " lines loaded.");

        System.out.println("Day 5: Hydrothermal Venture. --- Part One ---");
        System.out.println("Consider only horizontal and vertical lines. At how many points do at least two lines overlap?");
        final int answerOne = solutionPartOne(lines);
        System.out.println("Answer: " + answerOne);

        System.out.println("\nDay 5: Hydrothermal Venture. --- Part Two ---");
        System.out.println("Consider all of the lines. At how many points do at least two lines overlap?");
        final int answerTwo = solutionPartTwo(lines);
        System.out.println("Answer: " + answerTwo);
    }

    /**
     * Calculates how many points do at least two lines overlap
     * when consider only horizontal and vertical lines.
     * 
     * @param lines Defined lines from the challenge.
     * 
     * @return answer
     */
    private static int solutionPartOne(List<Line> lines) {
        Route route = new Route();
        for (Line line : lines) {
            drawLine(route, line, false);
        }
        return computePoints(route);
    }

    /**
     * Calculates how many points do at least two lines overlap
     * when consider all of the lines.
     * 
     * @param lines Defined lines from the challenge.
     * 
     * @return answer
     */
    private static int solutionPartTwo(List<Line> lines) {
        Route route = new Route();
        for (Line line : lines) {
            drawLine(route, line, true);
        }
        return computePoints(route);
    }

    private static void drawLine(Route route, Line line, boolean diagonal) {
        if (line.start.x == line.end.x) {
            int startY = Math.min(line.start.y, line.end.y);
            int endY = Math.max(line.start.y, line.end.y);
            for (int i = startY; i <= endY; i++) {
                Coordinate coordinate = route.coordinates[line.start.x][i];
                // Already lined.
                if (coordinate != null) {
                    coordinate.overlaps++;
                    route.overlappedCoordinates.add(coordinate);
                } else {
                    coordinate = new Coordinate(line.start.x, i);
                    route.coordinates[line.start.x][i] = coordinate;
                }
            }
        }
        
        if (line.start.y == line.end.y) {
            int startX = Math.min(line.start.x, line.end.x);
            int endX = Math.max(line.start.x, line.end.x);
            for (int i = startX; i <= endX; i++) {
                Coordinate coordinate = route.coordinates[i][line.start.y];
                // Already lined.
                if (coordinate != null) {
                    coordinate.overlaps++;
                    route.overlappedCoordinates.add(coordinate);
                } else {
                    coordinate = new Coordinate(i, line.start.y);
                    route.coordinates[i][line.start.y] = coordinate;
                }
            }
        }

        if (!diagonal) {
            return;
        }

        int sideX = Math.abs(line.start.x - line.end.x);
        int sideY = Math.abs(line.start.y - line.end.y);
        if (sideX == sideY) {
            for (int i = 0; i <= sideX; i++) {
                int movingX = line.start.x;
                int movingY = line.start.y;
                if (line.start.x >= line.end.x) {
                    movingX -= i;
                } else {
                    movingX += i;
                }
                if (line.start.y >= line.end.y) {
                    movingY -= i;
                } else {
                    movingY += i;
                }
                Coordinate coordinate = route.coordinates[movingX][movingY];
                // Already lined.
                if (coordinate != null) {
                    coordinate.overlaps++;
                    route.overlappedCoordinates.add(coordinate);
                } else {
                    coordinate = new Coordinate(movingX, movingY);
                    route.coordinates[movingX][movingY] = coordinate;
                }
            }
        }
    }

    private static List<Line> parseLines(List<String> input) {
        List<Line> lines = new ArrayList<>(input.size());
        for (String raw : input) {
            if (raw.isEmpty()) {
                continue;
            }
            String[] splits = raw.split("->");
            int x = Integer.valueOf(splits[0].split(",")[0].trim());
            int y = Integer.valueOf(splits[0].split(",")[1].trim());
            Coordinate start = new Coordinate(x, y);
            x = Integer.valueOf(splits[1].split(",")[0].trim());
            y = Integer.valueOf(splits[1].split(",")[1].trim());
            Coordinate end = new Coordinate(x, y);
            Line line = new Line(start, end);
            lines.add(line);
        }
        return lines;
    }

    private static int computePoints(Route route) {
        int points = 0;
        for (Coordinate coordinate : route.overlappedCoordinates) {
            if (coordinate.overlaps >= 2) {
                points++;
            }
        }
        return points;
    }
}
