import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

class FindingOcean {

    public void floodFill(char[][] board, int i, int j, char oldColor, char newColor) {
        if(board == null || board[0].length == 0)
            return;
        int rows = board.length;
        int cols = board[0].length;
        if(i < 0 || i>= rows || j<0 || j>= cols
                || board[i][j] != oldColor
                || board[i][j] == newColor) return;
        board[i][j] = newColor;
        floodFill(board, i+1, j, oldColor, newColor);
        floodFill(board, i-1, j, oldColor, newColor);
        floodFill(board, i, j+1, oldColor, newColor);
        floodFill(board, i, j-1, oldColor, newColor);
    }
}

public class FindingOceanTest {
    @Test
    public void test1() {
        FindingOcean sol = new FindingOcean();
        List<String> testData = new ArrayList<String>() {{
            add("WWWLLLW");
            add("WWLLLWW");
            add("WLLLLWW");
        }};
        char[][] map = new char[testData.size()][testData.get(0).length()];
        for (int i = 0; i < testData.size(); i++)
            for (int j = 0; j < testData.get(i).length(); j++)
                map[i][j] = testData.get(i).charAt(j);

        sol.floodFill(map, 0, 0, 'W', 'O');
        assertEquals('O', map[0][0]);


        testData = new ArrayList<String>() {{
            add("LLLLLLLLLLLLLLLLLLLL");
            add("LLLLLLLLLLLLLLLLLLLL");
            add("LLLLLLLLLLLLLLWLLLLL");
            add("LLWWLLLLLLLLLLLLLLLL");
            add("LLWWLLLLLLLLLLLLLLLL");
            add("LLLLLLLLLLLLLLLLLLLL");
            add("LLLLLLLWWLLLLLLLLLLL");
            add("LLLLLLLLWWLLLLLLLLLL");
            add("LLLLLLLLLWWWLLLLLLLL");
            add("LLLLLLLLLLWWWWWWLLLL");
            add("LLLLLLLLLLWWWWWWLLLL");
            add("LLLLLLLLLLWWWWWWLLLL");
            add("LLLLWWLLLLWWWWWWLLLL");
            add("LLLLWWWLLLWWWWWWWWWW");
            add("LLLLLWWWWWWWWWWWLLLL");
            add("LLLLLLLLLLLLLLWWWWLL");
            add("LLLLLLLLLLLLLLWLLLLL");
            add("LLLLWLLLLLLLLLLLLWLL");
            add("LLLLLLLLLLLLLLLLLLWL");
        }};

        map = new char[testData.size()][testData.get(0).length()];
        for (int i = 0; i < testData.size(); i++)
            for (int j = 0; j < testData.get(i).length(); j++)
                map[i][j] = testData.get(i).charAt(j);
        sol.floodFill(map, 9, 12, 'W', 'O');
        assertEquals('O', map[9][11]);
    }
}
