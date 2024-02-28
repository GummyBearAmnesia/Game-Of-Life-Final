import javafx.scene.paint.Color; 
import java.util.List;

/**
 * Simplest form of life.
 * Fun Fact: Mycoplasma are one of the simplest forms of life.  A type of
 * bacteria, they only have 500-1000 genes! For comparison, fruit flies have
 * about 14,000 genes.
 *
 * @author David J. Barnes, Michael Kölling & Jeffery Raphael
 * @version 2022.01.06
 */

public class Mycoplasma extends Cell {

    /**
     * Create a new Mycoplasma.
     *
     * @param field    The field currently occupied.
     * @param location The location within the field.
     */
    public Mycoplasma(Field field, Location location, Color col) {
        super(field, location, col);
    }

    /**
     * This is how the Mycoplasma decides if it's alive or not
     */
    public void act() {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        setNextState(false);

        if (isAlive()) {
            // if it has more than 3 neighbours or less than 2 the mycoplasma will die
            if (neighbours.size() > 3 || neighbours.size() < 2) {
                setNextState(false);
            }
            // otherwise if it has exactly 2 or 3 neighbours it will live
            else if (neighbours.size() == 3 || neighbours.size() == 2) {
                setNextState(true);
            }
        }

        if (!isAlive()) {
            // a dead mycoplasma will come back to life if it has exactly 3 neighbours
            if (neighbours.size() == 3) {
                // will only come back to life if there is no other cell at this location
                if (field.getObjectAt(this.getLocation()) == null) {
                    setNextState(true);
                }
            }

        }
    }
}