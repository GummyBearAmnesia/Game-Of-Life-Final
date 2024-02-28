import java.util.Collections;
import java.util.*;

/**
 * Represent a rectangular grid of field positions.
 * Each position stores a single cell
 *
 * @author David J. Barnes, Michael Kölling & Jeffery Raphael
 * @version 2022.01.06
 */

public class Field {
    private static final Random rand = Randomizer.getRandom();
    private int depth, width;
    private Cell[][] field;

    /**
     * Represent a field of the given dimensions.
     * @param depth The depth of the field.
     * @param width The width of the field.
     */
    public Field(int depth, int width) {
        this.depth = depth;
        this.width = width;
        field = new Cell[depth][width];
    }

    /**
     * Empty the field.
     */
    public void clear() {
        for (int row = 0; row < depth; row++) {
            for (int col = 0; col < width; col++) {
                field[row][col] = null;
            }
        }
    }

    /**
     * Clear the given location.
     * @param location The location to clear.
     */
    public void clear(Location location) {
        field[location.getRow()][location.getColumn()] = null;
    }

    /**
     * Place a cell at the given location.
     * If there is already a cell at the location it will be lost.
     * @param cell The cell to be placed.
     * @param row Row coordinate of the location.
     * @param col Column coordinate of the location.
     */
    public void place(Cell cell, int row, int col) {
        place(cell, new Location(row, col));
    }

    /**
     * Place a cell at the given location.
     * If there is already a cell at the location it will be lost.
     * @param cell The cell to be placed.
     * @param location Where to place the cell.
     */
    public void place(Cell cell, Location location) {
        field[location.getRow()][location.getColumn()] = cell;
    }

    /**
     * Return the cell at the given location, if any.
     * @param location Where in the field.
     * @return The cell at the given location, or null if there is none.
     */
    public Cell getObjectAt(Location location) {
        return getObjectAt(location.getRow(), location.getColumn());
    }

    /**
     * Return the cell at the given location, if any.
     * @param row The desired row.
     * @param col The desired column.
     * @return The cell at the given location, or null if there is none.
     */
    public Cell getObjectAt(int row, int col) {
        return field[row][col];
    }


    /**
     * Picks a direction, and "moves" the cell in the direction, the number of steps given.
     * Returns a list of locations, each step the cell takes, can be acted on in the cell methods.
     */
     public LinkedList<Location> prepareMove(Location location, int numSteps, int[] direction) {

         // direction is a cartesian vector in the form [x,y]
        Location newLocation = null;
        LinkedList<Location> locations = new LinkedList<>();

        int row = location.getRow();
        int col = location.getColumn();

        for (int i = 0; i < numSteps; i++) {
            row = row + direction[0];
            col = col + direction[1];

            newLocation = new Location(row, col);
            if (newLocation.getColumn() < width && newLocation.getColumn() > 0) {
                if (newLocation.getRow() < depth && newLocation.getRow() > 0) {
                    locations.add(newLocation);
                }
            }
        }
        System.out.println("So the current Location is" + location);
        System.out.println("SO THE LOCATIONS PASSED ARE"+ locations);

        return locations;
    }


    /**
     * Generate a random location that is adjacent to the
     * given location, or is the same location.
     * The returned location will be within the valid bounds
     * of the field.
     * @param location The location from which to generate an adjacency.
     * @return A valid location within the grid area.
     */
    public Location randomAdjacentLocation(Location location) {
        List<Location> adjacent = adjacentLocations(location);
        return adjacent.get(0);
    }

    /**
     * Return a shuffled list of locations adjacent to the given one.
     * The list will not include the location itself.
     * All locations will lie within the grid.
     * @param location The location from which to generate adjacencies.
     * @return A list of locations adjacent to that given.
     */
    public List<Location> adjacentLocations(Location location) {
        assert location != null : "Null location passed to adjacentLocations";
        // The list of locations to be returned.
        List<Location> locations = new LinkedList<>();
        if (location != null) {
            int row = location.getRow();
            int col = location.getColumn();
            for (int roffset = -1; roffset <= 1; roffset++) {
                int nextRow = row + roffset;
                if (nextRow >= 0 && nextRow < depth) {
                    for (int coffset = -1; coffset <= 1; coffset++) {
                        int nextCol = col + coffset;
                        // Exclude invalid locations and the original location.
                        if (nextCol >= 0 && nextCol < width && (roffset != 0 || coffset != 0)) {
                            locations.add(new Location(nextRow, nextCol));
                        }
                    }
                }
            }

            // Shuffle the list. Several other methods rely on the list
            // being in a random order.
            Collections.shuffle(locations, rand);
        }
        return locations;
    }

    /**
     * Return a shuffled list of EMPTY locations adjacent to the given one.
     * The list will not include the location itself.
     * All locations will lie within the grid.
     * @param location The location from which to generate adjacencies.
     * @return A list of locations adjacent to that given.
     */
    public List<Location> getEmptyAdjacentLocations(Location location){
        List<Location> empty = new LinkedList<>();

        if (location != null){
            List<Location> adjLocations = adjacentLocations(location);

            for (Location loc: adjLocations) {
                Cell cell = field[loc.getRow()][loc.getColumn()];
                if (cell != null){
                    if (!cell.isAlive()){
                        empty.add(loc);
                    }
                }
            //Collections.shuffle(empty, rand);
            }
            //System.out.println(empty);
        }
        return empty;
    }

    /**
     * Generate a random EMPTY location that is adjacent to the
     * given location, or is the same location.
     * The returned location will be within the valid bounds
     * of the field.
     * @param location The location from which to generate an adjacency.
     * @return A valid location within the grid area.
     */
    public Location randomEmptyAdjacentLocation(Location location) {
        List<Location> emptyLocations = getEmptyAdjacentLocations(location);
        if (!emptyLocations.isEmpty()){
            return emptyLocations.get(0);}
        else{
            return null;
        }
    }


    /**
     * Get a shuffled list of living neighbours
     * @param location Get locations adjacent to this.
     * @return A list of living neighbours
     */
    public List<Cell> getLivingNeighbours(Location location) {

        assert location != null : "Null location passed to adjacentLocations";
        List<Cell> neighbours = new LinkedList<>();

        List<Location> adjLocations = adjacentLocations(location);

        for (Location loc : adjLocations) {
            Cell cell = field[loc.getRow()][loc.getColumn()];
            if (cell != null) {
                //System.out.println(cell.isAlive());
                if (cell.isAlive())
                    neighbours.add(cell);
            }
            Collections.shuffle(neighbours, rand);
        }
        return neighbours;
    }


    /**
     * Return the depth of the field.
     * @return The depth of the field.
     */
    public int getDepth() {
        return depth;
    }

    /**
     * Return the width of the field.
     * @return The width of the field.
     */
    public int getWidth() {
        return width;
    }
}
