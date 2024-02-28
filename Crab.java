import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.Random;

/**
 * A crab cell.
 * Each generation, will move a random number of squares in a
 * random direction. Will kill everything in its path.
 * Nothing can kill the crab, the crab cannot become infected.
 * The crab can only be eaten by the snake.
 *
 * @author Aamukta Thogata K2369720
 *         Onessa Crispeyn K23080159
 * @24/Feb
 */

public class Crab extends Cell
{
    public Crab(Field field, Location location, Color col){ super(field, location, col);}
    Random rand = Randomizer.getRandom();

    /**
     * Crab chooses a random direction to move in, and a random number of steps each generation.
     * Everything that the crab crosses paths with, will die. Should leave a trail of destruction
     * everywhere it goes.
     */
    public void act()
    {
        // The crab cannot die, moves every generation.
        if(isAlive()){
            setNextState(true);
            move();
        }
    }


    /**
     * move() Allows the crab cell to move in a random direction, in a random number of steps.
     */
    private void move()
    {
        // The direction is a cartesian vector in the form {+row, +column} showing the direction of travel
        int[] direction  = getRandomDirection();
        int numSteps = (int) (rand.nextInt(30) + -1);

        // prepareMove() plans the route that the cell will take, given the current location
        // number of steps, and the direction of travel. Returns a list of locations passed through.
        LinkedList<Location> locations = field.prepareMove(getLocation(), numSteps, direction);

        //Clears the field in all the locations which the crab has passed over.
        obliterate(locations);

        //Places the crab in the new location ( the last valid location travelled to from prepareMove)
        Location newLocation = locations.peekLast();

        if(newLocation != null)
        {
            Crab movedCrab = new Crab(super.field, newLocation, getColor());
            movedCrab.setNextState(true);

            //Sets the current (old) crab located as dead
            setNextState(false);
        }
    }


    /**
     * Method to randomly choose a direction, in cartesian vector form [+row, +column]
     * For example, [0,1] would move it to the right ( +1 column ) and [0,-1] to the left ( -1 column )
     * Returns the output, to be used in the move method.
     */
    private int[] getRandomDirection()
    {
        // Generates a random number between -1 and 1 for x
        int x = (int) (rand.nextInt(3) + -1);
        
        // Generates a random number between -1 and 1 for y
        int y = (int) (rand.nextInt(3) + -1);

        int[] direction = {x,y};

        return direction;
    }


    /**
     * Destroys (clears) the field in all the locations which the crab has passed over.
     */
    private void obliterate(LinkedList<Location> locations)
    {
        // Iterate through the list of locations that are returned, field.clear all locations in the list
        Random rand = Randomizer.getRandom();

        for (Location l: locations){
            field.clear(l);
            Mycoplasma myco = new Mycoplasma(field, l, Color.ORANGE);
            myco.setDead();
        }
    }
}
