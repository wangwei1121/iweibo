package com.snail.iweibo.util;

import android.os.Build;
import android.util.Log;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by wang.weib on 2016/5/5.
 */
public class ThreadPoolUtil {

    private ThreadPoolExecutor executor = null;

    private ThreadPoolUtil(){
        executor = (ThreadPoolExecutor)Executors.newFixedThreadPool(getThreadPoolSize());
    }

    public static ThreadPoolUtil getInstance(){
        return SingletonHolder.instance;
    }

    public void execute(Runnable runnable){
        executor.execute(runnable);
    }

    public ThreadPoolExecutor getThreadPoolExecutor(){
        return (ThreadPoolExecutor)this.executor;
    }

    public int getThreadPoolSize(){
        return getNumberOfJVMCPU() + 1;
    }

    public int getNumberOfCPUCores() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1) {
            return 1;
        }
        int cores = 1;
        try {
            cores = new File("/sys/devices/system/cpu/").listFiles(CPU_FILTER).length;
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return cores;
    }

    public int getNumberOfJVMCPU(){
        return Runtime.getRuntime().availableProcessors();
    }

    private static final FileFilter CPU_FILTER = new FileFilter() {
        @Override
        public boolean accept(File cupFile) {
            String path = cupFile.getName();
            if (path.startsWith("cpu")) {
                for (int i = 3; i < path.length(); i++) {
                    if (path.charAt(i) < '0' || path.charAt(i) > '9') {
                        return false;
                    }
                }
                return true;
            }
            return false;
        }
    };

    private static class SingletonHolder{
        private static ThreadPoolUtil instance = new ThreadPoolUtil();
    }
}
