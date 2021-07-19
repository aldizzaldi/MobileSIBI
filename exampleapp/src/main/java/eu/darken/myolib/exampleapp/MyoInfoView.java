/*
 * Android Myo library by darken
 * Matthias Urhahn (matthias.urhahn@rwth-aachen.de)
 * mHealth - Uniklinik RWTH-Aachen.
 */
package eu.darken.myolib.exampleapp;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

import eu.darken.myolib.Myo;
import eu.darken.myolib.MyoCmds;
import eu.darken.myolib.msgs.MyoMsg;
import eu.darken.myolib.processor.classifier.ClassifierEvent;
import eu.darken.myolib.processor.classifier.ClassifierProcessor;
import eu.darken.myolib.processor.emg.EmgData;
import eu.darken.myolib.processor.emg.EmgProcessor;
import eu.darken.myolib.processor.imu.ImuData;
import eu.darken.myolib.processor.imu.ImuProcessor;
import eu.darken.myolib.processor.imu.MotionEvent;
import eu.darken.myolib.processor.imu.MotionProcessor;

import static eu.darken.myolib.exampleapp.MenuActivity.myoConnect;


/**
 * Debug view that takes a {@link Myo} object and displays it's data.
 */
public class MyoInfoView extends RelativeLayout implements
        Myo.BatteryCallback,
        Myo.FirmwareCallback,
        EmgProcessor.EmgDataListener,
        ImuProcessor.ImuDataListener,
        ClassifierProcessor.ClassifierEventListener,
        Myo.ReadDeviceNameCallback, MotionProcessor.MotionEventListener {

    private Myo mMyo;
    private TextView mTitle, mBatteryLevel, mFirmware, mSerialNumber, mAddress;
    private TextView mEmgData, mOrientationData, mGyroData, mAcclData, mEulerData;
    private Button buttonNext, buttonlast;
    private boolean mAttached = false;
    private EmgProcessor mEmgProcessor;
    private ImuProcessor mImuProcessor;
    private ClassifierProcessor mClassifierProcessor;
    private MotionProcessor mMotionProcessor;
    private String emgSensor, orientSensor, accelSensor, gyroSensor;
    double accelero[];
    double gyro[];
    double orient[];
    double euler[];
    float acceleroF[], acclX, acclY, acclZ;
    float gyroF[], gyroX, gyroY, gyroZ;
    float orientF[],  orientW, orientX, orientY, orientZ;
    float eulerF[], eulerX, eulerY, eulerZ;

    static ArrayList<Float> acclXTemp = new ArrayList<Float>();
    static ArrayList<Float> acclYTemp = new ArrayList<Float>();
    static ArrayList<Float> acclZTemp = new ArrayList<Float>();
    static ArrayList<Float> gyroXTemp = new ArrayList<Float>();
    static ArrayList<Float> gyroYTemp = new ArrayList<Float>();
    static ArrayList<Float> gyroZTemp = new ArrayList<Float>();
    static ArrayList<Float> orientWTemp = new ArrayList<Float>();
    static ArrayList<Float> orientXTemp = new ArrayList<Float>();
    static ArrayList<Float> orientYTemp = new ArrayList<Float>();
    static ArrayList<Float> orientZTemp = new ArrayList<Float>();
    static  ArrayList<Float> eulerXTemp = new ArrayList<Float>();
    static  ArrayList<Float> eulerYTemp = new ArrayList<Float>();
    static  ArrayList<Float> eulerZTemp = new ArrayList<Float>();
    static  ArrayList<Float> emgPod1 = new ArrayList<Float>();
    static  ArrayList<Float> emgPod2 = new ArrayList<Float>();
    static ArrayList<Float> emgPod3 = new ArrayList<Float>();
    static ArrayList<Float> emgPod4 = new ArrayList<Float>();
    static ArrayList<Float> emgPod5 = new ArrayList<Float>();
    static ArrayList<Float> emgPod6 = new ArrayList<Float>();
    static ArrayList<Float> emgPod7 = new ArrayList<Float>();
    static ArrayList<Float> emgPod8 = new ArrayList<Float>();

    public static String dataSensor;
    public static String dataSensorFloat;
    public static DecimalFormat df = new DecimalFormat("#.#######");
//    public static String dataSensor = "-0.301698;0.8918208;0.1591897;-0.2724609;0.9174805;0.1772461;0.008556869;0.04200686;0.02108614;0.09250335;0.2049558;0.1452107;0.09732257;0.1262519;0.1017085;0.1638137;0.2317657;0.1737301;-8.797194;-37.60842;-25.50255;8.625;-19.5625;-9.5;10536.67;2906.203;1566.028;102.6483;53.90921;39.57307;0.04276961;0.03605592;0.08760771;0.05473256;0.04358805;0.142382;0.1062697;0.1044598;-0.9631236;-0.1169571;0.06939697;0.1221924;-0.9765625;-0.02209473;0.01280289;0.005352004;0.0005351041;0.01864873;0.1131499;0.07315739;0.02313232;0.1365604;0.08804137;0.03276688;0.2879466;0.01234491;0.1433225;0.03836885;0.6957949;0.01044037;-0.2339806;0.1949974;2.886639;-0.2091318;0.1248213;3.066977;0.01282092;0.05920041;0.07583552;0.1132295;0.2433113;0.2753825;0.06015843;0.08454358;0.01084394;0.08625693;0.1357812;0.008783103;-0.7755102;-0.5714286;-1.632653;-1.142857;-0.244898;-0.877551;-0.8979592;-0.5918368;-1;1;1;-2;-2;-1;-1;-1;10.34439;141.5;150.9039;143.3333;113.4804;19.27636;45.2602;19.4966;3.216269;11.89538;12.2843;11.97219;10.65272;4.390485;6.727571;4.415495;0.4145376;0.6430464;0.127632;0.4733504;0.2716084;0.3103558;0.4523492;2.069908;1.131055;2.031055;0.2351499;1.349923;0.6436598;0.7689152;1.270663;9.653098";

    static boolean record = false;

    public MyoInfoView(Context context) {
        super(context);
    }

    public MyoInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyoInfoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MyoInfoView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        mTitle = findViewById(R.id.tv_title);
        mBatteryLevel = findViewById(R.id.tv_batterylevel);
        mFirmware = findViewById(R.id.tv_firmware);
        mSerialNumber = findViewById(R.id.tv_serialnumber);
        mAddress = findViewById(R.id.tv_address);
        mEmgData = findViewById(R.id.tv_emg);
        mGyroData = findViewById(R.id.tv_gyro);
        mAcclData = findViewById(R.id.tv_accl);
        mEulerData = findViewById(R.id.tv_euler);
        buttonNext = findViewById(R.id.next);
//        buttonlast = findViewById(R.id.last);
        mOrientationData = findViewById(R.id.tv_orientation);

        super.onFinishInflate();
    }


    @Override
    protected void onAttachedToWindow() {
        if (isInEditMode())
            return;
        mAttached = true;
        post(new Runnable() {
            @Override
            public void run() {
                if (mAttached) {
                    if (mMyo != null && mMyo.isRunning()) {
                        mMyo.readDeviceName(MyoInfoView.this);
                        mAddress.setText(mMyo.getBluetoothDevice().getAddress());
                        mMyo.readBatteryLevel(MyoInfoView.this);
                        mMyo.readFirmware(MyoInfoView.this);
                    }
                    postDelayed(this, 500);
                }
            }
        });
        mEmgProcessor = new EmgProcessor();
        mEmgProcessor.addListener(this);
        mMyo.addProcessor(mEmgProcessor);
        mImuProcessor = new ImuProcessor();
        mImuProcessor.addListener(this);
        mMyo.addProcessor(mImuProcessor);
        mClassifierProcessor = new ClassifierProcessor();
        mClassifierProcessor.addListener(this);
        mMyo.addProcessor(mClassifierProcessor);
        mMotionProcessor = new MotionProcessor();
        mMotionProcessor.addListener(this);
        mMyo.addProcessor(mMotionProcessor);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mMyo.writeVibrate(MyoCmds.VibrateType.SHORT, null);
            }
        });
        mBatteryLevel.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mMyo.writeDeepSleep(null);
                return true;
            }
        });
        mTitle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext())
                        .setTitle("Rename");
                Context dialogContext = dialogBuilder.getContext();
                final EditText editText = new EditText(dialogContext);
                editText.setText(mMyo.getDeviceName());
                dialogBuilder.setPositiveButton("Change", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mMyo.writeDeviceName(editText.getText().toString(), null);
                    }
                });
                dialogBuilder.setView(editText).show();
            }
        });

        buttonNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MenuBelajarActivity.class);
                myoConnect = true;
                getContext().startActivity(intent);

