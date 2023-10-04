package chess;

import java.util.Collection;

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
}
