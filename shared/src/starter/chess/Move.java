package chess;

import java.util.Objects;

public class Move implements ChessMove {
    private final ChessPosition startPosition;
    private final ChessPosition endPosition;
    private final ChessPiece.PieceType promotionPiece;

    public Move(ChessPosition startPosition, ChessPosition endPosition,
                ChessPiece.PieceType promotionPiece) {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.promotionPiece = promotionPiece;
    }

    @Override
    public ChessPosition getStartPosition() {
        return this.startPosition;
    }

    @Override
    public ChessPosition getEndPosition() {
        return this.endPosition;
    }

    @Override
    public ChessPiece.PieceType getPromotionPiece() {
        return promotionPiece;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return Objects.equals(startPosition, move.startPosition)
                && Objects.equals(endPosition, move.endPosition)
                && promotionPiece == move.promotionPiece;
    }

    @Override
    public int hashCode() {
        return Objects.hash(startPosition, endPosition, promotionPiece);
    }

    @Override
    public String toString() {
        return "Move {\n" +
                "\tstartPosition=" + startPosition +
                ",\n\tendPosition=" + endPosition +
                ",\n\tpromotionPiece=" + promotionPiece +
                "\n}" + "\n";
    }
}
