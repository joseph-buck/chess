package chess.pieces;

import chess.*;

import java.util.Collection;
import java.util.HashSet;


public class King extends Piece {

    public King(ChessGame.TeamColor teamColor) {
        super(teamColor);
        this.pieceType = PieceType.valueOf("KING");
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new HashSet<>();

        moves.addAll(getSingleMoves(board, myPosition, 1, 0));
        moves.addAll(getSingleMoves(board, myPosition, 1, 1));
        moves.addAll(getSingleMoves(board, myPosition, 0, 1));
        moves.addAll(getSingleMoves(board, myPosition, -1, 1));
        moves.addAll(getSingleMoves(board, myPosition, -1, 0));
        moves.addAll(getSingleMoves(board, myPosition, -1, -1));
        moves.addAll(getSingleMoves(board, myPosition, 0, -1));
        moves.addAll(getSingleMoves(board, myPosition, 1, -1));

        return moves;
    }
}
