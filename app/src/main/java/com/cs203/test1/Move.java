package com.cs203.test1;

/***
 * Move class to create individual moves
 */
public class Move {
    private Cell from;
    private Cell to;

    /***
     * constructor
     * @param from source cell
     * @param to destination cell
     */
    public Move(Cell from, Cell to) {
        this.from = from;
        this.to = to;
    }

    /***
     * source getter
     * @return from cell
     */
    public Cell getSource() {
        return from;
    }

    /***
     * destination getter
     * @return to cell
     */
    public Cell getDestination() {
        return to;
    }
}
