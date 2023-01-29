package NeuralNetwork;

import engine.Value;

import java.util.ArrayList;

//Collection of layers
public class MLP {
    Layer[] layers;

    //Creating layers and their neurons
    public MLP(int numOfInputs, int[] numOfOutputs){
        this.layers = new Layer[numOfOutputs.length];

        this.layers[0]= new Layer(numOfInputs, numOfOutputs[0]);

        for(int i=0; i < (numOfOutputs.length-1) ;i++){
            layers[i+1] = new Layer(numOfOutputs[i],numOfOutputs[i+1]);
        }

    }
    //Passing input through all the layers and returning output of the final layer
    public Value[] output(Value[] input){
        Value[] output = input;
        for(Layer l: layers){
            output = l.forward(output);
        }
        return output;
    }

    //Returns weights and biases of all the Neurons in the network
    public ArrayList<Value> parameters(){
        ArrayList<Value> parameters = new ArrayList<>();

        for (Layer l : layers){
            parameters.addAll(l.parameters());
        }
        return parameters;
    }
}
