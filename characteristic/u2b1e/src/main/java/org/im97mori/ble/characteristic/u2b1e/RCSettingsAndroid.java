package org.im97mori.ble.characteristic.u2b1e;

import android.bluetooth.BluetoothGattCharacteristic;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import org.im97mori.ble.ByteArrayCreater;

import static org.im97mori.ble.BLEConstants.CharacteristicUUID.R_C_SETTINGS_CHARACTERISTIC;

/**
 * RC Settings (Characteristics UUID: 0x2B1E)
 */
@SuppressWarnings("WeakerAccess")
public class RCSettingsAndroid extends RCSettings implements Parcelable {

    /**
     * @see ByteArrayCreater
     */
    public static final ByteArrayCreater<RCSettingsAndroid> CREATOR = new ByteArrayCreater<RCSettingsAndroid>() {

        /**
         * {@inheritDoc}
         */
        @Override
        @NonNull
        public RCSettingsAndroid createFromParcel(@NonNull Parcel in) {
            return new RCSettingsAndroid(in);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        @NonNull
        public RCSettingsAndroid[] newArray(int size) {
            return new RCSettingsAndroid[size];
        }

        /**
         * {@inheritDoc}
         */
        @NonNull
        public RCSettingsAndroid createFromByteArray(@NonNull byte[] values) {
            BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(R_C_SETTINGS_CHARACTERISTIC, 0, 0);
            bluetoothGattCharacteristic.setValue(values);
            return new RCSettingsAndroid(bluetoothGattCharacteristic);
        }

    };

    /**
     * Constructor from {@link BluetoothGattCharacteristic}
     *
     * @param bluetoothGattCharacteristic Characteristics UUID: 0x2B1E
     */
    public RCSettingsAndroid(@NonNull BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        super(bluetoothGattCharacteristic.getValue());
    }

    /**
     * Constructor from {@link Parcel}
     *
     * @param in Parcel
     */
    @SuppressWarnings("ConstantConditions")
    private RCSettingsAndroid(@NonNull Parcel in) {
        super(in.createByteArray());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeByteArray(getBytes());
    }

}