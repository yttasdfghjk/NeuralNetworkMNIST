package feedforwardnetwork;

import java.util.ArrayList;
import java.util.Arrays;


public class TrainSet {

    public final int input_size;
    public final int output_size;

    private ArrayList<double[][]> training_data = new ArrayList<>();

    public TrainSet(int input_size, int output_size) {
        this.input_size = input_size;
        this.output_size = output_size;
    }

    public void appendData(double[] input, double[] anticipated) {
        if(input.length != input_size || anticipated.length != output_size) return;
        training_data.add(new double[][]{input, anticipated});
    }

    public TrainSet getBatch(int size) {
        if(size > 0 && size <= this.getSize()) {
            TrainSet set = new TrainSet(input_size, output_size);
            Integer[] ids = Utilities.getRandomValues(0,this.getSize() - 1, size);
            for(Integer i:ids) {
                set.appendData(this.getInput(i),this.getOutput(i));
            }
            return set;
        }else { 
        	return this;
    
        }
    }

    public String toString() {
        String s = "TrainingSet ["+input_size+ " ; "+output_size+"]\n";
        int idx = 0;
        for(double[][] a:training_data) {
            s += idx +":   "+Arrays.toString(a[0]) +"  >-||-<  "+Arrays.toString(a[1]) +"\n";
            idx++;
        }
        return s;
    }

    public int getSize() {
        return training_data.size();
    }

    public double[] getInput(int idx) {
        if(idx >= 0 && idx < getSize())
            return training_data.get(idx)[0];
        else return null;
    }

    public double[] getOutput(int idx) {
        if(idx >= 0 && idx < getSize())
            return training_data.get(idx)[1];
        else return null;
    }

    public int getInput_size() {
        return input_size;
    }

    public int getOutput_size() {
        return output_size;
    }
}
