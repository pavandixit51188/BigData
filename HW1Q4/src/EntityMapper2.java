import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/*
 * This is a mapper class which is used for review entity processing.
 * */
public class EntityMapper2 extends Mapper<LongWritable, Text, Text, Text>{

	/*
	 * function : map
	 * arguments: LongWritable, Text Context
	 * This is a mapper function which is used to generate businessid and ratings as key-value pair.
	 * */
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException{
		
		String[] line = value.toString().split("::");
		if("review".equalsIgnoreCase(line[22])){
			context.write(new Text(line[2]), new Text("rev::"+line[20]));
		}
	}
}
