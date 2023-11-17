package com.cs203.test1;

import android.graphics.RectF;

/***
 * Cell class to create individual squares
 */
public class Cell {
    int x, y;
    RectF bounds;
    int color;
    boolean isOccupied;

    /***
     * constructor and calculate cell location
     * @param x nth cell on x axis
     * @param y nth cell on y axis
     * @param cellSize width of cell
     * @param color color of cell
     */
    public Cell(int x, int y, float cellSize, int color) {
        this.x = x;
        this.y = y;
        this.color = color;
        isOccupied = false;
        float offset = cellSize * 9 / 150; //add space between cells
        bounds = new RectF(x * cellSize + offset, y * cellSize + offset, (x + 1) * cellSize - offset, (y + 1) * cellSize - offset);
    }

    /***
     * check if move is legal
     * color: this cell's color
     * c.color: given chip's color
     * c.host.color: given chip's host cell's color
     * @param  c selected Chip to check
     * @return boolean when animation is done
     */
    public boolean isLegalMove(Chip c) {
        boolean legal = true;
        if (isOccupied) { //if this cell already has another chip on it:
            legal = false;
        //} else if (c.isHome()) {
        } else if (c.host.color == c.color && c.host.color == Team.NEUTRAL) { //if the given chip is already in a "home" square but this cell's is neutral:
            legal = false;
        } else if (c.color == color) { //if the given chip's color is the same as this cell's color:
            legal = true;
        } else if (c.host.color == Team.NEUTRAL && color == Team.NEUTRAL) { //if the given chip is already on a neutral square and this cell's color is neutral:
            legal = true;
        } else if (color != c.color) { //if the cell's color is different than the given chip's color:
            legal = false;
        }
        return legal;
    }

    /***
     * bounds RectF location getter
     * @return bounds
     */
    public RectF getBounds() {
        return bounds;
    }

    /***
     * x location getter
     * @return x
     */
    public int getX() {
        return x;
    }

    /***
     * y location getter
     * @return y
     */
    public int getY() {
        return y;
    }
}
