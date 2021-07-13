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
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import eu.darken.myolib.Myo;
import eu.darken.myolib.MyoCmds;
import eu.darken.myolib.MyoInfo;
import eu.darken.myolib.msgs.MyoMsg;
import eu.darken.myolib.processor.classifier.ClassifierEvent;
import eu.darken.myolib.processor.classifier.ClassifierProcessor;
import eu.darken.myolib.processor.emg.EmgData;
import eu.darken.myolib.processor.emg.EmgProcessor;
import eu.darken.myolib.processor.imu.ImuData;
import eu.darken.myolib.processor.imu.ImuProcessor;
import eu.darken.myolib.processor.imu.MotionEvent;
import eu.darken.myolib.processor.imu.MotionProcessor;


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
    double accelero[], acclX, acclY, acclZ;
    double gyro[], gyroX, gyroY, gyroZ;
    double orient[], orientW, orientX, orientY, orientZ;
    double euler[], eulerX, eulerY, eulerZ;
    static ArrayList<Double> acclXTemp = new ArrayList<Double>();
    static ArrayList<Double> acclYTemp = new ArrayList<Double>();
    static ArrayList<Double> acclZTemp = new ArrayList<Double>();
    static ArrayList<Double> gyroXTemp = new ArrayList<Double>();
    static ArrayList<Double> gyroYTemp = new ArrayList<Double>();
    static ArrayList<Double> gyroZTemp = new ArrayList<Double>();
    static ArrayList<Double> orientWTemp = new ArrayList<Double>();
    static ArrayList<Double> orientXTemp = new ArrayList<Double>();
    static ArrayList<Double> orientYTemp = new ArrayList<Double>();
    static ArrayList<Double> orientZTemp = new ArrayList<Double>();
    static  ArrayList<Double> eulerXTemp = new ArrayList<Double>();
    static  ArrayList<Double> eulerYTemp = new ArrayList<Double>();
    static  ArrayList<Double> eulerZTemp = new ArrayList<Double>();
    static  ArrayList<Double> emgPod1 = new ArrayList<Double>();
    static  ArrayList<Double> emgPod2 = new ArrayList<Double>();
    static ArrayList<Double> emgPod3 = new ArrayList<Double>();
    static ArrayList<Double> emgPod4 = new ArrayList<Double>();
    static ArrayList<Double> emgPod5 = new ArrayList<Double>();
    static ArrayList<Double> emgPod6 = new ArrayList<Double>();
    static ArrayList<Double> emgPod7 = new ArrayList<Double>();
    static ArrayList<Double> emgPod8 = new ArrayList<Double>();

    public static JSONArray dataSensor = new JSONArray();

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
        buttonlast = findViewById(R.id.last);
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
                Intent intent = new Intent(getContext(), NextActivity.class);
                getContext().startActivity(intent);
