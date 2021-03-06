package org.im97mori.ble;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattServer;
import android.bluetooth.BluetoothGattServerCallback;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.HandlerThread;
import android.os.ParcelUuid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.im97mori.ble.task.AbstractBLETask;
import org.im97mori.ble.task.AddServiceTask;
import org.im97mori.ble.task.NotificationTask;
import org.im97mori.ble.task.RemoveServiceTask;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import static android.bluetooth.le.AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY;
import static android.bluetooth.le.AdvertiseSettings.ADVERTISE_TX_POWER_HIGH;
import static org.im97mori.ble.BLEConstants.ErrorCodes.APPLICATION_ERROR_9F;

/**
 * BLE Connection(peripheral role)
 */
@SuppressWarnings({"WeakerAccess", "UnusedReturnValue"})
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class BLEServerConnection extends BluetoothGattServerCallback implements BLEServerCallbackDistributer.SubscriberInterface {

    /**
     * SERVICE_UUID for Advertising
     *
     * @see org.im97mori.ble.advertising.AdvertisingDataConstants.AdvertisingDataTypes#DATA_TYPE_COMPLETE_LIST_OF_128_BIT_SERVICE_UUIDS
     */
    public static final UUID MOCK_CONTROL_SERVICE_UUID = UUID.fromString("00000000-a087-4fa3-add4-3b8a7d5d491f");

    /**
     * {@link Context} instance
     */
    private final Context mContext;

    /**
     * {@link BluetoothManager} instance
     */
    private final BluetoothManager mBluetoothManager;

    /**
     * {@link BluetoothAdapter} instance
     */
    private final BluetoothAdapter mBluetoothAdapter;

    /**
     * {@link BluetoothGattServer} instance
     */
    private BluetoothGattServer mBluetoothGattServer;

    /**
     * {@link AdvertiseCallback} instance
     */
    private AdvertiseCallback mAdvertiseCallback;

    /**
     * {@link BluetoothLeAdvertiser} instance
     */
    private BluetoothLeAdvertiser mBluetoothLeAdvertiser;

    /**
     * newest {@link TaskHandler} instance
     */
    private TaskHandler mTaskHandler;

    /**
     * {@link BLEServerCallbackDistributer} instance
     */
    protected BLEServerCallbackDistributer mBLEServerCallbackDistributer;


    /**
     * for {@link BLEServerCallbackDistributer.SubscriberInterface#getSubscriberCallbackSet()}
     */
    protected final List<BLEServerCallback> mAttachedBLEServerCallbackSet = new LinkedList<>();

    /**
     * @param context {@link Context} instance
     */
    public BLEServerConnection(@NonNull Context context) {
        mContext = context;
        mBluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mBLEServerCallbackDistributer = new BLEServerCallbackDistributer(this);
    }

    /**
     * check server status
     *
     * @return {@code true}:{@link BluetoothGattServer} and {@link BluetoothLeAdvertiser} started, {@code false}: server stopped
     */
    public boolean isStarted() {
        return mBluetoothGattServer != null;
    }

    /**
     * start {@link BluetoothGattServer} and {@link BluetoothLeAdvertiser}
     */
    public synchronized void start() {
        if (mBluetoothGattServer == null) {

            mBluetoothGattServer = mBluetoothManager.openGattServer(mContext, this);
            if (mBluetoothGattServer != null) {

                HandlerThread thread = new HandlerThread(this.getClass().getSimpleName());
                thread.start();
                mTaskHandler = new TaskHandler(thread.getLooper());

                mBLEServerCallbackDistributer.onServerStarted();

                for (BLEServerCallback bleServerCallback : mAttachedBLEServerCallbackSet) {
                    bleServerCallback.setup(this);
                }
            }
        }
    }

    /**
     * stop {@link BluetoothGattServer} and {@link BluetoothLeAdvertiser}
     */
    public synchronized void quit() {
        if (mAdvertiseCallback != null) {
            mBluetoothLeAdvertiser.stopAdvertising(mAdvertiseCallback);
            mAdvertiseCallback = null;
            mBluetoothLeAdvertiser = null;
            mBLEServerCallbackDistributer.onAdvertisingFinished();
        }

        if (mBluetoothGattServer != null) {
            mBluetoothGattServer.close();
            mBluetoothGattServer = null;
            mBLEServerCallbackDistributer.onServerStopped();
        }

        if (mTaskHandler != null) {
            mTaskHandler.quit();
            mTaskHandler = null;
        }
    }

    /**
     * attach callback
     *
     * @param bleServerCallback {@link BLEServerCallback}
     */
    public synchronized void attach(BLEServerCallback bleServerCallback) {
        if (mAttachedBLEServerCallbackSet.add(bleServerCallback) && mBluetoothGattServer != null) {
            bleServerCallback.setup(this);
        }
    }

    /**
     * detach callback
     *
     * @param bleServerCallback {@link BLEServerCallback}
     */
    public synchronized void detach(BLEServerCallback bleServerCallback) {
        if (mAttachedBLEServerCallbackSet.remove(bleServerCallback) && mBluetoothGattServer != null) {
            bleServerCallback.tearDown(this);
        }
    }

    /**
     * check target callback is attached
     *
     * @param bleServerCallback target callback
     * @return {@code true}:attached, {@code false}:not attached
     */
    public synchronized boolean isAttached(BLEServerCallback bleServerCallback) {
        return mAttachedBLEServerCallbackSet.contains(bleServerCallback);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void onConnectionStateChange(@NonNull BluetoothDevice device
            , int status
            , int newState) {
        if (mBluetoothGattServer != null) {
            if (BluetoothProfile.STATE_CONNECTED == newState) {
                mBLEServerCallbackDistributer.onDeviceConnected(device);
            } else if (BluetoothProfile.STATE_DISCONNECTED == newState) {
                mBLEServerCallbackDistributer.onDeviceDisconnected(device);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void onServiceAdded(int status
            , @NonNull BluetoothGattService service) {
        if (mBluetoothGattServer != null) {
            if (BluetoothGatt.GATT_SUCCESS == status) {
                mTaskHandler.sendProcessingMessage(AddServiceTask.createAddServiceSuccessMessage(service));
            } else {
                mTaskHandler.sendProcessingMessage(AddServiceTask.createAddServiceErrorMessage(service, status));
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void onCharacteristicReadRequest(@NonNull BluetoothDevice device
            , int requestId
            , int offset
            , @NonNull BluetoothGattCharacteristic characteristic) {
        if (mBluetoothGattServer != null) {
            if (!mBLEServerCallbackDistributer.onCharacteristicReadRequest(this, device, requestId, offset, characteristic, true)) {
                mBluetoothGattServer.sendResponse(device, requestId, APPLICATION_ERROR_9F, offset, null);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void onCharacteristicWriteRequest(@NonNull BluetoothDevice device
            , int requestId
            , @NonNull BluetoothGattCharacteristic characteristic
            , boolean preparedWrite
            , boolean responseNeeded
            , int offset
            , @NonNull byte[] value) {
        if (mBluetoothGattServer != null) {
            if (!mBLEServerCallbackDistributer.onCharacteristicWriteRequest(this, device, requestId, characteristic, preparedWrite, responseNeeded, offset, value, true) && responseNeeded) {
                mBluetoothGattServer.sendResponse(device, requestId, APPLICATION_ERROR_9F, offset, null);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void onDescriptorReadRequest(@NonNull BluetoothDevice device
            , int requestId
            , int offset
            , @NonNull BluetoothGattDescriptor descriptor) {
        if (mBluetoothGattServer != null) {
            if (!mBLEServerCallbackDistributer.onDescriptorReadRequest(this, device, requestId, offset, descriptor, true)) {
                mBluetoothGattServer.sendResponse(device, requestId, APPLICATION_ERROR_9F, offset, null);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void onDescriptorWriteRequest(@NonNull BluetoothDevice device
            , int requestId
            , BluetoothGattDescriptor descriptor
            , boolean preparedWrite
            , boolean responseNeeded
            , int offset
            , @NonNull byte[] value) {
        if (mBluetoothGattServer != null) {
            if (!mBLEServerCallbackDistributer.onDescriptorWriteRequest(this, device, requestId, descriptor, preparedWrite, responseNeeded, offset, value, true) && responseNeeded) {
                mBluetoothGattServer.sendResponse(device, requestId, APPLICATION_ERROR_9F, offset, null);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void onExecuteWrite(BluetoothDevice device
            , int requestId
            , boolean execute) {
        if (mBluetoothGattServer != null) {
            if (!mBLEServerCallbackDistributer.onExecuteWrite(this, device, requestId, execute, true)) {
                mBluetoothGattServer.sendResponse(device, requestId, APPLICATION_ERROR_9F, 0, null);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void onNotificationSent(BluetoothDevice device
            , int status) {
        if (mBluetoothGattServer != null) {
            if (BluetoothGatt.GATT_SUCCESS == status) {
                mTaskHandler.sendProcessingMessage(NotificationTask.createNotificationSentSuccessMessage(device));
            } else {
                mTaskHandler.sendProcessingMessage(NotificationTask.createNotificationSentErrorMessage(device, status));
            }
        }
    }

    /**
     * @return {@link BLEServerCallback} instance
     */
    @NonNull
    public synchronized BLEServerCallback getBLEServerCallback() {
        if (mBLEServerCallbackDistributer == null) {
            mBLEServerCallbackDistributer = new BLEServerCallbackDistributer(this);
        }
        return mBLEServerCallbackDistributer;
    }

    /**
     * @return {@link BLEServerCallback} instance
     */
    @Override
    public Collection<BLEServerCallback> getSubscriberCallbackSet() {
        return mAttachedBLEServerCallbackSet;
    }

    /**
     * @return current {@link BluetoothGattServer} instance
     */
    @Nullable
    public BluetoothGattServer getBluetoothGattServer() {
        return mBluetoothGattServer;
    }

    /**
     * Add user original task
     *
     * @param task {@link AbstractBLETask} instance
     * @see TaskHandler#addTask(AbstractBLETask)
     */
    public synchronized void addTask(@NonNull AbstractBLETask task) {
        if (mTaskHandler != null) {
            mTaskHandler.addTask(task);
        }
    }

    /**
     * Create add service task
     *
     * @param bluetoothGattService task target {@link BluetoothGattService} instance
     * @param timeout              timeout(millis)
     * @param argument             callback argument
     * @param bleServerCallback    {@code null}:task result is communicated to all attached callbacks, {@code non-null}:the task result is communicated to the specified callback
     * @return task id. if {@code null} returned, task was not registed
     */
    public synchronized Integer createAddServiceTask(@NonNull BluetoothGattService bluetoothGattService
            , long timeout
            , @Nullable Bundle argument
            , @Nullable BLEServerCallback bleServerCallback) {
        Integer taskId = null;
        if (mBluetoothGattServer != null) {
            AddServiceTask task = new AddServiceTask(this
                    , mTaskHandler
                    , bluetoothGattService
                    , timeout
                    , BLEServerCallbackDistributer.wrapArgument(argument, bleServerCallback));
            mTaskHandler.addTask(task);
            taskId = task.getTaskId();
        }
        return taskId;
    }

    /**
     * Create remove service task
     *
     * @param bluetoothGattService task target {@link BluetoothGattService} instance
     * @param timeout              timeout(millis)
     * @param argument             callback argument
     * @param bleServerCallback    {@code null}:task result is communicated to all attached callbacks, {@code non-null}:the task result is communicated to the specified callback
     * @return task id. if {@code null} returned, task was not registed
     */
    public synchronized Integer createRemoveServiceTask(@NonNull BluetoothGattService bluetoothGattService
            , long timeout
            , @Nullable Bundle argument
            , @Nullable BLEServerCallback bleServerCallback) {
        Integer taskId = null;
        if (mBluetoothGattServer != null) {
            RemoveServiceTask task = new RemoveServiceTask(this
                    , mTaskHandler
                    , bluetoothGattService
                    , timeout
                    , BLEServerCallbackDistributer.wrapArgument(argument, bleServerCallback));
            mTaskHandler.addTask(task);
            taskId = task.getTaskId();
        }
        return taskId;
    }

    /**
     * Create Notification / Indication task
     *
     * @param device                   task target {@link BluetoothDevice} instance
     * @param serviceUUID              task target service {@link UUID}
     * @param serviceInstanceId        task target service incetanceId {@link BluetoothGattService#getInstanceId()}
     * @param characteristicUUID       task target characteristic {@link UUID}
     * @param characteristicInstanceId task target characteristic incetanceId {@link BluetoothGattCharacteristic#getInstanceId()}
     * @param byteArrayInterface       task target data class
     * @param isConfirm                {@code true}:indication, {@code false}:notification
     * @param timeout                  timeout(millis)
     * @param delay                    delay(millis)
     * @param argument                 callback argument
     * @param bleServerCallback        {@code null}:task result is communicated to all attached callbacks, {@code non-null}:the task result is communicated to the specified callback
     * @return task id. if {@code null} returned, task was not registed
     */
    public synchronized Integer createNotificationTask(@NonNull BluetoothDevice device
            , @NonNull UUID serviceUUID
            , int serviceInstanceId
            , @NonNull UUID characteristicUUID
            , int characteristicInstanceId
            , @NonNull ByteArrayInterface byteArrayInterface
            , boolean isConfirm
            , long timeout
            , long delay
            , @Nullable Bundle argument
            , @Nullable BLEServerCallback bleServerCallback) {
        Integer taskId = null;
        if (mBluetoothGattServer != null) {
            NotificationTask task = new NotificationTask(this
                    , mBluetoothGattServer
                    , device
                    , mTaskHandler
                    , serviceUUID
                    , serviceInstanceId
                    , characteristicUUID
                    , characteristicInstanceId
                    , byteArrayInterface
                    , isConfirm
                    , timeout
                    , BLEServerCallbackDistributer.wrapArgument(argument, bleServerCallback));
            mTaskHandler.addTaskDelayed(task, delay);
            taskId = task.getTaskId();
        }
        return taskId;
    }

    /**
     * @see #startAdvertising(boolean, UUID)
     */
    public synchronized boolean startAdvertising() {
        return startAdvertising(false, null);
    }

    /**
     * start advertising
     *
     * @param includeDeviceName flag for {@link android.bluetooth.le.AdvertiseData.Builder#setIncludeDeviceName(boolean)}
     * @param serviceUUID       UUID for {@link android.bluetooth.le.AdvertiseData.Builder#addServiceUuid(ParcelUuid)}
     * @return {@code true}:advertising started, {@code false}:advertising not started(allready started)
     */
    public synchronized boolean startAdvertising(boolean includeDeviceName, @Nullable UUID serviceUUID) {
        boolean result = false;
        if (mBluetoothLeAdvertiser == null) {
            mBluetoothLeAdvertiser = mBluetoothAdapter.getBluetoothLeAdvertiser();
            if (mBluetoothLeAdvertiser != null) {
                AdvertiseSettings.Builder asBuilder = new AdvertiseSettings.Builder();
                asBuilder.setConnectable(true);
                asBuilder.setAdvertiseMode(ADVERTISE_MODE_LOW_LATENCY);
                asBuilder.setTxPowerLevel(ADVERTISE_TX_POWER_HIGH);
                asBuilder.setTimeout(0);
                AdvertiseData.Builder adBuilder = new AdvertiseData.Builder();
                adBuilder.setIncludeDeviceName(includeDeviceName);
                adBuilder.addServiceUuid(new ParcelUuid(serviceUUID == null ? MOCK_CONTROL_SERVICE_UUID : serviceUUID));

                mAdvertiseCallback = new AdvertiseCallback() {

                    /**
                     * {@inheritDoc}
                     */
                    @Override
                    public void onStartSuccess(AdvertiseSettings settingsInEffect) {
                        mBLEServerCallbackDistributer.onAdvertisingStartSuccess(settingsInEffect);
                    }

                    /**
                     * {@inheritDoc}
                     */
                    @Override
                    public void onStartFailure(int errorCode) {
                        synchronized (BLEServerConnection.this) {
                            mAdvertiseCallback = null;
                            mBLEServerCallbackDistributer.onAdvertisingStartFailed(errorCode);
                        }
                    }

                };

                mBluetoothLeAdvertiser.startAdvertising(asBuilder.build(), adBuilder.build(), mAdvertiseCallback);
                result = true;
            }
        }

        if (!result) {
            mBLEServerCallbackDistributer.onAdvertisingStartFailed(null);
        }

        return result;
    }

    /**
     * Advertising stop
     *
     * @return {@code true}:advertising stopped, {@code false}:advertising not stopped(allready stopped)
     */
    public synchronized boolean stopAdvertising() {
        boolean result = false;
        if (mAdvertiseCallback != null) {
            mBluetoothLeAdvertiser.stopAdvertising(mAdvertiseCallback);
            mAdvertiseCallback = null;
            mBLEServerCallbackDistributer.onAdvertisingFinished();
            result = true;
        }
        return result;
    }

}
