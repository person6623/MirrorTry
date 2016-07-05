package com.mirror.mirrortry.main.allkind.glassdetails.atlas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.toolbox.ImageLoader;
import com.mirror.mirrortry.R;
import com.mirror.mirrortry.base.BaseActivity;
import com.mirror.mirrortry.main.allkind.glassdetails.GlassDetailsBean;
import com.mirror.mirrortry.login.LoginActivity;
import com.mirror.mirrortry.net.NetHelper;
import com.mirror.mirrortry.net.VolleySingleton;
import com.mirror.mirrortry.orderdetails.OrderDetailsActivity;
import com.mirror.mirrortry.tools.CustomVideoView;
import com.mirror.mirrortry.tools.Utils;

import java.util.ArrayList;

/**
 * Created by dllo on 16/6/24.
 */
public class WearTheAtlasActivity extends BaseActivity implements View.OnClickListener {
    private ArrayList<GlassDetailsBean.DataBean.ListBean.DataInfoBean.WearVideoBean> wearVideoBean;
    private WearTheAtlasAdapter adapter;
    private ListView listView;
    private RelativeLayout rlAtlas;
    private ImageView ivAtlasHeadItem;
    private View headView;
    private ArrayList<String> atlasUrl;
    private CustomVideoView videoView;
    private ImageView atlasPlay, atlasClose, atlasBack, atlasFull, ivWearTheAtlas;
    private TextView atlasBuy, tvWearTheAtlas;
    private Uri uri;
    private boolean flag;
    private int screenWidth;
    private int screenHeight;
    private boolean sensor_flag = true;
    private boolean stretch_flag = true;
    private SensorManager sm;
    private OrientationSensorListener listener;
    private Sensor sensor;
    private static int i = 0;
    private int state = 0;//默认状态
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private int horizontal, vertical;
    private SensorManager smOne;
    private Sensor sensorOne;
    private OrientationSensorListenerTwo listenerOne;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 888:
                    int orientation = msg.arg1;
                    if (orientation > 45 && orientation < 135) {

                    } else if (orientation > 135 && orientation < 225) {

                    } else if (orientation > 225 && orientation < 315) {
                        System.out.println("切换成横屏");
                        Log.d("SecActivity", "123456:" + 123456);
                        WearTheAtlasActivity.this.setRequestedOrientation(horizontal);
                        sensor_flag = false;
                        stretch_flag = false;
                    } else if ((orientation > 315 && orientation < 360) || (orientation > 0 && orientation < 45)) {
                        System.out.println("切换成竖屏");
                        Log.d("SecActivity", "789456:" + 789456);
                        WearTheAtlasActivity.this.setRequestedOrientation(vertical);
                        sensor_flag = true;
                        stretch_flag = true;

                    }
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    public int setLayout() {
        return R.layout.activity_wear_the_atlas;
    }


    @Override
    public void initView() {

        //创建一个SharedPreferences来实现数据持久化
        sp = getSharedPreferences("stateOne", MODE_PRIVATE);
        editor = sp.edit();

        //判断当前状态是播放还是暂停状态
        if (sp.getInt("state", -1) != -1) {
            state = sp.getInt("state", -1);
            editor.putInt("state", 0);
            editor.commit();
        }

        listView = findView(R.id.lv_atlas);
        atlasBack = findView(R.id.iv_wear_the_atlas_back);
        atlasBuy = findView(R.id.tv_wear_the_atlas_buy);
        ivWearTheAtlas = findView(R.id.iv_wear_the_atlas);
        tvWearTheAtlas = findView(R.id.tv_wear_the_atlas);
        headView = LayoutInflater.from(this).inflate(R.layout.atlas_head_item, null);
        ivAtlasHeadItem = (ImageView) headView.findViewById(R.id.iv_atlas_head_item);
        videoView = (CustomVideoView) headView.findViewById(R.id.vv_atlas);
        atlasPlay = (ImageView) headView.findViewById(R.id.iv_atlas_play);
        atlasClose = (ImageView) headView.findViewById(R.id.iv_atlas_close);
        atlasFull = (ImageView) headView.findViewById(R.id.iv_atlas_full);
        rlAtlas = (RelativeLayout) headView.findViewById(R.id.rl_atlas);


        //获取屏幕宽高
        screenWidth = Utils.getScreenWidth(this);
        screenHeight = Utils.getScreenHeight(this);

        atlasBack.setOnClickListener(this);
        atlasBuy.setOnClickListener(this);

        //去分割线
        listView.setDividerHeight(0);
        //去滚动条
        listView.setVerticalScrollBarEnabled(false);
        //去头部分割线
        listView.setHeaderDividersEnabled(false);

    }

