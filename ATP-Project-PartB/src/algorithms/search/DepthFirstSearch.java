package algorithms.search;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

public class DepthFirstSearch extends ASearchingAlgorithm {

    private Stack<AState> stack;

    public DepthFirstSearch(){
        super();
        this.collection =new Stack<AState>();
        this.stack = ((Stack<AState>)this.collection);
    }
    @Override
    public AState search(ISearchable s) {
        // init stack for LIFO
        AState curV = null;
        // init Hashset for visited states
        HashSet<AState> visitedStates = new HashSet<AState>();
        // put start state into stack
        stack.push(s.getStartState());
        //while stack dont empty
        while (!stack.empty()) {
            // pop from stack and add element to visited
            curV = stack.pop();
            // visitednodes++
            this.increaseVisitedStates();
            // check if pop the goal state
            if (curV.equals(s.getGoalState()))
                return curV;
            visitedStates.add(curV);
            // get elements neighbors
            // loop over all neigbors
            for (AState neighbor:s.getAllPossibleStates(curV)) {
                // add the ones which are not visited to stack
                if (!visitedStates.contains(neighbor)){
                    stack.push(neighbor);
                    neighbor.setFather(curV);
                }


            }


        }
        return null;

    }


    @Override
    public String getName() {
        return "DepthFirstSearch";
    }



}
