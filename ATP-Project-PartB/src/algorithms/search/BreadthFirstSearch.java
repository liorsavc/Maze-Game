package algorithms.search;

import java.util.HashSet;
import java.util.LinkedList;

public class BreadthFirstSearch extends ASearchingAlgorithm {

    private AState start;



    public  BreadthFirstSearch(){
        super();
        this.collection =new LinkedList<AState>();
//        this.queue = ((LinkedList<AState>)this.collection);

    }



    @Override
    public AState search(ISearchable s) {
        AState curV = s.getStartState();
        collection.add(curV);
        // hashset for visited states
        HashSet<AState> visitedStates = new HashSet<AState>();

        while(!collection.isEmpty()){
            // until the queue didnt empty , continue
            // label v as discoverd
            visitedStates.add(curV);
            curV = collection.iterator().next();
            collection.remove(curV);
            // visitedstates++
            this.increaseVisitedStates();
            // check if current V is the goal
            if (curV.equals(s.getGoalState())) {
                return curV;
            }
            // get all current V neighbors and apend to queue
            for (AState neighbor:s.getAllPossibleStates(curV)) {
                // for each neighbor we check if visited before
                if (!visitedStates.contains(neighbor)){
                    // neighbor not visited before
                    // add  not visited neighbors of curV to queue and update father
                    neighbor.setFather(curV);
//                    neighbor.setCost(curV.getCost() + neighbor.getCost());
                    collection.add(neighbor);
                    visitedStates.add(neighbor);

                }
            }

        }
        return null;
    }



    @Override
    public String getName() {
        return "BreadthFirstSearch";
    }
}