    @Override
    public void initData() {

        Log.d("WearTheAtlasActivity", "冲偶了");

        adapter = new WearTheAtlasAdapter(this);
        wearVideoBean = new ArrayList<>();
        atlasUrl = new ArrayList<>();
        wearVideoBean = getIntent().getExtras().getParcelableArrayList("wear_video");


        //注册重力感应器  屏幕旋转
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        listener = new OrientationSensorListener(handler);
        sm.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI);

        //根据  旋转之后 点击 符合之后 激活sm
        smOne = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorOne = smOne.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        listenerOne = new OrientationSensorListenerTwo();
        smOne.registerListener(listenerOne, sensorOne, SensorManager.SENSOR_DELAY_UI);


        for (GlassDetailsBean.DataBean.ListBean.DataInfoBean.WearVideoBean videoBean : wearVideoBean) {
            String atlasType = videoBean.getType();
            if (atlasType.equals("9")) {
                ImageLoader imageLoader = VolleySingleton.getInstance().getImageLoader();
                imageLoader.get(videoBean.getData(), imageLoader.getImageListener(ivAtlasHeadItem,
                        R.mipmap.null_state, R.mipmap.null_state));
            } else if (atlasType.equals("8")) {
                //获取视频网址
                uri = Uri.parse(videoBean.getData());

//                Log.d("..000..0.", "uri:" + uri);

            } else {
                atlasUrl.add(videoBean.getData());
            }
        }


        //调用媒体播放器的播放,暂停等功能的点击事件
        videoView.setMediaController(new MediaController(this));
        videoView.setVisibility(View.GONE);
        atlasClose.setVisibility(View.GONE);

        //获取uri
        videoView.setVideoURI(uri);
        //获取焦点
        videoView.requestFocus();


        //播放键的点击事件
        atlasPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlAtlas.setVisibility(View.VISIBLE);
                videoView.setVisibility(View.VISIBLE);
                videoView.start();
                atlasClose.setVisibility(View.VISIBLE);
                atlasPlay.setVisibility(View.GONE);
                ivAtlasHeadItem.setVisibility(View.GONE);
                atlasFull.setVisibility(View.VISIBLE);

