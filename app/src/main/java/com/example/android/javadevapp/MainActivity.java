package com.example.android.javadevapp;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String LOG_TAG = MainActivity.class.getName();
    private static final String JAVA_DEV_LAGOS = "https://api.github.com/search/users?q=location:lagos+language:java";
    JavaDevAdapter adapter;

    ListView developersListView;
    ArrayList<Developer> developers = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        developersListView = (ListView)findViewById(R.id.list_view);
        adapter = new JavaDevAdapter(MainActivity.this,developers);
        DeveloperAsyncTask task = new DeveloperAsyncTask();
        task.execute();

        //show user image wit
        developersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Developer currentDeveloper = adapter.getItem(position);
                Intent developerIntent = new Intent(MainActivity.this, DevelopersActivity.class);
                developerIntent.putExtra("image_url",currentDeveloper.getImageUrl());
                developerIntent.putExtra("profile_url",currentDeveloper.getProfileUrl());
                developerIntent.putExtra("username",currentDeveloper.getUsername());
                startActivity(developerIntent);
            }
        });
    }




    private class DeveloperAsyncTask extends AsyncTask<URL, Void, ArrayList<Developer>>{
        protected ArrayList<Developer> doInBackground(URL... urls) {
            // Create URL object
            URL url = createUrl(JAVA_DEV_LAGOS);

            // Perform HTTP request to the URL and receive a JSON response back
            String jsonResponse = "";
            try {
                jsonResponse = makeHttpRequest(url);
            } catch (IOException e) {
                // TODO Handle the IOException
            }

            // Extract relevant fields from the JSON response and create an {@link Developer} object
            return extractDeveloper(jsonResponse);
            // Return the {@link Developer} object as the result fo the {@link DeveloperAsyncTask}

        }

        /**
         * Update the screen with the given earthquake (which was the result of the
         * {@link DeveloperAsyncTask}).
         */
        @Override
        protected void onPostExecute(ArrayList<Developer> developers) {
            if (developers == null) {
                return;
            }
            developersListView.setAdapter(adapter);

        }

        /**
         * Returns new URL object from the given string URL.
         */
        private URL createUrl(String stringUrl) {
            URL url = null;
            try {
                url = new URL(stringUrl);
            } catch (MalformedURLException exception) {
                Log.e(MainActivity.LOG_TAG, "Error with creating URL", exception);
                return null;
            }
            return url;
        }

        /**
         * Make an HTTP request to the given URL and return a String as the response.
         */
        private String makeHttpRequest(URL url) throws IOException {
            String jsonResponse = "";
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(15000 /* milliseconds */);
                urlConnection.connect();
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } catch (IOException e) {
                // TODO: Handle the exception
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (inputStream != null) {
                    // function must handle java.io.IOException here
                    inputStream.close();
                }
            }
            return jsonResponse;
        }

        /**
         * Convert the {@link InputStream} into a String which contains the
         * whole JSON response from the server.
         */
        private String readFromStream(InputStream inputStream) throws IOException {
            StringBuilder output = new StringBuilder();
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            }
            return output.toString();
        }
        // Extract list of devlopers and return  new objects
        private ArrayList<Developer> extractDeveloper(String response){
            try{
                JSONObject jsonObjectRoot = new JSONObject(response);
                JSONArray jsonArray = jsonObjectRoot.optJSONArray("items");
                for(int i =0; i< jsonArray.length(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String imageUrl = jsonObject.optString("avatar_url");
                    String login = jsonObject.optString("login");
                    String profileUrl = jsonObject.optString("html_url");
                    developers.add(new Developer(login,imageUrl,profileUrl));
                }
            }catch (JSONException e){e.printStackTrace();}

            return developers;
        }
    }


}

