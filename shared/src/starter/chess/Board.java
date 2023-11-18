package chess;

import static chess.ChessGame.TeamColor.WHITE;
import static chess.ChessGame.TeamColor.BLACK;

import java.util.HashMap;
import java.util.Map.Entry;


public class Board implements ChessBoard {
    private HashMap<ChessPosition, ChessPiece> piecePositions;

    public Board() {
        piecePositions = new HashMap<>();
    }

    @Override
    public void addPiece(ChessPosition position, ChessPiece piece) {
        piecePositions.put(position, piece);
    }

    public void removePiece(ChessPosition position) {
        piecePositions.remove(position);
    }

    @Override
    public ChessPiece getPiece(ChessPosition position) {
        for (Entry<ChessPosition, ChessPiece> piecePos : piecePositions.entrySet()) {
            if (position.equals(piecePos.getKey())) {
                return piecePos.getValue();
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
            addPiece(new Position(2, i), new Pawn(WHITE));
            addPiece(new Position(7, i), new Pawn(BLACK));
        }
    }

    private void setRooks() {
        addPiece(new Position(1, 1), new Rook(WHITE));
        addPiece(new Position(1, 8), new Rook(WHITE));
        addPiece(new Position(8, 1), new Rook(BLACK));
        addPiece(new Position(8, 8), new Rook(BLACK));
    }

    private void setKnights() {
        addPiece(new Position(1, 2), new Knight(WHITE));
        addPiece(new Position(1, 7), new Knight(WHITE));
        addPiece(new Position(8, 2), new Knight(BLACK));
        addPiece(new Position(8, 7), new Knight(BLACK));
    }

    private void setBishops() {
        addPiece(new Position(1, 3), new Bishop(WHITE));
        addPiece(new Position(1, 6), new Bishop(WHITE));
        addPiece(new Position(8, 3), new Bishop(BLACK));
        addPiece(new Position(8, 6), new Bishop(BLACK));
    }

    private void setQueens() {
        addPiece(new Position(1, 4), new Queen(WHITE));
        addPiece(new Position(8, 4), new Queen(BLACK));
    }

    private void setKings() {
        addPiece(new Position(1, 5), new King(WHITE));
        addPiece(new Position(8, 5), new King(BLACK));
    }
}
