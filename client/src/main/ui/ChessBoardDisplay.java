package ui;

import chess.*;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static ui.EscapeSequences.*;


public class ChessBoardDisplay {
    private static final int BOARD_SIZE_IN_SQUARES = 8;
    private static final int SQUARE_SIZE_IN_CHARS = 3;
    private static final String EMPTY = "   ";

    public void drawBoard() {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        out.print(ERASE_SCREEN);

        int iter = 0;
        for (ChessGame.TeamColor sideToPrint : new ChessGame.TeamColor[]{
                ChessGame.TeamColor.WHITE, ChessGame.TeamColor.BLACK}) {
            Board newBoard = new Board();
            newBoard.resetBoard();

            drawHeaders(out, sideToPrint);
            drawChessBoard(out, newBoard, sideToPrint);
            drawHeaders(out, sideToPrint);

            out.print(SET_BG_COLOR_DARK_GREY);
            out.print(SET_TEXT_COLOR_WHITE);

            if (iter == 0) {
                drawBlankRow(out);
            }
            iter += 1;
        }
    }

    private static void drawBlankRow(PrintStream out) {
        for (int squareRow = 0; squareRow < SQUARE_SIZE_IN_CHARS; ++squareRow) {
            for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES + 2; ++boardCol) {
                setBlack(out);
                out.print(EMPTY.repeat(SQUARE_SIZE_IN_CHARS));
            }
            setDarkGrey(out);
            out.println();
        }
    }

    private static void drawHeaders(PrintStream out, ChessGame.TeamColor sideToPrint) {
        setDarkGrey(out);
        List<String> headers = new ArrayList<>(Arrays.asList(" h ", " g ", " f ", " e ", " d ", " c ", " b ", " a "));
        if (sideToPrint == ChessGame.TeamColor.BLACK) {
            Collections.reverse(headers);
        }
        for (int squareRow = 0; squareRow < SQUARE_SIZE_IN_CHARS; ++squareRow) {
            if (squareRow == SQUARE_SIZE_IN_CHARS / 2) {
                out.print(EMPTY.repeat(SQUARE_SIZE_IN_CHARS));
                for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
                    drawHeader(out, headers.get(boardCol));
                }
                out.print(EMPTY.repeat(SQUARE_SIZE_IN_CHARS));
            } else {
                out.print(EMPTY.repeat(SQUARE_SIZE_IN_CHARS));
                out.println();
            }
            setDarkGrey(out);
        }
        out.println();
    }

    private static void drawHeader(PrintStream out, String headerText) {
        int prefixLength = SQUARE_SIZE_IN_CHARS / 2;
        int suffixLength = SQUARE_SIZE_IN_CHARS - prefixLength - 1;

        out.print(EMPTY.repeat(prefixLength));
        printHeaderText(out, headerText);
        out.print(EMPTY.repeat(suffixLength));
    }

    private static void printHeaderText(PrintStream out, String player) {
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(SET_TEXT_COLOR_WHITE);

        out.print(player);
    }

    private static void drawChessBoard(PrintStream out, Board board, ChessGame.TeamColor sideToPrint) {
        if (sideToPrint == ChessGame.TeamColor.WHITE) {
            board = reverseBoard(board);
        }
        for (int boardRow = 0; boardRow < BOARD_SIZE_IN_SQUARES; ++boardRow) {
            drawRowOfSquares(out, boardRow, board, sideToPrint);
        }
    }

    private static Board reverseBoard(Board board) {
        Board newBoard = new Board();

        HashMap<ChessPosition, ChessPiece> piecePositions = board.getPiecePositions();
        HashMap<ChessPosition, ChessPiece> newPiecePositions = new HashMap<>();

        for (ChessPosition position : piecePositions.keySet()) {
            ChessPiece currPiece = piecePositions.get(position);
            int newRow = BOARD_SIZE_IN_SQUARES + 1 - position.getRow();
            int newCol = BOARD_SIZE_IN_SQUARES + 1 - position.getColumn();
            Position newPosition = new Position(newRow, newCol);
            newPiecePositions.put(newPosition, currPiece);
        }
        newBoard.setPiecePositions(newPiecePositions);
        return newBoard;
    }

    private static void drawRowOfSquares(PrintStream out, int boardRow, Board board, ChessGame.TeamColor sideToPrint) {
        for (int squareRow = 0; squareRow < SQUARE_SIZE_IN_CHARS; ++squareRow) {
            drawNumber(out, squareRow, boardRow, sideToPrint);

            for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
                if ((boardCol + boardRow) % 2 == 0) {
                    setWhite(out);
                } else {
                    setBlack(out);
                }
                if (squareRow == SQUARE_SIZE_IN_CHARS / 2) {
                    int prefixLength = SQUARE_SIZE_IN_CHARS / 2;
                    int suffixLength = SQUARE_SIZE_IN_CHARS - prefixLength - 1;

                    out.print(EMPTY.repeat(prefixLength));
                    printPlayer(out, boardRow, boardCol, board);
                    out.print(EMPTY.repeat(suffixLength));
                }
                else {
                    out.print(EMPTY.repeat(SQUARE_SIZE_IN_CHARS));
                }
                setDarkGrey(out);
            }

            drawNumber(out, squareRow, boardRow, sideToPrint);

            out.println();
        }
    }

    private static void drawNumber(PrintStream out, int squareRow, int boardRow,
                                   ChessGame.TeamColor sideToPrint) {
        int prefixLength = SQUARE_SIZE_IN_CHARS / 2;
        int suffixLength = SQUARE_SIZE_IN_CHARS - prefixLength - 1;

        if (squareRow == SQUARE_SIZE_IN_CHARS / 2) {
            out.print(EMPTY.repeat(prefixLength));
            out.print(SET_TEXT_COLOR_WHITE);
            if (sideToPrint == ChessGame.TeamColor.WHITE) {
                out.print(String.format(" %d ", boardRow + 1));
            } else {
                out.print(String.format(" %d ", BOARD_SIZE_IN_SQUARES - boardRow));
            }
            out.print(EMPTY.repeat(suffixLength));
        } else {
            out.print(EMPTY.repeat(SQUARE_SIZE_IN_CHARS));
        }
    }

    private static String calcPiece(ChessPiece piece) {
        if (piece == null) {
            return EMPTY;
        } else {
            if (piece instanceof Rook) {
                return " R ";
            } else if (piece instanceof Pawn) {
                return " P ";
            } else if (piece instanceof Knight) {
                return " N ";
            } else if (piece instanceof Bishop) {
                return " B ";
            } else if (piece instanceof Queen) {
                return " Q ";
            } else if (piece instanceof King) {
                return " K ";
            }
            return EMPTY;
        }
    }

    private static void setWhite(PrintStream out) {
        out.print(SET_BG_COLOR_WHITE);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void setDarkGrey(PrintStream out) {
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(SET_TEXT_COLOR_DARK_GREY);
    }

    private static void setBlack(PrintStream out) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_BLACK);
    }

    private static void printPlayer(PrintStream out, int boardRow, int boardCol, Board board) {
        ChessPiece piece = board.getPiece(new Position(boardRow + 1, boardCol + 1));
        String player = calcPiece(piece);
        if (piece != null) {
            if (piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                out.print(SET_TEXT_COLOR_BLUE);
            } else {
                out.print(SET_TEXT_COLOR_RED);
            }
        }

        out.print(player);
    }
}
