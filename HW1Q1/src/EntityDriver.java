import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/*
 * This is a driver class where job configuration is done
 * */
public class EntityDriver{

	public static void main(String[] args) throws Exception {

		if(args.length != 2){
			System.out.println("Two parameters are required <input-dir> <output-dir>");
			System.exit(-1);
		}
		
		Job job = new Job();
		job.setJobName("Entity Counter");
		
		job.setJarByClass(EntityDriver.class);
		
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		job.setMapperClass(EntityMapper1.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		job.setReducerClass(EntityReducer.class);
		
		
		System.exit(job.waitForCompletion(true) ? 0:1);
		
	}

}
