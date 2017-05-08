package edu.scu.gobang.util;

import android.graphics.Point;

import java.util.List;

import edu.scu.gobang.GobangPanel;

/**
 * Created by yuhaowang on 5/8/17.
 */

public class GobangUtil {
    /**
     * horizontal checking
     *
     * @param x
     * @param y
     * @param points
     * @return
     */
    public static boolean checkHorizontal(int x, int y, List<Point> points) {
        int MAX_COUNT_IN_LINE = GobangPanel.getMaxCountInLine();

        int count = 1;
        for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
            if (points.contains(new Point(x - i, y))) {
                count++;
            } else {
                break;
            }

        }
        if (count == MAX_COUNT_IN_LINE) {
            return true;
        }

        for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
            if (points.contains(new Point(x + i, y))) {
                count++;
            } else {
                break;
            }
        }
        if (count == MAX_COUNT_IN_LINE) {
            return true;
        }
        return false;
    }


    /**
     * vertical checking
     *
     * @param x
     * @param y
     * @param points
     * @return
     */
    public static boolean checkVertical(int x, int y, List<Point> points) {
        int MAX_COUNT_IN_LINE = GobangPanel.getMaxCountInLine();

        int count = 1;
        for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
            if (points.contains(new Point(x, y - i))) {
                count++;
            } else {
                break;
            }

        }
        if (count == MAX_COUNT_IN_LINE) {
            return true;
        }
        for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
            if (points.contains(new Point(x, y + i))) {
                count++;
            } else {
                break;
            }
        }
        if (count == MAX_COUNT_IN_LINE) {
            return true;
        }
        return false;
    }

    /**
     * left diagonal checking
     *
     * @param x
     * @param y
     * @param points
     * @return
     */
    public static boolean checkLeftDiagonal(int x, int y, List<Point> points) {
        int MAX_COUNT_IN_LINE = GobangPanel.getMaxCountInLine();
        int count = 1;
        for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
            if (points.contains(new Point(x - i, y + i))) {
                count++;
            } else {
                break;
            }

        }
        if (count == MAX_COUNT_IN_LINE)
            return true;

        for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
            if (points.contains(new Point(x + i, y - i))) {
                count++;
            } else {
                break;
            }
        }
        if (count == MAX_COUNT_IN_LINE) {
            return true;
        }
        return false;
    }

    /**
     * right diagonal checking
     *
     * @param x
     * @param y
     * @param points
     * @return
     */
    public static boolean checkRightDiagonal(int x, int y, List<Point> points) {
        int MAX_COUNT_IN_LINE = GobangPanel.getMaxCountInLine();

        int count = 1;
        for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
            if (points.contains(new Point(x - i, y - i))) {
                count++;
            } else {
                break;
            }

        }
        if (count == MAX_COUNT_IN_LINE) {
            return true;
        }

        for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
            if (points.contains(new Point(x + i, y + i))) {
                count++;
            } else {
                break;
            }
        }
        if (count == MAX_COUNT_IN_LINE) {
            return true;
        }
        return false;
    }
}

