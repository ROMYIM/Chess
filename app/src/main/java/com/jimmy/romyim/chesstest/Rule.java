package com.jimmy.romyim.chesstest;

/**
 * Created by Administrator on 2017/4/28.
 */
public class Rule {
    private boolean marshal(int chess, int[] start, int[] end, int[][] chessBoard) {
        if (chess == 1 ) {
            if (chessBoard[end[0]][end[1]] == 8 && this.tank(start, end, chessBoard) == 0)
                return true;
            else if (end[1] < 3 || end[1] > 5 || end[0] < 0 || end[0] > 2)
                return false;
        }
        else if (chess == 8) {
            if (chessBoard[end[0]][end[1]] == 1 && this.tank(start, end, chessBoard) == 0)
                return true;
            else if (end[1] < 3 || end[1] > 5 || end[0] < 7 || end[0] > 9)
                return false;
        }
        if (Math.abs(start[0] - end[0]) + Math.abs(start[1] - end[1]) == 1)
            return true;
        else
            return false;
    }

    private int tank(int[] start, int[] end, int[][] chessBoard) {
        int count = -1;
        if (Math.abs(end[0] - start[0]) == 0) {
            count++;
            int from = (end[1] > start[1] ? start[1] : end[1]);
            int to = (end[1] < start[1] ? start[1] : end[1]);
            while (++from <= --to) {
                if (chessBoard[end[0]][from] != 0 && from != to)
                    count++;
                if (chessBoard[end[0]][to] != 0)
                    count++;
                if (count >= 2)
                    break;
            }
            return count;
        }else if (Math.abs(end[1] - start[1]) == 0) {
            count++;
            int from = (end[0] > start[0] ? start[0] : end[0]);
            int to = (end[0] < start[0] ? start[0] : end[0]);
            while (++from <= --to) {
                if (chessBoard[from][end[1]] != 0 && from != to)
                    count++;
                if (chessBoard[to][end[1]] != 0)
                    count++;
                if (count >= 2)
                    break;
            }
            return count;
        }
        return count;
    }

    private boolean horse(int[] start, int[] end, int[][] chessBoard) {
        if (Math.abs(start[0] - end[0]) == 2 && Math.abs(start[1] - end[1]) == 1) {
            if (start[0] > end[0] && chessBoard[start[0] - 1][start[1]] == 0)
                return true;
            else if (start[0] < end[0] && chessBoard[start[0] + 1][start[1]] == 0)
                return true;
        }else if (Math.abs(start[0] - end[0]) == 1 && Math.abs(start[1] - end[1]) == 2) {
            if (start[1] > end[1] && chessBoard[start[0]][start[1] - 1] == 0)
                return true;
            else if (start[1] < end[1] && chessBoard[start[0]][start[1] + 1] == 0)
                return true;
        }
        return false;
    }

    private boolean artillery(int[] start, int[] end, int[][] chessBoard) {
        if (chessBoard[end[0]][end[1]] == 0) {
            if (this.tank(start, end, chessBoard) == 0)
                return true;
            return false;
        }
        else {
            if (this.tank(start, end, chessBoard) == 1)
                return true;
            return false;
        }
    }

    private boolean bodygraud(int chess, int[] start, int[] end) {
        if (chess == 5 && (end[1] < 3 || end[1] > 5 || end[0] < 0 || end[0] > 2))
            return false;
        else if (chess == 12 && (end[1] < 3 || end[1] > 5 || end[0] < 7 || end[0] > 9))
            return false;
        if (Math.abs(start[0] - end[0]) == 1 && Math.abs(start[1] - end[1]) == 1)
            return true;
        else
            return false;
    }

    private boolean chancellor(int chess, int[] start, int[] end, int[][] chessBoard) {
        if (chess == 6 && end[0] > 4)
            return false;
        else if (chess == 13 && end[0] < 5)
            return false;
        if (Math.abs(start[0] - end[0]) == 2 && Math.abs(start[1] - end[1]) == 2) {
            if (start[0] > end[0] && start[1] > end[1] && chessBoard[start[0] - 1][start[1] - 1] == 0)
                return true;
            else if (start[0] > end[0] && start[1] < end[1] && chessBoard[start[0] - 1][start[1] + 1] == 0)
                return true;
            else if (start[0] < end[0] && start[1] < end[1] && chessBoard[start[0] + 1][start[1] + 1] == 0)
                return true;
            else if (start[0] < end[0] && start[1] > end[1] && chessBoard[start[0] + 1][start[1] - 1] == 0)
                return true;
        }
        return false;
    }

    private boolean soldiers (int chess, int[] start, int[] end) {
        if (chess == 7 && start[0] < 5) {
            if (start[1] == end[1] && end[0] - start[0] == 1)
                return true;
        }else if (chess == 7 && start[0] >= 5) {
            if ((start[1] == end[1] && end[0] - start[0] == 1) || (start[0] == end[0] && Math.abs(end[1] - start[1]) == 1))
                return true;
        }else if (chess == 14 && start[0] > 4) {
            if (start[1] == end[1] && end[0] - start[0] == -1)
                return true;
        }else if (chess == 14 && start[0] <= 4) {
            if ((start[1] == end[1] && end[0] - start[0] == -1) || (start[0] == end[0] && Math.abs(end[1] - start[1]) == 1))
                return true;
        }
        return false;
    }

    public boolean canMove(int chess, int[] start, int[] end, int[][] chessBoard) {
        boolean result = false;
        if (end[0] < 0 || end[0] > 9 || end[1] > 8 || end[1] < 0)
            return result;
        switch (chess % 7) {
            case 1:
                return this.marshal(chess, start, end, chessBoard);
            case 2:
                if (this.tank(start, end, chessBoard) == 0)
                    result = true;
                break;
            case 3:
                return this.horse(start, end, chessBoard);
            case 4:
                return this.artillery(start, end, chessBoard);
            case 5:
                return this.bodygraud(chess, start, end);
            case 6:
                return this.chancellor(chess, start, end, chessBoard);
            case 0:
                return this.soldiers(chess, start, end);
        }
        return result;
    }
}
