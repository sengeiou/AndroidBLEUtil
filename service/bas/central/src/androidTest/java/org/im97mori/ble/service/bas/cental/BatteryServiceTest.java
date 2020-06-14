package org.im97mori.ble.service.bas.cental;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.im97mori.ble.BLECallback;
import org.im97mori.ble.ByteArrayInterface;
import org.im97mori.ble.characteristic.u2a19.BatteryLevelAndroid;
import org.im97mori.ble.descriptor.u2902.ClientCharacteristicConfigurationAndroid;
import org.im97mori.ble.descriptor.u2904.CharacteristicPresentationFormatAndroid;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.im97mori.ble.BLEConstants.CharacteristicUUID.BATTERY_LEVEL_CHARACTERISTIC;
import static org.im97mori.ble.BLEConstants.CharacteristicUUID.CURRENT_TIME_CHARACTERISTIC;
import static org.im97mori.ble.BLEConstants.DescriptorUUID.CHARACTERISTIC_PRESENTATION_FORMAT_DESCRIPTOR;
import static org.im97mori.ble.BLEConstants.DescriptorUUID.CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
import static org.im97mori.ble.BLEConstants.ServiceUUID.BATTERY_SERVICE;
import static org.im97mori.ble.BLEConstants.ServiceUUID.GENERIC_ACCESS_SERVICE;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class BatteryServiceTest {

    @Test
    public void test_onDiscoverServiceSuccess_00001() {
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        BatteryService batteryService = new BatteryService(mockBLEConnection, new MockBatteryServiceCallback(), null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.<BluetoothGattService>emptyList(), null);

        assertNull(batteryService.getCount());
    }

    @Test
    public void test_onDiscoverServiceSuccess_00002() {
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        BatteryService batteryService = new BatteryService(mockBLEConnection, new MockBatteryServiceCallback(), null);
        BluetoothGattService bluetoothGattService = new BluetoothGattService(GENERIC_ACCESS_SERVICE, 0);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);

        assertNull(batteryService.getCount());
    }

    @Test
    public void test_onDiscoverServiceSuccess_00003() {
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        BatteryService batteryService = new BatteryService(mockBLEConnection, new MockBatteryServiceCallback(), null);
        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);

        assertNull(batteryService.getCount());
    }

    @Test
    public void test_onDiscoverServiceSuccess_00004() {
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        BatteryService batteryService = new BatteryService(mockBLEConnection, new MockBatteryServiceCallback(), null) {
            @Override
            public boolean isStarted() {
                return true;
            }
        };
        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);

        assertNotNull(batteryService.getCount());
        assertEquals(1, batteryService.getCount().intValue());
    }

    @Test
    public void test_onCharacteristicReadSuccess_00001() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = BATTERY_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = BATTERY_LEVEL_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final byte[] originalValues = new byte[]{4};
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onBatteryLevelReadSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @NonNull Integer serviceInstanceId, @NonNull UUID characteristicUUID, @NonNull Integer characteristicInstanceId, @Nullable Integer index, @NonNull BatteryLevelAndroid batteryLevelAndroid, @Nullable Bundle argument) {
                assertEquals(originalTaskId, taskId);
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertNull(index);
                assertArrayEquals(originalValues, batteryLevelAndroid.getBytes());
                assertEquals(originalBundle, argument);
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onCharacteristicReadSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalValues, originalBundle);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onCharacteristicReadSuccess_00002() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = BATTERY_SERVICE;
        final Integer originalServiceInstanceId = 0;
        final UUID originalCharacteristicUUID = BATTERY_LEVEL_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 0;
        final byte[] originalValues = new byte[]{4};
        final Bundle originalBundle = new Bundle();

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onBatteryLevelReadSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @NonNull Integer serviceInstanceId, @NonNull UUID characteristicUUID, @NonNull Integer characteristicInstanceId, @Nullable Integer index, @NonNull BatteryLevelAndroid batteryLevelAndroid, @Nullable Bundle argument) {
                assertEquals(originalTaskId, taskId);
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertNotNull(index);
                assertEquals(0, index.intValue());
                assertArrayEquals(originalValues, batteryLevelAndroid.getBytes());
                assertEquals(originalBundle, argument);
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onCharacteristicReadSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalValues, originalBundle);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onCharacteristicReadSuccess_00003() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ACCESS_SERVICE;
        final Integer originalServiceInstanceId = 0;
        final UUID originalCharacteristicUUID = BATTERY_LEVEL_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 0;
        final byte[] originalValues = new byte[]{4};
        final Bundle originalBundle = new Bundle();

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onBatteryLevelReadSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @NonNull Integer serviceInstanceId, @NonNull UUID characteristicUUID, @NonNull Integer characteristicInstanceId, @Nullable Integer index, @NonNull BatteryLevelAndroid batteryLevelAndroid, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onCharacteristicReadSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalValues, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onCharacteristicReadSuccess_00004() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = BATTERY_LEVEL_CHARACTERISTIC;
        final Integer originalServiceInstanceId = 0;
        final UUID originalCharacteristicUUID = CURRENT_TIME_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 0;
        final byte[] originalValues = new byte[]{4};
        final Bundle originalBundle = new Bundle();

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onBatteryLevelReadSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @NonNull Integer serviceInstanceId, @NonNull UUID characteristicUUID, @NonNull Integer characteristicInstanceId, @Nullable Integer index, @NonNull BatteryLevelAndroid batteryLevelAndroid, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onCharacteristicReadSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalValues, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onCharacteristicReadSuccess_00005() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ACCESS_SERVICE;
        final Integer originalServiceInstanceId = 0;
        final UUID originalCharacteristicUUID = CURRENT_TIME_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 0;
        final byte[] originalValues = new byte[]{4};
        final Bundle originalBundle = new Bundle();

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onBatteryLevelReadSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @NonNull Integer serviceInstanceId, @NonNull UUID characteristicUUID, @NonNull Integer characteristicInstanceId, @Nullable Integer index, @NonNull BatteryLevelAndroid batteryLevelAndroid, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onCharacteristicReadSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalValues, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onCharacteristicReadFailed_00001() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = BATTERY_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = BATTERY_LEVEL_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onBatteryLevelReadFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, int status, @Nullable Bundle argument) {
                assertEquals(originalTaskId, taskId);
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertNull(index);
                assertEquals(originalStatus, status);
                assertEquals(originalBundle, argument);
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onCharacteristicReadFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalStatus, originalBundle);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onCharacteristicReadFailed_00002() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = BATTERY_SERVICE;
        final Integer originalServiceInstanceId = 0;
        final UUID originalCharacteristicUUID = BATTERY_LEVEL_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 0;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onBatteryLevelReadFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, int status, @Nullable Bundle argument) {
                assertEquals(originalTaskId, taskId);
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertNotNull(index);
                assertEquals(0, index.intValue());
                assertEquals(originalStatus, status);
                assertEquals(originalBundle, argument);
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onCharacteristicReadFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalStatus, originalBundle);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onCharacteristicReadFailed_00003() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ACCESS_SERVICE;
        final Integer originalServiceInstanceId = 0;
        final UUID originalCharacteristicUUID = BATTERY_LEVEL_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 0;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onBatteryLevelReadFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, int status, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onCharacteristicReadFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalStatus, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onCharacteristicReadFailed_00004() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = BATTERY_LEVEL_CHARACTERISTIC;
        final Integer originalServiceInstanceId = 0;
        final UUID originalCharacteristicUUID = CURRENT_TIME_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 0;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onBatteryLevelReadFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, int status, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onCharacteristicReadFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalStatus, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onCharacteristicReadFailed_00005() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ACCESS_SERVICE;
        final Integer originalServiceInstanceId = 0;
        final UUID originalCharacteristicUUID = CURRENT_TIME_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 0;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onBatteryLevelReadFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, int status, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onCharacteristicReadFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalStatus, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onCharacteristicReadTimeout_00001() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = BATTERY_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = BATTERY_LEVEL_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final int originalTimeout = 4;
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onBatteryLevelReadTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, long timeout, @Nullable Bundle argument) {
                assertEquals(originalTaskId, taskId);
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertNull(index);
                assertEquals(originalTimeout, timeout);
                assertEquals(originalBundle, argument);
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onCharacteristicReadTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalTimeout, originalBundle);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onCharacteristicReadTimeout_00002() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = BATTERY_SERVICE;
        final Integer originalServiceInstanceId = 0;
        final UUID originalCharacteristicUUID = BATTERY_LEVEL_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 0;
        final int originalTimeout = 4;
        final Bundle originalBundle = new Bundle();

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onBatteryLevelReadTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, long timeout, @Nullable Bundle argument) {
                assertEquals(originalTaskId, taskId);
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertNotNull(index);
                assertEquals(0, index.intValue());
                assertEquals(originalTimeout, timeout);
                assertEquals(originalBundle, argument);
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onCharacteristicReadTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalTimeout, originalBundle);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onCharacteristicReadTimeout_00003() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ACCESS_SERVICE;
        final Integer originalServiceInstanceId = 0;
        final UUID originalCharacteristicUUID = BATTERY_LEVEL_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 0;
        final int originalTimeout = 4;
        final Bundle originalBundle = new Bundle();

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onBatteryLevelReadTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, long timeout, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onCharacteristicReadTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalTimeout, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onCharacteristicReadTimeout_00004() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = BATTERY_LEVEL_CHARACTERISTIC;
        final Integer originalServiceInstanceId = 0;
        final UUID originalCharacteristicUUID = CURRENT_TIME_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 0;
        final int originalTimeout = 4;
        final Bundle originalBundle = new Bundle();

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onBatteryLevelReadTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, long timeout, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onCharacteristicReadTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalTimeout, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onCharacteristicReadTimeout_00005() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ACCESS_SERVICE;
        final Integer originalServiceInstanceId = 0;
        final UUID originalCharacteristicUUID = CURRENT_TIME_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 0;
        final int originalTimeout = 4;
        final Bundle originalBundle = new Bundle();

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onBatteryLevelReadTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, long timeout, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onCharacteristicReadTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalTimeout, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorReadSuccess_00001() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = BATTERY_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = BATTERY_LEVEL_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final byte[] originalValues = new byte[]{4, 5};
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onClientCharacteristicConfigurationReadSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @NonNull Integer serviceInstanceId, @NonNull UUID characteristicUUID, @NonNull Integer characteristicInstanceId, @Nullable Integer index, @NonNull ClientCharacteristicConfigurationAndroid clientCharacteristicConfigurationAndroid, @Nullable Bundle argument) {
                assertEquals(originalTaskId, taskId);
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertNull(index);
                assertArrayEquals(originalValues, clientCharacteristicConfigurationAndroid.getBytes());
                assertEquals(originalBundle, argument);
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDescriptorReadSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalValues, originalBundle);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onDescriptorReadSuccess_00002() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = BATTERY_SERVICE;
        final Integer originalServiceInstanceId = 0;
        final UUID originalCharacteristicUUID = BATTERY_LEVEL_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 0;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final byte[] originalValues = new byte[]{4, 5};
        final Bundle originalBundle = new Bundle();

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        BluetoothGattDescriptor bluetoothGattDescriptor = new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE);
        bluetoothGattCharacteristic.addDescriptor(bluetoothGattDescriptor);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onClientCharacteristicConfigurationReadSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @NonNull Integer serviceInstanceId, @NonNull UUID characteristicUUID, @NonNull Integer characteristicInstanceId, @Nullable Integer index, @NonNull ClientCharacteristicConfigurationAndroid clientCharacteristicConfigurationAndroid, @Nullable Bundle argument) {
                assertEquals(originalTaskId, taskId);
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertNotNull(index);
                assertEquals(0, index.intValue());
                assertArrayEquals(originalValues, clientCharacteristicConfigurationAndroid.getBytes());
                assertEquals(originalBundle, argument);
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onDescriptorReadSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalValues, originalBundle);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onDescriptorReadSuccess_00003() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ACCESS_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = BATTERY_LEVEL_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final byte[] originalValues = new byte[]{4, 5};
        final Bundle originalBundle = new Bundle();

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        BluetoothGattDescriptor bluetoothGattDescriptor = new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE);
        bluetoothGattCharacteristic.addDescriptor(bluetoothGattDescriptor);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onClientCharacteristicConfigurationReadSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @NonNull Integer serviceInstanceId, @NonNull UUID characteristicUUID, @NonNull Integer characteristicInstanceId, @Nullable Integer index, @NonNull ClientCharacteristicConfigurationAndroid clientCharacteristicConfigurationAndroid, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onDescriptorReadSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalValues, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorReadSuccess_00004() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = BATTERY_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = CURRENT_TIME_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final byte[] originalValues = new byte[]{4, 5};
        final Bundle originalBundle = new Bundle();

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        BluetoothGattDescriptor bluetoothGattDescriptor = new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE);
        bluetoothGattCharacteristic.addDescriptor(bluetoothGattDescriptor);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onClientCharacteristicConfigurationReadSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @NonNull Integer serviceInstanceId, @NonNull UUID characteristicUUID, @NonNull Integer characteristicInstanceId, @Nullable Integer index, @NonNull ClientCharacteristicConfigurationAndroid clientCharacteristicConfigurationAndroid, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onDescriptorReadSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalValues, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorReadSuccess_00005() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ACCESS_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = CURRENT_TIME_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final byte[] originalValues = new byte[]{4, 5};
        final Bundle originalBundle = new Bundle();

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        BluetoothGattDescriptor bluetoothGattDescriptor = new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE);
        bluetoothGattCharacteristic.addDescriptor(bluetoothGattDescriptor);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onClientCharacteristicConfigurationReadSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @NonNull Integer serviceInstanceId, @NonNull UUID characteristicUUID, @NonNull Integer characteristicInstanceId, @Nullable Integer index, @NonNull ClientCharacteristicConfigurationAndroid clientCharacteristicConfigurationAndroid, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onDescriptorReadSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalValues, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorReadSuccess_00101() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = BATTERY_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = BATTERY_LEVEL_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CHARACTERISTIC_PRESENTATION_FORMAT_DESCRIPTOR;
        final byte[] originalValues = new byte[]{4, 5, 6, 7, 8, 9, 10};
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onCharacteristicPresentationFormatReadSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @NonNull Integer serviceInstanceId, @NonNull UUID characteristicUUID, @NonNull Integer characteristicInstanceId, @Nullable Integer index, @NonNull CharacteristicPresentationFormatAndroid characteristicPresentationFormatAndroid, @Nullable Bundle argument) {
                assertEquals(originalTaskId, taskId);
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertNull(index);
                assertArrayEquals(originalValues, characteristicPresentationFormatAndroid.getBytes());
                assertEquals(originalBundle, argument);
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDescriptorReadSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalValues, originalBundle);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onDescriptorReadSuccess_00102() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = BATTERY_SERVICE;
        final Integer originalServiceInstanceId = 0;
        final UUID originalCharacteristicUUID = BATTERY_LEVEL_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 0;
        final UUID originalDescriptorUUID = CHARACTERISTIC_PRESENTATION_FORMAT_DESCRIPTOR;
        final byte[] originalValues = new byte[]{4, 5, 6, 7, 8, 9, 10};
        final Bundle originalBundle = new Bundle();

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        BluetoothGattDescriptor bluetoothGattDescriptor = new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE);
        bluetoothGattCharacteristic.addDescriptor(bluetoothGattDescriptor);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onCharacteristicPresentationFormatReadSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @NonNull Integer serviceInstanceId, @NonNull UUID characteristicUUID, @NonNull Integer characteristicInstanceId, @Nullable Integer index, @NonNull CharacteristicPresentationFormatAndroid characteristicPresentationFormatAndroid, @Nullable Bundle argument) {
                assertEquals(originalTaskId, taskId);
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertNotNull(index);
                assertEquals(0, index.intValue());
                assertArrayEquals(originalValues, characteristicPresentationFormatAndroid.getBytes());
                assertEquals(originalBundle, argument);
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onDescriptorReadSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalValues, originalBundle);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onDescriptorReadSuccess_00103() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ACCESS_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = BATTERY_LEVEL_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CHARACTERISTIC_PRESENTATION_FORMAT_DESCRIPTOR;
        final byte[] originalValues = new byte[]{4, 5, 6, 7, 8, 9, 10};
        final Bundle originalBundle = new Bundle();

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        BluetoothGattDescriptor bluetoothGattDescriptor = new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE);
        bluetoothGattCharacteristic.addDescriptor(bluetoothGattDescriptor);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onCharacteristicPresentationFormatReadSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @NonNull Integer serviceInstanceId, @NonNull UUID characteristicUUID, @NonNull Integer characteristicInstanceId, @Nullable Integer index, @NonNull CharacteristicPresentationFormatAndroid characteristicPresentationFormatAndroid, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onDescriptorReadSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalValues, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorReadSuccess_00104() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = BATTERY_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = CURRENT_TIME_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CHARACTERISTIC_PRESENTATION_FORMAT_DESCRIPTOR;
        final byte[] originalValues = new byte[]{4, 5, 6, 7, 8, 9, 10};
        final Bundle originalBundle = new Bundle();

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        BluetoothGattDescriptor bluetoothGattDescriptor = new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE);
        bluetoothGattCharacteristic.addDescriptor(bluetoothGattDescriptor);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onCharacteristicPresentationFormatReadSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @NonNull Integer serviceInstanceId, @NonNull UUID characteristicUUID, @NonNull Integer characteristicInstanceId, @Nullable Integer index, @NonNull CharacteristicPresentationFormatAndroid characteristicPresentationFormatAndroid, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onDescriptorReadSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalValues, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorReadSuccess_00105() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ACCESS_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = CURRENT_TIME_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CHARACTERISTIC_PRESENTATION_FORMAT_DESCRIPTOR;
        final byte[] originalValues = new byte[]{4, 5, 6, 7, 8, 9, 10};
        final Bundle originalBundle = new Bundle();

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        BluetoothGattDescriptor bluetoothGattDescriptor = new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE);
        bluetoothGattCharacteristic.addDescriptor(bluetoothGattDescriptor);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onCharacteristicPresentationFormatReadSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @NonNull Integer serviceInstanceId, @NonNull UUID characteristicUUID, @NonNull Integer characteristicInstanceId, @Nullable Integer index, @NonNull CharacteristicPresentationFormatAndroid characteristicPresentationFormatAndroid, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onDescriptorReadSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalValues, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorReadFailed_00001() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = BATTERY_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = BATTERY_LEVEL_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onClientCharacteristicConfigurationReadFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, int status, @Nullable Bundle argument) {
                assertEquals(originalTaskId, taskId);
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertNull(index);
                assertEquals(originalStatus, status);
                assertEquals(originalBundle, argument);
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDescriptorReadFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalStatus, originalBundle);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onDescriptorReadFailed_00002() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = BATTERY_SERVICE;
        final Integer originalServiceInstanceId = 0;
        final UUID originalCharacteristicUUID = BATTERY_LEVEL_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 0;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        BluetoothGattDescriptor bluetoothGattDescriptor = new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE);
        bluetoothGattCharacteristic.addDescriptor(bluetoothGattDescriptor);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onClientCharacteristicConfigurationReadFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, int status, @Nullable Bundle argument) {
                assertEquals(originalTaskId, taskId);
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertNotNull(index);
                assertEquals(0, index.intValue());
                assertEquals(originalStatus, status);
                assertEquals(originalBundle, argument);
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onDescriptorReadFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalStatus, originalBundle);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onDescriptorReadFailed_00003() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ACCESS_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = BATTERY_LEVEL_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        BluetoothGattDescriptor bluetoothGattDescriptor = new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE);
        bluetoothGattCharacteristic.addDescriptor(bluetoothGattDescriptor);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onClientCharacteristicConfigurationReadFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, int status, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onDescriptorReadFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalStatus, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorReadFailed_00004() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = BATTERY_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = CURRENT_TIME_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        BluetoothGattDescriptor bluetoothGattDescriptor = new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE);
        bluetoothGattCharacteristic.addDescriptor(bluetoothGattDescriptor);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onClientCharacteristicConfigurationReadFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, int status, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onDescriptorReadFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalStatus, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorReadFailed_00005() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ACCESS_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = CURRENT_TIME_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        BluetoothGattDescriptor bluetoothGattDescriptor = new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE);
        bluetoothGattCharacteristic.addDescriptor(bluetoothGattDescriptor);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onClientCharacteristicConfigurationReadFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, int status, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onDescriptorReadFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalStatus, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorReadFailed_00101() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = BATTERY_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = BATTERY_LEVEL_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CHARACTERISTIC_PRESENTATION_FORMAT_DESCRIPTOR;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onCharacteristicPresentationFormatReadFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, int status, @Nullable Bundle argument) {
                assertEquals(originalTaskId, taskId);
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertNull(index);
                assertEquals(originalStatus, status);
                assertEquals(originalBundle, argument);
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDescriptorReadFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalStatus, originalBundle);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onDescriptorReadFailed_00102() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = BATTERY_SERVICE;
        final Integer originalServiceInstanceId = 0;
        final UUID originalCharacteristicUUID = BATTERY_LEVEL_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 0;
        final UUID originalDescriptorUUID = CHARACTERISTIC_PRESENTATION_FORMAT_DESCRIPTOR;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        BluetoothGattDescriptor bluetoothGattDescriptor = new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE);
        bluetoothGattCharacteristic.addDescriptor(bluetoothGattDescriptor);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onCharacteristicPresentationFormatReadFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, int status, @Nullable Bundle argument) {
                assertEquals(originalTaskId, taskId);
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertNotNull(index);
                assertEquals(0, index.intValue());
                assertEquals(originalStatus, status);
                assertEquals(originalBundle, argument);
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onDescriptorReadFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalStatus, originalBundle);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onDescriptorReadFailed_00103() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ACCESS_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = BATTERY_LEVEL_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CHARACTERISTIC_PRESENTATION_FORMAT_DESCRIPTOR;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        BluetoothGattDescriptor bluetoothGattDescriptor = new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE);
        bluetoothGattCharacteristic.addDescriptor(bluetoothGattDescriptor);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onCharacteristicPresentationFormatReadFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, int status, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onDescriptorReadFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalStatus, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorReadFailed_00104() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = BATTERY_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = CURRENT_TIME_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CHARACTERISTIC_PRESENTATION_FORMAT_DESCRIPTOR;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        BluetoothGattDescriptor bluetoothGattDescriptor = new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE);
        bluetoothGattCharacteristic.addDescriptor(bluetoothGattDescriptor);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onCharacteristicPresentationFormatReadFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, int status, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onDescriptorReadFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalStatus, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorReadFailed_00105() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ACCESS_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = CURRENT_TIME_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CHARACTERISTIC_PRESENTATION_FORMAT_DESCRIPTOR;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        BluetoothGattDescriptor bluetoothGattDescriptor = new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE);
        bluetoothGattCharacteristic.addDescriptor(bluetoothGattDescriptor);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onCharacteristicPresentationFormatReadFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, int status, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onDescriptorReadFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalStatus, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorReadTimeout_00001() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = BATTERY_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = BATTERY_LEVEL_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final long originalTimeout = 4;
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onClientCharacteristicConfigurationReadTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, long timeout, @Nullable Bundle argument) {
                assertEquals(originalTaskId, taskId);
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertNull(index);
                assertEquals(originalTimeout, timeout);
                assertEquals(originalBundle, argument);
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDescriptorReadTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalTimeout, originalBundle);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onDescriptorReadTimeout_00002() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = BATTERY_SERVICE;
        final Integer originalServiceInstanceId = 0;
        final UUID originalCharacteristicUUID = BATTERY_LEVEL_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 0;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final int originalTimeout = 4;
        final Bundle originalBundle = new Bundle();

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        BluetoothGattDescriptor bluetoothGattDescriptor = new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE);
        bluetoothGattCharacteristic.addDescriptor(bluetoothGattDescriptor);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onClientCharacteristicConfigurationReadTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, long timeout, @Nullable Bundle argument) {
                assertEquals(originalTaskId, taskId);
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertNotNull(index);
                assertEquals(0, index.intValue());
                assertEquals(originalTimeout, timeout);
                assertEquals(originalBundle, argument);
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onDescriptorReadTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalTimeout, originalBundle);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onDescriptorReadTimeout_00003() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ACCESS_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = BATTERY_LEVEL_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final int originalTimeout = 4;
        final Bundle originalBundle = new Bundle();

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        BluetoothGattDescriptor bluetoothGattDescriptor = new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE);
        bluetoothGattCharacteristic.addDescriptor(bluetoothGattDescriptor);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onClientCharacteristicConfigurationReadTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, long timeout, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onDescriptorReadTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalTimeout, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorReadTimeout_00004() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = BATTERY_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = CURRENT_TIME_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final int originalTimeout = 4;
        final Bundle originalBundle = new Bundle();

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        BluetoothGattDescriptor bluetoothGattDescriptor = new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE);
        bluetoothGattCharacteristic.addDescriptor(bluetoothGattDescriptor);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onClientCharacteristicConfigurationReadTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, long timeout, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onDescriptorReadTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalTimeout, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorReadTimeout_00005() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ACCESS_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = CURRENT_TIME_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final int originalTimeout = 4;
        final Bundle originalBundle = new Bundle();

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        BluetoothGattDescriptor bluetoothGattDescriptor = new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE);
        bluetoothGattCharacteristic.addDescriptor(bluetoothGattDescriptor);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onClientCharacteristicConfigurationReadTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, long timeout, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onDescriptorReadTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalTimeout, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorReadTimeout_00101() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = BATTERY_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = BATTERY_LEVEL_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CHARACTERISTIC_PRESENTATION_FORMAT_DESCRIPTOR;
        final int originalTimeout = 4;
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onCharacteristicPresentationFormatReadTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, long timeout, @Nullable Bundle argument) {
                assertEquals(originalTaskId, taskId);
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertNull(index);
                assertEquals(originalTimeout, timeout);
                assertEquals(originalBundle, argument);
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDescriptorReadTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalTimeout, originalBundle);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onDescriptorReadTimeout_00102() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = BATTERY_SERVICE;
        final Integer originalServiceInstanceId = 0;
        final UUID originalCharacteristicUUID = BATTERY_LEVEL_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 0;
        final UUID originalDescriptorUUID = CHARACTERISTIC_PRESENTATION_FORMAT_DESCRIPTOR;
        final int originalTimeout = 4;
        final Bundle originalBundle = new Bundle();

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        BluetoothGattDescriptor bluetoothGattDescriptor = new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE);
        bluetoothGattCharacteristic.addDescriptor(bluetoothGattDescriptor);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onCharacteristicPresentationFormatReadTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, long timeout, @Nullable Bundle argument) {
                assertEquals(originalTaskId, taskId);
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertNotNull(index);
                assertEquals(0, index.intValue());
                assertEquals(originalTimeout, timeout);
                assertEquals(originalBundle, argument);
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onDescriptorReadTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalTimeout, originalBundle);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onDescriptorReadTimeout_00103() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ACCESS_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = BATTERY_LEVEL_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CHARACTERISTIC_PRESENTATION_FORMAT_DESCRIPTOR;
        final int originalTimeout = 4;
        final Bundle originalBundle = new Bundle();

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        BluetoothGattDescriptor bluetoothGattDescriptor = new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE);
        bluetoothGattCharacteristic.addDescriptor(bluetoothGattDescriptor);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onCharacteristicPresentationFormatReadTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, long timeout, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onDescriptorReadTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalTimeout, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorReadTimeout_00104() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = BATTERY_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = CURRENT_TIME_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CHARACTERISTIC_PRESENTATION_FORMAT_DESCRIPTOR;
        final int originalTimeout = 4;
        final Bundle originalBundle = new Bundle();

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        BluetoothGattDescriptor bluetoothGattDescriptor = new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE);
        bluetoothGattCharacteristic.addDescriptor(bluetoothGattDescriptor);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onCharacteristicPresentationFormatReadTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, long timeout, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onDescriptorReadTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalTimeout, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorReadTimeout_00105() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ACCESS_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = CURRENT_TIME_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CHARACTERISTIC_PRESENTATION_FORMAT_DESCRIPTOR;
        final int originalTimeout = 4;
        final Bundle originalBundle = new Bundle();

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        BluetoothGattDescriptor bluetoothGattDescriptor = new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE);
        bluetoothGattCharacteristic.addDescriptor(bluetoothGattDescriptor);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onCharacteristicPresentationFormatReadTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, long timeout, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onDescriptorReadTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalTimeout, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteSuccess_00001() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = BATTERY_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = BATTERY_LEVEL_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final byte[] originalValues = new byte[]{4, 5};
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 0);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onBatteryLevelNotifyStartSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, @Nullable Bundle argument) {
                assertEquals(originalTaskId, taskId);
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertNull(index);
                assertEquals(originalBundle, argument);
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDescriptorWriteSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalValues, originalBundle);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteSuccess_00002() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = BATTERY_SERVICE;
        final Integer originalServiceInstanceId = 0;
        final UUID originalCharacteristicUUID = BATTERY_LEVEL_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 0;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final byte[] originalValues = new byte[]{4, 5};
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 0);

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        BluetoothGattDescriptor bluetoothGattDescriptor = new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE);
        bluetoothGattCharacteristic.addDescriptor(bluetoothGattDescriptor);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onBatteryLevelNotifyStartSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, @Nullable Bundle argument) {
                assertEquals(originalTaskId, taskId);
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertNotNull(index);
                assertEquals(0, index.intValue());
                assertEquals(originalBundle, argument);
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onDescriptorWriteSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalValues, originalBundle);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteSuccess_00003() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ACCESS_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = BATTERY_LEVEL_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final byte[] originalValues = new byte[]{4, 5};
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 0);

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        BluetoothGattDescriptor bluetoothGattDescriptor = new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE);
        bluetoothGattCharacteristic.addDescriptor(bluetoothGattDescriptor);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onBatteryLevelNotifyStartSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onDescriptorWriteSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalValues, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteSuccess_00004() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = BATTERY_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = CURRENT_TIME_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final byte[] originalValues = new byte[]{4, 5};
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 0);

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        BluetoothGattDescriptor bluetoothGattDescriptor = new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE);
        bluetoothGattCharacteristic.addDescriptor(bluetoothGattDescriptor);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onBatteryLevelNotifyStartSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onDescriptorWriteSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalValues, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteSuccess_00005() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ACCESS_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = CURRENT_TIME_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final byte[] originalValues = new byte[]{4, 5};
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 0);

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        BluetoothGattDescriptor bluetoothGattDescriptor = new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE);
        bluetoothGattCharacteristic.addDescriptor(bluetoothGattDescriptor);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onBatteryLevelNotifyStartSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onDescriptorWriteSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalValues, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteSuccess_00101() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = BATTERY_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = BATTERY_LEVEL_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final byte[] originalValues = new byte[]{4, 5};
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 1);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onBatteryLevelNotifyStopSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, @Nullable Bundle argument) {
                assertEquals(originalTaskId, taskId);
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertNull(index);
                assertEquals(originalBundle, argument);
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDescriptorWriteSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalValues, originalBundle);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteSuccess_00102() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = BATTERY_SERVICE;
        final Integer originalServiceInstanceId = 0;
        final UUID originalCharacteristicUUID = BATTERY_LEVEL_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 0;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final byte[] originalValues = new byte[]{4, 5};
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 1);

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        BluetoothGattDescriptor bluetoothGattDescriptor = new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE);
        bluetoothGattCharacteristic.addDescriptor(bluetoothGattDescriptor);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onBatteryLevelNotifyStopSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, @Nullable Bundle argument) {
                assertEquals(originalTaskId, taskId);
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertNotNull(index);
                assertEquals(0, index.intValue());
                assertEquals(originalBundle, argument);
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onDescriptorWriteSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalValues, originalBundle);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteSuccess_00103() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ACCESS_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = BATTERY_LEVEL_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final byte[] originalValues = new byte[]{4, 5};
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 1);

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        BluetoothGattDescriptor bluetoothGattDescriptor = new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE);
        bluetoothGattCharacteristic.addDescriptor(bluetoothGattDescriptor);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onBatteryLevelNotifyStopSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onDescriptorWriteSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalValues, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteSuccess_00104() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = BATTERY_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = CURRENT_TIME_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final byte[] originalValues = new byte[]{4, 5};
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 1);

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        BluetoothGattDescriptor bluetoothGattDescriptor = new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE);
        bluetoothGattCharacteristic.addDescriptor(bluetoothGattDescriptor);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onBatteryLevelNotifyStopSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onDescriptorWriteSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalValues, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteSuccess_00105() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ACCESS_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = CURRENT_TIME_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final byte[] originalValues = new byte[]{4, 5};
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 1);

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        BluetoothGattDescriptor bluetoothGattDescriptor = new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE);
        bluetoothGattCharacteristic.addDescriptor(bluetoothGattDescriptor);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onBatteryLevelNotifyStopSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onDescriptorWriteSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalValues, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteSuccess_00201() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = BATTERY_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = BATTERY_LEVEL_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final byte[] originalValues = new byte[]{4, 5};
        final Bundle originalBundle = new Bundle();

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onBatteryLevelNotifyStartSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, @Nullable Bundle argument) {
                isCalled.set(true);
            }

            @Override
            public void onBatteryLevelNotifyStopSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDescriptorWriteSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalValues, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteSuccess_00202() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = BATTERY_SERVICE;
        final Integer originalServiceInstanceId = 0;
        final UUID originalCharacteristicUUID = BATTERY_LEVEL_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 0;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final byte[] originalValues = new byte[]{4, 5};
        final Bundle originalBundle = new Bundle();

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        BluetoothGattDescriptor bluetoothGattDescriptor = new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE);
        bluetoothGattCharacteristic.addDescriptor(bluetoothGattDescriptor);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onBatteryLevelNotifyStartSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, @Nullable Bundle argument) {
                isCalled.set(true);
            }

            @Override
            public void onBatteryLevelNotifyStopSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onDescriptorWriteSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalValues, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteSuccess_00203() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ACCESS_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = BATTERY_LEVEL_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final byte[] originalValues = new byte[]{4, 5};
        final Bundle originalBundle = new Bundle();

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        BluetoothGattDescriptor bluetoothGattDescriptor = new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE);
        bluetoothGattCharacteristic.addDescriptor(bluetoothGattDescriptor);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onBatteryLevelNotifyStartSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, @Nullable Bundle argument) {
                isCalled.set(true);
            }

            @Override
            public void onBatteryLevelNotifyStopSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onDescriptorWriteSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalValues, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteSuccess_00204() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = BATTERY_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = CURRENT_TIME_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final byte[] originalValues = new byte[]{4, 5};
        final Bundle originalBundle = new Bundle();

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        BluetoothGattDescriptor bluetoothGattDescriptor = new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE);
        bluetoothGattCharacteristic.addDescriptor(bluetoothGattDescriptor);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onBatteryLevelNotifyStartSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, @Nullable Bundle argument) {
                isCalled.set(true);
            }

            @Override
            public void onBatteryLevelNotifyStopSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onDescriptorWriteSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalValues, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteSuccess_00205() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ACCESS_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = CURRENT_TIME_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final byte[] originalValues = new byte[]{4, 5};
        final Bundle originalBundle = new Bundle();

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        BluetoothGattDescriptor bluetoothGattDescriptor = new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE);
        bluetoothGattCharacteristic.addDescriptor(bluetoothGattDescriptor);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onBatteryLevelNotifyStartSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, @Nullable Bundle argument) {
                isCalled.set(true);
            }

            @Override
            public void onBatteryLevelNotifyStopSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onDescriptorWriteSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalValues, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteFailed_00001() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = BATTERY_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = BATTERY_LEVEL_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 0);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onBatteryLevelNotifyStartFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, int status, @Nullable Bundle argument) {
                assertEquals(originalTaskId, taskId);
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertNull(index);
                assertEquals(originalStatus, status);
                assertEquals(originalBundle, argument);
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDescriptorWriteFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalStatus, originalBundle);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteFailed_00002() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = BATTERY_SERVICE;
        final Integer originalServiceInstanceId = 0;
        final UUID originalCharacteristicUUID = BATTERY_LEVEL_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 0;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 0);

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        BluetoothGattDescriptor bluetoothGattDescriptor = new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE);
        bluetoothGattCharacteristic.addDescriptor(bluetoothGattDescriptor);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onBatteryLevelNotifyStartFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, int status, @Nullable Bundle argument) {
                assertEquals(originalTaskId, taskId);
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertNotNull(index);
                assertEquals(0, index.intValue());
                assertEquals(originalStatus, status);
                assertEquals(originalBundle, argument);
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onDescriptorWriteFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalStatus, originalBundle);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteFailed_00003() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ACCESS_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = BATTERY_LEVEL_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 0);

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        BluetoothGattDescriptor bluetoothGattDescriptor = new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE);
        bluetoothGattCharacteristic.addDescriptor(bluetoothGattDescriptor);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onBatteryLevelNotifyStartFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, int status, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onDescriptorWriteFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalStatus, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteFailed_00004() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = BATTERY_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = CURRENT_TIME_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 0);

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        BluetoothGattDescriptor bluetoothGattDescriptor = new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE);
        bluetoothGattCharacteristic.addDescriptor(bluetoothGattDescriptor);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onBatteryLevelNotifyStartFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, int status, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onDescriptorWriteFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalStatus, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteFailed_00005() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ACCESS_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = CURRENT_TIME_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 0);

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        BluetoothGattDescriptor bluetoothGattDescriptor = new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE);
        bluetoothGattCharacteristic.addDescriptor(bluetoothGattDescriptor);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onBatteryLevelNotifyStartFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, int status, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onDescriptorWriteFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalStatus, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteFailed_00101() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = BATTERY_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = BATTERY_LEVEL_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 1);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onBatteryLevelNotifyStopFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, int status, @Nullable Bundle argument) {
                assertEquals(originalTaskId, taskId);
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertNull(index);
                assertEquals(originalStatus, status);
                assertEquals(originalBundle, argument);
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDescriptorWriteFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalStatus, originalBundle);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteFailed_00102() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = BATTERY_SERVICE;
        final Integer originalServiceInstanceId = 0;
        final UUID originalCharacteristicUUID = BATTERY_LEVEL_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 0;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 1);

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        BluetoothGattDescriptor bluetoothGattDescriptor = new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE);
        bluetoothGattCharacteristic.addDescriptor(bluetoothGattDescriptor);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onBatteryLevelNotifyStopFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, int status, @Nullable Bundle argument) {
                assertEquals(originalTaskId, taskId);
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertNotNull(index);
                assertEquals(0, index.intValue());
                assertEquals(originalStatus, status);
                assertEquals(originalBundle, argument);
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onDescriptorWriteFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalStatus, originalBundle);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteFailed_00103() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ACCESS_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = BATTERY_LEVEL_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 1);

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        BluetoothGattDescriptor bluetoothGattDescriptor = new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE);
        bluetoothGattCharacteristic.addDescriptor(bluetoothGattDescriptor);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onBatteryLevelNotifyStopFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, int status, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onDescriptorWriteFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalStatus, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteFailed_00104() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = BATTERY_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = CURRENT_TIME_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 1);

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        BluetoothGattDescriptor bluetoothGattDescriptor = new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE);
        bluetoothGattCharacteristic.addDescriptor(bluetoothGattDescriptor);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onBatteryLevelNotifyStopFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, int status, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onDescriptorWriteFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalStatus, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteFailed_00105() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ACCESS_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = CURRENT_TIME_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 1);

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        BluetoothGattDescriptor bluetoothGattDescriptor = new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE);
        bluetoothGattCharacteristic.addDescriptor(bluetoothGattDescriptor);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onBatteryLevelNotifyStopFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, int status, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onDescriptorWriteFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalStatus, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteFailed_00201() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = BATTERY_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = BATTERY_LEVEL_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onBatteryLevelNotifyStartFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, int status, @Nullable Bundle argument) {
                isCalled.set(true);
            }

            @Override
            public void onBatteryLevelNotifyStopFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, int status, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDescriptorWriteFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalStatus, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteFailed_00202() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = BATTERY_SERVICE;
        final Integer originalServiceInstanceId = 0;
        final UUID originalCharacteristicUUID = BATTERY_LEVEL_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 0;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        BluetoothGattDescriptor bluetoothGattDescriptor = new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE);
        bluetoothGattCharacteristic.addDescriptor(bluetoothGattDescriptor);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onBatteryLevelNotifyStartFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, int status, @Nullable Bundle argument) {
                isCalled.set(true);
            }

            @Override
            public void onBatteryLevelNotifyStopFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, int status, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onDescriptorWriteFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalStatus, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteFailed_00203() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ACCESS_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = BATTERY_LEVEL_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        BluetoothGattDescriptor bluetoothGattDescriptor = new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE);
        bluetoothGattCharacteristic.addDescriptor(bluetoothGattDescriptor);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onBatteryLevelNotifyStartFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, int status, @Nullable Bundle argument) {
                isCalled.set(true);
            }

            @Override
            public void onBatteryLevelNotifyStopFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, int status, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onDescriptorWriteFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalStatus, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteFailed_00204() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = BATTERY_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = CURRENT_TIME_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        BluetoothGattDescriptor bluetoothGattDescriptor = new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE);
        bluetoothGattCharacteristic.addDescriptor(bluetoothGattDescriptor);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onBatteryLevelNotifyStartFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, int status, @Nullable Bundle argument) {
                isCalled.set(true);
            }

            @Override
            public void onBatteryLevelNotifyStopFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, int status, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onDescriptorWriteFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalStatus, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteFailed_00205() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ACCESS_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = CURRENT_TIME_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        BluetoothGattDescriptor bluetoothGattDescriptor = new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE);
        bluetoothGattCharacteristic.addDescriptor(bluetoothGattDescriptor);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onBatteryLevelNotifyStartFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, int status, @Nullable Bundle argument) {
                isCalled.set(true);
            }

            @Override
            public void onBatteryLevelNotifyStopFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, int status, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onDescriptorWriteFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalStatus, originalBundle);

        assertFalse(isCalled.get());
    }


    @Test
    public void test_onDescriptorWriteTimeout_00001() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = BATTERY_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = BATTERY_LEVEL_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final int originalTimeout = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 0);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onBatteryLevelNotifyStartTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, long timeout, @Nullable Bundle argument) {
                assertEquals(originalTaskId, taskId);
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertNull(index);
                assertEquals(originalTimeout, timeout);
                assertEquals(originalBundle, argument);
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDescriptorWriteTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalTimeout, originalBundle);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteTimeout_00002() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = BATTERY_SERVICE;
        final Integer originalServiceInstanceId = 0;
        final UUID originalCharacteristicUUID = BATTERY_LEVEL_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 0;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final int originalTimeout = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 0);

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        BluetoothGattDescriptor bluetoothGattDescriptor = new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE);
        bluetoothGattCharacteristic.addDescriptor(bluetoothGattDescriptor);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onBatteryLevelNotifyStartTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, long timeout, @Nullable Bundle argument) {
                assertEquals(originalTaskId, taskId);
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertNotNull(index);
                assertEquals(0, index.intValue());
                assertEquals(originalTimeout, timeout);
                assertEquals(originalBundle, argument);
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onDescriptorWriteTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalTimeout, originalBundle);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteTimeout_00003() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ACCESS_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = BATTERY_LEVEL_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final int originalTimeout = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 0);

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        BluetoothGattDescriptor bluetoothGattDescriptor = new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE);
        bluetoothGattCharacteristic.addDescriptor(bluetoothGattDescriptor);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onBatteryLevelNotifyStartTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, long timeout, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onDescriptorWriteTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalTimeout, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteTimeout_00004() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = BATTERY_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = CURRENT_TIME_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final int originalTimeout = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 0);

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        BluetoothGattDescriptor bluetoothGattDescriptor = new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE);
        bluetoothGattCharacteristic.addDescriptor(bluetoothGattDescriptor);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onBatteryLevelNotifyStartTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, long timeout, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onDescriptorWriteTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalTimeout, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteTimeout_00005() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ACCESS_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = CURRENT_TIME_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final int originalTimeout = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 0);

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        BluetoothGattDescriptor bluetoothGattDescriptor = new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE);
        bluetoothGattCharacteristic.addDescriptor(bluetoothGattDescriptor);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onBatteryLevelNotifyStartTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, long timeout, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onDescriptorWriteTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalTimeout, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteTimeout_00101() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = BATTERY_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = BATTERY_LEVEL_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final int originalTimeout = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 1);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onBatteryLevelNotifyStopTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, long timeout, @Nullable Bundle argument) {
                assertEquals(originalTaskId, taskId);
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertNull(index);
                assertEquals(originalTimeout, timeout);
                assertEquals(originalBundle, argument);
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDescriptorWriteTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalTimeout, originalBundle);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteTimeout_00102() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = BATTERY_SERVICE;
        final Integer originalServiceInstanceId = 0;
        final UUID originalCharacteristicUUID = BATTERY_LEVEL_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 0;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final int originalTimeout = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 1);

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        BluetoothGattDescriptor bluetoothGattDescriptor = new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE);
        bluetoothGattCharacteristic.addDescriptor(bluetoothGattDescriptor);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onBatteryLevelNotifyStopTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, long timeout, @Nullable Bundle argument) {
                assertEquals(originalTaskId, taskId);
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertNotNull(index);
                assertEquals(0, index.intValue());
                assertEquals(originalTimeout, timeout);
                assertEquals(originalBundle, argument);
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onDescriptorWriteTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalTimeout, originalBundle);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteTimeout_00103() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ACCESS_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = BATTERY_LEVEL_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final int originalTimeout = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 1);

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        BluetoothGattDescriptor bluetoothGattDescriptor = new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE);
        bluetoothGattCharacteristic.addDescriptor(bluetoothGattDescriptor);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onBatteryLevelNotifyStopTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, long timeout, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onDescriptorWriteTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalTimeout, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteTimeout_00104() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = BATTERY_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = CURRENT_TIME_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final int originalTimeout = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 1);

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        BluetoothGattDescriptor bluetoothGattDescriptor = new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE);
        bluetoothGattCharacteristic.addDescriptor(bluetoothGattDescriptor);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onBatteryLevelNotifyStopTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, long timeout, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onDescriptorWriteTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalTimeout, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteTimeout_00105() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ACCESS_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = CURRENT_TIME_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final int originalTimeout = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 1);

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        BluetoothGattDescriptor bluetoothGattDescriptor = new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE);
        bluetoothGattCharacteristic.addDescriptor(bluetoothGattDescriptor);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onBatteryLevelNotifyStopTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, long timeout, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onDescriptorWriteTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalTimeout, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteTimeout_00201() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = BATTERY_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = BATTERY_LEVEL_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final int originalTimeout = 4;
        final Bundle originalBundle = new Bundle();

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onBatteryLevelNotifyStartTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, long timeout, @Nullable Bundle argument) {
                isCalled.set(true);
            }

            @Override
            public void onBatteryLevelNotifyStopTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, long timeout, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDescriptorWriteTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalTimeout, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteTimeout_00202() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = BATTERY_SERVICE;
        final Integer originalServiceInstanceId = 0;
        final UUID originalCharacteristicUUID = BATTERY_LEVEL_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 0;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final int originalTimeout = 4;
        final Bundle originalBundle = new Bundle();

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        BluetoothGattDescriptor bluetoothGattDescriptor = new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE);
        bluetoothGattCharacteristic.addDescriptor(bluetoothGattDescriptor);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onBatteryLevelNotifyStartTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, long timeout, @Nullable Bundle argument) {
                isCalled.set(true);
            }

            @Override
            public void onBatteryLevelNotifyStopTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, long timeout, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onDescriptorWriteTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalTimeout, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteTimeout_00203() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ACCESS_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = BATTERY_LEVEL_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final int originalTimeout = 4;
        final Bundle originalBundle = new Bundle();

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        BluetoothGattDescriptor bluetoothGattDescriptor = new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE);
        bluetoothGattCharacteristic.addDescriptor(bluetoothGattDescriptor);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onBatteryLevelNotifyStartTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, long timeout, @Nullable Bundle argument) {
                isCalled.set(true);
            }

            @Override
            public void onBatteryLevelNotifyStopTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, long timeout, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onDescriptorWriteTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalTimeout, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteTimeout_00204() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = BATTERY_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = CURRENT_TIME_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final int originalTimeout = 4;
        final Bundle originalBundle = new Bundle();

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        BluetoothGattDescriptor bluetoothGattDescriptor = new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE);
        bluetoothGattCharacteristic.addDescriptor(bluetoothGattDescriptor);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onBatteryLevelNotifyStartTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, long timeout, @Nullable Bundle argument) {
                isCalled.set(true);
            }

            @Override
            public void onBatteryLevelNotifyStopTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, long timeout, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onDescriptorWriteTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalTimeout, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteTimeout_00205() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ACCESS_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = CURRENT_TIME_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final int originalTimeout = 4;
        final Bundle originalBundle = new Bundle();

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        BluetoothGattDescriptor bluetoothGattDescriptor = new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE);
        bluetoothGattCharacteristic.addDescriptor(bluetoothGattDescriptor);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onBatteryLevelNotifyStartTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, long timeout, @Nullable Bundle argument) {
                isCalled.set(true);
            }

            @Override
            public void onBatteryLevelNotifyStopTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Integer index, long timeout, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onDescriptorWriteTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalTimeout, originalBundle);

        assertFalse(isCalled.get());
    }


    @Test
    public void test_onCharacteristicNotified_000001() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = BATTERY_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = BATTERY_LEVEL_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final byte[] originalValues = new byte[]{4};

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onBatteryLevelNotified(@NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @NonNull Integer serviceInstanceId, @NonNull UUID characteristicUUID, @NonNull Integer characteristicInstanceId, @Nullable Integer index, @NonNull BatteryLevelAndroid batteryLevelAndroid) {
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertNull(index);
                assertArrayEquals(originalValues, batteryLevelAndroid.getBytes());
                isCalled.set(true);
            }

        };

        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onCharacteristicNotified(originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalValues);
        assertTrue(isCalled.get());
    }

    @Test
    public void test_onCharacteristicNotified_000002() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = BATTERY_SERVICE;
        final Integer originalServiceInstanceId = 0;
        final UUID originalCharacteristicUUID = BATTERY_LEVEL_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 0;
        final byte[] originalValues = new byte[]{4};

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onBatteryLevelNotified(@NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @NonNull Integer serviceInstanceId, @NonNull UUID characteristicUUID, @NonNull Integer characteristicInstanceId, @Nullable Integer index, @NonNull BatteryLevelAndroid batteryLevelAndroid) {
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertNotNull(index);
                assertEquals(0, index.intValue());
                assertArrayEquals(originalValues, batteryLevelAndroid.getBytes());
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onCharacteristicNotified(originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalValues);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onCharacteristicNotified_000003() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ACCESS_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = BATTERY_LEVEL_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final byte[] originalValues = new byte[]{4};

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onBatteryLevelNotified(@NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @NonNull Integer serviceInstanceId, @NonNull UUID characteristicUUID, @NonNull Integer characteristicInstanceId, @Nullable Integer index, @NonNull BatteryLevelAndroid batteryLevelAndroid) {
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);

        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onCharacteristicNotified(originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalValues);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onCharacteristicNotified_000004() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = BATTERY_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = CURRENT_TIME_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final byte[] originalValues = new byte[]{4};

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onBatteryLevelNotified(@NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @NonNull Integer serviceInstanceId, @NonNull UUID characteristicUUID, @NonNull Integer characteristicInstanceId, @Nullable Integer index, @NonNull BatteryLevelAndroid batteryLevelAndroid) {
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);

        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onCharacteristicNotified(originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalValues);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onCharacteristicNotified_000005() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ACCESS_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = CURRENT_TIME_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final byte[] originalValues = new byte[]{4};

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockBatteryServiceCallback mockBatteryServiceCallback = new MockBatteryServiceCallback() {

            @Override
            public void onBatteryLevelNotified(@NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @NonNull Integer serviceInstanceId, @NonNull UUID characteristicUUID, @NonNull Integer characteristicInstanceId, @Nullable Integer index, @NonNull BatteryLevelAndroid batteryLevelAndroid) {
                isCalled.set(true);
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, mockBatteryServiceCallback, null);

        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        batteryService.onCharacteristicNotified(originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalValues);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_getCount_000001() {
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        BatteryService batteryService = new BatteryService(mockBLEConnection, new MockBatteryServiceCallback(), null);

        assertNull(batteryService.getCount());
    }

    @Test
    public void test_getCount_000002() {
        BatteryService batteryService = new BatteryService(new MockBLEConnection(), new MockBatteryServiceCallback(), null) {

            @Override
            public boolean isStarted() {
                return true;
            }
        };

        assertNotNull(batteryService.getCount());
        assertEquals(0, batteryService.getCount().intValue());
    }

    @Test
    public void test_getCount_000003() {
        BatteryService batteryService = new BatteryService(new MockBLEConnection(), new MockBatteryServiceCallback(), null) {

            @Override
            public boolean isStarted() {
                return true;
            }
        };

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);

        assertNotNull(batteryService.getCount());
        assertEquals(1, batteryService.getCount().intValue());
    }

    @Test
    public void test_getCount_000004() {
        BatteryService batteryService = new BatteryService(new MockBLEConnection(), new MockBatteryServiceCallback(), null) {

            @Override
            public boolean isStarted() {
                return true;
            }
        };

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Arrays.asList(bluetoothGattService, bluetoothGattService), null);

        assertNotNull(batteryService.getCount());
        assertEquals(2, batteryService.getCount().intValue());
    }

    @Test
    public void test_getBatteryLevel_000001() {
        final AtomicInteger originalIndex = new AtomicInteger(-1);
        BatteryService batteryService = new BatteryService(new MockBLEConnection(), new MockBatteryServiceCallback(), null) {

            @Nullable
            @Override
            public synchronized Integer getBatteryLevel(int index) {
                originalIndex.set(index);
                return super.getBatteryLevel(index);
            }
        };

        assertNull(batteryService.getBatteryLevel());
        assertEquals(0, originalIndex.get());
    }

    @Test
    public void test_getBatteryLevel_000002() {
        final int originalLevel = 1;
        BatteryService batteryService = new BatteryService(new MockBLEConnection(), new MockBatteryServiceCallback(), null) {

            @Override
            public synchronized Integer getBatteryLevel(int index) {
                return originalLevel;
            }

        };

        assertNotNull(batteryService.getBatteryLevel());
        assertEquals(originalLevel, batteryService.getBatteryLevel().intValue());
    }

    @Test
    public void test_getBatteryLevel_000101() {
        BatteryService batteryService = new BatteryService(new MockBLEConnection(), new MockBatteryServiceCallback(), null);

        assertNull(batteryService.getBatteryLevel(0));
    }

    @Test
    public void test_getBatteryLevel_000102() {
        BatteryService batteryService = new BatteryService(new MockBLEConnection(), new MockBatteryServiceCallback(), null) {

            @Override
            public boolean isStarted() {
                return true;
            }
        };

        assertNull(batteryService.getBatteryLevel(-1));
    }

    @Test
    public void test_getBatteryLevel_000103() {
        final int originalTaskId = 1;
        MockBLEConnection mockBLEConnection = new MockBLEConnection() {

            @Override
            public synchronized Integer createReadCharacteristicTask(@NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, long timeout, @Nullable Bundle argument, @Nullable BLECallback bleCallback) {
                return originalTaskId;
            }
        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, new MockBatteryServiceCallback(), null) {

            @Override
            public boolean isStarted() {
                return true;
            }
        };

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Arrays.asList(bluetoothGattService, bluetoothGattService), null);

        Integer taskId = batteryService.getBatteryLevel(0);
        assertNotNull(taskId);
        assertEquals(originalTaskId, taskId.intValue());
    }

    @Test
    public void test_isNotificatable_000001() {
        final AtomicInteger originalIndex = new AtomicInteger(-1);
        BatteryService batteryService = new BatteryService(new MockBLEConnection(), new MockBatteryServiceCallback(), null) {

            @Override
            public synchronized boolean isNotificatable(int index) {
                originalIndex.set(index);
                return super.isNotificatable(index);
            }

        };

        assertFalse(batteryService.isNotificatable());
        assertEquals(0, originalIndex.get());
    }

    @Test
    public void test_isNotificatable_000002() {
        final boolean originaNotificatable = true;
        BatteryService batteryService = new BatteryService(new MockBLEConnection(), new MockBatteryServiceCallback(), null) {

            @Override
            public synchronized boolean isNotificatable(int index) {
                return originaNotificatable;
            }

        };

        assertEquals(originaNotificatable, batteryService.isNotificatable());
    }

    @Test
    public void test_isNotificatable_000101() {
        BatteryService batteryService = new BatteryService(new MockBLEConnection(), new MockBatteryServiceCallback(), null);

        assertFalse(batteryService.isNotificatable(0));
    }

    @Test
    public void test_isNotificatable_000102() {
        BatteryService batteryService = new BatteryService(new MockBLEConnection(), new MockBatteryServiceCallback(), null) {

            @Override
            public boolean isStarted() {
                return true;
            }
        };

        assertFalse(batteryService.isNotificatable(-1));
    }

    @Test
    public void test_isNotificatable_000103() {
        BatteryService batteryService = new BatteryService(new MockBLEConnection(), new MockBatteryServiceCallback(), null) {

            @Override
            public boolean isStarted() {
                return true;
            }
        };

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ, BluetoothGattCharacteristic.PERMISSION_READ);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        assertFalse(batteryService.isNotificatable(0));
    }

    @Test
    public void test_isNotificatable_000104() {
        BatteryService batteryService = new BatteryService(new MockBLEConnection(), new MockBatteryServiceCallback(), null) {

            @Override
            public boolean isStarted() {
                return true;
            }
        };

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);

        assertFalse(batteryService.isNotificatable(0));
    }

    @Test
    public void test_isNotificatable_000105() {
        BatteryService batteryService = new BatteryService(new MockBLEConnection(), new MockBatteryServiceCallback(), null) {

            @Override
            public boolean isStarted() {
                return true;
            }
        };

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ, BluetoothGattCharacteristic.PERMISSION_READ);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);

        assertFalse(batteryService.isNotificatable(0));
    }

    @Test
    public void test_isNotificatable_000106() {
        BatteryService batteryService = new BatteryService(new MockBLEConnection(), new MockBatteryServiceCallback(), null) {

            @Override
            public boolean isStarted() {
                return true;
            }
        };

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);

        assertFalse(batteryService.isNotificatable(0));
    }

    @Test
    public void test_isNotificatable_000107() {
        BatteryService batteryService = new BatteryService(new MockBLEConnection(), new MockBatteryServiceCallback(), null) {

            @Override
            public boolean isStarted() {
                return true;
            }
        };

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        bluetoothGattCharacteristic.addDescriptor(new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE));
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);

        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);

        assertTrue(batteryService.isNotificatable(0));
    }

    @Test
    public void test_getCharacteristicPresentationFormat_000001() {
        BatteryService batteryService = new BatteryService(new MockBLEConnection(), new MockBatteryServiceCallback(), null);

        assertNull(batteryService.getCharacteristicPresentationFormat());
    }

    @Test
    public void test_getCharacteristicPresentationFormat_000002() {
        final int originalTaskId = 1;

        BatteryService batteryService = new BatteryService(new MockBLEConnection(), new MockBatteryServiceCallback(), null) {

            @Override
            public synchronized Integer getCharacteristicPresentationFormat(int index) {
                return originalTaskId;
            }
        };

        assertNotNull(batteryService.getCharacteristicPresentationFormat());
        assertEquals(originalTaskId, batteryService.getCharacteristicPresentationFormat().intValue());
    }

    @Test
    public void test_getCharacteristicPresentationFormat_00101() {
        BatteryService batteryService = new BatteryService(new MockBLEConnection(), new MockBatteryServiceCallback(), null) {

            @Override
            public boolean isStarted() {
                return true;
            }
        };

        assertNull(batteryService.getCharacteristicPresentationFormat(0));
    }

    @Test
    public void test_getCharacteristicPresentationFormat_00102() {
        BatteryService batteryService = new BatteryService(new MockBLEConnection(), new MockBatteryServiceCallback(), null) {

            @Override
            public boolean isStarted() {
                return true;
            }
        };

        assertNull(batteryService.getCharacteristicPresentationFormat(-1));
    }

    @Test
    public void test_getCharacteristicPresentationFormat_00103() {
        BatteryService batteryService = new BatteryService(new MockBLEConnection(), new MockBatteryServiceCallback(), null) {

            @Override
            public boolean isStarted() {
                return true;
            }
        };

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ, BluetoothGattCharacteristic.PERMISSION_READ);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);

        assertNull(batteryService.getCharacteristicPresentationFormat(0));
    }

    @Test
    public void test_getCharacteristicPresentationFormat_00104() {
        final int originalTaskId = 1;
        MockBLEConnection mockBLEConnection = new MockBLEConnection() {

            @Override
            public synchronized Integer createReadDescriptorTask(@NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @NonNull UUID descriptorUUID, long timeout, @Nullable Bundle argument, @Nullable BLECallback bleCallback) {
                return originalTaskId;
            }
        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, new MockBatteryServiceCallback(), null) {

            @Override
            public boolean isStarted() {
                return true;
            }
        };

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ, BluetoothGattCharacteristic.PERMISSION_READ);
        bluetoothGattCharacteristic.addDescriptor(new BluetoothGattDescriptor(CHARACTERISTIC_PRESENTATION_FORMAT_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ));
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);

        Integer taskId = batteryService.getCharacteristicPresentationFormat(0);
        assertNotNull(taskId);
        assertEquals(originalTaskId, taskId.intValue());
    }

    @Test
    public void test_getClientCharacteristicConfiguration_000001() {
        BatteryService batteryService = new BatteryService(new MockBLEConnection(), new MockBatteryServiceCallback(), null);

        assertNull(batteryService.getClientCharacteristicConfiguration());
    }

    @Test
    public void test_getClientCharacteristicConfiguration_000002() {
        final int originalTaskId = 1;

        BatteryService batteryService = new BatteryService(new MockBLEConnection(), new MockBatteryServiceCallback(), null) {

            @Override
            public synchronized Integer getClientCharacteristicConfiguration(int index) {
                return originalTaskId;
            }
        };

        assertNotNull(batteryService.getClientCharacteristicConfiguration());
        assertEquals(originalTaskId, batteryService.getClientCharacteristicConfiguration().intValue());
    }

    @Test
    public void test_getClientCharacteristicConfiguration_00101() {
        BatteryService batteryService = new BatteryService(new MockBLEConnection(), new MockBatteryServiceCallback(), null) {

            @Override
            public boolean isStarted() {
                return true;
            }
        };

        assertNull(batteryService.getClientCharacteristicConfiguration(0));
    }

    @Test
    public void test_getClientCharacteristicConfiguration_00102() {
        BatteryService batteryService = new BatteryService(new MockBLEConnection(), new MockBatteryServiceCallback(), null) {

            @Override
            public boolean isStarted() {
                return true;
            }
        };

        assertNull(batteryService.getClientCharacteristicConfiguration(-1));
    }

    @Test
    public void test_getClientCharacteristicConfiguration_00103() {
        BatteryService batteryService = new BatteryService(new MockBLEConnection(), new MockBatteryServiceCallback(), null) {

            @Override
            public boolean isStarted() {
                return true;
            }
        };

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ, BluetoothGattCharacteristic.PERMISSION_READ);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);

        assertNull(batteryService.getClientCharacteristicConfiguration(0));
    }

    @Test
    public void test_getClientCharacteristicConfiguration_00104() {
        final int originalTaskId = 1;
        MockBLEConnection mockBLEConnection = new MockBLEConnection() {

            @Override
            public synchronized Integer createReadDescriptorTask(@NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @NonNull UUID descriptorUUID, long timeout, @Nullable Bundle argument, @Nullable BLECallback bleCallback) {
                return originalTaskId;
            }
        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, new MockBatteryServiceCallback(), null) {

            @Override
            public boolean isStarted() {
                return true;
            }
        };

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        bluetoothGattCharacteristic.addDescriptor(new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ));
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);

        Integer taskId = batteryService.getClientCharacteristicConfiguration(0);
        assertNotNull(taskId);
        assertEquals(originalTaskId, taskId.intValue());
    }

    @Test
    public void test_startNotification_00001() {
        BatteryService batteryService = new BatteryService(new MockBLEConnection(), new MockBatteryServiceCallback(), null);

        assertNull(batteryService.startNotification());
    }

    @Test
    public void test_startNotification_00002() {
        final int originalTaskId = 1;

        BatteryService batteryService = new BatteryService(new MockBLEConnection(), new MockBatteryServiceCallback(), null) {

            @Override
            public synchronized Integer startNotification(int index) {
                return originalTaskId;
            }
        };

        Integer taskId = batteryService.startNotification();
        assertNotNull(taskId);
        assertEquals(originalTaskId, taskId.intValue());
    }

    @Test
    public void test_startNotification_00101() {
        BatteryService batteryService = new BatteryService(new MockBLEConnection(), new MockBatteryServiceCallback(), null) {

            @Override
            public boolean isStarted() {
                return true;
            }
        };

        assertNull(batteryService.startNotification(0));
    }

    @Test
    public void test_startNotification_00102() {
        BatteryService batteryService = new BatteryService(new MockBLEConnection(), new MockBatteryServiceCallback(), null) {

            @Override
            public boolean isStarted() {
                return true;
            }
        };

        assertNull(batteryService.startNotification(-1));
    }

    @Test
    public void test_startNotification_00103() {
        BatteryService batteryService = new BatteryService(new MockBLEConnection(), new MockBatteryServiceCallback(), null) {

            @Override
            public boolean isStarted() {
                return true;
            }
        };

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ, BluetoothGattCharacteristic.PERMISSION_READ);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);

        assertNull(batteryService.startNotification(0));
    }

    @Test
    public void test_startNotification_00104() {
        final int originalTaskId = 1;
        MockBLEConnection mockBLEConnection = new MockBLEConnection() {

            @Override
            public synchronized Integer createWriteDescriptorTask(@NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @NonNull UUID descriptorUUID, @NonNull ByteArrayInterface byteArrayInterface, long timeout, @Nullable Bundle argument, @Nullable BLECallback bleCallback) {
                return originalTaskId;
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, new MockBatteryServiceCallback(), null) {

            @Override
            public boolean isStarted() {
                return true;
            }
        };

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        bluetoothGattCharacteristic.addDescriptor(new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE));
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);

        Integer taskId = batteryService.startNotification(0);
        assertNotNull(taskId);
        assertEquals(originalTaskId, taskId.intValue());
    }

    @Test
    public void test_stopNotification_00001() {
        BatteryService batteryService = new BatteryService(new MockBLEConnection(), new MockBatteryServiceCallback(), null);

        assertNull(batteryService.stopNotification());
    }

    @Test
    public void test_stopNotification_00002() {
        final int originalTaskId = 1;

        BatteryService batteryService = new BatteryService(new MockBLEConnection(), new MockBatteryServiceCallback(), null) {

            @Override
            public synchronized Integer stopNotification(int index) {
                return originalTaskId;
            }
        };

        Integer taskId = batteryService.stopNotification();
        assertNotNull(taskId);
        assertEquals(originalTaskId, taskId.intValue());
    }

    @Test
    public void test_stopNotification_00101() {
        BatteryService batteryService = new BatteryService(new MockBLEConnection(), new MockBatteryServiceCallback(), null) {

            @Override
            public boolean isStarted() {
                return true;
            }
        };

        assertNull(batteryService.stopNotification(0));
    }

    @Test
    public void test_stopNotification_00102() {
        BatteryService batteryService = new BatteryService(new MockBLEConnection(), new MockBatteryServiceCallback(), null) {

            @Override
            public boolean isStarted() {
                return true;
            }
        };

        assertNull(batteryService.stopNotification(-1));
    }

    @Test
    public void test_stopNotification_00103() {
        BatteryService batteryService = new BatteryService(new MockBLEConnection(), new MockBatteryServiceCallback(), null) {

            @Override
            public boolean isStarted() {
                return true;
            }
        };

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ, BluetoothGattCharacteristic.PERMISSION_READ);
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);

        assertNull(batteryService.stopNotification(0));
    }

    @Test
    public void test_stopNotification_00104() {
        final int originalTaskId = 1;
        MockBLEConnection mockBLEConnection = new MockBLEConnection() {

            @Override
            public synchronized Integer createWriteDescriptorTask(@NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @NonNull UUID descriptorUUID, @NonNull ByteArrayInterface byteArrayInterface, long timeout, @Nullable Bundle argument, @Nullable BLECallback bleCallback) {
                return originalTaskId;
            }

        };
        BatteryService batteryService = new BatteryService(mockBLEConnection, new MockBatteryServiceCallback(), null) {

            @Override
            public boolean isStarted() {
                return true;
            }
        };

        BluetoothGattService bluetoothGattService = new BluetoothGattService(BATTERY_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BATTERY_LEVEL_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        bluetoothGattCharacteristic.addDescriptor(new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE));
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);
        batteryService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);

        Integer taskId = batteryService.stopNotification(0);
        assertNotNull(taskId);
        assertEquals(originalTaskId, taskId.intValue());
    }

}
