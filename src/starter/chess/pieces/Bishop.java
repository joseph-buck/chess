package chess.pieces;

import chess.*;
import java.util.Collection;

public class Bishop extends Piece {

    public Bishop(ChessGame.TeamColor teamColor) {
        super(teamColor);
        this.pieceType = PieceType.valueOf("BISHOP");
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        // For forward-left, forward-right, backward-left, backward-right, loop 1 to 8:
            // If currSquare is out of bounds: break
            // Else if currSquare is vacant:
                // Add current square to collection
            // Else if currSquare is occupied by enemy:
                // Add currSquare to collection
            //Else: break
        return null;
    }
}
