package com.akp.lintapipe_admin.Menu;

import static com.akp.lintapipe_admin.Retrofit.API_Config.getApiClient_ByPost;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;

public class Report_DIS_retOrderList extends AppCompatActivity {
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
    String dest_file_path = "Invoice.pdf";
    private URL url;
    ImageView norecord_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_dis_ret_order_list);
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
                }  else {
                    arrRepaymentList.clear();
                    adapter.notifyDataSetChanged();
                    LoanrepaymenttApi("");
//                    Member_EmpList("","","",username);
                } return true;
            } });
    }

    private void FindId() {
        sharedPreferences = getSharedPreferences("login_preference",MODE_PRIVATE);
        UserName = sharedPreferences.getString("U_id", "");
        rcvMemberLoanDetails=findViewById(R.id.rcvMemberLoanDetails);
        norecord_img = findViewById(R.id.norecord_img);
        rlHeader=findViewById(R.id.rlHeader);
        title_tv=findViewById(R.id.title_tv);
        et_search = findViewById(R.id.search);
    }

    public void LoanrepaymenttApi(String memberid) {
        String otp1 = new GlobalAppApis().Parm_SADIS_retOrderList(memberid);
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.APIName_SADIS_retOrderList(otp1);
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
                        norecord_img.setVisibility(View.GONE);
                        JSONArray jsonArrayr = arrayJSONObject.getJSONArray("Response");
                        for (int i = 0; i < jsonArrayr.length(); i++) {
                            title_tv.setText("Dis.|Retailer Order("+jsonArrayr.length()+")");
                            JSONObject jsonObject = jsonArrayr.getJSONObject(i);
                            HashMap<String, String> hashlist = new HashMap();
                            hashlist.put("SalepersonName", jsonObject.getString("SalepersonName"));
                            hashlist.put("OrderID", jsonObject.getString("OrderID"));
                            hashlist.put("Amount", jsonObject.getString("Amount"));
                            hashlist.put("OrderStatus", jsonObject.getString("OrderStatus"));
                            hashlist.put("EntryDate", jsonObject.getString("EntryDate"));
                            hashlist.put("PaymentStatus", jsonObject.getString("PaymentStatus"));
                            hashlist.put("DeliveryStatus", jsonObject.getString("DeliveryStatus"));
                            hashlist.put("urlAction", jsonObject.getString("urlAction"));
                            hashlist.put("Producttype", jsonObject.getString("Producttype"));
                            hashlist.put("Discount", jsonObject.getString("Discount"));
                            hashlist.put("Payable", jsonObject.getString("Payable"));
                            arrRepaymentList.add(hashlist);
                        }
                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(Report_DIS_retOrderList.this, 1);
                        adapter = new ReportAdapter(Report_DIS_retOrderList.this, arrRepaymentList);
                        rcvMemberLoanDetails.setLayoutManager(layoutManager);
                        rcvMemberLoanDetails.setAdapter(adapter);
                    } else {  norecord_img.setVisibility(View.VISIBLE);
//                        AlertDialog.Builder builder = new AlertDialog.Builder(Report_DIS_retOrderList.this);
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
//                        Toast.makeText(Report_DIS_retOrderList.this, "Data Not Found!", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, Report_DIS_retOrderList.this, call1, "", true);
    }


    //    Adapter class here added
    public class ReportAdapter extends RecyclerView.Adapter<Report_DIS_retOrderList.ReportAdapter.VH> {
        Context context;
        List<HashMap<String,String>> arrayList;
        public ReportAdapter(Context context, List<HashMap<String,String>> arrayList) {
            this.arrayList=arrayList;
            this.context=context;
        }
        @NonNull
        @Override
        public Report_DIS_retOrderList.ReportAdapter.VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.dynamic_myorder, viewGroup, false);
            Report_DIS_retOrderList.ReportAdapter.VH viewHolder = new Report_DIS_retOrderList.ReportAdapter.VH(view);
            return viewHolder;
        }
        @Override
        public void onBindViewHolder(@NonNull Report_DIS_retOrderList.ReportAdapter.VH vh, int i) {
            vh.tv.setText("Sr.No - "+String.valueOf(i+1));
            vh.tv1.setText(arrayList.get(i).get("SalepersonName"));
            vh.tv2.setText(arrayList.get(i).get("OrderID"));
            vh.tv3.setText(arrayList.get(i).get("Amount"));
            vh.tv4.setText(arrayList.get(i).get("OrderStatus"));
            vh.tv5.setText(arrayList.get(i).get("EntryDate"));
            vh.tv6.setText(arrayList.get(i).get("PaymentStatus"));
            vh.tv7.setText(arrayList.get(i).get("DeliveryStatus"));
            vh.tv8.setText(arrayList.get(i).get("Producttype"));

            vh.tv9.setText(arrayList.get(i).get("Discount"));
            vh.tv10.setText(arrayList.get(i).get("Payable"));



            if (arrayList.get(i).get("OrderStatus").equalsIgnoreCase("Placed")){
                vh.tv4.setTextColor(Color.BLUE);
            }
            else if (arrayList.get(i).get("OrderStatus").equalsIgnoreCase("Approved")){
                vh.tv4.setTextColor(Color.GREEN);
            }
            else {
                vh.tv4.setTextColor(Color.RED);
            }
            String path=arrayList.get(i).get("urlAction");


            vh.view_all_order_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getApplicationContext(), Report_DIS_retOrderListByOrderID.class);
                    intent.putExtra("Order_id",arrayList.get(i).get("OrderID"));
                    startActivity(intent);
                } });

            vh.invoice_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url_invoice = arrayList.get(i).get("urlAction");
                    Intent in = new Intent(Intent.ACTION_VIEW);
                    in.setData(Uri.parse(url_invoice));
                    startActivity(in);
                    try {
                        url = new URL(path);
                    } catch (MalformedURLException e) {
                        Toast.makeText(Report_DIS_retOrderList.this, "Error:- "+e, Toast.LENGTH_LONG).show();
                        e.printStackTrace();  }
                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url + ""));
                    request.setTitle(dest_file_path);
                    request.setMimeType("application/pdf");
                    request.allowScanningByMediaScanner();
                    request.setAllowedOverMetered(true);
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "AldoFiles/" + dest_file_path);
                    DownloadManager downloadManager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
                    downloadManager.enqueue(request);
                    Toast.makeText(Report_DIS_retOrderList.this, "Invoice Download Successfully.,Saved to your Internal Storage", Toast.LENGTH_LONG).show();
                    finish();
                } });

        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public class VH extends RecyclerView.ViewHolder {
            TextView tv,tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9,tv10;
            Button view_all_order_btn,invoice_btn;

            public VH(@NonNull View itemView) {
                super(itemView);
                tv = itemView.findViewById(R.id.tv);
                tv1 = itemView.findViewById(R.id.tv1);
                tv2 = itemView.findViewById(R.id.tv2);
                tv3 = itemView.findViewById(R.id.tv3);
                tv4 = itemView.findViewById(R.id.tv4);
                tv5 = itemView.findViewById(R.id.tv5);
                tv6 = itemView.findViewById(R.id.tv6);
                tv7 = itemView.findViewById(R.id.tv7);
                tv8 = itemView.findViewById(R.id.tv8);
                tv9 = itemView.findViewById(R.id.tv9);
                tv10 = itemView.findViewById(R.id.tv10);
                view_all_order_btn = itemView.findViewById(R.id.view_all_order_btn);
                invoice_btn = itemView.findViewById(R.id.invoice_btn);

            } }}


}