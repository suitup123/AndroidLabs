package com.example.youma.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.MalformedURLException;

public class WeatherForecast extends Activity {

    protected static final String URL_STRING = "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric";
    protected static final String URL_IMAGE = "http://openweathermap.org/img/w/";
    protected static final String ACTIVITY_NAME = "Weather Forecast";
    private ProgressBar progressBar;
    private TextView currentTempTxt;
    private TextView maxTempTxt;
    private TextView minTempTxt;
    private TextView windSpdTxt;
    private ImageView weatherImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);


        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        currentTempTxt = findViewById(R.id.currentTemp);
        minTempTxt = findViewById(R.id.minTemp);
        maxTempTxt = findViewById(R.id.maxTemp);
        windSpdTxt = findViewById(R.id.windSpeed);
        weatherImg = findViewById(R.id.Weather_Image);

        ForecastQuery forecast = new ForecastQuery();
        forecast.execute();
    }

    class ForecastQuery extends AsyncTask<String, Integer, String> {
        private String minTemp;
        private String maxTemp;
        private String currentTemp;
        private String iconFile;
        private String windSpd;
        private Bitmap bitmap;



        @Override
        protected String doInBackground(String... args) {
            InputStream stream;

            //Connect to URL and read data
            try {
                URL url = new URL(URL_STRING);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(10000); //in milliseconds
                connection.setConnectTimeout(15000); //in milliseconds
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.connect();

                stream = connection.getInputStream();
                XmlPullParser parser = Xml.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(stream, null);
                parser.nextTag();

                while(parser.next() != XmlPullParser.END_DOCUMENT){
                    if(parser.getEventType() != parser.START_TAG){
                        continue;
                    }

                    if (parser.getName().equals("temperature")) {
                        currentTemp = parser.getAttributeValue(null, "value");
                        publishProgress(25);
                        minTemp = parser.getAttributeValue(null, "min");
                        publishProgress(50);
                        maxTemp = parser.getAttributeValue(null, "max");
                        publishProgress(75);
                    } else if (parser.getName().equals("speed")) {
                        windSpd = parser.getAttributeValue(null, "value");
                        publishProgress(90);
                    } else if (parser.getName().equals("weather")) {
                        iconFile = parser.getAttributeValue(null, "icon");
                    }
                }
                connection.disconnect();

                //download image through file or URL object
                if(fileExistence(iconFile + ".png")){
                    Log.i(ACTIVITY_NAME, "Weather image exists, read file");
                    FileInputStream fis = null;
                    try {
                        fis = openFileInput(iconFile + ".png");
                    } catch (FileNotFoundException e){
                        e.printStackTrace();
                    }  bitmap = BitmapFactory.decodeStream(fis);

                }else {
                    Log.i(ACTIVITY_NAME, "Weather image does not exist, download URL");

                    URL imageUrl = new URL(URL_IMAGE + iconFile + ".png");
                    connection = (HttpURLConnection) imageUrl.openConnection();
                    connection.connect();
                    stream = connection.getInputStream();
                    bitmap = BitmapFactory.decodeStream(stream);

                    FileOutputStream fos = openFileOutput(iconFile + ".png", Context.MODE_PRIVATE);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 80, fos);
                    fos.flush();
                    fos.close();
                    connection.disconnect();
                }
                publishProgress(100);
            }catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer ... value){
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(value[0]);
        }

        @Override
        protected void onPostExecute(String args){
            progressBar.setVisibility(View.INVISIBLE);
            currentTempTxt.setText("Current: " + currentTemp + " C");
            minTempTxt.setText("Min: " + minTemp + " C");
            maxTempTxt.setText("Max: " + maxTemp + " C");
            windSpdTxt.setText("Wind: " +windSpd);
            weatherImg.setImageBitmap(bitmap);
        }

        public boolean fileExistence(String fname){
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();
        }
    }
}



