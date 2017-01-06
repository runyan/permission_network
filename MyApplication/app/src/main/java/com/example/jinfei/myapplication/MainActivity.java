package com.example.jinfei.myapplication;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jinfei.myapplication.client.CommonOkHttpClient;
import com.example.jinfei.myapplication.listener.DisposeDataHandler;
import com.example.jinfei.myapplication.listener.DisposeDataListener;

import java.io.IOException;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
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

    OkHttpClient client = new OkHttpClient();
    String post(String url) throws IOException {
        RequestBody formBody = new FormBody.Builder()
                .add("key", "07dd87c07e42408b87d2642970c71be0")
                .add("info", "明天去上海的航班")
                .add("loc", "北京市中关村")
                .add("userid", "12345678")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    private void coolPost() {
//        Map<String, Object> paramMap = new HashMap<>();
//        paramMap.put("key", "07dd87c07e42408b87d2642970c71be0");
//        paramMap.put("info", "今天天气怎么样");
//        paramMap.put("loc", "北京市中关村");
//        paramMap.put("userid", "12345678");
//        RequestParams params = new RequestParams(paramMap);
//        CommonOkHttpClient.post("http://www.tuling123.com/openapi/api", params, new DisposeDataHandler(new DisposeDataListener() {
//            @Override
//            public void onSuccess(Object response) {
//                tvContent.setText(response.toString());
//            }
//
//            @Override
//            public void onFailure(Object error) {
//                tvContent.setText(error.toString());
//            }
//        }, ResponseBean.class));
//        CommonOkHttpClient.get("http://www.tuling123.com/openapi/api", params, new DisposeDataHandler(new DisposeDataListener() {
//            @Override
//            public void onSuccess(Object response) {
//                tvContent.setText("1");
//            }
//
//            @Override
//            public void onFailure(Object error) {
//                tvContent.setText("2");
//            }
//        }));
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final String responseStr = post("http://www.tuling123.com/openapi/api");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvContent.setText(responseStr);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
