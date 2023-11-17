package com.cs203.test1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;


public class Aview extends View {

    private Theme theme;
    private Paint border, text, white;
    private boolean init = false;
    private boolean alrSelected = false;
    private boolean end = false;
    private Cell[][] cells = new Cell[9][10];
    private ArrayList<Chip> chips = new ArrayList<Chip>();
    private ArrayList<Cell> legalMoves = new ArrayList<Cell>();
    private Stack<Move> undoStack = new Stack<Move>();
    private Bitmap undo;
    private Toast t;
    private int turn;

    /***
     * Constructor for Aview and set colors
     * @param c context
     */
    public Aview(Context c) {
        super(c);
        white = new Paint();
        white.setColor(Color.LTGRAY);
        theme = SettingsActivity.getThemePreference(c);
        border = theme.getBorderColor();
        text = theme.getTextColor();
        undo = BitmapFactory.decodeResource(getResources(), R.drawable.undo);
    }

    /***
     * Override the onDraw method to add drawings
     * Draws 9x10 board, 2 3x3 corners different color
     * @param c canvas
     */
    @Override
    public void onDraw(Canvas c) {
        if(!init){
            float cellSize = getWidth()/9f;
            new hands();

            //instantiate cells
            for(int i=0; i<9; i++){
                for(int j=0; j<10; j++){
                    int color;
                    if(i>5 && j<3){
                        color = Team.DARK;
                    }else if(i<3 && j>6){
                        color = Team.LIGHT;
                    }else{
                        color = Team.NEUTRAL;
                    }
                    cells[i][j] = new Cell(i, j, cellSize, color);
                }
            }

            //instantiate chips
            for(int i=0; i<9; i++){
                Chip dark = null;
                Chip light = null;
                switch (SettingsActivity.getChipPreference(getContext())){
                    case 0:
                        if(i==4){
                            dark = Chip.power(Team.DARK, cells[i][i+1]);
                            light = Chip.power(Team.LIGHT, cells[i][i]);
                        }else{
                            dark = Chip.normal(Team.DARK, cells[i][i+1]);
                            light = Chip.normal(Team.LIGHT, cells[i][i]);
                        }
                        break;
                    case 1:
                        dark = Chip.normal(Team.DARK, cells[i][i+1]);
                        light = Chip.normal(Team.LIGHT, cells[i][i]);
                        break;
                    case 2:
                        dark = Chip.power(Team.DARK, cells[i][i+1]);
                        light = Chip.power(Team.LIGHT, cells[i][i]);
                        break;
                    }
                dark.setCell(cells[i][i+1]);
                light.setCell(cells[i][i]);
                chips.add(dark);
                chips.add(light);
            }

            //scale undo button
            undo = Bitmap.createScaledBitmap(undo, (int)cellSize*5/4, (int)cellSize*5/4, true);

            //get or randomize turn
            if (SettingsActivity.getStartingPreference(getContext()) == 0){
                turn = (int)Math.round(Math.random()+1);
            } else {
                turn = SettingsActivity.getStartingPreference(getContext());
            }

            init = true;
        }

        float w = getWidth();
        float h = getHeight();
        border.setStrokeWidth(w/300f);

        //draw background
        c.drawColor(theme.getBackgroundColor());

        //draw undo button
        if (!SettingsActivity.getUndoPreference(getContext())) {
            c.drawRoundRect(w-w/9*5/3, h-w/9*5/3, w*103/108, h-w*4/108, 20, 20, white);
            c.drawRoundRect(w-w/9*5/3, h-w/9*5/3, w*103/108, h-w*4/108, 20, 20, border);
            c.drawBitmap(undo, w-w/9*5/3, h-w/9*5/3, null);
        }

        //draw board and grid
        c.drawRect(0,0,w,w/9*10,theme.getCellColor());
        c.drawRect(w/9*6, 0, w, w/9*3, theme.getDarkCellColor());
        c.drawRect(0, w/9*7, w/9*3, w/9*10, theme.getLightCellColor());

        float lineX = 0;
        for(int i=0; i<=9;i++){
            c.drawLine(lineX + w/9 * i, 0, lineX + w/9 * i,w / 9 * 10,  theme.getBorderColor());
        }
        for(int i=0; i<=10; i++){
            c.drawLine(0, lineX + w/9 *i, w, w/9*i, theme.getBorderColor());
        }

        //draw chips
        for(Chip chipz: chips){
            chipz.draw(c, border, theme);
        }

        for(Cell cellz: legalMoves){
            RectF a = cellz.getBounds();
            c.drawOval(a.left + a.width()/4f, a.top + a.height()/4f, a.right - a.width()/4f, a.bottom - a.height()/4f, theme.getMoveColor());
        }

        //draw turn indicator
        text.setTextSize(w/18);
        if(turn == Team.LIGHT){
            c.drawText("Blue's turn", w/9/3, w/9*43/4, text);
        } else if (turn == Team.DARK) {
            c.drawText("Red's turn", w/9/3, w/9*43/4, text);
        }
    }

