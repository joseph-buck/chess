package chess;

import chess.*;

import java.util.Collection;
import java.util.HashSet;


public class Queen extends Piece {

    public Queen(ChessGame.TeamColor teamColor) {
        super(teamColor);
        this.pieceType = PieceType.valueOf("QUEEN");
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new HashSet<>();

        moves.addAll(getLongDistanceMoves(board, myPosition, 1, 0));
        moves.addAll(getLongDistanceMoves(board, myPosition, -1, 0));
        moves.addAll(getLongDistanceMoves(board, myPosition, 0, 1));
        moves.addAll(getLongDistanceMoves(board, myPosition, 0, -1));
        moves.addAll(getLongDistanceMoves(board, myPosition, 1, 1));
        moves.addAll(getLongDistanceMoves(board, myPosition, -1, -1));
        moves.addAll(getLongDistanceMoves(board, myPosition, 1, -1));
        moves.addAll(getLongDistanceMoves(board, myPosition, -1, 1));

        return moves;
    }
}
