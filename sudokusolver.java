import java.util.*;

public class sudokusolver 
{
    private static final int GRID_SIZE = 9;

    public static void main(String[] args) 
    {
        Scanner sc = new Scanner(System.in);
        String continueChoice;

        do 
        {
            int[][] board = new int[GRID_SIZE][GRID_SIZE];

            int[][] inbuiltPuzzle =                         // Initialize with an inbuilt Sudoku puzzle
            {
                {1, 0, 3, 0, 6, 0, 9, 0, 0},
                {0, 0, 0, 0, 0, 4, 0, 0, 0},
                {7, 0, 0, 0, 0, 8, 3, 0, 0},
                {6, 0, 0, 0, 0, 0, 0, 2, 0},
                {0, 3, 4, 0, 0, 0, 5, 7, 0},
                {0, 8, 0, 0, 0, 0, 0, 0, 6},
                {0, 0, 7, 9, 0, 0, 0, 0, 4},
                {0, 0, 0, 4, 0, 0, 0, 0, 0},
                {0, 0, 6, 0, 2, 0, 8, 0, 1}
            };

            System.out.println("\nDo you want to use the inbuilt puzzle? (yes/no)");
            String choice = sc.nextLine().trim().toLowerCase();

            if (choice.equals("yes")) 
            {
                // Use inbuilt puzzle
                board = inbuiltPuzzle;
            } 
            
            else 
            {
                // Get input from the user
                inputBoard(board);
            }

           if (solveBoard(board))       // Solve the Sudoku puzzle
            {
                System.out.println("Sudoku puzzle solved successfully!");
            } 
            
            else 
            {
                System.out.println("This Sudoku puzzle cannot be solved.");
            }

            printBoard(board);     // Print the solved board    
            

            System.out.println("\nDo you want to solve another puzzle? (yes/no)");    // Ask if the user wants to solve another puzzle
            continueChoice = sc.nextLine().trim().toLowerCase();

        } while (continueChoice.equals("yes"));

        System.out.println("\nThank you for using the Sudoku solver!");
        sc.close();
    }

    private static void inputBoard(int[][] board)  // Method to take input for the Sudoku board 
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the Sudoku puzzle (use 0 for empty cells):");

        for (int row = 0; row < GRID_SIZE; row++) 
        {
            for (int col = 0; col < GRID_SIZE; col++) 
            {
                board[row][col] = sc.nextInt();
            }
        }
    }

    private static void printBoard(int[][] board)        // Method to print the Sudoku board
    {
        for (int row = 0; row < GRID_SIZE; row++) 
        {
            if (row % 3 == 0 && row != 0) 
            {
                System.out.println("----------------------------------------------");
            }

            for (int col = 0; col < GRID_SIZE; col++) 
            {
                if (col % 3 == 0 && col != 0) 
                {
                    System.out.print("\t|\t");
                }
                System.out.print(board[row][col]);
            }
            System.out.println();
        }
    }

    private static boolean solveBoard(int[][] board)         // Method to solve the Sudoku board using backtracking 
    {
        for (int row = 0; row < GRID_SIZE; row++) 
        {
            for (int col = 0; col < GRID_SIZE; col++) 
            {
                if (board[row][col] == 0) 
                {
                    for (int numberToTry = 1; numberToTry <= GRID_SIZE; numberToTry++) 
                    {
                        if (isValidPlacement(board, numberToTry, row, col)) 
                        {
                            board[row][col] = numberToTry;

                            if (solveBoard(board)) 
                            {
                                return true;
                            } 
                            
                            else 
                            {
                                board[row][col] = 0;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isValidPlacement(int[][] board, int number, int row, int col)      // Method to check if placing a number in a position is valid 
    {
        for (int i = 0; i < GRID_SIZE; i++)                 // Check the row 
        {
            if (board[row][i] == number) 
            {
                return false;
            }
        }

        for (int i = 0; i < GRID_SIZE; i++)              // Check the column 
        {
            if (board[i][col] == number)
            {
                return false;
            }
        }

        int localBoxRow = row - row % 3;                  // Check the 3x3 grid
        int localBoxCol = col - col % 3;

        for (int i = localBoxRow; i < localBoxRow + 3; i++) 
        {
            for (int j = localBoxCol; j < localBoxCol + 3; j++) 
            {
                if (board[i][j] == number) 
                {
                    return false;
                }
            }
        }

        return true;
    }
}
