package com.example.prate.secretsanta;


import android.provider.Contacts;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;


public class MainActivity extends AppCompatActivity {

    /*
    Create a method that takes the ArrayList of names and randomizes the gifters and giftees and adds
    them as key value pairs in a Ha
     */
    public static HashMap<String, String> createHashMap(ArrayList<String> names) {

        // Create random variable for generating a random int
        Random random = new Random();
        // Create a HashMap to store key value pairs for secret santas
        HashMap<String, String> secretSantaHashMap = new HashMap<>();
        // Create a copy of the secret santa HashMap
        HashMap<String, String> reverseHashMap = new HashMap<>();
        // Create a
        List<String> namesListCopy = new ArrayList<>(names);

        //For each element in names pass in the element for current name
        for (String currentName : names) {
            List<String> namesList = new ArrayList<>(namesListCopy);

            //Remove the current name picked from the names list
            namesList.remove(currentName);
            //Remove the current name picked from the reverse HashMap of names
            namesList.remove(reverseHashMap.get(currentName));
            //Create a random integer to be used to find a specific index in the names list
            int randomInt = random.nextInt(namesList.size());
            // Use the random integer to get a random name at the specified index
            String randomName = namesList.get(randomInt);
            // Add the current name and the random name to the secret santa HashMap as key value pairs
            secretSantaHashMap.put(currentName, randomName);
            // Add the random name and current name to the reverse HashMap of names
            reverseHashMap.put(randomName, currentName);
            // Remove the random name from namesListCopy to prevent a participate from being selected twice
            namesListCopy.remove(randomName);

        }

        // Return the HashMap of secret santas
        return secretSantaHashMap;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    /*
    Create a method to get the names of the participants and add them to an ArrayList
     */
    public ArrayList<Person> createArrayList() {
        // Find the family members edit text and assign it to familyMembersEditText
        EditText familyMembersEditText = findViewById(R.id.family_members_edit_text);
        // Get the names from the familyMembersEditText
        String familyMembersNames = familyMembersEditText.getText().toString();
        // Create an array of names then separate the string of names and add them to array
        String namesArray[] = familyMembersNames.split(", ");
        // Create a new ArrayList of person objects stored as people
        ArrayList<Person> people = new ArrayList<>();

        //
        for (String name : namesArray){
           people.add(new Person(name));

        }

        return people;

    }

    /*
    Create a method to convert the ArrayList to a string and update the secret santa TextView
     */

    public void updateSecretSantaTextView(View view) {

        // Initialize a string builder for appending each key value pair with a arrow (key --> value)
        StringBuilder secretSantaStringBuilder = new StringBuilder();

        //Find the secret santa text view and assign it to the variable secret_santa_text_view
        TextView secretSantaTextView = findViewById(R.id.secret_santa_text_view);

        //Create the variable to store the secret santa text
        String secretSantaText;

        //Create a new array list of strings to store the names from the secret santa EditText
        ArrayList<Person> people = createArrayList();

        SecretSantaMaker maker = new SecretSantaMaker(people);

        // Created a for loop to create the final string to be displayed int the secret santa TextView
        for (Map.Entry<Person, Person> entry : maker.createSecretSantas().entrySet()) {
            // Get the key from the secret santa HashMap
            Person key = entry.getKey();
            // Get the value from the secret santa HashMap
            Person value = entry.getValue();
            // Used the string builder and append to create the final string to be displayed
            secretSantaStringBuilder.append(key.name + " --> " + value.name + "\n");

        }

        // Convert from secret santa string
        secretSantaText = secretSantaStringBuilder.toString();

        // Set the the final text to be displayed
        secretSantaTextView.setText(secretSantaText);

    }

}

