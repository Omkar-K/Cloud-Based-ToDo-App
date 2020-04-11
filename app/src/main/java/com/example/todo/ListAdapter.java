//Omkar Kulkarni 11925596
package com.example.todo;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends ArrayAdapter<TaskInfo> {
    private Activity context;
    private List<TaskInfo> TaskList= new ArrayList<>();


    public ListAdapter(Activity context, List<TaskInfo> Task){
        super(context, R.layout.mylist, Task);

        this.context = context;
        this.TaskList = Task;
    }

    //function to populate the list with tasks dynamically
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        LayoutInflater layoutInflater =context.getLayoutInflater();
        View rowView = layoutInflater.inflate( R.layout.mylist,null,true );
        TextView taskname = rowView.findViewById( R.id.tasklistname );
        TextView taskdes = rowView.findViewById( R.id.textView12 );
        TextView taskdate = rowView.findViewById( R.id.textView13 );
        Button done = rowView.findViewById( R.id.done );
        final TaskInfo Details = TaskList.get( position );
       // Log.d( "task"," "+Details.getTask() );
        taskname.setText(Details.getTask());
        taskdes.setText( Details.getDescription() );
        taskdate.setText( Details.getDate() );
        done.setOnClickListener( new View.OnClickListener() {
            //function to delete tasks from database
            @Override
            public void onClick(View view) {
                Toast.makeText( context,"Pressed",Toast.LENGTH_SHORT ).show();
                Log.d( "Tid",Details.getTid() );
                FirebaseDatabase.getInstance().getReference("Users")
                        .child( FirebaseAuth.getInstance().getCurrentUser().getUid() )
                        .child( Details.getTid() )
                        .removeValue();
            }
        } );

        return rowView;
    }

}
