package com.example.ds6;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.sdk.model.GeneralResult;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity implements history.OnFragmentInteractionListener  {
    final static String TAG = "OCR";
    String ff=" sorry";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        //初始化数据


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ocrClick(v);
            }
        });
        TapTargetView.showFor(this,                 // `this` is an Activity
                TapTarget.forView(findViewById(R.id.fab), "新增拍照翻译功能！", "Photo translation")

                        .outerCircleColor(R.color.colorAccent)      // Specify a color for the outer circle
                        .outerCircleAlpha(0.96f)            // Specify the alpha amount for the outer circle
                        .targetCircleColor(R.color.white)   // Specify a color for the target circle
                        .titleTextSize(20)                  // Specify the size (in sp) of the title text
                        .titleTextColor(R.color.white)      // Specify the color of the title text
                        .descriptionTextSize(10)            // Specify the size (in sp) of the description text
                        .descriptionTextColor(R.color.colorBackground)  // Specify the color of the description text
                        .textColor(R.color.blue)            // Specify a color for both the title and description text
                        .textTypeface(Typeface.SANS_SERIF)  // Specify a typeface for the text
                        .dimColor(R.color.black)            // If set, will dim behind the view with 30% opacity of the given color
                        // .drawShadow(true)                   // Whether to draw a drop shadow or not
                        .cancelable(true)                  // Whether tapping outside the outer circle dismisses the view
                        .tintTarget(true)                   // Whether to tint the target view's color
                        .transparentTarget(false)           // Specify whether the target is transparent (displays the content underneath)
                        //.icon(drawable)                     // Specify a custom drawable to draw as the target
                        .targetRadius(60),                  // Specify the target radius (in dp)
                new TapTargetView.Listener() {          // The listener can listen for regular clicks, long clicks or cancels
                    @Override
                    public void onTargetClick(TapTargetView view) {
                        super.onTargetClick(view);      // This call is optional
                        //  doSomething();
                    }
                });

        OCR.getInstance(this).initAccessToken(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken result) {
                // 调用成功，返回AccessToken对象
                String token = result.getAccessToken();
                Log.e(TAG,result.toString());
            }
            @Override
            public void onError(OCRError error) {
                // 调用失败，返回OCRError子类SDKError对象
                Log.e(TAG,error.toString());
            }
        }, getApplicationContext());
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    private  void parse(String js){

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
                ff+=title+"";
                // String title = value.optString("title");

                Toast.makeText(MainActivity.this,title,Toast.LENGTH_LONG).show();
            }
          //  TextView textView=findViewById(R.id.tx);
           // textView.setText(ff);


        } catch (JSONException e1) {
            e1.printStackTrace();
        }


    }
    public void ocrClick(View view) {
        // 生成intent对象
        Intent intent = new Intent(MainActivity.this, CameraActivity.class);

        // 设置临时存储
        intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH, FileUtil.getSaveFile(getApplication()).getAbsolutePath());

        // 调用除银行卡，身份证等识别的activity
        intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_GENERAL);

        startActivityForResult(intent, 111);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111 && resultCode == Activity.RESULT_OK) {
            // 获取调用参数
            String contentType = data.getStringExtra(CameraActivity.KEY_CONTENT_TYPE);
            // 通过临时文件获取拍摄的图片
            String filePath = FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath();


            OCRManager.recognizeAccurateBasic(this, filePath, new OCRManager.OCRCallBack<GeneralResult>() {
                @Override
                public void succeed(GeneralResult data) {
                    // 调用成功，返回GeneralResult对象
                    String content = OCRManager.getResult(data);
                    Log.e(TAG,content + "");
                    Intent intent=new Intent();
                    intent.setClass(MainActivity.this,paizhaojieguo.class);
                    intent.putExtra("tbefore",content);
                    intent.putExtra("tres",content);
                    startActivity(intent);
                    parse(content);
                }

                @Override
                public void failed(OCRError error) {
                    // 调用失败，返回OCRError对象
                    Intent intent=new Intent();
                    intent.setClass(MainActivity.this,paizhaojieguo.class);
                    intent.putExtra("tbefore",ff);
                    intent.putExtra("tres",error.getMessage());
                    startActivity(intent);
                    Log.e(TAG,"错误信息：" + error.getMessage());
                }
            });
        }
    }

}