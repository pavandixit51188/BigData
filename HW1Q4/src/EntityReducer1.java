import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/*
 * This is a Reducer class which is used for Reduce side join operations.
 * */
public class EntityReducer1 extends Reducer<Text, Text, Text, FloatWritable>{
	
	private Text tKey = new Text();
	private Text tValue = new Text();
	
	private List<Text> businessList = new ArrayList<Text>();
	private List<Text> reviewList = new ArrayList<Text>();
	
	/*
	 * function : reduce
	 * arguments: Text, Iterable<Text>, Context
	 * This is a reducer function which is used to generate review and business list from the input data.
	 * */
	public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException{
		
		businessList.clear();
		reviewList.clear();
		String tmp = "";
		for(Text value : values){
			
			tmp = value.toString().split("::")[0];
			
			if("rev".equalsIgnoreCase(tmp)){
				reviewList.add(new Text(value.toString().split("::")[1]));
			}else if("bus".equalsIgnoreCase(tmp)){
				businessList.add(new Text(value.toString().split("::")[1]));
			}
			
		}
		
		executeJoinLogic(key, context);
	}
	
	/*
	 * function : executeJoinLogic
	 * arguments: Text, Context
	 * In this function join logic is implemented and the whitespaces are replaced with ## symbols.
	 * */
	public void executeJoinLogic(Text key, Context context) throws IOException, InterruptedException{
		
		if(!businessList.isEmpty() && !reviewList.isEmpty()){
			for(Text textB : businessList){
				
				for(Text textR : reviewList){
					
					context.write(new Text(key+"##"+textB.toString().replaceAll(" ", "##")), new FloatWritable(new Float(textR.toString())));
				}
				
				
				
			}
		}
	}
}
