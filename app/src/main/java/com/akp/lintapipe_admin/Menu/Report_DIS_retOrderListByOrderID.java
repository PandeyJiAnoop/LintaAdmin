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

public class Report_DIS_retOrderListByOrderID extends AppCompatActivity {
    RecyclerView rcvMemberLoanDetails;
    private ArrayList<HashMap<String, String>> arrRepaymentList = new ArrayList<>();
    RelativeLayout rlHeader;
    String UserName;
    private SharedPreferences sharedPreferences;
    private SharedPreferences login_preference;
    private SharedPreferences.Editor login_editor;
    TextView title_tv;
//    SearchView et_search;
    ReportAdapter adapter;
    String OrderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_dis_ret_order_list_by_order_id);
        FindId();
        OnClick();




        LoanrepaymenttApi();

//        et_search.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return true;
//            }
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                if (newText.length() >= 4) {
//                    arrRepaymentList.clear();
////                    adapter.notifyDataSetChanged();
//                    LoanrepaymenttApi();
////                    Member_EmpList("",newText,"",username);
//                }
//                else {
//                    arrRepaymentList.clear();
////                    adapter.notifyDataSetChanged();
//                    LoanrepaymenttApi();
////                    Member_EmpList("","","",username);
//                }
//                return true;
//            }
//        });


    }

    private void OnClick() {
        //        et_search = findViewById(R.id.search);
        rlHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void FindId() {
        sharedPreferences = getSharedPreferences("login_preference",MODE_PRIVATE);
        UserName = sharedPreferences.getString("U_id", "");
        OrderId=getIntent().getStringExtra("Order_id");
        rcvMemberLoanDetails=findViewById(R.id.rcvMemberLoanDetails);


        rlHeader=findViewById(R.id.rlHeader);
        title_tv=findViewById(R.id.title_tv);
    }

    public void LoanrepaymenttApi() {
        String otp1 = new GlobalAppApis().Parm_SADIS_retOrderListByOrderID(OrderId);
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.APIName_SADIS_retOrderListByOrderID(otp1);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
//                Toast.makeText(getApplicationContext(),""+result,Toast.LENGTH_LONG).show();
                try {
                    Log.v("responsememberdetails", result);
                    arrRepaymentList.clear();
                    JSONObject arrayJSONObject = new JSONObject(result);
                    String status = arrayJSONObject.getString("Status");
                    if (status.equalsIgnoreCase("true")) {
                        JSONArray jsonArrayr = arrayJSONObject.getJSONArray("Response");
                        for (int i = 0; i < jsonArrayr.length(); i++) {
                            title_tv.setText("Order Items("+jsonArrayr.length()+")");
                            JSONObject jsonObject = jsonArrayr.getJSONObject(i);
                            HashMap<String, String> hashlist = new HashMap();
                            hashlist.put("id", jsonObject.getString("id"));
                            hashlist.put("ItemName", jsonObject.getString("ItemName"));
                            hashlist.put("SrNo", jsonObject.getString("SrNo"));
                            hashlist.put("ItemCode", jsonObject.getString("ItemCode"));
                            hashlist.put("MemberId", jsonObject.getString("MemberId"));
                            hashlist.put("EntryDate", jsonObject.getString("EntryDate"));
                            hashlist.put("quantity", jsonObject.getString("quantity"));
                            hashlist.put("Amount", jsonObject.getString("Amount"));

                            hashlist.put("TempEstimateNo", jsonObject.getString("TempEstimateNo"));
//                            hashlist.put("MRP", jsonObject.getString("MRP"));
//                            hashlist.put("OrderStatus", jsonObject.getString("OrderStatus"));
                            hashlist.put("ApprovedBy", jsonObject.getString("ApprovedBy"));
                            hashlist.put("OrderStatusDate", jsonObject.getString("OrderStatusDate"));

                            hashlist.put("MRP", jsonObject.getString("MRP"));
                            hashlist.put("DistributorPrice", jsonObject.getString("DistributorPrice"));
                            hashlist.put("DistributorPercent", jsonObject.getString("DistributorPercent"));
                            hashlist.put("RetailerPrice", jsonObject.getString("RetailerPrice"));
                            hashlist.put("RetailerPercent", jsonObject.getString("RetailerPercent"));

                            hashlist.put("ApproveQuantity", jsonObject.getString("ApproveQuantity"));
                            hashlist.put("PendingQuantity", jsonObject.getString("PendingQuantity"));
                            hashlist.put("NewAmount", jsonObject.getString("NewAmount"));

                            arrRepaymentList.add(hashlist);
                        }
                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(Report_DIS_retOrderListByOrderID.this, 1);
                        adapter = new ReportAdapter(Report_DIS_retOrderListByOrderID.this, arrRepaymentList);
                        rcvMemberLoanDetails.setLayoutManager(layoutManager);
                        rcvMemberLoanDetails.setAdapter(adapter);
                    } else {
//                        AlertDialog.Builder builder = new AlertDialog.Builder(Report_DIS_retOrderListByOrderID.this);
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
//                        Toast.makeText(Report_DIS_retOrderListByOrderID.this, "Data Not Found!", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, Report_DIS_retOrderListByOrderID.this, call1, "", true);
    }



    //    Adapter class here added
    public class ReportAdapter extends RecyclerView.Adapter<Report_DIS_retOrderListByOrderID.ReportAdapter.VH> {
        Context context;
        List<HashMap<String,String>> arrayList;
        public ReportAdapter(Context context, List<HashMap<String,String>> arrayList) {
            this.arrayList=arrayList;
            this.context=context;
        }
        @NonNull
        @Override
        public Report_DIS_retOrderListByOrderID.ReportAdapter.VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.dynamic_order_viewalitems, viewGroup, false);
            Report_DIS_retOrderListByOrderID.ReportAdapter.VH viewHolder = new Report_DIS_retOrderListByOrderID.ReportAdapter.VH(view);
            return viewHolder;
        }
        @Override
        public void onBindViewHolder(@NonNull Report_DIS_retOrderListByOrderID.ReportAdapter.VH vh, int i) {
            vh.tv.setText("Sr.No - "+String.valueOf(i+1));
            vh.tv4.setText(arrayList.get(i).get("ItemName")+"("+arrayList.get(i).get("ItemCode")+")");
            vh.tv1.setText(arrayList.get(i).get("quantity"));
            vh.tv2.setText(arrayList.get(i).get("Amount"));
            vh.tv3.setText(arrayList.get(i).get("MRP"));
            vh.tv5.setText(arrayList.get(i).get("ApprovedBy"));
            vh.tv9.setText(arrayList.get(i).get("OrderStatusDate"));
            vh.tv10.setText(arrayList.get(i).get("EntryDate"));
            vh.tv11.setText(arrayList.get(i).get("ApproveQuantity"));
            vh.tv12.setText(arrayList.get(i).get("PendingQuantity"));
            vh.tv13.setText(arrayList.get(i).get("NewAmount"));

        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }
        public class VH extends RecyclerView.ViewHolder {
            TextView tv,tv1,tv2,tv3,tv4,tv5,tv9,tv10,tv11,tv12,tv13;

            public VH(@NonNull View itemView) {
                super(itemView);
                tv = itemView.findViewById(R.id.tv);
                tv1 = itemView.findViewById(R.id.tv1);
                tv2 = itemView.findViewById(R.id.tv2);
                tv3 = itemView.findViewById(R.id.tv3);
                tv4 = itemView.findViewById(R.id.tv4);
                tv5 = itemView.findViewById(R.id.tv8);
                tv9 = itemView.findViewById(R.id.tv9);
                tv10 = itemView.findViewById(R.id.tv10);
                tv11 = itemView.findViewById(R.id.tv11);
                tv12 = itemView.findViewById(R.id.tv12);
                tv13 = itemView.findViewById(R.id.tv13);
            }
        }
    }


}