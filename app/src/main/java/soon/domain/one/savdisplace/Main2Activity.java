package soon.domain.one.savdisplace;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    // we will have access to these in this activity as well as Maps Activity
    static ArrayList<LatLng> locations = new ArrayList<>();
    static ArrayList<String> myAL = new ArrayList<>();
    static ArrayAdapter<String> myAA;

    public void addClicked(View view){
        Intent myInt = new Intent(getApplicationContext(), MapsActivity.class);
        startActivity(myInt);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ListView myLV = findViewById(R.id.listView);


        // an arrayList to store locations


        myAA = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, myAL);
        myLV.setAdapter(myAA);

        myLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
//             intent.putExtra("placesNumber", i);
                Log.d("Hooli", Integer.toString(i));
                intent.putExtra("placesNumber", i);
               startActivity(intent);
               Toast.makeText(Main2Activity.this, "Yeah", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
