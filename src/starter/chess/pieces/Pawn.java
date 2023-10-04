package chess.pieces;

import chess.*;
import chess.ChessPiece.PieceType;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;


public class Pawn extends Piece {

    public Pawn(ChessGame.TeamColor teamColor) {
        super(teamColor);
        this.pieceType = PieceType.valueOf("PAWN");
    }

    @Override
    public Collection<ChessMove> pieceMoves(
            ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new HashSet<>();

        moves.addAll(getForwardMoves(board, myPosition));
        moves.addAll(getDiagonalMoves(board, myPosition));

        return moves;
    }

    private HashSet<ChessMove> getForwardMoves(ChessBoard board, ChessPosition myPosition) {
        HashSet<ChessMove> forwardMoves = new HashSet<>();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        boolean whiteTeam = (getTeamColor() == ChessGame.TeamColor.WHITE);
        int startRow = whiteTeam ? 2 : 7;
        int direction = whiteTeam ? 1 : -1;

        Position newPosition = new Position(row + direction, col);
        if (board.getPiece(newPosition) == null) {
            if (((whiteTeam) && (row == 7)) || ((!whiteTeam) && (row == 2))) {
                for (PieceType promotePiece :  List.of(PieceType.ROOK, PieceType.KNIGHT,
                        PieceType.BISHOP, PieceType.QUEEN)) {
                    forwardMoves.add(new Move(myPosition, newPosition, promotePiece));
                }
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

        boolean whiteTeam = (getTeamColor() == ChessGame.TeamColor.WHITE);
        int direction = whiteTeam ? 1 : -1;

        Position newPosition = new Position(row + direction, col + horizontalMove);
        ChessPiece diagonalPiece = board.getPiece(newPosition);

        if (diagonalPiece != null) {
            if (((whiteTeam) && (row == 7) && (diagonalPiece.getTeamColor() == ChessGame.TeamColor.BLACK))
                    || ((!whiteTeam) && (row == 2) && (diagonalPiece.getTeamColor() == ChessGame.TeamColor.WHITE))) {
                for (PieceType promotePiece :  List.of(PieceType.ROOK, PieceType.KNIGHT,
                        PieceType.BISHOP, PieceType.QUEEN)) {
                    diagonalMoves.add(new Move(myPosition, newPosition, promotePiece));
                }
            } else {
                if ((whiteTeam) && (diagonalPiece.getTeamColor() == ChessGame.TeamColor.BLACK)) {
                    diagonalMoves.add(new Move(myPosition, newPosition, null));
                } else if ((!whiteTeam) && (diagonalPiece.getTeamColor() == ChessGame.TeamColor.WHITE)) {
                    diagonalMoves.add(new Move(myPosition, newPosition, null));
                }
            }
        }
        return diagonalMoves;
    }
}
