package com.example.administrator.dubbleprocess.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.widget.Toast;

import com.example.administrator.dubbleprocess.service.interfaces.IMyAidlInterface;

public class SecondService extends Service {

    private MyBinder mBinder;
    private MyConn mConn;

    public SecondService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
//        throw new UnsupportedOperationException("Not yet implemented");
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("second service");
        this.mBinder = new MyBinder();
        if(null == mConn){
            mConn = new MyConn();
        }
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        this.bindService(new Intent(this,MainService.class),mConn, Context.BIND_IMPORTANT);
    }

    class MyBinder extends IMyAidlInterface.Stub {

        @Override
        public String getServiceName() throws RemoteException {
            return "second service";
        }
    }

    class MyConn implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            System.out.println("secondservice conn mainservice success");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Toast.makeText(SecondService.this, "Main服务死了", Toast.LENGTH_SHORT).show();
            SecondService.this.startService(new Intent(SecondService.this,MainService.class));
            SecondService.this.bindService(new Intent(SecondService.this,MainService.class),mConn,Context.BIND_IMPORTANT);
        }
    }
}