//                record();
            }
        });

        buttonlast.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                record();
            }
        });
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
                            emgPod1.add((double) emgSensor1);
                            emgPod2.add((double) emgSensor2);
                            emgPod3.add((double) emgSensor3);
                            emgPod4.add((double) emgSensor4);
                            emgPod5.add((double) emgSensor5);
                            emgPod6.add((double) emgSensor6);
                            emgPod7.add((double) emgSensor7);
                            emgPod8.add((double) emgSensor8);
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
                        accelero = imuData.getAccelerometerData();
                        acclX = accelero[0];
                        acclY = accelero[1];
                        acclZ = accelero[2];

                        gyro = imuData.getGyroData();
                        gyroX = gyro[0];
                        gyroY = gyro[1];
                        gyroZ = gyro[2];

                        orient = imuData.getOrientationData();
                        orientW = orient[0];
                        orientX = orient[1];
                        orientY = orient[2];
                        orientZ = orient[3];
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
        euler = new double[3];
        eulerX = Math.atan2(2.0 * (orientW * orientX + orientY * orientZ), 1.0 - 2.0 * (orientX * orientX + orientY * orientY));
        eulerY = Math.asin(Math.max(-1.0, Math.min(1.0, 2.0 * (orientW * orientY - orientZ * orientX))));
        eulerZ = Math.atan2(2.0 * (orientW * orientZ + orientX * orientY), 1.0 - 2.0 * (orientY * orientY + orientZ * orientZ));

        euler[0] = eulerX;
        euler[1] = eulerY;
        euler[2] = eulerZ;
    }

    public static double CalculateMean(ArrayList<Double> values){
        int i;
        double mean = 0.0;
        for (i = 0; i < values.size(); i++) {
            mean += values.get(i);
        }
        return mean/values.size();
    }

    public static double CalculateMedian(ArrayList<Double> values){
        if(values.size() == 0) return 0;

        Collections.sort(values);

        double middle = values.size()/2;
        if (values.size()%2 == 1) {
            middle = (values.get(values.size()/2) + values.get(values.size()/2 - 1))/2;
        } else {
            middle = values.get(values.size() / 2);
        }
        return middle;
    }

    public static double CalculateVariance(ArrayList<Double> values){
        int i;
        double mean = CalculateMean(values);
        double variance = 0.0;
        for (i = 0; i < values.size(); i++) {
            variance +=  Math.pow(values.get(i) - 2, 2);
        }
        variance = variance / (values.size() - 1);
        return variance;
    }

    public static double CalculateDeviance(ArrayList<Double> values){
        return Math.sqrt(CalculateVariance(values));
    }

    public static double CalculateSkewness(ArrayList<Double> values){
        int i ;
        int size = 0;
        double skewness = 0.0;
        double mean = CalculateMean(values);
        double deviance = CalculateDeviance(values);

        for (i = 0; i < values.size(); i++) {
            double overDeviance;
            if(deviance == 0)
                overDeviance = 0;
            else
                overDeviance = ((values.get(i) - mean) / deviance);

            skewness = Math.pow(overDeviance,3);
            size++;
        }
        skewness = skewness / size;

        return skewness;
    }

    public static double CalculateKurtosis(ArrayList<Double> values){
        int i;
        int size = 0;
        double kurtosis = 0.0;
        double mean = CalculateMean(values);
        double deviance = CalculateDeviance(values);

        for (i = 0; i < values.size(); i++) {
            double overDeviance;
            if(deviance == 0)
                overDeviance = 0;
            else
                overDeviance = ((values.get(i) - mean) / deviance);

            kurtosis = Math.pow(overDeviance,4);
            size++;
        }
        kurtosis = kurtosis / size;

        return kurtosis;
    }

    public static void record(){
        record = true;
        acclXTemp.clear();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                record = false;
                calculateData();
            }
        },3000);
    }

    public static void calculateData(){
        double meanAcclX = CalculateMean(acclXTemp);
        double meanAcclY = CalculateMean(acclYTemp);
        double meanAcclZ = CalculateMean(acclZTemp);
        double meanGyroX = CalculateMean(gyroXTemp);
        double meanGyroY = CalculateMean(gyroYTemp);
        double meanGyroZ = CalculateMean(gyroZTemp);
        double meanOrientW = CalculateMean(orientWTemp);
        double meanOrientX = CalculateMean(orientXTemp);
        double meanOrientY = CalculateMean(orientYTemp);
        double meanOrientZ = CalculateMean(orientZTemp);
        double meanEulerX = CalculateMean(eulerXTemp);
        double meanEulerY = CalculateMean(eulerYTemp);
        double meanEulerZ = CalculateMean(eulerZTemp);
        double meanEMG1 = CalculateMean(emgPod1);
        double meanEMG2 = CalculateMean(emgPod2);
        double meanEMG3 = CalculateMean(emgPod3);
        double meanEMG4 = CalculateMean(emgPod4);
        double meanEMG5 = CalculateMean(emgPod5);
        double meanEMG6 = CalculateMean(emgPod6);
        double meanEMG7 = CalculateMean(emgPod7);
        double meanEMG8 = CalculateMean(emgPod8);
        double medianAcclX = CalculateMedian(acclXTemp);
        double medianAcclY = CalculateMedian(acclYTemp);
        double medianAcclZ = CalculateMedian(acclZTemp);
        double medianGyroX = CalculateMedian(gyroXTemp);
        double medianGyroY = CalculateMedian(gyroYTemp);
        double medianGyroZ = CalculateMedian(gyroZTemp);
        double medianOrientW = CalculateMedian(orientWTemp);
        double medianOrientX = CalculateMedian(orientXTemp);
        double medianOrientY = CalculateMedian(orientYTemp);
        double medianOrientZ = CalculateMedian(orientZTemp);
        double medianEulerX = CalculateMedian(eulerXTemp);
        double medianEulerY = CalculateMedian(eulerYTemp);
        double medianEulerZ = CalculateMedian(eulerZTemp);
        double medianEMG1 = CalculateMedian(emgPod1);
        double medianEMG2 = CalculateMedian(emgPod2);
        double medianEMG3 = CalculateMedian(emgPod3);
        double medianEMG4 = CalculateMedian(emgPod4);
        double medianEMG5 = CalculateMedian(emgPod5);
        double medianEMG6 = CalculateMedian(emgPod6);
        double medianEMG7 = CalculateMedian(emgPod7);
        double medianEMG8 = CalculateMedian(emgPod8);
        double varianceAcclX = CalculateVariance(acclXTemp);
        double varianceAcclY = CalculateVariance(acclYTemp);
        double varianceAcclZ = CalculateVariance(acclZTemp);
        double varianceGyroX = CalculateVariance(gyroXTemp);
        double varianceGyroY = CalculateVariance(gyroYTemp);
        double varianceGyroZ = CalculateVariance(gyroZTemp);
        double varianceOrientW = CalculateVariance(orientWTemp);
        double varianceOrientX = CalculateVariance(orientXTemp);
        double varianceOrientY = CalculateVariance(orientYTemp);
        double varianceOrientZ = CalculateVariance(orientZTemp);
        double varianceEulerX = CalculateVariance(eulerXTemp);
        double varianceEulerY = CalculateVariance(eulerYTemp);
        double varianceEulerZ = CalculateVariance(eulerZTemp);
        double varianceEMG1 = CalculateVariance(emgPod1);
        double varianceEMG2 = CalculateVariance(emgPod2);
        double varianceEMG3 = CalculateVariance(emgPod3);
        double varianceEMG4 = CalculateVariance(emgPod4);
        double varianceEMG5 = CalculateVariance(emgPod5);
        double varianceEMG6 = CalculateVariance(emgPod6);
        double varianceEMG7 = CalculateVariance(emgPod7);
        double varianceEMG8 = CalculateVariance(emgPod8);
        double devianceAcclX = CalculateDeviance(acclXTemp);
        double devianceAcclY = CalculateDeviance(acclYTemp);
        double devianceAcclZ = CalculateDeviance(acclZTemp);
        double devianceGyroX = CalculateDeviance(gyroXTemp);
        double devianceGyroY = CalculateDeviance(gyroYTemp);
        double devianceGyroZ = CalculateDeviance(gyroZTemp);
        double devianceOrientW = CalculateDeviance(orientWTemp);
        double devianceOrientX = CalculateDeviance(orientXTemp);
        double devianceOrientY = CalculateDeviance(orientYTemp);
        double devianceOrientZ = CalculateDeviance(orientZTemp);
        double devianceEulerX = CalculateDeviance(eulerXTemp);
        double devianceEulerY = CalculateDeviance(eulerYTemp);
        double devianceEulerZ = CalculateDeviance(eulerZTemp);
        double devianceEMG1 = CalculateDeviance(emgPod1);
        double devianceEMG2 = CalculateDeviance(emgPod2);
        double devianceEMG3 = CalculateDeviance(emgPod3);
        double devianceEMG4 = CalculateDeviance(emgPod4);
        double devianceEMG5 = CalculateDeviance(emgPod5);
        double devianceEMG6 = CalculateDeviance(emgPod6);
        double devianceEMG7 = CalculateDeviance(emgPod7);
        double devianceEMG8 = CalculateDeviance(emgPod8);
        double skewnessAcclX = CalculateSkewness(acclXTemp);
        double skewnessAcclY = CalculateSkewness(acclYTemp);
        double skewnessAcclZ = CalculateSkewness(acclZTemp);
        double skewnessGyroX = CalculateSkewness(gyroXTemp);
        double skewnessGyroY = CalculateSkewness(gyroYTemp);
        double skewnessGyroZ = CalculateSkewness(gyroZTemp);
        double skewnessOrientW = CalculateSkewness(orientWTemp);
        double skewnessOrientX = CalculateSkewness(orientXTemp);
        double skewnessOrientY = CalculateSkewness(orientYTemp);
        double skewnessOrientZ = CalculateSkewness(orientZTemp);
        double skewnessEulerX = CalculateSkewness(eulerXTemp);
        double skewnessEulerY = CalculateSkewness(eulerYTemp);
        double skewnessEulerZ = CalculateSkewness(eulerZTemp);
        double skewnessEMG1 = CalculateSkewness(emgPod1);
        double skewnessEMG2 = CalculateSkewness(emgPod2);
        double skewnessEMG3 = CalculateSkewness(emgPod3);
        double skewnessEMG4 = CalculateSkewness(emgPod4);
        double skewnessEMG5 = CalculateSkewness(emgPod5);
        double skewnessEMG6 = CalculateSkewness(emgPod6);
        double skewnessEMG7 = CalculateSkewness(emgPod7);
        double skewnessEMG8 = CalculateSkewness(emgPod8);
        double kurtosisAcclX = CalculateKurtosis(acclXTemp);
        double kurtosisAcclY = CalculateKurtosis(acclYTemp);
        double kurtosisAcclZ = CalculateKurtosis(acclZTemp);
        double kurtosisGyroX = CalculateKurtosis(gyroXTemp);
        double kurtosisGyroY = CalculateKurtosis(gyroYTemp);
        double kurtosisGyroZ = CalculateKurtosis(gyroZTemp);
        double kurtosisOrientW = CalculateKurtosis(orientWTemp);
        double kurtosisOrientX = CalculateKurtosis(orientXTemp);
        double kurtosisOrientY = CalculateKurtosis(orientYTemp);
        double kurtosisOrientZ = CalculateKurtosis(orientZTemp);
        double kurtosisEulerX = CalculateKurtosis(eulerXTemp);
        double kurtosisEulerY = CalculateKurtosis(eulerYTemp);
        double kurtosisEulerZ = CalculateKurtosis(eulerZTemp);
        double kurtosisEMG1 = CalculateKurtosis(emgPod1);
        double kurtosisEMG2 = CalculateKurtosis(emgPod2);
        double kurtosisEMG3 = CalculateKurtosis(emgPod3);
        double kurtosisEMG4 = CalculateKurtosis(emgPod4);
        double kurtosisEMG5 = CalculateKurtosis(emgPod5);
        double kurtosisEMG6 = CalculateKurtosis(emgPod6);
        double kurtosisEMG7 = CalculateKurtosis(emgPod7);
        double kurtosisEMG8 = CalculateKurtosis(emgPod8);

        double[] dataFeature = new double[126];
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

        JSONArray sendData = new JSONArray();
        for (int i = 0; i<dataFeature.length; i++){
            try {
                sendData = sendData.put(dataFeature[i]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        dataSensor = sendData;

        Log.d("send_data", sendData + "");
        Log.d("data_feature", dataFeature[23] + "");
        Log.d("data_sensor1", dataSensor + "");

    }

}
