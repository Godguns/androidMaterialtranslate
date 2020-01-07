package com.example.ds6;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.sdk.model.GeneralResult;
import com.baidu.ocr.ui.camera.CameraActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class oorc extends AppCompatActivity {
    final static String TAG = "OCR";
    String data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oorc);
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

                Toast.makeText(oorc.this,title,Toast.LENGTH_LONG).show();
            }
            TextView textView=findViewById(R.id.tx);
            textView.setText(ff);

            } catch (JSONException e1) {
            e1.printStackTrace();
        }


    }



    public void ocrClick(View view) {
        // 生成intent对象
        Intent intent = new Intent(oorc.this, CameraActivity.class);

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
                    parse(content);
                }

                @Override
                public void failed(OCRError error) {
                    // 调用失败，返回OCRError对象
                    Log.e(TAG,"错误信息：" + error.getMessage());
                }
            });
        }
    }
}
