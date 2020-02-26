package org.im97mori.ble.characteristic.u2a14;

import android.bluetooth.BluetoothGattCharacteristic;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import org.im97mori.ble.ByteArrayCreater;

import static org.im97mori.ble.BLEConstants.CharacteristicUUID.REFERENCE_TIME_INFORMATION_CHARACTERISTIC;

/**
 * Reference Time Information (Characteristics UUID: 0x2A14)
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class ReferenceTimeInformationAndroid extends ReferenceTimeInformation implements Parcelable {

    /**
     * @see ByteArrayCreater
     */
    public static final ByteArrayCreater<ReferenceTimeInformationAndroid> CREATOR = new ByteArrayCreater<ReferenceTimeInformationAndroid>() {

        /**
         * {@inheritDoc}
         */
        @Override
        @NonNull
        public ReferenceTimeInformationAndroid createFromParcel(@NonNull Parcel in) {
            return new ReferenceTimeInformationAndroid(in);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        @NonNull
        public ReferenceTimeInformationAndroid[] newArray(int size) {
            return new ReferenceTimeInformationAndroid[size];
        }

        /**
         * {@inheritDoc}
         */
        @NonNull
        public ReferenceTimeInformationAndroid createFromByteArray(@NonNull byte[] values) {
            BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(REFERENCE_TIME_INFORMATION_CHARACTERISTIC, 0, 0);
            bluetoothGattCharacteristic.setValue(values);
            return new ReferenceTimeInformationAndroid(bluetoothGattCharacteristic);
        }

    };

    /**
     * Constructor from {@link BluetoothGattCharacteristic}
     *
     * @param bluetoothGattCharacteristic Characteristics UUID: 0x2A14
     */
    public ReferenceTimeInformationAndroid(@NonNull BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        super(bluetoothGattCharacteristic.getValue());
    }

    /**
     * Constructor from {@link Parcel}
     *
     * @param in Parcel
     */
    @SuppressWarnings("ConstantConditions")
    private ReferenceTimeInformationAndroid(@NonNull Parcel in) {
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