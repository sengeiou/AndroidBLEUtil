package org.im97mori.ble.profile.peripheral;

import android.content.Context;

import androidx.annotation.NonNull;

import org.im97mori.ble.BLEServerCallback;
import org.im97mori.ble.BLEServerConnection;

import java.util.UUID;

/**
 * Core Peripheral Profile
 */
public abstract class AbstractProfileMockCallback {

    /**
     * {@link BLEServerConnection} instance
     */
    private final BLEServerConnection mBLEServerConnection;

    /**
     * advertising flag
     */
    private final boolean mIsAdvertisingFlag;

    /**
     * @param context            {@link Context} instance
     * @param isAdvertising      advertising flag
     * @param bleServerCallbacks Service array
     */
    public AbstractProfileMockCallback(@NonNull Context context, boolean isAdvertising, @NonNull BLEServerCallback... bleServerCallbacks) {
        mBLEServerConnection = new BLEServerConnection(context);
        mIsAdvertisingFlag = isAdvertising;

        for (BLEServerCallback bleServerCallback : bleServerCallbacks) {
            if (bleServerCallback != null) {
                mBLEServerConnection.attach(bleServerCallback);
            }
        }
    }

    /**
     * @return {@link UUID} for advertising
     */
    @NonNull
    public abstract UUID getServiceUUID();

    /**
     * start profile
     */
    public synchronized void start() {
        mBLEServerConnection.start();
        startAdvertising();
    }

    /**
     * start advertising
     *
     * @see BLEServerConnection#startAdvertising()
     */
    public boolean startAdvertising() {
        boolean result = false;
        if (mIsAdvertisingFlag) {
            result = mBLEServerConnection.startAdvertising(false, getServiceUUID());
        }
        return result;
    }

    /**
     * quit profile
     */
    public synchronized void quit() {
        stopAdvertising();
        mBLEServerConnection.quit();
    }

    /**
     * stop advertising
     *
     * @see BLEServerConnection#stopAdvertising()
     */
    public boolean stopAdvertising() {
        boolean result = false;
        if (mIsAdvertisingFlag) {
            result = mBLEServerConnection.stopAdvertising();
        }
        return result;
    }

    /**
     * @see BLEServerConnection#isStarted()
     */
    public boolean isStarted() {
        return mBLEServerConnection.isStarted();
    }

}
