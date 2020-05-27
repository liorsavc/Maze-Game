package Server;
import algorithms.mazeGenerators.Maze;
import algorithms.search.*;
import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class ServerStrategySolveSearchProblem implements IServerStrategy {
    private ISearchingAlgorithm solver;
    private HashMap<Integer,Solution> mazeToSolutionHashMap;

    public ServerStrategySolveSearchProblem() {
                solver = new BestFirstSearch();


        //TODO - uncomment when using config file
//        String algorithm=Configurations.getSolveAlgorithm();
//        switch (algorithm)
//        {
//            case "DFS":
//                solver = new DepthFirstSearch();
//                break;
//            case "BFS":
//                solver = new BreadthFirstSearch();
//                break;
//            case "BestFS":
//                solver = new BestFirstSearch();
//                break;
//        }
         mazeToSolutionHashMap= new HashMap<Integer, Solution>();
    }

    @Override
    public void HandleClient(InputStream inputFromClient, OutputStream outputToClient) throws IOException, ClassNotFoundException {
        ObjectInputStream oisFromClient = new ObjectInputStream(inputFromClient);
        ObjectOutputStream oosToClient= new ObjectOutputStream(outputToClient);
        boolean mazeFound=false;
        Solution solution=null;
        //get maze from client for solving
        //Deserialization
//        System.out.println("SolveStrategy: start reading");
        Maze maze = (Maze)oisFromClient.readObject();
//        System.out.println("SolveStrategy: finished reading holding maze field");

        //check if solution already exist
        if(this.mazeToSolutionHashMap.containsKey(maze.hashCode()))
        {//solution exist
            solution=this.mazeToSolutionHashMap.get(maze.hashCode());

        }
        else
        {//solution not exist
            //solve the maze and
            solution=SolveMaze(maze);
            //add solution to map
            mazeToSolutionHashMap.put(maze.hashCode(),solution);
            //write solution to client
        }
        oosToClient.writeObject(solution);
        oosToClient.flush();
    }

//    public Solution buildSolutionFromString(String str)
////    { // convert str (given from line in solution file) to Solution type;
////        Solution solution = new Solution();
////        String[] splitedStr = str.split(",");
////        for (int i=0; i<=splitedStr.length-2;i+=2)
////
////        {
////            MazeState node = new MazeState(Integer.parseInt(splitedStr[i]),Integer.parseInt(splitedStr[i+1]),0);
////
////            solution.addToSolutionPath(node);
////        }
////        return solution;
//    }
    public Solution SolveMaze(Maze maze) throws IOException {
        boolean writen=false;
        byte[] maze1d;
        String lineToWriteInFile="";
        String tempDirectoryPath = System.getProperty("java.io.tmpdir");
        File folder=new File(tempDirectoryPath);
        int fileNum=0;
        String fileName=tempDirectoryPath+"\\"+maze.hashCode()+".txt";


        // solves the maze given in argument and write the maze data and the solution to new file
        ISearchable searchableMaze = new SearchableMaze(maze);
        Solution solution = solver.solve(searchableMaze);

        //create new file:
        File file = new File(fileName);
//        while (file.exists())
//        {
//            fileNum++;
//            fileName=tempDirectoryPath+"\\"+fileNum+".txt";
//            file = new File(fileName);
//
//        }

        //write to the file
        file.createNewFile();
        FileWriter fw = new FileWriter(fileName);
//        // now need to create string to write from maze.
//        //convert maze to 1d array
//       maze1d= maze.toByteArray();
//        // loop over 1d array and take each cell and convert to string divided by ','
//        lineToWriteInFile+=maze1d[0];//first time write to line without comma
//        for (int i=1;i<maze1d.length;i++)
//            lineToWriteInFile+=","+maze1d[i];
//        fw.write(lineToWriteInFile);
//        fw.write("\n");

        //convert solution to string
        String solutionAsString="";
        //write solution the maze file at second line
        //first time write with comma
        solutionAsString+=((MazeState)(solution.getSolutionPath().get(0))).getPosition().getRowIndex()+",";
        solutionAsString+=((MazeState)(solution.getSolutionPath().get(0))).getPosition().getColumnIndex();

        for (int i=1;i<solution.getSolutionPath().size();i++) {
            solutionAsString +=","+ ((MazeState) (solution.getSolutionPath().get(i))).getPosition().getRowIndex();
            solutionAsString +=","+ ((MazeState) (solution.getSolutionPath().get(i))).getPosition().getColumnIndex();

        }
        //write solution to file
        fw.write(solutionAsString);
        fw.flush();

        return solution;
    }
}
