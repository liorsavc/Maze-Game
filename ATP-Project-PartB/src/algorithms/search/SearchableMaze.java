package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.util.ArrayList;
import java.util.HashSet;

public class SearchableMaze implements ISearchable {
    private Maze maze;
    private MazeState goalState;
    private MazeState startState;


    public SearchableMaze(Maze maze) {
        this.maze = maze;
        this.goalState = new MazeState(maze.getGoalPosition().getRowIndex(), maze.getGoalPosition().getColumnIndex(),0);
        this.startState = new MazeState(maze.getStartPosition().getRowIndex(), maze.getStartPosition().getColumnIndex(),0);
        startState.setCost(0);
    }

    @Override
    public ArrayList<AState> getAllPossibleStates(AState curState) {

        // TODO check
        ArrayList<AState> possibleStates = new ArrayList<AState>();

        MazeState curMazeState = ((MazeState) curState);
        MazeState temp = new MazeState();


        // regular neighbors
        if (curMazeState.getPosition().getRowIndex() - 1 >= 0 &&
                !(this.maze.isWall(curMazeState.getPosition().getRowIndex() - 1, curMazeState.getPosition().getColumnIndex()))) {//there is neighbor above position
                possibleStates.add(new MazeState(curMazeState.getPosition().getRowIndex() - 1, curMazeState.getPosition().getColumnIndex(),10));
        }
        if (curMazeState.getPosition().getRowIndex() + 1 < this.maze.getRow() &&
                !this.maze.isWall(curMazeState.getPosition().getRowIndex() + 1, curMazeState.getPosition().getColumnIndex())) {
            //there is neighbor under position
            possibleStates.add(new MazeState(curMazeState.getPosition().getRowIndex() + 1, curMazeState.getPosition().getColumnIndex(),10));

        }
        if (curMazeState.getPosition().getColumnIndex() + 1 < this.maze.getCol() &&
                !this.maze.isWall(curMazeState.getPosition().getRowIndex(), curMazeState.getPosition().getColumnIndex() + 1))
        {//there is neighbor right position
                possibleStates.add(new MazeState(curMazeState.getPosition().getRowIndex(), curMazeState.getPosition().getColumnIndex() + 1,10));
        }
        if (curMazeState.getPosition().getColumnIndex() - 1 >= 0 &&
                !this.maze.isWall(curMazeState.getPosition().getRowIndex(), curMazeState.getPosition().getColumnIndex() - 1))
        {//there is neighbor left position
                possibleStates.add(new MazeState(curMazeState.getPosition().getRowIndex(), curMazeState.getPosition().getColumnIndex() - 1,10));


        }


        // diagonal neighbors
        if (curMazeState.getPosition().getRowIndex() - 1 >= 0 && curMazeState.getPosition().getColumnIndex() + 1 < this.maze.getCol() &&
                !(this.maze.isWall(curMazeState.getPosition().getRowIndex() - 1, curMazeState.getPosition().getColumnIndex() + 1)))
        {//there is neighbor above and right position
                possibleStates.add(new MazeState(curMazeState.getPosition().getRowIndex() - 1, curMazeState.getPosition().getColumnIndex() + 1,15));
        }
        if (curMazeState.getPosition().getRowIndex() + 1 < this.maze.getRow() && curMazeState.getPosition().getColumnIndex() + 1 < this.maze.getCol() &&
                !this.maze.isWall(curMazeState.getPosition().getRowIndex() + 1, curMazeState.getPosition().getColumnIndex() + 1))
        {//there is neighbor under and right position
                possibleStates.add(new MazeState(curMazeState.getPosition().getRowIndex() + 1, curMazeState.getPosition().getColumnIndex() + 1,15));

        }
        if (curMazeState.getPosition().getRowIndex() + 1 < this.maze.getRow() && curMazeState.getPosition().getColumnIndex() - 1 >= 0 &&
                !this.maze.isWall(curMazeState.getPosition().getRowIndex() + 1, curMazeState.getPosition().getColumnIndex() - 1))
        {//there is neighbor under and right position
                possibleStates.add(new MazeState(curMazeState.getPosition().getRowIndex() + 1, curMazeState.getPosition().getColumnIndex() - 1,15));

        }
        if (curMazeState.getPosition().getRowIndex() - 1 >= 0 && curMazeState.getPosition().getColumnIndex() - 1 >= 0 &&
                !(this.maze.isWall(curMazeState.getPosition().getRowIndex() - 1, curMazeState.getPosition().getColumnIndex() - 1)))
        {//there is neighbor above and left position
                possibleStates.add(new MazeState(curMazeState.getPosition().getRowIndex() - 1, curMazeState.getPosition().getColumnIndex() - 1,15));
        }
        return possibleStates;

    }

    @Override
    public MazeState getStartState() {
        return startState;
    }

    public MazeState getGoalState() {
        return goalState;
    }



    public Maze getMaze() {
        return maze;
    }
}