    /***
     * Override the onTouchEvent method to add touch event
     * highlight legal moves, check Cells for legal moves
     * move selected chip to destination cell
     * @param e The motion event.
     * @return boolean
     */
    @Override
    public boolean onTouchEvent(MotionEvent e){
        float x = e.getX();
        float y = e.getY();
        boolean tappedChip = false;

        //check if chip is selected
        if(e.getAction() == MotionEvent.ACTION_DOWN){

            for(Chip chips: chips){
                if(anyMovingChips()) {
                    return true;
                }
                if(chips.host.getBounds().contains(x,y) && !alrSelected && chips.color == turn){
                    chips.selected = true;
                    tappedChip = true;
                    alrSelected = true;
                }
            }

            for(Cell move: legalMoves){
                if(move.getBounds().contains(x,y)){
                    for(Chip chips: chips){
                        if(chips.selected){
                            undoStack.push(new Move(chips.host, move));
                            chips.setDestination(move,getContext());
                            chips.selected = false;
                            chips.moving(true);
                            turn = turn == Team.LIGHT ? Team.DARK : Team.LIGHT;
                        }
                    }
                }
            }

            if(!SettingsActivity.getUndoPreference(getContext()) && x > getWidth()-getWidth()/9*5/3 && y > getHeight()-getWidth()/9*5/3){
                undoLastMove();
                if(undoStack.size() != 0) {
                    turn = turn == Team.LIGHT ? Team.DARK : Team.LIGHT;
                }
            }

            if(!tappedChip){
//                for(Chip chips: chips){
//                    chips.selected = false;
//                }
                Arrays.stream(chips.toArray()).forEach(chip -> ((Chip)chip).selected = false);
                legalMoves.clear();
                alrSelected = false;
                return true;
            }

            //find legal moves
            for(Chip chips: chips){
                if(chips.selected){
                    legalMoves.clear();
                    Cell currentcell = chips.host;
                    int chipx = currentcell.getX();
                    int chipy = currentcell.getY();
                    Cell candidate;

                    if (!chips.power) {
                        candidate = null;
                        for(int i=chipx+1; i<=8; i++){
                            if(cells[i][chipy].isLegalMove(chips)){
                                candidate = cells[i][chipy];
                            }else{
                                break;
                            }
                        }

                        if(candidate != null){
                            legalMoves.add(candidate);
                        }

                        candidate = null;
                        for(int i=chipx-1; i>=0; i--){
                            if(cells[i][chipy].isLegalMove(chips)){
                                candidate = cells[i][chipy];
                            }else{
                                break;
                            }
                        }

                        if(candidate != null){
                            legalMoves.add(candidate);
                        }

                        candidate = null;
                        for(int i=chipy+1; i<=9; i++){
                            if(cells[chipx][i].isLegalMove(chips)){
                                candidate = cells[chipx][i];
                            }else{
                                break;
                            }
                        }

                        if(candidate != null){
                            legalMoves.add(candidate);
                        }

                        candidate = null;
                        for(int i=chipy-1; i>=0; i--){
                            if(cells[chipx][i].isLegalMove(chips)){
                                candidate = cells[chipx][i];
                            }else{
                                break;
                            }
                        }

                        if(candidate != null){
                            legalMoves.add(candidate);
                        }
                    } else {
                        int i,j;

                        for (i = chipx + 1; i <= 8; i++) {
                            if (cells[i][chipy].isLegalMove(chips)) {
                                candidate = cells[i][chipy];
                                legalMoves.add(candidate);
                            } else {
                                break;
                            }
                        }

                        for (i = chipx - 1; i >= 0; i--) {
                            if (cells[i][chipy].isLegalMove(chips)) {
                                candidate = cells[i][chipy];
                                legalMoves.add(candidate);
                            } else {
                                break;
                            }
                        }

                        for (i = chipy + 1; i <= 9; i++) {
                            if (cells[chipx][i].isLegalMove(chips)) {
                                candidate = cells[chipx][i];
                                legalMoves.add(candidate);
                            } else {
                                break;
                            }
                        }

                        for (i = chipy - 1; i >= 0; i--) {
                            if (cells[chipx][i].isLegalMove(chips)) {
                                candidate = cells[chipx][i];
                                legalMoves.add(candidate);
                            } else {
                                break;
                            }
                        }

                        i = chipx + 1;
                        j = chipy - 1;
                        while(i<=8 && j>=0){
                            if(cells[i][j].isLegalMove(chips)){
                                candidate = cells[i][j];
                                legalMoves.add(candidate);
                                i++;
                                j--;
                            }else{
                                break;
                            }
                        }

                        i = chipx - 1;
                        j = chipy - 1;
                        while(i>=0 && j>=0){
                            if(cells[i][j].isLegalMove(chips)){
                                candidate = cells[i][j];
                                legalMoves.add(candidate);
                                i--;
                                j--;
                            }else{
                                break;
                            }
                        }

                        i = chipx + 1;
                        j = chipy + 1;
                        while(i<=8 && j<=9){
                            if(cells[i][j].isLegalMove(chips)){
                                candidate = cells[i][j];
                                legalMoves.add(candidate);
                                i++;
                                j++;
                            }else{
                                break;
                            }
                        }

                        i = chipx - 1;
                        j = chipy + 1;
                        while(i>=0 && j<=9){
                            if(cells[i][j].isLegalMove(chips)){
                                candidate = cells[i][j];
                                legalMoves.add(candidate);
                                i--;
                                j++;
                            }else{
                                break;
                            }
                        }
                    }
                }
            }
        }

        invalidate();
        return true;
    }

