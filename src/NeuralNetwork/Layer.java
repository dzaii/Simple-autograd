package NeuralNetwork;

import engine.Value;

import java.util.ArrayList;


//Collection of Neurons
public class Layer {

    Neuron[] neurons;

    Layer(int numOfInputs, int numOfOutputs){
        this.neurons = new Neuron[numOfOutputs];
        for(int i=0;i<numOfOutputs;i++){
            this.neurons[i] = new Neuron(numOfInputs);
        }
    }

    //Outputting Values of all Neurons in the Layer
    public Value[] forward(Value[] inputs){
        Value[] activation = new Value[this.neurons.length];

        for(int i=0; i<activation.length;i++){
            activation[i]= this.neurons[i].forward(inputs);
        }
        return activation;
    }
    public ArrayList<Value> parameters(){
       ArrayList<Value> parameters = new ArrayList<>();
       for(Neuron n: neurons){
           parameters.addAll(n.parameters());
       }
       return parameters;
    }


}
