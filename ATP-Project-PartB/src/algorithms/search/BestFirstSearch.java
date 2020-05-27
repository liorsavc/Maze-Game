package algorithms.search;

import java.util.Comparator;
import java.util.PriorityQueue;

public class BestFirstSearch extends BreadthFirstSearch{


//    private PriorityQueue<AState> queue;

    public BestFirstSearch(){

        this.collection = new PriorityQueue<AState>(new AstateComparator());

    }




    @Override
    public String getName() {
        return "BestFirstSearch";
    }


}
