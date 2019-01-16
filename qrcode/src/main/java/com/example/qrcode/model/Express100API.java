package com.example.qrcode.model;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.example.qrcode.bean.ExpressDynamic;
import com.example.qrcode.listener.OnRequestExpressInfoListener;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Express100API {
    public static String ReqUrl = "http://www.kuaidi100.com/query?type=快递公司标识码&postid=运单号";

    public void queryExpressInfoByKuaidi100(final String expressNo, final OnRequestExpressInfoListener listener) {
        new AsyncTask<Void, Void, List<ExpressDynamic>>() {

            @Override
            protected List<ExpressDynamic> doInBackground(Void... voids) {
                try {
                    if (TextUtils.isEmpty(expressNo)) {
                        return null;
                    }
                    // 1.通过快递单号获取是哪个快递公司。
                    String typeResult = getResponse("http://www.kuaidi100.com/autonumber/autoComNum?text=" + expressNo);
                    JSONObject typeJsonObject = new JSONObject(typeResult);
                    JSONArray typeDataArray = (JSONArray) typeJsonObject.get("auto");
                    if (typeDataArray.length() == 0) {
                        return null;
                    }
                    JSONObject typeObject = (JSONObject) typeDataArray.get(0);
                    String type = typeObject.getString("comCode");
                    // 2.通过快递公司及快递单号获取物流信息。
                    String kuaidiResult = getResponse("http://www.kuaidi100.com/query?type=" + type + "&postid=" + expressNo);
                    JSONObject jsonObject = new JSONObject(kuaidiResult);
                    String status = jsonObject.getString("status");
                    if (!"200".equals(status)) {
                        return null;
                    }
                    List<ExpressDynamic> list = new ArrayList<>();
                    JSONArray dataArray = (JSONArray) jsonObject.get("data");
                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject temp = (JSONObject) dataArray.get(i);
                        String date = (String) temp.get("time");
                        String dynamic = (String) temp.get("context");
                        list.add(new ExpressDynamic(expressNo, date, dynamic));
                    }
                    return list;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(List<ExpressDynamic> list) {
                super.onPostExecute(list);
                if (null != listener) {
                    if (null == list) {
                        listener.onErrorResponse();
                    } else {
                        listener.onResponse(list);
                    }
                }
            }
        }.execute();
    }

    private String getResponse(String url) throws IOException {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        HttpUriRequest request = httpGet;
        HttpContext localContext = new BasicHttpContext();
        HttpResponse response = httpClient.execute(request, localContext);
        return EntityUtils.toString(response.getEntity());
    }
}
