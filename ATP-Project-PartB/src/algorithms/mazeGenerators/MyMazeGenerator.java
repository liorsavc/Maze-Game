package algorithms.mazeGenerators;

import java.util.*;

public class MyMazeGenerator extends AMazeGenerator {


    @Override
    public Maze generate(int row, int col) {


        Random rand = new Random();
        HashSet<Position> m_sNeighbors = new HashSet<Position>();
        Position position1;
        Position neighbor = new Position(0, 0);
        int i =0;
        int item = 0;
        //using prim algorithm
        // init my maze:*****************************************************
        Maze myMaze = new Maze(row, col);
        //random start point:
        myMaze.setStart(rand.nextInt(row), rand.nextInt(col));
        myMaze.setAllWalls(); /// init the maze with walls (1)
        myMaze.setPoint(myMaze.getStartPosition().getRowIndex(),myMaze.getStartPosition().getColumnIndex(),0);
        //*******************************************************************
        // get all neighbors of cur position
        m_sNeighbors.addAll(myMaze.getNeighbors(myMaze.getStartPosition(),2));
        if (m_sNeighbors.isEmpty()) {
            // 2x2,2x3,3x2,3x3 cases
            // pick random goal position
            myMaze.setGoal(rand.nextInt(row), rand.nextInt(col));
            // check if goal equals to start if so randomized new point
            while (myMaze.getGoalPosition().equals(myMaze.getStartPosition())) {
                myMaze.setGoal(rand.nextInt(row), rand.nextInt(col));
            }
            myMaze.setPoint(myMaze.getGoalPosition().getRowIndex(),myMaze.getGoalPosition().getColumnIndex(),0);
            // 2*2 case:
            if(row==2 && col==2)
            {
                int tmpcol=myMaze.getStartPosition().getColumnIndex();
                if(tmpcol==0)
                    myMaze.setPoint(myMaze.getStartPosition().getRowIndex(),1,0);
                else
                    myMaze.setPoint(myMaze.getStartPosition().getRowIndex(),0,0);


            }
            //2*3 case:
            if(row==2 && col==3 && myMaze.getStartPosition().getColumnIndex()==1)
            {
               myMaze.setPoint(myMaze.getStartPosition().getRowIndex(),2,0);
               myMaze.setPoint(myMaze.getStartPosition().getRowIndex(),0,0);

            }
            //case 3*2
            if(row==3 && col==2 && myMaze.getStartPosition().getRowIndex()==1)
            {
                myMaze.setPoint(2,myMaze.getStartPosition().getColumnIndex(),0);
                myMaze.setPoint(0,myMaze.getStartPosition().getColumnIndex(),0);

            }
            //case 3*3
            if(row==3 && col==3 && myMaze.getStartPosition().getRowIndex()==1 && myMaze.getStartPosition().getColumnIndex()==1)
            {
                myMaze.setPoint(myMaze.getStartPosition().getRowIndex(),2,0);
                myMaze.setPoint(myMaze.getStartPosition().getRowIndex(),0,0);
            }
            return myMaze;
        }

        while (!m_sNeighbors.isEmpty()) {

            // there are neigbors in the set
            // pick random neighbor
             i =0;
             item = rand.nextInt(m_sNeighbors.size());
            for(Position pos : m_sNeighbors)
            {
                if (i == item) {
                    neighbor = pos;
                    break;
                }
                i++;
            }
            //remove neigbor from neigbor set
            m_sNeighbors.remove(neighbor);
            // connect this neigbor to the Path
            myMaze.connectToPath(neighbor);
            // add the new neighbors of neighbor to the set
            for (Position pos:myMaze.getNeighbors(neighbor,2)) {
                if (!m_sNeighbors.contains(pos))
                    m_sNeighbors.add(pos);
            }
//            m_sNeighbors.addAll(myMaze.getNeighbors(neighbor,2));
        }
        Position tmpGoal = new Position(rand.nextInt(row),rand.nextInt(col));
        while(myMaze.isWall(tmpGoal) ||  myMaze.getStartPosition().equals(tmpGoal))
        {
            tmpGoal = new Position(rand.nextInt(row),rand.nextInt(col));
        }
        myMaze.setGoal(tmpGoal);
        return myMaze;
    }
}

