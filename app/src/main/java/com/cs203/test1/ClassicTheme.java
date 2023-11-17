package com.cs203.test1;

import android.graphics.Color;
import android.graphics.Paint;

public class ClassicTheme implements Theme {

    private Paint darkChipColor, lightChipColor, cellColor, darkCellColor, lightCellColor, moveColor, borderColor, textColor, titleBG;

    /***
     * constructor to set the colors of Paint
     */
    public ClassicTheme() {
        darkChipColor = new Paint();
        darkChipColor.setColor(Color.rgb(237, 115, 119));
        lightChipColor = new Paint();
        lightChipColor.setColor(Color.rgb(56, 174, 230));
        cellColor = new Paint();
        cellColor.setColor(Color.rgb(234, 217, 189));
        darkCellColor = new Paint();
        darkCellColor.setColor(Color.rgb(242, 156, 158));
        lightCellColor = new Paint();
        lightCellColor.setColor(Color.rgb(140, 192, 222));
        moveColor = new Paint();
        moveColor.setColor(Color.rgb(130, 214, 130));
        borderColor = new Paint();
        borderColor.setColor(Color.BLACK);
        textColor = new Paint();
        textColor.setColor(Color.BLACK);
        titleBG = new Paint();
        titleBG.setColor(Color.rgb(243, 243, 243));
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
        return Color.rgb(202, 182, 157);
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
