//Omkar Kulkarni 11925596
package com.example.todo;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class todo extends AppCompatActivity {

    private EditText task = null,des=null;
            String fulldate;
    private Button date=null;
    private Calendar cal=null;
    private DatePickerDialog datePickerDialog=null;
    private TextView showdate=null;
    int day,month,year,d,m,i=0;
    private FirebaseAuth mAuth;
    ListView listView = null;
    DatabaseReference db;
    FirebaseUser user;
    String userid;
    List<TaskInfo> tasksList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.todo );

        //wire the objects
        task  = findViewById( R.id.editText );
        des = findViewById( R.id.editText3 );
        date = findViewById( R.id.date );
        showdate = findViewById( R.id.textView9 );
        listView = findViewById( R.id.listview );
        tasksList = new ArrayList<>(  );

        //declare authorization instance
        mAuth = FirebaseAuth.getInstance();
        //create a database instance
        db = FirebaseDatabase.getInstance().getReference("Users");
        user = mAuth.getCurrentUser();
        userid = user.getUid();


        //Create data picker window
        date.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cal = Calendar.getInstance();
                day = cal.get(Calendar.DAY_OF_MONTH);
                month = cal.get(Calendar.MONTH);
                year = cal.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog( todo.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        showdate.setText( day+"/"+month+"/"+year );
                        fulldate = day+"/"+month+"/"+year;
                    }
                },year, month,day);
                datePickerDialog.show();
            }
        } );
        /*
        listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TaskInfo temp = new TaskInfo(  );
                String selectedItem = temp.getDescription();
                Toast.makeText( getApplicationContext(), selectedItem,Toast.LENGTH_SHORT ).show();
                Log.d( "Item","inItem" );
            }
        } );*/
    }

    //this function is invoked whenever thw current window is started
    @Override
    protected void onStart() {
        super.onStart();
        db.addValueEventListener( new ValueEventListener() {
            //function which is invoked anytime data is changed in the database cloud
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tasksList.clear();//clears the list if any previous data still present

                //Iterating to the user specific task in the database
                for(DataSnapshot taskSnapshot:dataSnapshot.getChildren()) {
                    //Log.d( "KEY", " " + taskSnapshot.getKey()+"="+userid );

                    //This will allow only the user data to be fetched
                    if(taskSnapshot.getKey().equals(userid)) {
                        for (DataSnapshot task1Snapshot : taskSnapshot.getChildren()) {
                            String getTask = task1Snapshot.getValue( TaskInfo.class ).getTask();
                            String getDes = task1Snapshot.getValue( TaskInfo.class ).getDescription();
                            String getDate = task1Snapshot.getValue( TaskInfo.class ).getDate();
                            String getTid = task1Snapshot.getKey();
                            //Log.d( "KEY", " " + task1Snapshot.getKey() );
                            TaskInfo tasklist = new TaskInfo( getTask, getDes, getDate ,getTid);

                            tasksList.add( tasklist );
                            //pushing the data in the custom adapter
                            ListAdapter adapter = new ListAdapter( todo.this, tasksList );
                            listView.setAdapter( adapter );

                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );

    }

    //function to add new task in the database
    public void addtask(View v)
    {
        task  = findViewById( R.id.editText );
        des = findViewById( R.id.editText3 );
        if(task.getText()==null || des.getText()==null || fulldate==null)
        {
            Toast.makeText( getApplicationContext(),"All fields are mandatory",Toast.LENGTH_SHORT ).show();
        }
        else {
            //adds the data to the database
            TaskInfo taskinfo = new TaskInfo( " " + task.getText(), " " + des.getText(), fulldate, "N/A" );
            FirebaseDatabase.getInstance().getReference( "Users" )
                    .child( FirebaseAuth.getInstance().getCurrentUser().getUid() )
                    .push().setValue( taskinfo );
            Toast.makeText( getApplicationContext(),"Task added",Toast.LENGTH_SHORT ).show();
        }
    }

    //for logging out the user
    public void onLogout(View v)
    {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText( getApplicationContext(),"You are logged out.",Toast.LENGTH_SHORT ).show();
        finish();
        startActivity( new Intent( this,LoginActivity.class ) );
    }

}
