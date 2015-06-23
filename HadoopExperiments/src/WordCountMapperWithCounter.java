import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


public class WordCountMapperWithCounter extends Mapper<LongWritable, Text, Text, IntWritable> {

	private static final IntWritable one = new IntWritable(1);
	private Text word = new Text();
		
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException{
		String line = value.toString();
			
		StringTokenizer itr = new StringTokenizer(line);
		while(itr.hasMoreTokens()){
			String tmp = itr.nextToken().toLowerCase();
			if(!(Character.isDigit(tmp.charAt(0)))){
				word.set(tmp);
				context.write(word, one);
			}
			else{
				context.getCounter("BadWordsCounter", "BadRecords").increment(1);
			}
			
		}
			
			
	}
}