    /***
     * undo last move
     * unset selected chip
     * show toast if no more moves to undo
     */

    public void undoLastMove() {
        for(Chip chips: chips){
            chips.selected = false;
        }
        legalMoves.clear();
        if(undoStack.size() == 0){
            if(t != null) {
                t.setText("No more moves to undo");
                t.show();
            } else {
                t = Toast.makeText(getContext(), "No more moves to undo", Toast.LENGTH_SHORT);
                t.show();
            }
        } else {
            Move lastMove = undoStack.pop();
            for(Chip chip: chips) {
                if(chip.host == lastMove.getDestination()) {
                    chip.selected = true;
                    chip.setDestination(lastMove.getSource(), getContext());
                }
            }
        }
    }

    /***
     * handler inner class to handle animation and check for winner every 10ms
     */
    public class hands extends Handler{
        public hands(){
            sendMessageDelayed(obtainMessage(), 10);
        }

        @Override
        public void handleMessage(Message msg){
            for(Chip chips: chips){
                boolean moved = chips.animate(getContext());
                if(moved){
                    checkForWinner();
                }
            }
            invalidate();
            sendMessageDelayed(obtainMessage(), 10);
        }
    }

    /***
     * check if any chips are moving
     * @return boolean
     */
    private boolean anyMovingChips(){
        boolean result = false;
        for(Chip c: chips){
            if(c.isMoving()){
                result = true;
                break;
            }
        }
        return result;
    }

    /***
     * check for winner
     * if 9 chips of same color, show alert dialog
     */
    private void checkForWinner() {
        int darkCount = 0;
        int lightCount = 0;
        String winner = "";
        for(Chip c: chips) {
            if(c.host.color == Team.DARK) {
                darkCount++;
            } else if(c.host.color == Team.LIGHT) {
                lightCount++;
            }
        }

        winner = darkCount == 9 ? "Blue" : "Red";

        AlertDialog.Builder ab = new AlertDialog.Builder(getContext())
                .setTitle("Winner")
                .setMessage("Congratulations! " + winner + " wins!")
                .setPositiveButton("Play again!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((Activity)getContext()).recreate();
                    }
                })
                .setNegativeButton("Quit", (d, i) -> ((Activity)getContext()).finish())
                .setCancelable(false);

        if(darkCount == 9 && !end) {
            ab.show();
            end = true;
        } else if(lightCount == 9 && !end) {
            ab.show();
            end = true;
        }
    }
}
