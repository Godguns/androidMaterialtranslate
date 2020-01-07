package com.example.ds6;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.cocosw.bottomsheet.BottomSheet;
import com.yalantis.phoenix.PullToRefreshView;

import org.angmarch.views.NiceSpinner;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class paizhaojieguo extends Activity {
    String responseData = "";
    String key = "z4V4YVvDR6KbMd21RS1X";
    String URL = "https://fanyi-api.baidu.com/api/trans/vip/translate";
    String q = "apple";
    String from = "auto";
    String to = "en";
    String appid = "20180412000145416";
    String salt = String.valueOf(System.currentTimeMillis());
    PullToRefreshView mPullToRefreshView;
    int REFRESH_UI = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paizhaojieguo);
       Intent intent=getIntent();
       String text1=intent.getStringExtra("tbefore");
       String text2=intent.getStringExtra("tres");
        NiceSpinner niceSpinner = (NiceSpinner) findViewById(R.id.nice_spinner);
        List<String> dataset = new LinkedList<>(Arrays.asList("中文  ", "英文  ", "日语  ", "韩语  ", "法语  "));
        niceSpinner.attachDataSource(dataset);
        niceSpinner.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        to="zh";
                        sendRequestWithURLConnection();
                        break;
                    case 1:
                        to="en";
                        sendRequestWithURLConnection();
                        break;
                    case 2:
                        to="jp";
                        sendRequestWithURLConnection();
                        break;
                    case 3:
                        to="kor";
                        sendRequestWithURLConnection();
                        break;

                }
            }
        });

        parse(text1);

       sendRequestWithURLConnection();



sendRequestWithURLConnection();

    }
    public void sendRequestWithURLConnection() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {


                    String src = appid + q + salt + key; // 加密前的原文
                    String sign = MD5.md5(src);
                    String newq = decodeUnicode(q);
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("https://fanyi-api.baidu.com/api/trans/vip/translate?q=" + newq + "&from=" + from + "&to=" + to + "&appid=" + appid + "&salt=" + salt + "&sign=" + sign)
                            .build();
                    Response response = client.newCall(request).execute();
                    responseData = response.body().string();

                   runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            parse2(responseData);
                        }
                    });
                    ;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private  void parse(String js){
        String ff=" ";
        // List<Data> list = new ArrayList<Data>();
        try{
            //第一步，string参数相当于一个JSON,依次解析下一步
            JSONObject json = new JSONObject(js);
            // JSONObject result = json.getJSONObject("words_result");
            //当你获得JSONArray 类型的时候，然后要写for循环遍历获取里面每一个Object

            JSONArray data = json.getJSONArray("words_result");
            for (int i = 0; i < data.length(); i++) {
                JSONObject value = data.getJSONObject(i);
                //获取到title值

                String title = value.getString("words");
                ff+=title;
                // String title = value.optString("title");

               // Toast.makeText(oorc.this,title,Toast.LENGTH_LONG).show();
            }
            TextView textView=findViewById(R.id.yuanci);
            textView.setText(ff);
            q=ff;

        } catch (JSONException e1) {
            e1.printStackTrace();
        }


    }
    private void parse2(String js) {
        try {
            JSONObject jsonObject = new JSONObject(js);
            JSONArray mcourse = jsonObject.getJSONArray("trans_result");
            for (int i = 0; i < mcourse.length(); i++) {
                JSONObject jsonObjecta = mcourse.getJSONObject(i);
                final String data = jsonObjecta.getString("dst");
                TextView textView1=findViewById(R.id.yuanci);
                TextView textView2=findViewById(R.id.fyjg);
                textView1.setText(q);
                textView2.setText(data);

            }

        } catch (JSONException e) {

        }
    }

    public void showresponse(String data) {


    }

    //Unicode编码转中文
    public static String decodeUnicode(String theString) {

        char aChar;

        int len = theString.length();

        StringBuffer outBuffer = new StringBuffer(len);

        for (int x = 0; x < len; ) {

            aChar = theString.charAt(x++);

            if (aChar == '\\') {

                aChar = theString.charAt(x++);

                if (aChar == 'u') {


                    int value = 0;

                    for (int i = 0; i < 4; i++) {

                        aChar = theString.charAt(x++);

                        switch (aChar) {

                            case '0':

                            case '1':

                            case '2':

                            case '3':

                            case '4':

                            case '5':

                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed   \\uxxxx   encoding.");
                        }

                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';

                    else if (aChar == 'n')

                        aChar = '\n';

                    else if (aChar == 'f')

                        aChar = '\f';

                    outBuffer.append(aChar);

                }

            } else

                outBuffer.append(aChar);

        }

        return outBuffer.toString();

    }

}
