import javafx.scene.paint.Color;
import java.util.List;
import java.util.Random;

/**
 * A bacteria that infects other life forms in order to spread.
 * Uses probabilty to either infect or kill a neighbouring cell.
 *
 * @author Aamukta Thogata K2369720
 *         Onessa Crispeyn K23080159
 * @9/Feb 12:04
 */
public class Disease extends Cell
{
    // The probabilities for the disease infecting/ killing other cells
    public static final double INFECTION_PROB = 0.03;
    public static final double KILL_PROB = 0.02;
    
    /**
     * Constructor for objects of class Disease
     */
    public Disease(Field field, Location location, Color col)
    {
        super(field, location, col);
    }
    

    /**
     * Determines how the disease cell acts and whether it's alive or not in the next stage
     */
    public void act()
    {
        if(isAlive()){
            // Checks if the cell is a functioning disease cell
            // Black disease cells do not spread disease, so we will refer to them as non-functioning
            if(!getColor().equals(Color.BLACK)){
                List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
                setNextState(false);
                // If the disease has neighbours, it can infect its neighbours
                if (!neighbours.isEmpty()){
                    setNextState(true);
                    infect(neighbours);
                }
                else{
                    setNextState(false);
                }
            }
        }
    }


    /**
     * Infects neighbouring cells, which will become fully functioning disease cells if the
     * infection probability is met.
     * Kills neighbouring cells if the kill probability is met
     * The Crab and Snake cells cannot become infected or be killed.
     */
    private void infect(List<Cell> neighbours)
    {
        Random rand = Randomizer.getRandom();
        // Iterates through each cell's neighbours
        for (Cell c: neighbours){
            if (!(c instanceof Crab) && !(c instanceof Snake))
            {
                if (rand.nextDouble() <= INFECTION_PROB) {
                    field.clear(c.getLocation());
                    Disease disease = new Disease (super.field, c.getLocation(), Color.RED);
                }
                if (rand.nextDouble() <= KILL_PROB) {
                    c.setColor(Color.BLACK);
                    c.setDead();
                    c.setNextState(true);
                }
            }
        }
    }
}
