package com.example.insomniafinal;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DiaryFragment extends Fragment {

    View view;
    ChipNavigationBar chipNavigationBar;
    FragmentManager fragmentManager;
    EditText userInput;
    SwipeMenuListView listView;
    ArrayList<String> usersEmotions;
    ArrayAdapter listAdapter;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    DocumentReference documentReference;
    AnyChartView anyChartView;
    HashMap hashMap;
    Button submitButton;

    public DiaryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_diary, container, false);
        chipNavigationBar = view.findViewById(R.id.navBar);
        userInput = view.findViewById(R.id.editText);
        listView = view.findViewById(R.id.listView);
        anyChartView = view.findViewById(R.id.any_chart_view);
        submitButton = view.findViewById(R.id.submitButton);

        usersEmotions = new ArrayList<>();
        hashMap = new HashMap();

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        documentReference = fStore.collection("users").document(userID);
        listAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, usersEmotions);

        populateList();
        listView.setAdapter(listAdapter);

        // When submit button plessed firestore array updated with new value and the list is populated with the new value.
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newEntry = userInput.getText().toString();

                if (userInput.length() != 0) {
                    userInput.setText("");
                    usersEmotions.add(newEntry);
                    listView.setAdapter(listAdapter);

                    // Add new values inputed by user and add the currentEmotion at the end to parse later on for retreival of that number.
                    documentReference.update("posts", FieldValue.arrayUnion(newEntry));


                    populateList();

                }
            }
        });


        //Create swipemenu to allow for edit and delete swipe options.
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getActivity().getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0x00, 0x66,
                        0xff)));
                // set item width
                openItem.setWidth(170);
                // set item title
                openItem.setIcon(R.drawable.ic_edit);
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getActivity().getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(170);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        listView.setMenuCreator(creator);

        // Case 0 if the user decides to edit the post and case 1 if they choose to delete.
        // Case 0 starts a new activity and send the neccesary data to allow the user to read all the text and edit where needed.
        // Case 1 updates the firestore array and the list view cell corresponding with that value is also deleted then the list is repopulated with the new array.
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        String itemChosen = listView.getItemAtPosition(position).toString();
                        Intent i = new Intent(getContext(), DisplayPost.class);
                        i.putExtra("g", itemChosen);
                        i.putExtra("p", position);
                        startActivityForResult(i, 1);
                        //startActivity(new Intent(getApplicationContext(),DisplayPostActivity.class));
                        documentReference.update("posts", FieldValue.arrayRemove(listView.getItemAtPosition(position).toString()));
                        break;
                    case 1:
                        documentReference.update("posts", FieldValue.arrayRemove(listView.getItemAtPosition(position).toString()));
                        usersEmotions.remove(listView.getItemAtPosition(position).toString());
                        //earnings.remove(position);
                        listView.setAdapter(listAdapter);
                        listAdapter.remove(listView.getItemAtPosition(position).toString());
                        populateList();
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {

            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    populateList();
                }
            }
        }
    }

    // PopulateList gets the data from the array in firestore and populates the values array to populate and update the pie chart.
    protected void populateList() {
        listAdapter.clear();

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        //Gets array from firestore and assign those values to userEmotions list.
                        usersEmotions = (ArrayList<String>) document.get("posts");

                        // Parse int from end of array value to store in values array.
                        for(String n : usersEmotions){

                            listAdapter.add(n);

                        }
                        listView.setAdapter(listAdapter);

                    }
                }
            }
        });
    }

}