                //动态设置控件的宽高
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) rlAtlas.getLayoutParams();
                layoutParams.width = Integer.MAX_VALUE;
                layoutParams.height = 560;
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) videoView.getLayoutParams();
                params.width = Integer.MAX_VALUE;
                layoutParams.height = 560;
                RelativeLayout.LayoutParams imageParams = (RelativeLayout.LayoutParams) ivAtlasHeadItem.getLayoutParams();
                imageParams.width = Integer.MAX_VALUE;
                imageParams.height = 560;

            }
        });

        //为Media Player的播放完成事件绑定事件监听器
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                videoView.start();
            }
        });

        //全屏键的点击事件
        atlasFull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sm.unregisterListener(listener);
                if (stretch_flag) {
                    stretch_flag = false;
                    //切换成横屏
                    WearTheAtlasActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


                } else {
                    stretch_flag = true;
                    //切换成竖屏
                    WearTheAtlasActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                }
            }
        });

        //关闭键的点击事件
        //判断当前是出于横屏还是竖屏 当处于横屏时点击关闭键跳回竖屏模式并且关闭播放
        if (sp.getBoolean("stretch_flag", true) == true) {
            atlasClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WearTheAtlasActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    videoView.stopPlayback();
                    videoView.pause();
                    rlAtlas.setVisibility(View.GONE);
                    videoView.setVisibility(View.GONE);
                    atlasClose.setVisibility(View.GONE);
                    atlasPlay.setVisibility(View.VISIBLE);
                    ivAtlasHeadItem.setVisibility(View.VISIBLE);

                }
            });
        } else {
            atlasClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    videoView.stopPlayback();
                    rlAtlas.setVisibility(View.GONE);
                    videoView.setVisibility(View.GONE);
                    atlasClose.setVisibility(View.GONE);
                    atlasPlay.setVisibility(View.VISIBLE);
                    ivAtlasHeadItem.setVisibility(View.VISIBLE);
                }
            });
        }

        listView.addHeaderView(headView);
        adapter.setAtlasUrl(atlasUrl);
        listView.setAdapter(adapter);


        SharedPreferences sp = getSharedPreferences("isLogin", MODE_PRIVATE);
        flag = sp.getBoolean("login", false);

    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        Log.d("WearTheAtlasActivity", "走了没");

        if (stretch_flag) {

            //竖屏
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(screenHeight,
                    screenWidth);
            videoView.setLayoutParams(params);

        } else {

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(screenHeight,
                    screenWidth);
            videoView.setLayoutParams(params);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //获取到切换屏幕之前播放的节点
        videoView.seekTo(i);
        //判断处于播放还是暂停状态
        if (state == 1) {
            ivAtlasHeadItem.setVisibility(View.GONE);
            rlAtlas.setVisibility(View.VISIBLE);
            videoView.setVisibility(View.VISIBLE);
            //判断当前是横屏还是竖屏
            if (sp.getBoolean("stretch_flag", false) == true) {
                ivWearTheAtlas.setVisibility(View.GONE);
                tvWearTheAtlas.setVisibility(View.GONE);
                atlasBack.setVisibility(View.GONE);
                atlasBuy.setVisibility(View.GONE);
                atlasClose.setVisibility(View.VISIBLE);
                atlasFull.setVisibility(View.VISIBLE);


                //使listView不能滑动
                listView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return true;
                    }
                });
                //动态设置屏幕宽高
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) rlAtlas.getLayoutParams();
//                layoutParams.width = getWindowManager().getDefaultDisplay().getWidth();
//                layoutParams.height = getWindowManager().getDefaultDisplay().getHeight();
                layoutParams.width = Integer.MAX_VALUE;
                layoutParams.height = 990;

                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) videoView.getLayoutParams();
