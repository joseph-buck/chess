import chess.Board;
import chess.Position;
import chess.*;
import chess.pieces.*;


public class TESTER {
    public static void main(String[] args) {
        Position myPos;
        Board myBoard = new Board();
        myBoard.resetBoard();

        //System.out.println("============================");
        //System.out.println("TESTING pieceMoves:");
        //for (ChessMove entry : myBoard.getPiece(
        //        new Position(2,1)).pieceMoves(myBoard, new Position(2, 1))) {
        //    System.out.println(String.format("Move from (%s, %s), to (%s, %s)",
        //            entry.getStartPosition().getRow(), entry.getStartPosition().getColumn(),
        //            entry.getEndPosition().getRow(), entry.getEndPosition().getColumn()));
        System.out.println("============================");
        System.out.println("TESTING White Move Blocked:");
        myBoard.addPiece(new Position(4, 4), new Pawn(ChessGame.TeamColor.WHITE));
        myBoard.addPiece(new Position(5, 4), new Knight(ChessGame.TeamColor.BLACK));
        System.out.println(myBoard.getPiece(new Position(4, 4)).pieceMoves(myBoard, new Position(4, 4)));

        //}
    }
}
