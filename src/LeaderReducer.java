import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class LeaderReducer extends Reducer<Text, DoubleWritable, Text, DoubleWritable> {

    private String bestCategory = "";
    private double bestSum = 0;
    @Override
    protected void reduce(Text key, Iterable<DoubleWritable> values, Context context)
            throws IOException, InterruptedException {
        double sum = 0;

        for (DoubleWritable doubleWritable : values) 
            sum += doubleWritable.get();
        
        if (sum > bestSum) {
            bestSum = sum;
            bestCategory = key.toString();
        }
    }

      @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {
        context.write(new Text(bestCategory), new DoubleWritable(bestSum));
  }
}