//                layoutParams.width = getWindowManager().getDefaultDisplay().getWidth();
//                layoutParams.height = getWindowManager().getDefaultDisplay().getHeight();
                params.width = Integer.MAX_VALUE;
                layoutParams.height = 990;
            } else {
                ivWearTheAtlas.setVisibility(View.VISIBLE);
                tvWearTheAtlas.setVisibility(View.VISIBLE);
                atlasBuy.setVisibility(View.VISIBLE);
                atlasBack.setVisibility(View.VISIBLE);
                atlasClose.setVisibility(View.VISIBLE);
                atlasFull.setVisibility(View.VISIBLE);


                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) rlAtlas.getLayoutParams();
                layoutParams.width = Integer.MAX_VALUE;
                layoutParams.height = 560;
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) videoView.getLayoutParams();
                params.width = Integer.MAX_VALUE;
                layoutParams.height = 560;

            }
            videoView.start();
            atlasPlay.setVisibility(View.GONE);
        } else {
            videoView.pause();
        }
        //监听当前是播放还是暂停状态
        videoView.setPlayPauseListener(new CustomVideoView.PlayPauseListener() {
            @Override
            public void onPlay() {
                state = 1;
            }

            @Override
            public void onPause() {
                state = 0;
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_wear_the_atlas_back:
                finish();
                break;
            case R.id.tv_wear_the_atlas_buy:
                if (NetHelper.isHaveInternet(this) == true) {
                    if (flag == false) {
                        Intent loginIntent = new Intent(WearTheAtlasActivity.this, LoginActivity.class);
                        loginIntent.putExtra("sign", 1);
                        startActivity(loginIntent);
                        finish();
                    } else {

                        Intent buy = new Intent(this, OrderDetailsActivity.class);
                        startActivity(buy);

                    }
                } else {
                    Toast.makeText(this, "訂單失敗,請檢查網絡", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 重力感应监听者
     */
    public class OrientationSensorListener implements SensorEventListener {
        private static final int _DATA_X = 0;
        private static final int _DATA_Y = 1;
        private static final int _DATA_Z = 2;
        public static final int ORIENTATION_UNKNOWN = -1;

        private Handler rotateHandler;


        public OrientationSensorListener(Handler handler) {
            rotateHandler = handler;
        }

        @Override
        public void onAccuracyChanged(Sensor arg0, int arg1) {

        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (sensor_flag != stretch_flag)  //只有两个不相同才开始监听行为
            {
                float[] values = event.values;
                int orientation = ORIENTATION_UNKNOWN;
                float X = -values[_DATA_X];
                float Y = -values[_DATA_Y];
                float Z = -values[_DATA_Z];
                float magnitude = X * X + Y * Y;
                // Don't trust the angle if the magnitude is small compared to the y value
                if (magnitude * 4 >= Z * Z) {
                    //屏幕旋转时
                    float OneEightyOverPi = 57.29577957855f;
                    float angle = (float) Math.atan2(-Y, X) * OneEightyOverPi;
                    orientation = 90 - (int) Math.round(angle);
                    // normalize to 0 - 359 range
                    while (orientation >= 360) {
                        orientation -= 360;
                    }
                    while (orientation < 0) {
                        orientation += 360;
                    }
                }
                if (rotateHandler != null) {
                    rotateHandler.obtainMessage(888, orientation, 0).sendToTarget();
                }
            }
        }
    }

    public class OrientationSensorListenerTwo implements SensorEventListener {
        private static final int _DATA_X = 0;
        private static final int _DATA_Y = 1;
        private static final int _DATA_Z = 2;

        public static final int ORIENTATION_UNKNOWN = -1;

        @Override
        public void onAccuracyChanged(Sensor arg0, int arg1) {

        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            float[] values = event.values;

            int orientation = ORIENTATION_UNKNOWN;
            float X = -values[_DATA_X];
            float Y = -values[_DATA_Y];
            float Z = -values[_DATA_Z];

            /**
             * 这一段据说是 android源码里面拿出来的计算 屏幕旋转的 不懂 先留着 万一以后懂了呢
             */
            float magnitude = X * X + Y * Y;
            // Don't trust the angle if the magnitude is small compared to the y value
            if (magnitude * 4 >= Z * Z) {
                //屏幕旋转时
                float OneEightyOverPi = 57.29577957855f;
                float angle = (float) Math.atan2(-Y, X) * OneEightyOverPi;
                orientation = 90 - (int) Math.round(angle);
                // normalize to 0 - 359 range
                while (orientation >= 360) {
                    orientation -= 360;
                }
                while (orientation < 0) {
                    orientation += 360;
                }
            }

            if (orientation > 225 && orientation < 315) {  //横屏
                sensor_flag = false;
            } else if ((orientation > 315 && orientation < 360) || (orientation > 0 && orientation < 45)) {  //竖屏
                sensor_flag = true;
            }

            if (stretch_flag == sensor_flag) {  //点击变成横屏  屏幕 也转横屏 激活
                sm.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI);

            }
        }


    }

    //设置view的margin属性
    public static void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        //得到当前播放的节点
        i = videoView.getCurrentPosition();
        //保存当前的状态
        editor.putInt("state", state);
        editor.commit();
        //设置判断横竖屏的标识 并且保存到sp里
        stretch_flag = !stretch_flag;
        editor.putBoolean("stretch_flag", stretch_flag);
        editor.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //销毁videoView的缓存
        videoView.destroyDrawingCache();
    }
}
