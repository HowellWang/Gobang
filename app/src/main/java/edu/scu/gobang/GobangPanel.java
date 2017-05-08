package edu.scu.gobang;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.scu.gobang.util.GobangUtil;

/**
 * Created by yuhaowang on 5/7/17.
 */

public class GobangPanel extends View {

    private int myPanelWidth;
    private double myLineHeight;
    private int MAX_LINE = 10;
    private static int MAX_COUNT_IN_LINE = 5;

    private Paint myPaint = new Paint();

    private Bitmap myWhitePiece;
    private Bitmap myBlackPiece;

    private double radioPieceLineHeight = 3.0 / 4;

    //This turn is withe
    private boolean myIsWhite = true;
    private ArrayList<Point> myWhiteArray = new ArrayList<>();
    private ArrayList<Point> myBlackArray = new ArrayList<>();

    private boolean myIsGameOver;
    private boolean myIsWhiteWinner;

    public GobangPanel(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //setBackgroundColor(0x44ff0000);
        init();
    }


    private void init() {
        myPaint.setColor(0x88000000);
        myPaint.setAntiAlias(true);
        myPaint.setDither(true);
        myPaint.setStyle(Paint.Style.STROKE);

        myWhitePiece = BitmapFactory.decodeResource(getResources(), R.drawable.stone_w2);
        myBlackPiece = BitmapFactory.decodeResource(getResources(), R.drawable.stone_b1);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width = Math.min(widthSize, heightSize);

        if (widthMode == MeasureSpec.UNSPECIFIED) {
            width = heightSize;
        } else if (heightMode == MeasureSpec.UNSPECIFIED) {
            width = widthSize;
        }

        setMeasuredDimension(width, width);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        myPanelWidth = w;
        myLineHeight = myPanelWidth * 1.0 / MAX_LINE;


        int picecWidth = (int) (myLineHeight * radioPieceLineHeight);
        myWhitePiece = Bitmap.createScaledBitmap(myWhitePiece, picecWidth, picecWidth, false);
        myBlackPiece = Bitmap.createScaledBitmap(myBlackPiece, picecWidth, picecWidth, false);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (myIsGameOver) {
            return false;
        }

        int action = event.getAction();
        if (action == MotionEvent.ACTION_UP) {

            int x = (int) event.getX();
            int y = (int) event.getY();
            Point p = getValidPoint(x, y);

            if (myWhiteArray.contains(p) || myBlackArray.contains(p)) {
                return false;
            }

            if (myIsWhite) {
                myWhiteArray.add(p);
            } else {
                myBlackArray.add(p);
            }
            invalidate();
            myIsWhite = !myIsWhite;
        }

        return true;
    }

    private Point getValidPoint(int x, int y) {
        return new Point((int) (x / myLineHeight), (int) (y / myLineHeight));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBoard(canvas);
        drawPieces(canvas);
        checkGameOver();
    }

    private void checkGameOver() {
        boolean whiteWin = checkFiveInLine(myWhiteArray);
        boolean blackWin = checkFiveInLine(myBlackArray);
        if (whiteWin || blackWin) {
            myIsGameOver = true;
            myIsWhiteWinner = whiteWin;

            String text = myIsWhiteWinner ? "White win" : "Black win";

            Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkFiveInLine(List<Point> points) {
        for (Point point : points) {
            int x = point.x;
            int y = point.y;

            boolean win = GobangUtil.checkHorizontal(x, y, points);
            if (win) {
                return true;
            }
            win = GobangUtil.checkVertical(x, y, points);
            if (win) {
                return true;
            }
            win = GobangUtil.checkLeftDiagonal(x, y, points);
            if (win) {
                return true;
            }
            win = GobangUtil.checkRightDiagonal(x, y, points);
            if (win) {
                return true;
            }
        }

        return false;
    }


    private void drawPieces(Canvas canvas) {
        for (int i = 0, n = myWhiteArray.size(); i < n; i++) {
            Point whitePoint = myWhiteArray.get(i);
            canvas.drawBitmap(myWhitePiece,
                    (float) ((whitePoint.x + (1 - radioPieceLineHeight) / 2) * myLineHeight),
                    (float) ((whitePoint.y + ((1 - radioPieceLineHeight) / 2)) * myLineHeight), null);

        }

        for (int i = 0, size = myBlackArray.size(); i < size; i++) {
            Point blackPoint = myBlackArray.get(i);
            canvas.drawBitmap(myBlackPiece,
                    (float) ((blackPoint.x + (1 - radioPieceLineHeight) / 2) * myLineHeight),
                    (float) ((blackPoint.y + ((1 - radioPieceLineHeight) / 2)) * myLineHeight), null);

        }
    }


    private void drawBoard(Canvas canvas) {
        int w = myPanelWidth;
        double lineHeight = myLineHeight;

        for (int i = 0; i < MAX_LINE; i++) {
            int startX = (int) (lineHeight / 2);
            int endX = (int) (w - lineHeight / 2);

            int y = (int) ((0.5 + i) * lineHeight);
            canvas.drawLine(startX, y, endX, y, myPaint);
            canvas.drawLine(y, startX, y, endX, myPaint);
        }
    }

    public static int getMaxCountInLine() {
        return MAX_COUNT_IN_LINE;
    }

    public void start() {
        myWhiteArray.clear();
        myBlackArray.clear();
        myIsGameOver = false;
        myIsWhiteWinner = false;
        invalidate();
    }

    private static final String INSTANCE = "instance";
    private static final String INSTANCE_GAME_OVER = "instanceGameOvew";
    private static final String INSTANCE_WHITE_ARRAY = "instance_WA";
    private static final String INSTANCE_BLACK_ARRAY = "instance_BA";

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE, super.onSaveInstanceState());
        bundle.putBoolean(INSTANCE_GAME_OVER, myIsGameOver);
        bundle.putParcelableArrayList(INSTANCE_WHITE_ARRAY, myWhiteArray);
        bundle.putParcelableArrayList(INSTANCE_BLACK_ARRAY, myBlackArray);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            myIsGameOver = bundle.getBoolean(INSTANCE_GAME_OVER);
            myWhiteArray = bundle.getParcelableArrayList(INSTANCE_WHITE_ARRAY);
            myBlackArray = bundle.getParcelableArrayList(INSTANCE_BLACK_ARRAY);
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE));
            return;
        }
        super.onRestoreInstanceState(state);
    }
}
