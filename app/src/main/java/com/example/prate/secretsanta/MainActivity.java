package com.example.prate.secretsanta;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    public static HashMap<String, String> createHashMap(ArrayList<String> names) {

        Random random = new Random();
        HashMap<String, String> secretSantaHashMap = new HashMap<>();
        List<String> namesListCopy = new ArrayList<>(names);

        //For each element in names pass in the element
        for (String currentName : names) {
            List<String> namesList = new ArrayList<>(namesListCopy);

            namesList.remove(currentName);
            int randomInt = Math.abs(random.nextInt(namesList.size()));
            String randomName = namesList.get(randomInt);
            secretSantaHashMap.put(currentName, randomName);
            namesListCopy.remove(randomName);

            System.out.println("Random int " + randomInt);

        }


        return secretSantaHashMap;
    }

    public ArrayList<String> createArrayList() {
        EditText familyMembersEditText = findViewById(R.id.family_members_edit_text);
        String familyMembersNames = familyMembersEditText.getText().toString();
        String namesArray[] = familyMembersNames.split(", ");
        ArrayList<String> names = new ArrayList<>(Arrays.asList(namesArray));

        return names;
    }



    public void updateSecretSantaTextView(View view) {

        // Initialize a string builder for appending each key value pair with a arrow (key --> value)
        StringBuilder secretSantaBuilder = new StringBuilder();

        //Find the secret santa text view and assign it to the variable secret_santa_text_view
        TextView secretSantaTextView = findViewById(R.id.secret_santa_text_view);
        //Create the variable to update the secret santa text view
        String secretSantaText;

        //Create a new array list of strings from the names edit text an
        ArrayList<String> names = createArrayList();

        //Create secret santa HashMap from the names ArrayList
        HashMap<String, String> secretSantaHashMap = createHashMap(names);


        // Break the HashMap into it's ke
        for (Map.Entry<String, String> entry : secretSantaHashMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            secretSantaBuilder.append(key + " --> " + value + "\n");

        }

        secretSantaText = secretSantaBuilder.toString();

        secretSantaTextView.setText(secretSantaText);

        //names.clear();

    }

}

