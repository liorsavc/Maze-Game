package algorithms.mazeGenerators;

import java.util.Random;

public class EmptyMazeGenerator extends AMazeGenerator{

    @Override
    public Maze generate(int row, int col) {
        Maze emptyMaze = new Maze(row, col);
        Random rand = new Random();
        emptyMaze.setStartAndGoal();

        return emptyMaze;

    }


}
