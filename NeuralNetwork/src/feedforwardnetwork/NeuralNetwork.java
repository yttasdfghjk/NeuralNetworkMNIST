package feedforwardnetwork;


public class NeuralNetwork {

    private double[][] output;
    private double[][][] weights;
    private double[][] bias;
    private double[][] error;
    private double[][] derivatives;
    public final int[] layer_sizes;
    public final int   network_size;
    public final int   input_dimensions;
    public final int   output_dimensions;

    public NeuralNetwork(int... layer_sizes) {
        this.layer_sizes = layer_sizes;
        this.input_dimensions = layer_sizes[0];
        this.network_size = layer_sizes.length;
        this.output_dimensions = layer_sizes[network_size-1];

        this.output = new double[network_size][];
        this.weights = new double[network_size][][];
        this.bias = new double[network_size][];

        this.error = new double[network_size][];
        this.derivatives = new double[network_size][];

        for(int i = 0; i < network_size; i++) {
            this.output[i] = new double[layer_sizes[i]];
            this.error[i] = new double[layer_sizes[i]];
            this.derivatives[i] = new double[layer_sizes[i]];

            this.bias[i] = Utilities.generateRandomArray(layer_sizes[i], -0.5,0.5);

            if(i > 0) {
                weights[i] = Utilities.generateRandomArray(layer_sizes[i],layer_sizes[i-1], -1,1);
            }
        }
    }



    public void train(TrainSet set, int iterations, int batch_size) {
        if(set.input_size != input_dimensions || set.output_size != output_dimensions) return;
        for(int i = 0; i < iterations; i++) {
            TrainSet batchset = set.getBatch(batch_size);
            for(int b = 0; b < batch_size; b++) {
                this.train(batchset.getInput(b), batchset.getOutput(b), 0.1);
            }
            System.out.println(MSE(batchset));
        }
    }

    private double sigmoid( double x) {
        return 1 / ( 1 + Math.exp(-x));
    }
    
    public double MSE(double[] input, double[] target) {
        if(input.length != input_dimensions || target.length != output_dimensions) return 0;
        process(input);
        double c = 0;
        for(int i = 0; i < target.length; i++) {
            c += (target[i] - output[network_size-1][i]) * (target[i] - output[network_size-1][i]);
        }
        return c / (2 * target.length);
    }

    public double MSE(TrainSet set) {
        double c = 0;
        for(int i = 0; i< set.getSize(); i++) {
            c += MSE(set.getInput(i), set.getOutput(i));
        }
        return c / set.getSize();
    }

    public void train(double[] input, double[] target, double lr) {
        if(input.length != input_dimensions || target.length != output_dimensions) return;
        process(input);
        backprop(target);
        adaptWeights(lr);
    }

    public double[] process(double... input) {
        if(input.length != this.input_dimensions) return null;
        this.output[0] = input;
        for(int layer = 1; layer < network_size; layer ++) {
            for(int neuron = 0; neuron < layer_sizes[layer]; neuron ++) {

                double sum = bias[layer][neuron];
                for(int previous_neuron = 0; previous_neuron < layer_sizes[layer-1]; previous_neuron ++) {
                    sum += output[layer-1][previous_neuron] * weights[layer][neuron][previous_neuron];
                }
                output[layer][neuron] = sigmoid(sum);
                derivatives[layer][neuron] = output[layer][neuron] * (1 - output[layer][neuron]);
            }
        }
        return output[network_size-1];
    }
    
    public void backprop(double[] target) {
        for(int neuron = 0; neuron < layer_sizes[network_size-1]; neuron ++) {
            error[network_size-1][neuron] = (output[network_size-1][neuron] - target[neuron])
                    * derivatives[network_size-1][neuron];
        }
        for(int layer = network_size-2; layer > 0; layer --) {
            for(int neuron = 0; neuron < layer_sizes[layer]; neuron ++){
                double sum = 0;
                for(int next_neuron = 0; next_neuron < layer_sizes[layer+1]; next_neuron ++) {
                    sum += weights[layer + 1][next_neuron][neuron] * error[layer + 1][next_neuron];
                }
                this.error[layer][neuron] = sum * derivatives[layer][neuron];
            }
        }
    }

    public void adaptWeights(double lr) {
        for(int layer = 1; layer < network_size; layer++) {
            for(int neuron = 0; neuron < layer_sizes[layer]; neuron++) {

                double adaption = - lr * error[layer][neuron];
                bias[layer][neuron] += adaption;

                for(int previous_neuron = 0; previous_neuron < layer_sizes[layer-1]; previous_neuron ++) {
                    weights[layer][neuron][previous_neuron] += adaption * output[layer-1][previous_neuron];
                }
            }
        }
    }
}
