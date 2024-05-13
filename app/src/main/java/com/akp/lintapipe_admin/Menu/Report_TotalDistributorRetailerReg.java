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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.google.firebase.firestore.auth.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;

public class Report_TotalDistributorRetailerReg extends AppCompatActivity {
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
        setContentView(R.layout.activity_report_total_distributor_retailer_reg);
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
//                    LoanrepaymenttApi(UserName,cityid,newText);
//                    Member_EmpList("",newText,"",username);
                }
                else {
                    arrRepaymentList.clear();
                    adapter.notifyDataSetChanged();
                    LoanrepaymenttApi("");
//                    Member_EmpList("","","",username);
                }return true;
            } });
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
        String otp1 = new GlobalAppApis().Parm_SATotalDistributorRetailerReg(MemberId);
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.APIName_SATotalDistributorRetailerReg(otp1);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
//                Toast.makeText(getApplicationContext(),""+result,Toast.LENGTH_LONG).show();
                try {
                    Log.v("responsememberdetails", result);
//                    arrRepaymentList.clear();
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("Status");
                    if (status.equalsIgnoreCase("true")) {
                        norecord_img.setVisibility(View.GONE);
                        JSONArray jsonArrayr = jsonObject.getJSONArray("Response");
                        for (int i = 0; i < jsonArrayr.length(); i++) {
                            title_tv.setText("Register By Sales("+jsonArrayr.length()+")");
                            JSONObject arrayJSONObject = jsonArrayr.getJSONObject(i);
                            HashMap<String, String> hashlist = new HashMap();
                            hashlist.put("SalespersonId", arrayJSONObject.getString("SalespersonId"));
                            hashlist.put("SalespersonName", arrayJSONObject.getString("SalespersonName"));
                            hashlist.put("EmployeCode", arrayJSONObject.getString("EmployeCode"));
                            hashlist.put("EmployeeName", arrayJSONObject.getString("EmployeeName"));
                            hashlist.put("ActiveStatus", arrayJSONObject.getString("ActiveStatus"));
                            hashlist.put("Password", arrayJSONObject.getString("Password"));
                            hashlist.put("EntryDate", arrayJSONObject.getString("EntryDate"));
                            hashlist.put("WorkContactNo", arrayJSONObject.getString("WorkContactNo"));
                            hashlist.put("GSTIn", arrayJSONObject.getString("GSTIn"));
                            hashlist.put("AadharNo", arrayJSONObject.getString("AadharNo"));
                            hashlist.put("PANNo", arrayJSONObject.getString("PANNo"));
                            hashlist.put("DegiName", arrayJSONObject.getString("DegiName"));
                            hashlist.put("City", arrayJSONObject.getString("City"));
                            hashlist.put("FullAddress", arrayJSONObject.getString("FullAddress"));
//                            hashlist.put("sponsername", arrayJSONObject.getString("sponsername"));
                            arrRepaymentList.add(hashlist);
                        }
                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(Report_TotalDistributorRetailerReg.this, 1);
                        adapter = new ReportAdapter(Report_TotalDistributorRetailerReg.this, arrRepaymentList);
                        rcvMemberLoanDetails.setLayoutManager(layoutManager);
                        rcvMemberLoanDetails.setAdapter(adapter);
                    } else {  norecord_img.setVisibility(View.VISIBLE);
//                        AlertDialog.Builder builder = new AlertDialog.Builder(Report_TotalDistributorRetailerReg.this);
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
//                        Toast.makeText(Report_TotalDistributorRetailerReg.this, "Data Not Found!", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }} }, Report_TotalDistributorRetailerReg.this, call1, "", true);
    }


    //    Adapter class here added
    public class ReportAdapter extends RecyclerView.Adapter<Report_TotalDistributorRetailerReg.ReportAdapter.VH> {
        Context context;
        List<HashMap<String,String>> arrayList;
        public ReportAdapter(Context context, List<HashMap<String,String>> arrayList) {
            this.arrayList=arrayList;
            this.context=context;
        }
        @NonNull
        @Override
        public Report_TotalDistributorRetailerReg.ReportAdapter.VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.report_distretreg, viewGroup, false);
            Report_TotalDistributorRetailerReg.ReportAdapter.VH viewHolder = new Report_TotalDistributorRetailerReg.ReportAdapter.VH(view);
            return viewHolder;
        }
        @Override
        public void onBindViewHolder(@NonNull Report_TotalDistributorRetailerReg.ReportAdapter.VH vh, int i) {
            vh.tv.setText(String.valueOf(i+1));
            vh.tv1.setText(arrayList.get(i).get("SalespersonId")+"("+arrayList.get(i).get("SalespersonName")+")");
            vh.tv2.setText(arrayList.get(i).get("EmployeCode")+"("+arrayList.get(i).get("EmployeeName")+")");
            vh.tv3.setText(arrayList.get(i).get("ActiveStatus"));
            vh.tv4.setText(arrayList.get(i).get("Password"));
            vh.tv5.setText(arrayList.get(i).get("EntryDate"));
            vh.tv6.setText(arrayList.get(i).get("WorkContactNo"));
            vh.tv7.setText(arrayList.get(i).get("GSTIn"));
            vh.tv8.setText(arrayList.get(i).get("AadharNo"));
            vh.tv9.setText(arrayList.get(i).get("PANNo"));
            vh.tv10.setText(arrayList.get(i).get("DegiName"));
            vh.tv11.setText(arrayList.get(i).get("City"));
            vh.tv12.setText(arrayList.get(i).get("FullAddress"));
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }
        public class VH extends RecyclerView.ViewHolder {
            TextView tv,tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9,tv10,tv11,tv12;
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
                tv10=itemView.findViewById(R.id.tv10);
                tv11=itemView.findViewById(R.id.tv11);
                tv12=itemView.findViewById(R.id.tv12);
            }}}
}