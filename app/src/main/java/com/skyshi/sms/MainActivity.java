package com.skyshi.sms;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.skyshi.sms.Adapter.SmsAdapter;
import com.skyshi.sms.Model.SmsModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {
    ArrayList<SmsModel> listSmsModel = new ArrayList<>();
    private static MainActivity inst;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getInbox();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }
        RecyclerView rv = (RecyclerView)findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        SmsAdapter adapter = new SmsAdapter(listSmsModel,MainActivity.this,this);
        rv.setAdapter(adapter);
    }
    public static MainActivity instance(){
        return inst;
    }
    public void updateList(final String smsMessage) {
        listSmsModel.clear();
        getInbox();
    }
    public void getInbox(){
        Uri sms = Uri.parse("content://sms/inbox");
        ContentResolver cr = this.getContentResolver();
        Cursor c = cr.query(sms,new String[]{"address","body","date_sent","date","_id","thread_id"},null,null,"thread_id asc");
        c.moveToFirst();
        String address = "";
        String addres="";
        String body="";
        String date_sent="";
        String date="";
        int count=0;
        do{
            if (address.equalsIgnoreCase("")){
                address = c.getString(0);
            }

            if (address.equalsIgnoreCase(c.getString(0))){
                count+=1;
                addres=c.getString(0);
                body= c.getString(1);
                date_sent= c.getString(2);
                date= c.getString(3);
            }else{
                address = "";
                listSmsModel.add(new SmsModel(addres, body, getDate(Long.parseLong(date_sent), "dd/MM hh:mm"), getDate(Long.parseLong(date), "dd/MM hh:mm"), count));
                count=1;
            }
        }while(c.moveToNext());
        c.close();
        Collections.sort(listSmsModel, new Comparator<SmsModel>() {
            @Override
            public int compare(SmsModel smsModel, SmsModel t1) {
                return t1.getCount() - smsModel.getCount();
            }
        });
        for (int i = 0; i <listSmsModel.size() ; i++) {
            Log.v("tab-les", "address : " + listSmsModel.get(i).getAddress());
            Log.v("tab-les","body : "+listSmsModel.get(i).getBody());
            Log.v("tab-les","date_sent : "+listSmsModel.get(i).getDate_sent());
            Log.v("tab-les","date : "+listSmsModel.get(i).getDate());
            Log.v("tab-les","count : "+listSmsModel.get(i).getCount());
            Log.v("tab-les", "==============================");
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    }public static String getDate(long milliSeconds, String dateFormat)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
}
