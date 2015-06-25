import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;

/*
 * This is a reducer class. This will calculate the average ratings and 
 * the top 10 businesses which has highest ratings
 * */
public class EntityReducer extends Reducer<Text, IntWritable, Text, FloatWritable> {

	private static final TreeMap<Float, List<Text>> top10Map = new TreeMap<Float, List<Text>>();
	
	private static int counter = 0;
	
	/*
	 * function : reduce
	 * arguments: Text, Iterable<IntWritable>, Context
	 * In this reduce function average ratings are calculated and stored in the treemap of size 10.
	 * */
	public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException{
		int counter = 0;
		float avg = 0.0f;
		
		for (IntWritable value : values) {
		
			avg += value.get();
			counter++;
		}
		
		if (counter == 0)
			counter = 1;
		avg = avg / counter;
		
		
		
		if(!top10Map.containsKey(avg)){
			List<Text> t = new ArrayList<Text>();
			t.add(new Text(key));
			top10Map.put(new Float(avg), t);
			//context.write(new Text(key), new FloatWritable(avg));
			//counter ++;
		}
		else{
			top10Map.get(avg).add(new Text(key));
			//context.write(new Text(key), new FloatWritable(avg));
			//counter++;
		}
		
		if(top10Map.size() > 10){
			top10Map.remove(top10Map.firstKey());
			//context.write(new Text("Pavan items are getting removed: "), new FloatWritable(counter));
		}
			
		
		//context.write(key, new FloatWritable(avg));
		
	}
	
	/*
	 * function : cleanup
	 * arguments: context
	 * This is a cleanup function. In this only top 10 entries are written.
	 * */
	protected void cleanup(Context context) throws IOException, InterruptedException{
		int counter = 0;
		for (Float k : top10Map.descendingKeySet()) {
			List<Text> lst = top10Map.get(k);
			
			for(Text key : lst){
				context.write(key, new FloatWritable(k));
				if(counter++ > 10)
					break;
			}
			
			if(counter++ > 10)
				break;
			
		}
	}
}
