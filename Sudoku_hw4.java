package com.gradescope.cs201;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sudoku_hw4 {
    
    public static int[] find_possible_values(int[][] matrix, int x, int y) {
        List<Integer> possibleValues = new ArrayList<>();
        
        for (int i = 1; i <= 9; i++) {
            if (isValid(matrix, x, y, i)) {
                possibleValues.add(i);
            }
        }
        
        return possibleValues.stream().mapToInt(Integer::intValue).toArray();
    }
    
    private static boolean isValid(int[][] matrix, int row, int col, int value) {
        // Check if value is present in the same row or column
        for (int i = 0; i < 9; i++) {
            if (matrix[row][i] == value || matrix[i][col] == value) {
                return false;
            }
        }
        
        // Check if value is present in the 3x3 submatrix
        int startRow = row - row % 3;
        int startCol = col - col % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (matrix[startRow + i][startCol + j] == value) {
                    return false;
                }
            }
        }
        
        return true;
    }

    public static void solve(int[][] matrix) {
        solveSudoku(matrix);
    }

    private static boolean solveSudoku(int[][] matrix) {
        int[] emptyLocation = findEmptyLocation(matrix);
        
        if (emptyLocation == null) {
            return true;  // All cells are filled
        }
        
        int row = emptyLocation[0];
        int col = emptyLocation[1];

        for (int num = 1; num <= 9; num++) {
            if (isValid(matrix, row, col, num)) {
                matrix[row][col] = num;

                if (solveSudoku(matrix)) {
                    return true;
                }

                matrix[row][col] = 0;  // Backtrack if solution is not found
            }
        }

        return false;  // No valid number found for this cell
    }

    private static int[] findEmptyLocation(int[][] matrix) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (matrix[i][j] == 0) {
                    return new int[]{i, j};
                }
            }
        }
        return null;  // No empty location found
    }

    public static void main(String[] args) {
        int[][] matrix_1 = {
                {0, 5, 0, 0, 0, 2, 0, 0, 6},
                {0, 8, 0, 0, 0, 6, 7, 0, 3},
                {0, 0, 0, 7, 4, 0, 0, 0, 8},
                {6, 7, 0, 9, 8, 0, 0, 0, 0},
                {0, 3, 1, 0, 5, 0, 6, 0, 9},
                {0, 0, 0, 0, 0, 3, 8, 2, 0},
                {0, 0, 0, 0, 0, 0, 1, 8, 0},
                {0, 0, 0, 3, 0, 0, 0, 0, 2},
                {9, 2, 0, 5, 0, 0, 0, 7, 0}
        };
        int[][] matrix_10 = {{5, 3, 0, 0, 8, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0, 0, 0, 2, 0},
                            {0, 0, 6, 9, 0, 0, 5, 0, 7},
                            {0, 0, 7, 5, 0, 0, 4, 0, 9},
                            {0, 0, 0, 0, 0, 0, 0, 0, 1},
                            {6, 0, 0, 0, 0, 7, 0, 0, 0},
                            {0, 0, 4, 0, 0, 0, 0, 1, 0},
                            {8, 0, 0, 0, 9, 0, 2, 0, 4},
                            {0, 0, 0, 2, 0, 0, 0, 6, 0}
        };

        System.out.println(Arrays.toString(Sudoku_hw4.find_possible_values(matrix_1, 0, 0)));
        System.out.println(Arrays.toString(Sudoku_hw4.find_possible_values(matrix_1, 3, 5)));
        System.out.println(Arrays.toString(Sudoku_hw4.find_possible_values(matrix_1, 6, 3)));

        Sudoku_hw4.solve(matrix_1);
        Sudoku_hw4.solve(matrix_10);

        System.out.println("After solving matrix 1:");
        printMatrix(matrix_1);

        System.out.println("After solving matrix 10:");
        printMatrix(matrix_10);
    }

    private static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
    }
}

