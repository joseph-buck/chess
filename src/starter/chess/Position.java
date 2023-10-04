package chess;

import java.util.Objects;

public class Position implements ChessPosition {
    private int row;
    private int column;

    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    @Override
    public int getRow() {
        return this.row;
    }

    @Override
    public int getColumn() {
        return this.column;
    }

    public void setRow(int newRow) {
        this.row = newRow;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return row == position.row && column == position.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    public void setColumn(int newColumn) {
        this.column = newColumn;
    }

    //@Override
    //public boolean equals(ChessPosition compPos) {
     //   return (this.getRow() == compPos.getRow())
    //            && (this.getColumn() == compPos.getColumn());
    //}
}
