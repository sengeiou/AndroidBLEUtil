package org.im97mori.ble.task;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.os.Bundle;
import android.os.Message;
import android.text.format.DateUtils;

import org.im97mori.ble.BLEConnection;
import org.im97mori.ble.TaskHandler;

import java.util.UUID;

import static org.im97mori.ble.BLEConstants.ErrorCodes.BUSY;
import static org.im97mori.ble.BLEConstants.ErrorCodes.CANCEL;
import static org.im97mori.ble.BLEConstants.ErrorCodes.UNKNOWN;

/**
 * Read characteristic task
 */
@SuppressWarnings("JavadocReference")
public class ReadCharacteristicTask extends AbstractBLETask {

    /**
     * Default timeout(millis) for read:5sec
     */
    public static final long TIMEOUT_MILLIS = DateUtils.SECOND_IN_MILLIS * 5;

    /**
     * create read characteristic message
     *
     * @param serviceUUID        target service UUID
     * @param characteristicUUID target characteristic UUID
     * @param obj                instance for {@link android.os.Handler#removeCallbacksAndMessages(Object)}
     * @return read characteristic {@link Message} instance
     */
    public static Message createReadCharacteristicMessage(UUID serviceUUID, UUID characteristicUUID, Object obj) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_SERVICE_UUID, serviceUUID);
        bundle.putSerializable(KEY_CHARACTERISTIC_UUID, characteristicUUID);
        bundle.putInt(KEY_NEXT_PROGRESS, PROGRESS_CHARACTERISTIC_READ_START);
        Message message = new Message();
        message.setData(bundle);
        message.obj = obj;
        return message;
    }

    /**
     * create read characteristic finished message
     *
     * @param serviceUUID        target service UUID
     * @param characteristicUUID target characteristic UUID
     * @param values             {@link BluetoothGattCharacteristic#getValue()}
     * @return read characteristic finished {@link Message} instance
     */
    public static Message createReadCharacteristicFinishedMessage(UUID serviceUUID, UUID characteristicUUID, byte[] values) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_SERVICE_UUID, serviceUUID);
        bundle.putSerializable(KEY_CHARACTERISTIC_UUID, characteristicUUID);
        bundle.putByteArray(KEY_VALUES, values);
        bundle.putInt(KEY_NEXT_PROGRESS, PROGRESS_CHARACTERISTIC_READ_SUCCESS);
        Message message = new Message();
        message.setData(bundle);
        return message;
    }

    /**
     * create read characteristic error message
     *
     * @param serviceUUID        target service UUID
     * @param characteristicUUID target characteristic UUID
     * @param status             {@link android.bluetooth.BluetoothGattCallback#onCharacteristicRead(BluetoothGatt, BluetoothGattCharacteristic, int)} 3rd parameter
     * @return read characteristic error {@link Message} instance
     */
    public static Message createReadCharacteristicErrorMessage(UUID serviceUUID, UUID characteristicUUID, int status) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_SERVICE_UUID, serviceUUID);
        bundle.putSerializable(KEY_CHARACTERISTIC_UUID, characteristicUUID);
        bundle.putInt(KEY_STATUS, status);
        bundle.putInt(KEY_NEXT_PROGRESS, PROGRESS_CHARACTERISTIC_READ_ERROR);
        Message message = new Message();
        message.setData(bundle);
        return message;
    }

    /**
     * task target {@link BLEConnection} instance
     */
    private final BLEConnection mBLEConnection;

    /**
     * task target {@link BluetoothGatt} instance
     */
    private final BluetoothGatt mBluetoothGatt;

    /**
     * task target {@link TaskHandler} instance
     */
    private final TaskHandler mTaskHandler;

    /**
     * task target service {@link UUID}
     */
    private final UUID mServiceUUID;

    /**
     * task target characteristic {@link UUID}
     */
    private final UUID mCharacteristicUUID;

    /**
     * timeout(millis)
     */
    private final long mTimeout;

    /**
     * callback argument
     */
    private final Bundle mArgument;

    /**
     * @param bleConnection      task target {@link BLEConnection} instance
     * @param bluetoothGatt      task target {@link BluetoothGatt} instance
     * @param taskHandler        task target {@link TaskHandler} instance
     * @param serviceUUID        task target service {@link UUID}
     * @param characteristicUUID task target characteristic {@link UUID}
     * @param timeout            timeout(millis)
     * @param argument           callback argument
     */
    public ReadCharacteristicTask(BLEConnection bleConnection, BluetoothGatt bluetoothGatt, TaskHandler taskHandler, UUID serviceUUID, UUID characteristicUUID, long timeout, Bundle argument) {
        mBLEConnection = bleConnection;
        mBluetoothGatt = bluetoothGatt;
        mServiceUUID = serviceUUID;
        mCharacteristicUUID = characteristicUUID;
        mTaskHandler = taskHandler;
        mTimeout = timeout;
        mArgument = argument;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean doProcess(Message message) {
        Bundle bundle = message.getData();
        UUID serviceUUID = (UUID) bundle.getSerializable(KEY_SERVICE_UUID);
        UUID characteristicUUID = (UUID) bundle.getSerializable(KEY_CHARACTERISTIC_UUID);
        int nextProgress = bundle.getInt(KEY_NEXT_PROGRESS);

        // timeout
        if (this == message.obj && PROGRESS_TIMEOUT == nextProgress) {
            mBLEConnection.getBLECallback().onCharacteristicReadTimeout(mTaskId, mBLEConnection.getBluetoothDevice(), mServiceUUID, mCharacteristicUUID, mTimeout, mArgument);
            mCurrentProgress = nextProgress;
        } else if (PROGRESS_INIT == mCurrentProgress) {
            // current:init, next:read start
            if (this == message.obj && PROGRESS_CHARACTERISTIC_READ_START == nextProgress) {

                BluetoothGattCharacteristic bluetoothGattCharacteristic = null;
                boolean result = false;
                BluetoothGattService bluetoothGattService = mBluetoothGatt.getService(mServiceUUID);
                if (bluetoothGattService != null) {
                    bluetoothGattCharacteristic = bluetoothGattService.getCharacteristic(mCharacteristicUUID);
                    if (bluetoothGattCharacteristic != null) {
                        // read characteristic
                        result = mBluetoothGatt.readCharacteristic(bluetoothGattCharacteristic);
                    }
                }

                if (result) {
                    // set timeout message
                    mTaskHandler.sendProcessingMessage(createTimeoutMessage(mCharacteristicUUID, this), mTimeout);
                } else {
                    if (bluetoothGattCharacteristic == null) {
                        nextProgress = PROGRESS_FINISHED;
                        mBLEConnection.getBLECallback().onCharacteristicReadFailed(mTaskId, mBLEConnection.getBluetoothDevice(), mServiceUUID, mCharacteristicUUID, UNKNOWN, mArgument);
                    } else {
                        nextProgress = PROGRESS_BUSY;
                        mBLEConnection.getBLECallback().onCharacteristicReadFailed(mTaskId, mBLEConnection.getBluetoothDevice(), mServiceUUID, mCharacteristicUUID, BUSY, mArgument);
                    }
                }
                mCurrentProgress = nextProgress;
            }
        } else if (PROGRESS_CHARACTERISTIC_READ_START == mCurrentProgress) {
            if (mServiceUUID.equals(serviceUUID) && mCharacteristicUUID.equals(characteristicUUID)) {
                // current:read start, next:read success
                if (PROGRESS_CHARACTERISTIC_READ_SUCCESS == nextProgress) {
                    mBLEConnection.getBLECallback().onCharacteristicReadSuccess(mTaskId, mBLEConnection.getBluetoothDevice(), mServiceUUID, mCharacteristicUUID, bundle.getByteArray(KEY_VALUES), mArgument);
                } else if (PROGRESS_CHARACTERISTIC_READ_ERROR == nextProgress) {
                    // current:read start, next:read error
                    mBLEConnection.getBLECallback().onCharacteristicReadFailed(mTaskId, mBLEConnection.getBluetoothDevice(), mServiceUUID, mCharacteristicUUID, bundle.getInt(KEY_STATUS), mArgument);
                }

                mCurrentProgress = PROGRESS_FINISHED;
                // remove timeout message
                mTaskHandler.removeCallbacksAndMessages(this);
            }
        }

        return PROGRESS_FINISHED == mCurrentProgress || PROGRESS_BUSY == mCurrentProgress || PROGRESS_TIMEOUT == mCurrentProgress;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isBusy() {
        return PROGRESS_BUSY == mCurrentProgress || PROGRESS_TIMEOUT == mCurrentProgress;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void cancel() {
        mBLEConnection.getBLECallback().onCharacteristicReadFailed(mTaskId, mBLEConnection.getBluetoothDevice(), mServiceUUID, mCharacteristicUUID, CANCEL, mArgument);
    }

}
