import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/*
 * This is a driver class. All job configuration is done in this class.
 * */
public class EntityDriver extends Configured implements Tool{

	private static final String OUTPUT_PATH = "intermediate_output";
	
	public static void main(String[] args) throws Exception {

		int exitCode = ToolRunner.run(new Configuration(), new EntityDriver(), args);
		System.exit(exitCode);
	}

	@Override
	public int run(String[] arg0) throws Exception {
		Configuration conf = getConf();
		
		Job job = new Job(conf, "Entity Driver Join");
		
		job.setJarByClass(EntityDriver.class);
		
		MultipleInputs.addInputPath(job, new Path(arg0[0]), TextInputFormat.class, EntityMapper1.class);
		MultipleInputs.addInputPath(job, new Path(arg0[1]), TextInputFormat.class, EntityMapper2.class);
		
		job.setReducerClass(EntityReducer1.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		
		FileOutputFormat.setOutputPath(job, new Path(OUTPUT_PATH));
		//return (job.waitForCompletion(true) ? 0:1);
		job.waitForCompletion(true);
		
		Configuration conf2 = getConf();
		Job job2 = new Job(conf2, "Entity Driver Chaining");
		
		job2.setJarByClass(EntityDriver.class);
		
		FileInputFormat.addInputPath(job2, new Path(OUTPUT_PATH));
		FileOutputFormat.setOutputPath(job2, new Path(arg0[2]));
		
		job2.setMapperClass(EntityMapper3.class);
		job2.setMapOutputKeyClass(Text.class);
		job2.setMapOutputValueClass(FloatWritable.class);
		
		job2.setReducerClass(EntityReducer3.class);
		
		job2.setNumReduceTasks(1);
		
		job2.setOutputKeyClass(Text.class);
		job2.setOutputValueClass(FloatWritable.class);
		
		return (job2.waitForCompletion(true)? 0:1);
	
	
	}
	

}
