package org.im97mori.ble.characteristic.u2a24;

import android.bluetooth.BluetoothGattCharacteristic;
import android.os.Parcel;

import org.junit.Test;

import java.nio.charset.StandardCharsets;

import static org.im97mori.ble.BLEConstants.BASE_UUID;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class ModelNumberStringAndroidTest {

    @Test
    public void test_constructor001() {
        String modelNumber = "2JCIE-BU01";

        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BASE_UUID, 0, 0);
        bluetoothGattCharacteristic.setValue(modelNumber.getBytes(StandardCharsets.UTF_8));

        ModelNumberStringAndroid result1 = new ModelNumberStringAndroid(bluetoothGattCharacteristic);
        assertEquals(modelNumber, result1.getModelNumber());
    }

    @Test
    public void test_constructor002() {
        String modelNumber = "2JCIE-BU01";

        ModelNumberStringAndroid result1 = new ModelNumberStringAndroid(modelNumber);
        assertEquals(modelNumber, result1.getModelNumber());
    }

    @Test
    public void test_parcelable001() {
        String modelNumber = "2JCIE-BU01";

        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BASE_UUID, 0, 0);
        bluetoothGattCharacteristic.setValue(modelNumber.getBytes(StandardCharsets.UTF_8));

        ModelNumberStringAndroid result1 = new ModelNumberStringAndroid(bluetoothGattCharacteristic);
        Parcel parcel = Parcel.obtain();
        result1.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);
        ModelNumberStringAndroid result2 = ModelNumberStringAndroid.CREATOR.createFromParcel(parcel);

        assertEquals(result1.getModelNumber(), result2.getModelNumber());
    }


    @Test
    public void test_parcelable002() {
        String modelNumber = "2JCIE-BU01";

        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BASE_UUID, 0, 0);
        bluetoothGattCharacteristic.setValue(modelNumber.getBytes(StandardCharsets.UTF_8));

        ModelNumberStringAndroid result1 = new ModelNumberStringAndroid(bluetoothGattCharacteristic);
        byte[] resultData = result1.getBytes();
        assertArrayEquals(modelNumber.getBytes(), resultData);
    }

    @Test
    public void test_parcelable003() {
        String modelNumber = "2JCIE-BU01";

        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BASE_UUID, 0, 0);
        bluetoothGattCharacteristic.setValue(modelNumber.getBytes(StandardCharsets.UTF_8));

        ModelNumberStringAndroid result1 = new ModelNumberStringAndroid(bluetoothGattCharacteristic);
        ModelNumberStringAndroid result2 = ModelNumberStringAndroid.CREATOR.createFromByteArray(modelNumber.getBytes(StandardCharsets.UTF_8));
        assertArrayEquals(result1.getBytes(), result2.getBytes());
    }
}
