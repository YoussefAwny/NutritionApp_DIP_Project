package com.example.spoon;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.Vector;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;


public class Profile_Home extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView captured_image;
    Button capture_button;
    Button ok_captured_button;
    EditText amount_text;
    LinearLayout buttons_layout;
    Bitmap input_image;
    Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    int month = calendar.get(Calendar.MONTH)+1;
    int year = calendar.get(Calendar.YEAR);
    TextView date_text,energy_text,protein_text,carbs_text,fat_text,fibre_text,sodium_text;
    ArrayList<Data> data;
    Profile current_profile;
    Data current_data;
    int amount;

    static {
        if (!OpenCVLoader.initDebug()) {
            // Handle initialization error
            Log.i("mark","error");
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__home);

        Bundle pname = getIntent().getExtras();
        if (pname== null){
            return;
        }
        String profile_name = pname.getString("profile_name");
        current_profile = Profile.getProfile(MainActivity.profiles, profile_name);
        capture_button= (Button) findViewById(R.id.capture_button);
        captured_image = (ImageView) findViewById(R.id.imageView);
        //Disable the button if the user has no camera
        if(!hasCamera())
            capture_button.setEnabled(false);
        date_text = (TextView)findViewById(R.id.date_text);
        energy_text = (TextView)findViewById(R.id.energy_text);
        protein_text = (TextView)findViewById(R.id.protein_text);
        carbs_text = (TextView)findViewById(R.id.carbs_text);
        fat_text= (TextView)findViewById(R.id.fat_text);
        fibre_text = (TextView)findViewById(R.id.fibre_text);
        sodium_text = (TextView)findViewById(R.id.sodium_text);


        data = MainActivity.dbHandler.getData();
        current_data = new Data();
        boolean flag = false;
        for (Data d : data)
        {
            if (d.getDay()==day&&d.getMonth()==month&& d.getYear()==year && d.getId()==current_profile.get_id())
            {
                flag = true;
                current_data.setId(d.getId());
                current_data.setDay(day);
                current_data.setMonth(month);
                current_data.setYear(year);
                current_data.setEnergy(d.getEnergy());
                current_data.setProtein(d.getProtein());
                current_data.setCarbs(d.getCarbs());
                current_data.setFat(d.getFat());
                current_data.setFibre(d.getFibre());
                current_data.setSodium(d.getSodium());
            }
        }

        if(!flag)
        {
            current_data.setId(current_profile.get_id());
            current_data.setDay(day);
            current_data.setMonth(month);
            current_data.setYear(year);
            current_data.setEnergy(0);
            current_data.setProtein(0);
            current_data.setCarbs(0);
            current_data.setFat(0);
            current_data.setFibre(0);
            current_data.setSodium(0);
            MainActivity.dbHandler.addData(current_data);
            data.add(current_data);

        }

        date_text.setText(Integer.toString(current_data.getDay())+"/"+Integer.toString(current_data.getMonth())+"/"+Integer.toString(current_data.getYear()));
        energy_text.setText(Integer.toString(current_data.getEnergy()));
        protein_text.setText(Integer.toString(current_data.getProtein()));
        carbs_text.setText(Integer.toString(current_data.getCarbs()));
        fat_text.setText(Integer.toString(current_data.getFat()));
        fibre_text.setText(Integer.toString(current_data.getFibre()));
        sodium_text.setText(Integer.toString(current_data.getSodium()));


        final Spinner history_list = (Spinner)findViewById(R.id.history_list);
        ArrayList<String> history_items = new ArrayList<>();
        for (Data d : data)
        {
            if(d.getId()==current_profile.get_id())
            {
                if(d.getDay()==day&& d.getMonth()==month&&d.getYear()==year)
                    history_items.add(0,Integer.toString(day)+"/"+Integer.toString(month)+"/"+Integer.toString(year));
                else
                    history_items.add(Integer.toString(d.getDay())+"/"+Integer.toString(d.getMonth())+"/"+Integer.toString(d.getYear()));
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, history_items);
        history_list.setAdapter(adapter);
        history_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                String selected_date = history_list.getSelectedItem().toString();
                int selected_day = Integer.valueOf(selected_date.substring(0,selected_date.indexOf("/")));
                int pos1 = selected_date.indexOf("/");
                int selected_month = Integer.valueOf(selected_date.substring(pos1+1,selected_date.indexOf("/",pos1+1)));
                int pos2 = selected_date.indexOf("/",pos1+1);
                int selected_year = Integer.valueOf(selected_date.substring(pos2+1));

                for (Data d : data)
                {
                    if(d.getId()==current_profile.get_id())
                    {
                        if(d.getDay()==selected_day&& d.getMonth()==selected_month&&d.getYear()==selected_year)
                        {
                            current_data=d;
                            date_text.setText(Integer.toString(current_data.getDay())+"/"+Integer.toString(current_data.getMonth())+"/"+Integer.toString(current_data.getYear()));
                            energy_text.setText(Integer.toString(current_data.getEnergy()));
                            protein_text.setText(Integer.toString(current_data.getProtein()));
                            carbs_text.setText(Integer.toString(current_data.getCarbs()));
                            fat_text.setText(Integer.toString(current_data.getFat()));
                            fibre_text.setText(Integer.toString(current_data.getFibre()));
                            sodium_text.setText(Integer.toString(current_data.getSodium()));
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }

    //Check if the user has a camera
    private boolean hasCamera(){
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }
    //Launching the camera
    public void launchCamera(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //Take a picture and pass results along to onActivityResult
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    //If you want to return the image taken
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            //Get the photo
            Bundle extras = data.getExtras();
            Bitmap photo = (Bitmap) extras.get("data");
            input_image = photo;
            captured_image.setImageBitmap(photo);
            ok_captured_button = new Button(this);
            ok_captured_button.setText("Ok");
            amount_text = new EditText(this);
            amount_text.setWidth(50);
            amount_text.setInputType(InputType.TYPE_CLASS_NUMBER);
            Toast.makeText(this, "Please Enter amount in grams", Toast.LENGTH_LONG).show();
            buttons_layout = (LinearLayout)findViewById(R.id.buttons_layout);
            buttons_layout.addView(amount_text);
            buttons_layout.addView(ok_captured_button);
            ok_captured_button.setEnabled(true);
            ok_captured_button.setOnClickListener(
                    new Button.OnClickListener(){
                        public void onClick(View v){

                            buttons_layout.removeView(ok_captured_button);
                            captured_image.setImageResource(0);
                            amount = Integer.parseInt(amount_text.getText().toString());
                            buttons_layout.removeView(amount_text);
                            BoxDetection B =  new BoxDetection();
                            Mat result1 =B.findRectangle(input_image);
                            Bitmap temp= Bitmap.createBitmap(result1.cols(),result1.rows(),Bitmap.Config.ARGB_8888);
                            Utils.matToBitmap(result1,temp);
                            captured_image.setImageBitmap(temp);
                            //Result from Slice after box detection
                            Log.i("mark", "hii2");
                            Slice S = new Slice();
                            Vector<Mat> result2 =S.run(result1);
                            Log.i("mark", "size: " +result2.size());
                            //Display the Images after cropping from slice
                            for (Mat m :result2)
                            {
                                Log.i("mark", "hii");
                                ArrayList<String> current_slice;
                                Bitmap temp1= Bitmap.createBitmap(m.cols(),m.rows(),Bitmap.Config.ARGB_8888);
                                captured_image.setImageBitmap(temp1);
                                Utils.matToBitmap(m,temp1);
                                current_slice=detectText(input_image);
                                for (String s: current_slice)
                                    Log.i("mark", "text: "+s);

                            }

                        }
                    }
            );

        }
    }

    public ArrayList<String> detectText(Bitmap textBitmap) {
        ArrayList<String> final_result = new ArrayList<>();
        TextRecognizer textRecognizer = new TextRecognizer.Builder(this).build();

        if (!textRecognizer.isOperational()) {
            new AlertDialog.Builder(this)
                    .setMessage("Text recognizer could not be set up on your device :(")
                    .show();
            return final_result;
        }

        Frame frame = new Frame.Builder().setBitmap(textBitmap).build();
        SparseArray<TextBlock> text = textRecognizer.detect(frame);


        for (int i = 0; i < text.size(); ++i) {
            TextBlock item = text.valueAt(i);
            if (item != null && item.getValue() != null) {
                final_result.add(item.getValue());
            }
        }
        return final_result;
    }

}
