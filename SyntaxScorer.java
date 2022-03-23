import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * Solution to Day 10: Syntax Scoring.
 * 
 * @author bluebillxp
 */
public class SyntaxScorer {
    public static void main(String[] args) {
    
        List<String> input = AdventHelper.readInput("input-day10-syntax-scoring.txt");
        System.out.println("Day 10: Syntax Scoring " + input.size() + " commands loaded.");

        System.out.println("Day 10: Syntax Scoring --- Part One ---");
        System.out.println("What is the total syntax error score for those errors?");
        final int answerOne = solutionPartOne(input);
        System.out.println("Answer: " + answerOne);

        System.out.println("\nDay 10: Syntax Scoring --- Part Two ---");
        System.out.println("Find the completion string for each incomplete line, score the completion strings, and sort the scores. What is the middle score?");
        final long answerTwo = solutionPartTwo(input);
        System.out.println("Answer: " + answerTwo);
    }

    /**
     * Calculates the total syntax error score for those errors.
     * 
     * @param input Defined input from the challenge.
     * 
     * @return answer
     */
    private static int solutionPartOne(List<String> input) {
        int[] corruptedChars = new int[4];
        for (String syntaxLine : input) {
            char incorrectChar = findFirstIncorrectChar(syntaxLine);
            switch (incorrectChar) {
                case ')': {
                    corruptedChars[0]++;
                    break;
                }
                case ']': {
                    corruptedChars[1]++;
                    break;
                }
                case '}': {
                    corruptedChars[2]++;
                    break;
                }
                case '>': {
                    corruptedChars[3]++;
                    break;
                }
            }
        }
        int totalScore = 0;
        totalScore += (corruptedChars[0] * 3);
        totalScore += (corruptedChars[1] * 57);
        totalScore += (corruptedChars[2] * 1197);
        totalScore += (corruptedChars[3] * 25137);
        return totalScore;
    }

    /**
     * Calculates the middle score of incomplete lines.
     * 
     * @param input Defined input from the challenge.
     * 
     * @return answer
     */
    private static long solutionPartTwo(List<String> input) {
        ArrayList<Long> scores = new ArrayList<>();
        for (String line : input) {
            long score = computeAutocompleteScore(line);
            if (score > 0) {
                scores.add(score);
            }
        }
        Collections.sort(scores);
        return scores.get(scores.size()/2);
    }

    private static char findFirstIncorrectChar(String line) {
        // Whatever pops is not a pair, then it's corrupted.
        // Whatever pops is a pair, but not empty the stack, it's incomplete.
        // Wherever pops is a pait, and empty the stack, it's complete.
        Stack<Character> syntaxPairStack = new Stack<>();
        boolean corrupted = false;
        char corruptedChar = '?';
        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);
            switch (ch) {
                case ')': {
                    Character pairCh = syntaxPairStack.pop();
                    if (pairCh.charValue() != '(') {
                        corrupted = true;
                    }
                    break;
                }
                case ']': {
                    Character pairCh = syntaxPairStack.pop();
                    if (pairCh.charValue() != '[') {
                        corrupted = true;
                    }
                    break;
                }
                case '}': {
                    Character pairCh = syntaxPairStack.pop();
                    if (pairCh.charValue() != '{') {
                        corrupted = true;
                    }
                    break;
                }
                case '>': {
                    Character pairCh = syntaxPairStack.pop();
                    if (pairCh.charValue() != '<') {
                        corrupted = true;
                    }
                    break;
                }
                default: {
                    syntaxPairStack.push(Character.valueOf(ch));
                    break;
                }
            }
            if (corrupted) {
                corruptedChar = ch;
                break;
            }
        }
        return corruptedChar;
    }

    private static long computeAutocompleteScore(String line) {
        Stack<Character> syntaxPairStack = new Stack<>();
        boolean corrupted = false;
        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);
            switch (ch) {
                case ')': {
                    Character pairCh = syntaxPairStack.pop();
                    if (pairCh.charValue() != '(') {
                        corrupted = true;
                    }
                    break;
                }
                case ']': {
                    Character pairCh = syntaxPairStack.pop();
                    if (pairCh.charValue() != '[') {
                        corrupted = true;
                    }
                    break;
                }
                case '}': {
                    Character pairCh = syntaxPairStack.pop();
                    if (pairCh.charValue() != '{') {
                        corrupted = true;
                    }
                    break;
                }
                case '>': {
                    Character pairCh = syntaxPairStack.pop();
                    if (pairCh.charValue() != '<') {
                        corrupted = true;
                    }
                    break;
                }
                default: {
                    syntaxPairStack.push(Character.valueOf(ch));
                    break;
                }
            }
            if (corrupted) {
                break;
            }
        }
        if (corrupted) {
            return 0;
        }
        long autocompleteScore = 0;
        while (syntaxPairStack.size() > 0)  {
            Character leadingCh = syntaxPairStack.pop();
            autocompleteScore *= 5;
            switch (leadingCh.charValue()) {
                case '(': {
                    autocompleteScore += 1;
                    System.out.print(')');
                    break;
                }
                case '[': {
                    autocompleteScore += 2;
                    System.out.print(']');
                    break;
                }
                case '{': {
                    autocompleteScore += 3;
                    System.out.print('}');
                    break;
                }
                case '<': {
                    autocompleteScore += 4;
                    System.out.print('>');
                    break;
                }
            }
        }
        System.out.println(autocompleteScore);
        return autocompleteScore;
    }
}
