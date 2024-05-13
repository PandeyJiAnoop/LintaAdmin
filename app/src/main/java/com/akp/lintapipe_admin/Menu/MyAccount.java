package com.akp.lintapipe_admin.Menu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.akp.lintapipe_admin.Basic.AppUrls;
import com.akp.lintapipe_admin.MainActivity;
import com.akp.lintapipe_admin.R;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MyAccount extends AppCompatActivity {
    private ImageView ivMenu;
    private RelativeLayout rlBottom;
    private EditText et_name,et_email,et_dob,et_city,et_loc,et_adhar,et_namef,et_namem;
    String UserId,UserName;
    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;
    RadioButton rdbMale,rdbfemale;
    RadioGroup radioSexGroup;
    private RadioButton radioButton;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        SharedPreferences sharedPreferences = getSharedPreferences("login_preference",MODE_PRIVATE);
        UserId= sharedPreferences.getString("U_id", "");
        UserName= sharedPreferences.getString("U_name", "");

//        Toast.makeText(getApplicationContext(),""+getmob+getOTPcode,Toast.LENGTH_LONG).show();
        radioSexGroup=(RadioGroup)findViewById(R.id.sexgroup);
        rdbMale=findViewById(R.id.rdbMale);
        rdbfemale=findViewById(R.id.rdbfemale);
        et_name=findViewById(R.id.et_name);
        et_email=findViewById(R.id.et_email);
        et_dob=findViewById(R.id.et_dob);
        et_city=findViewById(R.id.et_city);
        et_loc=findViewById(R.id.et_loc);

        et_adhar=findViewById(R.id.et_adhar);
        et_namef=findViewById(R.id.et_namef);
        et_namem=findViewById(R.id.et_namem);
        img=findViewById(R.id.img);

        rlBottom=findViewById(R.id.rlBottom);
        ivMenu=findViewById(R.id.ivMenu);
        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });



        rlBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_name.getText().toString().equalsIgnoreCase("")){
                    et_name.setError("Fields can't be blank!");
                    et_name.requestFocus();
                }
                else if (et_email.getText().toString().equalsIgnoreCase("")){
                    et_email.setError("Fields can't be blank!");
                    et_email.requestFocus();
                }
                else {
                    int selectedId = radioSexGroup.getCheckedRadioButtonId();
                    // find the radiobutton by returned id
                    radioButton = (RadioButton) findViewById(selectedId);
//                   Toast.makeText(PersonalActivity.this, radioButton.getText(), Toast.LENGTH_SHORT).show();
//                    UpdateProfileAPI();
                }
            }
        });

/*
        et_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(MyAccount.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                                  int dayOfMonth) {
                                Calendar calander2 = Calendar.getInstance();
                                calander2.setTimeInMillis(0);
                                calander2.set(year, monthOfYear, dayOfMonth, 0, 0, 0);
                                Date SelectedDate = calander2.getTime();
//                                DateFormat dateformat_US = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.US);
//                                String StringDateformat_US = dateformat_US.format(SelectedDate);
//                                et_dob.setText(StringDateformat_US + "\n");
                                DateFormat dateformat_UK = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.UK);
                                String StringDateformat_UK = dateformat_UK.format(SelectedDate);
                                et_dob.setText(StringDateformat_UK);

//                                et_dob.setText(day + "-" + (month + 1) + "-" + year);
                            }
                        }, year, month, dayOfMonth);
//                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });
*/

        ProfileAPI();




    }
    public void ProfileAPI() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppUrls.ProfileGetService, new  Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                String jsonString = response;
                jsonString = jsonString.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>"," ");
                jsonString = jsonString.replace("<string xmlns=\"http://tempuri.org/\">"," ");
                jsonString = jsonString.replace("</string>"," ");
                Log.d("test",jsonString);
//                Toast.makeText(LoginActivity.this, "msg"+response, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    String msg  = jsonObject.getString("status");
                    if (msg.equalsIgnoreCase("false")){
                        Toast.makeText(getApplicationContext(), "Data not matched!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        JSONArray jsonArrayr = jsonObject.getJSONArray("Response");
                        for (int i = 0; i < jsonArrayr.length(); i++) {
                            JSONObject jsonObject1 = jsonArrayr.getJSONObject(i);
                            et_name.setText(jsonObject1.getString("EmployeeName"));
                            et_email.setText(jsonObject1.getString("EmailAddress"));
                            et_dob.setText("+91- "+jsonObject1.getString("ContactNo"));
                            et_city.setText(jsonObject1.getString("Address"));
                            et_loc.setText(jsonObject1.getString("PANNo"));
                            et_namef.setText(jsonObject1.getString("FatherName"));
                            et_namem.setText(jsonObject1.getString("MotherName"));
                            et_adhar.setText(jsonObject1.getString("AadharNo"));


                            Glide.with(MyAccount.this)
                                    .load(jsonObject1.getString("ProfilePic"))
                                    .error(R.drawable.profileimg)
                                    .into(img);

                            if (jsonObject1.getString("Gender").equalsIgnoreCase("Male")){
                                rdbMale.setChecked(true);
                                rdbfemale.setChecked(false);

                            }
                            else {
                                rdbfemale.setChecked(true);
                                rdbMale.setChecked(false);

                            }
                        }
                    }




                } catch (JSONException e) {
                    Toast.makeText(MyAccount.this, "Something went wrong!"+e, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                } }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.d("myTag", "message:"+error);
                Toast.makeText(MyAccount.this, "Something went wrong!"+error, Toast.LENGTH_SHORT).show();
            }
        }) {  @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            HashMap<String, String> params = new HashMap<>();
            params.put("EmployeeCode", UserId);
            return params; }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(MyAccount.this);
        requestQueue.add(stringRequest);

    }
}