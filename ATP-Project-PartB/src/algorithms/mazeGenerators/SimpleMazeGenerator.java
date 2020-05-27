package algorithms.mazeGenerators;

import java.util.*;

public class SimpleMazeGenerator extends AMazeGenerator {
    @Override
    public Maze generate(int row, int col)
    {
        Random rand = new Random();
        Maze simpleMaze = new Maze(row, col);
        //simpleMaze.setAllWalls();
        simpleMaze.setStartAndGoal();
        int[][] tempMaze = simpleMaze.getMaze();
        // make different copy of the starting point:
        Position curPosition = new Position(simpleMaze.getStartPosition().getRowIndex(),simpleMaze.getStartPosition().getColumnIndex());
        ArrayList<Position> path = new ArrayList<Position>();
        path.add(simpleMaze.getStartPosition());
        // find a path from start to goal:
        while(curPosition.getRowIndex()<simpleMaze.getGoalPosition().getRowIndex()){// curr is higher than goal
        //move curr down
            curPosition.setRowIndex(curPosition.getRowIndex()+1);
            Position tmp= new Position(curPosition.getRowIndex(),curPosition.getColumnIndex());
            path.add(tmp);
        }
        while(curPosition.getRowIndex()>simpleMaze.getGoalPosition().getRowIndex()){// curr is lower than goal
            //move curr up
            curPosition.setRowIndex(curPosition.getRowIndex()-1);
            Position tmp= new Position(curPosition.getRowIndex(),curPosition.getColumnIndex());
            path.add(tmp);

        }
        while(curPosition.getColumnIndex()<simpleMaze.getGoalPosition().getColumnIndex()){// curr is left to goal
            //move curr right
            curPosition.setColumnIndex(curPosition.getColumnIndex()+1);
            Position tmp= new Position(curPosition.getRowIndex(),curPosition.getColumnIndex());


            path.add(tmp);

        }
        while(curPosition.getColumnIndex()<simpleMaze.getGoalPosition().getColumnIndex()){// curr is right to goal
            //move curr left
            curPosition.setColumnIndex(curPosition.getColumnIndex()-1);
            Position tmp= new Position(curPosition.getRowIndex(),curPosition.getColumnIndex());

            path.add(tmp);
        }
        // loop over all positions in the maze
        // random choose wall or not (expect to path)
        Position position = new Position(0,0);
        for (int i=0;i<simpleMaze.getRow();i++)//rows
        {
            for (int j=0;j<simpleMaze.getCol();j++)//cols
            {
                position.setRowIndex(i);
                position.setColumnIndex(j);
                //check if current is not part of the solution path:
                if(!path.contains(position)) {
                    //not solution:
                    // randomize wall
                    tempMaze[i][j] = rand.nextInt(2);
                }
            }
        }
        simpleMaze.setMaze(tempMaze);
        return simpleMaze;

    }
}
