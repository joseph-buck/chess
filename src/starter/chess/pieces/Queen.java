package chess.pieces;

import chess.*;
import java.util.Collection;

public class Queen extends Piece {

    public Queen(ChessGame.TeamColor teamColor) {
        super(teamColor);
        this.pieceType = PieceType.valueOf("QUEEN");
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        // Make another class I can import here and in Bishop/Rook (and maybe king) to loop through directions

        // For forward, left, right, backward, loop 1 to 8:
            // If currSquare is out of bounds: break
            // Else if currSquare is vacant:
                // Add current square to collection
            // Else if currSquare is occupied by enemy:
                // Add currSquare to collection
            //Else: break
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
