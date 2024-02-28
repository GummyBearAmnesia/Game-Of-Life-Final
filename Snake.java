import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.Random;

/**
 * A snake like microorganism. Long, moves towards food and grows every time it
 * consumes something.
 *
 * @author Aamukta Thogata K2369720
 *         Onessa Crispeyn K23080159
 * @9/Feb 11:57
 */

public class Snake extends Cell 
{
    /**
     * Constructor for objects of class Snake
     */

    // Used to determine whether the snake cell is acting as the head of the snake.
    private Boolean isHead;
    private int age;

    public Snake(Field field, Location location, Color col, Boolean isHead)
    {
        super(field, location, col);
        this.isHead = isHead;
        this.age = 1;
    }


    /**
     * This makes the cell do actions, determines the cell's status in the next generation
     */
    public void act()
    {
        // All snake cells age by one every generation
        age++;

        // The head of the snake can move and consume another cell
        if(isHead)
        {
            Location newLocation = getNewLocation(getLocation());

            // Choses a random adjacent location, and if there is a living cell, can infect
            if((newLocation != null) && (field.getObjectAt(newLocation)!= null))
            {
                Cell cellToConsume = field.getObjectAt(newLocation);
                consume(cellToConsume);
            }
            else
            {
                // Doesn't age/ perform any actions, and waits
                setNextState(true);
            }
        }
        // The head of the snake cannot die, body cells will die after 15 generations
        else if(age >= 15){
            setNextState(false);
        }
    }

    /**
     * Moves to the location of the cell to be consumed. The cell is consumed and the location
     * instead holds the head of the snake. The snake gets one cell longer.
     */
    private void consume(Cell neighbour)
    {
        // Clears the consumed cell's location
        field.clear(neighbour.getLocation());
        // Creates a new snake head at the location of the consumed cell
        Snake newHead = new Snake (super.field, neighbour.getLocation(), Color.DARKGREEN, true);
        // Sets the current snake cell as not the head
        this.isHead = false;
        // Changes the snake's colour to its general one, as it's no longer the head
        this.setColor(Color.SPRINGGREEN);
        // Sets the next state of the current snake to alive, making it longer
        this.setNextState(true);
    }


    /**
     * Choses a direction for the snake to go in, up, down, left or right
     * Returns the location which the head of the snake will move to
     */
    private Location getNewLocation(Location currentLocation)
    {
        Location newLocation = null;

        Random rand = Randomizer.getRandom();

        // Sets the possible directions as up, down, left and right
        int[][] directions = { {1,0}, {-1,0}, {0,1}, {0,-1} };

        // Choses one of the random direction vectors
        int random = (int) (rand.nextInt(4));
        int[] chosenDirection = directions[random];

        // Gets the new location of the snake head by using the direction and current location
        LinkedList<Location> locations = field.prepareMove(currentLocation, 1, chosenDirection);

        // Checks if the location to move to does not hold a snake cell and is a location on the grid
        assert locations.peekLast() != null;

        if ((locations.peekLast() != null) && (field.getObjectAt(locations.peekLast()) != null)) {
            if( !(field.getObjectAt(locations.peekLast()) instanceof Snake)){
                newLocation = locations.peekLast();
            }
        }

        return newLocation;
    }

}
