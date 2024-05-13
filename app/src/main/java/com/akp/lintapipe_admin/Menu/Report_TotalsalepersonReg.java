package com.akp.lintapipe_admin.Menu;

import static com.akp.lintapipe_admin.Retrofit.API_Config.getApiClient_ByPost;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.akp.lintapipe_admin.R;
import com.akp.lintapipe_admin.Retrofit.ApiService;
import com.akp.lintapipe_admin.Retrofit.ConnectToRetrofit;
import com.akp.lintapipe_admin.Retrofit.GlobalAppApis;
import com.akp.lintapipe_admin.Retrofit.RetrofitCallBackListenar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;

public class Report_TotalsalepersonReg extends AppCompatActivity {
    RecyclerView rcvMemberLoanDetails;
    private ArrayList<HashMap<String, String>> arrRepaymentList = new ArrayList<>();
    RelativeLayout rlHeader;
    String UserName;
    private SharedPreferences sharedPreferences;
    private SharedPreferences login_preference;
    private SharedPreferences.Editor login_editor;
    TextView title_tv;
    SearchView et_search;
 ReportAdapter adapter;

 ImageView norecord_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_totalsaleperson_reg);
        FindId();
        OnClick();
        LoanrepaymenttApi("");
    }

    private void OnClick() {
        rlHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        et_search.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() >= 4) {
                    arrRepaymentList.clear();
                    adapter.notifyDataSetChanged();
                    LoanrepaymenttApi(newText);
//                    Member_EmpList("",newText,"",username);
                }
                else {
                    arrRepaymentList.clear();
                    adapter.notifyDataSetChanged();
                    LoanrepaymenttApi("");
//                    Member_EmpList("","","",username);
                }
                return true;
            }
        });

    }

    private void FindId() {
        sharedPreferences = getSharedPreferences("login_preference",MODE_PRIVATE);
        UserName = sharedPreferences.getString("U_id", "");
        rcvMemberLoanDetails=findViewById(R.id.rcvMemberLoanDetails);


        rlHeader=findViewById(R.id.rlHeader);
        title_tv=findViewById(R.id.title_tv);
        et_search = findViewById(R.id.search);
        norecord_img = findViewById(R.id.norecord_img);
    }

    public void LoanrepaymenttApi(String MemberId) {
        String otp1 = new GlobalAppApis().Parm_SATotalsalepersonReg(MemberId);
        Log.d("APIName_SATotalsalepersonReg", otp1);
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.APIName_SATotalsalepersonReg(otp1);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
                Log.d("remhf", result);

//                Toast.makeText(getApplicationContext(),""+result,Toast.LENGTH_LONG).show();
                try {
//                    arrRepaymentList.clear();
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("Status");
                    if (status.equalsIgnoreCase("true")) {
                        norecord_img.setVisibility(View.GONE);
                        JSONArray jsonArrayr = jsonObject.getJSONArray("Response");
                        for (int i = 0; i < jsonArrayr.length(); i++) {
                            title_tv.setText("Total Sale Person Reg("+jsonArrayr.length()+")");
                            JSONObject arrayJSONObject = jsonArrayr.getJSONObject(i);
                            HashMap<String, String> hashlist = new HashMap();
                            hashlist.put("EmployeeName", arrayJSONObject.getString("EmployeeName"));
                            hashlist.put("EmployeCode", arrayJSONObject.getString("EmployeCode"));
                            hashlist.put("FatherName", arrayJSONObject.getString("FatherName"));
                            hashlist.put("PersonalEmailAddress", arrayJSONObject.getString("PersonalEmailAddress"));
                            hashlist.put("PersonalContactNo", arrayJSONObject.getString("PersonalContactNo"));
                            hashlist.put("DesignationName", arrayJSONObject.getString("DesignationName"));
                            hashlist.put("DesignationId", arrayJSONObject.getString("DesignationId"));
                            hashlist.put("MonthlyBasicSalary", arrayJSONObject.getString("MonthlyBasicSalary"));
                            hashlist.put("DOJ", arrayJSONObject.getString("DOJ"));
                            hashlist.put("sponsername", arrayJSONObject.getString("sponsername"));
                            hashlist.put("sponsorId", arrayJSONObject.getString("sponsorId"));
                            arrRepaymentList.add(hashlist);
                        }
                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(Report_TotalsalepersonReg.this, 1);
                        adapter = new ReportAdapter(Report_TotalsalepersonReg.this, arrRepaymentList);
                        rcvMemberLoanDetails.setLayoutManager(layoutManager);
                        rcvMemberLoanDetails.setAdapter(adapter);
                    } else {
//                        AlertDialog.Builder builder = new AlertDialog.Builder(Report_TotalsalepersonReg.this);
//                        builder.setTitle("Data Not Found!")
//                                .setMessage("Data Not Found!")
//                                .setCancelable(false)
//                                .setIcon(R.drawable.appicon)
//                                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int id) {
//                                        dialog.cancel();
//                                    }
//                                });
//                        builder.create().show();
                        norecord_img.setVisibility(View.VISIBLE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, Report_TotalsalepersonReg.this, call1, "", true);
    }



    //    Adapter class here added
    public class ReportAdapter extends RecyclerView.Adapter<Report_TotalsalepersonReg.ReportAdapter.VH> {
        Context context;
        List<HashMap<String,String>> arrayList;
        public ReportAdapter(Context context, List<HashMap<String,String>> arrayList) {
            this.arrayList=arrayList;
            this.context=context;
        }
        @NonNull
        @Override
        public Report_TotalsalepersonReg.ReportAdapter.VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.report_salesreg, viewGroup, false);
            Report_TotalsalepersonReg.ReportAdapter.VH viewHolder = new Report_TotalsalepersonReg.ReportAdapter.VH(view);
            return viewHolder;
        }
        @Override
        public void onBindViewHolder(@NonNull Report_TotalsalepersonReg.ReportAdapter.VH vh, int i) {
            vh.tv.setText(String.valueOf(i+1));
            vh.tv1.setText(arrayList.get(i).get("EmployeeName")+"("+arrayList.get(i).get("EmployeCode")+")");
            vh.tv2.setText(arrayList.get(i).get("FatherName"));
            vh.tv3.setText(arrayList.get(i).get("PersonalContactNo"));
            vh.tv4.setText(arrayList.get(i).get("PersonalEmailAddress"));
            vh.tv5.setText(arrayList.get(i).get("DesignationName"));
            vh.tv6.setText(arrayList.get(i).get("DOJ"));
            vh.tv7.setText(arrayList.get(i).get("sponsername"));
            vh.tv8.setText(arrayList.get(i).get("sponsorId"));
            vh.tv9.setText(arrayList.get(i).get("sponsername"));
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }
        public class VH extends RecyclerView.ViewHolder {
            TextView tv,tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9;
            public VH(@NonNull View itemView) {
                super(itemView);
                tv=itemView.findViewById(R.id.tv);
                tv1=itemView.findViewById(R.id.tv1);
                tv2=itemView.findViewById(R.id.tv2);
                tv3=itemView.findViewById(R.id.tv3);
                tv4=itemView.findViewById(R.id.tv4);
                tv5=itemView.findViewById(R.id.tv5);
                tv6=itemView.findViewById(R.id.tv6);
                tv7=itemView.findViewById(R.id.tv7);
                tv8=itemView.findViewById(R.id.tv8);
                tv9=itemView.findViewById(R.id.tv9);

            }
        }}
}