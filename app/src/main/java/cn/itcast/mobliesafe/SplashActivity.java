package cn.itcast.mobliesafe;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import cn.itcast.mobliesafe.utils.VersionUpdateUtils;
import cn.itcast.mobliesafe.utils.VersionUtils;

/**
 * 欢迎页面
 *
 * @author admin
 */
public class SplashActivity extends Activity {

    private TextView mVersionTV;///** 应用版本号 */
    private String mVersion;///** 本地版本号 */

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置没有标题栏 在加载布局之前调用
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);

		initView();
		initData();

	}

	/** 初始化控件 */
	private void initView() {
        mVersionTV = (TextView)this.findViewById(R.id.tv_splash_version);
	}
	private void initData(){
        mVersion = VersionUtils.getVersion(getApplicationContext());
        //申请权限
        if (ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }else if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }else {
            startDownload();
        }
        /*VersionUpdateUtils versionUpdateUtilsNew=new VersionUpdateUtils(mVersion,this);
        versionUpdateUtilsNew.getDownloadVer("http://192.168.1.100:8080/updateinfo.html");
        mVersionTV.setText("版本号 " + mVersion);*/
	}

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //处理权限申请
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startDownload();
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                    EnterHome();
                }
                break;
            default:
        }
    }

    private void startDownload(){
        VersionUpdateUtils versionUpdateUtilsNew=new VersionUpdateUtils(mVersion,this);
        versionUpdateUtilsNew.getDownloadVer("http://192.168.1.101:8080/updateinfo.html");
        mVersionTV.setText("版本号 " + mVersion);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0){
            finish();
        }
            //EnterHome();
    }
    private void EnterHome(){
	    Intent intent = new Intent(SplashActivity.this,HomeActivity.class);
	    startActivity(intent);
	    this.finish();
    }
}

