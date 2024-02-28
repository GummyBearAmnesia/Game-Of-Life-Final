/**
 * Represent a location in a rectangular grid.
 *
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29
 */

public class Location {

    private int row;
    private int column;

    /**
     * Represent a row and columnumn.
     * @param row The row.
     * @param column The columnumn.
     */
    public Location(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Implement content equality.
     */
    public boolean equals(Object obj) {
        if (obj instanceof Location) {
            Location other = (Location) obj;
            return row == other.getRow() && column == other.getColumn();
        }
        else {
            return false;
        }
    }

    /**
     * Return a string of the form row,column
     * @return A string representation of the location.
     */
    public String toString() {
        return row + "," + column;
    }

    /**
     * Use the top 16 bits for the row value and the bottom for
     * the columnumn. Except for very big grids, this should give a
     * unique hash code for each (row, column) pair.
     * @return A hashcode for the location.
     */
    public int hashCode() {
        return (row << 16) + column;
    }

    /**
     * @return The row.
     */
    public int getRow() {
        return row;
    }

    /**
     * @return The columnumn.
     */
    public int getColumn() {
        return column;
    }
}
