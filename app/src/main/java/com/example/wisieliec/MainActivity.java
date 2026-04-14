package com.example.wisieliec;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wisieliec.R;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    String[] slowa = {"ANDROID", "JAVA", "KOT", "SZKOLA", "PROGRAM"};

    String slowo;
    char[] ukryte;
    int zycia = 7;

    boolean gameOver = false;

    ImageView wisielecImg;
    LinearLayout slowoLayout;
    TextView textProby;
    TextView wynikText;
    EditText input;
    Button button;
    Button restartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wisielecImg = findViewById(R.id.wisielecImg);
        slowoLayout = findViewById(R.id.slowoLayout);
        textProby = findViewById(R.id.proby);
        wynikText = findViewById(R.id.wynikText);
        input = findViewById(R.id.inputLitera);
        button = findViewById(R.id.sprawdz);
        restartButton = findViewById(R.id.restartButton);

        startGame();

        button.setOnClickListener(v -> {

            if (gameOver) return;

            String txt = input.getText().toString().toUpperCase();
            input.setText("");

            if (txt.length() == 0) return;

            char litera = txt.charAt(0);
            boolean trafiona = false;

            for (int i = 0; i < slowo.length(); i++) {
                if (slowo.charAt(i) == litera) {
                    ukryte[i] = litera;
                    trafiona = true;
                }
            }

            if (!trafiona) {
                zycia--;
                updateWisielec();
            }

            updateUI();
            sprawdzKoniec();
        });

        restartButton.setOnClickListener(v -> {
            startGame();
            wynikText.setText("");
            gameOver = false;
        });
    }

    void startGame() {
        slowo = slowa[(int)(Math.random() * slowa.length)];
        ukryte = new char[slowo.length()];

        Arrays.fill(ukryte, '_');

        zycia = 7;
        gameOver = false;

        updateWisielec();
        updateUI();
    }

    void updateUI() {
        slowoLayout.removeAllViews();

        for (int i = 0; i < ukryte.length; i++) {
            TextView tv = new TextView(this);
            tv.setText(String.valueOf(ukryte[i]));
            tv.setTextSize(32);
            tv.setPadding(10, 10, 10, 10);
            slowoLayout.addView(tv);
        }

        textProby.setText("Życia: " + zycia);
    }

    void updateWisielec() {

        if (zycia < 0) zycia = 0;

        switch (zycia) {
            case 7:
                wisielecImg.setImageResource(R.drawable.wisilec_0);
                break;
            case 6:
                wisielecImg.setImageResource(R.drawable.wisilec_01);
                break;
            case 5:
                wisielecImg.setImageResource(R.drawable.wisilec_02);
                break;
            case 4:
                wisielecImg.setImageResource(R.drawable.wisilec_03);
                break;
            case 3:
                wisielecImg.setImageResource(R.drawable.wisilec_04);
                break;
            case 2:
                wisielecImg.setImageResource(R.drawable.wisilec_05);
                break;
            case 1:
                wisielecImg.setImageResource(R.drawable.wisilec_06);
                break;
            case 0:
                wisielecImg.setImageResource(R.drawable.wisilec_07);
                break;
        }
    }

    void sprawdzKoniec() {

        if (String.valueOf(ukryte).equals(slowo)) {
            Toast.makeText(this, "Wygrałeś 🎉", Toast.LENGTH_LONG).show();
            startGame();
        }

        if (zycia <= 0 && !gameOver) {
            gameOver = true;

            wisielecImg.setImageResource(R.drawable.wisilec_07);

            wynikText.setText("PRZEGRAŁEŚ 💀 Hasło: " + slowo);
        }
    }
}