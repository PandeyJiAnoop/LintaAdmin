package com.akp.lintapipe_admin;

import static com.akp.lintapipe_admin.Retrofit.API_Config.getApiClient_ByPost;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.akp.lintapipe_admin.Basic.ChangePasswordScreen;
import com.akp.lintapipe_admin.Basic.LoginActivity;
import com.akp.lintapipe_admin.Basic.SplashScreen;
import com.akp.lintapipe_admin.Menu.Report_AllCommissionDetail;
import com.akp.lintapipe_admin.Menu.Report_DIS_retOrderList;
import com.akp.lintapipe_admin.Menu.Report_DIS_retOrderListByOrderID;
import com.akp.lintapipe_admin.Menu.Report_Orderbysales;
import com.akp.lintapipe_admin.Menu.Report_TotalDistributorRetailerReg;
import com.akp.lintapipe_admin.Menu.Report_TotalsalepersonReg;
import com.akp.lintapipe_admin.Retrofit.ApiService;
import com.akp.lintapipe_admin.Retrofit.ConnectToRetrofit;
import com.akp.lintapipe_admin.Retrofit.GlobalAppApis;
import com.akp.lintapipe_admin.Retrofit.RetrofitCallBackListenar;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;

public class MainActivity extends AppCompatActivity {

