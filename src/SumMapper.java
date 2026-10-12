import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class SumMapper extends Mapper<LongWritable, Text, Text, DoubleWritable> {

    @Override
    protected void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {

        String data = value.toString();

        if (data.contains("user_id,timestamp,category,amount")) {
            return;
        }

        String[] row = data.split(",");

        String dateString = row[1].substring(0, 10);
        String category = row[2];
        String sumString = row[3];
        
        double sum = Double.parseDouble(sumString);

        String keyData = dateString + " " + category;

        context.write(new Text(keyData), new DoubleWritable(sum));
    }
}