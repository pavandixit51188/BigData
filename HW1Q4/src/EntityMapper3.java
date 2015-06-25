import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/*
 * This is a mapper class which is used for job chaining.
 * */
public class EntityMapper3 extends Mapper<LongWritable, Text, Text, FloatWritable>{

	/*
	 * function : map
	 * arguments: LongWritable, Text, Context
	 * This is a mapper function which will create ratings as value and other field as key
	 * */
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException{
		
		StringTokenizer itr = new StringTokenizer(value.toString());
		Float key1 = 0.0f;
		String value1 = "";
		while(itr.hasMoreTokens()){
			value1 = itr.nextToken().toString();
			key1 = new Float(itr.nextToken().toString());
		}
		
		context.write(new Text(value1), new FloatWritable(key1));
	}
}
