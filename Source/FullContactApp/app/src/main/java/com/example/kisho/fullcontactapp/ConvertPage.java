package com.example.kisho.fullcontactapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConvertPage extends AppCompatActivity {

    EditText ConvertPageActivity_EmailEditText;
    TextView ConvertPageActivity_ResponseTextView;
    ProgressBar ConvertPageActivity_ProgressBar;
    static final String API_KEY = "88fc4674870dca5b";
    static final String API_URL = "https://api.fullcontact.com/v2/person.json?";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert_page);

        ConvertPageActivity_ResponseTextView = (TextView) findViewById(R.id.ConvertPageContent_ResponseTextView);
        ConvertPageActivity_EmailEditText = (EditText) findViewById(R.id.ConvertPageContent_EmailEditText);
        ConvertPageActivity_ProgressBar = (ProgressBar) findViewById(R.id.ConvertPageContent_ProgressBar);
        Button ConvertPageActivity_LogoutButton = (Button) findViewById(R.id.ConvertPageContent_LogoutButton);
        Button ConvertPageActivity_SearchButton = (Button) findViewById(R.id.ContentPageContent_SearchButton);
        ConvertPageActivity_SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new RetrieveFeedTask().execute();
            }
        });
        ConvertPageActivity_LogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    class RetrieveFeedTask extends AsyncTask<Void, Void, String> {

        String emailId = ConvertPageActivity_EmailEditText.getText().toString();

        private Exception exception;

        protected void onPreExecute() {
            ConvertPageActivity_ProgressBar.setVisibility(View.VISIBLE);
            ConvertPageActivity_ResponseTextView.setText("");
        }

        protected String doInBackground(Void... urls) {


            try {
                URL url = new URL(API_URL+"email="+ emailId +"&apiKey="+API_KEY);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                }
                finally{
                    urlConnection.disconnect();
                }
            }
            catch(Exception error) {
                Log.e("ERROR", error.getMessage(), error);
                return null;
            }
        }

        protected void onPostExecute(String response) {
            if(response == null) {
                response = "Not a valid Email address";
            }
            ConvertPageActivity_ProgressBar.setVisibility(View.GONE);
            Log.i("INFO", response);

            try {
                JSONObject object = new JSONObject(response);
                JSONArray socialProfiles = object.optJSONArray("socialProfiles");
                String ResultText="\n";
                for(int i=0;i<socialProfiles.length();i++){
                    JSONObject resultObject = socialProfiles.getJSONObject(i);
                    String sample = resultObject.getString("typeName");
                    ResultText = ResultText+sample+"\n";
                }
                JSONObject fName = new JSONObject(response);
                JSONObject contactInfo = fName.getJSONObject("contactInfo");
                String fullName = contactInfo.getString("fullName");
                JSONObject demographics = fName.getJSONObject("demographics");
                String gender = demographics.getString("gender");

                String finalResult = "Full Name - "+fullName+"\nGender - "+gender+"\nAll Social Network Profiles of this email address are \n"+ResultText;
                ConvertPageActivity_ResponseTextView.setText(finalResult);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
