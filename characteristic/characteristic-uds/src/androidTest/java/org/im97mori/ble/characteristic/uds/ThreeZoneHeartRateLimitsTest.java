package org.im97mori.ble.characteristic.uds;

import android.bluetooth.BluetoothGattCharacteristic;
import android.os.Parcel;

import org.junit.Test;

import static org.im97mori.ble.BLEConstants.BASE_UUID;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class ThreeZoneHeartRateLimitsTest {

    @Test
    public void test_constructor001() {
        //@formatter:off
        byte[] data = new byte[2];
        data[ 0] = 0x01;
        data[ 1] = 0x02;
        //@formatter:on

        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BASE_UUID, 0, 0);
        bluetoothGattCharacteristic.setValue(data);

        ThreeZoneHeartRateLimits result1 = new ThreeZoneHeartRateLimits(bluetoothGattCharacteristic);
        assertEquals(0x01, result1.getThreeZoneHeartRateLimitsLightFatBurnModerateAerobicLimit());
        assertEquals(0x02, result1.getThreeZoneHeartRateLimitsModerateAerobicHardAnaerobicLimit());
    }

    @Test
    public void test_constructor002() {
        //@formatter:off
        byte[] data = new byte[2];
        data[ 0] = (byte) 0xff;
        data[ 1] = (byte) 0xff;
        //@formatter:on

        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BASE_UUID, 0, 0);
        bluetoothGattCharacteristic.setValue(data);

        ThreeZoneHeartRateLimits result1 = new ThreeZoneHeartRateLimits(bluetoothGattCharacteristic);
        assertEquals(0xff, result1.getThreeZoneHeartRateLimitsLightFatBurnModerateAerobicLimit());
        assertEquals(0xff, result1.getThreeZoneHeartRateLimitsModerateAerobicHardAnaerobicLimit());
    }

    @Test
    public void test_parcelable001() {
        //@formatter:off
        byte[] data = new byte[2];
        data[ 0] = 0x01;
        data[ 1] = 0x02;
        //@formatter:on

        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BASE_UUID, 0, 0);
        bluetoothGattCharacteristic.setValue(data);

        ThreeZoneHeartRateLimits result1 = new ThreeZoneHeartRateLimits(bluetoothGattCharacteristic);
        Parcel parcel = Parcel.obtain();
        result1.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);
        ThreeZoneHeartRateLimits result2 = ThreeZoneHeartRateLimits.CREATOR.createFromParcel(parcel);

        assertEquals(result2.getThreeZoneHeartRateLimitsLightFatBurnModerateAerobicLimit(), result1.getThreeZoneHeartRateLimitsLightFatBurnModerateAerobicLimit());
        assertEquals(result2.getThreeZoneHeartRateLimitsModerateAerobicHardAnaerobicLimit(), result1.getThreeZoneHeartRateLimitsModerateAerobicHardAnaerobicLimit());
    }

    @Test
    public void test_parcelable002() {
        //@formatter:off
        byte[] data = new byte[2];
        data[ 0] = 0x01;
        data[ 1] = 0x02;
        //@formatter:on

        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BASE_UUID, 0, 0);
        bluetoothGattCharacteristic.setValue(data);

        ThreeZoneHeartRateLimits result1 = new ThreeZoneHeartRateLimits(bluetoothGattCharacteristic);
        assertArrayEquals(data, result1.getBytes());
    }

    @Test
    public void test_parcelable003() {
        //@formatter:off
        byte[] data = new byte[2];
        data[ 0] = 0x01;
        data[ 1] = 0x02;
        //@formatter:on

        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BASE_UUID, 0, 0);
        bluetoothGattCharacteristic.setValue(data);

        ThreeZoneHeartRateLimits result1 = new ThreeZoneHeartRateLimits(bluetoothGattCharacteristic);
        ThreeZoneHeartRateLimits result2 = ThreeZoneHeartRateLimits.CREATOR.createFromByteArray(data);
        assertArrayEquals(result1.getBytes(), result2.getBytes());
    }

}