import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Solution to Day 4: Giant Squid.
 * 
 * @author bluebillxp
 */
public class BingoSubsystem {

    private static int ROW_SIZE = 5;
    private static int COL_SIZE = 5;

    private static class Cell {
        int value;
        int row;
        int column;
        boolean check;
    }
    
    private static class BingoBoard {
        Cell[][] cells = new Cell[ROW_SIZE][COL_SIZE];
        HashMap<Integer, Cell> index = new HashMap<>(ROW_SIZE * COL_SIZE);
        boolean won;
    }

    public static void main(String[] args) {
        List<String> input = AdventHelper.readInput("input-day4-giant-squid.txt");
        List<BingoBoard> boards = buildBoards(input);
        String[] randomNumbers = input.get(0).split(",");
        System.out.println("Day 4: Giant Squid: " + boards.size() + " boards loaded.");

        System.out.println("Day 4: Giant Squid --- Part One ---");
        System.out.println("What will your final score be if you choose that board?");
        final int answerOne = solutionPartOne(boards, randomNumbers);
        System.out.println("Answer: " + answerOne);

        System.out.println("\nDay 4: Giant Squid --- Part Two ---");
        System.out.println("Once it wins, what would its final score be?");
        final int answerTwo = solutionPartTwo(boards, randomNumbers);
        System.out.println("Answer: " + answerTwo);
    }

    /**
     * Calculates the final score of the winning board.
     * 
     * @param boards Defined bingo boards from the challenge.
     * @param randomNumbers Defined random numbers from the challenge.
     * 
     * @return answer
     */
    private static int solutionPartOne(List<BingoBoard> boards, String[] randomNumbers) {
        int lastCalledNum = 0;
        BingoBoard winningBoard = null;
        for (String num : randomNumbers) {
            Integer value = Integer.valueOf(num);
            winningBoard = findWinningBoard(boards, value);
            if (winningBoard != null) {
                lastCalledNum = value;
                break;
            }
        }
        return computeBoardScore(winningBoard, lastCalledNum);
    }

    /**
     * Calculates the final score of the least winning board.
     * 
     * @param boards Defined bingo boards from the challenge.
     * @param randomNumbers Defined random numbers from the challenge.
     * 
     * @return answer
     */
    private static int solutionPartTwo(List<BingoBoard> boards, String[] randomNumbers) {
        int lastCalledNum = 0;
        BingoBoard leastWinningBoard = null;
        for (String num : randomNumbers) {
            Integer value = Integer.valueOf(num);
            leastWinningBoard = findLeastWinningBoard(boards, value);
            if (leastWinningBoard != null) {
                lastCalledNum = value;
                break;
            }
        }
        return computeBoardScore(leastWinningBoard, lastCalledNum);
    }

    private static int computeBoardScore(BingoBoard board, int lastCalledNum) {
        int unmarkedSum = 0;
        for (Cell cell : board.index.values()) {
            if (!cell.check) {
                unmarkedSum += cell.value;
            }
        }
        return unmarkedSum * lastCalledNum;
    }

    private static BingoBoard findWinningBoard(List<BingoBoard> boards, Integer num) {
        BingoBoard winningBoard = null;
        for (BingoBoard board : boards) {
            Cell cell = board.index.get(num);
            if (cell != null) {
                cell.check = true;
                int cntRow = 0;
                int cntColumn = 0;
                for (int i = 0; i < ROW_SIZE; i++) {
                    if (board.cells[cell.row][i].check) {
                        cntRow++;
                    }
                    if (board.cells[i][cell.column].check) {
                        cntColumn++;
                    }
                }
                if (cntRow == ROW_SIZE || cntColumn == COL_SIZE) {
                    board.won = true;
                    winningBoard = board;
                    break;
                }
            }
        }
        return winningBoard;
    }

    private static BingoBoard findLeastWinningBoard(List<BingoBoard> boards, Integer num) {
        BingoBoard leastWinningBoard = null;
        int cntWinningBoards = 0;
        for (BingoBoard board : boards) {
            if (board.won) {
                cntWinningBoards++;
                continue;
            }
            Cell cell = board.index.get(num);
            if (cell != null) {
                cell.check = true;
                int cntRow = 0;
                int cntColumn = 0;
                for (int i = 0; i < ROW_SIZE; i++) {
                    if (board.cells[cell.row][i].check) {
                        cntRow++;
                    }
                    if (board.cells[i][cell.column].check) {
                        cntColumn++;
                    }
                }
                if (cntRow == ROW_SIZE || cntColumn == COL_SIZE) {
                    board.won = true;
                    cntWinningBoards++;
                    leastWinningBoard = board;
                }
            }
        }
        
        if (cntWinningBoards == boards.size()) {
            return leastWinningBoard;
        }
        return null;
    }

    private static List<BingoBoard> buildBoards(List<String> input) {
        List<BingoBoard> boards = new ArrayList<>();
        BingoBoard board = new BingoBoard();
        int row = 0;
        for (int i = 1; i < input.size(); i++) {
            if (row == ROW_SIZE) {
                row = 0;
                boards.add(board);
                board = new BingoBoard();
            }
            String line = input.get(i);
            if (line.isEmpty()) {
                continue;
            }
            String[] values = line.split(" ");
            int column = 0;
            for (int j = 0; j < values.length; j++) {
                if (values[j].isEmpty()) {
                    continue;
                }
                Cell cell = new Cell();
                cell.value = Integer.valueOf(values[j]);
                cell.row = row;
                cell.column = column;
                board.cells[row][column] = cell;
                board.index.put(cell.value, cell);
                column++;
            }
            row++;
        }
        return boards;
    }
}
