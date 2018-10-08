package soon.domain.one.savdisplace;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    public void addClicked(View view){
        Intent myInt = new Intent(getApplicationContext(), MapsActivity.class);
        startActivity(myInt);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ListView myLV = findViewById(R.id.listView);
        ArrayList<String> myAL = new ArrayList<>();

        ArrayAdapter<String> myAA = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, myAL);
        myLV.setAdapter(myAA);



    }
}
