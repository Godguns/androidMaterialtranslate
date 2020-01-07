package com.example.ds6;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.cocosw.bottomsheet.BottomSheet;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.yalantis.phoenix.PullToRefreshView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import scut.carson_ho.searchview.ICallBack;
import scut.carson_ho.searchview.SearchView;
import scut.carson_ho.searchview.bCallBack;


public class MoviesFragment extends Fragment {
    String responseData = "";
    MenuItem gMenuItem;
    String key = "z4V4YVvDR6KbMd21RS1X";
    String URL = "https://fanyi-api.baidu.com/api/trans/vip/translate";
    String q = "apple";
    String from = "auto";
    String to = "zh";
    String appid = "20180412000145416";
    String salt = String.valueOf(System.currentTimeMillis());
    PullToRefreshView mPullToRefreshView;
    int REFRESH_UI = 1;
    List<String> list=new ArrayList<String>();
    String[] title = new String[]{"电话","地图","hh"};

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_movies, container, false);
        mPullToRefreshView = (PullToRefreshView) rootView.findViewById(R.id.pull_to_refresh);
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {

            @Override
            public void onRefresh() {
                Toast.makeText(getActivity(), "暂时没有数据可以更新QaQ", Toast.LENGTH_LONG).show();
                mPullToRefreshView.setRefreshing(false);
            }
        });
//
//        MagicButton magicButton = rootView.findViewById(R.id.magic_button);
//        magicButton.setMagicButtonClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                EditText editText = rootView.findViewById(R.id.edit);
//                q = editText.getText().toString();
//                sendRequestWithURLConnection();
//
//            }
//        });
        // 3. 绑定组件
       SearchView searchView = (SearchView)rootView. findViewById(R.id.edit);

        // 4. 设置点击键盘上的搜索按键后的操作（通过回调接口）
        // 参数 = 搜索框输入的内容
        searchView.setOnClickSearch(new ICallBack() {
            @Override
            public void SearchAciton(String string) {
                //System.out.println("我收到了" + string);
                q=string;
                sendRequestWithURLConnection();


            }
        });

        // 5. 设置点击返回按键后的操作（通过回调接口）
        searchView.setOnClickBack(new bCallBack() {
            @Override
            public void BackAciton() {
                final String items[] = {"中文", "英文",  "日语", "韩语", "文言文"};
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), 3);
                builder.setTitle("单选");
//                builder.setIcon(R.mipmap.ic_launcher);
                builder.setSingleChoiceItems(items, 0,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        to = "zh";
                                        break;
                                    case 1:
                                        to = "en";
                                        break;
                                    case 2:
                                        to = "jp";
                                        break;
                                }
                                Toast.makeText(getActivity(), items[which],
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Toast.makeText(getActivity(), "确定", Toast.LENGTH_SHORT)
                                .show();
                    }
                });
                builder.create().show();
            }
        });




        return rootView;


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

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            parse(responseData);
                        }
                    });
                    ;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parse(String js) {
        try {
            JSONObject jsonObject = new JSONObject(js);
            JSONArray mcourse = jsonObject.getJSONArray("trans_result");
            for (int i = 0; i < mcourse.length(); i++) {
                JSONObject jsonObjecta = mcourse.getJSONObject(i);
                final String data = jsonObjecta.getString("dst");


                new BottomSheet.Builder(getActivity()).title(data).sheet(R.menu.list).listener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case R.id.call:
                                Intent intent=new Intent();
                                intent.putExtra("ddd",data);
                                intent.setClass(getActivity(),star.class);
                                startActivity(intent);
                                break;
                        }
                    }
                }).show();
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

