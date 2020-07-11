package com.suriyal.webservice1d;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import javax.xml.transform.ErrorListener;

public class MainActivity extends AppCompatActivity {

    EditText tname, temail, tpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tname = findViewById(R.id.name);
        temail = findViewById(R.id.email);
        tpass = findViewById(R.id.pass);

    }

    public void submit(View view) {
        String name = tname.getText().toString();
        String email = temail.getText().toString();
        String pass = tpass.getText().toString();

        sendToserver();
    }

    private void sendToserver() {
        //4 parameter
        //1 method name(post)
        //2 url(http://localhost:9090/MyServerTom/insert)
        //3 Response Listener annonymous inner class
        //4 Error Listener

        //3 Response Listener
        Response.Listener rl = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jo = new JSONObject(response);

                    // method 1
                    /*String name=jo.getString("name");
                    String email=jo.getString("email");
                    String pass=jo.getString("pass");
                    //create Student object
                    Student student=new Student();
                    student.setName(name);
                    student.setEmail(email);
                    student.setPass(pass);
                    */
                    // method 2
                    //Gson gson = new Gson();
                    //Student student = gson.fromJson(jo.toString(), Student.class);

                    // method 3  - optimised version
                    Student student = new Gson().fromJson(new JSONObject(response).toString(), Student.class);
                    tname.setText(student.getName());
                    temail.setText(student.getEmail());
                    tpass.setText(student.getPass());
                    Toast.makeText(MainActivity.this, student.getName(), Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    Log.e("error", e.toString());
                }

                //Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
            }
        };

        //4 Error Listener
        Response.ErrorListener re = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        };
        //StringRequest sr=new StringRequest(Request.Method.POST,"http://localhost:9090/MyServerTom/insert",rl,re);
        StringRequest sr = new StringRequest(Request.Method.POST, "http://192.168.43.163:9090/MyServerTom/insert", rl, re);
        Volley.newRequestQueue(this).add(sr);
    }


}