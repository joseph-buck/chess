package chess.pieces;

import chess.*;
import java.util.Collection;

public class Knight extends Piece {

    public Knight(ChessGame.TeamColor teamColor) {
        super(teamColor);
        this.pieceType = PieceType.valueOf("KNIGHT");
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        // Loop through these combos:
            // (r+2,c+1)
            // (r+1,c+2)
            // (r-1,c+2)
            // (r-2,c+1)
            // (r-2,c-1)
            // (r-1,c-2)
            // (r+1,c-2)
            // (r+2,c-1)
            // If they are vacant or occupied by the enemy, add to collection
        return null;
    }
}
