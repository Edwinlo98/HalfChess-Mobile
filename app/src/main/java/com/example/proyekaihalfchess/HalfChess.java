package com.example.proyekaihalfchess;

import android.content.Context;
import android.graphics.Color;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;
import java.util.Stack;

import java.lang.reflect.Field;

import java.lang.Object;
class HalfChess {
    static final int widthBoard = 4, heightBoard = 8;
    //char[][] currentBoard = new char[heightBoard][widthBoard];
//    Object[][] Board = new Object[heightBoard][widthBoard];
//    Field[][] BoardField = new Field[heightBoard][widthBoard];
    Piece[][] currentBoard = new Piece[heightBoard][widthBoard];
//    HashMap<Integer, Piece> currentBoard = new HashMap<Integer, Piece>();
//    HashMap<Integer, Piece> currentBoard = new HashMap<Integer, Piece>();
    //Map
    Coordinate coordinate;
    enum Coordinate{
        Position_X, Position_Y;
        Integer value;
//        Coordinate() {
//            this.value = value;
//        }
        public Integer getPosition() {
            return value;
        }
        public void setPosition(Integer value) {
            this.value = value;
        }
    }
//    HashMap<Coordinate, Piece> currentBoard = new HashMap<Coordinate, Piece>();
    Stack possibleMoves;
    int turnColor;
    boolean Check;

//    public Coordinate getCoordinate() {
//        return coordinate;
//    }
//
//    public void setCoordinate(Coordinate coordinate) {
//        this.coordinate = coordinate;
//    }

    //    List<Piece> hcBoard = new List<Piece>() {
//        @Override
//        public int size() {
//            return 0;
//        }
//
//        @Override
//        public boolean isEmpty() {
//            return false;
//        }
//
//        @Override
//        public boolean contains(@Nullable Object o) {
//            return false;
//        }
//
//        @NonNull
//        @Override
//        public Iterator<Piece> iterator() {
//            return null;
//        }
//
//        @NonNull
//        @Override
//        public Object[] toArray() {
//            return new Object[0];
//        }
//
//        @NonNull
//        @Override
//        public <T> T[] toArray(@NonNull T[] a) {
//            return null;
//        }
//
//        @Override
//        public boolean add(Piece piece) {
//            return false;
//        }
//
//        @Override
//        public boolean remove(@Nullable Object o) {
//            return false;
//        }
//
//        @Override
//        public boolean containsAll(@NonNull Collection<?> c) {
//            return false;
//        }
//
//        @Override
//        public boolean addAll(@NonNull Collection<? extends Piece> c) {
//            return false;
//        }
//
//        @Override
//        public boolean addAll(int index, @NonNull Collection<? extends Piece> c) {
//            return false;
//        }
//
//        @Override
//        public boolean removeAll(@NonNull Collection<?> c) {
//            return false;
//        }
//
//        @Override
//        public boolean retainAll(@NonNull Collection<?> c) {
//            return false;
//        }
//
//        @Override
//        public void clear() {
//
//        }
//
//        @Override
//        public Piece get(int index) {
//            return null;
//        }
//
//        @Override
//        public Piece set(int index, Piece element) {
//            return null;
//        }
//
//        @Override
//        public void add(int index, Piece element) {
//
//        }
//
//        @Override
//        public Piece remove(int index) {
//            return null;
//        }
//
//        @Override
//        public int indexOf(@Nullable Object o) {
//            return 0;
//        }
//
//        @Override
//        public int lastIndexOf(@Nullable Object o) {
//            return 0;
//        }
//
//        @NonNull
//        @Override
//        public ListIterator<Piece> listIterator() {
//            return null;
//        }
//
//        @NonNull
//        @Override
//        public ListIterator<Piece> listIterator(int index) {
//            return null;
//        }
//
//        @NonNull
//        @Override
//        public List<Piece> subList(int fromIndex, int toIndex) {
//            return null;
//        }
//    }

//    private Object Objects;
    //private Object Objects;
    //private java.util.Object Object;
//    public HalfChess(Stack possibleMoves) {
//        this.possibleMoves = possibleMoves;
//

