# DoubleProcessService
双服务进程守护，实现后台长期运行杀不死。

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