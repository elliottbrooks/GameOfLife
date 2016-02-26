package bbc.gameoflifestub;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by elliottbrooks on 26/02/2016.
 */
public class LifeBuilder {
    private ArrayList<String> initialStateRows = new ArrayList<>();

    public void addInitialStateRows(String row) {
        initialStateRows.add(row);
    }

    public boolean readyToBuild() {
        return (initialStateRows.size() > 1);
    }

    public Life build() {
        ArrayList<Cell> cells = new ArrayList<>();
        for (int i = 0; i < initialStateRows.size(); i++) {
            String rowContents = initialStateRows.get(i);
            for (int j = 0; j < rowContents.length(); j++) {
                if (rowContents.charAt(j) == '*') {
                    Cell currentCell = new Cell(i, j);
                    cells.add(currentCell);
                }
            }
        }
        Life life = new Life(new HashSet<>(cells));

        return life;
    }
}