    public HalfChess() {
        InitPieceBoard();
        //this.turnColor = Color.WHITE;
        this.setTurnColor(Color.WHITE);
        this.setCheck(false);
    }

    private Context context;
    public HalfChess(Context context) {
        this.context = context.getApplicationContext();
    }

    public static int getWidthBoard() {
        return widthBoard;
    }

    public static int getHeightBoard() {
        return heightBoard;
    }

    public Piece[][] getCurrentBoard() {
        return currentBoard;
    }

    public void setCurrentBoard(Piece[][] currentBoard) {
        this.currentBoard = currentBoard;
    }

    public int getTurnColor() {
        return turnColor;
    }

    public void setTurnColor(int turnColor) {
        this.turnColor = turnColor;
    }

    public boolean isCheck() {
        return Check;
    }

    public void setCheck(boolean check) {
        Check = check;
    }

    //    public HashMap<Coordinate, Piece> getCurrentBoard() {
//        return currentBoard;
//    }
//
//    public void setCurrentBoard(HashMap<Coordinate, Piece> currentBoard) {
//        this.currentBoard = currentBoard;
//    }

//    public HashMap<Integer, Piece> getCurrentBoard() {
//        return currentBoard;
//    }
//
//    public void setCurrentBoard(HashMap<Integer, Piece> currentBoard) {
//        this.currentBoard = currentBoard;
//    }

//    public HashMap<Color, Piece> getCurrentBoard() {
//        return currentBoard;
//    }
//
//    public void setCurrentBoard(HashMap<Color, Piece> currentBoard) {
//        this.currentBoard = currentBoard;
//    }

    private void InitPieceBoard() {
        Integer PieceColor = 0;
        Piece PieceType;
        Integer[][] SquarePosition;
        for (int i = 0; i < this.getHeightBoard(); i++) {
            for (int j = 0; j < this.getWidthBoard(); j++) {
                //Context context;
                //Toast.makeText(HalfChess.this.context,"ihc = "+i, Toast.LENGTH_SHORT).show();
                //Toast.makeText(HalfChess.this.context,"jhc = "+j, Toast.LENGTH_SHORT).show();
                if (i <= 1) PieceColor = Color.BLACK;
                else if (i >= 6) PieceColor = Color.WHITE;
                if (i == 0 || i == 7) {
                    if (j == 0) PieceType = new King(PieceColor);
                    else if (j == 1) PieceType = new Queen(PieceColor);
                    else if (j == 2) PieceType = new Knight(PieceColor);
                    else PieceType = new Bishop(PieceColor);
                }
                else if (i == 1 || i == 6) {
                    PieceType = new Pawn(PieceColor);
                }
                else PieceType = null;
                currentBoard[i][j] = PieceType;
                //currentBoard.put(PieceType.getTeamColor(), PieceType);
//                if (i < 2 || i > 5)
//                if (i == 1 || i == 6) currentBoard.put(PieceType.getTeamColor(), PieceType);

                //SquarePosition[i][j]=i;
//                coordinate.Position_X.setPosition(j);
//                coordinate.Position_Y.setPosition(i);
//                currentBoard.put(coordinate, PieceType);
                //currentBoard.put(PieceType.getTeamColor(), PieceType);
            }
        }
//        for (Map.Entry<Character, Piece> board : currentBoard.entrySet()) {
//
//        }
    }

