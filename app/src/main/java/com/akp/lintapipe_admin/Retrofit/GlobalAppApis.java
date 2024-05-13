package com.akp.lintapipe_admin.Retrofit;
/**
 * Created by Anoop pandey-9696381023.
 */
import org.json.JSONException;
import org.json.JSONObject;

public class GlobalAppApis {
    public String Parm_Login(String MemberId, String Password) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("MemberId", MemberId);
            jsonObject1.put("Password", Password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }


    public String Parm_SATotalsalepersonReg(String MemberId) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("MemberId", MemberId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }


    public String Parm_SATotalDistributorRetailerReg(String MemberId) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("MemberId", MemberId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }

    public String Parm_SAOrderbysales(String MemberId) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("MemberId", MemberId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }

    public String Parm_SAOrderbysalesByorderId(String OrderId) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("OrderId", OrderId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }

    public String Parm_SADIS_retOrderList(String MemberId) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("MemberId", MemberId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }

    public String Parm_SADIS_retOrderListByOrderID(String OrderId) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("OrderId", OrderId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }


    public String Parm_SAAllCommissionDetail(String MemberId) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("MemberId", MemberId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }


}

