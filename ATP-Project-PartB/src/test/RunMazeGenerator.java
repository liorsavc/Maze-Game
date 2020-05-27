package test;
import algorithms.mazeGenerators.*;
public class RunMazeGenerator {
    public static void main(String[] args) {

        //testMazeGenerator(new EmptyMazeGenerator());
        //testMazeGenerator(new SimpleMazeGenerator());
        testMazeGenerator(new MyMazeGenerator());

    }
    private static void testMazeGenerator(IMazeGenerator mazeGenerator) {
        // prints the time it takes the algorithm to run
        for (int i=0;i<20;i++)
            System.out.println(String.format("Maze generation time(ms): %s",
                mazeGenerator.measureAlgorithmTimeMillis(1000/*rows*/,1000/*columns*/)));
         //generate another maze
        for (int i=0;i<20;i++)
        {
            Maze maze = mazeGenerator.generate(10/*rows*/, 10/*columns*/);
       // prints the maze
//            maze.print();
        System.out.println();
        System.out.println();
        }
        // get the maze entrance
//        Position startPosition = maze.getStartPosition();
//        // print the position
//        System.out.println(String.format("Start Position: %s",
//                startPosition)); // format "{row,column}"
//        // prints the maze exit position
//        System.out.println(String.format("Goal Position: %s",
//                maze.getGoalPosition()));
    }
}