    Integer CountLegalMove(HalfChess halfchess, Integer From_Position_X, Integer From_Position_Y) {
        Integer Count_Legal_Move = 0;
        for (Integer Position_Y = 0; Position_Y < getWidthBoard(); Position_Y++) {
            for (Integer Position_X = 0; Position_X < getHeightBoard(); Position_X++) {
                if (halfchess.getCurrentBoard()[From_Position_X][From_Position_Y] instanceof King && ((King)halfchess.getCurrentBoard()[From_Position_X][From_Position_Y]).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[From_Position_X][From_Position_Y], From_Position_X, From_Position_Y, Position_X, Position_Y) ||
                    halfchess.getCurrentBoard()[From_Position_X][From_Position_Y] instanceof Queen && ((Queen)halfchess.getCurrentBoard()[From_Position_X][From_Position_Y]).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[From_Position_X][From_Position_Y], From_Position_X, From_Position_Y, Position_X, Position_Y) ||
                    halfchess.getCurrentBoard()[From_Position_X][From_Position_Y] instanceof Knight && ((Knight)halfchess.getCurrentBoard()[From_Position_X][From_Position_Y]).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[From_Position_X][From_Position_Y], From_Position_X, From_Position_Y, Position_X, Position_Y) ||
                    halfchess.getCurrentBoard()[From_Position_X][From_Position_Y] instanceof Bishop && ((Bishop)halfchess.getCurrentBoard()[From_Position_X][From_Position_Y]).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[From_Position_X][From_Position_Y], From_Position_X, From_Position_Y, Position_X, Position_Y) ||
                    halfchess.getCurrentBoard()[From_Position_X][From_Position_Y] instanceof Pawn && ((Pawn)halfchess.getCurrentBoard()[From_Position_X][From_Position_Y]).Rule(halfchess.getTurnColor(), halfchess.getCurrentBoard(), halfchess.getCurrentBoard()[From_Position_X][From_Position_Y], From_Position_X, From_Position_Y, Position_X, Position_Y)) {
                    ++Count_Legal_Move;
                }
            }
        }
        return Count_Legal_Move;
    }

}

class Piece {
    protected Integer teamColor;
//    protected char symbolPiece;
    protected boolean allowMove;

    Piece(Integer color) {
        this.setTeamColor(color);
        this.allowMove = true;
    }

    public Integer getTeamColor() {
        return teamColor;
    }

    public void setTeamColor(Integer teamColor) {
        this.teamColor = teamColor;
    }

    public boolean isAllowMove() {
        return allowMove;
    }

    public void setAllowMove(boolean allowMove) {
        this.allowMove = allowMove;
    }

//    public char getSymbolPiece() {
//        return symbolPiece;
//    }
//
//    public void setSymbolPiece(char symbolPiece) {
//        this.symbolPiece = symbolPiece;
//    }

}

class King extends Piece {
    King(Integer color) {
        super(color);
        //this.setSymbolPiece('k');
//        this.teamColor = getTeamColor();
    }

