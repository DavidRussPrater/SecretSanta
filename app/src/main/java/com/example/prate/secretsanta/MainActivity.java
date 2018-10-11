package com.example.prate.secretsanta;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    // Initialize an ArrayList to store a HashSet of Person objects
    ArrayList<HashSet<Person>> familyList = new ArrayList<>();
    //Initialized and ArrayList to store Person objects
    ArrayList<Person> people;
    // Defined a counter to be used
    int counter = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    // This method returns an ArrayList of person objects to be used
    public ArrayList<Person> createPeopleList() {
        // Initialize an ArrayList of person object called people
        ArrayList<Person> people = new ArrayList<>();

        for (HashSet<Person> family : familyList) {

            for (Person person : family) {

                person.excludedPeople = family;
                people.add(person);

            }
        }

        return people;
    }


    // Create a new method to
    public void addFamily(View view) {

        // Find the family members edit text and assign it to familyMembersEditText
        EditText familyMembersEditText = findViewById(R.id.family_members_edit_text);
        // Get the names from the familyMembersEditText
        String familyMembersNames = familyMembersEditText.getText().toString();
        // Create an array of names then separate the string of names and add them to array
        String namesArray[] = familyMembersNames.split(", ");
        // Initialize a new hash set of person objects
        HashSet<Person> people = new HashSet<>();

        // Loop through each name in namesArray and add it the the people HashSet
        for (String name : namesArray) {
            //
            Person person = new Person(name, 3);
            //
            people.add(person);
        }
        // Add the list of people to the current families list of names
        familyList.add(people);
        // Set the edit text to empty so the user knows the family has been added
        familyMembersEditText.setText("");

    }

    public void createSecretSanta(View view) {

        // Increment the counter variable to one to allow the people list to be created
        counter++;

            /* If counter equals one assign the people list to the variable people
             This is used when the reset button is clicked because it sets the counter to zero
             that way when the moethod is called again it recreates the people list*/
        if (counter == 1) {
            people = createPeopleList();
        }

        // Find th TextView for the secret santa and assign it to the variable secretSantaTextView
        TextView secretSantaTextView = (TextView) findViewById(R.id.secret_santa_text_view);
        //Create the variable to store the secret santa text
        String secretSantaText;
        // Initialize a string builder for appending each key value pair with a arrow (key --> value)
        StringBuilder secretSantaStringBuilder = new StringBuilder();
        // Call the secret santa maker class
        SecretSantaMaker maker = new SecretSantaMaker(people);
        //Create a secret santa HashMap from the names ArrayList
        HashMap<Person, Person> secretSantaHashMap = maker.createSecretSantas();
        // Created a for loop to create the final string to be displayed int the secret santa TextView
        for (Map.Entry<Person, Person> entry : secretSantaHashMap.entrySet()) {

            // Used the string builder and append to create the final string to be displayed
            secretSantaStringBuilder.append(entry.getKey().name + " --> " + entry.getValue().name + "\n");
            entry.getValue().addSecretSanta(entry.getKey());

        }

        // Convert from secret santa string string builder to a string
        secretSantaText = secretSantaStringBuilder.toString();

        System.out.println("SecretSanta Tag" + " " + secretSantaText);
        // Conditional statement to display no solution if the secret santa text is empty
        if (secretSantaText.isEmpty()) {
            secretSantaTextView.setText(R.string.no_solution);
        } else {
            // Set the the final text to be displayed as long as there is a valid solution
            secretSantaTextView.setText(secretSantaText);
        }

        // Find the main layout int he activity_main file and assign it to a variable
        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.linear_layout);

        // This code closes the on screen soft keyboard after the users clicks submit that way it
        // is easier to view the secret santa list
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);

    }

    // Created a method to reset the secret santa application so a user can start over
    public void resetPeople(View view) {
        // Set the counter to zero that way when addFamily is called again it is in its original
        counter = 0;
        // Find the secret santa text view and set it to blank so the user knows the application has
        // been reset
        TextView secretSantaTextView = (TextView) findViewById(R.id.secret_santa_text_view);
        // Clear the secret santa text so the user knows the reset button has been clicked
        secretSantaTextView.setText("");
        // Clear the familyList
        familyList.clear();

    }

}