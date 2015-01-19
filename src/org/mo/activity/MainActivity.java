package org.mo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.Dao;
import org.mo.entity.Weather;
import org.mo.entity.WeatherInfo;
import org.mo.ormlite.R;
import org.mo.ormlite.dao.DatabaseHelper;
import org.mo.ormlite.entity.User;
import org.mo.ormlite.service.UserService;
import org.mo.volley.GsonRequest;
import org.mo.volley.XMLRequest;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener {
    private EditText username;
    private EditText password;
    private Button submit;
    private TextView mTextView;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    protected void initUI() {
        username = (EditText) findViewById(R.id.main_et_username);
        password = (EditText) findViewById(R.id.main_et_password);
        submit = (Button) findViewById(R.id.main_btn_submit);
        mTextView = (TextView) findViewById(R.id.textView);
        UserService userService = new UserService(this);
//        List<User> all = userService.getAllByLimit(1, 2);
//        List<User> all = userService.getAllUserOnlyUsernamePassword();
//        String s = "";
//        for (User user : all) {
//            s += user.toString();
//        }
//        mTextView.setText(s + "大小:" + userService.countAll());
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        UserService userService = new UserService(this);
        switch (v.getId()) {
            case R.id.main_btn_submit:
//                saveUser(userService);
//                User userById = userService.getUserById(2);
//                mTextView.setText(userById.toString());
                //TODO 测试http
                RequestQueue mQueue = Volley.newRequestQueue(this);
//                StringRequest request = new StringRequest("http://www.baidu.com",
//                        new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                Log.e("TAG", response);
//                            }
//                        },
//                        new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                Log.e("TAG", error.getMessage(), error);
//                            }
//                        });
                //todo
//                XMLRequest request = new XMLRequest("http://flash.weather.com.cn/wmaps/xml/china.xml",
//                        new Response.Listener<XmlPullParser>() {
//                            @Override
//                            public void onResponse(XmlPullParser response) {
//                                try {
//                                    int eventType = response.getEventType();
//                                    while (eventType != XmlPullParser.END_DOCUMENT) {
//                                        switch (eventType) {
//                                            case XmlPullParser.START_TAG:
//                                                String nodeName = response.getName();
//                                                if ("city".equals(nodeName)) {
//                                                    String pName = response.getAttributeValue(0);
//                                                    Log.d("TAG", "pName is " + pName);
//                                                }
//                                                break;
//                                        }
//                                        eventType = response.next();
//                                    }
//                                } catch (XmlPullParserException e) {
//                                    e.printStackTrace();
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        },
//                        new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                Log.e("TAG", error.getMessage(), error);
//                            }
//                        });
                GsonRequest<Weather> gsonRequest = new GsonRequest<Weather>(
                        "http://www.weather.com.cn/data/sk/101010100.html", Weather.class,
                        new Response.Listener<Weather>() {
                            @Override
                            public void onResponse(Weather weather) {
                                WeatherInfo weatherInfo = weather.getWeatherinfo();
                                Log.d("TAG", "city is " + weatherInfo.getCity());
                                Log.d("TAG", "temp is " + weatherInfo.getTemp());
                                Log.d("TAG", "time is " + weatherInfo.getTime());
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("TAG", error.getMessage(), error);
                    }
                });
                mQueue.add(gsonRequest);
                break;
        }
    }

    private void saveUser(UserService userService) {
        int result = userService.saveUser(getUserData());
        switch (result) {
            case -1:
                showToast("保存出错");
                break;
            case 1:
                showToast(getUserData().getUsername() + ",保存成功");
                break;
            case 0:
                showToast(getUserData().getUsername() + ",已存在");
                break;
        }
    }

    /**
     * 获取页面的值
     */
    private User getUserData() {
        User mUser = new User();
        mUser.setUsername(username.getText().toString());
        mUser.setPassword(password.getText().toString());
        //设置参数
        Calendar calendar = Calendar.getInstance();
        mUser.setLoginTime(calendar.getTime().getDate() + "");
        mUser.setCreateDate(calendar.getTime().getTime() + "");
        mUser.setUpdateDate(calendar.getTime().getYear() + "");
        return mUser;
    }

    private void showToast(String msg) {
        Toast.makeText(getApplication(), msg, Toast.LENGTH_SHORT).show();
    }

}
