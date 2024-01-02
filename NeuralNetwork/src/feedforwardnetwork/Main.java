package feedforwardnetwork;

import mnist.MnistImageFile;
import mnist.MnistLabelFile;
import java.io.File;


public class Main {

    public static void main(String[] args) {
        NeuralNetwork mlp = new NeuralNetwork(784, 40, 30, 10);
        TrainSet trainset = generateTrainSet(0,4999);
        training(mlp, trainset, 50, 50, 100);

        TrainSet testset = generateTrainSet(5000,9999);
        testing(mlp, testset, 10);
    }

    public static TrainSet generateTrainSet(int from_idx, int to_idx) {

        TrainSet trainset = new TrainSet(784, 10);

        try {

            String path = new File("").getAbsolutePath();

            MnistImageFile images = new MnistImageFile(path + "/data/t10k-images.idx3-ubyte", "rw");
            MnistLabelFile labels = new MnistLabelFile(path + "/data/t10k-labels.idx1-ubyte", "rw");

            for(int i = from_idx; i <= to_idx; i++) {
                if(i % 100 ==  0){
                    System.out.println("generated rows from file: " + i);
                }

                double[] input = new double[784];
                double[] output = new double[10];

                output[labels.readLabel()] = 1d;
                for(int r = 0; r < 784; r++){
                    input[r] = (double)images.read() / (double)256;
                }

                trainset.appendData(input, output);
                images.next();
                labels.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
         return trainset;
    }

    public static void training(NeuralNetwork network,TrainSet trainset, int epochs, int iterations, int batch_size) {
        for(int e = 0; e < epochs; e++) {
            network.train(trainset, iterations, batch_size);
            System.out.println("");
            System.out.println(">>>>>>>>>>>>>        Epoch: "+ e+ "        <<<<<<<<<<<<");
        }
    }

    public static void testing(NeuralNetwork network, TrainSet trainset, int printAccuracy) {
        int correct_predictions = 0;
        for(int i = 0; i < trainset.getSize(); i++) {

            double predicted_value = Utilities.getIdxOfMaxValue(network.process(trainset.getInput(i)));
            double target_value = Utilities.getIdxOfMaxValue(trainset.getOutput(i));
            if(predicted_value == target_value) {
                correct_predictions ++ ;
            }
            if(i % printAccuracy == 0) {
                System.out.println(i + ": " + ((double)correct_predictions / (double) (i + 1))*100);
            }
        }
        System.out.println("Model was tested succesfully.  RESULT: " + correct_predictions + " / " + trainset.getSize()+ "  ->    Accuracy: " + ((double)correct_predictions / (double)trainset.getSize())*100 +" %");
    }
}
