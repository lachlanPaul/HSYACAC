package com.lachlanpaul.hsyacac;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public ArrayList<String> hist = new ArrayList<>();
    public float finalEquation = 0;
    public boolean equationIsFinalised = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView outputBox = findViewById(R.id.output);

        View.OnClickListener buttonListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idName = getResources().getResourceEntryName(view.getId());
                Log.i("Button Pressed:", idName);

                switch (idName) {
                    case "add":
                        hist.add("+");
                    case "subtract":
                        hist.add("-");
                    case "times":
                        hist.add("*");
                    case "divide" :
                        hist.add("/");
                    case "del":
                        hist.remove(-1);
                    default:  // This will always be a number
                        hist.add(String.valueOf(idName.charAt(idName.length() - 1)));
                }

                if (!equationIsFinalised) {
                    outputBox.setText(displayEquationInProgress());
                } else {
                    outputBox.setText(String.valueOf(finalEquation));
                }

                Log.d("Current History", String.valueOf(hist));
            }
        };
    }

    public void finaliseEquation(String operator) {
        for (int i = 0; i < hist.size(); i++) {
            if (hist.get(i).equals(operator)) {
                float before = i > 0 ? Float.parseFloat(hist.get(i - 1)) : null;
                float after = i < hist.size() - 1 ? Float.parseFloat(hist.get(i + 1)) : null;

                // In this house we follow the order of operations goddamnit!
                switch (operator) {
                    case "/":
                        // Just a note, dividing by 0 produces infinity when used on a float.
                        finalEquation = finalEquation + before / after;
                    case "*":
                        finalEquation = finalEquation + before * after;
                    case "+":
                        finalEquation = finalEquation + before + after;
                    case "-":
                        finalEquation = finalEquation + before - after;
                }
            }
        }
    }

    public String displayEquationInProgress() {
        return String.join(" ", hist);
    }
}
