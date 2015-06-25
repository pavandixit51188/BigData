import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/*
 * This is a reducer class which will list the business ids which has location as Palo Alto
 * */
public class EntityReducer extends Reducer<Text, IntWritable, Text, Text>{

	/*
	 * function : reduce
	 * arguments: text, Iterable<IntWritable>, Context
	 * This is a reduce function which will write business ids.
	 * */
	public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException{
		/*int sum = 0;
		
		for (IntWritable value : values) {
			
			sum +=value.get();
		}*/
		
		context.write(key, new Text(""));
	}
}
