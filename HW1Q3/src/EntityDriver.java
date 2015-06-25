import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/*
 * This is a driver class which is used for job configuration.
 * This is the assignment for calculating top 10 entries on the basis of average ratings.
 * */
public class EntityDriver {

	public static void main(String args[]) throws IOException, InterruptedException, ClassNotFoundException{
		
		if(args.length != 2){
			System.out.println("Two parameters are required <input-dir> <output-dir>");
			System.exit(-1);
		}
		
		Job job = new Job();
		
		job.setJobName("Entity Driver");
		job.setJarByClass(EntityDriver.class);
		
		job.setMapperClass(EntityMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		
		job.setReducerClass(EntityReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(FloatWritable.class);
		job.setNumReduceTasks(1);
		
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
