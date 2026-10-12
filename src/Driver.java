import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Driver {

    public static void main(String[] args) throws Exception {
        String input = args[0];
        String output = args[1];
        String job2Output = args[2];

        Configuration config = new Configuration();
        Job job = Job.getInstance(config, "sum_per_category_per_day");

        job.setJarByClass(SumMapper.class);
        job.setMapperClass(SumMapper.class);
        job.setReducerClass(SumReducer.class);
        job.setCombinerClass(SumReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(DoubleWritable.class);

        FileInputFormat.addInputPath(job, new Path(input));
        FileOutputFormat.setOutputPath(job, new Path(output));

        boolean job1Result = job.waitForCompletion(true);
        if (!job1Result)
            System.exit(1);

        Configuration job2Config = new Configuration();
        Job job2 = Job.getInstance(job2Config, "get_category_leader");

        job2.setJarByClass(LeaderMapper.class);
        job2.setMapperClass(LeaderMapper.class);
        job2.setReducerClass(LeaderReducer.class);
        
        job2.setNumReduceTasks(1);

        job2.setOutputKeyClass(Text.class);
        job2.setOutputValueClass(DoubleWritable.class);

        FileInputFormat.addInputPath(job2, new Path(output));
        FileOutputFormat.setOutputPath(job2, new Path(job2Output));

        boolean job2Result = job2.waitForCompletion(true);

        System.exit(job2Result ? 0 : 1);
    }
}