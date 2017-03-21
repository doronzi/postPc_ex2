package com.example.and.todolist;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    EditText inputPlace;
    ListView messagesToShow;
    boolean flagFirst = true;
    ArrayAdapter<String> adapter;
    ArrayList<String> messagesArr = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        messagesToShow = (ListView) findViewById(R.id.lvItems);
        inputPlace = (EditText) findViewById(R.id.etNewItem);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, messagesArr);
        messagesToShow.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                writeToList();

            }
        });
        messagesToShow.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                showDialog(pos,adapter);
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        writeToList();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void writeToList(){
        String input = inputPlace.getText().toString();
        if (!input.trim().equals("")) {

            messagesArr.add(input);
            colorList();
            adapter.notifyDataSetChanged();
            messagesToShow.setSelection(adapter.getCount() - 1);
            inputPlace.setText("");

        }
    }
    public void showDialog(final int pos, final ArrayAdapter adpat) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Delete");

        builder.setMessage("Do you want to remove this task?");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                messagesArr.remove(pos);
                colorList();
                adpat.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    public void colorList(){
        int color;
        int tmp = messagesToShow.getLastVisiblePosition() - messagesToShow.getFirstVisiblePosition();
        for(int i = 0; i < messagesToShow.getChildCount(); i++) {
            color = i % 2 != 0 ? Color.RED : Color.BLUE;
            messagesToShow.setSelection(i);
            View v = messagesToShow.getChildAt(i);
            v.setBackgroundColor(color);
        }
    }



}



