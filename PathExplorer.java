import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Solution to Day 12: Passage Pathing
 * 
 * @author bluebillxp
 */
public class PathExplorer {

    private static class Node {
        String name;
        LinkedList<Node> adjents;

        Node(String name) {
            this.name = name;
            adjents = new LinkedList<>();
        }
    }
    public static void main(String[] args) {
        List<String> input = AdventHelper.readInput("input-day12-passage-pathing.txt");
        System.out.println("Day 12: Passage Pathing " + input.size() + " commands loaded.");

        Map<String, Node> nodeMap = buildNodeMap(input);
        System.out.println("Day 12: Passage Pathing --- Part One ---");
        System.out.println("How many paths through this cave system are there that visit small caves at most once?");
        final int answerOne = solutionPartOne(nodeMap.get("start"));
        System.out.println("Answer: " + answerOne);

        System.out.println("\nDay 12: Passage Pathing --- Part Two ---");
        System.out.println("Given these new rules, how many paths through this cave system are there?");
        final int answerTwo = solutionPartTwo(nodeMap.get("start"));
        System.out.println("Answer: " + answerTwo);
    }

    /**
     * Calculates how many paths through this cave system are there that visit small caves at most once.
     * 
     * @param startNode Defined starting node from the challenge.
     * 
     * @return answer
     */
    private static int solutionPartOne(Node startNode) {
        Set<String> totalPaths = new HashSet<>();
        exploreSmallCavesOnlyOncePaths(startNode, new LinkedList<>(), totalPaths);
        return totalPaths.size();
    }

    /**
     * Calculates how many paths through this cave system are there with new rules.
     * 
     * @param startNode Defined starting node from the challenge.
     * 
     * @return answer
     */
    private static int solutionPartTwo(Node startNode) {
        Set<String> totalPaths = new HashSet<>();
        exploreSmallCavesOnlyTwicePaths(startNode, new LinkedList<>(), totalPaths, true);
        return totalPaths.size();
    }

    private static void exploreSmallCavesOnlyTwicePaths(Node current,
        LinkedList<Node> currentPath, Set<String> uniquePaths,
        boolean allowSmallCaveTwice) {
        // Small caves can only visit once.
        boolean smallCave = current.name.toLowerCase() == current.name;
        if (smallCave && currentPath.contains(current)) {
            if (!allowSmallCaveTwice) {
                return;
            } else {
                allowSmallCaveTwice = false;
            }
        }
        currentPath.add(current);
        if (current.name.equals("end")) {
            String fullPath = "";
            for (Node node : currentPath) {
                fullPath += node.name + "-";
            }
            uniquePaths.add(fullPath);
            return;
        }
        for (Node adjent : current.adjents) {
            if (adjent.name.equals("start")) {
                continue;
            }
            exploreSmallCavesOnlyTwicePaths(adjent,
                new LinkedList<>(currentPath),
                uniquePaths,
                allowSmallCaveTwice);
        }
    }

    private static void exploreSmallCavesOnlyOncePaths(Node current,
        LinkedList<Node> currentPath, Set<String> uniquePaths) {
        // Small caves can only visit once.
        if (current.name.toLowerCase() == current.name
            && currentPath.contains(current)) {
            return;
        }
        currentPath.add(current);
        if (current.name.equals("end")) {
            String fullPath = "";
            for (Node node : currentPath) {
                fullPath += node.name + "-";
            }
            uniquePaths.add(fullPath);
            return;
        }
        for (Node adjent : current.adjents) {
            exploreSmallCavesOnlyOncePaths(adjent,
                new LinkedList<>(currentPath), uniquePaths);
        }
    }

    private static Map<String, Node> buildNodeMap(List<String> input) {
        Map<String, Node> nodeMap = new HashMap<>();
        for (String line : input) {
            String[] nodeNames = line.split("-");
            Node leftNode = nodeMap.get(nodeNames[0]);
            if (leftNode == null) {
                leftNode = new Node(nodeNames[0]);
                nodeMap.put(leftNode.name, leftNode);
            }
            Node rightNode = nodeMap.get(nodeNames[1]);
            if (rightNode == null) {
                rightNode = new Node(nodeNames[1]);
                nodeMap.put(rightNode.name, rightNode);
            }
            leftNode.adjents.add(rightNode);
            rightNode.adjents.add(leftNode);
        }
        return nodeMap;
    }
}
