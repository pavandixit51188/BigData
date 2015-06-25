import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/*
 * This is a mapper class which is used to list all business ids which are located in Palo Alto.
 * */
public class EntityMapper extends Mapper<LongWritable, Text, Text, IntWritable>{

	public static final IntWritable one = new IntWritable(1);
	private Text word = new Text();
	
	/*
	 * function : map
	 * arguments: LongWritable, text, Context
	 * This map function is used to generate business ids which has location in Palo alto
	 * */
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException{
		
		String line = value.toString();
		
		//StringTokenizer itr = new StringTokenizer(line, "::");
		
		/*int counter = 0;
		String temp;
		while(itr.hasMoreTokens()){
			temp = itr.nextToken();
			counter++;
			
			if(counter == 4 && "Palo Alto".equalsIgnoreCase(temp)){
				break;
			}
		}
		*/
		if(line.split("::")[3].contains("Palo Alto")){
			word.set(line.split("::")[2]);
			context.write(word, one);
		}
		
		
		
	}
}
