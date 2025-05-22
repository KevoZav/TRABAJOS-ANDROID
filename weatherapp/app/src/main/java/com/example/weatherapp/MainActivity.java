package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private EditText cityInput;
    private TextView weatherText;
    private OkHttpClient client = new OkHttpClient();
    private static final String API_KEY = "28e09579eaac7928cb87e5ff1af90a75\n"; // Cambia aquí por tu API Key real

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityInput = findViewById(R.id.cityInput);
        weatherText = findViewById(R.id.weatherText);
        Button fetchButton = findViewById(R.id.fetchButton);

        fetchButton.setOnClickListener(v -> {
            String city = cityInput.getText().toString().trim();
            if (!city.isEmpty()) {
                fetchWeather(city);
            } else {
                weatherText.setText("Please enter a city name");
            }
        });
    }

    private void fetchWeather(String city) {
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + API_KEY + "&units=metric";

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> weatherText.setText("Error: " + e.getMessage()));
            }

            @Override public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();

                    try {
                        JSONObject json = new JSONObject(result);
                        JSONObject main = json.getJSONObject("main");
                        double temp = main.getDouble("temp");
                        JSONObject weather = json.getJSONArray("weather").getJSONObject(0);
                        String description = weather.getString("description");

                        String displayText = "Temperature: " + temp + "°C\n" +
                                "Description: " + description;

                        runOnUiThread(() -> weatherText.setText(displayText));
                    } catch (Exception e) {
                        runOnUiThread(() -> weatherText.setText("Parsing error"));
                    }

                } else {
                    runOnUiThread(() -> weatherText.setText("API Error: " + response.code()));
                }
            }
        });
    }
}
