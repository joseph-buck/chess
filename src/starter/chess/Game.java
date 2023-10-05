package chess;

import chess.pieces.*;
import static chess.ChessGame.TeamColor.WHITE;
import static chess.ChessGame.TeamColor.BLACK;
import static chess.ChessPiece.PieceType.KING;

import java.util.Collection;
import java.util.HashSet;


public class Game implements ChessGame {
    private TeamColor teamTurn;
    private Board board;

    public Game() {
        teamTurn = WHITE;
        board = new Board();
    }

    @Override
    public TeamColor getTeamTurn() {
        return teamTurn;
    }

    @Override
    public void setTeamTurn(TeamColor team) {
        this.teamTurn = team;
    }

    @Override
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        Collection<ChessMove> moves = new HashSet<>();

        ChessPiece myPiece = board.getPiece(startPosition);
        if (myPiece != null) {
            moves = new HashSet<>(
                    myPiece.pieceMoves(board, startPosition));
        }
        return moves;
    }

    @Override
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPosition startPosition = move.getStartPosition();
        ChessPosition endPosition = move.getEndPosition();
        ChessPiece sourcePiece = board.getPiece(startPosition);
        ChessPiece destinationPiece = board.getPiece(endPosition);


        if (!validMoves(startPosition).contains(move)) {
            throw new InvalidMoveException(String.format(
                    "Move %s is not a valid move", move));
        }
        if (teamTurn != sourcePiece.getTeamColor()) {
            throw new InvalidMoveException(String.format(
                    "The piece at %s is not on %s's team", startPosition, teamTurn));
        }
        carryOutMove(move, sourcePiece);
        if (isInCheck(sourcePiece.getTeamColor())) {
            backTrackMove(move, sourcePiece, destinationPiece);
            throw new InvalidMoveException(String.format(
                    "Move %s resulted in a vulnerable position for %s's King",
                    move, teamTurn), true);
        }
        this.teamTurn = (this.teamTurn == WHITE) ? BLACK : WHITE;
    }

    private void carryOutMove(ChessMove move, ChessPiece sourcePiece) {
        ChessPiece movingPiece = sourcePiece;
        ChessPiece.PieceType promotionPieceType = move.getPromotionPiece();
        TeamColor teamColor = sourcePiece.getTeamColor();
        if (move.getPromotionPiece() != null) {
            System.out.println("INSIDE PROMOTION");
            System.out.println(move.getPromotionPiece());
            switch (promotionPieceType) {
                case ROOK:      movingPiece = new Rook(teamColor);
                                break;
                case KNIGHT:    movingPiece = new Knight(teamColor);
                                break;
                case BISHOP:    movingPiece = new Bishop(teamColor);
                                break;
                case QUEEN:     movingPiece = new Queen(teamColor);
                                break;
            }
        }
        board.removePiece(move.getEndPosition());
        board.addPiece(move.getEndPosition(), movingPiece);
        board.removePiece(move.getStartPosition());
    }

    private void backTrackMove(ChessMove move, ChessPiece sourcePiece,
                               ChessPiece destinationPiece) {
        board.addPiece(move.getStartPosition(), sourcePiece);
        board.removePiece(move.getEndPosition());
        board.addPiece(move.getEndPosition(), destinationPiece);
    }

    @Override
    public boolean isInCheck(TeamColor teamColor) {
        ChessPosition kingPosition = findKing(teamColor);
        Collection<ChessMove> validMoves;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                ChessPosition currPosition = new Position(i, j);
                ChessPiece currPiece = board.getPiece(currPosition);
                if ((currPiece != null) && (currPiece.getTeamColor() != teamColor)) {
                    validMoves = validMoves(currPosition);
                    for (ChessMove move : validMoves) {
                        if (move.getEndPosition().equals(kingPosition)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private ChessPosition findKing(TeamColor teamColor) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                ChessPosition currPosition = new Position(i, j);
                ChessPiece currPiece = board.getPiece(currPosition);
                if ((currPiece != null) && (currPiece.getPieceType() == KING)
                        && (currPiece.getTeamColor() == teamColor)) {
                    return currPosition;
                }
            }
        }
        return null;
    }

    @Override
    public boolean isInCheckmate(TeamColor teamColor) {
        Collection<ChessMove> validMoves = getAllValidMoves(teamColor);
        Collection<ChessMove> legalMoves = getAllLegalMoves(validMoves, teamColor);

        if ((isInCheck(teamColor) && (legalMoves.isEmpty()))) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isInStalemate(TeamColor teamColor) {
        Collection<ChessMove> validMoves = getAllValidMoves(teamColor);
        Collection<ChessMove> finalMoves = new HashSet<>();


        /*
        System.out.println(this.teamTurn);
        System.out.println(teamColor);

        for (ChessMove move : moves) {
            ChessPosition startPosition = move.getStartPosition();
            ChessPosition endPosition = move.getEndPosition();
            ChessPiece sourcePiece = board.getPiece(startPosition);
            ChessPiece destinationPiece = board.getPiece(endPosition);

            carryOutMove(move, sourcePiece);
            if (!isInCheck(teamColor)) {
                finalMoves.add(move);
            }
            backTrackMove(move, sourcePiece, destinationPiece);
        }

        System.out.println(this.teamTurn);
        System.out.println(teamColor);*/
        finalMoves.addAll(getAllLegalMoves(validMoves, teamColor));

        return (finalMoves.isEmpty()) && (this.teamTurn == teamColor);
    }

    private Collection<ChessMove> getAllLegalMoves(Collection<ChessMove> validMoves,
                                                   TeamColor teamColor) {
        Collection<ChessMove> finalMoves = new HashSet<>();

        for (ChessMove move : validMoves) {
            ChessPosition startPosition = move.getStartPosition();
            ChessPosition endPosition = move.getEndPosition();
            ChessPiece sourcePiece = board.getPiece(startPosition);
            ChessPiece destinationPiece = board.getPiece(endPosition);

            carryOutMove(move, sourcePiece);
            if (!isInCheck(teamColor)) {
                finalMoves.add(move);
            }
            backTrackMove(move, sourcePiece, destinationPiece);
        }
        return finalMoves;
    }

    private Collection<ChessMove> getAllValidMoves(TeamColor teamColor) {
        Collection<ChessMove> moves = new HashSet<>();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                ChessPosition currPosition = new Position(i, j);
                ChessPiece currPiece = board.getPiece(currPosition);
                if ((currPiece != null) && (currPiece.getTeamColor() == teamColor)) {
                    moves.addAll(validMoves(currPosition));
                }
            }
        }
        return moves;
    }

    @Override
    public void setBoard(ChessBoard board) {
        this.board = (Board)board;
    }

    @Override
    public ChessBoard getBoard() {
        return this.board;
    }
}
