package algorithms.mazeGenerators;

import java.util.Random;

public abstract class AMazeGenerator implements IMazeGenerator{
    @Override
    public abstract Maze generate(int row, int col);

    @Override
    public long measureAlgorithmTimeMillis(int row, int col) {
        long startTime = System.currentTimeMillis();
        generate(row,col);
        long endTime = System.currentTimeMillis();

        return endTime - startTime;

    }



}
