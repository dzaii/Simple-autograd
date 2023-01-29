import NeuralNetwork.MLP;
import engine.Value;

public class Main {
    public static void main(String[] args) {


        Value[][] inputs = {{new Value(2),   new Value(3),  new Value(-1)}  //input 1 with 3 values
                           ,{new Value(3),   new Value(-1), new Value(0.5)} //input 2
                           ,{new Value(0.5), new Value(1),  new Value(1)}   //etc.
                           ,{new Value(1),   new Value(1),  new Value(-1)}};

        Value[] targets = {new Value(1),   //target for input 1
                           new Value(-1),  //target for input 2
                           new Value(-1),  //...
                           new Value(1)};


        //Initializing multilayer perceptron
        MLP n = new MLP(inputs[0].length, // number of input layer neurons (3 in this case)
                        new int[]{4, 4, 1}); //array containing number of outputs for each layer


        //Printing initial values
        System.out.println("    Initial output     |   Target output ");
        System.out.println("-".repeat(40));
        for(int i =0; i< inputs.length; i++) {
            System.out.println( (n.output(inputs[i])[0].data + "")
                                 + " ".repeat(22 -(n.output(inputs[i])[0].data + "").length()) + "|"
                                 + " ".repeat(7 ) + targets[i].data);
        }

        //Training parameters
        int iterations = 1000;
        double stepSize = 0.05;

        //Training the NN
        for(int k=0; k<iterations; k++) {

            //Calculating total loss
            Value loss = n.output(inputs[0])[0].subtract(targets[0]).square();

            for (int i = 1; i < targets.length; i++) {
                Value value = n.output(inputs[i])[0].subtract(targets[i]);
                loss = loss.add(value.square());
            }
            //Zeroing gradients of all parameters
            for(Value p : n.parameters()){
                p.gradient = 0;
            }

            //Calculating new gradients using backward propagation
            loss.backwards();

            //Adjusting parameters to minimize the loss
            for(Value p : n.parameters()){
                p.data += -stepSize * p.gradient;
            }
        }

        //Printing final values
        System.out.println("\nTraining with " + iterations + " iterations with step size of " + stepSize+"...\n");
        System.out.println("     Final output     |   Target output ");
        System.out.println("-".repeat(40));
        for(int i =0; i< inputs.length; i++) {
            System.out.println( (n.output(inputs[i])[0].data + "")
                                 + " ".repeat(22 -(n.output(inputs[i])[0].data + "").length())+ "|"
                                 + " ".repeat(7 ) + targets[i].data);
        }
    }
}