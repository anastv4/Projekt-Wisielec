package com.example.wisieliec;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    // tablica haseł do gry
    String[] slowa = {"ANDROID", "JAVA", "KOT", "SZKOLA", "PROGRAM"};

    String slowo;        // aktualne hasło w grze
    char[] ukryte;       // ukryte litery
    int zycia = 7;       // liczba prób

    boolean gameOver = false; // czy gra się skończyła

    // elementy z XML (UI)
    ImageView wisielecImg;      // obrazek wisielca
    LinearLayout slowoLayout;   // układ liter hasła
    TextView textProby;         // tekst z liczbą żyć
    TextView wynikText;         // komunikat wygrana/przegrana
    EditText input;            // pole wpisywania litery
    Button button;             // przycisk sprawdzania
    Button restartButton;      // przycisk restartu

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // łączenie zmiennych z elementami XML
        wisielecImg = findViewById(R.id.wisielecImg);
        slowoLayout = findViewById(R.id.slowoLayout);
        textProby = findViewById(R.id.proby);
        wynikText = findViewById(R.id.wynikText);
        input = findViewById(R.id.inputLitera);
        button = findViewById(R.id.sprawdz);
        restartButton = findViewById(R.id.restartButton);

        startGame(); // start gry

        // kliknięcie przycisku "sprawdź"
        button.setOnClickListener(v -> {

            if (gameOver) return; // jeśli gra skończona → nic nie rób

            String txt = input.getText().toString().toUpperCase(); // pobiera literę i zmienia na wielką
            input.setText(""); // czyści pole

            if (txt.length() == 0) return; // jeśli nic nie wpisano → stop

            char litera = txt.charAt(0); // bierze pierwszą literę
            boolean trafiona = false; // czy litera jest w haśle

            // sprawdzanie liter w haśle
            for (int i = 0; i < slowo.length(); i++) {
                if (slowo.charAt(i) == litera) {
                    ukryte[i] = litera; // odkrywa literę
                    trafiona = true;
                }
            }

            // jeśli nietrafiona litera
            if (!trafiona) {
                zycia--; // zmniejsza życie
                updateWisielec(); // zmienia obrazek wisielca
            }

            updateUI();       // odświeża UI
            sprawdzKoniec();  // sprawdza czy koniec gry
        });

        // kliknięcie restart
        restartButton.setOnClickListener(v -> {
            startGame();          // nowa gra
            wynikText.setText(""); // usuwa napis
            gameOver = false;      // reset stanu gry
        });
    }

    // start nowej gry
    void startGame() {

        // losuje słowo
        slowo = slowa[(int)(Math.random() * slowa.length)];

        // tworzy tablicę ukrytych liter
        ukryte = new char[slowo.length()];

        Arrays.fill(ukryte, '_'); // wypełnia podkreśleniami

        zycia = 7;        // reset żyć
        gameOver = false; // gra trwa

        updateWisielec(); // aktualizuje obraz
        updateUI();       // aktualizuje ekran
    }

    // aktualizacja UI (litery + życie)
    void updateUI() {

        slowoLayout.removeAllViews(); // usuwa stare litery

        // pokazuje każdą literę jako TextView
        for (int i = 0; i < ukryte.length; i++) {
            TextView tv = new TextView(this);
            tv.setText(String.valueOf(ukryte[i])); // pokazuje literę lub _
            tv.setTextSize(32); // rozmiar tekstu
            tv.setPadding(10, 10, 10, 10); // odstępy
            slowoLayout.addView(tv); // dodaje do ekranu
        }

        textProby.setText("Życia: " + zycia); // pokazuje życie
    }

    // zmiana obrazka wisielca
    void updateWisielec() {

        if (zycia < 0) zycia = 0; // nie może być mniej niż 0

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

    // sprawdzenie końca gry
    void sprawdzKoniec() {

        // wygrana (ukryte == slowo)
        if (String.valueOf(ukryte).equals(slowo)) {
            Toast.makeText(this, "Wygrałeś 🎉", Toast.LENGTH_LONG).show();
            startGame(); // nowa gra
        }

        // przegrana
        if (zycia <= 0 && !gameOver) {
            gameOver = true;

            wisielecImg.setImageResource(R.drawable.wisilec_07);

            wynikText.setText("PRZEGRAŁEŚ 💀 Hasło: " + slowo);
        }
    }
}