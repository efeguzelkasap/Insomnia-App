package com.example.insomniafinal;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionnaireFragment extends Fragment {

    View view;
    TextView resultsLabel;
    Button submitButton;
    RadioButton alwaysOne, SometimesOne, NeutralOne, AlmostNeverOne, NeverOne;
    RadioButton alwaysTwo, sometimesTwo, NeutralTwo, AlmostNeverTwo, NeverTwo;
    RadioButton alwaysThree, sometimesThree, NeutralThree, AlmostNeverThree, NeverThree;
    RadioButton alwaysFour, sometimesFour, NeutralFour, AlmostNeverFour, NeverFour;
    RadioButton alwaysFive, sometimesFive, NeutralFive, AlmostNeverFive, NeverFive;
    RadioButton alwaysSix, sometimesSix, NeutralSix, AlmostNeverSix, NeverSix;

    public QuestionnaireFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_questionnaire, container, false);

        alwaysOne = view.findViewById(R.id.alwaysOne);
        SometimesOne = view.findViewById(R.id.sometimesOne);
        NeutralOne = view.findViewById(R.id.NeutralOne);
        AlmostNeverOne = view.findViewById(R.id.AlmostNeverOne);
        NeverOne = view.findViewById(R.id.NeverOne);

        alwaysTwo = view.findViewById(R.id.alwaysTwo);
        sometimesTwo = view.findViewById(R.id.sometimesTwo);
        NeutralTwo = view.findViewById(R.id.NeutralTwo);
        AlmostNeverTwo = view.findViewById(R.id.AlmostNeverTwo);
        NeverTwo = view.findViewById(R.id.NeverTwo);

        alwaysThree = view.findViewById(R.id.alwaysThree);
        sometimesThree = view.findViewById(R.id.sometimesThree);
        NeutralThree = view.findViewById(R.id.NeutralThree);
        AlmostNeverThree = view.findViewById(R.id.AlmostNeverThree);
        NeverThree = view.findViewById(R.id.NeverThree);

        alwaysFour = view.findViewById(R.id.alwaysFour);
        sometimesFour = view.findViewById(R.id.sometimesFour);
        NeutralFour = view.findViewById(R.id.NeutralFour);
        AlmostNeverFour = view.findViewById(R.id.AlmostNeverFour);
        NeverFour = view.findViewById(R.id.NeverFour);

        alwaysFive = view.findViewById(R.id.alwaysFive);
        sometimesFive = view.findViewById(R.id.sometimesFive);
        NeutralFive = view.findViewById(R.id.NeutralFive);
        AlmostNeverFive = view.findViewById(R.id.AlmostNeverFive);
        NeverFive = view.findViewById(R.id.NeverFive);

        alwaysSix = view.findViewById(R.id.alwaysSix);
        sometimesSix = view.findViewById(R.id.sometimesSix);
        NeutralSix = view.findViewById(R.id.NeutralSix);
        AlmostNeverSix = view.findViewById(R.id.AlmostNeverSix);
        NeverSix = view.findViewById(R.id.NeverSix);

        resultsLabel = view.findViewById(R.id.resultLabel);

        submitButton = view.findViewById(R.id.submitButton);

        //Too many possibilities so feedback from questionaire will be based on the answer from one question whitch is the "i have difficulty getting to sleep".
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(alwaysFive.isChecked())
                {
                    resultsLabel.setText("You may have symptoms of insomnia and should really try to learn more about getting a good nights sleep.");
                }

                if(sometimesFive.isChecked())
                {
                    resultsLabel.setText("You may have symptoms of insomnia and should really try to learn more about getting a good nights sleep.");
                }

                if(NeutralFive.isChecked())
                {
                    resultsLabel.setText("You may have symptoms of insomnia and should really try to learn more about getting a good nights sleep.");
                }

                if(AlmostNeverFive.isChecked())
                {
                    resultsLabel.setText("You do not show any symptoms of insomnia.");
                }

                if(NeverFive.isChecked())
                {
                    resultsLabel.setText("You do not show any symptoms of insomnia.");
                }


            }
        });










        return view;
    }
}
