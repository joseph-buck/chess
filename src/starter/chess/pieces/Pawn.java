package chess.pieces;

import chess.*;
import static chess.ChessGame.TeamColor.BLACK;
import static chess.ChessGame.TeamColor.WHITE;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;


public class Pawn extends Piece {

    public Pawn(ChessGame.TeamColor teamColor) {
        super(teamColor);
        this.pieceType = PieceType.valueOf("PAWN");
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new HashSet<>();

        moves.addAll(getForwardMoves(board, myPosition));
        moves.addAll(getDiagonalMoves(board, myPosition));

        return moves;
    }

    private HashSet<ChessMove> getForwardMoves(ChessBoard board, ChessPosition myPosition) {
        HashSet<ChessMove> forwardMoves = new HashSet<>();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        boolean whiteTeam = (getTeamColor() == WHITE);
        int startRow = whiteTeam ? 2 : 7;
        int direction = whiteTeam ? 1 : -1;

        Position newPosition = new Position(row + direction, col);
        if (board.getPiece(newPosition) == null) {
            if (((whiteTeam) && (row == 7)) || ((!whiteTeam) && (row == 2))) {
                forwardMoves.addAll(getPromotionMoves(myPosition, newPosition));
            } else {
                forwardMoves.add(new Move(myPosition, newPosition, null));
            }

            newPosition = new Position(row + 2 * direction, col);
            if ((board.getPiece(newPosition) == null) && (row == startRow)) {
                forwardMoves.add(new Move(myPosition, newPosition, null));
            }
        }
        return forwardMoves;
    }

    private HashSet<ChessMove> getPromotionMoves(ChessPosition myPosition, ChessPosition newPosition) {
        HashSet<ChessMove> promotionMoves = new HashSet<>();

        for (PieceType promotePiece : List.of(PieceType.ROOK, PieceType.KNIGHT,
                PieceType.BISHOP, PieceType.QUEEN)) {
            promotionMoves.add(new Move(myPosition, newPosition, promotePiece));
        }
        return promotionMoves;
    }

    private HashSet<ChessMove> getDiagonalMoves(
            ChessBoard board, ChessPosition myPosition) {
        HashSet<ChessMove> diagonalMoves = diagonalMove(board, myPosition, -1);
        diagonalMoves.addAll(diagonalMove(board, myPosition, 1));
        return diagonalMoves;
    }

    private HashSet<ChessMove> diagonalMove(
            ChessBoard board, ChessPosition myPosition, int horizontalMove) {
        HashSet<ChessMove> diagonalMoves = new HashSet<>();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        boolean whiteTeam = (getTeamColor() == WHITE);
        int direction = whiteTeam ? 1 : -1;

        Position newPosition = new Position(row + direction, col + horizontalMove);
        ChessPiece diagonalPiece = board.getPiece(newPosition);
        if (diagonalPiece != null) {
            if (((whiteTeam) && (row == 7) && (diagonalPiece.getTeamColor() == BLACK))
                    || ((!whiteTeam) && (row == 2) && (diagonalPiece.getTeamColor() == WHITE))) {
                diagonalMoves.addAll(getPromotionMoves(myPosition, newPosition));
            } else {
                if ((whiteTeam) && (diagonalPiece.getTeamColor() == BLACK)) {
                    diagonalMoves.add(new Move(myPosition, newPosition, null));
                } else if ((!whiteTeam) && (diagonalPiece.getTeamColor() == WHITE)) {
                    diagonalMoves.add(new Move(myPosition, newPosition, null));
                }
            }
        }
        return diagonalMoves;
    }
}