    private LinearLayout dash_ll,logout_ll;
    private ImageView sliding_menu;
    private DrawerLayout mDrawer;
    private ImageView close;
    SliderLayout slider;
TextView marqueeText;

TextView today_business_tv,total_sales_tv,sales_reg_tv,comission_report_tv,change_pass_tv,
        self_order_tv,sales_order_tv,tv1,tv2,tv3,tv4;
LinearLayout total_sales_ll,sales_reg_ll,comission_report_ll,changepass_ll,total_buss_ll,
        self_order_ll,sales_order_ll,change_pass_ll;
LinearLayout SATotalsalepersonRegll,SATotalDistributorRetailerRegll,SAAllCommissionDetailll,SADIS_retOrderListll,SAOrderbysalesll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findId();
        OnClick();
        getDashboardAPI();


    }

    private void OnClick() {
        sliding_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
//                mDrawer.openDrawer(Gravity.START);
                mDrawer.openDrawer(Gravity.START);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDrawer.closeDrawer(Gravity.START);
                    }
                });
                dash_ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDrawer.closeDrawer(Gravity.START);
                    }
                });

                SATotalsalepersonRegll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), Report_TotalsalepersonReg.class);
                        startActivity(intent);
                        mDrawer.closeDrawer(Gravity.START);
                    }
                });
                SATotalDistributorRetailerRegll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), Report_TotalDistributorRetailerReg.class);
                        startActivity(intent);
                        mDrawer.closeDrawer(Gravity.START);
                    }
                });
                SAAllCommissionDetailll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), Report_AllCommissionDetail.class);
                        startActivity(intent);
                        mDrawer.closeDrawer(Gravity.START);
                    }
                });
                SADIS_retOrderListll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), Report_DIS_retOrderList.class);
                        startActivity(intent);
                        mDrawer.closeDrawer(Gravity.START);
                    }
                });
                SAOrderbysalesll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), Report_Orderbysales.class);
                        startActivity(intent);
                        mDrawer.closeDrawer(Gravity.START);
                    }
                });
                changepass_ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), ChangePasswordScreen.class);
                        startActivity(intent);
                        mDrawer.closeDrawer(Gravity.START);
                    }
                });
                logout_ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDrawer.closeDrawer(Gravity.START);
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("Are you sure want to logout?");
                        builder.setPositiveButton(Html.fromHtml("<font color='#E5B80B'>Yes</font>"), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences myPrefs = getSharedPreferences("login_preference", MODE_PRIVATE);
                                SharedPreferences.Editor editor = myPrefs.edit();
                                editor.clear();
                                editor.commit();
                                Intent intent = new Intent(getApplicationContext(), SplashScreen.class);
                                intent.putExtra("finish", true);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
                                startActivity(intent);
                            }
                        });
                        builder.setNegativeButton(Html.fromHtml("<font color='#E5B80B'>NO</font>"), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.show(); }}); }});
    }

    private void findId() {
        dash_ll=findViewById(R.id.dash_ll);
        logout_ll=findViewById(R.id.logout_ll);
        close=findViewById(R.id.close);
        sliding_menu = (ImageView) findViewById(R.id.sliding_menu);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        slider = (SliderLayout) findViewById(R.id.slider);
        setSliderViews();
        marqueeText=findViewById(R.id.marqueeText);
        marqueeText.setSelected(true);
        today_business_tv=findViewById(R.id.today_business_tv);
        total_sales_tv=findViewById(R.id.total_sales_tv);
        sales_reg_tv=findViewById(R.id.sales_reg_tv);
        comission_report_tv=findViewById(R.id.comission_report_tv);
        change_pass_tv=findViewById(R.id.change_pass_tv);
        self_order_tv=findViewById(R.id.self_order_tv);
        sales_order_tv=findViewById(R.id.sales_order_tv);
        total_sales_ll=findViewById(R.id.total_sales_ll);
        sales_reg_ll=findViewById(R.id.sales_reg_ll);
        comission_report_ll=findViewById(R.id.comission_report_ll);
        changepass_ll=findViewById(R.id.changepass_ll);
        self_order_ll=findViewById(R.id.self_order_ll);
        sales_order_ll=findViewById(R.id.sales_order_ll);
        change_pass_ll=findViewById(R.id.change_pass_ll);
        total_buss_ll=findViewById(R.id.total_buss_ll);
        tv1=findViewById(R.id.tv1);
        tv2=findViewById(R.id.tv2);
        tv3=findViewById(R.id.tv3);
        tv4=findViewById(R.id.tv4);

        SATotalsalepersonRegll=findViewById(R.id.SATotalsalepersonRegll);
        SATotalDistributorRetailerRegll=findViewById(R.id.SATotalDistributorRetailerRegll);
        SAAllCommissionDetailll=findViewById(R.id.SAAllCommissionDetailll);
        SADIS_retOrderListll=findViewById(R.id.SADIS_retOrderListll);
        SAOrderbysalesll=findViewById(R.id.SAOrderbysalesll);

        total_sales_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),Report_TotalsalepersonReg.class);
                startActivity(intent);

            }
        });
        sales_reg_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Report_TotalDistributorRetailerReg.class);
                startActivity(intent);
            }
        });
        comission_report_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Report_AllCommissionDetail.class);
                startActivity(intent);
            }
        });
        self_order_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Report_DIS_retOrderList.class);
                startActivity(intent);
            }
        });
        sales_order_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Report_Orderbysales.class);
                startActivity(intent);
            }
        });
        change_pass_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent change_pass = new Intent(MainActivity.this, ChangePasswordScreen.class);
                startActivity(change_pass);
            }
        });
        total_buss_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent change_pass = new Intent(MainActivity.this, Report_Orderbysales.class);
                startActivity(change_pass);
            }
        });

    }

    private void setSliderViews() {
        for (int i = 0; i <= 4; i++) {
            SliderView sliderView = new SliderView(MainActivity.this);
            switch (i) {
                case 0:
                    sliderView.setImageDrawable(R.drawable.ban);
                    sliderView.setDescription("Welcome To\n"+"LintaPipe Admin Group");
                    break;
                case 1:
                    sliderView.setImageDrawable(R.drawable.ban_a);
//                    sliderView.setDescription("सच होगा सपना");
                    break;
                case 2:
                    sliderView.setImageDrawable(R.drawable.ban);;
//                    sliderView.setDescription("सोचो  एक  नयी  दुनिया ");
                    break;
                case 3:
                    sliderView.setImageDrawable(R.drawable.ban_a);;
//                    sliderView.setDescription("खुशियां  हो  जहाँ  ");
                    break;
                case 4:
                    sliderView.setImageDrawable(R.drawable.ban);;
//                    sliderView.setDescription("खुशियां  हो  जहाँ  ");
                    break;
            }
            sliderView.setImageScaleType(ImageView.ScaleType.FIT_XY);
            final int finalI = i;
            sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(SliderView sliderView) {
                    Toast.makeText(getApplicationContext(), "This is slider " + (finalI + 1), Toast.LENGTH_SHORT).show();
                }
            });
            //at last add this view in your layout :
            slider.addSliderView(sliderView);
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton(Html.fromHtml("<font color='#000000'>Yes</font>"), new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id) {
                        //finish();
                        onSuperBackPressed();
                        //super.onBackPressed();
                    } })
                .setNegativeButton(Html.fromHtml("<font color='#000000'>No</font>"), new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void onSuperBackPressed(){
        super.onBackPressed();
    }

    public void getDashboardAPI() {
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.SAAllDashboard();
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
                Log.d("SAAllDashboard", result);
//                Toast.makeText(getApplicationContext(),""+result,Toast.LENGTH_LONG).show();
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getString("Status").equalsIgnoreCase("true")) {
                        JSONArray Jarray = object.getJSONArray("Response");
                        for (int i = 0; i < Jarray.length(); i++) {
                            JSONObject jsonobject = Jarray.getJSONObject(i);
                            today_business_tv.setText("Rs. "+jsonobject.getString("TotalApprovedBusiness"));
                            total_sales_tv.setText("Total Sales\nPerson("+jsonobject.getString("TotalSalePerson")+")");
                            sales_reg_tv.setText("Total Reg.\nby Sales("+jsonobject.getString("TotalRegisteredBysaleperson")+")");
                            comission_report_tv.setText("Commission\nReport("+jsonobject.getString("SalesExecutiveCommissionn")+")");
                            self_order_tv.setText("Dist./Retail\nOrder("+jsonobject.getString("TotalOrderByRetSelf")+")");
                            sales_order_tv.setText("Sales\nOrder("+jsonobject.getString("TotalOrderBySale")+")");
                            tv1.setText(jsonobject.getString("TotalDistributor"));
                            tv2.setText(jsonobject.getString("TotalPrimeRetailer"));
                            tv3.setText(jsonobject.getString("TotalRetailer"));
                            tv4.setText(jsonobject.getString("TotalCorportae"));
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Data Not Found!", Toast.LENGTH_LONG).show();
                    } } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "UserId and password not matched!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                } } }, MainActivity.this, call1, "", true);
    }

}


