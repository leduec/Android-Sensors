package pc.leduec.sensors;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Recording extends Activity {
	
	String saveFile="";
	
	boolean stopHasClicked;
	
	//Textformatierungselemente
	private static final String tab = "\t";
	private static final String lineSeperator = System.getProperty("line.separator");
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recording);
        
        Button record = (Button)findViewById(R.id.record);
        Button stop = (Button)findViewById(R.id.stop);
        stop.setEnabled(false);
        stopHasClicked=false;
        
        record.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Button record = (Button)findViewById(R.id.record);
		        Button stop = (Button)findViewById(R.id.stop);
				record.setEnabled(false);
				stop.setEnabled(true);
				
				Thread t = new Thread(new Runnable() {
					
					public void run() {
						while (!stopHasClicked) {
							getData();
							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						
					}
				});
				t.start();
				
			}
		});
        
        stop.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Button record = (Button)findViewById(R.id.record);
		        Button stop = (Button)findViewById(R.id.stop);
		        record.setEnabled(true);
				stop.setEnabled(false);
				
				stopRecord();
			}
		});
        
        //Navigation
        Button sensors = (Button) findViewById(R.id.sensors);
        sensors.setOnClickListener(new OnClickListener() {
      			
        	public void onClick(View v) {
        		Intent selectionIntent = new Intent(v.getContext(), SensorsData.class);
        		v.getContext().startActivity(selectionIntent);				
        	}
      	});
      	Button selections = (Button) findViewById(R.id.selections);
      	selections.setOnClickListener(new OnClickListener() {
      			
      		public void onClick(View v) {
      			Intent selectionIntent = new Intent(v.getContext(), Selections.class);
      			v.getContext().startActivity(selectionIntent);				
      		}
      	});
    }
    
    public void saveRecord(){
    	String filename = Selections.file;
    	String path = Environment.getExternalStorageDirectory()+"/PCexercise5/";
    	File filePath = new File(path);
    	filePath.mkdirs();
    	File file = new File(path, filename);
    	FileOutputStream fos;
    	byte[] data = saveFile.getBytes();
    	try {
    	    fos = new FileOutputStream(file);
    	    fos.write(data);
    	    fos.flush();
    	    fos.close();
    	} catch (FileNotFoundException e) {
    	    // handle exception
    	} catch (IOException e) {
    	    // handle exception
    	}
    }
    
    public void stopRecord(){
    	stopHasClicked=true;
    	try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	saveRecord();
    }
    
    public void getData(){
    	saveFile += new SimpleDateFormat("HH:mm:ss.SSS").format(new Date());
    	if(Selections.selection[0]){saveFile+=tab+SensorsData.valuesGps[0]+tab+SensorsData.valuesGps[1]+tab+SensorsData.valuesGps[2];}
    	if(Selections.selection[1]){saveFile+=tab+SensorsData.valuesAccelerometer[0]+tab+SensorsData.valuesAccelerometer[1]+tab+SensorsData.valuesAccelerometer[2];}
    	if(Selections.selection[2]){saveFile+=tab+SensorsData.valuesMagnetometer[0]+tab+SensorsData.valuesMagnetometer[1]+tab+SensorsData.valuesMagnetometer[2];}
    	if(Selections.selection[3]){saveFile+=tab+SensorsData.valuesProximity[0];}
    	if(Selections.selection[4]){saveFile+=tab+SensorsData.valuesBrightness[0];}
    	if(Selections.selection[5]){saveFile+=tab+SensorsData.valuesGyroscope[0]+tab+SensorsData.valuesGyroscope[1]+tab+SensorsData.valuesGyroscope[2];}
    	saveFile+=lineSeperator;
    }
}