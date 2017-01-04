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
                        Toast.makeText(MainActivity.this, getResources().getString(R.string.permission_granted), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onDenied(List<String> deniedPermissions) {
                        StringBuilder sb = new StringBuilder();
                        for(String permission :deniedPermissions) {
                            sb.append(permission).append(getResources().getString(R.string.permission_denied));
                            Toast.makeText(MainActivity.this, sb.toString(), Toast.LENGTH_SHORT).show();
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
                tvContent.setText(getResources().getString(R.string.hello_world));
            }
        });
    }

    private void coolGet() {
        CommonOkHttpClient.get(getResources().getString(R.string.test_get_url), null, new DisposeDataHandler(new DisposeDataListener() {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityController.removeActivity(this);
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

}
