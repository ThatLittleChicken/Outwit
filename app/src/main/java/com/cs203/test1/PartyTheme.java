package com.cs203.test1;

import android.graphics.Color;
import android.graphics.Paint;

public class PartyTheme implements Theme {

    private Paint darkChipColor, lightChipColor, cellColor, darkCellColor, lightCellColor, moveColor, borderColor, textColor, titleBG;

    /***
     * constructor to set the colors of Paint
     */
    public PartyTheme() {
        darkChipColor = new Paint();
        darkChipColor.setColor(Color.rgb(181, 23, 158));
        lightChipColor = new Paint();
        lightChipColor.setColor(Color.rgb(72, 149, 239));
        cellColor = new Paint();
        cellColor.setColor(Color.rgb(58, 12, 163));
        darkCellColor = new Paint();
        darkCellColor.setColor(Color.rgb(247, 37, 133));
        lightCellColor = new Paint();
        lightCellColor.setColor(Color.rgb(76, 201, 240));
        moveColor = new Paint();
        moveColor.setColor(Color.rgb(100, 209, 100));
        borderColor = new Paint();
        borderColor.setColor(Color.rgb(90, 90, 90));
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
