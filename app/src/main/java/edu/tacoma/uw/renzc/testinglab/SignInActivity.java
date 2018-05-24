package edu.tacoma.uw.renzc.testinglab;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class SignInActivity extends AppCompatActivity implements RegisterFragment.onRegisterListener, LoginFragment.OnFragmentInteractionListener{

    private static final String TAG = "SignInActivity";
    private boolean mLoginMode;
    private static final String REGISTER_URL
            = "https://renzcweblab.000webhostapp.com/addUser.php?email=";
    private static final String LOGIN_URL
            = "https://renzcweblab.000webhostapp.com/login.php?email=";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        if(findViewById(R.id.fragment_container) != null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, new RegisterFragment())
                    .commit();
        }
    }

    @Override
    public void register(Account account) {

        try {
            mLoginMode = false;
            StringBuilder stringBuilder = new StringBuilder(REGISTER_URL);
            stringBuilder.append(URLEncoder.encode(account.getEmail(), "UTF-8"));
            stringBuilder.append("&password=");
            stringBuilder.append(URLEncoder.encode(account.getPassword(), "UTF-8"));

            Log.i(TAG, "Url is " +stringBuilder.toString());
            SignInAsyncTask registerAsyncTask = new SignInAsyncTask();
            registerAsyncTask.execute(stringBuilder.toString());
        }
        catch (Exception e) {
            Toast.makeText(this, "Couldn't register, Something wrong with the URL" + e.getMessage(),
                    Toast.LENGTH_SHORT). show();
        }
    }


    @Override
    public void launchLoginFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new LoginFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void login(Account account) {
        try {
            mLoginMode = true;
            StringBuilder stringBuilder = new StringBuilder(LOGIN_URL);
            stringBuilder.append(URLEncoder.encode(account.getEmail(), "UTF-8"));
            stringBuilder.append("&password=");
            stringBuilder.append(URLEncoder.encode(account.getPassword(), "UTF-8"));

            Log.i(TAG, "Url is " +stringBuilder.toString());
            SignInAsyncTask registerAsyncTask = new SignInAsyncTask();
            registerAsyncTask.execute(stringBuilder.toString());
        }
        catch (Exception e) {
            Toast.makeText(this, "Couldn't login, Something wrong with the URL" + e.getMessage(),
                    Toast.LENGTH_SHORT). show();
        }
    }


    public class SignInAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                String status = (String) jsonObject.get("result");
                if (status.equals("success")) {
                    if (!mLoginMode) {
                        Toast.makeText(getApplicationContext()
                                , "User successfully registered!"
                                , Toast.LENGTH_SHORT)
                                .show();
                    } else {

                        Toast.makeText(getApplicationContext()
                                , "User successfully authenticated!"
                                , Toast.LENGTH_SHORT)
                                .show();
                    }
                    launchMain();
                } else {
                    if (!mLoginMode) {
                        Toast.makeText(getApplicationContext(), "Failed to register: "
                                        + jsonObject.get("error")
                                , Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Failed to login: "
                                        + jsonObject.get("error")
                                , Toast.LENGTH_SHORT)
                                .show();
                    }
                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "Something wrong with the data" +
                        e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            HttpURLConnection urlConnection = null;
            for (String url : urls) {
                try {
                    URL urlObject = new URL(url);
                    urlConnection = (HttpURLConnection) urlObject.openConnection();

                    InputStream content = urlConnection.getInputStream();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }

                } catch (Exception e) {
                    response = "Unable to download the list of courses, Reason: "
                            + e.getMessage();
                }
                finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            }
            return response;
        }
    }

    public void launchMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }



}
