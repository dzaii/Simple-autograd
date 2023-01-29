package engine;

import java.util.*;

public class Value {
    public double data;
    public double gradient = 0;

    //Operation that created this node (+,-,*...) used calculate correct derivatives
    char op;

    //Set of nodes used for the creation of the current node.
    // I just realized that children are producing parents...
    Set<Value> children = new HashSet<>();

    //Constructor for leaf nodes
    public Value(double data) {
        this.data = data;
    }

    //Constructor for nodes created using operations on other nodes
    public Value(double data, char op, Value ... children) {
        this.data = data;
        Collections.addAll(this.children, children);
        this.op = op;
    }

    //Operations on nodes
    public Value add(Value x){
        return  new Value(this.data + x.data,  '+',this, x);
    }
    public Value add(double x){
        return this.add(new Value(x));
    }

    public static Value sum(Value ... values){

        double sum =0;

        for(Value v : values){
            sum+= v.data;
        }
        return new Value(sum,'+',values);
    }

    public Value multiply(Value x){
        return  new Value(this.data * x.data,'*', this, x);
    }

    public Value multiply(double x){
        return this.multiply(new Value(x));}

    public Value neg(){
        return this.multiply(-1);
    }

    public Value subtract(Value x){
        return this.add(x.neg());
    }

    public  Value square(){
        return new Value(Math.pow(this.data,2),'^',this);
    }

    public Value tanh(){
        double x = this.data;
        double y = (Math.exp(2*x) - 1)/(Math.exp(2*x) + 1);
        return new Value(y,'t',this);
    }

    //Calculating gradient (derivative) of child nodes
    public void backwardStep(){
        switch (this.op) {
            case '*' -> {
                List<Value> children = this.children.stream().toList();

                Value child1 = children.get(0);
                Value child2 = children.get(1);

                child1.gradient += child2.data * this.gradient;
                child2.gradient += child1.data * this.gradient;

            }
            case '+' -> {
                for (Value child : this.children) {
                    child.gradient += this.gradient;
                }
            }
            case '^' -> {
                List<Value> children = this.children.stream().toList();
                Value child = children.get(0);

                child.gradient = 2*child.data * this.gradient;
            }

            case 't' -> {
                for (Value child : this.children) {
                    child.gradient += (1 - (this.data * this.data)) * this.gradient;
                }
            }
            default -> {
            }
        }
    }

    //Sorting array so that no child can come after parent
    public void topologicalSort(ArrayList<Value> sortedList, Set<Value> visited){

        visited.add(this);

        for( Value child : this.children){
            child.topologicalSort(sortedList,visited);
        }
        sortedList.add(this);
    }

    //Calculating gradients of all descendants
    public void backwards(){
        ArrayList<Value> topoArray = new ArrayList<>();
        Set<Value> visited = new HashSet<>();

        this.topologicalSort(topoArray,visited);
        Collections.reverse(topoArray);

        this.gradient = 1;

        for( Value v : topoArray){
            v.backwardStep();
        }
    }

    @Override
    public String toString() {
        return "engine.Value{" +
                "data=" + data +
                '}';
    }

}
