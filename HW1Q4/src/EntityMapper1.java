import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/*
 * This is a mapper class which is used for business entity processing
 * */
public class EntityMapper1 extends Mapper<LongWritable, Text, Text, Text>{

	/*
	 * function : map
	 * arguments: LongWritable, Text, Context
	 * This is a mapper function which is used to generate businessid and (full address and categories) as 
	 * key-value pairs.
	 * */
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException{
		
		String[] line = value.toString().split("::");
		if("business".equalsIgnoreCase(line[22])){
			context.write(new Text(line[2]), new Text("bus::"+line[3]+":"+line[10]));
		}
	}
}
