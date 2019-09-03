
import org.junit.Test;

import java.util.*;

class SlideGame {

    private int[][] board;
    int rows;
    int cols;
    Random rand = new Random();
    int zeroX;
    int zeroY;

    public SlideGame(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        board = new int[rows][cols];
        zeroX = rows - 1;
        zeroY = cols - 1;
        initBoard();
    }
    void initBoard() {
        int value = 1;
        for(int i = 0; i< rows; ++i) {
            for(int j = 0; j< cols; ++j) {
                board[i][j] = value++;
            }
        }
        board[rows-1][cols-1] = 0;
    }

    public boolean isWin() {
        return isWin(board);
    }
    public boolean isWin(int[][] board){
        int value = 1;
        for(int i = 0; i< rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                if (board[i][j] == value) {
                    value++;
                    continue;
                }
                if (board[i][j] == 0 && i == rows - 1 && j == cols - 1)
                    return true;
                else
                    return false;
            }
        }
        return false;
    }

    List<int[]> getNeighborDorections() {
        return new ArrayList(Arrays.asList (new int[]{1, 0}, new int[]{-1, 0}, new int[]{0, 1}, new int[]{0, -1}));
    }

    public void shuffle(int n) {
        zeroX = rows - 1;
        zeroY = cols - 1;
        for(int i = 0; i < n; ++i) {
//            System.out.println("shuffle: " + i);
            int []next = getRandomPoint(zeroX, zeroY);
            swap(board, zeroX, zeroY, next[0], next[1]);
            zeroX = next[0];
            zeroY = next[1];
//            printBoard(board);
        }
    }

    int[] getRandomPoint(int x, int y) {
        List<int[]> neighbors = getNeighborDorections();
        while(true) {
//            System.out.println("(x, y) = (" + x+", " + y+"), neighbors.size() = " + neighbors.size());
            int choice = rand.nextInt(neighbors.size());
            int[] point = neighbors.get(choice);
            int newX = x+point[0];
            int newY = y+point[1];
//            System.out.println("YEP choice = " + choice + "(" + point[0] + ", " + point[1] +")" + "(newX, newY) = (" + newX+", " + newY+")");
            if(isValid(newX, newY)){
                return new int[]{newX, newY};
            } else {
                neighbors.remove(choice);
            }
//            System.out.println("failed choice = " + choice + "(" + point[0] + ", " + point[1] +")" + "(newX, newY) = (" + newX+", " + newY+")");

        }
    }

    void swap(int[][] board, int i, int j, int x, int y) {
        int v = board[x][y];
        board[x][y] = board[i][j];
        board[i][j] = v;
    }
    boolean isValid(int x, int y) {
        return x>=0 && x<rows && y>=0 && y<cols;
    }
    public void printBoard() {
        printBoard(board);
    }
    public void printBoard(int[][] board) {
        System.out.println("------------------------------");
        for(int[] row: board) {
            for(int i: row) {
                System.out.print(i+ " ");
            }
            System.out.println();
        }
    }

    private String getVisitString(int[][]board){
        StringBuilder sb= new StringBuilder();
        for(int[] row: board) {
            for(int i: row){
                sb.append(i);
                sb.append("-");
            }
        }
        return sb.toString();
    }
    private int[][] copyBoard(int[][] board){
        int[][] newBoard = new int[rows][cols];
        for(int i = 0; i< rows; ++i) {
            for(int j = 0; j< cols; ++j) {
                newBoard[i][j] = board[i][j];
            }
        }
        return newBoard;
    }

    void setZeroPoint() {
        for(int i = 0; i< rows; ++i) {
            for(int j = 0; j< cols; ++j) {
                if(board[i][j] == 0) {
                    zeroX = i;
                    zeroY = j;
                    break;
                }
            }
        }
    }
    public int resolve() {
        int steps = 0;
        setZeroPoint();
        if(isWin(board)) return steps;
        Queue<int[][]> boardQueue = new LinkedList<>();
        Queue<int[]> zeroQueue = new LinkedList<>();
        zeroQueue.offer(new int[]{zeroX, zeroY});
        boardQueue.offer(board);
        Set<String> visited= new HashSet<>();
        List<int[]> directions = getNeighborDorections();
        visited.add(getVisitString(board));
        while(!zeroQueue.isEmpty()) {
            int size = zeroQueue.size();
            steps++;
            for(int i=0; i<size; ++i) {
                int[] point = zeroQueue.poll();
                int[][] topBoard = boardQueue.poll();
//                System.out.println("top board: " + "("+zeroX+", " + zeroY+")");
//                printBoard(topBoard);
                for(int[] direction: directions) {
                    int[]newPoint = new int[]{point[0] + direction[0], point[1] + direction[1]};
                    if(isValid(newPoint[0], newPoint[1])) {
                        int[][]newBoard = copyBoard(topBoard);
                        swap(newBoard, point[0], point[1], newPoint[0], newPoint[1]);
                        String boardString = getVisitString(newBoard);
                        if(visited.contains(boardString)) continue;
                        visited.add(boardString);
//                        System.out.println("-- new board: ");
                        if(isWin(newBoard)) {
                            board = newBoard;
                            return steps;
                        } else {
                            boardQueue.offer(newBoard);
                            zeroQueue.offer(newPoint);
                        }
                    }
                }
            }
        }
        return -1;
    }

}

public class SlideGameTest {
    @Test
    public void test1() {
        SlideGame game = new SlideGame(3,3);
        game.printBoard();
        System.out.println(game.isWin());
        game.shuffle(1000);

        game.printBoard();
        System.out.println(game.isWin());
        int steps = game.resolve();
        System.out.println("total steps: " + steps);
        game.printBoard();

    }

}
