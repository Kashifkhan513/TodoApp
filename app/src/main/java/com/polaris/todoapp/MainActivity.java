package com.polaris.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ListView listView;
    EditText editTask;
    String id;
    ArrayList<Task>arrayList;
    ArrayAdapter<Task>arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=findViewById(R.id.listView);
        editTask=findViewById(R.id.ed_Task);
        arrayList=new ArrayList<>();

        sharedPreferences = getSharedPreferences("user_info",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        String fullname=sharedPreferences.getString("fullname","");
        id=sharedPreferences.getString("id","");
        getSupportActionBar().setTitle("Welcome "+fullname);

        getAllTasks();

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Are u Sure to delete ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        deleteTask();

                    }
                });
                builder.setNegativeButton("No",null);
                builder.show();


                return true;
            }
        });

    }

    private void deleteTask() {
        RequestQueue queue=Volley.newRequestQueue(this);
        String url="http://192.168.43.160/Todoapp/delete_task.php";
        StringRequest request=new StringRequest(Request.Method.POST, url, new
                Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                            if (response.equals("Success"))
                            {
                                Toast.makeText(MainActivity.this, "Task Deleted Successfully", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>map=new HashMap<>();
                map.put("id",id);
                return map;
            }
        };
        queue.add(request);
    }


    private void getAllTasks() {

        String url="http://192.168.43.160/Todoapp/get_all_tasks.php";
        RequestQueue queue=Volley.newRequestQueue(this);
        StringRequest request=new StringRequest(Request.Method.POST, url, new
                Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("Not Found"))
                        {
                            Toast.makeText(MainActivity.this, "Task not yet...", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            try {
                                JSONObject jsonObject=new JSONObject(response);
                                JSONArray array=jsonObject.getJSONArray("tasks");
                                for (int i=0;i<array.length();i++)
                                {
                                JSONObject taskObject=array.getJSONObject(i);
                                String id=taskObject.getString("id");
                                String taskName=taskObject.getString("taskname");
                                String userid=taskObject.getString("userid");
                                Task task=new Task(id,taskName,userid);
                                    arrayList.add(task);
                                }

                                arrayAdapter=new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_list_item_1,arrayList);
                                listView.setAdapter(arrayAdapter);




                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>map=new HashMap<>();
                map.put("userid",id);
                return map;
            }
        };
        queue.add(request);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.logout:
                editor.clear();
                editor.commit();
                startActivity(new Intent(this,LandingActivity.class));
                finish();
                break;


        }
        return super.onOptionsItemSelected(item);
    }



    public void addTask(View view) {
        final String taskname=editTask.getText().toString().trim();
        String url="http://192.168.43.160/Todoapp/add_task.php";
        RequestQueue queue= Volley.newRequestQueue(this);
        StringRequest request=new StringRequest(Request.Method.POST, url, new
                Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    if (response.equals("Success"))
                    {
                        getAllTasks();
                    }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>map=new HashMap<>();
                map.put("userid",id);
                map.put("taskname",taskname);
                return map;
            }
        };
        queue.add(request);



    }
}
