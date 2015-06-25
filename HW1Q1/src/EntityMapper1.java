import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


public class EntityMapper1 extends Mapper<LongWritable, Text, Text, IntWritable>{

	private Text word = new Text("BUNTY123");
	public static final IntWritable one = new IntWritable(1);
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException{
		String line = value.toString();
		StringTokenizer itr = new StringTokenizer(line, "::");
		
		String temp = line.split("::")[22];
		if("review".equalsIgnoreCase(temp)){
			word.set("review");
		}
		else if("business".equalsIgnoreCase(temp)){
			word.set("business");
		}
		else if("user".equalsIgnoreCase(temp)){
			word.set("user");
		}
		
		context.write(word, one);
		
		/*while(itr.hasMoreTokens()){
			String temp = itr.nextToken();
			if("review".equalsIgnoreCase(temp)){
				word.set("review");
			}
			else if("business".equalsIgnoreCase(temp)){
				word.set("business");
			}
			else if("user".equalsIgnoreCase(temp)){
				word.set("user");
			}
		}
		if(!"BUNTY123".equalsIgnoreCase(word.toString()))
			context.write(word, one);
			*/	
	}
}
