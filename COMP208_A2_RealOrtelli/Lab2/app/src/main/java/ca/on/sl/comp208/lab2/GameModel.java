package ca.on.sl.comp208.lab2;

import java.util.Random;

/**
 * Created by Ray on 2017-02-13.
 */

/**
 * This class is the model of the application
 */
public class GameModel {

    /**
     * Variables
      */
    private final int rows = 30;
    private final int cols = 30;
    private boolean[][] oldGen = new boolean[rows][cols];

    /**
     * nextGeneration, handles making the next generation to be drawn.
     * Calls tallyNeighbours to tally the neighbours
     */
    public synchronized void nextGeneration() {

        boolean[][] nextGen = new boolean[rows][cols];

        for (int row = 1; row < rows - 1; row++) {
            for (int col = 1; col < cols - 1; col++) {

                int neighbours = tallyNeighbours(row, col);

                if (neighbours < 2 || neighbours > 3)
                {
                    nextGen[row][col] = false;
                } else if(neighbours == 3){
                    nextGen[row][col] = true;
                }
                else
                {
                    nextGen[row][col] = oldGen[row][col];
                }
            }
        }
        oldGen = nextGen;
    }

    /**
     * tallyNeighbours, tallys the neighbours
     * @param col
     * @param row
     * @return
     */
    public synchronized int tallyNeighbours(int col, int row) {

        int numNeighbors = 0;

        for (int x = col - 1; x <= col + 1; x++) {

            for (int y = row - 1; y <= row + 1; y++) {

                if(!(x == col && y == row) &&    // is not itself
                        x >= 0 && x < cols &&    // is valid col
                        y >= 0 && y < rows &&    // is valid row
                        oldGen[x][y] == true     // is alive
                  )
                {
                    numNeighbors++;
                }
            }
        }

        return numNeighbors;
    }

    /**
     * populateGrid, populates the grid with a randomized array
     */
    public synchronized void populateGrid()
    {
        Random random = new Random();

        for(int row = 1; row < rows - 1; row++)
        {
            for(int col = 1; col < cols - 1; col++)
            {
                oldGen[row][col] = random.nextBoolean();
            }
        }
    }

    /**
     * addSingleCell, adds a single cell to the grid
     * @param cellRow
     * @param cellCol
     */
    public synchronized void addSingleCell(int cellRow , int cellCol)
    {
        if ((cellRow > 0 && cellRow < rows - 1) && (cellCol > 0 && cellCol < cols - 1))
        {
            oldGen[cellRow][cellCol] = true;
        }
    }

    /**
     * addGlider, adds a single Glider to the grid
     * @param cellRow
     * @param cellCol
     */
    public synchronized void addGlider(int cellRow, int cellCol, char quad)
    {
        if ((cellRow > 0 && cellRow < rows - 1) && (cellCol > 0 && cellCol < cols - 1))
        {
            switch(quad)
            {
                case 'A':
                    oldGen[cellRow][cellCol] = true;
                    oldGen[cellRow + 1][cellCol + 1] = true;
                    oldGen[cellRow + 2][cellCol - 1] = true; // A
                    oldGen[cellRow + 2][cellCol] = true;
                    oldGen[cellRow + 2][cellCol + 1] = true;
                    break;
                case 'B':
                    oldGen[cellRow][cellCol] = true;
                    oldGen[cellRow - 1][cellCol + 1] = true;
                    oldGen[cellRow - 2][cellCol + 1] = true; // B
                    oldGen[cellRow - 2][cellCol] = true;
                    oldGen[cellRow - 2][cellCol - 1] = true;
                    break;
                case 'C':
                    oldGen[cellRow][cellCol] = true;
                    oldGen[cellRow + 1][cellCol - 1] = true;
                    oldGen[cellRow + 2][cellCol - 1] = true; // C
                    oldGen[cellRow + 2][cellCol] = true;
                    oldGen[cellRow + 2][cellCol + 1] = true;
                    break;
                case 'D':
                    oldGen[cellRow][cellCol] = true;
                    oldGen[cellRow - 1][cellCol - 1] = true;
                    oldGen[cellRow - 1][cellCol - 2] = true; // D
                    oldGen[cellRow][cellCol - 2] = true;
                    oldGen[cellRow + 1][cellCol - 2] = true;
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * shakeScreen, handles the shakeing of the screen and randomizes the positions of the cells
     */
    public synchronized void shakeScreen()
    {
        //Collections.shuffle(Arrays.asList(oldGen));
        boolean[][] arrayShake = new boolean[rows][cols];
        Random random = new Random();
        int plusMinus = random.nextInt(8) + 1;

        for(int row = 1; row < rows - 1; row++)
        {
            for(int col = 1; col < cols - 1; col++)
            {
                if(oldGen[row][col] == true)
                {
                    switch(plusMinus)
                    {
                        case 1:
                            if(oldGen[row + 1][col + 1] != true)
                            {
                                arrayShake[row + 1][col + 1] = true;
                            }
                            break;
                        case 2:
                            if(oldGen[row - 1][col - 1] != true)
                            {
                                arrayShake[row - 1][col - 1] = true;
                            }
                            break;
                        case 3:
                            if(oldGen[row - 1][col + 1] != true)
                            {
                                arrayShake[row - 1][col + 1] = true;
                            }
                            break;
                        case 4:
                            if(oldGen[row + 1][col - 1] != true)
                            {
                                arrayShake[row + 1][col - 1] = true;
                            }
                            break;
                        case 5:
                            if(oldGen[row][col + 1] != true)
                            {
                                arrayShake[row][col + 1] = true;
                            }
                            break;
                        case 6:
                            if(oldGen[row + 1][col] != true)
                            {
                                arrayShake[row + 1][col] = true;
                            }
                            break;
                        case 7:
                            if(oldGen[row - 1][col] != true)
                            {
                                arrayShake[row - 1][col] = true;
                            }
                            break;
                        case 8:
                            if(oldGen[row][col - 1] != true)
                            {
                                arrayShake[row][col - 1] = true;
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        oldGen = arrayShake;
    }

    /**
     * clearArray, clears the array
     */
    public synchronized void clearArray()
    {
        oldGen = new boolean[rows][cols];
    }

    /**
     * getRows, returns rows
     * @return
     */
    public int getRows()
    {
        return this.rows;
    }

    /**
     * getCols, returns cols
     * @return
     */
    public int getCols()
    {
        return this.cols;
    }

    /**
     * getCells, returns the grid array
     * @return
     */
    public boolean[][] getCells()
    {
        return this.oldGen;
    }

    /**
     * setCells, sets the grid array
     * @param oldGen
     */
    public void setCells(boolean[][] oldGen) {
        this.oldGen = oldGen;
    }
}
