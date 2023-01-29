package NeuralNetwork;

import engine.Value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public class Neuron {

    //Neuron parameters
    Value[] weights;
    Value bias;

    Neuron(int numOfInputs) {
        this.weights = new Value[numOfInputs];
        for (int i = 0; i < numOfInputs; i++) {
            Random rng = new Random();
            weights[i] = new Value(rng.nextDouble(-1, 1));
            bias = new Value(rng.nextDouble(-1, 1));
        }
    }

    //Calculating single output using inputs and Neuron parameters
        public Value forward(Value[] inputs){

            Value[] weightsAndBiases = new Value[inputs.length];
            for(int i =0; i<inputs.length;i++){
                weightsAndBiases[i] = weights[i].multiply(inputs[i]);
            }

            Value activation = this.bias.add(Value.sum(weightsAndBiases));

            return activation.tanh();
        }

        public ArrayList<Value> parameters(){

           ArrayList<Value> parameters = new ArrayList<>(Arrays.stream(this.weights).toList());
           parameters.add(this.bias);

           return parameters;
        }
}
