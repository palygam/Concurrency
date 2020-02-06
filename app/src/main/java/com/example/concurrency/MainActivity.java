package com.example.concurrency;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Button launchProcessButton;
    private ProgressBar progressBar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addButtonsMainActivity();
    }

    public void addButtonsMainActivity() {
        final Handler handler = new Handler();
        launchProcessButton = findViewById(R.id.button_launch_process);
        progressBar = findViewById(R.id.progress_bar);
        launchProcessButton.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            launchProcessButton.setEnabled(false);
            Thread backgroundThread = new Thread(() -> {
                sortArray();
                handler.postDelayed(updateUI, 10000);
            });
            backgroundThread.start();
        });

        Button randomNumber = findViewById(R.id.button_random_number);
        randomNumber.setOnClickListener(v -> calculateRandomNumber());
    }

    private Runnable updateUI = new Runnable() {
        public void run() {
            launchProcessButton.setEnabled(true);
            progressBar.setVisibility(View.INVISIBLE);
            Toast toast = Toast.makeText(MainActivity.this, "Array is sorted", Toast.LENGTH_SHORT);
            toast.show();
        }
    };

    private void sortArray() {
        int[] array = new int[1000000];
        for (int i = 0; i < array.length; i++) {
            array[i] = new Random().nextInt(10);
        }
        Arrays.sort(array);
    }

    private void calculateRandomNumber() {
        int random = new Random().nextInt(100);
        TextView infoTextView = findViewById(R.id.info_text_view);
        infoTextView.setText(Integer.toString(random));
    }
}



