package pc.leduec.sensors;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class Selections extends Activity {
	
	public static boolean[] selection = {false,false,false,false,false,false};
	public static String file = "sensorsData.txt";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selections);
        
        Button apply = (Button)findViewById(R.id.apply);
        apply.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				saveSelections();
				
				EditText et = (EditText)findViewById(R.id.editText1);
				file = et.getEditableText()+".txt";
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
      	Button recording = (Button) findViewById(R.id.recording);
      	recording.setOnClickListener(new OnClickListener() {
      			
      		public void onClick(View v) {
      			Intent selectionIntent = new Intent(v.getContext(), Recording.class);
      			v.getContext().startActivity(selectionIntent);				
      		}
      	});
    }
    
    public void saveSelections(){
    	
    	CheckBox[] cb = {(CheckBox)findViewById(R.id.radioButtonGPS),
    			(CheckBox)findViewById(R.id.radioButtonAccelerometer),
    			(CheckBox)findViewById(R.id.radioButtonMagnetometer),
    			(CheckBox)findViewById(R.id.radioButtonProximity),
    			(CheckBox)findViewById(R.id.radioButtonBrightness),
    			(CheckBox)findViewById(R.id.radioButtonGyroscope)};
    	
    	for (int i = 0; i < selection.length; i++) {
			if (cb[i].isChecked()) {
				selection[i] = true;
			}
		}
    }
}