package algorithms.mazeGenerators;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Random;

public class Maze implements Serializable {
    private int[][] maze;
    private int row;
    private int col;
    private Position start;
    private Position goal;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Maze maze1 = (Maze) o;
        return row == maze1.row &&
                col == maze1.col &&
                Arrays.equals(maze, maze1.maze) &&
                Objects.equals(start, maze1.start) &&
                Objects.equals(goal, maze1.goal);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(row, col, start, goal);
        result = 31 * result + Arrays.hashCode(maze);
        return result;
    }

    public Maze(int row, int col) {
        this.maze = new int[row][col];
        this.row = row;
        this.col = col;
        this.start=new Position(0,0);
        this.goal=new Position(0,0);



    }

    public Maze(byte[] maze1d){
        // first parse all meta data
        this.row = (maze1d[0]&0xFF)+(maze1d[1]&0xFF);
        this.col = (maze1d[2]&0xFF)+(maze1d[3]&0xFF);
        this.maze = new int[row][col];
        this.start=new Position((maze1d[4]&0xFF)+(maze1d[5]&0xFF),(maze1d[6]&0xFF)+(maze1d[7]&0xFF));
        this.goal=new Position((maze1d[8]&0xFF)+(maze1d[9]&0xFF),(maze1d[10]&0xFF)+(maze1d[11]&0xFF));
        // parse maze data:
        int i=12;
        //first cell is times the number replays. the cell after it is data.
        for (int rowCounter=0;rowCounter<this.row;rowCounter++)
            {
                for (int colCounter=0;colCounter<this.col;colCounter++)
                {
                    maze[rowCounter][colCounter]=maze1d[i];
                    i++;
                }
            }
    }

    public boolean isWall(int row,int col){
        if (this.maze[row][col]==1)
            return true;
        return false;
    }
    public boolean isWall(Position pos){
        if (this.maze[pos.getRowIndex()][pos.getColumnIndex()]==1)
            return true;
        return false;
    }

    public void setAllWalls(){
        ///  init all the maze with ones
        for (int i = 0;i<row;i++){
            for (int j=0;j<col;j++){
                maze[i][j] = 1;
            }
        }

    }
    public void setStart(int row,int col)
    {
        start.setRowIndex(row);
        start.setColumnIndex(col);
    }
    public void setStart(Position pos)
    {
        start.setRowIndex(pos.getRowIndex());
        start.setColumnIndex(pos.getColumnIndex());

    }
    public void setGoal(int row,int col)
    {
        goal.setRowIndex(row);
        goal.setColumnIndex(col);
    }
    public void setGoal(Position pos)
    {
        goal.setRowIndex(pos.getRowIndex());
        goal.setColumnIndex(pos.getColumnIndex());

    }

    public int[][] getMaze() {
        return maze;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setMaze(int[][] maze) {
        this.maze = maze;
    }



    public void print() {
        for (int i =0;i<row;i++){

            for (int j =0;j<col;j++){

                if (start.getRowIndex()==i&&start.getColumnIndex()==j) // check if we are in startin position
                {
                    // printing $ instead of number
                    System.out.print("S");

                }
                else if (goal.getRowIndex() == i && goal.getColumnIndex()== j )
                {
                    System.out.print("E");
            }
                else{
                    System.out.print(maze[i][j]);
                }

        }
            System.out.println();
    }
    }


    public Position getGoalPosition() {
        return goal;
    }

    public Position getStartPosition() {
        return start;
    }
    public void setStartAndGoal(){
        // this function sets random start and goal position and checks
        //that they are not on the same row or column..
       Random rand = new Random();
       int goalRowIndex,goalColIndex;
       //set start and goal randomly
       start.setRowIndex(rand.nextInt(row));
       start.setColumnIndex(rand.nextInt(col));
       goal.setRowIndex(rand.nextInt(row));
       goal.setColumnIndex(rand.nextInt(col));
       while(start.equals(goal))
       {//start and goal are the same points
           // random new goal point
           goal.setRowIndex(rand.nextInt(row));
           goal.setColumnIndex(rand.nextInt(col));
       }
    }

    public HashSet<Position> getNeighbors(Position position,int distance)
    {
        // returns a set of neighbors  of the position given as argument that are walls only. if the neighbor isnt a wall - dont return it.
        // neighbor in 2 cells distance

        HashSet<Position> neighbors=new HashSet<>();
        //check all directions:
        if (position.getRowIndex()- distance>=0 && this.maze[position.getRowIndex()-distance][position.getColumnIndex()]==1 )
        {//there is neighbor above position
                neighbors.add(new Position(position.getRowIndex()-distance,position.getColumnIndex()));
        }
        if (position.getRowIndex()+distance<this.maze.length&& this.maze[position.getRowIndex()+distance][position.getColumnIndex()]==1)
        {//there is neighbor under position
                neighbors.add(new Position(position.getRowIndex()+distance,position.getColumnIndex()));

        }
        if (position.getColumnIndex()+distance<this.maze[0].length&& this.maze[position.getRowIndex()][position.getColumnIndex()+distance]==1)
        {//there is neighbor right position
                neighbors.add(new Position(position.getRowIndex(),position.getColumnIndex()+distance));
        }
        if (position.getColumnIndex()-distance>=0&& this.maze[position.getRowIndex()][position.getColumnIndex()-distance]==1)
        {//there is neighbor left position
                neighbors.add(new Position(position.getRowIndex(),position.getColumnIndex()-distance));

        }


        return neighbors;
    }







    public void setPoint(int rowIndex,int colIndex,int value)
    {
        maze[rowIndex][colIndex]=value;
    }

    public void connectToPath(Position position) {
        boolean found = false;
        Random rand = new Random();
        // search for first neighbor  in Path that in distance of 2 cells from current position

        while (!found){
            // until we didnt find cell to connect to , continue searching
            // choose random direction
            switch (rand.nextInt(4)){
                case 0:
                    // go up
                    if (position.getRowIndex()-2>=0 && maze[position.getRowIndex()-2][position.getColumnIndex()] == 0){
                        maze[position.getRowIndex()-1][position.getColumnIndex()] =0;
                        maze[position.getRowIndex()][position.getColumnIndex()] =0;
                        found = true;
                    }
                    break;
                case 1:
                    // go right
                    if (position.getColumnIndex()+2<maze[0].length && maze[position.getRowIndex()][position.getColumnIndex()+2] == 0) {
                        maze[position.getRowIndex()][position.getColumnIndex()] = 0;
                        maze[position.getRowIndex()][position.getColumnIndex() + 1] = 0;
                        found = true;
                    }
                    break;
                case 2:
                    // go down
                    if (position.getRowIndex()+2<maze.length && maze[position.getRowIndex()+2][position.getColumnIndex()] == 0){
                        maze[position.getRowIndex()+1][position.getColumnIndex()] =0;
                        maze[position.getRowIndex()][position.getColumnIndex()] =0;
                        found = true;
                    }
                    break;
                case 3:
                    // go left
                    if (position.getColumnIndex()-2>=0 && maze[position.getRowIndex()][position.getColumnIndex()-2] == 0){
                        maze[position.getRowIndex()][position.getColumnIndex()-1] =0;
                        maze[position.getRowIndex()][position.getColumnIndex()] =0;
                        found = true;
                    }
                    break;


            }
        }
    }


    public byte[] toByteArray(){
        // transfer our 2d maze array to one byte array
        int counter=12;

          byte[] maze1d = new byte[12+(this.col*this.row)];
        // start init rows
        if(this.row>255)
        {//size is to big for 1byte:  break into 2 bytes
            maze1d[0]=((byte)255) ;
            maze1d[1]=(byte)((this.row-255));
        }
        else
        {
            maze1d[0]=(byte)(this.row);
            maze1d[1]=(byte)0;
        }
        // init cols:
        if(this.col>255)
        {//size is to big for 1byte:  break into 2 bytes
            maze1d[2]=(byte)255;
            maze1d[3]=(byte)(this.col-255);
        }
        else
        {
            maze1d[2]=(byte)this.col;
            maze1d[3]=(byte)0;
        }
        // init start row 2 bytes
        if(this.start.getRowIndex()>255)
        {//size is to big for 1byte:  break into 2 bytes
            maze1d[4]=(byte)255;
            maze1d[5]=(byte)(this.start.getRowIndex()-255);
        }
        else
        {
            maze1d[4]=(byte)this.start.getRowIndex();
            maze1d[5]=(byte)0;
        }
        // init start col 2 bytes
        if(this.start.getColumnIndex()>255)
        {//size is to big for 1byte:  break into 2 bytes
            maze1d[6]=(byte)255;
            maze1d[7]=(byte)(this.start.getColumnIndex()-255);
        }
        else
        {
            maze1d[6]=(byte)this.start.getColumnIndex();
            maze1d[7]=(byte)0;
        }
        // init goal row 2 bytes
        if(this.goal.getRowIndex()>255)
        {//size is to big for 1byte:  break into 2 bytes
            maze1d[8]=(byte)255;
            maze1d[9]=(byte)(this.goal.getRowIndex()-255);
        }
        else
        {
            maze1d[8]=(byte)this.goal.getRowIndex();
            maze1d[9]=(byte)0;
        }
        // init goal col 2 bytes
        if(this.goal.getColumnIndex()>255)
        {//size is to big for 1byte:  break into 2 bytes
            maze1d[10]=(byte)255;
            maze1d[11]=(byte)(this.goal.getColumnIndex()-255);
        }
        else
        {
            maze1d[10]=(byte)this.goal.getColumnIndex();
            maze1d[11]=(byte)0;
        }
        // loop over maze and copy each cell
        for (int rowIndex=0;rowIndex<this.row;rowIndex++)
        {
            for (int colIndex=0;colIndex<this.col;colIndex++)
            {
                maze1d[counter]=(byte)this.maze[rowIndex][colIndex];
                counter++;
            }
        }
        return maze1d;
    }


    }