//                record();
            }
        });

//        buttonlast.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                record();
//            }
//        });
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        if (isInEditMode())
            return;
        mAttached = false;
        mMyo.removeProcessor(mEmgProcessor);
        mMyo.removeProcessor(mImuProcessor);
        mMyo.removeProcessor(mClassifierProcessor);
        super.onDetachedFromWindow();
    }

    public Myo getMyo() {
        return mMyo;
    }

    public void setMyo(Myo myo) {
        mMyo = myo;
    }

    @Override
    public void onBatteryLevelRead(Myo myo, MyoMsg msg, final int batteryLevel) {
        if (getHandler() != null)
            getHandler().post(new Runnable() {
                @Override
                public void run() {
                    mBatteryLevel.setText("Battery: " + batteryLevel + "%");
                }
            });
    }

    @Override
    public void onFirmwareRead(Myo myo, MyoMsg msg, final String version) {
        if (getHandler() != null)
            getHandler().post(new Runnable() {
                @Override
                public void run() {
                    mFirmware.setText("Firmware: " + version);
                }
            });
    }

    private long mLastEmgUpdate = 0;

    @Override
    public void onNewEmgData(final EmgData emgData) {
        if (System.currentTimeMillis() - mLastEmgUpdate > 500) {
            if (getHandler() != null)
                getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        byte emgSensor1 = emgData.getData()[0];
                        byte emgSensor2 = emgData.getData()[1];
                        byte emgSensor3 = emgData.getData()[2];
                        byte emgSensor4 = emgData.getData()[3];
                        byte emgSensor5 = emgData.getData()[4];
                        byte emgSensor6 = emgData.getData()[5];
                        byte emgSensor7 = emgData.getData()[6];
                        byte emgSensor8 = emgData.getData()[7];
                        mEmgData.setText("Emg:\n" + emgData.toString() + "\n" + (mEmgProcessor.getPacketCounter() * 2) + " EMG/s");
                        Log.d("emg", "Emg:\n" + emgData.toString() + "\n" + (mEmgProcessor.getPacketCounter() * 2) + " EMG/s");

                        if (record){
                            emgPod1.add((float) emgSensor1);
                            emgPod2.add((float) emgSensor2);
                            emgPod3.add((float) emgSensor3);
                            emgPod4.add((float) emgSensor4);
                            emgPod5.add((float) emgSensor5);
                            emgPod6.add((float) emgSensor6);
                            emgPod7.add((float) emgSensor7);
                            emgPod8.add((float) emgSensor8);
                        }
                    }
                });
            mLastEmgUpdate = System.currentTimeMillis();
        }
    }

    private long mLastImuUpdate = 0;

    @Override
    public void onNewImuData(final ImuData imuData) {
        if (System.currentTimeMillis() - mLastImuUpdate > 500) {
            if (getHandler() != null)
                getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        acceleroF = new float[3];
                        gyroF = new float[3];
                        orientF = new float[4];

                        accelero = imuData.getAccelerometerData();
                        for (int i=0; i<imuData.getAccelerometerData().length; i++){
                            acceleroF[i] = (float) accelero[i];
                        }
                        acclX = acceleroF[0];
                        acclY = acceleroF[1];
                        acclZ = acceleroF[2];

                        gyro =imuData.getGyroData();
                        for (int i=0; i<imuData.getAccelerometerData().length; i++){
                            gyroF[i] = (float) gyro[i];
                        }
                        gyroX = gyroF[0];
                        gyroY = gyroF[1];
                        gyroZ = gyroF[2];

                        orient = imuData.getOrientationData();
                        for (int i=0; i<imuData.getOrientationData().length; i++){
                            orientF[i] = (float) orient[i];
                        }
                        orientW = orientF[0];
                        orientX = orientF[1];
                        orientY = orientF[2];
                        orientZ = orientF[3];

                        Log.d("orient nich", "w = " + orientW + ", x = "+ orientX + ", y = " + orientY + ", z = " + orientZ + "");
                        eulerSensor();

                        orientSensor = ImuData.format(imuData.getAccelerometerData()) + "\n" + mImuProcessor.getPacketCounter();
                        mOrientationData.setText("Orient: " + ImuData.format(imuData.getOrientationData()) + "\n" + mImuProcessor.getPacketCounter() + " IMU/s");
                        mAcclData.setText("Accel: " + ImuData.format(imuData.getAccelerometerData()) + "\n" + mImuProcessor.getPacketCounter() + " IMU/s");
                        mGyroData.setText("Gyro: " + ImuData.format(imuData.getGyroData()) + "\n" + mImuProcessor.getPacketCounter() + " IMU/s");
                        mEulerData.setText("Euler: " + ImuData.format(euler) + "\n" + mImuProcessor.getPacketCounter() + " IMU/s");
                        Log.d("orient", "Orient: " + ImuData.format(imuData.getOrientationData()) + "\n" + mImuProcessor.getPacketCounter() + " IMU/s");
                        Log.d("accl", "Accel: " + ImuData.format(imuData.getAccelerometerData()) + "\n" + mImuProcessor.getPacketCounter() + " IMU/s");
                        Log.d("gyro", "Gyro: " + ImuData.format(imuData.getGyroData()) + "\n" + mImuProcessor.getPacketCounter() + " IMU/s");
                        Log.d("euler", "Euler: " + ImuData.format(euler) + "\n" + mImuProcessor.getPacketCounter() + " IMU/s");

                        if(record){
                            acclXTemp.add(acclX);
                            Log.d("acclwe", acclX + "/" + imuData.getAccelerometerData());
                            acclYTemp.add(acclY);
                            acclZTemp.add(acclZ);
                            gyroXTemp.add(gyroX);
                            gyroYTemp.add(gyroY);
                            gyroZTemp.add(gyroZ);
                            orientWTemp.add(orientW);
                            orientXTemp.add(orientX);
                            orientYTemp.add(orientY);
                            orientZTemp.add(orientZ);
                            eulerXTemp.add(eulerX);
                            eulerYTemp.add(eulerY);
                            eulerZTemp.add(eulerZ);
                        }
                    }
                });
            mLastImuUpdate = System.currentTimeMillis();
        }
    }

    @Override
    public void onClassifierEvent(ClassifierEvent classifierEvent) {

    }

    @Override
    public void onMotionEvent(MotionEvent motionEvent) {

    }

    @Override
    public void onDeviceNameRead(Myo myo, MyoMsg msg, final String deviceName) {
        if (getHandler() != null)
            getHandler().post(new Runnable() {
                @Override
                public void run() {
                    mTitle.setText(deviceName);
                }
            });
    }

    public void eulerSensor(){
        eulerF = new float[3];
        euler = new double[3];
        eulerX = (float) Math.atan2(2.0 * (orientW * orientX + orientY * orientZ), 1.0 - 2.0 * (orientX * orientX + orientY * orientY));
        eulerY = (float) Math.asin(Math.max(-1.0, Math.min(1.0, 2.0 * (orientW * orientY - orientZ * orientX))));
        eulerZ = (float) Math.atan2(2.0 * (orientW * orientZ + orientX * orientY), 1.0 - 2.0 * (orientY * orientY + orientZ * orientZ));

        eulerF[0] = eulerX;
        eulerF[1] = eulerY;
        eulerF[2] = eulerZ;
        euler[0] = eulerX;
        euler[1] = eulerY;
        euler[2] = eulerZ;
    }

    public static float CalculateMean(ArrayList<Float> values){
        int i;
        float mean = (float) 0.0;
        for (i = 0; i < values.size(); i++) {
            mean += values.get(i);
        }
        return mean/values.size();
    }

    public static float CalculateMedian(ArrayList<Float> values){
        if(values.size() == 0) return 0;

        Collections.sort(values);

        float middle = values.size()/2;
        if (values.size()%2 == 0) {
            middle = (values.get(values.size()/2) + values.get(values.size()/2 - 1))/2;
        } else {
            middle = values.get(values.size() / 2);
        }
        return middle;
    }

    public static float CalculateVariance(ArrayList<Float> values){
        int i;
        float mean = CalculateMean(values);
        float variance = (float) 0.0;
        for (i = 0; i < values.size(); i++) {
            variance +=  Math.pow(values.get(i) - mean, 2);
        }
        variance = variance / (values.size() - 1);
        return variance;
    }

    public static float CalculateDeviance(ArrayList<Float> values){
        return (float) Math.sqrt(CalculateVariance(values));
    }

    public static float CalculateSkewness(ArrayList<Float> values){
        int i ;
        int size = 0;
        float skewness = (float) 0.0;
        float mean = CalculateMean(values);
        float deviance = CalculateDeviance(values);

        for (i = 0; i < values.size(); i++) {
            float overDeviance;
            if(deviance == 0)
                overDeviance = 0;
            else
                overDeviance = ((values.get(i) - mean) / deviance);

            skewness = (float) Math.pow(overDeviance,3);
            size++;
        }
        skewness = skewness / size;

        return skewness;
    }

    public static float CalculateKurtosis(ArrayList<Float> values){
        int i;
        int size = 0;
        float kurtosis = (float) 0.0;
        float mean = CalculateMean(values);
        float deviance = CalculateDeviance(values);

        for (i = 0; i < values.size(); i++) {
            float overDeviance;
            if(deviance == 0)
                overDeviance = 0;
            else
                overDeviance = ((values.get(i) - mean) / deviance);

            kurtosis = (float) Math.pow(overDeviance,4);
            size++;
        }
        kurtosis = kurtosis / size;

        return kurtosis;
    }

    public static void clear(){
        acclXTemp.clear();
        acclYTemp.clear();
        acclZTemp.clear();
        gyroXTemp.clear();
        gyroYTemp.clear();
        gyroZTemp.clear();
        orientWTemp.clear();
        orientXTemp.clear();
        orientYTemp.clear();
        orientZTemp.clear();
        eulerXTemp.clear();
        eulerYTemp.clear();
        eulerZTemp.clear();

    }

    public static void record(){
        clear();
        record = true;

        new Handler().postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
                record = false;
                calculateData();
            }
        },3000);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void calculateData(){
        float meanAcclX = CalculateMean(acclXTemp);
        float meanAcclY = CalculateMean(acclYTemp);
        float meanAcclZ = CalculateMean(acclZTemp);
        float meanGyroX = CalculateMean(gyroXTemp);
        float meanGyroY = CalculateMean(gyroYTemp);
        float meanGyroZ = CalculateMean(gyroZTemp);
        float meanOrientW = CalculateMean(orientWTemp);
        float meanOrientX = CalculateMean(orientXTemp);
        float meanOrientY = CalculateMean(orientYTemp);
        float meanOrientZ = CalculateMean(orientZTemp);
        float meanEulerX = CalculateMean(eulerXTemp);
        float meanEulerY = CalculateMean(eulerYTemp);
        float meanEulerZ = CalculateMean(eulerZTemp);
        float meanEMG1 = CalculateMean(emgPod1);
        float meanEMG2 = CalculateMean(emgPod2);
        float meanEMG3 = CalculateMean(emgPod3);
        float meanEMG4 = CalculateMean(emgPod4);
        float meanEMG5 = CalculateMean(emgPod5);
        float meanEMG6 = CalculateMean(emgPod6);
        float meanEMG7 = CalculateMean(emgPod7);
        float meanEMG8 = CalculateMean(emgPod8);
        float medianAcclX = CalculateMedian(acclXTemp);
        float medianAcclY = CalculateMedian(acclYTemp);
        float medianAcclZ = CalculateMedian(acclZTemp);
        float medianGyroX = CalculateMedian(gyroXTemp);
        float medianGyroY = CalculateMedian(gyroYTemp);
        float medianGyroZ = CalculateMedian(gyroZTemp);
        float medianOrientW = CalculateMedian(orientWTemp);
        float medianOrientX = CalculateMedian(orientXTemp);
        float medianOrientY = CalculateMedian(orientYTemp);
        float medianOrientZ = CalculateMedian(orientZTemp);
        float medianEulerX = CalculateMedian(eulerXTemp);
        float medianEulerY = CalculateMedian(eulerYTemp);
        float medianEulerZ = CalculateMedian(eulerZTemp);
        float medianEMG1 = CalculateMedian(emgPod1);
        float medianEMG2 = CalculateMedian(emgPod2);
        float medianEMG3 = CalculateMedian(emgPod3);
        float medianEMG4 = CalculateMedian(emgPod4);
        float medianEMG5 = CalculateMedian(emgPod5);
        float medianEMG6 = CalculateMedian(emgPod6);
        float medianEMG7 = CalculateMedian(emgPod7);
        float medianEMG8 = CalculateMedian(emgPod8);
        float varianceAcclX = CalculateVariance(acclXTemp);
        float varianceAcclY = CalculateVariance(acclYTemp);
        float varianceAcclZ = CalculateVariance(acclZTemp);
        float varianceGyroX = CalculateVariance(gyroXTemp);
        float varianceGyroY = CalculateVariance(gyroYTemp);
        float varianceGyroZ = CalculateVariance(gyroZTemp);
        float varianceOrientW = CalculateVariance(orientWTemp);
        float varianceOrientX = CalculateVariance(orientXTemp);
        float varianceOrientY = CalculateVariance(orientYTemp);
        float varianceOrientZ = CalculateVariance(orientZTemp);
        float varianceEulerX = CalculateVariance(eulerXTemp);
        float varianceEulerY = CalculateVariance(eulerYTemp);
        float varianceEulerZ = CalculateVariance(eulerZTemp);
        float varianceEMG1 = CalculateVariance(emgPod1);
        float varianceEMG2 = CalculateVariance(emgPod2);
        float varianceEMG3 = CalculateVariance(emgPod3);
        float varianceEMG4 = CalculateVariance(emgPod4);
        float varianceEMG5 = CalculateVariance(emgPod5);
        float varianceEMG6 = CalculateVariance(emgPod6);
        float varianceEMG7 = CalculateVariance(emgPod7);
        float varianceEMG8 = CalculateVariance(emgPod8);
        float devianceAcclX = CalculateDeviance(acclXTemp);
        float devianceAcclY = CalculateDeviance(acclYTemp);
        float devianceAcclZ = CalculateDeviance(acclZTemp);
        float devianceGyroX = CalculateDeviance(gyroXTemp);
        float devianceGyroY = CalculateDeviance(gyroYTemp);
        float devianceGyroZ = CalculateDeviance(gyroZTemp);
        float devianceOrientW = CalculateDeviance(orientWTemp);
        float devianceOrientX = CalculateDeviance(orientXTemp);
        float devianceOrientY = CalculateDeviance(orientYTemp);
        float devianceOrientZ = CalculateDeviance(orientZTemp);
        float devianceEulerX = CalculateDeviance(eulerXTemp);
        float devianceEulerY = CalculateDeviance(eulerYTemp);
        float devianceEulerZ = CalculateDeviance(eulerZTemp);
        float devianceEMG1 = CalculateDeviance(emgPod1);
        float devianceEMG2 = CalculateDeviance(emgPod2);
        float devianceEMG3 = CalculateDeviance(emgPod3);
        float devianceEMG4 = CalculateDeviance(emgPod4);
        float devianceEMG5 = CalculateDeviance(emgPod5);
        float devianceEMG6 = CalculateDeviance(emgPod6);
        float devianceEMG7 = CalculateDeviance(emgPod7);
        float devianceEMG8 = CalculateDeviance(emgPod8);
        float skewnessAcclX = CalculateSkewness(acclXTemp);
        float skewnessAcclY = CalculateSkewness(acclYTemp);
        float skewnessAcclZ = CalculateSkewness(acclZTemp);
        float skewnessGyroX = CalculateSkewness(gyroXTemp);
        float skewnessGyroY = CalculateSkewness(gyroYTemp);
        float skewnessGyroZ = CalculateSkewness(gyroZTemp);
        float skewnessOrientW = CalculateSkewness(orientWTemp);
        float skewnessOrientX = CalculateSkewness(orientXTemp);
        float skewnessOrientY = CalculateSkewness(orientYTemp);
        float skewnessOrientZ = CalculateSkewness(orientZTemp);
        float skewnessEulerX = CalculateSkewness(eulerXTemp);
        float skewnessEulerY = CalculateSkewness(eulerYTemp);
        float skewnessEulerZ = CalculateSkewness(eulerZTemp);
        float skewnessEMG1 = CalculateSkewness(emgPod1);
        float skewnessEMG2 = CalculateSkewness(emgPod2);
        float skewnessEMG3 = CalculateSkewness(emgPod3);
        float skewnessEMG4 = CalculateSkewness(emgPod4);
        float skewnessEMG5 = CalculateSkewness(emgPod5);
        float skewnessEMG6 = CalculateSkewness(emgPod6);
        float skewnessEMG7 = CalculateSkewness(emgPod7);
        float skewnessEMG8 = CalculateSkewness(emgPod8);
        float kurtosisAcclX = CalculateKurtosis(acclXTemp);
        float kurtosisAcclY = CalculateKurtosis(acclYTemp);
        float kurtosisAcclZ = CalculateKurtosis(acclZTemp);
        float kurtosisGyroX = CalculateKurtosis(gyroXTemp);
        float kurtosisGyroY = CalculateKurtosis(gyroYTemp);
        float kurtosisGyroZ = CalculateKurtosis(gyroZTemp);
        float kurtosisOrientW = CalculateKurtosis(orientWTemp);
        float kurtosisOrientX = CalculateKurtosis(orientXTemp);
        float kurtosisOrientY = CalculateKurtosis(orientYTemp);
        float kurtosisOrientZ = CalculateKurtosis(orientZTemp);
        float kurtosisEulerX = CalculateKurtosis(eulerXTemp);
        float kurtosisEulerY = CalculateKurtosis(eulerYTemp);
        float kurtosisEulerZ = CalculateKurtosis(eulerZTemp);
        float kurtosisEMG1 = CalculateKurtosis(emgPod1);
        float kurtosisEMG2 = CalculateKurtosis(emgPod2);
        float kurtosisEMG3 = CalculateKurtosis(emgPod3);
        float kurtosisEMG4 = CalculateKurtosis(emgPod4);
        float kurtosisEMG5 = CalculateKurtosis(emgPod5);
        float kurtosisEMG6 = CalculateKurtosis(emgPod6);
        float kurtosisEMG7 = CalculateKurtosis(emgPod7);
        float kurtosisEMG8 = CalculateKurtosis(emgPod8);

        float[] dataFeature = new float[126];
//        float[] dataFeatureDF = new float[126];
        float[] dataFeautreFloat = new float[126];
        dataFeature[0] = meanAcclX;
        dataFeature[1] = meanAcclY;
        dataFeature[2] = meanAcclZ;
        dataFeature[3] = medianAcclX;
        dataFeature[4] = medianAcclY;
        dataFeature[5] = medianAcclZ;
        dataFeature[6] = varianceAcclX;
        dataFeature[7] = varianceAcclY;
        dataFeature[8] = varianceAcclZ;
        dataFeature[9] = devianceAcclX;
        dataFeature[10] = devianceAcclY;
        dataFeature[11] = devianceAcclZ;
        dataFeature[12] = skewnessAcclX;
        dataFeature[13] = skewnessAcclY;
        dataFeature[14] = skewnessAcclZ;
        dataFeature[15] = kurtosisAcclX;
        dataFeature[16] = kurtosisAcclY;
        dataFeature[17] = kurtosisAcclZ;
        dataFeature[18] = meanGyroX;
        dataFeature[19] = meanGyroY;
        dataFeature[20] = meanGyroZ;
        dataFeature[21] = medianGyroX;
        dataFeature[22] = medianGyroY;
        dataFeature[23] = medianGyroZ;
        dataFeature[24] = varianceGyroX;
        dataFeature[25] = varianceGyroY;
        dataFeature[26] = varianceGyroZ;
        dataFeature[27] = devianceGyroX;
        dataFeature[28] = devianceGyroY;
        dataFeature[29] = devianceGyroZ;
        dataFeature[30] = skewnessGyroX;
        dataFeature[31] = skewnessGyroY;
        dataFeature[32] = skewnessGyroZ;
        dataFeature[33] = kurtosisGyroX;
        dataFeature[34] = kurtosisGyroY;
        dataFeature[35] = kurtosisGyroZ;
        dataFeature[36] = meanOrientX;
        dataFeature[37] = meanOrientY;
        dataFeature[38] = meanOrientZ;
        dataFeature[39] = meanOrientW;
        dataFeature[40] = medianOrientX;
        dataFeature[41] = medianOrientY;
        dataFeature[42] = medianOrientZ;
        dataFeature[43] = medianOrientW;
        dataFeature[44] = varianceOrientX;
        dataFeature[45] = varianceOrientY;
        dataFeature[46] = varianceOrientZ;
        dataFeature[47] = varianceOrientW;
        dataFeature[48] = devianceOrientX;
        dataFeature[49] = devianceOrientY;
        dataFeature[50] = devianceOrientZ;
        dataFeature[51] = devianceOrientW;
        dataFeature[52] = skewnessOrientX;
        dataFeature[53] = skewnessOrientY;
        dataFeature[54] = skewnessOrientZ;
        dataFeature[55] = skewnessOrientW;
        dataFeature[56] = kurtosisOrientX;
        dataFeature[57] = kurtosisOrientY;
        dataFeature[58] = kurtosisOrientZ;
        dataFeature[59] = kurtosisOrientW;
        dataFeature[60] = meanEulerX;
        dataFeature[61] = meanEulerY;
        dataFeature[62] = meanEulerZ;
        dataFeature[63] = medianEulerX;
        dataFeature[64] = medianEulerY;
        dataFeature[65] = medianEulerZ;
        dataFeature[66] = varianceEulerX;
        dataFeature[67] = varianceEulerY;
        dataFeature[68] = varianceEulerZ;
        dataFeature[69] = devianceEulerX;
        dataFeature[70] = devianceEulerY;
        dataFeature[71] = devianceEulerZ;
        dataFeature[72] = skewnessEulerX;
        dataFeature[73] = skewnessEulerY;
        dataFeature[74] = skewnessEulerZ;
        dataFeature[75] = kurtosisEulerX;
        dataFeature[76] = kurtosisEulerY;
        dataFeature[77] = kurtosisEulerZ;
        dataFeature[78] = meanEMG1;
        dataFeature[79] = meanEMG2;
        dataFeature[80] = meanEMG3;
        dataFeature[81] = meanEMG4;
        dataFeature[82] = meanEMG5;
        dataFeature[83] = meanEMG6;
        dataFeature[84] = meanEMG7;
        dataFeature[85] = meanEMG8;
        dataFeature[86] = medianEMG1;
        dataFeature[87] = medianEMG2;
        dataFeature[88] = medianEMG3;
        dataFeature[89] = medianEMG4;
        dataFeature[90] = medianEMG5;
        dataFeature[91] = medianEMG6;
        dataFeature[92] = medianEMG7;
        dataFeature[93] = medianEMG8;
        dataFeature[94] = varianceEMG1;
        dataFeature[95] = varianceEMG2;
        dataFeature[96] = varianceEMG3;
        dataFeature[97] = varianceEMG4;
        dataFeature[98] = varianceEMG5;
        dataFeature[99] = varianceEMG6;
        dataFeature[100] = varianceEMG7;
        dataFeature[101] = varianceEMG8;
        dataFeature[102] = devianceEMG1;
        dataFeature[103] = devianceEMG2;
        dataFeature[104] = devianceEMG3;
        dataFeature[105] = devianceEMG4;
        dataFeature[106] = devianceEMG5;
        dataFeature[107] = devianceEMG6;
        dataFeature[108] = devianceEMG7;
        dataFeature[109] = devianceEMG8;
        dataFeature[110] = skewnessEMG1;
        dataFeature[111] = skewnessEMG2;
        dataFeature[112] = skewnessEMG3;
        dataFeature[113] = skewnessEMG4;
        dataFeature[114] = skewnessEMG5;
        dataFeature[115] = skewnessEMG6;
        dataFeature[116] = skewnessEMG7;
        dataFeature[117] = skewnessEMG8;
        dataFeature[118] = kurtosisEMG1;
        dataFeature[119] = kurtosisEMG2;
        dataFeature[120] = kurtosisEMG3;
        dataFeature[121] = kurtosisEMG4;
        dataFeature[122] = kurtosisEMG5;
        dataFeature[123] = kurtosisEMG6;
        dataFeature[124] = kurtosisEMG7;
        dataFeature[125] = kurtosisEMG8;

        for (int i = 0 ; i < dataFeature.length; i++)
        {
            dataFeautreFloat[i] = dataFeature[i];
        }
        String[] str = new String[dataFeature.length];
//        String[] strDF = new String[dataFeatureDF.length];
        String[] strFLOAT = new String[dataFeautreFloat.length];


//        String[] str = new String[dataFeautreFloat.length];
//        for(int i=0; i<dataFeature.length; i++) {
//            dataFeatureDF[i] = do.parsefloat(df.format(dataFeature[i]));
//        }

        for(int i=0; i<dataFeature.length; i++) {
            str[i] = df.format(dataFeature[i]);
        }

//        for(int i=0; i<dataFeature.length; i++) {
//            strDF[i] = String.valueOf(dataFeatureDF[i]);
//        }

        for(int i=0; i<dataFeautreFloat.length; i++) {
            strFLOAT[i] = String.valueOf(dataFeautreFloat[i]);
        }


        dataSensor = String.join(";", str);
//        dataSensorDF = String.join(";", strDF);
        dataSensorFloat = String.join(";", strFLOAT);

//        JSONArray sendData = new JSONArray();
//        for (int i = 0; i<dataFeature.length; i++){
//            try {
//                sendData = sendData.put(dataFeature[i]);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//        dataSensor = sendData;
        Log.d("data_sensor1", dataSensor + "");
        Log.d("data_sensor_df", dataSensorFloat + "");

    }
}
