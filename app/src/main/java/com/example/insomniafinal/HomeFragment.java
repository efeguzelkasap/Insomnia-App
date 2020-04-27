package com.example.insomniafinal;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.SeriesBase;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    AlarmManager alarm_manager;
    TimePicker alarm_timepicker;
    TextView update_text;
    Context context;
    PendingIntent pending_intent;
    int sound;
    long startTime = 0;
    long start = 0;
    float numberOfHours = 0;

    DocumentReference documentReference;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String userID;

    ArrayList<Number> totalSleep = new ArrayList<>();
    ChipNavigationBar chipNavigationBar;
    View view;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        chipNavigationBar = view.findViewById(R.id.navBar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();
        documentReference = firebaseFirestore.collection("users").document(userID);

        populateChart();

        alarm_manager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        alarm_timepicker = (TimePicker) view.findViewById(R.id.timePicker);
        update_text = (TextView) view.findViewById(R.id.update_text);

        final Calendar calendar = Calendar.getInstance();
        final Intent my_intent = new Intent(getActivity(), Alarm_Receiver.class);

        Spinner spinner = (Spinner) view.findViewById(R.id.richard_spinner);
        //Again the editor is not picking up on the alarm_sounds array in strings.xml or the custom format of the array adapter.
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.alarm_sounds, R.layout.custom_textview);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        // Set an onclick listener to the onItemSelected method
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sound = (int) id;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Button alarm_on = (Button) view.findViewById(R.id.alarm_on);

        // create an onClick listener to start the alarm
        alarm_on.setOnClickListener(new View.OnClickListener() {
            //@TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                // setting calendar instance with the hour and minute that we picked
                // on the time picker
                calendar.set(Calendar.HOUR_OF_DAY, alarm_timepicker.getHour());
                calendar.set(Calendar.MINUTE, alarm_timepicker.getMinute());

                // get the int values of the hour and minute
                int hour = alarm_timepicker.getHour();
                int minute = alarm_timepicker.getMinute();

                // convert the int values to strings
                String hour_string = String.valueOf(hour);
                String minute_string = String.valueOf(minute);

                // convert 24-hour time to 12-hour time
                if (hour > 12) {
                    hour_string = String.valueOf(hour - 12);
                }

                if (minute < 10) {
                    //10:7 --> 10:07
                    minute_string = "0" + String.valueOf(minute);
                }

                //Account for the fact that the time could be in the past.
                if(calendar.before(Calendar.getInstance()))
                {
                    calendar.add(Calendar.DATE,1);
                }

                // method that changes the update text Textbox
                set_alarm_text("Alarm set to: " + hour_string + ":" + minute_string);

                // put in extra string into my_intent
                // tells the clock that you pressed the "alarm on" button
                my_intent.putExtra("extra", "alarm on");

                // put in an extra int into my_intent
                // tells the clock that you want a certain value from the drop-down menu/spinner
                my_intent.putExtra("sound", sound);


                // create a pending intent that delays the intent
                // until the specified calendar time
                pending_intent = PendingIntent.getBroadcast(getActivity(), 0,
                        my_intent, PendingIntent.FLAG_UPDATE_CURRENT);

                // set the alarm manager
                alarm_manager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        pending_intent);

                start = System.currentTimeMillis();

            }

        });

        Button alarm_off = (Button) view.findViewById(R.id.alarm_off);

        // create an onClick listener to stop the alarm or undo an alarm set
        alarm_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // method that changes the update text Textbox
                set_alarm_text("Alarm off!");

                // cancel the alarm
                if(pending_intent != null)
                {
                    alarm_manager.cancel(pending_intent);
                }

                // put extra string into my_intent
                // tells the clock that you pressed the "alarm off" button
                my_intent.putExtra("extra", "alarm off");
                // also put an extra int into the alarm off section
                // to prevent crashes in a Null Pointer Exception
                my_intent.putExtra("sound", sound);

                //Get duration of the alarm.
                long end = System.currentTimeMillis();
                long delta = end - start;
                int elapsedSeconds = (int)delta / 1000;
                int mins = elapsedSeconds/60;
                float hours = mins/60;

                if(hours >= 1){
                    documentReference.update("sleep", FieldValue.arrayUnion(hours));
                }


                // stop the ringtone
                getContext().sendBroadcast(my_intent);

                numberOfHours = startTime / 1000;

                populateChart();


            }
        });



        return view;
    }


    public void populateChart()
    {
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        //Gets array from firestore and assign those values to totalsleep list.
                        totalSleep = (ArrayList<Number>) document.get("sleep");

                        final Cartesian cartesian = AnyChart.column();

                        List<DataEntry> data = new ArrayList<>();

                        for(int i = 0; i < totalSleep.size(); i++)
                        {
                            data.add(new ValueDataEntry(i, totalSleep.get(i).intValue()));
                        }

                        AnyChartView anyChartView = view.findViewById(R.id.any_chart_view);

                        Column column = cartesian.column(data);

                        //Simple formating of the chart and its axis.
                        column.tooltip()
                                .titleFormat("{%X}")
                                .position(Position.CENTER_BOTTOM)
                                .anchor(Anchor.CENTER_BOTTOM)
                                .offsetX(0d)
                                .offsetY(5d)
                                .format("${%Value}{groupsSeparator: }");

                        cartesian.animation(true);
                        cartesian.title("Total Sleep");
                        cartesian.yScale().minimum(0d);
                        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
                        cartesian.interactivity().hoverMode(HoverMode.BY_X);

                        cartesian.xAxis(0).title("Day");
                        cartesian.yAxis(0).title("Sleep");
                        anyChartView.setBackgroundColor("#121212");
                        cartesian.background().fill("#121212");
                        column.fill("#fff");


                        anyChartView.setChart(cartesian);

                        // Updates bar chart values with new values after a refresh.
                        final int delayMillis = 500;
                        final Handler handler = new Handler();
                        final Runnable runnable = new Runnable() {
                            public void run() {
                                List<DataEntry> data = new ArrayList<>();
                                for(int i = 0; i < totalSleep.size(); i++)
                                {
                                    data.add(new ValueDataEntry("Day " + (i+1), totalSleep.get(i).intValue()));
                                }
                                cartesian.data(data);

                                handler.postDelayed(this, delayMillis);
                            }
                        };
                        handler.postDelayed(runnable, delayMillis);

                    }
                }
            }
        });
    }

    private void set_alarm_text(String output) {
        update_text.setText(output);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_main, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    public class MyXAxisValueFormatter extends ValueFormatter {

        private String[] mValues;
        public MyXAxisValueFormatter(String[] values){
            this.mValues = values;
        }

        @Override
        public String getAxisLabel(float value, AxisBase axis) {
            return mValues[(int)value];
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.context = null;
    }

    @Override
    public void onResume() {
        super.onResume();

    }




}



