import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


public class EntityMapper3 extends Mapper<LongWritable, Text, Text, FloatWritable>{

	
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
