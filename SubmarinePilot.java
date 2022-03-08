import java.util.List;

/**
 * Solution to Day 2 - Dive!
 * 
 * @author bluebillxp
 */
public class SubmarinePilot {

    private static final String CMD_FORWARD = "forward";
    private static final String CMD_UP = "up";
    private static final String CMD_DOWN = "down";
    
    public static void main(String[] args) {
        List<String> input = AdventHelper.readInput("input-day2-dive.txt");
        System.out.println("Day 2: Dive! " + input.size() + " commands loaded.");

        System.out.println("Day 2: Dive! --- Part One ---");
        System.out.println("What do you get if you multiply your final horizontal position by your final depth?");
        final int answerOne = solutionPartOne(input);
        System.out.println("Answer: " + answerOne);

        System.out.println("\nDay 2: Dive! --- Part Two ---");
        System.out.println("What do you get if you multiply your final horizontal position by your final depth?");
        final int answerTwo = solutionPartTwo(input);
        System.out.println("Answer: " + answerTwo);
    }

    /**
     * Calculates what you get if you multiply your final horizontal
     * position by your final depth.
     * 
     * @param input Defined input from the challenge.
     * 
     * @return answer
     */
    private static int solutionPartOne(List<String> input) {
        int posHorizontal = 0;
        int posDepth = 0;
        for (String cmd : input) {
            if (cmd.startsWith(CMD_FORWARD)) {
                int x = Integer.valueOf(cmd.substring(CMD_FORWARD.length() + 1));
                posHorizontal += x;
            } else if (cmd.startsWith(CMD_DOWN)) {
                int x = Integer.valueOf(cmd.substring(CMD_DOWN.length() + 1));
                posDepth += x;
            } else if (cmd.startsWith(CMD_UP)) {
                int x = Integer.valueOf(cmd.substring(CMD_UP.length() + 1));
                posDepth -= x;
            }
        }
        return posHorizontal * posDepth;
    }

    /**
     * Calculates what you get if you multiply your final horizontal
     * position by your final depth.
     * 
     * @param input Defined input from the challenge.
     * 
     * @return answer
     */
    private static int solutionPartTwo(List<String> input) {
        int posHorizontal = 0;
        int posDepth = 0;
        int posAim = 0;
        for (String cmd : input) {
            if (cmd.startsWith(CMD_FORWARD)) {
                int x = Integer.valueOf(cmd.substring(CMD_FORWARD.length() + 1));
                posHorizontal += x;
                posDepth += posAim * x;
            } else if (cmd.startsWith(CMD_DOWN)) {
                int x = Integer.valueOf(cmd.substring(CMD_DOWN.length() + 1));
                posAim += x;
            } else if (cmd.startsWith(CMD_UP)) {
                int x = Integer.valueOf(cmd.substring(CMD_UP.length() + 1));
                posAim -= x;
            }
        }
        return posHorizontal * posDepth;
    }
}
