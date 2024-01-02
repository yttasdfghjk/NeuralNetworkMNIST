package feedforwardnetwork;

public class Utilities {
	
    public static double getRandomValue(double min, double max){
        return Math.random()*(max-min) + min;
    }

    
    public static Integer[] getRandomValues(int min, int max, int amount) {
        min --;
        if(amount > (max-min)){
            return null;
        }

        Integer[] random_values = new Integer[amount];
        for(int i = 0; i< amount; i++){
            int v = (int)(Math.random() * (max-min+1) + min);
            while(containsVal(random_values, v)){
                v = (int)(Math.random() * (max-min+1) + min);
            }
            random_values[i] = v;
        }
        return random_values;
    }

    
    public static double[] generateArray(int size, double initial_value){
        if(size < 1){
            return null;
        }
        double[] array = new double[size];
        for(int i = 0; i < size; i++){
            array[i] = initial_value;
        }
        return array;
    }

    
    public static double[] generateRandomArray(int size, double min, double max){
        if(size < 1){
            return null;
        }
        double[] array = new double[size];
        for(int i = 0; i < size; i++){
            array[i] = getRandomValue(min,max);
        }
        return array;
    }

    
    public static double[][] generateRandomArray(int size_x, int size_y, double min, double max){
        if(size_x < 1 || size_y < 1){
            return null;
        }
        double[][] array = new double[size_x][size_y];
        for(int i = 0; i < size_x; i++){
            array[i] = generateRandomArray(size_y, min, max);
        }
        return array;
    }

    
    public static int getIdxOfMaxValue(double[] values){
        int idx = 0;
        int size = values.length;
        for(int i = 1; i < size; i++){
            if(values[i] > values[idx]){
                idx = i;
            }
        }
        return idx;
    }
    
    
    public static <T extends Comparable<T>> boolean containsVal(T[] array, T value){
        for(int i = 0; i < array.length; i++){
            if(array[i] != null){
                if(value.compareTo(array[i]) == 0){
                    return true;
                }
            }
        }
        return false;
    }
}
