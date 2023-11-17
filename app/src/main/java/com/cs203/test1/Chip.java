package com.cs203.test1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

/***
 * Chip class to create individual chips
 */
public class Chip {
    int color;
    Cell host;
    RectF currentLocation;
    static Paint goldColor;
    boolean power;
    boolean selected;
    private PointF velocity;
    private Cell destination;
    private boolean moving;

    static {
        goldColor = new Paint();
        int gold = android.graphics.Color.rgb(202, 192, 6);
        goldColor.setColor(gold);
    }

    /***
     * constructor
     * @param color color of chip
     * @param host Cell that has the chip
     * @param power is power chip
     */
    private Chip(int color, Cell host, boolean power) {
        this.color = color;
        this.host = host;
        this.power = power;
        currentLocation = new RectF();
        host.isOccupied = true;
        velocity = new PointF(0, 0);
        destination = null;
    }

    /***
     * draw chip
     * @param c canvas
     * @param p paint
     */
    public void draw(Canvas c, Paint p, Theme theme) {
        RectF bounds = currentLocation;
        if (selected) {
            c.drawOval(bounds.left - bounds.width() / 4, bounds.top - bounds.height() / 4, bounds.right + bounds.width() / 4, bounds.bottom + bounds.height() / 4, goldColor);
        }
        if (color == Team.LIGHT) {
            c.drawOval(bounds, theme.getLightChipColor());
        } else if (color == Team.DARK) {
            c.drawOval(bounds, theme.getDarkChipColor());
        }
        p.setStyle(Paint.Style.STROKE);
        c.drawOval(bounds, p);
        if (power) {
            c.drawOval(bounds.left + bounds.width() / 3.5f, bounds.top + bounds.height() / 3.5f, bounds.right - bounds.width() / 3.5f, bounds.bottom - bounds.height() / 3.5f, goldColor);
        }
    }

    /***
     * animate chip by offsetting current location by velocity
     */
    public boolean animate(Context c) {
        if(destination != null) {
            if (!SettingsActivity.getAnimationPreference(c)) {
                currentLocation.offset(velocity.x, velocity.y);
                float dx = currentLocation.left - destination.bounds.left;
                float dy = currentLocation.top - destination.bounds.top;
                float distance = (float) Math.hypot(dx, dy);
                if (distance < host.bounds.width() / 2) {
                    setCell(destination);
                    velocity.x = 0;
                    velocity.y = 0;
                    moving = false;
                    return true;
                }
            } else {
                setCell(destination);
                velocity.x = 0;
                velocity.y = 0;
                moving = false;
                return true;
            }
        }
        return false;
    }

    /***
     * calculate velocity and direction
     * @param destination cell to move to
     * @param c context
     */
    public void setDestination(Cell destination, Context c) {
        this.destination = destination;
        float aniSpeed = SettingsActivity.getAnimationSpeedPreference(c);
        velocity.x = Math.signum(destination.x - host.x);
        velocity.y = Math.signum(destination.y - host.y);
        velocity.x *= host.bounds.width()/aniSpeed;
        velocity.y *= host.bounds.height()/aniSpeed;
    }

    /***
     * changes host cell of chip
     * sets occupied status of cells
     * set variable currentLocation to host cell's bounds
     * @param c new host cell
     */
    public void setCell(Cell c) {
        if(host.isOccupied) {
            host.isOccupied = false;
        }
        host = c;
        host.isOccupied = true;
        currentLocation.set(host.bounds);
    }

    /***
     * setter for is chip moving field
     * @param moving is chip moving
     */
    public void moving(boolean moving) {
        this.moving = moving;
    }

    /***
     * getter for is chip moving field
     * @return moving
     */
    public boolean isMoving() {
        return moving;
    }

    /***
     * factory method to create power chip
     * @param color color of chip
     */
    public static Chip power(int color, Cell host) {
        return new Chip(color, host, true);
    }

    /***
     * factory method to create normal chip
     * @param color color of chip
     */
    public static Chip normal(int color, Cell host) {
        return new Chip(color, host, false);
    }

    /***
     * getter for current cell
     * @return host
     */
//    public Cell getCurrentCell() {
//        return host;
//    }


//    public boolean isHome() {
//        return (color == host.color);
//    }
}
