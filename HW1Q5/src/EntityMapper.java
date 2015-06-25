import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/*
 * This is a mapper class.
 * */
public class EntityMapper extends Mapper<LongWritable, Text, Text, Text>{

	public static List<String> businessList = new ArrayList<String>();
	private BufferedReader reader;
	
	
	/*
	 * Function : setup
	 * arguments: Context
	 * This function is used to get cache file name from the Distribution cache.
	 * */
	protected void setup(Context context) throws IOException, InterruptedException{
		
		Path[] cacheLocalFiles = DistributedCache.getLocalCacheFiles(context.getConfiguration());
		
		//context.write(new Text(cacheLocalFiles[0].toString()), new Text("--cache file name"));
		for(Path eachPath : cacheLocalFiles){
			//context.write(new Text("in for each loop"), new Text("--pavan"));
			loadBusinessList(eachPath, context);
		}
	}
	
	/*
	 * Function : loadBusinessList
	 * Arguments : Path, Context
	 * This function is used to create Business List which has a city Stanford
	 */
	private void loadBusinessList(Path filePath, Context context) throws IOException{
		String strLineRead = "";
		
		try{
			String fileName = filePath.getName();
			File file = new File(fileName+"");
			reader = new BufferedReader(new FileReader(file));
			//context.write(new Text("loadBusinessList getting called"), new Text("Priya"));
			while((strLineRead = reader.readLine()) != null){
				String businessArray[] = strLineRead.split("::");
				if("Stanford".equalsIgnoreCase(businessArray[12]))
					businessList.add(new String(businessArray[2]));
			}
		}catch(Exception e){
			
		}finally{
			if(reader != null){
				reader.close();
			}
		}
	}
	/*
	 * Function : map
	 * Arguments : LongWritable, text, Context
	 * This mapper function is used to list the user ids and review text who reviewd business id which are 
	 * present in business list. Distribution cache is used for business which is small file.
	 */
	
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException{
		
		String strLine = value.toString();
		
		String businessId = strLine.split("::")[2];
		
		//context.write(new Text(businessList.toString()), new Text(businessId));
		if(businessList.contains(businessId)){
			context.write(new Text(strLine.split("::")[8]), new Text(strLine.split("::")[1]));
		}
	}
	
	/*
	 * Function : cleanup
	 * Arguments : Context
	 * This function is used for cleanup activities. This will get executed at the end of last key value 
	 * pair processed in mapper function.
	 */
	public void cleanup(Context context) throws IOException, InterruptedException{
		//context.write(new Text("Pavan cleanup is working!"), new Text("--Pavan"));
	}
}
