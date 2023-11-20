package ui;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import static ui.EscapeSequences.*;


public class ChessBoardDisplay {
    private static final int BOARD_SIZE_IN_SQUARES = 8;
    private static final int SQUARE_SIZE_IN_CHARS = 3;
    private static final String EMPTY = "   ";
    private static Random rand = new Random();

    public void drawBoard() {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        out.print(ERASE_SCREEN);

        drawHeaders(out);
        drawChessBoard(out);
        drawHeaders(out);

        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void drawHeaders(PrintStream out) {
        setDarkGrey(out);
        String[] headers = {" a ", " b ", " c ", " d ", " e ", " f ", " g ", " h ", EMPTY};
        for (int squareRow = 0; squareRow < SQUARE_SIZE_IN_CHARS; ++squareRow) {
            if (squareRow == SQUARE_SIZE_IN_CHARS / 2) {
                out.print(EMPTY.repeat(SQUARE_SIZE_IN_CHARS));
                for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
                    drawHeader(out, headers[boardCol]);
                }
                out.print(EMPTY.repeat(SQUARE_SIZE_IN_CHARS));
            } else {
                out.print(EMPTY.repeat(SQUARE_SIZE_IN_CHARS));
                out.println("");
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

    private static void drawChessBoard(PrintStream out) {
        for (int boardRow = 0; boardRow < BOARD_SIZE_IN_SQUARES; ++boardRow) {
            drawRowOfSquares(out, boardRow);
        }
    }

    private static void drawRowOfSquares(PrintStream out, int boardRow) {
        for (int squareRow = 0; squareRow < SQUARE_SIZE_IN_CHARS; ++squareRow) {
            for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES + 2; ++boardCol) {
                if ((boardCol == 0) || (boardCol == BOARD_SIZE_IN_SQUARES + 1)
                        || (boardRow == BOARD_SIZE_IN_SQUARES + 1)) {
                    setDarkGrey(out);
                } else if ((boardCol + boardRow) % 2 == 0) {
                    setWhite(out);
                } else {
                    setBlack(out);
                }
                if (squareRow == SQUARE_SIZE_IN_CHARS / 2) {
                    int prefixLength = SQUARE_SIZE_IN_CHARS / 2;
                    int suffixLength = SQUARE_SIZE_IN_CHARS - prefixLength - 1;

                    out.print(EMPTY.repeat(prefixLength));
                    printPlayer(out, calcInitPiece(boardRow, boardCol));
                    out.print(EMPTY.repeat(suffixLength));
                }
                else {
                    out.print(EMPTY.repeat(SQUARE_SIZE_IN_CHARS));
                }
                setDarkGrey(out);
            }
            out.println();
        }
    }

    private static String calcInitPiece(int boardRow, int boardCol) {
        String returnVal = "";
        if ((boardRow == 0) && (boardCol == 1)) {
            returnVal = " R ";
        } else if ((boardRow == 0) && (boardCol == 2)) {
            returnVal = " N ";
        } else if ((boardRow == 0) && (boardCol == 3)) {
            returnVal = " B ";
        } else if ((boardRow == 0) && (boardCol == 4)) {
            returnVal = " Q ";
        } else if ((boardRow == 0) && (boardCol == 5)) {
            returnVal = " K ";
        } else if ((boardRow == 0) && (boardCol == 6)) {
            returnVal = " B ";
        } else if ((boardRow == 0) && (boardCol == 7)) {
            returnVal = " N ";
        } else if ((boardRow == 0) && (boardCol == 8)) {
            returnVal = " R ";
        } else if ((boardRow == 1) && (boardCol > 0) && (boardCol < BOARD_SIZE_IN_SQUARES + 1)) {
            returnVal = " P ";
        } else if ((boardRow == 6) && (boardCol > 0) && (boardCol < BOARD_SIZE_IN_SQUARES + 1)) {
            returnVal = " P ";
        } else if ((boardRow == 7) && (boardCol == 1)) {
            returnVal = " R ";
        } else if ((boardRow == 7) && (boardCol == 2)) {
            returnVal = " N ";
        } else if ((boardRow == 7) && (boardCol == 3)) {
            returnVal = " B ";
        } else if ((boardRow == 7) && (boardCol == 4)) {
            returnVal = " Q ";
        } else if ((boardRow == 7) && (boardCol == 5)) {
            returnVal = " K ";
        } else if ((boardRow == 7) && (boardCol == 6)) {
            returnVal = " B ";
        } else if ((boardRow == 7) && (boardCol == 7)) {
            returnVal = " N ";
        } else if ((boardRow == 7) && (boardCol == 8)) {
            returnVal = " R ";
        } else {
            returnVal = EMPTY;
        }
        return returnVal;
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

    private static void printPlayer(PrintStream out, String player) {
        //out.print(SET_BG_COLOR_WHITE);
        out.print(SET_TEXT_COLOR_BLUE);

        out.print(player);
    }
}
