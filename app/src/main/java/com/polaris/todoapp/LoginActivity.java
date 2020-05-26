package com.polaris.todoapp;

import android.content.Intent;
import android.content.SharedPreferences;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    EditText edUserName;
    EditText edPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edUserName=findViewById(R.id.editUserName);
        edPassword=findViewById(R.id.editPassword);
    }

    public void loginClicked(View view) {
        final String username=edUserName.getText().toString().trim();
        final String password=edPassword.getText().toString().trim();

        final String url="http://192.168.43.160/Todoapp/login.php";

        RequestQueue queue= Volley.newRequestQueue(this);
        StringRequest request=new StringRequest(Request.Method.POST, url, new
                Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("Not Found"))
                        {
                            Toast.makeText(LoginActivity.this, "invalid username or password", Toast.LENGTH_SHORT).show();

                        }
                        else {
                            SharedPreferences sharedPreferences=getSharedPreferences("user_info",MODE_PRIVATE);
                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            try {
                                JSONObject object=new JSONObject(response);
                                editor.putString("id",object.getString("id"));
                                editor.putString("fullname",object.getString("fullname"));
                                editor.putString("username",object.getString("username"));
                                editor.putString("password",object.getString("password"));
                                editor.putBoolean("LOGGED_IN",true);
                                editor.commit();


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                            finish();
                        }




                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>map=new HashMap<>();
                map.put("username",username);
                map.put("password",password);
                return map;
            }
        };

        queue.add(request);


    }
}
