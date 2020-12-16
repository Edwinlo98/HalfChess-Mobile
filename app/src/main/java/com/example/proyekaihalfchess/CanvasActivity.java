package com.example.proyekaihalfchess;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.text.TextPaint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class CanvasActivity extends View {
    HalfChess halfchess = new HalfChess();
    Paint paint = new Paint();
    Drawable[] drawablePiece = new Drawable[10];
    Rect[][] rectangle = new Rect[halfchess.getHeightBoard()][halfchess.getWidthBoard()];
    Integer DrawMode = 0, Touched_Position_X = -1, Touched_Position_Y = -1, Check_From_X = -1, Check_From_Y = -1, Check_To_X = -1, Check_To_Y = -1;
    boolean checkByKnight = false, checkBlockade = false;
    Piece piece = null;
    private Timer myTimer;
    private boolean timerState;

    public CanvasActivity(Context context) {
        super(context);
//        setContentView(R.layout.board_game);
        drawablePiece[0] = context.getResources().getDrawable(R.drawable.black_king);
        drawablePiece[1] = context.getResources().getDrawable(R.drawable.white_king);
        drawablePiece[2] = context.getResources().getDrawable(R.drawable.black_queen);
        drawablePiece[3] = context.getResources().getDrawable(R.drawable.white_queen);
        drawablePiece[4] = context.getResources().getDrawable(R.drawable.black_knight);
        drawablePiece[5] = context.getResources().getDrawable(R.drawable.white_knight);
        drawablePiece[6] = context.getResources().getDrawable(R.drawable.black_bishop);
        drawablePiece[7] = context.getResources().getDrawable(R.drawable.white_bishop);
        drawablePiece[8] = context.getResources().getDrawable(R.drawable.black_pawn);
        drawablePiece[9] = context.getResources().getDrawable(R.drawable.white_pawn);
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        int X = (int) event.getX();
        int Y = (int) event.getY();
        boolean Touch_Out_Board = true;
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            for (Integer Position_Y = 0; Position_Y < halfchess.getWidthBoard(); Position_Y++) {
                for (Integer Position_X = 0; Position_X < halfchess.getHeightBoard(); Position_X++) {
                    if (rectangle[Position_X][Position_Y].contains(X, Y)) {
                        Touch_Out_Board = !Touch_Out_Board;
                        if (DrawMode == 0 && halfchess.getCurrentBoard()[Position_X][Position_Y] != null && halfchess.getCurrentBoard()[Position_X][Position_Y].getTeamColor() == halfchess.getTurnColor() && halfchess.CountLegalMove(halfchess, Position_X, Position_Y) > 0 && (Touched_Position_X != Position_X || Touched_Position_Y != Position_Y)) {
//                            Toast.makeText(this.getContext(),"ke (0) Draw Mode = " + DrawMode, Toast.LENGTH_SHORT).show();
//                            Toast.makeText(this.getContext(),"Position_X = " + Position_X, Toast.LENGTH_SHORT).show();
//                            Toast.makeText(this.getContext(),"Position_Y = " + Position_Y, Toast.LENGTH_SHORT).show();
                            SetActiveLegalPiece(Position_X, Position_Y);
                            DrawMode = 1;
                        }
                        else if (DrawMode == 1) {
                            if (halfchess.getCurrentBoard()[Position_X][Position_Y] != null && halfchess.getCurrentBoard()[Position_X][Position_Y].getTeamColor() == halfchess.getCurrentBoard()[Touched_Position_X][Touched_Position_Y].getTeamColor() && halfchess.CountLegalMove(halfchess, Position_X, Position_Y) > 0 && (Touched_Position_X != Position_X || Touched_Position_Y != Position_Y)) {
                                SetActiveLegalPiece(Position_X, Position_Y);
                            } else {
                                if (isKingSave(Position_X, Position_Y)) {
                                    if (piece instanceof King) {
                                        //Toast.makeText(this.getContext(), String.valueOf(((King)piece).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), piece, Touched_Position_X, Touched_Position_Y, Position_X, Position_Y)), Toast.LENGTH_SHORT).show();
                                        if (((King) piece).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), piece, Touched_Position_X, Touched_Position_Y, Position_X, Position_Y)) {
                                            //AnimationMove(Touched_Position_X, Touched_Position_Y, Position_X, Position_Y);
                                            Piece[][] halfChessBoard = halfchess.getCurrentBoard();
                                            halfChessBoard[Position_X][Position_Y] = piece;
                                            halfChessBoard[Touched_Position_X][Touched_Position_Y] = null;
                                            halfchess.setCurrentBoard(halfChessBoard);
                                            DisableEnPassant(piece.getTeamColor());
                                            halfchess.setTurnColor(halfchess.getTurnColor() == Color.WHITE ? Color.BLACK : Color.WHITE);
                                        } else Toast.makeText(this.getContext(), "Illegal " + piece.getClass().getSimpleName() + " Move", Toast.LENGTH_SHORT).show();
                                    } else if (piece instanceof Queen) {
                                        //Toast.makeText(this.getContext(), String.valueOf(((Queen)piece).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), piece, Touched_Position_X, Touched_Position_Y, Position_X, Position_Y)), Toast.LENGTH_SHORT).show();
                                        if (((Queen) piece).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), piece, Touched_Position_X, Touched_Position_Y, Position_X, Position_Y)) {
                                            //AnimationMove(Touched_Position_X, Touched_Position_Y, Position_X, Position_Y);
                                            Piece[][] halfChessBoard = halfchess.getCurrentBoard();
                                            halfChessBoard[Position_X][Position_Y] = piece;
                                            halfChessBoard[Touched_Position_X][Touched_Position_Y] = null;
                                            halfchess.setCurrentBoard(halfChessBoard);
                                            DisableEnPassant(piece.getTeamColor());
                                            halfchess.setTurnColor(halfchess.getTurnColor() == Color.WHITE ? Color.BLACK : Color.WHITE);
                                        } else Toast.makeText(this.getContext(), "Illegal " + piece.getClass().getSimpleName() + " Move", Toast.LENGTH_SHORT).show();
                                    } else if (piece instanceof Knight) {
                                        //Toast.makeText(this.getContext(), String.valueOf(((Knight)piece).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), piece, Touched_Position_X, Touched_Position_Y, Position_X, Position_Y)), Toast.LENGTH_SHORT).show();
                                        if (((Knight) piece).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), piece, Touched_Position_X, Touched_Position_Y, Position_X, Position_Y)) {
                                            //AnimationMove(Touched_Position_X, Touched_Position_Y, Position_X, Position_Y);
                                            Piece[][] halfChessBoard = halfchess.getCurrentBoard();
                                            halfChessBoard[Position_X][Position_Y] = piece;
                                            halfChessBoard[Touched_Position_X][Touched_Position_Y] = null;
                                            halfchess.setCurrentBoard(halfChessBoard);
                                            DisableEnPassant(piece.getTeamColor());
                                            halfchess.setTurnColor(halfchess.getTurnColor() == Color.WHITE ? Color.BLACK : Color.WHITE);
                                        } else Toast.makeText(this.getContext(), "Illegal " + piece.getClass().getSimpleName() + " Move", Toast.LENGTH_SHORT).show();
                                    } else if (piece instanceof Bishop) {
                                        //Toast.makeText(this.getContext(), String.valueOf(((Bishop)piece).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), piece, Touched_Position_X, Touched_Position_Y, Position_X, Position_Y)), Toast.LENGTH_SHORT).show();
                                        if (((Bishop) piece).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), piece, Touched_Position_X, Touched_Position_Y, Position_X, Position_Y)) {
                                            //AnimationMove(Touched_Position_X, Touched_Position_Y, Position_X, Position_Y);
                                            Piece[][] halfChessBoard = halfchess.getCurrentBoard();
                                            halfChessBoard[Position_X][Position_Y] = piece;
                                            halfChessBoard[Touched_Position_X][Touched_Position_Y] = null;
                                            halfchess.setCurrentBoard(halfChessBoard);
                                            DisableEnPassant(piece.getTeamColor());
                                            halfchess.setTurnColor(halfchess.getTurnColor() == Color.WHITE ? Color.BLACK : Color.WHITE);
                                        } else Toast.makeText(this.getContext(), "Illegal " + piece.getClass().getSimpleName() + " Move", Toast.LENGTH_SHORT).show();
                                    } else if (piece instanceof Pawn) {
                                        //Toast.makeText(this.getContext(), String.valueOf(((Pawn)piece).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), piece, Touched_Position_X, Touched_Position_Y, Position_X, Position_Y)), Toast.LENGTH_SHORT).show();
                                        if (((Pawn) piece).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), piece, Touched_Position_X, Touched_Position_Y, Position_X, Position_Y)) {
                                            if (Math.abs(Position_X - Touched_Position_X) > 1) ((Pawn) piece).setEnPassant(true);
                                            else ((Pawn) piece).setEnPassant(false);
                                            if (Math.abs(Position_X - Touched_Position_X) == 1 && Math.abs(Position_Y - Touched_Position_Y) == 1 && halfchess.getCurrentBoard()[Position_X][Position_Y] == null && halfchess.getCurrentBoard()[Touched_Position_X][Position_Y] instanceof Pawn)
                                                halfchess.getCurrentBoard()[Touched_Position_X][Position_Y] = null;
                                            //AnimationMove(Touched_Position_X, Touched_Position_Y, Position_X, Position_Y);
                                            Piece[][] halfChessBoard = halfchess.getCurrentBoard();
                                            halfChessBoard[Position_X][Position_Y] = piece;
                                            halfChessBoard[Touched_Position_X][Touched_Position_Y] = null;
                                            halfchess.setCurrentBoard(halfChessBoard);
                                            DisableEnPassant(piece.getTeamColor());
                                            halfchess.setTurnColor(halfchess.getTurnColor() == Color.WHITE ? Color.BLACK : Color.WHITE);
                                        } else Toast.makeText(this.getContext(), "Illegal " + piece.getClass().getSimpleName() + " Move", Toast.LENGTH_SHORT).show();
                                    }
                                } else Toast.makeText(this.getContext(), "King is in Checked", Toast.LENGTH_SHORT).show();
                                ClearLegalSquare();
                            }
                        } else ClearLegalSquare();
                        this.invalidate();
                    }
                }
            }
            if (Touch_Out_Board) {
                ClearLegalSquare();
                this.invalidate();
            }
        }
        //invalidate();
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                point = new PointF(x, y);
//                invalidate();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                point.set(x, y);
//                invalidate();
//                break;
//            case MotionEvent.ACTION_UP:
//            case MotionEvent.ACTION_CANCEL:
//                point = null;
//                invalidate();
//                break;
//        }
        return true;
    }

    private boolean isKingSave(Integer To_X, Integer To_Y) {
//        if (halfchess.getCurrentBoard()[Check_From_X][Check_From_Y] instanceof Queen && ((Queen)halfchess.getCurrentBoard()[Check_From_X][Check_From_Y]).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[Check_From_X][Check_From_Y], Check_From_X, Check_From_Y, Check_To_X, Check_To_Y)
//                || halfchess.getCurrentBoard()[Check_From_X][Check_From_Y] instanceof Knight && ((Knight)halfchess.getCurrentBoard()[Check_From_X][Check_From_Y]).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[Check_From_X][Check_From_Y], Check_From_X, Check_From_Y, Check_To_X, Check_To_Y)
//                || halfchess.getCurrentBoard()[Check_From_X][Check_From_Y] instanceof Bishop && ((Bishop)halfchess.getCurrentBoard()[Check_From_X][Check_From_Y]).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[Check_From_X][Check_From_Y], Check_From_X, Check_From_Y, Check_To_X, Check_To_Y)
//                || halfchess.getCurrentBoard()[Check_From_X][Check_From_Y] instanceof Pawn && ((Pawn)halfchess.getCurrentBoard()[Check_From_X][Check_From_Y]).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[Check_From_X][Check_From_Y], Check_From_X, Check_From_Y, Check_To_X, Check_To_Y)) {
//
//        }
        if (halfchess.isCheck()) {
            Piece[][] nextBoard = halfchess.getCurrentBoard().clone();
            nextBoard[To_X][To_Y] = piece;
            nextBoard[Touched_Position_X][Touched_Position_Y] = null;
            for (Integer Position_Y = 0; Position_Y < halfchess.getWidthBoard(); Position_Y++) {
                for (Integer Position_X = 0; Position_X < halfchess.getHeightBoard(); Position_X++) {
                    if (nextBoard[Position_X][Position_Y] != null && nextBoard[Position_X][Position_Y].getTeamColor() == (halfchess.getTurnColor() == Color.WHITE ? Color.BLACK : Color.WHITE) && !(nextBoard[Position_X][Position_Y] instanceof King)) {
                        Toast.makeText(this.getContext(),"Position_X = " + Position_X + ", Position_Y = " + Position_Y, Toast.LENGTH_SHORT).show();
                        for (Integer SPosition_Y = 0; SPosition_Y < halfchess.getWidthBoard(); SPosition_Y++) {
                            for (Integer SPosition_X = 0; SPosition_X < halfchess.getHeightBoard(); SPosition_X++) {
                                if ((nextBoard[Position_X][Position_Y] instanceof Queen && ((Queen) nextBoard[Position_X][Position_Y]).Rule((halfchess.getTurnColor() == Color.WHITE ? Color.BLACK : Color.WHITE), nextBoard, nextBoard[Position_X][Position_Y], Position_X, Position_Y, SPosition_X, SPosition_Y)
                                        || nextBoard[Position_X][Position_Y] instanceof Knight && ((Knight) nextBoard[Position_X][Position_Y]).Rule((halfchess.getTurnColor() == Color.WHITE ? Color.BLACK : Color.WHITE), nextBoard, nextBoard[Position_X][Position_Y], Position_X, Position_Y, SPosition_X, SPosition_Y)
                                        || nextBoard[Position_X][Position_Y] instanceof Bishop && ((Bishop) nextBoard[Position_X][Position_Y]).Rule((halfchess.getTurnColor() == Color.WHITE ? Color.BLACK : Color.WHITE), nextBoard, nextBoard[Position_X][Position_Y], Position_X, Position_Y, SPosition_X, SPosition_Y)
                                        || nextBoard[Position_X][Position_Y] instanceof Pawn && ((Pawn) nextBoard[Position_X][Position_Y]).Rule((halfchess.getTurnColor() == Color.WHITE ? Color.BLACK : Color.WHITE), nextBoard, nextBoard[Position_X][Position_Y], Position_X, Position_Y, SPosition_X, SPosition_Y))
                                        && nextBoard[SPosition_X][SPosition_Y] != null && nextBoard[SPosition_X][SPosition_Y].getTeamColor() == halfchess.getTurnColor() && nextBoard[SPosition_X][SPosition_Y] instanceof King) {
//                    Check_From_X = Position_X;
//                    Check_From_Y = Position_Y;
//                    Check_To_X = SPosition_X;
//                    Check_To_Y = SPosition_Y;
//                                halfchess.setCheck(true);
//                                    Toast.makeText(this.getContext(),"ke (0) Draw Mode = " + DrawMode, Toast.LENGTH_SHORT).show();
//                                    Toast.makeText(this.getContext(),"Position_X = " + Position_X, Toast.LENGTH_SHORT).show();
//                                    Toast.makeText(this.getContext(),"Position_Y = " + Position_Y, Toast.LENGTH_SHORT).show();
                                    Toast.makeText(this.getContext(),"Position_X = " + Position_X + ", Position_Y = " + Position_Y, Toast.LENGTH_SHORT).show();
                                    Toast.makeText(this.getContext(),"SPosition_X = " + SPosition_X + ", SPosition_Y = " + SPosition_Y, Toast.LENGTH_SHORT).show();
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    private void SetActiveLegalPiece(Integer Position_X, Integer Position_Y) {
        Touched_Position_X = Position_X;
        Touched_Position_Y = Position_Y;
        piece = halfchess.getCurrentBoard()[Position_X][Position_Y];
    }

    private void DisableEnPassant(int turn) {
        halfchess.setCheck(false);
        for (Integer Position_Y = 0; Position_Y < halfchess.getWidthBoard(); Position_Y++) {
            for (Integer Position_X = 0; Position_X < halfchess.getHeightBoard(); Position_X++) {
                // Disable En Passant Opponent Pawn
                if (halfchess.getCurrentBoard()[Position_X][Position_Y] instanceof Pawn && halfchess.getCurrentBoard()[Position_X][Position_Y].getTeamColor() == (turn == Color.WHITE ? Color.BLACK : Color.WHITE) && ((Pawn) halfchess.getCurrentBoard()[Position_X][Position_Y]).isEnPassant()) {
                    ((Pawn)halfchess.getCurrentBoard()[Position_X][Position_Y]).setEnPassant(false);
                }
                //Reset Disabled Allow Move
                if (halfchess.getCurrentBoard()[Position_X][Position_Y] != null && halfchess.getCurrentBoard()[Position_X][Position_Y].getTeamColor() == (turn == Color.WHITE ? Color.BLACK : Color.WHITE)) {
                    halfchess.getCurrentBoard()[Position_X][Position_Y].setAllowMove(true);
                }
                // Get Piece based Turn
                if (halfchess.getCurrentBoard()[Position_X][Position_Y] != null && halfchess.getCurrentBoard()[Position_X][Position_Y].getTeamColor() == turn && !(halfchess.getCurrentBoard()[Position_X][Position_Y] instanceof King)) {
                    for (Integer SPosition_Y = 0; SPosition_Y < halfchess.getWidthBoard(); SPosition_Y++) {
                        for (Integer SPosition_X = 0; SPosition_X < halfchess.getHeightBoard(); SPosition_X++) {
                            // Disable Opponent Piece From Blockade Queen And Bishop
//                            if (halfchess.getCurrentBoard()[Position_X][Position_Y] instanceof Queen && ((Queen)halfchess.getCurrentBoard()[Position_X][Position_Y]).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[Position_X][Position_Y], Position_X, Position_Y, SPosition_X, SPosition_Y)
//                                    && halfchess.getCurrentBoard()[SPosition_X][SPosition_Y] != null && halfchess.getCurrentBoard()[SPosition_X][SPosition_Y].getTeamColor() == (turn == Color.WHITE ? Color.BLACK : Color.WHITE)) {
//                                if (halfchess.getCurrentBoard()[SPosition_X][SPosition_Y] instanceof King) {
//                                    halfchess.setCheck(true);
//                                    for (Integer EPosition_Y = 0; EPosition_Y < halfchess.getWidthBoard(); EPosition_Y++) {
//                                        for (Integer EPosition_X = 0; EPosition_X < halfchess.getHeightBoard(); EPosition_X++) {
//                                            if (halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] != null && halfchess.getCurrentBoard()[EPosition_X][EPosition_Y].getTeamColor() == (turn == Color.WHITE ? Color.BLACK : Color.WHITE) && !(halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof King)) {
//                                                halfchess.getCurrentBoard()[EPosition_X][EPosition_Y].setAllowMove(false);
//                                            }
//                                        }
//                                    }
//                                    // Search Piece To Block From Check
//                                    for (Integer EPosition_Y = 0; EPosition_Y < halfchess.getWidthBoard(); EPosition_Y++) {
//                                        for (Integer EPosition_X = 0; EPosition_X < halfchess.getHeightBoard(); EPosition_X++) {
//                                            if (halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] != null && halfchess.getCurrentBoard()[EPosition_X][EPosition_Y].getTeamColor() == (turn == Color.WHITE ? Color.BLACK : Color.WHITE) && !(halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof King)) {
//                                                Toast.makeText(this.getContext(),"Position_X = " + Position_X + ", Position_Y = " + Position_Y, Toast.LENGTH_SHORT).show();
//                                                Toast.makeText(this.getContext(),"SPosition_X = " + SPosition_X + ", SPosition_Y = " + SPosition_Y, Toast.LENGTH_SHORT).show();
//                                                Toast.makeText(this.getContext(),"EPosition_X = " + EPosition_X + ", EPosition_Y = " + EPosition_Y, Toast.LENGTH_SHORT).show();
//                                                for (Integer SEPosition_Y = 0; SEPosition_Y < halfchess.getWidthBoard(); SEPosition_Y++) {
//                                                    for (Integer SEPosition_X = 0; SEPosition_X < halfchess.getHeightBoard(); SEPosition_X++) {
//                                                        if (SPosition_X > Position_X) {
//                                                            if (SPosition_Y > Position_Y) {
//                                                                if (SEPosition_X > Position_X && SEPosition_X < SPosition_X && SEPosition_Y > Position_Y && SEPosition_Y < SPosition_Y && (halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Queen && ((Queen) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y) ||
//                                                                        halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Knight && ((Knight) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y) || halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Bishop && ((Bishop) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y) ||
//                                                                        halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Pawn && ((Pawn) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y))) {
//                                                                    halfchess.getCurrentBoard()[EPosition_X][EPosition_Y].setAllowMove(true);
//                                                                }
//                                                            } else if (SPosition_Y < Position_Y) {
//                                                                if (SEPosition_X > Position_X && SEPosition_X < SPosition_X && SEPosition_Y > SPosition_Y && SEPosition_Y < Position_Y && (halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Queen && ((Queen) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y) ||
//                                                                        halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Knight && ((Knight) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y) || halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Bishop && ((Bishop) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y) ||
//                                                                        halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Pawn && ((Pawn) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y))) {
//                                                                    halfchess.getCurrentBoard()[EPosition_X][EPosition_Y].setAllowMove(true);
//                                                                }
//                                                            } else {
//                                                                if (SEPosition_X > Position_X && SEPosition_X < SPosition_X && SEPosition_Y == SPosition_Y && (halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Queen && ((Queen) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y) ||
//                                                                        halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Knight && ((Knight) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y) || halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Bishop && ((Bishop) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y) ||
//                                                                        halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Pawn && ((Pawn) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y))) {
//                                                                    halfchess.getCurrentBoard()[EPosition_X][EPosition_Y].setAllowMove(true);
//                                                                }
//                                                            }
//                                                        }
//                                                        else if (SPosition_X < Position_X) {
//                                                            if (SPosition_Y > Position_Y) {
//                                                                if (SEPosition_X > SPosition_X && SEPosition_X < Position_X && SEPosition_Y > Position_Y && SEPosition_Y < SPosition_Y && (halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Queen && ((Queen) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y) ||
//                                                                        halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Knight && ((Knight) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y) || halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Bishop && ((Bishop) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y) ||
//                                                                        halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Pawn && ((Pawn) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y))) {
//                                                                    halfchess.getCurrentBoard()[EPosition_X][EPosition_Y].setAllowMove(true);
//                                                                }
//                                                            } else if (SPosition_Y < Position_Y) {
//                                                                if (SEPosition_X > SPosition_X && SEPosition_X < Position_X && SEPosition_Y > SPosition_Y && SEPosition_Y < Position_Y && (halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Queen && ((Queen) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y) ||
//                                                                        halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Knight && ((Knight) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y) || halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Bishop && ((Bishop) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y) ||
//                                                                        halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Pawn && ((Pawn) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y))) {
//                                                                    halfchess.getCurrentBoard()[EPosition_X][EPosition_Y].setAllowMove(true);
//                                                                }
//                                                            } else {
//                                                                if (SEPosition_X > SPosition_X && SEPosition_X < Position_X && SEPosition_Y == SPosition_Y && (halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Queen && ((Queen) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y) ||
//                                                                        halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Knight && ((Knight) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y) || halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Bishop && ((Bishop) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y) ||
//                                                                        halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Pawn && ((Pawn) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y))) {
//                                                                    halfchess.getCurrentBoard()[EPosition_X][EPosition_Y].setAllowMove(true);
//                                                                }
//                                                            }
//                                                        } else {
//                                                            if (SPosition_Y > Position_Y) {
//                                                                if (SEPosition_X == SPosition_X && SEPosition_Y > Position_Y && SEPosition_Y < SPosition_Y && (halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Queen && ((Queen) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y) ||
//                                                                        halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Knight && ((Knight) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y) || halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Bishop && ((Bishop) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y) ||
//                                                                        halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Pawn && ((Pawn) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y))) {
//                                                                    halfchess.getCurrentBoard()[EPosition_X][EPosition_Y].setAllowMove(true);
//                                                                }
//                                                            } else if (SPosition_Y < Position_Y) {
//                                                                if (SEPosition_X == SPosition_X && SEPosition_Y > SPosition_Y && SEPosition_Y < Position_Y && (halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Queen && ((Queen) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y) ||
//                                                                        halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Knight && ((Knight) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y) || halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Bishop && ((Bishop) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y) ||
//                                                                        halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Pawn && ((Pawn) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y))) {
//                                                                    halfchess.getCurrentBoard()[EPosition_X][EPosition_Y].setAllowMove(true);
//                                                                }
//                                                            } else {
//                                                                if (SEPosition_X == SPosition_X && SEPosition_Y == SPosition_Y && (halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Queen && ((Queen) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y) ||
//                                                                        halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Knight && ((Knight) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y) || halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Bishop && ((Bishop) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y) ||
//                                                                        halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Pawn && ((Pawn) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y))) {
//                                                                    halfchess.getCurrentBoard()[EPosition_X][EPosition_Y].setAllowMove(true);
//                                                                }
//                                                            }
//                                                        }
//                                                    }
//                                                }
//                                            }
//                                        }
//                                    }
//                                } else if (!(halfchess.getCurrentBoard()[SPosition_X][SPosition_Y] instanceof King) && (Math.abs(SPosition_X - Position_X) == Math.abs(SPosition_Y - Position_Y) && Math.abs(SPosition_Y - Position_Y) > 0 && Math.abs(SPosition_X - Position_X) > 0 && SPosition_X > 0 && SPosition_X < halfchess.getHeightBoard() - 1 && SPosition_Y > 0 && SPosition_Y < halfchess.getWidthBoard() - 1
//                                        || SPosition_Y - Position_Y == 0 && Math.abs(SPosition_X - Position_X) > 0 && SPosition_Y > 0 && SPosition_Y < halfchess.getWidthBoard() - 1 || Math.abs(SPosition_Y - Position_Y) > 0 && SPosition_X - Position_X == 0 && SPosition_X > 0 && SPosition_X < halfchess.getHeightBoard() - 1)) {
//                                    Integer KSPosition_X = SPosition_X > Position_X ? SPosition_X + 1 : SPosition_X < Position_X ? SPosition_X - 1 : SPosition_X;
//                                    Integer KSPosition_Y = SPosition_Y > Position_Y ? SPosition_Y + 1 : SPosition_Y < Position_Y ? SPosition_Y - 1 : SPosition_Y;
//                                    if (halfchess.getCurrentBoard()[KSPosition_X][KSPosition_Y] instanceof King && halfchess.getCurrentBoard()[KSPosition_X][KSPosition_Y].getTeamColor() == (turn == Color.WHITE ? Color.BLACK : Color.WHITE)) {
//                                        halfchess.getCurrentBoard()[SPosition_X][SPosition_Y].setAllowMove(false);
//                                    }
//                                }
//                            }

                            // Disable Opponent Piece From Blockade Queen And Bishop
                            if (halfchess.getCurrentBoard()[Position_X][Position_Y] instanceof Queen && ((Queen)halfchess.getCurrentBoard()[Position_X][Position_Y]).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[Position_X][Position_Y], Position_X, Position_Y, SPosition_X, SPosition_Y)
                                    && (halfchess.getCurrentBoard()[SPosition_X][SPosition_Y] != null && halfchess.getCurrentBoard()[SPosition_X][SPosition_Y].getTeamColor() == (turn == Color.WHITE ? Color.BLACK : Color.WHITE) && !(halfchess.getCurrentBoard()[SPosition_X][SPosition_Y] instanceof King))
                                    && (Math.abs(SPosition_X - Position_X) == Math.abs(SPosition_Y - Position_Y) && Math.abs(SPosition_Y - Position_Y) > 0 && Math.abs(SPosition_X - Position_X) > 0 && SPosition_X > 0 && SPosition_X < halfchess.getHeightBoard() - 1 && SPosition_Y > 0 && SPosition_Y < halfchess.getWidthBoard() - 1
                                    || SPosition_Y - Position_Y == 0 && Math.abs(SPosition_X - Position_X) > 0 && SPosition_Y > 0 && SPosition_Y < halfchess.getWidthBoard() - 1 || Math.abs(SPosition_Y - Position_Y) > 0 && SPosition_X - Position_X == 0 && SPosition_X > 0 && SPosition_X < halfchess.getHeightBoard() - 1)) {
                                Integer KSPosition_X = SPosition_X > Position_X ? SPosition_X + 1 : SPosition_X < Position_X ? SPosition_X - 1 : SPosition_X;
                                Integer KSPosition_Y = SPosition_Y > Position_Y ? SPosition_Y + 1 : SPosition_Y < Position_Y ? SPosition_Y - 1 : SPosition_Y;
                                if (halfchess.getCurrentBoard()[KSPosition_X][KSPosition_Y] instanceof King && halfchess.getCurrentBoard()[KSPosition_X][KSPosition_Y].getTeamColor() == (turn == Color.WHITE ? Color.BLACK : Color.WHITE)) {
                                    halfchess.getCurrentBoard()[SPosition_X][SPosition_Y].setAllowMove(false);
                                }
                            }
                            else if (halfchess.getCurrentBoard()[Position_X][Position_Y] instanceof Bishop && ((Bishop)halfchess.getCurrentBoard()[Position_X][Position_Y]).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[Position_X][Position_Y], Position_X, Position_Y, SPosition_X, SPosition_Y)
                                    && halfchess.getCurrentBoard()[SPosition_X][SPosition_Y] != null && halfchess.getCurrentBoard()[SPosition_X][SPosition_Y].getTeamColor() == (turn == Color.WHITE ? Color.BLACK : Color.WHITE) && !(halfchess.getCurrentBoard()[SPosition_X][SPosition_Y] instanceof King)
                                    && Math.abs(SPosition_X - Position_X) == Math.abs(SPosition_Y - Position_Y) && Math.abs(SPosition_Y - Position_Y) > 0 && Math.abs(SPosition_X - Position_X) > 0 && SPosition_X > 0 && SPosition_X < halfchess.getHeightBoard() - 1 && SPosition_Y > 0 && SPosition_Y < halfchess.getWidthBoard() - 1) {
                                Integer KSPosition_X = SPosition_X > Position_X ? SPosition_X + 1 : SPosition_X - 1;
                                Integer KSPosition_Y = SPosition_Y > Position_Y ? SPosition_Y + 1 : SPosition_Y - 1;
                                if (halfchess.getCurrentBoard()[KSPosition_X][KSPosition_Y] instanceof King && halfchess.getCurrentBoard()[KSPosition_X][KSPosition_Y].getTeamColor() == (turn == Color.WHITE ? Color.BLACK : Color.WHITE)) {
                                    halfchess.getCurrentBoard()[SPosition_X][SPosition_Y].setAllowMove(false);
                                }
                            }

                            // Check King
                            if (!(halfchess.getCurrentBoard()[Position_X][Position_Y] instanceof King) && (halfchess.getCurrentBoard()[Position_X][Position_Y] instanceof Queen && ((Queen)halfchess.getCurrentBoard()[Position_X][Position_Y]).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[Position_X][Position_Y], Position_X, Position_Y, SPosition_X, SPosition_Y)
                                    || halfchess.getCurrentBoard()[Position_X][Position_Y] instanceof Knight && ((Knight)halfchess.getCurrentBoard()[Position_X][Position_Y]).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[Position_X][Position_Y], Position_X, Position_Y, SPosition_X, SPosition_Y)
                                    || halfchess.getCurrentBoard()[Position_X][Position_Y] instanceof Bishop && ((Bishop)halfchess.getCurrentBoard()[Position_X][Position_Y]).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[Position_X][Position_Y], Position_X, Position_Y, SPosition_X, SPosition_Y)
                                    || halfchess.getCurrentBoard()[Position_X][Position_Y] instanceof Pawn && ((Pawn)halfchess.getCurrentBoard()[Position_X][Position_Y]).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[Position_X][Position_Y], Position_X, Position_Y, SPosition_X, SPosition_Y))
                                    && halfchess.getCurrentBoard()[SPosition_X][SPosition_Y] != null && halfchess.getCurrentBoard()[SPosition_X][SPosition_Y].getTeamColor() == (turn == Color.WHITE ? Color.BLACK : Color.WHITE) && halfchess.getCurrentBoard()[SPosition_X][SPosition_Y] instanceof King) {
                                Check_From_X = Position_X;
                                Check_From_Y = Position_Y;
                                Check_To_X = SPosition_X;
                                Check_To_Y = SPosition_Y;
                                halfchess.setCheck(true);
//                                for (Integer EPosition_Y = 0; EPosition_Y < halfchess.getWidthBoard(); EPosition_Y++) {
//                                    for (Integer EPosition_X = 0; EPosition_X < halfchess.getHeightBoard(); EPosition_X++) {
//                                        if (halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] != null && halfchess.getCurrentBoard()[EPosition_X][EPosition_Y].getTeamColor() == (turn == Color.WHITE ? Color.BLACK : Color.WHITE) && !(halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof King)) {
//                                            halfchess.getCurrentBoard()[EPosition_X][EPosition_Y].setAllowMove(false);
//                                        }
//                                    }
//                                }
                                for (Integer EPosition_Y = 0; EPosition_Y < halfchess.getWidthBoard(); EPosition_Y++) {
                                    for (Integer EPosition_X = 0; EPosition_X < halfchess.getHeightBoard(); EPosition_X++) {
                                        if (halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] != null && halfchess.getCurrentBoard()[EPosition_X][EPosition_Y].getTeamColor() == (halfchess.getTurnColor() == Color.WHITE ? Color.BLACK : Color.WHITE) && !(halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof King)) {
                                            //halfchess.getCurrentBoard()[EPosition_X][EPosition_Y].setAllowMove(false);

                                            //Check By Knight Or Single Square
                                            if (Math.abs(Check_To_X - Check_From_X) != Math.abs(Check_To_Y - Check_From_Y) /*&& Math.abs(Check_To_X - Check_From_X) > 0 && Math.abs(Check_To_Y - Check_From_Y) > 0*/ || Math.abs(Check_To_X - Check_From_X) == 1 && Math.abs(Check_To_Y - Check_From_Y) == 1) {
                                                if (halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] != null && halfchess.getCurrentBoard()[EPosition_X][EPosition_Y].getTeamColor() == (halfchess.getTurnColor() == Color.WHITE ? Color.BLACK : Color.WHITE) && !(halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof King) &&
                                                        (halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Queen && ((Queen) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((halfchess.getTurnColor() == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, Check_From_X, Check_From_Y)
                                                        || halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Knight && ((Knight) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((halfchess.getTurnColor() == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, Check_From_X, Check_From_Y)
                                                        || halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Bishop && ((Bishop) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((halfchess.getTurnColor() == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, Check_From_X, Check_From_Y)
                                                        || halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Pawn && ((Pawn) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((halfchess.getTurnColor() == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, Check_From_X, Check_From_Y))) {
//                                                    Toast.makeText(this.getContext(),"Condition Piece of "+halfchess.getCurrentBoard()[EPosition_X][EPosition_Y].getClass().getSimpleName()+" at EPosition_X = " + EPosition_X+" and EPosition_Y = "+EPosition_Y+" to SEPosition_X = "+Check_From_X+" and SEPosition_Y = "+Check_From_Y+" is "+(halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Queen && ((Queen) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((halfchess.getTurnColor() == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, Check_From_X, Check_From_Y)
//                                                            || halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Knight && ((Knight) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((halfchess.getTurnColor() == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, Check_From_X, Check_From_Y)
//                                                            || halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Bishop && ((Bishop) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((halfchess.getTurnColor() == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, Check_From_X, Check_From_Y)
//                                                            || halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Pawn && ((Pawn) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((halfchess.getTurnColor() == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, Check_From_X, Check_From_Y)), Toast.LENGTH_SHORT).show();
                                                    halfchess.getCurrentBoard()[EPosition_X][EPosition_Y].setAllowMove(true);
                                                }
                                            } else {
                                                // Diagonal
                                                if (Check_To_X != Check_From_X && Check_To_Y != Check_From_Y) {
                                                    if (Check_To_Y > Check_From_Y && Check_To_X > Check_From_X) {
//                                                        for (Integer SEPosition_Y = Check_From_Y; SEPosition_Y < Check_To_Y; SEPosition_Y++) {
//                                                            for (Integer SEPosition_X = Check_From_X; SEPosition_X < Check_To_X; SEPosition_X++) {
                                                        for (Integer SEPosition_Y = Check_From_Y, SEPosition_X = Check_From_X; SEPosition_Y < Check_To_Y && SEPosition_X < Check_To_X; SEPosition_Y++, SEPosition_X++) {
                                                            Toast.makeText(this.getContext(),"SEPosition_X = " + SEPosition_X + "SEPosition_Y = " + SEPosition_Y, Toast.LENGTH_SHORT).show();
                                                            if (//halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] != null && halfchess.getCurrentBoard()[EPosition_X][EPosition_Y].getTeamColor() == (turn == Color.WHITE ? Color.BLACK : Color.WHITE) && !(halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof King) &&
                                                                    (halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Queen && ((Queen) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y)
                                                                    || halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Knight && ((Knight) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y)
                                                                    || halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Bishop && ((Bishop) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y)
                                                                    || halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Pawn && ((Pawn) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y))) {
                                                                halfchess.getCurrentBoard()[EPosition_X][EPosition_Y].setAllowMove(true);
                                                            }
                                                        }
//                                                        }
                                                    } else if (Check_To_Y < Check_From_Y && Check_To_X > Check_From_X) {
//                                                        for (Integer SEPosition_Y = Check_To_Y + 1; SEPosition_Y <= Check_From_Y; SEPosition_Y++) {
//                                                            for (Integer SEPosition_X = Check_From_X; SEPosition_X < Check_To_X; SEPosition_X++) {
                                                        for (Integer SEPosition_Y = Check_From_Y, SEPosition_X = Check_From_X; SEPosition_Y > Check_To_Y && SEPosition_X < Check_To_X && !halfchess.getCurrentBoard()[EPosition_X][EPosition_Y].isAllowMove(); SEPosition_Y--, SEPosition_X++) {
                                                            Toast.makeText(this.getContext(),"SEPosition_X = " + SEPosition_X + "SEPosition_Y = " + SEPosition_Y, Toast.LENGTH_SHORT).show();
                                                            if (//halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] != null && halfchess.getCurrentBoard()[EPosition_X][EPosition_Y].getTeamColor() == (turn == Color.WHITE ? Color.BLACK : Color.WHITE) && !(halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof King) &&
                                                                    (halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Queen && ((Queen) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y)
                                                                    || halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Knight && ((Knight) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y)
                                                                    || halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Bishop && ((Bishop) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y)
                                                                    || halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Pawn && ((Pawn) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y))) {
                                                                halfchess.getCurrentBoard()[EPosition_X][EPosition_Y].setAllowMove(true);
                                                            }
                                                        }
//                                                        }
                                                    } else if (Check_To_Y > Check_From_Y && Check_To_X < Check_From_X) {
//                                                        for (Integer SEPosition_Y = Check_From_Y; SEPosition_Y < Check_To_Y; SEPosition_Y++) {
//                                                            for (Integer SEPosition_X = Check_To_X + 1; SEPosition_X <= Check_From_X; SEPosition_X++) {
                                                        for (Integer SEPosition_Y = Check_From_Y, SEPosition_X = Check_From_X; SEPosition_Y < Check_To_Y && SEPosition_X > Check_To_X && !halfchess.getCurrentBoard()[EPosition_X][EPosition_Y].isAllowMove(); SEPosition_Y++, SEPosition_X--) {
                                                            if (halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] != null && halfchess.getCurrentBoard()[EPosition_X][EPosition_Y].getTeamColor() == (turn == Color.WHITE ? Color.BLACK : Color.WHITE) && !(halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof King) &&
                                                                    (halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Queen && ((Queen) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y)
                                                                            || halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Knight && ((Knight) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y)
                                                                            || halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Bishop && ((Bishop) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y)
                                                                            || halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Pawn && ((Pawn) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y))) {
                                                                halfchess.getCurrentBoard()[EPosition_X][EPosition_Y].setAllowMove(true);
                                                            }
                                                        }
//                                                        }
                                                    } else {
//                                                        for (Integer SEPosition_Y = Check_To_Y + 1; SEPosition_Y <= Check_From_Y; SEPosition_Y++) {
//                                                            for (Integer SEPosition_X = Check_To_X + 1; SEPosition_X <= Check_From_X; SEPosition_X++) {
                                                        //Toast.makeText(this.getContext(),"4) EPosition_X = " + EPosition_X + ", EPosition_Y = " + EPosition_Y, Toast.LENGTH_SHORT).show();
                                                        for (Integer SEPosition_Y = Check_From_Y, SEPosition_X = Check_From_X; SEPosition_Y > Check_To_Y && SEPosition_X > Check_To_X && !halfchess.getCurrentBoard()[EPosition_X][EPosition_Y].isAllowMove(); SEPosition_Y--, SEPosition_X--) {
                                                            //Toast.makeText(this.getContext(),"4) SEPosition_X = " + SEPosition_X + ", SEPosition_Y = " + SEPosition_Y, Toast.LENGTH_SHORT).show();
//                                                            if (//halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] != null && halfchess.getCurrentBoard()[EPosition_X][EPosition_Y].getTeamColor() == (turn == Color.WHITE ? Color.BLACK : Color.WHITE) && !(halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof King) &&
//                                                                    (halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Queen && ((Queen) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y)
//                                                                            || halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Knight && ((Knight) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y)
//                                                                            || halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Bishop && ((Bishop) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y)
//                                                                            || halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Pawn && ((Pawn) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y))) {
//
//                                                                halfchess.getCurrentBoard()[EPosition_X][EPosition_Y].setAllowMove(true);
//                                                            }
                                                            //Toast.makeText(this.getContext(),"Queen : SEPosition_X = " + ((Queen) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, 1, 1), Toast.LENGTH_SHORT).show();

                                                            if (halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Queen && ((Queen) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y)) {
//                                                                Toast.makeText(this.getContext(),"Queen : EPosition_X = " + EPosition_X + ", EPosition_Y = " + EPosition_Y, Toast.LENGTH_SHORT).show();

                                                                ((Queen) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).setAllowMove(true);
                                                            } else if (halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Knight && ((Knight) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y)) {
                                                                //Toast.makeText(this.getContext(),"Knight : EPosition_X = " + EPosition_X + ", EPosition_Y = " + EPosition_Y, Toast.LENGTH_SHORT).show();
                                                                //Toast.makeText(this.getContext(),"Knight : SEPosition_X = " + SEPosition_X + ", SEPosition_Y = " + SEPosition_Y, Toast.LENGTH_SHORT).show();
                                                                ((Knight) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).setAllowMove(true);
                                                            } else if (halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Bishop && ((Bishop) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y)) {
                                                                //Toast.makeText(this.getContext(),"Bishop : EPosition_X = " + EPosition_X + ", EPosition_Y = " + EPosition_Y, Toast.LENGTH_SHORT).show();
                                                                ((Bishop) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).setAllowMove(true);
                                                            } else if (halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Pawn && ((Pawn) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y)) {
                                                                //Toast.makeText(this.getContext(),"Pawn : EPosition_X = " + EPosition_X + ", EPosition_Y = " + EPosition_Y, Toast.LENGTH_SHORT).show();
                                                                ((Pawn) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).setAllowMove(true);
                                                            }
                                                            Toast.makeText(this.getContext(),"Condition Piece of "+halfchess.getCurrentBoard()[EPosition_X][EPosition_Y].getClass().getSimpleName()+" at EPosition_X = " + EPosition_X+" and EPosition_Y = "+EPosition_Y+" to SEPosition_X = "+SEPosition_X+" and SEPosition_Y = "+SEPosition_Y+" is "+(halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Queen && ((Queen) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y)
                                                                    || halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Knight && ((Knight) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y)
                                                                    || halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Bishop && ((Bishop) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y)
                                                                    || halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Pawn && ((Pawn) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y)), Toast.LENGTH_SHORT).show();
                                                        }
//                                                        }
                                                    }

//                                                    for (Integer SEPosition_Y = Check_To_Y > Check_From_Y ? Check_From_Y : Check_To_Y; SEPosition_Y < (Check_To_Y > Check_From_Y ? Check_To_Y : Check_From_Y); SEPosition_Y++) {
//                                                        for (Integer SEPosition_X = Check_To_X > Check_From_X ? Check_From_X : Check_To_X; SEPosition_X < (Check_To_X > Check_From_X ? Check_To_X : Check_From_X); SEPosition_X++) {
//                                                            //for (Integer SEPosition_X = 0; SEPosition_X < halfchess.getHeightBoard(); SEPosition_X++) {
//                                                            if (!(piece instanceof King) && (piece instanceof Queen && ((Queen) piece).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), piece, Touched_Position_X, Touched_Position_Y, SEPosition_X, SEPosition_Y) ||
//                                                                    piece instanceof Knight && ((Knight) piece).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), piece, Touched_Position_X, Touched_Position_Y, SEPosition_X, SEPosition_Y) ||
//                                                                    piece instanceof Bishop && ((Bishop) piece).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), piece, Touched_Position_X, Touched_Position_Y, SEPosition_X, SEPosition_Y) ||
//                                                                    piece instanceof Pawn && ((Pawn) piece).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), piece, Touched_Position_X, Touched_Position_Y, SEPosition_X, SEPosition_Y))) {
//
//                                                            }
//                                                        }
//                                                    }
                                                } else {
                                                    if (Check_To_X == Check_From_X) {
                                                        //Integer SEPosition_X = Check_From_X;
                                                        if (Check_To_Y > Check_From_Y) {
                                                            for (Integer SEPosition_Y = Check_From_Y, SEPosition_X = Check_From_X; SEPosition_Y < Check_To_Y; SEPosition_Y++, SEPosition_X+=0) {
                                                                if (halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] != null && halfchess.getCurrentBoard()[EPosition_X][EPosition_Y].getTeamColor() == (turn == Color.WHITE ? Color.BLACK : Color.WHITE) && !(halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof King) &&
                                                                        (halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Queen && ((Queen) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y)
                                                                                || halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Knight && ((Knight) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y)
                                                                                || halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Bishop && ((Bishop) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y)
                                                                                || halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Pawn && ((Pawn) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y))) {
                                                                    halfchess.getCurrentBoard()[EPosition_X][EPosition_Y].setAllowMove(true);
                                                                }
                                                            }
                                                        } else {
                                                            for (Integer SEPosition_Y = Check_To_Y + 1, SEPosition_X = Check_From_X; SEPosition_Y <= Check_From_Y; SEPosition_Y++, SEPosition_X*=1) {
                                                                if (halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] != null && halfchess.getCurrentBoard()[EPosition_X][EPosition_Y].getTeamColor() == (turn == Color.WHITE ? Color.BLACK : Color.WHITE) && !(halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof King) &&
                                                                        (halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Queen && ((Queen) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y)
                                                                                || halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Knight && ((Knight) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y)
                                                                                || halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Bishop && ((Bishop) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y)
                                                                                || halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Pawn && ((Pawn) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y))) {
                                                                    halfchess.getCurrentBoard()[EPosition_X][EPosition_Y].setAllowMove(true);
                                                                }
                                                            }
                                                        }

//                                                        for (Integer SEPosition_Y = Check_To_Y > Check_From_Y ? Check_From_Y : Check_To_Y; SEPosition_Y < (Check_To_Y > Check_From_Y ? Check_To_Y : Check_From_Y); SEPosition_Y++) {
//                                                            if (!(piece instanceof King) && (piece instanceof Queen && ((Queen) piece).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), piece, Touched_Position_X, Touched_Position_Y, SEPosition_X, SEPosition_Y) ||
//                                                                    piece instanceof Knight && ((Knight) piece).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), piece, Touched_Position_X, Touched_Position_Y, SEPosition_X, SEPosition_Y) ||
//                                                                    piece instanceof Bishop && ((Bishop) piece).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), piece, Touched_Position_X, Touched_Position_Y, SEPosition_X, SEPosition_Y) ||
//                                                                    piece instanceof Pawn && ((Pawn) piece).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), piece, Touched_Position_X, Touched_Position_Y, SEPosition_X, SEPosition_Y))) {
//
//                                                            }
//                                                        }
                                                    } else if (Check_To_Y == Check_From_Y) {
//                                                        Integer SEPosition_Y = Check_From_Y;
                                                        if (Check_To_X > Check_From_X) {
                                                            for (Integer SEPosition_X = Check_From_X, SEPosition_Y = Check_From_Y; SEPosition_X < Check_To_X; SEPosition_X++, SEPosition_Y+=0) {
                                                                if (halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] != null && halfchess.getCurrentBoard()[EPosition_X][EPosition_Y].getTeamColor() == (turn == Color.WHITE ? Color.BLACK : Color.WHITE) && !(halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof King) &&
                                                                        (halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Queen && ((Queen) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y)
                                                                                || halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Knight && ((Knight) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y)
                                                                                || halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Bishop && ((Bishop) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y)
                                                                                || halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Pawn && ((Pawn) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y))) {
                                                                    halfchess.getCurrentBoard()[EPosition_X][EPosition_Y].setAllowMove(true);
                                                                }
                                                            }
                                                        } else {
                                                            for (Integer SEPosition_X = Check_To_X + 1, SEPosition_Y = Check_From_Y; SEPosition_X < Check_From_X; SEPosition_X++, SEPosition_Y*=1) {
                                                                if (halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] != null && halfchess.getCurrentBoard()[EPosition_X][EPosition_Y].getTeamColor() == (turn == Color.WHITE ? Color.BLACK : Color.WHITE) && !(halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof King) &&
                                                                        (halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Queen && ((Queen) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y)
                                                                                || halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Knight && ((Knight) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y)
                                                                                || halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Bishop && ((Bishop) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y)
                                                                                || halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Pawn && ((Pawn) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y))) {
                                                                    halfchess.getCurrentBoard()[EPosition_X][EPosition_Y].setAllowMove(true);
                                                                }
                                                            }
                                                        }
//                                                        for (Integer SEPosition_X = Check_To_X > Check_From_X ? Check_From_X : Check_To_X; SEPosition_X < (Check_To_X > Check_From_X ? Check_To_X : Check_From_X); SEPosition_X++) {
//                                                            if (!(piece instanceof King) && (piece instanceof Queen && ((Queen) piece).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), piece, Touched_Position_X, Touched_Position_Y, SEPosition_X, SEPosition_Y) ||
//                                                                    piece instanceof Knight && ((Knight) piece).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), piece, Touched_Position_X, Touched_Position_Y, SEPosition_X, SEPosition_Y) ||
//                                                                    piece instanceof Bishop && ((Bishop) piece).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), piece, Touched_Position_X, Touched_Position_Y, SEPosition_X, SEPosition_Y) ||
//                                                                    piece instanceof Pawn && ((Pawn) piece).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), piece, Touched_Position_X, Touched_Position_Y, SEPosition_X, SEPosition_Y))) {
//
//                                                            }
//                                                        }
                                                    }
                                                }
//                                                for (Integer SEPosition_Y = Check_To_Y > Check_From_Y ? Check_From_Y : Check_To_Y; SEPosition_Y < (Check_To_Y > Check_From_Y ? Check_To_Y : Check_From_Y); SEPosition_Y++) {
//                                                    for (Integer SEPosition_X = Check_To_X > Check_From_X ? Check_From_X : Check_To_X; SEPosition_X < (Check_To_X > Check_From_X ? Check_To_X : Check_From_X); SEPosition_X++) {
//                                                    //for (Integer SEPosition_X = 0; SEPosition_X < halfchess.getHeightBoard(); SEPosition_X++) {
//
//                                                    }
//                                                }

//                                                for (Integer SEPosition_Y = (Check_To_Y >= Check_From_Y ? Check_From_Y : Check_To_Y); SEPosition_X = (Check_To_X >= Check_From_X ? Check_From_X : Check_To_X);
//                                                SEPosition_Y < (Check_To_Y > Check_From_Y ? Check_To_Y : Check_From_Y) && SEPosition_X < (Check_To_X > Check_From_X ? Check_To_X : Check_From_X); SEPosition_Y++; SEPosition_X++) {
//
//                                                }

//                                                if (checkField(Check_To_X, Check_To_Y, Check_From_X > Check_To_X ? Check_From_X : Check_To_X, Check_To_Y)) {
//
//                                                }
//                                                for(Integer SEPosition_Y = (Check_To_Y >= Check_From_Y ? Check_From_Y : Check_To_Y), SEPosition_X = (Check_To_X >= Check_From_X ? Check_From_X : Check_To_X);
//                                                    SEPosition_Y < (Check_To_Y > Check_From_Y ? Check_To_Y : Check_From_Y) && SEPosition_X > (Check_To_X > Check_From_X ? Check_To_X : Check_From_X); SEPosition_Y++, SEPosition_X++) {
//
//                                                }

//                                                for (Integer SEPosition_Y = Check_To_Y > Check_From_Y ? Check_From_Y : Check_From_Y > Check_To_Y ? Check_To_Y : Check_From_Y; SEPosition_Y < (Check_To_Y > Check_From_Y ? Check_To_Y : Check_From_Y > Check_To_Y ? Check_From_Y : Check_From_Y); SEPosition_Y++) {
//                                                    for (Integer SEPosition_X = Check_To_X > Check_From_X ? Check_From_X : Check_From_X > Check_To_X ? Check_To_X : Check_From_X; SEPosition_X < (Check_To_X > Check_From_X ? Check_To_X : Check_From_X > Check_To_X ? Check_From_X : Check_From_X); SEPosition_X++) {
//                                                        Toast.makeText(this.getContext(),"SEPosition_X = " + SEPosition_X + "SEPosition_Y = " + SEPosition_Y, Toast.LENGTH_SHORT).show();
//                                                        //Toast.makeText(this.getContext(),"SEPosition_Y = " + SEPosition_Y, Toast.LENGTH_SHORT).show();
////                                                        if (halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Queen && ((Queen) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y) ||
////                                                                halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Knight && ((Knight) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y) ||
////                                                                halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Bishop && ((Bishop) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y) ||
////                                                                halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Pawn && ((Pawn) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SEPosition_X, SEPosition_Y)) {
////                                                            if (halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Queen)
////                                                                ((Queen) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).setAllowMove(true);
////                                                            else if (halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Knight)
////                                                                ((Knight) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).setAllowMove(true);
////                                                            else if (halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Bishop)
////                                                                ((Bishop) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).setAllowMove(true);
////                                                            else if (halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Pawn)
////                                                                ((Pawn) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).setAllowMove(true);
////                                                        }
//
//                                                    }
//                                                }
//                                                Toast.makeText(this.getContext(),"EPosition_X = " + EPosition_X + ", EPosition_Y = " + EPosition_Y, Toast.LENGTH_SHORT).show();
//                                                Toast.makeText(this.getContext(),"DPosition_X = " + Check_From_X + ", DPosition_Y = " + Check_From_Y, Toast.LENGTH_SHORT).show();

//                                                if (checkDefendKing(EPosition_X, EPosition_Y, Check_From_X, Check_From_Y)) {
//                                                    //halfchess.getCurrentBoard()[EPosition_X][EPosition_Y].setAllowMove(true);
//                                                    if (halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Queen)
//                                                        ((Queen) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).setAllowMove(true);
//                                                    else if (halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Knight)
//                                                        ((Knight) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).setAllowMove(true);
//                                                    else if (halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Bishop)
//                                                        ((Bishop) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).setAllowMove(true);
//                                                    else if (halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Pawn)
//                                                        ((Pawn) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).setAllowMove(true);
//                                                }

                                            }
//                                            if (halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] != null && halfchess.getCurrentBoard()[EPosition_X][EPosition_Y].getTeamColor() == (turn == Color.WHITE ? Color.BLACK : Color.WHITE) && !(halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof King) &&
//                                                    (halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Queen && ((Queen) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, Position_X, Position_Y)
//                                                            || halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Knight && ((Knight) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, Position_X, Position_Y)
//                                                            || halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Bishop && ((Bishop) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, Position_X, Position_Y)
//                                                            || halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Pawn && ((Pawn) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, Position_X, Position_Y))) {
//                                                halfchess.getCurrentBoard()[EPosition_X][EPosition_Y].setAllowMove(true);
//                                            }
                                        }
                                    }
                                }

                                for (Integer EPosition_Y = 0; EPosition_Y < halfchess.getWidthBoard(); EPosition_Y++) {
                                    for (Integer EPosition_X = 0; EPosition_X < halfchess.getHeightBoard(); EPosition_X++) {
                                        if (halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] != null && halfchess.getCurrentBoard()[EPosition_X][EPosition_Y].getTeamColor() == (turn == Color.WHITE ? Color.BLACK : Color.WHITE) && !(halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof King)) {
//                                            Toast.makeText(this.getContext(),"EPosition_X = " + EPosition_X + ", EPosition_Y = " + EPosition_Y, Toast.LENGTH_SHORT).show();
//                                            Toast.makeText(this.getContext(),"Allow = " + halfchess.getCurrentBoard()[EPosition_X][EPosition_Y].isAllowMove(), Toast.LENGTH_SHORT).show();
                                            //Toast.makeText(this.getContext(),"EPosition_X = " + EPosition_X + ", EPosition_Y = " + EPosition_Y, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                                //Toast.makeText(this.getContext(),(turn == Color.WHITE ? "Black" : "White") + "in Check" + DrawMode, Toast.LENGTH_SHORT).show();
//                                for (Integer EPosition_Y = 0; EPosition_Y < halfchess.getWidthBoard(); EPosition_Y++) {
//                                    for (Integer EPosition_X = 0; EPosition_X < halfchess.getHeightBoard(); EPosition_X++) {
//                                        if (halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] != null && halfchess.getCurrentBoard()[EPosition_X][EPosition_Y].getTeamColor() == (turn == Color.WHITE ? Color.BLACK : Color.WHITE) && !(halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof King) && (((Pawn)halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((turn == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, SPosition_X, SPosition_Y))) {
//
//                                        }
//                                    }
//                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean checkField(int Check_Position_X, int Check_Position_Y, int Check_To_X, int Check_To_Y) {

//        if (Check_Position_X != Check_To_X || EPosition_Y != DPosition_Y) {
//
//        }
        return false;
    }

    private boolean checkDefendKing(int EPosition_X, int EPosition_Y, int DPosition_X, int DPosition_Y) {
        if (EPosition_X != DPosition_X || EPosition_Y != DPosition_Y) {
            if (//halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] != null && halfchess.getCurrentBoard()[EPosition_X][EPosition_Y].getTeamColor() == (halfchess.getTurnColor() == Color.WHITE ? Color.BLACK : Color.WHITE) && !(halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof King) &&
                    (halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Queen && ((Queen) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((halfchess.getTurnColor() == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, DPosition_X, DPosition_Y)
                    || halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Knight && ((Knight) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((halfchess.getTurnColor() == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, DPosition_X, DPosition_Y)
                    || halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Bishop && ((Bishop) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((halfchess.getTurnColor() == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, DPosition_X, DPosition_Y)
                    || halfchess.getCurrentBoard()[EPosition_X][EPosition_Y] instanceof Pawn && ((Pawn) halfchess.getCurrentBoard()[EPosition_X][EPosition_Y]).Rule((halfchess.getTurnColor() == Color.WHITE ? Color.BLACK : Color.WHITE), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[EPosition_X][EPosition_Y], EPosition_X, EPosition_Y, DPosition_X, DPosition_Y))) {
                //halfchess.getCurrentBoard()[EPosition_X][EPosition_Y].setAllowMove(true);
//                Toast.makeText(this.getContext(),"EPosition_X = " + EPosition_X + ", EPosition_Y = " + EPosition_Y, Toast.LENGTH_SHORT).show();
//                Toast.makeText(this.getContext(),"DPosition_X = " + DPosition_X + ", DPosition_Y = " + DPosition_Y, Toast.LENGTH_SHORT).show();
                return true;
            }
            if (DPosition_X < EPosition_X && DPosition_Y > EPosition_Y)
                return checkDefendKing(EPosition_X - 1, EPosition_Y + 1, DPosition_X, DPosition_Y);
            else if (DPosition_X > EPosition_X && DPosition_Y > EPosition_Y)
                return checkDefendKing(EPosition_X + 1, EPosition_Y + 1, DPosition_X, DPosition_Y);
            else if (DPosition_X > EPosition_X && DPosition_Y < EPosition_Y)
                return checkDefendKing(EPosition_X + 1, EPosition_Y - 1, DPosition_X, DPosition_Y);
            else if (DPosition_X < EPosition_X && DPosition_Y < EPosition_Y)
                return checkDefendKing(EPosition_X - 1, EPosition_Y - 1, DPosition_X, DPosition_Y);
            else if (DPosition_X == EPosition_X && DPosition_Y < EPosition_Y)
                return checkDefendKing(EPosition_X, EPosition_Y - 1, DPosition_X, DPosition_Y);
            else if (DPosition_X == EPosition_X && DPosition_Y > EPosition_Y)
                return checkDefendKing(EPosition_X, EPosition_Y + 1, DPosition_X, DPosition_Y);
            else if (DPosition_X < EPosition_X && DPosition_Y == EPosition_Y)
                return checkDefendKing(EPosition_X - 1, EPosition_Y, DPosition_X, DPosition_Y);
            else if (DPosition_X > EPosition_X && DPosition_Y == EPosition_Y)
                return checkDefendKing(EPosition_X + 1, EPosition_Y, DPosition_X, DPosition_Y);
        }
        return false;
    }

    private boolean kingIsChecked() {
        return false;
    }

    private void MoveActiveLegalPiece(Integer Position_X, Integer Position_Y) {

    }

    private void AnimationMove(int from_X, int from_Y, int to_X, int to_Y) {
        final int From_X = from_X;
        final int From_Y = from_Y;
        final int To_X = to_X;
        final int To_Y = to_Y;
        myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                //movePiece(From_X, From_Y, To_X, To_Y);
                timerState = true;
                if (From_X == To_X && From_Y == To_Y)
                    timerState = false;
            }
        }, 500);

    }

    private void movePiece(Canvas canvas, int from_X, int from_Y, int to_X, int to_Y) {
//        if (halfchess.getCurrentBoard()[Position_X][Position_Y] != null) {
//            Drawable drawable = null;
//            if (halfchess.getCurrentBoard()[Position_X][Position_Y] instanceof King)
//                drawable = halfchess.getCurrentBoard()[Position_X][Position_Y].getTeamColor() == Color.BLACK ? drawablePiece[0] : drawablePiece[1];
//            else if (halfchess.getCurrentBoard()[Position_X][Position_Y] instanceof Queen)
//                drawable = halfchess.getCurrentBoard()[Position_X][Position_Y].getTeamColor() == Color.BLACK ? drawablePiece[2] : drawablePiece[3];
//            else if (halfchess.getCurrentBoard()[Position_X][Position_Y] instanceof Knight)
//                drawable = halfchess.getCurrentBoard()[Position_X][Position_Y].getTeamColor() == Color.BLACK ? drawablePiece[4] : drawablePiece[5];
//            else if (halfchess.getCurrentBoard()[Position_X][Position_Y] instanceof Bishop)
//                drawable = halfchess.getCurrentBoard()[Position_X][Position_Y].getTeamColor() == Color.BLACK ? drawablePiece[6] : drawablePiece[7];
//            else if (halfchess.getCurrentBoard()[Position_X][Position_Y] instanceof Pawn)
//                drawable = halfchess.getCurrentBoard()[Position_X][Position_Y].getTeamColor() == Color.BLACK ? drawablePiece[8] : drawablePiece[9];
//            drawable.setBounds(rectangle[Position_X][Position_Y].left + 15, rectangle[Position_X][Position_Y].top + 15, rectangle[Position_X][Position_Y].right - 15, rectangle[Position_X][Position_Y].bottom - 15);
//            drawable.draw(canvas);
//        }
    }

    private void ClearLegalSquare() {
        Touched_Position_X = -1; Touched_Position_Y = -1;
        piece = null;
        DrawMode = 0;
    }

    private void DrawTileBoard (Canvas canvas) {
        for (Integer Position_Y = 0; Position_Y < halfchess.getWidthBoard(); Position_Y++) {
            for (Integer Position_X = 0; Position_X < halfchess.getHeightBoard(); Position_X++) {
                paint.setStyle(Paint.Style.FILL);
                paint.setColor(Position_Y % 2 == 0 && Position_X % 2 == 1 || Position_Y % 2 == 1 && Position_X % 2 == 0 ? Color.GRAY : Color.WHITE);
                rectangle[Position_X][Position_Y] = new Rect();
                rectangle[Position_X][Position_Y].left = 180 * Position_Y + 180;
                rectangle[Position_X][Position_Y].top = 180 * Position_X + 180;
                rectangle[Position_X][Position_Y].right = rectangle[Position_X][Position_Y].left + 180;
                rectangle[Position_X][Position_Y].bottom = rectangle[Position_X][Position_Y].top + 180;
                canvas.drawRect(rectangle[Position_X][Position_Y], paint);
            }
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //OnDrawMode(DrawMode, canvas);
//        if (DrawMode == 0) { // Initialization and Show HalfChess Board
        DrawTileBoard(canvas);
//        if (timerState) {
//            Drawable drawable = null;
//            if (halfchess.getCurrentBoard()[Touched_Position_X][Touched_Position_Y] instanceof King)
//                drawable = halfchess.getCurrentBoard()[Touched_Position_X][Touched_Position_Y].getTeamColor() == Color.BLACK ? drawablePiece[0] : drawablePiece[1];
//            else if (halfchess.getCurrentBoard()[Touched_Position_X][Touched_Position_Y] instanceof Queen)
//                drawable = halfchess.getCurrentBoard()[Touched_Position_X][Touched_Position_Y].getTeamColor() == Color.BLACK ? drawablePiece[2] : drawablePiece[3];
//            else if (halfchess.getCurrentBoard()[Touched_Position_X][Touched_Position_Y] instanceof Knight)
//                drawable = halfchess.getCurrentBoard()[Touched_Position_X][Touched_Position_Y].getTeamColor() == Color.BLACK ? drawablePiece[4] : drawablePiece[5];
//            else if (halfchess.getCurrentBoard()[Touched_Position_X][Touched_Position_Y] instanceof Bishop)
//                drawable = halfchess.getCurrentBoard()[Touched_Position_X][Touched_Position_Y].getTeamColor() == Color.BLACK ? drawablePiece[6] : drawablePiece[7];
//            else if (halfchess.getCurrentBoard()[Touched_Position_X][Touched_Position_Y] instanceof Pawn)
//                drawable = halfchess.getCurrentBoard()[Touched_Position_X][Touched_Position_Y].getTeamColor() == Color.BLACK ? drawablePiece[8] : drawablePiece[9];
//            drawable.setBounds(rectangle[Touched_Position_X][Touched_Position_Y].left + 150, rectangle[Touched_Position_X][Touched_Position_Y].top + 150, rectangle[Touched_Position_X][Touched_Position_Y].right - 150, rectangle[Touched_Position_X][Touched_Position_Y].bottom - 150);
//            drawable.draw(canvas);
//        }
        for (Integer Position_Y = 0; Position_Y < halfchess.getWidthBoard(); Position_Y++) {
            for (Integer Position_X = 0; Position_X < halfchess.getHeightBoard(); Position_X++) {
//                paint.setStyle(Paint.Style.FILL);
//                paint.setColor(Position_Y % 2 == 0 && Position_X % 2 == 1 || Position_Y % 2 == 1 && Position_X % 2 == 0 ? Color.GRAY : Color.WHITE);
//                rectangle[Position_X][Position_Y] = new Rect();
//                rectangle[Position_X][Position_Y].left = 180 * Position_Y + 180;
//                rectangle[Position_X][Position_Y].top = 180 * Position_X + 180;
//                rectangle[Position_X][Position_Y].right = rectangle[Position_X][Position_Y].left + 180;
//                rectangle[Position_X][Position_Y].bottom = rectangle[Position_X][Position_Y].top + 180;
//                canvas.drawRect(rectangle[Position_X][Position_Y], paint);

                // Hold and Show Legal Move of Piece
                if (DrawMode == 1 && Touched_Position_X == Position_X && Touched_Position_Y == Position_Y) {

                    if (!halfchess.isCheck()) {
                        for (Integer SPosition_Y = 0; SPosition_Y < halfchess.getWidthBoard(); SPosition_Y++) {
                            for (Integer SPosition_X = 0; SPosition_X < halfchess.getHeightBoard(); SPosition_X++) {
                                if (piece instanceof King && ((King) piece).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), piece, Touched_Position_X, Touched_Position_Y, SPosition_X, SPosition_Y) ||
                                        piece instanceof Queen && ((Queen) piece).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), piece, Touched_Position_X, Touched_Position_Y, SPosition_X, SPosition_Y) ||
                                        piece instanceof Knight && ((Knight) piece).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), piece, Touched_Position_X, Touched_Position_Y, SPosition_X, SPosition_Y) ||
                                        piece instanceof Bishop && ((Bishop) piece).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), piece, Touched_Position_X, Touched_Position_Y, SPosition_X, SPosition_Y) ||
                                        piece instanceof Pawn && ((Pawn) piece).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), piece, Touched_Position_X, Touched_Position_Y, SPosition_X, SPosition_Y)) {
                                    paint.setColor(SPosition_Y % 2 == 0 && SPosition_X % 2 == 1 || SPosition_Y % 2 == 1 && SPosition_X % 2 == 0 ? Color.rgb(204, 204, 0) : Color.rgb(255, 255, 51));
                                    canvas.drawRect(rectangle[SPosition_X][SPosition_Y], paint);
                                    DrawPiece(canvas, SPosition_X, SPosition_Y);
                                }
                            }
                        }
                    } else {
                        if (Math.abs(Check_To_X - Check_From_X) != Math.abs(Check_To_Y - Check_From_Y) && Math.abs(Check_To_X - Check_From_X) > 0 && Math.abs(Check_To_Y - Check_From_Y) > 0 || Math.abs(Check_To_X - Check_From_X) == 1 && Math.abs(Check_To_Y - Check_From_Y) == 1) {
                            if (!(piece instanceof King) && (piece instanceof Queen && ((Queen) piece).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), piece, Touched_Position_X, Touched_Position_Y, Check_From_X, Check_From_Y)
                                    || piece instanceof Knight && ((Knight) piece).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), piece, Touched_Position_X, Touched_Position_Y, Check_From_X, Check_From_Y)
                                    || piece instanceof Bishop && ((Bishop) piece).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), piece, Touched_Position_X, Touched_Position_Y, Check_From_X, Check_From_Y)
                                    || piece instanceof Pawn && ((Pawn) piece).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), piece, Touched_Position_X, Touched_Position_Y, Check_From_X, Check_From_Y))) {
                                paint.setColor(Check_From_Y % 2 == 0 && Check_From_X % 2 == 1 || Check_From_Y % 2 == 1 && Check_From_X % 2 == 0 ? Color.rgb(204, 204, 0) : Color.rgb(255, 255, 51));
                                canvas.drawRect(rectangle[Check_From_X][Check_From_Y], paint);
                                DrawPiece(canvas, Check_From_X, Check_From_Y);
                            }
                        } else {
                            if (Check_To_X != Check_From_X && Check_To_Y != Check_From_Y) {
                                if (Check_To_Y > Check_From_Y && Check_To_X > Check_From_X) {
                                    for (Integer SEPosition_Y = Check_From_Y, SEPosition_X = Check_From_X; SEPosition_Y < Check_To_Y && SEPosition_X < Check_To_X; SEPosition_Y++, SEPosition_X++) {
                                        if (!(piece instanceof King) && (piece instanceof Queen && ((Queen) piece).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), piece, Touched_Position_X, Touched_Position_Y, SEPosition_X, SEPosition_Y) ||
                                                piece instanceof Knight && ((Knight) piece).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), piece, Touched_Position_X, Touched_Position_Y, SEPosition_X, SEPosition_Y) ||
                                                piece instanceof Bishop && ((Bishop) piece).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), piece, Touched_Position_X, Touched_Position_Y, SEPosition_X, SEPosition_Y) ||
                                                piece instanceof Pawn && ((Pawn) piece).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), piece, Touched_Position_X, Touched_Position_Y, SEPosition_X, SEPosition_Y))) {
                                            paint.setColor(SEPosition_Y % 2 == 0 && SEPosition_X % 2 == 1 || SEPosition_Y % 2 == 1 && SEPosition_X % 2 == 0 ? Color.rgb(204, 204, 0) : Color.rgb(255, 255, 51));
                                            canvas.drawRect(rectangle[SEPosition_X][SEPosition_Y], paint);
                                            DrawPiece(canvas, SEPosition_X, SEPosition_Y);
                                        }
                                    }
                                } else if (Check_To_Y < Check_From_Y && Check_To_X > Check_From_X) {
                                    for (Integer SEPosition_Y = Check_From_Y, SEPosition_X = Check_From_X; SEPosition_Y > Check_To_Y && SEPosition_X < Check_To_X; SEPosition_Y--, SEPosition_X++) {
                                        if (!(piece instanceof King) && (piece instanceof Queen && ((Queen) piece).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), piece, Touched_Position_X, Touched_Position_Y, SEPosition_X, SEPosition_Y) ||
                                                piece instanceof Knight && ((Knight) piece).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), piece, Touched_Position_X, Touched_Position_Y, SEPosition_X, SEPosition_Y) ||
                                                piece instanceof Bishop && ((Bishop) piece).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), piece, Touched_Position_X, Touched_Position_Y, SEPosition_X, SEPosition_Y) ||
                                                piece instanceof Pawn && ((Pawn) piece).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), piece, Touched_Position_X, Touched_Position_Y, SEPosition_X, SEPosition_Y))) {
                                            paint.setColor(SEPosition_Y % 2 == 0 && SEPosition_X % 2 == 1 || SEPosition_Y % 2 == 1 && SEPosition_X % 2 == 0 ? Color.rgb(204, 204, 0) : Color.rgb(255, 255, 51));
                                            canvas.drawRect(rectangle[SEPosition_X][SEPosition_Y], paint);
                                            DrawPiece(canvas, SEPosition_X, SEPosition_Y);
                                        }
                                    }
                                } else if (Check_To_Y > Check_From_Y && Check_To_X < Check_From_X) {
                                    for (Integer SEPosition_Y = Check_From_Y, SEPosition_X = Check_From_X; SEPosition_Y < Check_To_Y && SEPosition_X > Check_To_X; SEPosition_Y++, SEPosition_X--) {
                                        if (!(piece instanceof King) && (piece instanceof Queen && ((Queen) piece).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), piece, Touched_Position_X, Touched_Position_Y, SEPosition_X, SEPosition_Y) ||
                                                piece instanceof Knight && ((Knight) piece).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), piece, Touched_Position_X, Touched_Position_Y, SEPosition_X, SEPosition_Y) ||
                                                piece instanceof Bishop && ((Bishop) piece).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), piece, Touched_Position_X, Touched_Position_Y, SEPosition_X, SEPosition_Y) ||
                                                piece instanceof Pawn && ((Pawn) piece).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), piece, Touched_Position_X, Touched_Position_Y, SEPosition_X, SEPosition_Y))) {
                                            paint.setColor(SEPosition_Y % 2 == 0 && SEPosition_X % 2 == 1 || SEPosition_Y % 2 == 1 && SEPosition_X % 2 == 0 ? Color.rgb(204, 204, 0) : Color.rgb(255, 255, 51));
                                            canvas.drawRect(rectangle[SEPosition_X][SEPosition_Y], paint);
                                            DrawPiece(canvas, SEPosition_X, SEPosition_Y);
                                        }
                                    }
                                } else {
                                    for (Integer SEPosition_Y = Check_From_Y, SEPosition_X = Check_From_X; SEPosition_Y > Check_To_Y && SEPosition_X > Check_To_X; SEPosition_Y--, SEPosition_X--) {
                                        if (!(piece instanceof King) && (piece instanceof Queen && ((Queen) piece).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), piece, Touched_Position_X, Touched_Position_Y, SEPosition_X, SEPosition_Y) ||
                                                piece instanceof Knight && ((Knight) piece).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), piece, Touched_Position_X, Touched_Position_Y, SEPosition_X, SEPosition_Y) ||
                                                piece instanceof Bishop && ((Bishop) piece).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), piece, Touched_Position_X, Touched_Position_Y, SEPosition_X, SEPosition_Y) ||
                                                piece instanceof Pawn && ((Pawn) piece).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), piece, Touched_Position_X, Touched_Position_Y, SEPosition_X, SEPosition_Y))) {
                                            paint.setColor(SEPosition_Y % 2 == 0 && SEPosition_X % 2 == 1 || SEPosition_Y % 2 == 1 && SEPosition_X % 2 == 0 ? Color.rgb(204, 204, 0) : Color.rgb(255, 255, 51));
                                            canvas.drawRect(rectangle[SEPosition_X][SEPosition_Y], paint);
                                            DrawPiece(canvas, SEPosition_X, SEPosition_Y);
                                        }
                                    }
                                }
                            } else {
                                if (Check_To_X == Check_From_X) {
                                    if (Check_To_Y > Check_From_Y) {
                                        for (Integer SEPosition_Y = Check_From_Y, SEPosition_X = Check_From_X; SEPosition_Y < Check_To_Y; SEPosition_Y++, SEPosition_X+=0) {
                                            if (!(piece instanceof King) && (piece instanceof Queen && ((Queen) piece).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), piece, Touched_Position_X, Touched_Position_Y, SEPosition_X, SEPosition_Y) ||
                                                    piece instanceof Knight && ((Knight) piece).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), piece, Touched_Position_X, Touched_Position_Y, SEPosition_X, SEPosition_Y) ||
                                                    piece instanceof Bishop && ((Bishop) piece).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), piece, Touched_Position_X, Touched_Position_Y, SEPosition_X, SEPosition_Y) ||
                                                    piece instanceof Pawn && ((Pawn) piece).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), piece, Touched_Position_X, Touched_Position_Y, SEPosition_X, SEPosition_Y))) {
                                                paint.setColor(SEPosition_Y % 2 == 0 && SEPosition_X % 2 == 1 || SEPosition_Y % 2 == 1 && SEPosition_X % 2 == 0 ? Color.rgb(204, 204, 0) : Color.rgb(255, 255, 51));
                                                canvas.drawRect(rectangle[SEPosition_X][SEPosition_Y], paint);
                                                DrawPiece(canvas, SEPosition_X, SEPosition_Y);
                                            }
                                        }
                                    } else {
                                        for (Integer SEPosition_Y = Check_From_Y, SEPosition_X = Check_From_X; SEPosition_Y > Check_To_Y; SEPosition_Y--, SEPosition_X*=1) {
                                            if (!(piece instanceof King) && (piece instanceof Queen && ((Queen) piece).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), piece, Touched_Position_X, Touched_Position_Y, SEPosition_X, SEPosition_Y) ||
                                                    piece instanceof Knight && ((Knight) piece).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), piece, Touched_Position_X, Touched_Position_Y, SEPosition_X, SEPosition_Y) ||
                                                    piece instanceof Bishop && ((Bishop) piece).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), piece, Touched_Position_X, Touched_Position_Y, SEPosition_X, SEPosition_Y) ||
                                                    piece instanceof Pawn && ((Pawn) piece).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), piece, Touched_Position_X, Touched_Position_Y, SEPosition_X, SEPosition_Y))) {
                                                paint.setColor(SEPosition_Y % 2 == 0 && SEPosition_X % 2 == 1 || SEPosition_Y % 2 == 1 && SEPosition_X % 2 == 0 ? Color.rgb(204, 204, 0) : Color.rgb(255, 255, 51));
                                                canvas.drawRect(rectangle[SEPosition_X][SEPosition_Y], paint);
                                                DrawPiece(canvas, SEPosition_X, SEPosition_Y);
                                            }
                                        }
                                    }
                                } else if (Check_To_Y == Check_From_Y) {
                                    if (Check_To_X > Check_From_X) {
                                        for (Integer SEPosition_X = Check_From_X, SEPosition_Y = Check_From_Y; SEPosition_X < Check_To_X; SEPosition_X++, SEPosition_Y+=0) {
                                            if (!(piece instanceof King) && (piece instanceof Queen && ((Queen) piece).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), piece, Touched_Position_X, Touched_Position_Y, SEPosition_X, SEPosition_Y) ||
                                                    piece instanceof Knight && ((Knight) piece).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), piece, Touched_Position_X, Touched_Position_Y, SEPosition_X, SEPosition_Y) ||
                                                    piece instanceof Bishop && ((Bishop) piece).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), piece, Touched_Position_X, Touched_Position_Y, SEPosition_X, SEPosition_Y) ||
                                                    piece instanceof Pawn && ((Pawn) piece).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), piece, Touched_Position_X, Touched_Position_Y, SEPosition_X, SEPosition_Y))) {
                                                paint.setColor(SEPosition_Y % 2 == 0 && SEPosition_X % 2 == 1 || SEPosition_Y % 2 == 1 && SEPosition_X % 2 == 0 ? Color.rgb(204, 204, 0) : Color.rgb(255, 255, 51));
                                                canvas.drawRect(rectangle[SEPosition_X][SEPosition_Y], paint);
                                                DrawPiece(canvas, SEPosition_X, SEPosition_Y);
                                            }
                                        }
                                    } else {
                                        for (Integer SEPosition_X = Check_From_X, SEPosition_Y = Check_From_Y; SEPosition_X > Check_To_X; SEPosition_X--, SEPosition_Y*=1) {
                                            if (!(piece instanceof King) && (piece instanceof Queen && ((Queen) piece).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), piece, Touched_Position_X, Touched_Position_Y, SEPosition_X, SEPosition_Y) ||
                                                    piece instanceof Knight && ((Knight) piece).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), piece, Touched_Position_X, Touched_Position_Y, SEPosition_X, SEPosition_Y) ||
                                                    piece instanceof Bishop && ((Bishop) piece).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), piece, Touched_Position_X, Touched_Position_Y, SEPosition_X, SEPosition_Y) ||
                                                    piece instanceof Pawn && ((Pawn) piece).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), piece, Touched_Position_X, Touched_Position_Y, SEPosition_X, SEPosition_Y))) {
                                                paint.setColor(SEPosition_Y % 2 == 0 && SEPosition_X % 2 == 1 || SEPosition_Y % 2 == 1 && SEPosition_X % 2 == 0 ? Color.rgb(204, 204, 0) : Color.rgb(255, 255, 51));
                                                canvas.drawRect(rectangle[SEPosition_X][SEPosition_Y], paint);
                                                DrawPiece(canvas, SEPosition_X, SEPosition_Y);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        // Only King Legal Move Draw
                        for (Integer SPosition_Y = 0; SPosition_Y < halfchess.getWidthBoard(); SPosition_Y++) {
                            for (Integer SPosition_X = 0; SPosition_X < halfchess.getHeightBoard(); SPosition_X++) {
                                if (piece instanceof King && ((King) piece).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), piece, Touched_Position_X, Touched_Position_Y, SPosition_X, SPosition_Y)) {
                                    paint.setColor(SPosition_Y % 2 == 0 && SPosition_X % 2 == 1 || SPosition_Y % 2 == 1 && SPosition_X % 2 == 0 ? Color.rgb(204, 204, 0) : Color.rgb(255, 255, 51));
                                    canvas.drawRect(rectangle[SPosition_X][SPosition_Y], paint);
                                    DrawPiece(canvas, SPosition_X, SPosition_Y);
                                }
                            }
                        }
                    }
                }
                // Draw Piece Back
                DrawPiece(canvas, Position_X, Position_Y);
            }
        }
        // Draw Red Zone if King in Check
        if (halfchess.isCheck()) {
            paint.setColor(Check_To_Y % 2 == 0 && Check_To_X % 2 == 1 || Check_To_Y % 2 == 1 && Check_To_X % 2 == 0 ? Color.rgb(204, 0, 0) : Color.rgb(255, 51, 51));
            canvas.drawRect(rectangle[Check_To_X][Check_To_Y], paint);
            Drawable drawable = halfchess.getCurrentBoard()[Check_To_X][Check_To_Y].getTeamColor() == Color.BLACK ? drawablePiece[0] : drawablePiece[1];
            drawable.setBounds(rectangle[Check_To_X][Check_To_Y].left + 15, rectangle[Check_To_X][Check_To_Y].top + 15, rectangle[Check_To_X][Check_To_Y].right - 15, rectangle[Check_To_X][Check_To_Y].bottom - 15);
            drawable.draw(canvas);
        }

        paint.setColor(Color.BLACK);
        paint.setTextSize(72);
