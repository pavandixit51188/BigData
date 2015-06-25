import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/*
 * This is a reducer class.
 * */
public class EntityReducer extends Reducer<Text, IntWritable, Text, IntWritable>{

	/*
	 * function : reduce
	 * arguments: Text, Iterable<IntWritable>, Context
	 * This mapper function will count the total counts of the review, business and user keys.
	 * Key-Value pair is (either review or business or user) and value is total count
	 * */
	public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException{
	
		int sum = 0;
		for(IntWritable value : values){
			sum += value.get();
		}
		
		context.write(key, new IntWritable(sum));
	}
}
