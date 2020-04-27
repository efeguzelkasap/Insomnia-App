package com.example.insomniafinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class DisplayPost extends AppCompatActivity {

    TextView emotion;
    Button savePost;
    String newString;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    DocumentReference documentReference;
    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_post);

        emotion = findViewById(R.id.editText2);
        savePost = findViewById(R.id.savePostButton);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        documentReference = fStore.collection("users").document(userID);


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                newString = null;
            } else {
                //Get values from other activity.
                newString = extras.getString("g");
                position = extras.getInt("p");
            }
        } else {
            newString = (String) savedInstanceState.getSerializable("g");
        }

        emotion.setText(newString);

        //Delete current post from firestore and update it with the new one
        savePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                documentReference.update("posts", FieldValue.arrayRemove(emotion.getText().toString()));
                newString = emotion.getText().toString();
                documentReference.update("posts", FieldValue.arrayUnion(newString));
                Intent returnIntent = new Intent();
                returnIntent.putExtra("g",newString);
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });


    }
}
