package tools;

import ontology.Types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by Raluca on 05-Nov-17.
 */
public class ArrayEntropy<T> {
    public double entropy;
    ArrayList<T> elements;
    HashMap<T, Integer> countArray; //count how many times unique elements appear

    public ArrayEntropy(ArrayList<T> elements) {
        countArray = new HashMap<T, Integer>();
        this.elements = elements;
        calculate(elements);
    }

    private HashMap<T, Integer> calculate(ArrayList<T> elements) {
        if (elements != null && !elements.isEmpty()) {
            for (T e : elements) {
                if (countArray.containsKey(e)) {
                    countArray.put(e, countArray.get(e) + 1);
                } else {
                    countArray.put(e, 1);
                }
            }
            int[] array = new int[countArray.keySet().size()];
            int i = 0;
            for (Object j : countArray.values().toArray()) {
                array[i] = (int) j;
            }
            calculate(array);
        }

        return countArray;
    }

    private void calculate (int[] countArray) {
        entropy = 0;
        for (Integer aCountArray : countArray) {
            double p = 1.0 * aCountArray / elements.size();
            if (aCountArray > 0) {
                entropy -= p * (Math.log(p) / Math.log(2));
            }
        }
    }

    public double getPercentage(T element) {
        if (!countArray.containsKey(element)) return -1;
        return 1.0 * countArray.get(element) / elements.size();
    }

    public String toString() {
        String s = "" + entropy + " ";
        for (T element : countArray.keySet()) {
            s += String.format("%.2f",getPercentage(element)) + " ";
        }
        return s;
    }
}
