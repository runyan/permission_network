package com.example.jinfei.myapplication;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jinfei.myapplication.bean.ResponseBean;
import com.example.jinfei.myapplication.client.CommonOkHttpClient;
import com.example.jinfei.myapplication.listener.DisposeDataHandler;
import com.example.jinfei.myapplication.listener.DisposeDataListener;
import com.example.jinfei.myapplication.request.RequestParams;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends BaseActivity {

    Button btnGet;
    Button btnPost;
    Button btnClear;
    private TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityController.addActivity(this);
        btnGet = (Button) findViewById(R.id.btn_get);
        btnPost = (Button) findViewById(R.id.btn_post);
        btnClear = (Button) findViewById(R.id.btn_clear);
        tvContent = (TextView) findViewById(R.id.tv_content);

        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestRuntimePermission(new String[]{Manifest.permission.INTERNET}, new PermissionListener() {
                    @Override
                    public void onGranted() {
                        Toast.makeText(MainActivity.this, "所有权限都同意了", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onDenied(List<String> deniedPermissions) {
                        for(String permission :deniedPermissions) {
                            Toast.makeText(MainActivity.this, permission + "被拒绝了", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                coolGet();
            }
        });

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coolPost();
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvContent.setText(getResources().getText(R.string.hello_world));
            }
        });
    }

    private void coolGet() {
        CommonOkHttpClient.get("http://www.qq.com", null, new DisposeDataHandler(new DisposeDataListener() {
            @Override
            public void onSuccess(Object response) {
                tvContent.setText(response.toString());
            }

            @Override
            public void onFailure(Object error) {
                tvContent.setText(error.toString());
            }
        }));

    }

    private void coolPost() {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("mb", "19811230100");
        paramMap.put("pwd", "999999q");
        RequestParams params = new RequestParams(paramMap);
        CommonOkHttpClient.post("http://mail.docmail.cn/auth/login", params, new DisposeDataHandler(new DisposeDataListener() {
            @Override
            public void onSuccess(Object response) {
                tvContent.setText(response.toString());
            }

            @Override
            public void onFailure(Object error) {
                tvContent.setText(error.toString());
            }
        }, ResponseBean.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityController.removeActivity(this);
    }
}
