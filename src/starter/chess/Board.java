package chess;

import chess.pieces.*;

import java.util.HashMap;
import java.util.Map.Entry;

public class Board implements ChessBoard {
    private HashMap<ChessPiece, ChessPosition> piecePositions;

    public Board() {
        piecePositions = new HashMap<>();
        //resetBoard();
    }

    @Override
    public void addPiece(ChessPosition position, ChessPiece piece) {
        piecePositions.put(piece, position);
    }

    @Override
    public ChessPiece getPiece(ChessPosition position) {
        for (Entry<ChessPiece, ChessPosition> piecePos : piecePositions.entrySet()) {
            if (position.equals(piecePos.getValue())) {
                return piecePos.getKey();
            }
        }
        return null;
    }

    @Override
    public void resetBoard() {
        piecePositions = new HashMap<>();
        setPawns();
        setRooks();
        setKnights();
        setBishops();
        setQueens();
        setKings();
    }

    private void setPawns() {
        for (int i = 1; i < 9; i++) {
            addPiece(new Position(2, i), new Pawn(ChessGame.TeamColor.WHITE));
            addPiece(new Position(7, i), new Pawn(ChessGame.TeamColor.BLACK));
        }
    }

    private void setRooks() {
        addPiece(new Position(1, 1), new Rook(ChessGame.TeamColor.WHITE));
        addPiece(new Position(1, 8), new Rook(ChessGame.TeamColor.WHITE));
        addPiece(new Position(8, 1), new Rook(ChessGame.TeamColor.BLACK));
        addPiece(new Position(8, 8), new Rook(ChessGame.TeamColor.BLACK));
    }

    private void setKnights() {
        addPiece(new Position(1, 2), new Knight(ChessGame.TeamColor.WHITE));
        addPiece(new Position(1, 7), new Knight(ChessGame.TeamColor.WHITE));
        addPiece(new Position(8, 2), new Knight(ChessGame.TeamColor.BLACK));
        addPiece(new Position(8, 7), new Knight(ChessGame.TeamColor.BLACK));
    }

    private void setBishops() {
        addPiece(new Position(1, 3), new Bishop(ChessGame.TeamColor.WHITE));
        addPiece(new Position(1, 6), new Bishop(ChessGame.TeamColor.WHITE));
        addPiece(new Position(8, 3), new Bishop(ChessGame.TeamColor.BLACK));
        addPiece(new Position(8, 6), new Bishop(ChessGame.TeamColor.BLACK));
    }

    private void setQueens() {
        addPiece(new Position(1, 4), new Queen(ChessGame.TeamColor.WHITE));
        addPiece(new Position(8, 4), new Queen(ChessGame.TeamColor.BLACK));
    }

    private void setKings() {
        addPiece(new Position(1, 5), new King(ChessGame.TeamColor.WHITE));
        addPiece(new Position(8, 5), new King(ChessGame.TeamColor.BLACK));
    }

}