    boolean Rule(int turn, Piece[][] board, Piece myPiece, int From_Position_X, int From_Position_Y, int To_Position_X, int To_Position_Y) {
        if (Math.abs(To_Position_X - From_Position_X) == 1 && Math.abs(To_Position_Y - From_Position_Y) <= 1 || Math.abs(To_Position_Y - From_Position_Y) == 1 && Math.abs(To_Position_X - From_Position_X) <= 1) {
            if (myPiece.getTeamColor() == Color.WHITE && turn == Color.WHITE && (board[To_Position_X][To_Position_Y] == null || board[To_Position_X][To_Position_Y] != null && board[To_Position_X][To_Position_Y].getTeamColor() != Color.WHITE)) {
                // Check Mobility Enemy Piece
                for (Integer Position_Y = 0; Position_Y < HalfChess.getWidthBoard(); Position_Y++) {
                    for (Integer Position_X = 0; Position_X < HalfChess.getHeightBoard(); Position_X++) {
                        if (board[Position_X][Position_Y] != null && board[Position_X][Position_Y].getTeamColor() != Color.WHITE) {
                            if (board[Position_X][Position_Y] instanceof Pawn && ((Pawn) board[Position_X][Position_Y]).Rule(Color.BLACK, board, board[Position_X][Position_Y], Position_X, Position_Y, To_Position_X, To_Position_Y) ||
                                    board[Position_X][Position_Y] instanceof King && ((King) board[Position_X][Position_Y]).Rule(Color.BLACK, board, board[Position_X][Position_Y], Position_X, Position_Y, To_Position_X, To_Position_Y) ||
                                    board[Position_X][Position_Y] instanceof Knight && ((Knight) board[Position_X][Position_Y]).Rule(Color.BLACK, board, board[Position_X][Position_Y], Position_X, Position_Y, To_Position_X, To_Position_Y) ||
                                    board[Position_X][Position_Y] instanceof Bishop && ((Bishop) board[Position_X][Position_Y]).Rule(Color.BLACK, board, board[Position_X][Position_Y], Position_X, Position_Y, To_Position_X, To_Position_Y) ||
                                    board[Position_X][Position_Y] instanceof Queen && ((Queen) board[Position_X][Position_Y]).Rule(Color.BLACK, board, board[Position_X][Position_Y], Position_X, Position_Y, To_Position_X, To_Position_Y)) {
                                return false;
                            }
                        }
                    }
                }
                return true;
            } else if (myPiece.getTeamColor() == Color.BLACK && turn == Color.BLACK && (board[To_Position_X][To_Position_Y] == null || board[To_Position_X][To_Position_Y] != null && board[To_Position_X][To_Position_Y].getTeamColor() != Color.BLACK)) {
                // Check Mobility Enemy Piece
                for (Integer Position_Y = 0; Position_Y < HalfChess.getWidthBoard(); Position_Y++) {
                    for (Integer Position_X = 0; Position_X < HalfChess.getHeightBoard(); Position_X++) {
                        if (board[Position_X][Position_Y] != null && board[Position_X][Position_Y].getTeamColor() != Color.BLACK) {
                            if (board[Position_X][Position_Y] instanceof Pawn && ((Pawn) board[Position_X][Position_Y]).Rule(Color.WHITE, board, board[Position_X][Position_Y], Position_X, Position_Y, To_Position_X, To_Position_Y) ||
                                    board[Position_X][Position_Y] instanceof King && ((King) board[Position_X][Position_Y]).Rule(Color.WHITE, board, board[Position_X][Position_Y], Position_X, Position_Y, To_Position_X, To_Position_Y) ||
                                    board[Position_X][Position_Y] instanceof Knight && ((Knight) board[Position_X][Position_Y]).Rule(Color.WHITE, board, board[Position_X][Position_Y], Position_X, Position_Y, To_Position_X, To_Position_Y) ||
                                    board[Position_X][Position_Y] instanceof Bishop && ((Bishop) board[Position_X][Position_Y]).Rule(Color.WHITE, board, board[Position_X][Position_Y], Position_X, Position_Y, To_Position_X, To_Position_Y) ||
                                    board[Position_X][Position_Y] instanceof Queen && ((Queen) board[Position_X][Position_Y]).Rule(Color.WHITE, board, board[Position_X][Position_Y], Position_X, Position_Y, To_Position_X, To_Position_Y)) {
                                return false;
                            }
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }
}

class Queen extends Piece {
    Queen(Integer color) {
        super(color);
    }

    boolean Rule(int turn, Piece[][] board, Piece myPiece, int From_Position_X, int From_Position_Y, int To_Position_X, int To_Position_Y) {
        if (myPiece.isAllowMove() && CrossDiagonalSquare(board, From_Position_X, From_Position_Y, From_Position_X, From_Position_Y, To_Position_X, To_Position_Y) && (Math.abs(To_Position_Y - From_Position_Y) == Math.abs(To_Position_X - From_Position_X) && Math.abs(To_Position_Y - From_Position_Y) > 0 && Math.abs(To_Position_X - From_Position_X) > 0 || To_Position_Y - From_Position_Y == 0 && Math.abs(To_Position_X - From_Position_X) > 0 || Math.abs(To_Position_Y - From_Position_Y) > 0 && To_Position_X - From_Position_X == 0)) {
            if (myPiece.getTeamColor() == Color.WHITE && turn == Color.WHITE && (board[To_Position_X][To_Position_Y] == null || board[To_Position_X][To_Position_Y] != null && board[To_Position_X][To_Position_Y].getTeamColor() != Color.WHITE)) {
                return true;
            } else if (myPiece.getTeamColor() == Color.BLACK && turn == Color.BLACK && (board[To_Position_X][To_Position_Y] == null || board[To_Position_X][To_Position_Y] != null && board[To_Position_X][To_Position_Y].getTeamColor() != Color.BLACK)) {
                return true;
            }
        }
        return false;
    }

    boolean RuleInCheck() {
        return false;
    }

    boolean CrossDiagonalSquare(Piece[][] board, int From_Position_X, int From_Position_Y, int Current_Position_X, int Current_Position_Y, int To_Position_X, int To_Position_Y) {
        if (To_Position_X != Current_Position_X || To_Position_Y != Current_Position_Y) {
            if (Current_Position_X != From_Position_X || Current_Position_Y != From_Position_Y) {
                if (board[Current_Position_X][Current_Position_Y] != null && (!(board[Current_Position_X][Current_Position_Y] instanceof King) || board[Current_Position_X][Current_Position_Y] instanceof King && board[Current_Position_X][Current_Position_Y].getTeamColor() == board[From_Position_X][From_Position_Y].getTeamColor())) {
                    return false;
                }
            }
            if (To_Position_X < Current_Position_X && To_Position_Y > Current_Position_Y)
                return CrossDiagonalSquare(board, From_Position_X, From_Position_Y, Current_Position_X - 1, Current_Position_Y + 1, To_Position_X, To_Position_Y);
            else if (To_Position_X > Current_Position_X && To_Position_Y > Current_Position_Y)
                return CrossDiagonalSquare(board, From_Position_X, From_Position_Y, Current_Position_X + 1, Current_Position_Y + 1, To_Position_X, To_Position_Y);
            else if (To_Position_X > Current_Position_X && To_Position_Y < Current_Position_Y)
                return CrossDiagonalSquare(board, From_Position_X, From_Position_Y, Current_Position_X + 1, Current_Position_Y - 1, To_Position_X, To_Position_Y);
            else if (To_Position_X < Current_Position_X && To_Position_Y < Current_Position_Y)
                return CrossDiagonalSquare(board, From_Position_X, From_Position_Y, Current_Position_X - 1, Current_Position_Y - 1, To_Position_X, To_Position_Y);
            else if (To_Position_X == Current_Position_X && To_Position_Y < Current_Position_Y)
                return CrossDiagonalSquare(board, From_Position_X, From_Position_Y, Current_Position_X, Current_Position_Y - 1, To_Position_X, To_Position_Y);
            else if (To_Position_X == Current_Position_X && To_Position_Y > Current_Position_Y)
                return CrossDiagonalSquare(board, From_Position_X, From_Position_Y, Current_Position_X, Current_Position_Y + 1, To_Position_X, To_Position_Y);
            else if (To_Position_X < Current_Position_X && To_Position_Y == Current_Position_Y)
                return CrossDiagonalSquare(board, From_Position_X, From_Position_Y, Current_Position_X - 1, Current_Position_Y, To_Position_X, To_Position_Y);
            else if (To_Position_X > Current_Position_X && To_Position_Y == Current_Position_Y)
                return CrossDiagonalSquare(board, From_Position_X, From_Position_Y, Current_Position_X + 1, Current_Position_Y, To_Position_X, To_Position_Y);
        }
        return true;
    }
}

class Knight extends Piece {
    Knight(Integer color) {
        super(color);
    }

    boolean Rule(int turn, Piece[][] board, Piece myPiece, int From_Position_X, int From_Position_Y, int To_Position_X, int To_Position_Y) {
        if (myPiece.isAllowMove() && Math.abs(To_Position_Y - From_Position_Y) == 1 && Math.abs(To_Position_X - From_Position_X) == 2 || Math.abs(To_Position_Y - From_Position_Y) == 2 && Math.abs(To_Position_X - From_Position_X) == 1) {
            if (myPiece.getTeamColor() == Color.WHITE && turn == Color.WHITE && (board[To_Position_X][To_Position_Y] == null || board[To_Position_X][To_Position_Y] != null && board[To_Position_X][To_Position_Y].getTeamColor() != Color.WHITE)) {
                return true;
            } else if (myPiece.getTeamColor() == Color.BLACK && turn == Color.BLACK && (board[To_Position_X][To_Position_Y] == null || board[To_Position_X][To_Position_Y] != null && board[To_Position_X][To_Position_Y].getTeamColor() != Color.BLACK)) {
                return true;
            }
        }
        return false;
    }
}

class Bishop extends Piece {
    Bishop(Integer color) {
        super(color);
    }

    boolean Rule(int turn, Piece[][] board, Piece myPiece, int From_Position_X, int From_Position_Y, int To_Position_X, int To_Position_Y) {
        if (myPiece.isAllowMove() && DiagonalSquare(board, From_Position_X, From_Position_Y, From_Position_X, From_Position_Y, To_Position_X, To_Position_Y) && Math.abs(To_Position_Y - From_Position_Y) == Math.abs(To_Position_X - From_Position_X) && Math.abs(To_Position_Y - From_Position_Y) > 0 && Math.abs(To_Position_X - From_Position_X) > 0) {
            if (myPiece.getTeamColor() == Color.WHITE && turn == Color.WHITE && (board[To_Position_X][To_Position_Y] == null || board[To_Position_X][To_Position_Y] != null && board[To_Position_X][To_Position_Y].getTeamColor() != Color.WHITE)) {
                return true;
            } else if (myPiece.getTeamColor() == Color.BLACK && turn == Color.BLACK && (board[To_Position_X][To_Position_Y] == null || board[To_Position_X][To_Position_Y] != null && board[To_Position_X][To_Position_Y].getTeamColor() != Color.BLACK)) {
                return true;
            }
        }
        return false;
    }

    boolean DiagonalSquare(Piece[][] board, int From_Position_X, int From_Position_Y, int Current_Position_X, int Current_Position_Y, int To_Position_X, int To_Position_Y) {
        if (To_Position_X != Current_Position_X && To_Position_Y != Current_Position_Y) {
            if (Current_Position_X != From_Position_X && Current_Position_Y != From_Position_Y) {
                if (board[Current_Position_X][Current_Position_Y] != null && (!(board[Current_Position_X][Current_Position_Y] instanceof King) || board[Current_Position_X][Current_Position_Y] instanceof King && board[Current_Position_X][Current_Position_Y].getTeamColor() == board[From_Position_X][From_Position_Y].getTeamColor())) {
                    return false;
                }
            }
            if (To_Position_X < Current_Position_X && To_Position_Y > Current_Position_Y)
                return DiagonalSquare(board, From_Position_X, From_Position_Y, Current_Position_X - 1, Current_Position_Y + 1, To_Position_X, To_Position_Y);
            else if (To_Position_X > Current_Position_X && To_Position_Y > Current_Position_Y)
                return DiagonalSquare(board, From_Position_X, From_Position_Y, Current_Position_X + 1, Current_Position_Y + 1, To_Position_X, To_Position_Y);
            else if (To_Position_X > Current_Position_X && To_Position_Y < Current_Position_Y)
                return DiagonalSquare(board, From_Position_X, From_Position_Y, Current_Position_X + 1, Current_Position_Y - 1, To_Position_X, To_Position_Y);
            else
                return DiagonalSquare(board, From_Position_X, From_Position_Y, Current_Position_X - 1, Current_Position_Y - 1, To_Position_X, To_Position_Y);
        }
        return true;
    }
}

class Pawn extends Piece {
    boolean enPassant;
    Pawn(Integer color) {
        super(color);
        this.setEnPassant(false);
    }

    public boolean isEnPassant() {
        return enPassant;
    }

    public void setEnPassant(boolean enPassant) {
        this.enPassant = enPassant;
    }

    boolean Rule(int turn, Piece[][] board, Piece myPiece, int From_Position_X, int From_Position_Y, int To_Position_X, int To_Position_Y) {
        if (myPiece.isAllowMove() && myPiece.getTeamColor() == Color.WHITE && turn == Color.WHITE && From_Position_X >= 0) {
            // Eat Piece Rule
            if (board[To_Position_X][To_Position_Y] != null && board[To_Position_X][To_Position_Y].getTeamColor() != Color.WHITE && To_Position_X == From_Position_X - 1 && Math.abs(To_Position_Y - From_Position_Y) == 1) {
                return true;
            }

            // En Passant Rule
            if (From_Position_X == 3 && board[From_Position_X][To_Position_Y] != null && board[From_Position_X][To_Position_Y] instanceof Pawn && board[From_Position_X][To_Position_Y].getTeamColor() != Color.WHITE && ((Pawn) board[From_Position_X][To_Position_Y]).isEnPassant() && Math.abs(To_Position_Y - From_Position_Y) == 1 && To_Position_X - From_Position_X == -1) {
                return true;
            }

            // Move Rule
            if ((From_Position_X == 6 && To_Position_X - From_Position_X == -2 || To_Position_X - From_Position_X == -1) && To_Position_Y - From_Position_Y == 0 && DoubleSquare(board, myPiece, From_Position_X - 1, From_Position_Y, To_Position_X)) {
                return true;
            }
        }
        else if (myPiece.isAllowMove() && myPiece.getTeamColor() == Color.BLACK && turn == Color.BLACK /*&& From_Position_X > 0*/ && From_Position_X <= HalfChess.getHeightBoard() - 1) {
            // Eat Piece Rule
            if (board[To_Position_X][To_Position_Y] != null && board[To_Position_X][To_Position_Y].getTeamColor() != Color.BLACK && To_Position_X == From_Position_X + 1 && Math.abs(To_Position_Y - From_Position_Y) == 1) {
                return true;
            }

            // En Passant Rule
            if (From_Position_X == 4 && board[From_Position_X][To_Position_Y] != null && board[From_Position_X][To_Position_Y] instanceof Pawn && board[From_Position_X][To_Position_Y].getTeamColor() != Color.BLACK && ((Pawn) board[From_Position_X][To_Position_Y]).isEnPassant() && Math.abs(To_Position_Y - From_Position_Y) == 1 && To_Position_X - From_Position_X == 1) {
                return true;
            }

            // Move Rule
            if ((From_Position_X == 1 && To_Position_X - From_Position_X == 2 || To_Position_X == From_Position_X + 1) && To_Position_Y - From_Position_Y == 0 && DoubleSquare(board, myPiece, From_Position_X + 1, From_Position_Y, To_Position_X)) {
                return true;
            }
        }
        return false;
    }

    boolean DoubleSquare(Piece[][] board, Piece myPiece, int From_Position_X, int From_Position_Y, int To_Position_X) {
        if (board[From_Position_X][From_Position_Y] != null) return false;
        if (From_Position_X == To_Position_X) return true;
        return DoubleSquare(board, myPiece, myPiece.getTeamColor() == Color.WHITE ? From_Position_X - 1 : From_Position_X + 1, From_Position_Y, To_Position_X);
    }

    private void PawnPromotion() {

    }

}


