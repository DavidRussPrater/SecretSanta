package com.example.prate.secretsanta;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MainActivityUnitTests extends MainActivity {

    View mockedEditText = mock(View.class);

    MainActivity mainActivity = new MainActivity();
    String names[] = {"Mike", "David", "Tim", "Chris", "Jesus"};
    ArrayList<Person> people = new ArrayList<>(Arrays.asList(
            new Person(names[0], 1),
            new Person(names[1], 1),
            new Person(names[2], 1),
            new Person(names[3], 1),
            new Person(names[4], 1)
    ));


    // Test to check MainActivity is not null
    @Test
    public void mainActivityShouldNotBeNull() {
        assertNotNull(mainActivity);
    }


    @Test
    public void testPeopleListWorks(){
        assertSame(names[0], "Mike");
        System.out.println(names[0]);
    }

}