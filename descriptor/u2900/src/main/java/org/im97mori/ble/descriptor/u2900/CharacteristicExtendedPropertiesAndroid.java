package org.im97mori.ble.descriptor.u2900;

import android.bluetooth.BluetoothGattDescriptor;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import org.im97mori.ble.ByteArrayCreater;

import static org.im97mori.ble.BLEConstants.DescriptorUUID.CHARACTERISTIC_EXTENDED_PROPERTIES_DESCRIPTOR;

/**
 * Characteristic Extended Properties (Descriptor UUID: 0x2900)
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class CharacteristicExtendedPropertiesAndroid extends CharacteristicExtendedProperties implements Parcelable {

    /**
     * @see ByteArrayCreater
     */
    public static final ByteArrayCreater<CharacteristicExtendedPropertiesAndroid> CREATOR = new ByteArrayCreater<CharacteristicExtendedPropertiesAndroid>() {

        /**
         * {@inheritDoc}
         */
        @Override
        public CharacteristicExtendedPropertiesAndroid createFromParcel(@NonNull Parcel in) {
            return new CharacteristicExtendedPropertiesAndroid(in);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public CharacteristicExtendedPropertiesAndroid[] newArray(int size) {
            return new CharacteristicExtendedPropertiesAndroid[size];
        }

        /**
         * {@inheritDoc}
         */
        @NonNull
        public CharacteristicExtendedPropertiesAndroid createFromByteArray(@NonNull byte[] values) {
            BluetoothGattDescriptor bluetoothGattDescriptor = new BluetoothGattDescriptor(CHARACTERISTIC_EXTENDED_PROPERTIES_DESCRIPTOR, 0);
            bluetoothGattDescriptor.setValue(values);
            return new CharacteristicExtendedPropertiesAndroid(bluetoothGattDescriptor);
        }

    };

    /**
     * Constructor from {@link BluetoothGattDescriptor}
     *
     * @param bluetoothGattDescriptor Characteristics UUID: 0x2900
     */
    public CharacteristicExtendedPropertiesAndroid(@NonNull BluetoothGattDescriptor bluetoothGattDescriptor) {
        super(bluetoothGattDescriptor.getValue());
    }

    /**
     * Constructor from {@link Parcel}
     *
     * @param in Parcel
     */
    @SuppressWarnings("ConstantConditions")
    private CharacteristicExtendedPropertiesAndroid(@NonNull Parcel in) {
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
