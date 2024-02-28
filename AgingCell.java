import javafx.scene.paint.Color;
import java.util.List;
import java.util.ArrayList;

/**
 * The aging cell changes its behaviour as time passes.
 * For the first 10 generations, it is a baby cell, it is pink and cannot reproduce.
 * The middle 10 generations, the cell settles down and can reproduce.
 * The last 10 generations, the cell turns yellow and then dies.
 *
 * @author Aamukta Thogata K2369720
 *         Onessa Crispeyn K23080159
 * @version 11/02/2024
 */
public class AgingCell extends Cell {
    private int age;

    /**
     * Constructor for objects of class AgingCell
     */
    public AgingCell(Field field, Location location, Color col) {
        super(field, location, col);
        this.age = 0;
    }
    
    /**
     * Performs actions based on the cell's age.
     * Moves between age 0-10, reproduces during age 11-20, and turns yellow during age 21-30.
     * When the cell reaches age 30, it dies.
     */
    public void act() {
        if (isAlive()) {
            setNextState(true);
            setColor(Color.PINK);
            age++;
            if (age <= 10){
                migrate();
            }
            else if (age > 10 && age <= 20) {
                reproduce();
            }
            else if (age > 20 && age < 30) {
                setColor(Color.YELLOW);
            }
            else if (age == 30) {
                setNextState(false);
            }
        }
    }

    /**
     * migrate() Moves the cell to an empty adjacent location on the grid, each generation.
     */
    private void migrate() {
        // Retrieves the cell's current location, and a random adjacent location which the cell will move to
        Location currentLocation = getLocation();
        Location newLocation = getField().randomEmptyAdjacentLocation(currentLocation);

        if (newLocation != null) {
            AgingCell movedCell = new AgingCell(super.field, newLocation, getColor());
            // Ensures that the original cell's age is transferred to the moved cell's
            movedCell.setAge(this.age);
            // Sets the next state of the moved cell to alive
            movedCell.setNextState(true);
            // Sets the next state of the original cell to dead
            setNextState(false);
        }
    }


    /**
     * reproduce() Reproduces by creating new aging cells in adjacent empty locations,
     * if there are 2/ 3 neighbouring cells between the ages of 10-20
     */
    private void reproduce() {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        // Creates an arraylist of each aging cell's neighbours
        ArrayList<AgingCell> agingNeighbours = new ArrayList<>();
        
        for (Cell neighbour : neighbours) {
            // Gets neighbour's age and checks if it's also an aging cell between ages 11 and 20
            // Creates a list of valid cells which it can reproduce with
            if (neighbour instanceof AgingCell) {
                int neighbourAge = ((AgingCell) neighbour).getAge();
                if (neighbourAge >= 10 && neighbourAge <= 20) {
                    agingNeighbours.add((AgingCell) neighbour);
                }
            }
        }
        // Reproduces if the aging cell has 2-3 valid neighbours, and if there's an empty location next to it
        if (agingNeighbours.size() == 2 || agingNeighbours.size() == 3) {
            Location randomEmptyLocation = getField().randomEmptyAdjacentLocation(getLocation());
            if (randomEmptyLocation != null) {
                AgingCell baby = new AgingCell(super.field, randomEmptyLocation, Color.PINK);
                baby.setNextState(true);
                super.field.place(baby, randomEmptyLocation);
            }
        }
    }

    private void setAge(int age) {
        this.age = age;
    }

    private int getAge() {
        return age;
    }
}