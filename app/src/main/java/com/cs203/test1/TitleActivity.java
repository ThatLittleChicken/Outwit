package com.cs203.test1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/***
 * TitleActivity class
 */
public class TitleActivity extends Activity {

    private ImageView bg;
    private View v;

    /***
     * onCreate method to set content view to TitleView
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        bg = new ImageView(this);
//        bg.setImageResource(R.drawable.outwit_light);
//        bg.setScaleType(ImageView.ScaleType.FIT_START);
//        setContentView(bg);
//  }
        v = new TitleView(this);
        setContentView(v);
    }

    /***
     * inner TitleView class
     */
    class TitleView extends View {

        private Theme theme;
        private Paint orange;
        private Paint orangetxt;
        private Paint black;
        private Bitmap qm;
        private Bitmap cog;
        private Bitmap board;
        private boolean init;
        private boolean p2b;
        private boolean abb;
        private boolean sb;

        /***
         * constructor to set up colors, fonts and bitmaps
         * @param c context
         */
        public TitleView(Context c) {
            super(c);
            theme = SettingsActivity.getThemePreference(getContext());
            orange = new Paint();
            orange.setColor(android.graphics.Color.rgb(255, 189, 89));
            orangetxt = new Paint();
            orangetxt.setColor(android.graphics.Color.rgb(255, 145, 77));
            black = new Paint();
            black.setColor(android.graphics.Color.rgb(0, 0, 0));
            Typeface plain = Typeface.createFromAsset(getAssets(), "fonts/ptsans_regular.ttf");
            Typeface bold = Typeface.createFromAsset(getAssets(), "fonts/ptsans_bold.ttf");
            orangetxt.setTypeface(bold);
            black.setTypeface(bold);
            qm = BitmapFactory.decodeResource(getResources(), R.drawable.qm);
            cog = BitmapFactory.decodeResource(getResources(), R.drawable.cog);
            board = BitmapFactory.decodeResource(getResources(), R.drawable.board);
        }

        /***
         * onDraw method to draw title screen
         * @param c canvas
         */
        @Override
        public void onDraw(Canvas c){
            float w = getWidth();
            float h = getHeight();
            if (!init) {
                init = true;
                qm = Bitmap.createScaledBitmap(qm, (int)w/7, (int)w/7, true);
                cog = Bitmap.createScaledBitmap(cog, (int)w/8, (int)w/8, true);
                Matrix m = new Matrix();
                m.postScale(w/board.getWidth(), w/board.getWidth());
                m.postRotate(-166);
                board = Bitmap.createBitmap(board, 0, 0, board.getWidth(), board.getHeight(), m, true);
            }
            c.drawRect(0, 0, w, h, theme.getTitleBG());
            c.drawRoundRect(w/15, h/20, w/15+w/5,h/20+w/5, 35, 35, abb ? orangetxt : orange);
            c.drawRoundRect(w-w/15-w/5, h/20, w-w/15,h/20+w/5, 35, 35, sb ? orangetxt : orange);
            c.drawRect(0, h/3+h/18, w, h/3+h/18+h/15, p2b ? orangetxt : orange);
            c.drawRect(0, h/3+h/18+h/15+h/60, w, h/3+h/18+2*h/15+h/60, orange);
            orangetxt.setTextSize(w/6.5f);
            c.drawText("Outwit", w/2-w/35, h/3, orangetxt);
            black.setTextSize(w/13);
            c.drawText("Play - 2 Player", w/10, h/3+h/18+h/15-w/26, black);
            c.drawText("Play - 1 Player", w/10, h/3+h/18+2*h/15+h/60-w/26, black);
            c.drawBitmap(qm, w/15+w/35, h/20+w/35, null);
            c.drawBitmap(cog, w-w/15-w/5+w/26, h/20+w/26, null);
            c.drawBitmap(board, -2*w/13, h/2+2*h/11, null);
        }

        /***
         * onTouchEvent method to handle when button is pressed
         * update buttons accordingly
         * @param m The motion event.
         * @return boolean
         */
        @Override
        public boolean onTouchEvent(MotionEvent m) {
            if (m.getAction() == MotionEvent.ACTION_UP) {
                p2b = false;
                abb = false;
                sb = false;
                invalidate();
            }

            if (m.getAction() == MotionEvent.ACTION_DOWN) {
                float w = getWidth();
                float h = getHeight();
                float x = m.getX();
                float y = m.getY();
                if (y >= h/3+h/18 && y <= h/3+h/18+h/15) {
                    p2b = true;
                    invalidate();
                    Intent play2p = new Intent(this.getContext(), MainActivity.class);
                    startActivity(play2p);
                    finish();
                } else if (x >= w/15 && y >= h/20 && x <= w/15+w/5 && y <= h/20+w/5) {
                    abb = true;
                    invalidate();
                    AlertDialog.Builder ab = new AlertDialog.Builder(getContext())
                            .setTitle("About")
                            .setMessage("Be the first to slide all nine of your chips into your own corner of the board.\n" +
                                    "\n" +
                                    "The chips are setup in the middle of the board at start. " +
                                    "Each player plays alternately one chip in one direction only. " +
                                    "A regular chip may move horizontally or vertically only. " +
                                    "A power chip may move also diagonally. A regular chip must slide as far as it can go. " +
                                    "Stopped only by reaching the edge of the board, another chip or the opponent's corner. " +
                                    "A power chip can stop whenever it wants, but must stop for the same reasons as a regular chip.\n" +
                                    "\n" +
                                    "Once inside its own corner, no chip can move back into the playing area. " +
                                    "A regular chip must move horizontally or vertically as far as it can within the corner, " +
                                    "a power chip may move any number of squares, within the corner.")
                            .setNeutralButton("Close", (d,i) -> {d.dismiss(); abb = false; invalidate();});
                    ab.show();
                } else if (x >= w-w/15-w/5 && y >= h/20 && x <= w-w/15 && y <= h/20+w/5) {
                    sb = true;
                    invalidate();
                    Intent settings = new Intent(this.getContext(), SettingsActivity.class);
                    startActivity(settings);
                }
            }
            return true;
        }
    }

    /***
     * onResume method to reset buttons
     */
    @Override
    protected void onResume() {
        super.onResume();
        ((TitleView)v).sb = false;
        ((TitleView)v).abb = false;
        ((TitleView)v).p2b = false;
        v.invalidate();
    }
}
