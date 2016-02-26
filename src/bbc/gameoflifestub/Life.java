package bbc.gameoflifestub;

import java.util.HashSet;
import java.util.Set;

public class Life {

    private Set<Cell> liveCells;
    private int minX, maxX, minY, maxY;

    public Life(Set<Cell> initialState) {
        applyCells(initialState);
    }

    // Read-only access to the game state
    public Set<Cell> getLiveCells() {
        return liveCells;
    }

    public boolean cellShouldSurvive(int numNeighbours) {
        return (numNeighbours >= 2 && numNeighbours <= 3);
    }

    public boolean cellShouldSpawn(int numNeighbours) {
        return (numNeighbours == 3);
    }

    /**
     * Reduces the set of Cell objects into a two-dimensional array according to the X and Y values of each cell, and the X and Y bounds of this class.
     *
     * @return two-dimensional index of Cell instances
     */

    public Cell[][] getCellArray() {
        Cell[][] cells = new Cell[maxX - minX + 1][maxY - minY + 1];
        for (Cell currentCell : liveCells) {
            cells[currentCell.getX() - minX][currentCell.getY() - minY] = currentCell;
        }
        return cells;
    }

    public Cell getCellAt(int x, int y) {
        Cell[][] cells = getCellArray();
        try {
            return cells[minX + x][minY + y];
        } catch (IndexOutOfBoundsException ioobe) {
            return null;
        }
    }

    public boolean canEvolve() {
        return liveCells.size() > 0;
    }

    public boolean evolve() {
        Set<Cell> newCells = new HashSet<>();
        Cell[][] cells = getCellArray();

        // Consider cells within the active area
        for (int i = -1; i <= cells.length; i++) {
            for (int j = -1; j <= cells[0].length; j++) {
                if (getCell(cells, i, j) != null) {
                    if (cellShouldSurvive(getNumberOfNeighbours(cells, i, j))) {
                        newCells.add(cells[i][j]);
                    }
                } else {
                    if (cellShouldSpawn(getNumberOfNeighbours(cells, i, j))) {
                        newCells.add(new Cell(minX + i, minY + j));
                    }
                }
            }
        }

        boolean identical = newCells.equals(liveCells);

        applyCells(newCells);

        return !identical;
    }

    private void applyCells(Set<Cell> newCells) {
        this.liveCells = newCells;
        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE, maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;
        for (Cell cell : newCells) {
            minX = Math.min(minX, cell.getX());
            minY = Math.min(minY, cell.getY());
            maxX = Math.max(maxX, cell.getX());
            maxY = Math.max(maxY, cell.getY());
        }
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    public Cell getCell(Cell[][] cells, int x, int y) {
        if(x < 0 || x >= cells.length){
            return null;
        }
        if(y < 0 || y >= cells[x].length){
            return null;
        }
        return cells[x][y];
    }

    public int getNumberOfNeighbours(Cell[][] cells, int x, int y) {
        // A|B|C
        // D| |E
        // F|G|H

        int neighbours = 0;
        // A
        if (getCell(cells, x - 1, y - 1) != null) neighbours++;
        // D
        if (getCell(cells, x - 1, y) != null) neighbours++;
        // F
        if (getCell(cells, x - 1, y + 1) != null) neighbours++;
        // B
        if (getCell(cells, x, y - 1) != null) neighbours++;
        // G
        if (getCell(cells, x, y + 1) != null) neighbours++;
        // C
        if (getCell(cells, x + 1, y - 1) != null) neighbours++;
        // E
        if (getCell(cells, x + 1, y) != null) neighbours++;
        // H
        if (getCell(cells, x + 1, y + 1) != null) neighbours++;

        return neighbours;
    }

    /**
     * A string representation of the current state
     */
    public String toString() {
        String output = "";
        // cls
        for(int i=0; i<20; i++){
            output += "\n";
        }
        Cell[][] cells = getCellArray();
        for (int i = 0; i < cells[0].length + 2; i++) {
            output += "-";
        }
        for (Cell[] currentRow : cells) {
            output += "\n|";
            for (Cell currentCell : currentRow) {
                output += (currentCell != null ? "*" : ".");
            }
            output += "|";
        }
        output += "\n";
        for (int i = 0; i < cells[0].length + 2; i++) {
            output += "-";
        }
        return output;
    }
}
