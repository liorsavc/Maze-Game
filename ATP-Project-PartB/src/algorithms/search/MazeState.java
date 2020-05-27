package algorithms.search;

import algorithms.mazeGenerators.Position;

import java.util.Objects;

public class MazeState extends AState{

    private Position position;


    public MazeState(){
        super();
        this.setCost(1);

        this.position  = null;
    }

    public MazeState(int row,int col,double cost){
        super();
        this.setCost(cost);


        this.position  = new Position(row, col);
    }


    @Override
    public boolean equals(Object o) {
        MazeState cur = (MazeState)o;
        if (this.getPosition().equals(cur.getPosition())){
            return true;
        }
        return false;

    }

    @Override
    public int hashCode() {
        return Objects.hash(position);
    }

    @Override
    public String toString() {
        return position.toString();
    }



    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