//        canvas.drawText("Style.STROKE", 200, 6310, paint);
//        canvas.drawText("Turn = " + (halfchess.getTurnColor() == Color.WHITE ? "White" : "Black"), 300, 135, paint);
        String textTurn = "Turn = " + (halfchess.getTurnColor() == Color.WHITE ? "White" : "Black");
        canvas.drawText(textTurn, this.getPivotX() / 2 + textTurn.length() * 5, 120, paint);

//        if (DrawMode == 1) { // Hold and Show Legal Move of Piece
////            paint.setStyle(Paint.Style.FILL);
//            if (piece instanceof Pawn) {
//                for (Integer Position_Y = 0; Position_Y < halfchess.getWidthBoard(); Position_Y++) {
//                    for (Integer Position_X = 0; Position_X < halfchess.getHeightBoard(); Position_X++) {
//                        //if (piece.Rule(halfchess.getTurn(), ))
//                        if (((Pawn)piece).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), piece, Touched_Position_X, Touched_Position_Y, Position_X, Position_Y)) {
////                            Rect LegalRect = new Rect();
////                            LegalRect.left = 150 * Position_Y + 150;
////                            LegalRect.top = 150 * Position_X + 150;
////                            LegalRect.right = LegalRect.left + 150;
////                            LegalRect.bottom = LegalRect.top + 150;
//                            paint.setColor(Position_Y % 2 == 0 && (Position_X - 2) % 2 == 1 || Position_Y % 2 == 1 && (Position_X - 2) % 2 == 0 ? Color.rgb(204,204,0) : Color.rgb(255,255,51));
////                            canvas.drawRect(rectangle[Touched_Position_X - 2][Touched_Position_Y], paint);
//                            canvas.drawRect(rectangle[Position_X][Position_Y], paint);
////                            canvas.drawRect(LegalRect, paint);
//                        }
//                    }
//                }
////                if (piece.getTeamColor() == Color.WHITE && Touched_Position_X > 0 && halfchess.getTurnColor() == Color.WHITE) {
////                    if (Touched_Position_X == 6) {
//////                        paint.setColor(Touched_Position_Y % 2 == 0 && (Touched_Position_X - 2) % 2 == 1 || Touched_Position_Y % 2 == 1 && (Touched_Position_X - 2) % 2 == 0 ? Color.rgb(255,153,51) : Color.rgb(204,102,0));
////                        paint.setColor(Touched_Position_Y % 2 == 0 && (Touched_Position_X - 2) % 2 == 1 || Touched_Position_Y % 2 == 1 && (Touched_Position_X - 2) % 2 == 0 ? Color.rgb(204,204,0) : Color.rgb(255,255,51));
////                        canvas.drawRect(rectangle[Touched_Position_X - 2][Touched_Position_Y], paint);
////                    }
//////                    paint.setColor(Touched_Position_Y % 2 == 0 && (Touched_Position_X - 1) % 2 == 1 || Touched_Position_Y % 2 == 1 && (Touched_Position_X - 1) % 2 == 0 ? Color.rgb(255,153,51) : Color.rgb(204,102,0));
////                    paint.setColor(Touched_Position_Y % 2 == 0 && (Touched_Position_X - 1) % 2 == 1 || Touched_Position_Y % 2 == 1 && (Touched_Position_X - 1) % 2 == 0 ? Color.rgb(204,204,0) : Color.rgb(255,255,51));
////                    canvas.drawRect(rectangle[Touched_Position_X - 1][Touched_Position_Y], paint);
////                } else if (piece.getTeamColor() == Color.BLACK && Touched_Position_X < halfchess.getHeightBoard() - 1 && halfchess.getTurnColor() == Color.BLACK) {
////                    if (Touched_Position_X == 1) {
////                        paint.setColor(Touched_Position_Y % 2 == 0 && (Touched_Position_X + 2) % 2 == 1 || Touched_Position_Y % 2 == 1 && (Touched_Position_X + 2) % 2 == 0 ? Color.rgb(204,204,0) : Color.rgb(255,255,51));
////                        canvas.drawRect(rectangle[Touched_Position_X + 2][Touched_Position_Y], paint);
////                    }
////                    paint.setColor(Touched_Position_Y % 2 == 0 && (Touched_Position_X + 1) % 2 == 1 || Touched_Position_Y % 2 == 1 && (Touched_Position_X + 1) % 2 == 0 ? Color.rgb(204,204,0) : Color.rgb(255,255,51));
////                    canvas.drawRect(rectangle[Touched_Position_X + 1][Touched_Position_Y], paint);
////                }
//            }
//        }
    }

    private void DrawPiece(Canvas canvas, Integer Position_X, Integer Position_Y) {
        if (halfchess.getCurrentBoard()[Position_X][Position_Y] != null) {
            Drawable drawable = null;
            if (halfchess.getCurrentBoard()[Position_X][Position_Y] instanceof King)
                drawable = halfchess.getCurrentBoard()[Position_X][Position_Y].getTeamColor() == Color.BLACK ? drawablePiece[0] : drawablePiece[1];
            else if (halfchess.getCurrentBoard()[Position_X][Position_Y] instanceof Queen)
                drawable = halfchess.getCurrentBoard()[Position_X][Position_Y].getTeamColor() == Color.BLACK ? drawablePiece[2] : drawablePiece[3];
            else if (halfchess.getCurrentBoard()[Position_X][Position_Y] instanceof Knight)
                drawable = halfchess.getCurrentBoard()[Position_X][Position_Y].getTeamColor() == Color.BLACK ? drawablePiece[4] : drawablePiece[5];
            else if (halfchess.getCurrentBoard()[Position_X][Position_Y] instanceof Bishop)
                drawable = halfchess.getCurrentBoard()[Position_X][Position_Y].getTeamColor() == Color.BLACK ? drawablePiece[6] : drawablePiece[7];
            else if (halfchess.getCurrentBoard()[Position_X][Position_Y] instanceof Pawn)
                drawable = halfchess.getCurrentBoard()[Position_X][Position_Y].getTeamColor() == Color.BLACK ? drawablePiece[8] : drawablePiece[9];
            drawable.setBounds(rectangle[Position_X][Position_Y].left + 15, rectangle[Position_X][Position_Y].top + 15, rectangle[Position_X][Position_Y].right - 15, rectangle[Position_X][Position_Y].bottom - 15);
            drawable.draw(canvas);
        }
    }

//    private void SetLegalSquare() {
//
//    }

//    private void OnDrawMode (Integer mode, Canvas canvas) {
//
//    }


}
