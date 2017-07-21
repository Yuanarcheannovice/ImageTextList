package com.xz.list.app;

import android.os.Environment;

import com.xz.list.utils.DateTimeUtil;

import java.io.File;
import java.util.Date;


/**
 * App的文件处理
 * <p>
 * 所有的安卓开发者都应该按照这个方向走，不要再随意地再SD卡里建立各式各样的文件夹， 而应该将所有应用数据都在/Android/data/内进行读写
 * 。统一的规范并不违背开源的初衷，
 * 甚至有利于整个生态圈的有序发展。
 */
public class AppPathManager {

    public static String appFolderName = "";//app文件的名字

    /**
     * 初始话文件管理类
     */
    public static void initPathManager(String str) {
        appFolderName = str;
        //设置app默认文件路径
        AppPathManager.setAppPath();
    }

    /**
     * 方法说明:返回多个sd卡的该应用私有数据区的files目录
     * 方法名称:getExternalRootFilesCachePath
     * return：/storage/sdcard0 or sdcard1/Android/data/<包名>/files
     * /storage/emulated/0/Android/data/com.xz.list/files/mounted
     */
    public static StringBuffer getExternalRootFilesCachePath() {
        if (AppApplication.context.getExternalCacheDir() == null) {
            return new StringBuffer(AppApplication.context.getCacheDir()
                    .getAbsolutePath()).append("/");
        }
        return new StringBuffer(AppApplication.context.getExternalFilesDir(
                Environment.MEDIA_MOUNTED).getAbsolutePath()).append("/");
    }

    /**
     * 方法说明:返回多个sd卡下该应用私有数据库的缓存目录
     * 方法名称:getExternalRootCachePath
     * return：/storage/sdcard0 or sdcard1/Android/data/<包名>/caches
     * 返回值:StringBuffer
     * /storage/emulated/0/Android/data/com.xz.list/cache
     */
    public static StringBuffer getExternalRootCachePath() {
        if (AppApplication.context.getExternalCacheDir() == null) {
            return new StringBuffer(AppApplication.context.getCacheDir()
                    .getAbsolutePath()).append("/");
        }
        return new StringBuffer(AppApplication.context.getExternalCacheDir()
                .getAbsolutePath()).append("/");
    }

    /**
     * 设置app的初始目录
     */
    public static void setAppPath() {
        File fileAppPath = null;
        try {
            fileAppPath = new File(getExternalRootFilesCachePath().toString());
            if (!fileAppPath.exists()) {
                fileAppPath.mkdirs();
            }
            fileAppPath = new File(getExternalRootCachePath().toString());
            if (!fileAppPath.exists()) {
                fileAppPath.mkdirs();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * return
     * 方法说明:清除缓存
     * 方法名称:ManualClearCache
     * 返回值:boolean
     */
    public static void ManualClearCache() {
        File file = new File(getExternalRootCachePath().toString());
        DeleteFile(file);
    }

    /**
     * 方法说明:清除appfiles
     * 方法名称:ManualClearFiles
     * 返回值:void
     */
    public static void ManualClearFiles() {
        File file = new File(getExternalRootFilesCachePath().toString());
        DeleteFile(file);
    }

    /**
     * 方法说明:递归删除文件和文件夹
     * 方法名称:DeleteFile
     * 返回值:void
     */
    public static void DeleteFile(File file) {
        if (file.exists() == false) {
            return;
        } else {
            if (file.isFile()) {
                file.delete();
                return;
            }
            if (file.isDirectory()) {
                File[] childFile = file.listFiles();
                if (childFile == null || childFile.length == 0) {
                    file.delete();
                    return;
                }
                for (File f : childFile) {
                    DeleteFile(f);
                }
                file.delete();
            }
        }
    }


    /**
     * 方法说明：得到主目录文件的路径
     * 方法名称：getAPPPath
     * 返回值：void
     */
    public static StringBuffer getAPPPath() {
        String path = "";
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            File sd = Environment.getExternalStorageDirectory();
            path = sd.getPath() + "/" + appFolderName + "/";
        }
        return new StringBuffer(path);
    }

    /**
     * 判断文件夹是否存在，
     * 不存在则创建
     * <p>
     */
    public static Boolean ifFolderExit(String filePath) {
        File fileAppPath = null;
        try {
            fileAppPath = new File(filePath);
            if (!fileAppPath.exists()) {
                fileAppPath.mkdirs();
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    //得到图片名
    public static String getImageName() {
        return DateTimeUtil.formatDate(new Date(), DateTimeUtil.GROUP_BY_EACH_DAYSM) + ".jpg";
    }


    public static String getSaveImageUrl() {
        if (ifFolderExit(getExternalRootCachePath().append("/image").toString()))
            return getExternalRootCachePath().append("/image").toString();
        else
            return null;
    }

}
