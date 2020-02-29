package org.im97mori.ble.characteristic.u2ad0;

import android.bluetooth.BluetoothGattCharacteristic;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import org.im97mori.ble.ByteArrayCreater;

import static org.im97mori.ble.BLEConstants.CharacteristicUUID.STAIR_CLIMBER_DATA_CHARACTERISTIC;

/**
 * Stair Climber Data packet (Characteristics UUID: 0x2AD0)
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class StairClimberDataPacketAndroid extends StairClimberDataPacket implements Parcelable {

    /**
     * @see ByteArrayCreater
     */
    public static final ByteArrayCreater<StairClimberDataPacketAndroid> CREATOR = new ByteArrayCreater<StairClimberDataPacketAndroid>() {

        /**
         * {@inheritDoc}
         */
        @Override
        @NonNull
        public StairClimberDataPacketAndroid createFromParcel(@NonNull Parcel in) {
            return new StairClimberDataPacketAndroid(in);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        @NonNull
        public StairClimberDataPacketAndroid[] newArray(int size) {
            return new StairClimberDataPacketAndroid[size];
        }

        /**
         * {@inheritDoc}
         */
        @NonNull
        public StairClimberDataPacketAndroid createFromByteArray(@NonNull byte[] values) {
            BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(STAIR_CLIMBER_DATA_CHARACTERISTIC, 0, 0);
            bluetoothGattCharacteristic.setValue(values);
            return new StairClimberDataPacketAndroid(bluetoothGattCharacteristic);
        }

    };

    /**
     * Constructor from {@link BluetoothGattCharacteristic}
     *
     * @param bluetoothGattCharacteristic Characteristics UUID: 0x2AD0
     */
    public StairClimberDataPacketAndroid(@NonNull BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        super(bluetoothGattCharacteristic.getValue());
    }

    /**
     * Constructor from {@link Parcel}
     *
     * @param in Parcel
     */
    @SuppressWarnings("ConstantConditions")
    private StairClimberDataPacketAndroid(@NonNull Parcel in) {
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