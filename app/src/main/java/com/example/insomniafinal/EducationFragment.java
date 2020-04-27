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

        final String[] items = {"What is insomnia?", "What causes insomnia?", "How to prevent insomnia", "Sleep Environment", "How Drug and alcohol use effect sleep", "Effects", "Symptoms", "Different Types Of Insomnia"};
        final String[] itemContents = {

                "Insomnia is a condition that effects how a person sleeps in a negative way and is a\n" +
                        "leading cause in mental health problems and relationship issues.\n\nAbout 30 percent of adults have symptoms of insomnia and " +
                        "about 10 percent of adults have insomnia that is severe enough to cause daytime consequences. Less than 10 percent of adults are likely to have chronic insomnia.",

                "Currently the most common causes for insomnia are stress, anxiety, mild depression,"+
                        "maladaptive conditioning, and disturbances of the sleep-wake pattern.\n\n" + "Insomnia can be caused by psychiatric and medical conditions, unhealthy sleep habits, specific substances, and/or certain biological factors. Recently, researchers have begun to think about insomnia as a problem of your brain being unable to stop being awake (your brain has a sleep cycle and a wake cycle when one is turned on the other is turned off.\n\ninsomnia can be a problem with either part of this cycle: too much wake drive or too little sleep drive). It's important to first understand what could be causing your sleep difficulties.",

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

                "Temperature:\nHaving your bedroom too hot or too cold can seriously effect your sleep. The most ideal temperature to have your room at is around 16 - 18\u2109 \nElderly people and young children may need a slightly warmer environment so investing in a room thermometer  will only lead to better rest and sleep." +
                "\n\nLights:\nThe sleep environment should be as dark as possible and there should not be any sort of light in your room as this can effect your sleep. Black out curtains are a common and useful product to use when limiting the about of light in your bedroom." +
                "\n\nSound:\nThere is only so much control we have over noises from traffic and neighbors. Many people use white noise such as recordings of soothing sounds to exclude any unwanted sounds that we cannot control. Another option are ear plugs so that any outside noise will be decreased leading to a better sleep",

                "Sleep problems have been associated with medication use, drug abuse, and withdrawal from drugs. Sleep disturbances also have been linked to the use of alcohol and to alcoholism.\n\n" +
                        "Drugs and Sleep:\n" +

                        "Many prescription and nonprescription drugs can cause sleep problems. The severity of sleep problems caused by a drug will vary from person to person.\n" +

                        "Prescription drugs that may cause sleep problems include:\n\n" +

                        "\u2022High blood pressure drugs like beta blockers\n\n" +
                        "\u2022Hormones such as oral contraceptives\n\n" +
                        "\u2022Steroids, including prednisone\n\n" +
                        "\u2022Inhaled respiratory drugs\n\n" +
                        "\u2022Diet pills\n\n" +
                        "\u2022Seizure medications\n\n" +
                        "\u2022Attention deficit hyperactivity disorder stimulant medications\n\n" +
                        "\u2022Some antidepressants\n\n" +

                        "The following nonprescription drugs can cause sleep problems:\n\n" +

                        "\u2022Pseudoephedrine, including the brand Sudafed\n\n" +
                        "\u2022Medications with caffeine. These include the brands Anacin, Excedrin, and No-Doz, as well as many cough and cold medications.\n\n" +
                        "\u2022Illegal drugs such as cocaine, amphetamines, and methamphetamines.\n\n" +
                        "\u2022Nicotine, which can disrupt sleep and reduce total sleep time. Smokers report more daytime sleepiness and minor accidents than do nonsmokers, especially in younger age groups.\n\n",

                "Having insomnia can lead to many medical conditions such as:\n\n" +
                        "\u2022Stroke\n\n" +
                        "\u2022Asthma attacks\n\n" +
                        "\u2022Seizures\n\n" +
                        "\u2022Weak immune system\n\n" +
                        "\u2022sensitivity to pain\n\n" +
                        "\u2022Inflammation\n\n" +
                        "\u2022Obesity\n\n" +
                        "\u2022Heart disease\n\n" +
                        "\u2022High blood pressure\n\n" +
                "Suffering from insomnia can also increase the risk for mental health disorders such as:\n\n" +
                "\u2022Depression\n\n" +
                "\u2022Anxiety\n\n",
                "Symptoms of insomnia are as follows: \n\n" +
                "\u2022Find it hard to go to sleep\n\n" +
                "\u2022Wake up several times during the night\n\n" +
                "\u2022Lie awake at night\n\n" +
                "\u2022Wake up early and cannot go back to sleep\n\n" +
                "\u2022Still feel tired after waking up\n\n" +
                "\u2022Find it hard to nap during the day even though you are tired\n\n" +
                "\u2022Feel tired and irritable during the day\n\n" +
                "\u2022Find it difficult to concentrate during the day because you are tired\n\n",
                "Acute Insomnia\n\n" +
                        "A brief episode of difficulty sleeping. Acute insomnia is usually caused by a life event, such as a stressful change in a persons job, receiving bad news, or travel. Often acute insomnia resolves without any treatment\n\n" +
                        "Chronic Insomnia\n\n" +
                        "A long-term pattern of difficulty sleeping. Insomnia is usually considered chronic if a person has trouble falling asleep or staying asleep at least three nights per week for three months or longer. Some people with chronic insomnia have a long-standing history of sleeping. Chronic insomnia has many causes.\n\n" +
                        "Comorbid insomnia\n\n" +
                        "Insomnia that occurs with another condition. Psychiatric symptoms — such as anxiety and depression — are known to be associated with changes in sleep. Certain medical conditions can either cause insomnia or make a person uncomfortable at night (as in the case of arthritis or back pain), which may make it hard to sleep."

        };

        listView.setAdapter(new ArrayAdapter<String>(root.getContext(), R.layout.custom_textview, items){

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
