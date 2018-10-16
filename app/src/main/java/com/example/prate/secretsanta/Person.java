package com.example.prate.secretsanta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class Person implements Serializable {

    String name;

    //    int familyNumber;
    // Set up variable for how often a participant can have the same secret santa
    int yearsConstraint;

    // Create an array list to store previous secret Santas
    ArrayList<Person> previousSecretSantas = new ArrayList<>();
    //Create an array list to store excluded participants for a given person
    HashSet<Person> excludedPeople = new HashSet<>(Collections.singletonList(this));

    // Create a constructor
    Person(String name) {
        this.name = name;
    }

    // Create a constructor to
    Person(String name, int yearsConstraint) {
        this.name = name;
        this.yearsConstraint = yearsConstraint;
    }

    // This method takes into account the years constraint and a participants secret santa
    public void addSecretSanta(Person secretSanta) {
        /* Get the size of the previousSecretSantas array list and store it as an int representing
         the number of previous secret santas*/
        int numberOfPreviousSantas = previousSecretSantas.size();

        /* If the the number for previous secret Santas is equal to the years constraint remove that
           person from previousSecretSantas ArrayList so they can be picked again*/
        if (numberOfPreviousSantas == yearsConstraint) {
            Person removePerson = previousSecretSantas.get(numberOfPreviousSantas - 1);
            // Remove a previous secret santa if they have met the years constraint
            previousSecretSantas.remove(removePerson);
            excludedPeople.remove(removePerson);
        }

        // Add this years secret Santa to the first index of the previousSecretSantas array list
        previousSecretSantas.add(0, secretSanta);
        excludedPeople.add(secretSanta);
    }
}

