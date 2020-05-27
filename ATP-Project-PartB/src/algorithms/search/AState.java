package algorithms.search;

import java.io.Serializable;

public abstract class AState implements Serializable {
    private double cost;
    private AState father;
    private boolean visited;


    public abstract boolean equals(Object o);

    public AState(){
    this.cost =0 ;
    this.father = null;
    this.visited = false;
    }


    public AState getFather() {
        return father;
    }

    public void setFather(AState father) {
        this.father = father;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

//    public String getState() {
//        return state;
//    }

//    public void setState(String state) {
//        this.state = state;
//    }


    public void turnVisitedOn() {
        this.visited = true;
    }

    public boolean isVisited() {
        return visited;
    }
}
