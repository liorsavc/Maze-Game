package algorithms.mazeGenerators;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;

public class Position implements Serializable {
    private int rowIndex;
    private int colIndex;



    public Position(int row,int col) {
        //constructor
        this.rowIndex = row;
        this.colIndex = col;
    }

    public Position(Position position) {
        //constructor
        this.rowIndex = position.rowIndex;
        this.colIndex = position.colIndex;
    }



    public void setColumnIndex(int colIndex) {
        this.colIndex = colIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public int getColumnIndex() {
        return colIndex;
    }

    public int getRowIndex() {
        return rowIndex;
    }



    @Override
    public String toString() {

        return "{" + rowIndex + "," + colIndex + '}';
    }

    public boolean equals(Object obj){
        Position position=(Position)obj;
        if(this.colIndex==position.colIndex && this.rowIndex==position.rowIndex)
            return true;
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rowIndex, colIndex);
    }

}
