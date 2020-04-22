package com.example.insomniafinal;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;


/**
 * A simple {@link Fragment} subclass.
 */
public class EducationFragment extends Fragment {

    private ListView listView;
    private View root;
    ChipNavigationBar chipNavigationBar;
    FragmentManager fragmentManager;

    public EducationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_education, container, false);
        listView = root.findViewById(R.id.topicListView);
        chipNavigationBar = root.findViewById(R.id.navBar);

        final String[] items = {"What is insomnia?", "What causes insomnia?", "How to prevent insomnia", "Sleep Environment", "How Drug and alcohol use effect sleep", "Effects", "Symptoms", "Risk factors", "Different Types Of Insomnia"};
        final String[] itemContents = {

                "Insomnia is a condition that effects how a person sleeps in a negative way and is a\n" +
                        "leading cause in mental health problems and relationship issues. About 30 percent of adults have symptoms of insomnia " +
                        "About 10 percent of adults have insomnia that is severe enough to cause daytime consequences Less than 10 percent of adults are likely to have chronic insomnia.",

                "Currently the most common causes for insomnia are stress, anxiety, mild depression,"+
                        "maladaptive conditioning, and disturbances of the sleep-wake pattern." + "Insomnia can be caused by psychiatric and medical conditions, unhealthy sleep habits, specific substances, and/or certain biological factors. Recently, researchers have begun to think about insomnia as a problem of your brain being unable to stop being awake (your brain has a sleep cycle and a wake cycle—when one is turned on the other is turned off—insomnia can be a problem with either part of this cycle: too much wake drive or too little sleep drive). It's important to first understand what could be causing your sleep difficulties.",

                "Good sleep habits can help prevent insomnia and promote sound sleep:\n" +
                        "\n" +
                        "Keep your bedtime and wake time consistent from day to day, including weekends.\n\n" +
                        "Stay active — regular activity helps promote a good night's sleep.\n\n" +
                        "Check your medications to see if they may contribute to insomnia.\n\n" +
                        "Avoid or limit naps.\n\n" +
                        "Avoid or limit caffeine and alcohol, and don't use nicotine.\n\n" +
                        "Avoid large meals and beverages before bedtime.\n\n" +
                        "Make your bedroom comfortable for sleep and only use it for sex or sleep.\n\n" +
                        "Create a relaxing bedtime ritual, such as taking a warm bath, reading or listening to soft music.\n\n",

                "Temperature:\nHaving your bedroom too hot or too cold can seriously effect your sleep. The most ideal temperature to have your room at is around 16 - 18\u2109 \nElderly people and young children may need a slightly warmer environment so investing in a room thermometer  will only lead to better rest and sleep.",

                "Sleep problems have been associated with medication use, drug abuse, and withdrawal from drugs. Sleep disturbances also have been linked to the use of alcohol and to alcoholism.\n" +
                        "Drugs and Sleep\n" +

                        "Many prescription and nonprescription drugs can cause sleep problems. The severity of sleep problems caused by a drug will vary from person to person.\n" +

                        "Prescription drugs that may cause sleep problems include:\n" +

                        "    High blood pressure drugs like beta blockers\n" +
                        "    Hormones such as oral contraceptives\n" +
                        "    Steroids, including prednisone\n" +
                        "    Inhaled respiratory drugs\n" +
                        "    Diet pills\n" +
                        "    Seizure medications\n" +
                        "    Attention deficit hyperactivity disorder stimulant medications\n" +
                        "    Some antidepressants\n" +

                        "The following nonprescription drugs can cause sleep problems:\n" +

                        "    Pseudoephedrine, including the brand Sudafed\n" +
                        "    Medications with caffeine. These include the brands Anacin, Excedrin, and No-Doz, as well as many cough and cold medications.\n" +
                        "    Illegal drugs such as cocaine, amphetamines, and methamphetamines.\n" +
                        "    Nicotine, which can disrupt sleep and reduce total sleep time. Smokers report more daytime sleepiness and minor accidents than do nonsmokers, especially in younger age groups.\n",

                "Effects",
                "Symptoms",
                "Risk Factors"

        };


        listView.setAdapter(new ArrayAdapter<String>(root.getContext(), android.R.layout.simple_list_item_1, items){

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = ((TextView) view.findViewById(android.R.id.text1));
                textView.setMinHeight(0); // Min Height
                textView.setMinimumHeight(0); // Min Height
                textView.setHeight(300); // Height
                textView.setTextSize(20);
                return view;
            }

        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String itemChosen = itemContents[i];
                Intent intent = new Intent(getActivity(), InformationActivity.class);
                intent.putExtra("g", itemChosen);
                startActivityForResult(intent, 1);

            }



        });



        // Inflate the layout for this fragment
        return root;
    }
}
