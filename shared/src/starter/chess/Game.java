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

    /**
     * Description in ChessGame.java
     */
    @Override
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        Collection<ChessMove> grossMoves = getGrossMoves(startPosition);
        Collection<ChessMove> validMoves = getAllLegalMoves(grossMoves,
                board.getPiece(startPosition).getTeamColor());
        return validMoves;
    }

    /**
     * Function to get all possible moves from a given position without taking
     * into account moves into check.
     * @param startPosition : The location of the piece to be moved
     * @return grossMoves : The set of possible moves
     */
    private Collection<ChessMove> getGrossMoves(ChessPosition startPosition) {
        Collection<ChessMove> grossMoves = new HashSet<>();

        ChessPiece myPiece = board.getPiece(startPosition);
        if (myPiece != null) {
            grossMoves = new HashSet<>(myPiece.pieceMoves(board, startPosition));
        }
        return grossMoves;
    }

    /**
     * Generates a set of legal moves, taking moves into check into account.
     * @param grossMoves : Set of possible moves prior to considering check.
     * @param teamColor : Color of team whose piece will be moved
     * @return
     */
    private Collection<ChessMove> getAllLegalMoves(Collection<ChessMove> grossMoves,
                                                   TeamColor teamColor) {
        Collection<ChessMove> finalMoves = new HashSet<>();

        for (ChessMove move : grossMoves) {
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

    /**
     * Description in ChessGame.java
     */
    @Override
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPosition startPosition = move.getStartPosition();
        ChessPosition endPosition = move.getEndPosition();
        ChessPiece sourcePiece = board.getPiece(startPosition);
        ChessPiece destinationPiece = board.getPiece(endPosition);

        if (!getGrossMoves(startPosition).contains(move)) {
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

    /**
     * Performs a move. Handles details of pawn promotion.
     * @param move : The move to be carried out
     * @param sourcePiece : The piece to be moved
     */
    private void carryOutMove(ChessMove move, ChessPiece sourcePiece) {
        ChessPiece movingPiece = sourcePiece;
        ChessPiece.PieceType promotionPieceType = move.getPromotionPiece();
        TeamColor teamColor = sourcePiece.getTeamColor();
        if (move.getPromotionPiece() != null) {
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

    /**
     * Undoes a move in the even it needs to be undone.
     * @param move : The move to be undone
     * @param sourcePiece : The piece to return to its original location
     * @param destinationPiece : The piece that was captured (it
     */
    private void backTrackMove(ChessMove move, ChessPiece sourcePiece,
                               ChessPiece destinationPiece) {
        board.addPiece(move.getStartPosition(), sourcePiece);
        board.removePiece(move.getEndPosition());
        if (destinationPiece != null) {
            board.addPiece(move.getEndPosition(), destinationPiece);
        }
    }

    /**
     * Description in ChessGame.java
     */
    @Override
    public boolean isInCheck(TeamColor teamColor) {
        ChessPosition kingPosition = findKing(teamColor);
        Collection<ChessMove> validMoves;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                ChessPosition currPosition = new Position(i, j);
                ChessPiece currPiece = board.getPiece(currPosition);
                if ((currPiece != null) && (currPiece.getTeamColor() != teamColor)) {
                    validMoves = getGrossMoves(currPosition);
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

    /**
     * Finds the position of the king of the given team.
     * @param teamColor : Color of team whose king will be found
     * @return currPosition : The position of the king
     */
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

    /**
     * Description in ChessGame.java
     */
    @Override
    public boolean isInCheckmate(TeamColor teamColor) {
        Collection<ChessMove> validMoves = getTeamValidMoves(teamColor);
        Collection<ChessMove> legalMoves = getAllLegalMoves(validMoves, teamColor);

        if ((isInCheck(teamColor) && (legalMoves.isEmpty()))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Description in ChessGame.java
     */
    @Override
    public boolean isInStalemate(TeamColor teamColor) {
        Collection<ChessMove> validMoves = getTeamValidMoves(teamColor);
        Collection<ChessMove> finalMoves = new HashSet<>();

        finalMoves.addAll(getAllLegalMoves(validMoves, teamColor));

        return (finalMoves.isEmpty()) && (this.teamTurn == teamColor);
    }

    /**
     * Creates a set of all moves that a given team can execute, without taking
     * check into account. That is handled later by getAllLegalMoves.
     * @param teamColor : The team whose moves will be compiled.
     * @return moves : Set of moves
     */
    private Collection<ChessMove> getTeamValidMoves(TeamColor teamColor) {
        Collection<ChessMove> moves = new HashSet<>();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                ChessPosition currPosition = new Position(i, j);
                ChessPiece currPiece = board.getPiece(currPosition);
                if ((currPiece != null) && (currPiece.getTeamColor() == teamColor)) {
                    moves.addAll(getGrossMoves(currPosition));
                }
            }
        }
        return moves;
    }

    /**
     * Description in ChessGame.java
     */
    @Override
    public void setBoard(ChessBoard board) {
        this.board = (Board)board;
    }

    /**
     * Description in ChessGame.java
     */
    @Override
    public ChessBoard getBoard() {
        return this.board;
    }
}
