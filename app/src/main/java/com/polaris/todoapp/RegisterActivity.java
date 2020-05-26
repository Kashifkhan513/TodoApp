package com.polaris.todoapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    EditText edFullName;
    EditText edUserName;
    EditText edPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edFullName = findViewById(R.id.editFullName);
        edUserName = findViewById(R.id.editUserName);
        edPassword = findViewById(R.id.editPassword);
    }

    public void sinUpClicked(View view) {

    final String fullname=edFullName.getText().toString().trim();
    final String username=edUserName.getText().toString().trim();
    final String password=edPassword.getText().toString().trim();

    final String url="http://192.168.43.160/Todoapp/register.php";

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest request=new StringRequest(Request.Method.POST, url, new
                Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterActivity.this, error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String ,String>map=new HashMap<>();
                map.put("fullname",fullname);
                map.put("username",username);
                map.put("password",password);

                return map;
            }
        };
        requestQueue.add(request);





    }
}
