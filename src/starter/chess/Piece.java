package chess;

import java.util.Collection;
import java.util.HashSet;

public abstract class Piece implements ChessPiece {
    private final ChessGame.TeamColor teamColor;
    protected PieceType pieceType;

    public Piece(ChessGame.TeamColor teamColor) {
        this.teamColor = teamColor;
    }

    @Override
    public ChessGame.TeamColor getTeamColor() {
        return teamColor;
    }

    @Override
    public PieceType getPieceType() {
        return pieceType;
    }

    @Override
    public abstract Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition);

    protected HashSet<ChessMove> getLongDistanceMoves(ChessBoard board, ChessPosition myPosition,
                                                      int rowVariation, int colVariation) {
        HashSet<ChessMove> longDistanceMoves = new HashSet<>();

        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessPosition newPosition = new Position(row, col);
        ChessPiece newPiece = board.getPiece(newPosition);

        int numIter = 0;
        while (!outOfBounds(newPosition)) {
            if (!newPosition.equals(myPosition)) {
                longDistanceMoves.addAll(getSingleMoves(board, myPosition,
                        rowVariation * numIter, colVariation * numIter));
                if (newPiece != null) {
                    break;
                }
            }
            numIter += 1;
            newPosition = new Position(row + rowVariation * numIter,
                    col + colVariation * numIter);
            newPiece = board.getPiece(newPosition);
        }
        return longDistanceMoves;
    }

    protected HashSet<ChessMove> getSingleMoves(ChessBoard board, ChessPosition myPosition,
                                                  int rowVariation, int colVariation) {
        HashSet<ChessMove> move = new HashSet<>();

        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        ChessPosition newPosition = new Position(row + rowVariation, col + colVariation);
        ChessPiece newPiece = board.getPiece(newPosition);
        ChessPiece myPiece = board.getPiece(myPosition);

        if (!outOfBounds(newPosition)) {
            if (newPiece == null) {
                move.add(new Move(myPosition, newPosition, null));
            } else {
                if (newPiece.getTeamColor() != myPiece.getTeamColor()) {
                    move.add(new Move(myPosition, newPosition, null));
                }
            }
        }
        return move;
    }

    protected boolean outOfBounds(ChessPosition position) {
        int row = position.getRow();
        int col = position.getColumn();

        return (row > 8) || (row < 1) || (col > 8) || (col < 1);
    }
}
