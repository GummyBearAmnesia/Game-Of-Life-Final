import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * The Color Changing Cell changes colour every 5 generations, from light blue -> purple -> dark blue.
 * After 15 generations the cell will die.
 * If the colour changing cell has 3 or more valid neighbours it will reproduce.
 *
 * @author Onessa Crispeyn K23080159
 *         Aamukta Thogata K2369720
 * @version 11/02/2024
 */
public class ColorChangingCell extends Cell
{
    private int age;

    /**
     * Constructor for objects of class ColorChangingCell
     */
    public ColorChangingCell(Field field, Location location, Color col)
    {
        super(field, location ,col);
        this.age = 0;
    }

    /**
     * Allows the color changing cell to act and determines whether it is alive or not in th e
     * next generation
     */
    public void act()
    {
        if(isAlive()){
            // Each generation, its age will increase, it may change colour and reproduce
            age++;
            setNextState(true);

            changeColor();
            reproduce();
        }
    }


    /**
     * reproduce() Allows the Color Changing Cell to reproduce if the conditions are met.
     * If the cell has 3+ neighbours which are the same cell type, it makes a new baby cell.
     * The baby cell is placed in an empty space next to the cell.
     */
    private void reproduce(){
        // Gets a list of the cell's alive neighbours
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        /// An arraylist that stores the cells' neighbours
        ArrayList<Cell> colorNeighbours = new ArrayList<Cell>();

        for (int i = 0; i < neighbours.size(); i++){
            if (neighbours.get(i) instanceof ColorChangingCell){
                colorNeighbours.add(neighbours.get(i));
            }
        }
        // Reproduces if the cell has 3+ neighbours, and if a valid empty location is found that the baby cell
        // can be placed next to
        if ((colorNeighbours.size() >= 3)){
            Location randomEmptyLocation = getField().randomEmptyAdjacentLocation(getLocation());
            if (randomEmptyLocation != null){
                ColorChangingCell baby = new ColorChangingCell(super.field, randomEmptyLocation, Color.LIGHTBLUE);
                // Sets the next state of the baby cell to alive, and places the baby cell in a random empty location
                baby.setNextState(true);
                super.field.place(baby, randomEmptyLocation);
            }
        }
    }


    /**
     * Allows the cell to colour based on the cell's age.
     * Turns light blue between age 0-4, blue-violet between ages 5-9, blue between ages 10-14, 
     * When the cell reaches age 15, it dies.
     */
    private void changeColor(){
        if (age >= 0 && age < 5){
            setColor(Color.LIGHTBLUE);
        }
        else if (age >= 5 && age < 10){
            setColor(Color.BLUEVIOLET);
        }
        else if (age >= 10 && age < 15){
            setColor(Color.BLUE);
        }
        else if (age >= 15){
            // After 15 generations the cell dies.
            setDead();
            setNextState(false);
        }
    }
}
