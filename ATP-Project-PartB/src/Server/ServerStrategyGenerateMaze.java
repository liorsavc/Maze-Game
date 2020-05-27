package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;

import java.io.*;

public class ServerStrategyGenerateMaze implements IServerStrategy {

    private AMazeGenerator mazeGenerator;

    public ServerStrategyGenerateMaze(){
         // TODO check here
        mazeGenerator = new MyMazeGenerator();
    }


    @Override
    public void HandleClient(InputStream inFromClient, OutputStream outToClient) throws IOException, ClassNotFoundException {
        //cast to object input stream
        ObjectOutputStream oosToClient= new ObjectOutputStream(outToClient);
        ObjectInputStream oisFromClient = new ObjectInputStream(inFromClient);
        MyCompressorOutputStream compressor = new MyCompressorOutputStream(oosToClient);
        int[] sizeOfMazeArr;
        sizeOfMazeArr = (int[])oisFromClient.readObject();
//        System.out.println( "GenerateStrategy: generating maze");
        Maze maze= mazeGenerator.generate(sizeOfMazeArr[0],sizeOfMazeArr[1]);
//        System.out.println( "GenerateStrategy: maze generated");
        byte[] maze1d =maze.toByteArray();
//        System.out.println( "GenerateStrategy: maze converted to 1d. starting writing ");

        compressor.write(maze1d);
        compressor.flush();
//        System.out.println( "GenerateStrategy: maze sent");

    }
}
