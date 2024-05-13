package com.akp.lintapipe_admin.Basic;

import static com.akp.lintapipe_admin.Retrofit.API_Config.getApiClient_ByPost;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.akp.lintapipe_admin.MainActivity;
import com.akp.lintapipe_admin.R;
import com.akp.lintapipe_admin.Retrofit.ApiService;
import com.akp.lintapipe_admin.Retrofit.ConnectToRetrofit;
import com.akp.lintapipe_admin.Retrofit.GlobalAppApis;
import com.akp.lintapipe_admin.Retrofit.RetrofitCallBackListenar;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;


public class LoginActivity extends AppCompatActivity {
    ImageView sliding_menu;
    String UserId;
    EditText userid_et, pass_et;
    Button login_btn;
    private SharedPreferences login_preference;
    private SharedPreferences.Editor login_editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FindId();
    }

    private void FindId() {
        SharedPreferences sharedPreferences = getSharedPreferences("login_preference", MODE_PRIVATE);
        UserId = sharedPreferences.getString("U_id", "");
        userid_et = (EditText) findViewById(R.id.userid_et);
        pass_et = (EditText) findViewById(R.id.pass_et);
        login_btn = (Button) findViewById(R.id.login_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userid_et.getText().toString().equalsIgnoreCase("")) {
                    userid_et.setError("Fields can't be blank!");
                    userid_et.requestFocus();
                } else if (pass_et.getText().toString().equalsIgnoreCase("")) {
                    pass_et.setError("Fields can't be blank!");
                    pass_et.requestFocus();
                } else {
                    getLoginAPI(userid_et.getText().toString(),pass_et.getText().toString());
                }
            }
        });
    }

    public void getLoginAPI(String MemberId, String Password) {
        String otp1 = new GlobalAppApis().Parm_Login(MemberId, Password);
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.APIName_SuperAdminLogin(otp1);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
                Log.d("response_login", result);
//                Toast.makeText(getApplicationContext(),""+result,Toast.LENGTH_LONG).show();
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getString("Status").equalsIgnoreCase("true")) {
                        JSONArray Jarray = object.getJSONArray("Response");
                        for (int i = 0; i < Jarray.length(); i++) {
                            JSONObject jsonobject = Jarray.getJSONObject(i);
                            String UserName = jsonobject.getString("UserName");
                            login_preference = getSharedPreferences("login_preference", MODE_PRIVATE);
                            login_editor = login_preference.edit();
                            login_editor.putString("U_id", UserName);
                            login_editor.putString("U_name", UserName);
                            login_editor.commit();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setTitle(Html.fromHtml(object.getString("Message")));
                        builder.setNegativeButton(Html.fromHtml("<font color='#D32F2F'>Exit</font>"), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int arg1) {
                                dialog.dismiss();
                            }
                        });
                        builder.create();
                        builder.show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "UserId and password not matched!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, LoginActivity.this, call1, "", true);
    }

}