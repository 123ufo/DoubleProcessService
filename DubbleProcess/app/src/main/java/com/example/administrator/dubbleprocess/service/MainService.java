package com.example.administrator.dubbleprocess.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.administrator.dubbleprocess.service.interfaces.IMyAidlInterface;

/**
 * Created by Administrator on 2016/6/12.
 */
public class MainService extends Service {

    private MyBinder mBinder;
    private MyConn mConn;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("main service");
        this.mBinder = new MyBinder();
        if(null == mConn){
            mConn = new MyConn();
        }
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        this.bindService(new Intent(this,SecondService.class),mConn, Context.BIND_IMPORTANT);
    }


    class MyBinder extends IMyAidlInterface.Stub{

        @Override
        public String getServiceName() throws RemoteException {
            return "MainService";
        }
    }

    class MyConn implements ServiceConnection{

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            System.out.println("mainservice conn secondservice success");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            System.out.println("线程名："+Thread.currentThread().getName());
            Toast.makeText(MainService.this, "Second服务死了", Toast.LENGTH_SHORT).show();
            MainService.this.startService(new Intent(MainService.this,SecondService.class));
            MainService.this.bindService(new Intent(MainService.this,SecondService.class),mConn,Context.BIND_IMPORTANT);
        }
    }
}
