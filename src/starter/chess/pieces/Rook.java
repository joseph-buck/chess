package chess.pieces;

import chess.*;

import java.util.Collection;
import java.util.HashSet;


public class Rook extends Piece {

    public Rook(ChessGame.TeamColor teamColor) {
        super(teamColor);
        this.pieceType = PieceType.valueOf("ROOK");
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new HashSet<>();

        moves.addAll(getVerticalMoves(board, myPosition));
        moves.addAll(getHorizontalMoves(board, myPosition));

        return moves;
    }

    private HashSet<ChessMove> getVerticalMoves(ChessBoard board, ChessPosition myPosition) {
        HashSet<ChessMove> verticalMoves = new HashSet<>();

        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        for (int i = row + 1; i < 9; i++) {
            ChessPosition newPosition = new Position(i, col);
            ChessMove returnMove = checkMove(board, myPosition, newPosition);
            if (returnMove == null) {
                break;
            } else {
                verticalMoves.add(returnMove);
                if (board.getPiece(newPosition) != null) {
                    break;
                }
            }
        }

        for (int i = row - 1; i > 0; i--) {
            ChessPosition newPosition = new Position(i, col);
            ChessMove returnMove = checkMove(board, myPosition, newPosition);
            if (returnMove == null) {
                break;
            } else {
                verticalMoves.add(returnMove);
                if (board.getPiece(newPosition) != null) {
                    break;
                }
            }
        }

        return verticalMoves;
    }

    private HashSet<ChessMove> getHorizontalMoves(ChessBoard board, ChessPosition myPosition) {
        HashSet<ChessMove> horizontalMoves = new HashSet<>();

        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        for (int i = col + 1; i < 9; i++) {
            ChessPosition newPosition = new Position(row, i);
            ChessMove returnMove = checkMove(board, myPosition, newPosition);
            if (returnMove == null) {
                break;
            } else {
                horizontalMoves.add(returnMove);
                if (board.getPiece(newPosition) != null) {
                    break;
                }
            }
        }

        for (int i = col - 1; i > 0; i--) {
            ChessPosition newPosition = new Position(row, i);
            ChessMove returnMove = checkMove(board, myPosition, newPosition);
            if (returnMove == null) {
                break;
            } else {
                horizontalMoves.add(returnMove);
                if (board.getPiece(newPosition) != null) {
                    break;
                }
            }
        }

        return horizontalMoves;
    }

    private ChessMove checkMove(
            ChessBoard board, ChessPosition myPosition, ChessPosition newPosition) {
        ChessPiece currPiece = board.getPiece(newPosition);
        ChessPiece myPiece = board.getPiece(myPosition);

        System.out.println("==============");
        System.out.println(newPosition);
        System.out.println(currPiece);
        System.out.println(myPiece.toString());

        ChessMove move = new Move(myPosition, newPosition, null);
        if ((currPiece != null) && (currPiece.getTeamColor() == myPiece.getTeamColor())) {
            move = null;
        }
        return move;
    }
}
