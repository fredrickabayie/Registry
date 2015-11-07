package com.practicals.fredrickabayie.registry;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author fredrickabayie
 * @version 1.0.0
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button registerStudent_btn, details_btn;
    private EditText studentName, phoneNumber;
    private TextView display_details;
    private static final int REQUEST_CODE = 1;
    private String studentId, studentGPA, studentMajor;


    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display_details = (TextView) findViewById(R.id.display_details);
//        display_details.setText(getIntent().getExtras().getString("studentId"));

        registerStudent_btn = (Button) findViewById(R.id.registerStudent_btn);
        registerStudent_btn.setOnClickListener(this);

        details_btn = (Button) findViewById(R.id.details_btn);
        details_btn.setOnClickListener(this);

        studentName = (EditText) findViewById(R.id.studentName);
        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
    }

    /**
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (v == details_btn) {
            Intent details_screen = new Intent(MainActivity.this, select_year.class);
            startActivityForResult(details_screen, REQUEST_CODE);
        }

        if (v == registerStudent_btn) {
            readWebpage(v);
        }
    }


    private class DownloadWebPageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            HttpURLConnection urlConnection = null;
            for (String url1 : urls) {
                try {
                    URL url = new URL(url1);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    System.out.println(url);
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    BufferedReader buffer = new BufferedReader(
                            new InputStreamReader(in));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        System.out.println(s);
                        response += s;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    urlConnection.disconnect();
                }
            }

            return response;

        }


        /**
         *
         * @param result
         */
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        }
    }


    /**
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            if (data.hasExtra("studentId")) {
                studentId = data.getExtras().getString("studentId");
                studentGPA = data.getExtras().getString("studentGPA");
                studentMajor = data.getExtras().getString("studentMajor");

                display_details.setText("Student ID: "+data.getExtras().getString("studentId")+
                        "\nCourse: "+data.getExtras().getString("studentMajor")+"\nGPA: "+data.getExtras().getString("studentGPA"));
            }
        }
    }


    /**
     * A function to read the web page
     * @param view
     */
    public void readWebpage(View view) {
        System.out.println("clicked");
        DownloadWebPageTask task = new DownloadWebPageTask();
//        Toast.makeText(getApplicationContext(), studentId+" "+studentGPA+" "+studentMajor, Toast.LENGTH_LONG).show();
        String name = studentName.getText().toString().replaceAll(" ", "%20");
        String major = studentMajor.replaceAll(" ", "%20");
        task.execute("http://cs.ashesi.edu.gh/~csashesi/class2016/fredrick-abayie/mobileweb/student_registration_system_sms/php/sms.php?cmd=register&message="
                +studentId+","+name+","+studentGPA+","+major+","+phoneNumber.getText());

    }

}
