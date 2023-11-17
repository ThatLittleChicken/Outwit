package com.cs203.test1;

import android.graphics.Color;
import android.graphics.Paint;

public class DarkTheme implements Theme {

    private Paint darkChipColor, lightChipColor, cellColor, darkCellColor, lightCellColor, moveColor, borderColor, textColor, titleBG;

    /***
     * constructor to set the colors of Paint
     */
    public DarkTheme() {
        darkChipColor = new Paint();
        darkChipColor.setColor(Color.rgb(212, 63, 68));
        lightChipColor = new Paint();
        lightChipColor.setColor(Color.rgb(2, 168, 245));
        cellColor = new Paint();
        cellColor.setColor(Color.rgb(40, 40, 40));
        darkCellColor = new Paint();
        darkCellColor.setColor(Color.rgb(232, 72, 75));
        lightCellColor = new Paint();
        lightCellColor.setColor(Color.rgb(92, 166, 209));
        moveColor = new Paint();
        moveColor.setColor(Color.rgb(100, 209, 100));
        borderColor = new Paint();
        borderColor.setColor(Color.rgb(75, 75, 75));
        textColor = new Paint();
        textColor.setColor(Color.rgb(194, 194, 194));
        titleBG = new Paint();
        titleBG.setColor(Color.rgb(40, 40, 40));
    }

    @Override
    public Paint getDarkChipColor() {
        return darkChipColor;
    }

    @Override
    public Paint getLightChipColor() {
        return lightChipColor;
    }


    @Override
    public Paint getCellColor() {
        return cellColor;
    }

    @Override
    public Paint getDarkCellColor() {
        return darkCellColor;
    }

    @Override
    public Paint getLightCellColor() {
        return lightCellColor;
    }

    @Override
    public Paint getMoveColor() {
        return moveColor;
    }

    @Override
    public int getBackgroundColor() {
        return Color.rgb(25, 25, 25);
    }

    @Override
    public Paint getBorderColor() {
        return borderColor;
    }

    @Override
    public Paint getTextColor() {
        return textColor;
    }

    @Override
    public Paint getTitleBG() {
        return titleBG;
    }
}
