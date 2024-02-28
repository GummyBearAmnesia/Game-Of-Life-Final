import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;


/**
 * A Life (Game of Life) simulator, first described by British mathematician
 * John Horton Conway in 1970.
 *
 * @author David J. Barnes, Michael Kölling & Jeffery Raphael
 * @version 2024.02.03
 */

public class Simulator {

    private static final double MYCOPLASMA_ALIVE_PROB = 0.25;
    private static final double SNAKE_ALIVE_PROB = 0.001;
    private static final double DISEASE_ALIVE_PROB = 0.005;
    private static final double AGINGCELL_ALIVE_PROB = 0.1;
    private static final double COLORCELL_ALIVE_PROB = 0.1;
    private static final double CRAB_ALIVE_PROB = 0.0003;

    private List<Cell> cells;
    private Field field;
    private int generation;

    /**
     * Construct a simulation field with default size.
     */
    public Simulator() {
        this(SimulatorView.GRID_HEIGHT, SimulatorView.GRID_WIDTH);
    }

    /**
     * Create a simulation field with the given size.
     *
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width) {
        cells = new ArrayList<>();
        field = new Field(depth, width);
        reset();
    }

    /**
     * Run the simulation from its current state for a single generation.
     * Iterate over the whole field updating the state of each life form.
     */
    public void simOneGeneration() {
        generation++;
        updateCells();

        for (Iterator<Cell> it = cells.iterator(); it.hasNext(); ) {
            Cell cell = it.next();
            cell.act();
        }

        for (Cell cell : cells) {
            cell.updateState();
        }
    }

    /**
     * Reset the simulation to a starting position.
     */
    public void reset() {
        generation = 0;
        cells.clear();
        populate();
    }

    /**
     * Randomly populate the field live/dead life forms
     */
    private void populate() {
        //Randomizer.reset();
        Random rand = Randomizer.getRandom();
        field.clear();
        for (int row = 0; row < field.getDepth(); row++) {
            for (int col = 0; col < field.getWidth(); col++) {
                Location location = new Location(row, col);
                if (rand.nextDouble() <= MYCOPLASMA_ALIVE_PROB) {
                    Mycoplasma myco = new Mycoplasma(field, location, Color.ORANGE);
                    cells.add(myco);
                }
                else if (rand.nextDouble() <= CRAB_ALIVE_PROB){
                    Crab crab = new Crab(field, location, Color.GOLD);
                    cells.add(crab);
                }
                else if (rand.nextDouble() <= SNAKE_ALIVE_PROB) {
                Snake snake = new Snake(field, location, Color.GREEN, true);
                cells.add(snake);
                }
                else if (rand.nextDouble() <= DISEASE_ALIVE_PROB){
                    Disease bad = new Disease(field, location, Color.RED);
                    cells.add(bad);
                }
                else if (rand.nextDouble() <= COLORCELL_ALIVE_PROB) {
                    ColorChangingCell color = new ColorChangingCell(field, location, Color.LIGHTBLUE);
                    cells.add(color);
                }
                else if (rand.nextDouble() <= AGINGCELL_ALIVE_PROB){
                    AgingCell aging = new AgingCell(field, location, Color.PINK);
                    cells.add(aging);
                }
                else {
                    //field.clear(location);
                    Mycoplasma myco = new Mycoplasma(field, location, Color.ORANGE);
                    myco.setDead();
                    cells.add(myco);
                }
            }
        }
    }

    /**
     * Pause for a given time.
     *
     * @param millisec The time to pause for, in milliseconds
     */
    public void delay(int millisec) {
        try {
            Thread.sleep(millisec);
        } catch (InterruptedException ie) {
            // wake up
        }
    }

    public Field getField() {
        return field;
    }

    public int getGeneration() {
        return generation;
    }

    private void updateCells() {
        cells.clear();
        for (int row = 0; row < field.getDepth(); row++) {
            for (int col = 0; col < field.getWidth(); col++) {
                Location location = new Location(row, col);
                Cell cell = (getField().getObjectAt(location));

                if (cell != null) {
                    cells.add(cell);
                    //cell.setColor(Color.GREEN);
                }
                else{
                    System.out.println("cannot fid cell"+ location);
                }
            }
        }
        //.System.out.println(cells);
    }
}
