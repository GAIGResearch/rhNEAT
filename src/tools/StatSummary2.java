package tools;

import java.util.ArrayList;
import java.util.Collections;

import static RHEA.utils.Constants.*;

/**
 This class is used to model the statistics
 of a fix of numbers.  For the statistics
 we choose here it is not necessary to store
 all the numbers - just keeping a running total
 of how many, the sum and the sum of the squares
 is sufficient (plus max and min, for max and min).

 */

public class StatSummary2 {

    ArrayList<Double> values;

    private double sum;
    private double sumsq;
    private double min;
    private double max;

    private double mean;
    private double median;
    private double q1, q3;
    private double entropy;
    private double sd;

    private int wins;

    private double lastAdded;

    // trick class loader into loading this now
    // private static StatisticalTests dummy = new StatisticalTests();

    int n;
    boolean valid;

    public StatSummary2() {
        n = 0;
        sum = 0;
        sumsq = 0;
        wins = 0;
        min = Double.POSITIVE_INFINITY;
        max = Double.NEGATIVE_INFINITY;
        valid = false;
        values = new ArrayList<>();
    }

    public final void reset() {
        n = 0;
        sum = 0;
        sumsq = 0;
        wins = 0;
        min = Double.POSITIVE_INFINITY;
        max = Double.NEGATIVE_INFINITY;
        values.clear();
    }


    public ArrayList<Double> getValues() {
        return values;
    }

    public double max() {
        if (max == Double.NEGATIVE_INFINITY)
            return -1;
        return max;
    }

    public double min() {
        if (min == Double.POSITIVE_INFINITY)
            return -1;
        return min;
    }

    public double mean() {
        if (!valid)
            computeStats();
        return mean;
    }

    // returns the sum of the squares of the differences
    //  between the mean and the ith values
    public double sumSquareDiff() {
        return sumsq - n * mean() * mean();
    }


    private void computeStats() {
        if (!valid && n != 0) {
            mean = sum / n;
            double num = sumsq - (n * mean * mean);
            if (num < 0) {
                // avoids tiny negative numbers possible through imprecision
                num = 0;
            }
            sd = Math.sqrt(num / (n - 1));

            //calc entropy
            entropy = new ArrayEntropy(values).entropy;

            //calc median
            ArrayList<Double> sortedValues = new ArrayList<>();
            sortedValues.addAll(values);
            Collections.sort(sortedValues);

            if (!sortedValues.isEmpty()) {
                int middle = sortedValues.size() / 2;
                middle = middle % 2 == 0 ? (middle > 0 ? middle - 1 : middle) : middle;
                median = sortedValues.get(middle);
            } else {
                median = 0;
            }

            //calc q1 and q3
            if (!sortedValues.isEmpty()) {
                int middle1 = sortedValues.size() / 4;
                middle1 = middle1 % 2 == 0 ? (middle1 > 0 ? middle1 - 1 : middle1) : middle1;
                q1 = sortedValues.get(middle1);
            } else {
                q1 = 0;
            }

            if (!sortedValues.isEmpty()) {
                int middle2 = sortedValues.size() * 3 / 4;
                middle2 = middle2 % 2 == 0 ? (middle2 > 0 ? middle2 - 1 : middle2) : middle2;
                q3 = sortedValues.get(middle2);
            } else {
                q3 = 0;
            }

            valid = true;
        }
    }

    public double sd() {
        if (!valid)
            computeStats();
        return sd;
    }

    public int n() {
        return n;
    }

    public double stdErr() {
        if (n != 0)
            return sd() / Math.sqrt(n);
        else return 0;
    }

    public void add(double d) {
        if (d != HUGE_POSITIVE && d != HUGE_NEGATIVE) {
            n++;
            sum += d;
            sumsq += d * d;
            min = Math.min(min, d);
            max = Math.max(max, d);
            valid = false;
            lastAdded = d;
            values.add(d);
        }
    }

    public double median() {
        if (!valid)
            computeStats();
        return median;
    }

    public double entropy() {
        if (!valid)
            computeStats();
        return entropy;
    }

    public double q1() {
        if (!valid)
            computeStats();
        return q1;
    }

    public double q3() {
        if (!valid)
            computeStats();
        return q3;
    }

    public double getLastAdded() {return lastAdded;}

    //MIN MAX MEAN Q1 MEDIAN Q3 SD STDERR ENTROPY
    @Override
	public String toString() {
        return String.format("%.2f",min()) + " " + String.format("%.2f",max()) + " " + String.format("%.2f",mean())
                + " " + String.format("%.2f",q1()) + " " + String.format("%.2f",median()) + " "
                + String.format("%.2f",q3()) + " " + String.format("%.2f",sd()) + " " + String.format("%.2f",stdErr())
                + " " + String.format("%.2f", entropy());

    }

    public double sum(){
        return sum;
    }

    public StatSummary2 copy()
    {
        StatSummary2 ss = new StatSummary2();

        ss.sum = this.sum;
        ss.sumsq = this.sumsq;
        ss.min = this.min;
        ss.max = this.max;
        ss.mean = this.mean;
        ss.median = this.median;
        ss.q1 = this.q1;
        ss.q3 = this.q3;
        ss.sd = this.sd;
        ss.n = this.n;
        ss.valid = this.valid;
        ss.wins = this.wins;
        ss.lastAdded = this.lastAdded;

        return ss;
    }
}