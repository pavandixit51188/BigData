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
 * This is a reducer class which is used for reduce side join operations.
 * */
public class EntityReducer3 extends Reducer<Text, FloatWritable, Text, FloatWritable>{

private static final TreeMap<Float, List<Text>> top10Map = new TreeMap<Float, List<Text>>();
	
	private static int counter = 0;
	
	/*
	 * Function : reduce
	 * arguments: Text, Iterable<FloatWritable>, Context
	 * This is a reduce function which is used to calculate average ratings and TreeMap is used to calculate 
	 * top 10 values. 
	 * */
	public void reduce(Text key, Iterable<FloatWritable> values, Context context) throws IOException, InterruptedException{
		int counter = 0;
		float avg = 0.0f;
		
		for (FloatWritable value : values) {
		
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
	 * arguments: Context
	 * This function is used for cleanup activities and replace the ## with whitespace from the intermediate file results.
	 * This function will write top 10 values only.
	 * */
	protected void cleanup(Context context) throws IOException, InterruptedException{
		int counter = 0;
		for (Float k : top10Map.descendingKeySet()) {
			List<Text> lst = top10Map.get(k);
			
			for(Text key : lst){
				if(counter > 9)
					break;
				context.write(new Text(key.toString().replaceAll("##", " ")), new FloatWritable(k));
				counter++;
				
			}
			
			if(counter > 9)
				break;
			
		}
	}
}
