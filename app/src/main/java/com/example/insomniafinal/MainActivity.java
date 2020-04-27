package com.example.insomniafinal;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;




public class MainActivity extends AppCompatActivity {

    ChipNavigationBar chipNavigationBar;
    FragmentManager fragmentManager;
    HomeFragment homeFragment;
    DiaryFragment diaryFragment;
    EducationFragment educationFragment;
    QuestionnaireFragment questionnaireFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chipNavigationBar = findViewById(R.id.navBar);

        //Initialise the fragments for first time.
        if (savedInstanceState == null) {
            chipNavigationBar.setItemSelected(R.id.home, true);
            fragmentManager = getSupportFragmentManager();
            homeFragment = new HomeFragment();
            diaryFragment = new DiaryFragment();
            educationFragment = new EducationFragment();
            questionnaireFragment = new QuestionnaireFragment();
            fragmentManager.beginTransaction().add(R.id.fragment_container, homeFragment).commit();
        }

        //Switch between fragments.
        //Instead of replacing fragment we hide the fragments we are not using and show the one we are to save on performance.
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {

                switch (i) {

                    case R.id.education:
                        displayEducationFragment();
                        break;

                    case R.id.home:
                        displayHomeFragment();
                        break;

                    case R.id.diary:
                        displayDiaryFragment();
                        break;

                    case R.id.Question:
                        displayQuestionnaireFragment();
                        break;
                }
            }
        });
    }

    protected void displayHomeFragment()
    {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if (homeFragment.isAdded())
        {
            ft.show(homeFragment);
            ft.hide(educationFragment);
            ft.hide(diaryFragment);
            ft.hide(questionnaireFragment);
        }
        else
        {
            ft.add(R.id.fragment_container, homeFragment);
            ft.show(homeFragment);
        }

        if(homeFragment.isHidden())
        {
            ft.show(homeFragment);
            ft.hide(educationFragment);
            ft.hide(diaryFragment);
            ft.hide(questionnaireFragment);
        }

        ft.commit();

    }

    protected void displayDiaryFragment()
    {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if(diaryFragment.isAdded())
        {
            ft.show(diaryFragment);
            ft.hide(educationFragment);
            ft.hide(homeFragment);
            ft.hide(questionnaireFragment);
        }
        else
        {
            ft.add(R.id.fragment_container, diaryFragment);
            ft.show(diaryFragment);
            ft.hide(homeFragment);
            ft.hide(educationFragment);
            ft.hide(questionnaireFragment);
        }

        if(diaryFragment.isHidden())
        {
            ft.show(diaryFragment);
            ft.hide(educationFragment);
            ft.hide(homeFragment);
            ft.hide(questionnaireFragment);
        }

        ft.commit();

    }

    protected void displayEducationFragment()
    {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (educationFragment.isAdded())
        {
            ft.show(educationFragment);
            ft.hide(homeFragment);
            ft.hide(diaryFragment);
            ft.hide(questionnaireFragment);
        }
        else
        {
            ft.add(R.id.fragment_container, educationFragment);
            ft.show(educationFragment);
            ft.hide(homeFragment);
            ft.hide(diaryFragment);
            ft.hide(questionnaireFragment);
        }

        if(educationFragment.isHidden())
        {
            ft.show(educationFragment);
            ft.hide(homeFragment);
            ft.hide(diaryFragment);
            ft.hide(questionnaireFragment);
        }

        ft.commit();
    }

    protected void displayQuestionnaireFragment()
    {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (questionnaireFragment.isAdded())
        {
            ft.show(questionnaireFragment);
            ft.hide(homeFragment);
            ft.hide(diaryFragment);
            ft.hide(educationFragment);
        }
        else
        {
            ft.add(R.id.fragment_container, questionnaireFragment);
            ft.show(questionnaireFragment);
            ft.hide(homeFragment);
            ft.hide(diaryFragment);
            ft.hide(educationFragment);
        }

        if(questionnaireFragment.isHidden())
        {
            ft.show(questionnaireFragment);
            ft.hide(homeFragment);
            ft.hide(diaryFragment);
            ft.hide(educationFragment);
        }

        ft.commit();
    }




}