package cn.itcast.mobliesafe.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.File;

public class VersionUtils {

    /*
    * 获取版本号
    *
    * @param context
    * @return 返回版本号
    * */
    public static String getVersion(Context context){
        //
        PackageManager manager = context.getPackageManager();
        try {
            //
            //
            PackageInfo packageInfo = manager.getPackageInfo(context.getPackageName(),0);
            return packageInfo.versionName;
        }catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
            return "";
        }
    }

    /*
     * 安装新版本
     * @param activity
     * */
    public static void installAPK(Activity activity){

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // 添加默认分类
        intent.addCategory("android.intent.category.DEFAULT");
        // 设置数据和类型 在文件中
        String  downPath= Environment.getExternalStorageDirectory() + "/Download/mobilesafe20.apk";
        Log.d("<<<<安装APK路径>>>",downPath);
        //intent.setDataAndType(Uri.fromFile(new File(downPath)),  "application/vnd.android.package-archive");

        Uri data= null;
        File file=new File(downPath);
        if (Build.VERSION.SDK_INT < 24) {
            data = Uri.fromFile(file);
        } else {
            data = FileProvider.getUriForFile(activity, "cn.itcast.mobliesafe.fileprovider", file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }

        Log.d("<<<安装apk的uri路径>>>",data.getHost()+data.getPath());
        intent.setDataAndType(data,"application/vnd.android.package-archive");

        // 如果开启的activity 退出的时候 会回调当前activity的onActivityResult
        activity.startActivityForResult(intent, 0);

        /*Intent intent = new Intent("android.intent.action.VIEW");
        //添加默认分类
        intent.addCategory("android.intent.category.DEFAULT");
        //设置数据和类型 在文件中
        //intent.setDataAndType(Uri.fromFile(new File("/mnt/sdcard/mobilesafe2.0.apk")),"application/vnd.android.package-archive");
        String  downPath= Environment.getExternalStorageDirectory() + "/Download/mobilesafe20.apk";
        Log.d("<<<<安装APK路径>>>",downPath);
        //intent.setDataAndType(Uri.fromFile(new File(downPath)),  "application/vnd.android.package-archive");

        Uri data= null;
        File file=new File(downPath);
        if (Build.VERSION.SDK_INT < 24) {
            data = Uri.fromFile(file);
        } else {
            data = FileProvider.getUriForFile(activity, "cn.itcast.mobliesafe.fileprovider", file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.setDataAndType(data,"application/vnd.android.package-archive");
        // 如果开启的activity 退出的时候 会回调当前activity的onActivityResult
        activity.startActivityForResult(intent, 0);*/
    }




}
