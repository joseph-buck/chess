package chess.pieces;

import chess.*;
import java.util.Collection;

public class King extends Piece {

    public King(ChessGame.TeamColor teamColor) {
        super(teamColor);
        this.pieceType = PieceType.valueOf("KING");
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        // Loop through all neighbors:
            // Each neighbor that is either empty or occupied by an enemy is added to the collection
        return null;
    }
}
