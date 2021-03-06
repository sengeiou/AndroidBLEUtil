package org.im97mori.ble.characteristic.u2ad5;

import android.bluetooth.BluetoothGattCharacteristic;
import android.os.Parcel;

import org.junit.Test;

import static org.im97mori.ble.BLEConstants.BASE_UUID;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

@SuppressWarnings("unused")
public class SupportedInclinationRangeAndroidTest {

    //@formatter:off
    private static final byte[] data_00001;
    static {
        byte[] data = new byte[6];
        data[ 0] = 0x01;
        data[ 1] = 0x02;
        data[ 2] = 0x03;
        data[ 3] = 0x04;
        data[ 4] = 0x05;
        data[ 5] = 0x06;
        data_00001 = data;
    }
    //@formatter:on

    private byte[] getData() {
        int index = -1;
        byte[] data = null;

        StackTraceElement[] stackTraceElementArray = Thread.currentThread().getStackTrace();
        for (int i = 0; i < stackTraceElementArray.length; i++) {
            StackTraceElement stackTraceElement = stackTraceElementArray[i];
            if ("getData".equals(stackTraceElement.getMethodName())) {
                index = i + 1;
                break;
            }
        }
        if (index >= 0 && index < stackTraceElementArray.length) {
            StackTraceElement stackTraceElement = stackTraceElementArray[index];
            String[] splitted = stackTraceElement.getMethodName().split("_");
            try {
                data = (byte[]) this.getClass().getDeclaredField("data_" + splitted[splitted.length - 1]).get(null);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    @Test
    public void test_constructor_00001() {
        byte[] data = getData();

        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BASE_UUID, 0, 0);
        bluetoothGattCharacteristic.setValue(data);

        SupportedInclinationRangeAndroid result1 = new SupportedInclinationRangeAndroid(bluetoothGattCharacteristic);
        assertEquals(0x0201, result1.getMinimumInclination());
        assertEquals(SupportedInclinationRange.MINIMUM_INCLINATION_RESOLUTION * 0x0201, result1.getMinimumInclinationPercent(), 0);
        assertEquals(0x0403, result1.getMaximumInclination());
        assertEquals(SupportedInclinationRange.MAXIMUM_INCLINATION_RESOLUTION * 0x0403, result1.getMaximumInclinationPercent(), 0);
        assertEquals(0x0605, result1.getMinimumIncrement());
        assertEquals(SupportedInclinationRange.MINIMUM_INCREMENT_RESOLUTION * 0x0605, result1.getMinimumIncrementPercent(), 0);
    }

    @Test
    public void test_constructor_00002() {
        int minimumInclination = 1;
        int maximumInclination = 2;
        int minimumIncrement = 3;

        SupportedInclinationRangeAndroid result1 = new SupportedInclinationRangeAndroid(minimumInclination, maximumInclination, minimumIncrement);
        assertEquals(minimumInclination, result1.getMinimumInclination());
        assertEquals(maximumInclination, result1.getMaximumInclination());
        assertEquals(minimumIncrement, result1.getMinimumIncrement());
    }

    @Test
    public void test_parcelable_1_00001() {
        byte[] data = getData();

        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BASE_UUID, 0, 0);
        bluetoothGattCharacteristic.setValue(data);

        SupportedInclinationRangeAndroid result1 = new SupportedInclinationRangeAndroid(bluetoothGattCharacteristic);
        Parcel parcel = Parcel.obtain();
        result1.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);
        SupportedInclinationRangeAndroid result2 = SupportedInclinationRangeAndroid.CREATOR.createFromParcel(parcel);
        assertEquals(result1.getMinimumInclination(), result2.getMinimumInclination());
        assertEquals(result1.getMaximumInclination(), result2.getMaximumInclination());
        assertEquals(result1.getMinimumIncrement(), result2.getMinimumIncrement());
    }

    @Test
    public void test_parcelable_2_00001() {
        byte[] data = getData();

        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BASE_UUID, 0, 0);
        bluetoothGattCharacteristic.setValue(data);

        SupportedInclinationRangeAndroid result1 = new SupportedInclinationRangeAndroid(bluetoothGattCharacteristic);
        assertArrayEquals(data, result1.getBytes());
    }

    @Test
    public void test_parcelable_3_00001() {
        byte[] data = getData();

        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BASE_UUID, 0, 0);
        bluetoothGattCharacteristic.setValue(data);

        SupportedInclinationRangeAndroid result1 = new SupportedInclinationRangeAndroid(bluetoothGattCharacteristic);
        SupportedInclinationRangeAndroid result2 = SupportedInclinationRangeAndroid.CREATOR.createFromByteArray(data);
        assertArrayEquals(result1.getBytes(), result2.getBytes());
    }

}
