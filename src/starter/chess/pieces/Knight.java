package chess.pieces;

import chess.*;

import java.util.Collection;
import java.util.HashSet;

public class Knight extends Piece {

    public Knight(ChessGame.TeamColor teamColor) {
        super(teamColor);
        this.pieceType = PieceType.valueOf("KNIGHT");
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new HashSet<>();

        moves.addAll(getSingleMoves(board, myPosition, 2, 1));
        moves.addAll(getSingleMoves(board, myPosition, 1, 2));
        moves.addAll(getSingleMoves(board, myPosition, -1, 2));
        moves.addAll(getSingleMoves(board, myPosition, -2, 1));
        moves.addAll(getSingleMoves(board, myPosition, -2, -1));
        moves.addAll(getSingleMoves(board, myPosition, -1, -2));
        moves.addAll(getSingleMoves(board, myPosition, 1, -2));
        moves.addAll(getSingleMoves(board, myPosition, 2, -1));

        return moves;
    }
}
