package org.im97mori.ble.service.lns.central;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.im97mori.ble.BLECallback;
import org.im97mori.ble.ByteArrayInterface;
import org.im97mori.ble.characteristic.u2a67.LocationAndSpeedAndroid;
import org.im97mori.ble.characteristic.u2a68.NavigationAndroid;
import org.im97mori.ble.characteristic.u2a69.PositionQualityAndroid;
import org.im97mori.ble.characteristic.u2a6a.LNFeatureAndroid;
import org.im97mori.ble.characteristic.u2a6b.LNControlPoint;
import org.im97mori.ble.characteristic.u2a6b.LNControlPointAndroid;
import org.im97mori.ble.descriptor.u2902.ClientCharacteristicConfigurationAndroid;
import org.junit.Test;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.im97mori.ble.BLEConstants.CharacteristicUUID.LN_CONTROL_POINT_CHARACTERISTIC;
import static org.im97mori.ble.BLEConstants.CharacteristicUUID.LN_FEATURE_CHARACTERISTIC;
import static org.im97mori.ble.BLEConstants.CharacteristicUUID.LOCATION_AND_SPEED_CHARACTERISTIC;
import static org.im97mori.ble.BLEConstants.CharacteristicUUID.NAVIGATION_CHARACTERISTIC;
import static org.im97mori.ble.BLEConstants.CharacteristicUUID.POSITION_QUALITY_CHARACTERISTIC;
import static org.im97mori.ble.BLEConstants.DescriptorUUID.CHARACTERISTIC_PRESENTATION_FORMAT_DESCRIPTOR;
import static org.im97mori.ble.BLEConstants.DescriptorUUID.CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
import static org.im97mori.ble.BLEConstants.DescriptorUUID.ENVIRONMENTAL_SENSING_CONFIGURATION_DESCRIPTOR;
import static org.im97mori.ble.BLEConstants.ServiceUUID.GENERIC_ACCESS_SERVICE;
import static org.im97mori.ble.BLEConstants.ServiceUUID.GENERIC_ATTRIBUTE_SERVICE;
import static org.im97mori.ble.BLEConstants.ServiceUUID.LOCATION_AND_NAVIGATION_SERVICE;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("UnnecessaryLocalVariable")
public class LocationAndNavigationServiceTest {

    @Test
    public void test_onBLEDisconnected_00001() {
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, new MockLocationAndNavigationServiceCallback(), null);
        BluetoothGattService bluetoothGattService = new BluetoothGattService(LOCATION_AND_NAVIGATION_SERVICE, 0);
        bluetoothGattService.addCharacteristic(new BluetoothGattCharacteristic(POSITION_QUALITY_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ, BluetoothGattCharacteristic.PERMISSION_READ));
        locationAndNavigationService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        locationAndNavigationService.onBLEDisconnected(1, MockBLEConnection.MOCK_DEVICE, 0, null);

        assertFalse(locationAndNavigationService.isPositionQualityCharacteristicSupporeted());
    }

    @Test
    public void test_onBLEDisconnected_00101() {
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, new MockLocationAndNavigationServiceCallback(), null);
        BluetoothGattService bluetoothGattService = new BluetoothGattService(LOCATION_AND_NAVIGATION_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(LN_CONTROL_POINT_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_INDICATE, 0);
        bluetoothGattCharacteristic.addDescriptor(new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, 0));
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);
        locationAndNavigationService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        locationAndNavigationService.onBLEDisconnected(1, MockBLEConnection.MOCK_DEVICE, 0, null);

        assertFalse(locationAndNavigationService.isLNControlPointCharacteristicSupporeted());
    }

    @Test
    public void test_onBLEDisconnected_00201() {
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, new MockLocationAndNavigationServiceCallback(), null);
        BluetoothGattService bluetoothGattService = new BluetoothGattService(LOCATION_AND_NAVIGATION_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(NAVIGATION_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_NOTIFY, 0);
        bluetoothGattCharacteristic.addDescriptor(new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, 0));
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);
        locationAndNavigationService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        locationAndNavigationService.onBLEDisconnected(1, MockBLEConnection.MOCK_DEVICE, 0, null);

        assertFalse(locationAndNavigationService.isNavigationCharacteristicSupporeted());
    }

    @Test
    public void test_onDiscoverServiceSuccess_00001() {
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, new MockLocationAndNavigationServiceCallback(), null);
        locationAndNavigationService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.<BluetoothGattService>emptyList(), null);

        assertFalse(locationAndNavigationService.isPositionQualityCharacteristicSupporeted());
    }

    @Test
    public void test_onDiscoverServiceSuccess_00002() {
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, new MockLocationAndNavigationServiceCallback(), null);
        BluetoothGattService bluetoothGattService = new BluetoothGattService(GENERIC_ACCESS_SERVICE, 0);
        locationAndNavigationService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);

        assertFalse(locationAndNavigationService.isPositionQualityCharacteristicSupporeted());
    }

    @Test
    public void test_onDiscoverServiceSuccess_00003() {
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, new MockLocationAndNavigationServiceCallback(), null);
        BluetoothGattService bluetoothGattService = new BluetoothGattService(LOCATION_AND_NAVIGATION_SERVICE, 0);
        locationAndNavigationService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);

        assertFalse(locationAndNavigationService.isPositionQualityCharacteristicSupporeted());
    }

    @Test
    public void test_onDiscoverServiceSuccess_00004() {
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, new MockLocationAndNavigationServiceCallback(), null);
        BluetoothGattService bluetoothGattService = new BluetoothGattService(LOCATION_AND_NAVIGATION_SERVICE, 0);
        bluetoothGattService.addCharacteristic(new BluetoothGattCharacteristic(POSITION_QUALITY_CHARACTERISTIC, 0, 0));
        locationAndNavigationService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);

        assertFalse(locationAndNavigationService.isPositionQualityCharacteristicSupporeted());
    }

    @Test
    public void test_onDiscoverServiceSuccess_00005() {
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, new MockLocationAndNavigationServiceCallback(), null);
        BluetoothGattService bluetoothGattService = new BluetoothGattService(LOCATION_AND_NAVIGATION_SERVICE, 0);
        bluetoothGattService.addCharacteristic(new BluetoothGattCharacteristic(POSITION_QUALITY_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ, 0));
        locationAndNavigationService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);

        assertTrue(locationAndNavigationService.isPositionQualityCharacteristicSupporeted());
    }

    @Test
    public void test_onDiscoverServiceSuccess_00006() {
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, new MockLocationAndNavigationServiceCallback(), null);
        BluetoothGattService bluetoothGattService = new BluetoothGattService(LOCATION_AND_NAVIGATION_SERVICE, 0);
        bluetoothGattService.addCharacteristic(new BluetoothGattCharacteristic(POSITION_QUALITY_CHARACTERISTIC, 0, BluetoothGattCharacteristic.PERMISSION_READ));
        locationAndNavigationService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);

        assertFalse(locationAndNavigationService.isPositionQualityCharacteristicSupporeted());
    }

    @Test
    public void test_onDiscoverServiceSuccess_00007() {
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, new MockLocationAndNavigationServiceCallback(), null);
        BluetoothGattService bluetoothGattService = new BluetoothGattService(LOCATION_AND_NAVIGATION_SERVICE, 0);
        bluetoothGattService.addCharacteristic(new BluetoothGattCharacteristic(POSITION_QUALITY_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ, BluetoothGattCharacteristic.PERMISSION_READ));
        locationAndNavigationService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);

        assertTrue(locationAndNavigationService.isPositionQualityCharacteristicSupporeted());
    }

    @Test
    public void test_onDiscoverServiceSuccess_00101() {
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, new MockLocationAndNavigationServiceCallback(), null);
        locationAndNavigationService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.<BluetoothGattService>emptyList(), null);

        assertFalse(locationAndNavigationService.isLNControlPointCharacteristicSupporeted());
    }

    @Test
    public void test_onDiscoverServiceSuccess_00102() {
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, new MockLocationAndNavigationServiceCallback(), null);
        BluetoothGattService bluetoothGattService = new BluetoothGattService(GENERIC_ACCESS_SERVICE, 0);
        locationAndNavigationService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);

        assertFalse(locationAndNavigationService.isLNControlPointCharacteristicSupporeted());
    }

    @Test
    public void test_onDiscoverServiceSuccess_00103() {
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, new MockLocationAndNavigationServiceCallback(), null);
        BluetoothGattService bluetoothGattService = new BluetoothGattService(LOCATION_AND_NAVIGATION_SERVICE, 0);
        locationAndNavigationService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);

        assertFalse(locationAndNavigationService.isLNControlPointCharacteristicSupporeted());
    }

    @Test
    public void test_onDiscoverServiceSuccess_00104() {
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, new MockLocationAndNavigationServiceCallback(), null);
        BluetoothGattService bluetoothGattService = new BluetoothGattService(LOCATION_AND_NAVIGATION_SERVICE, 0);
        bluetoothGattService.addCharacteristic(new BluetoothGattCharacteristic(LN_CONTROL_POINT_CHARACTERISTIC, 0, 0));
        locationAndNavigationService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);

        assertFalse(locationAndNavigationService.isLNControlPointCharacteristicSupporeted());
    }

    @Test
    public void test_onDiscoverServiceSuccess_00105() {
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, new MockLocationAndNavigationServiceCallback(), null);
        BluetoothGattService bluetoothGattService = new BluetoothGattService(LOCATION_AND_NAVIGATION_SERVICE, 0);
        bluetoothGattService.addCharacteristic(new BluetoothGattCharacteristic(LN_CONTROL_POINT_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_INDICATE, 0));
        locationAndNavigationService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);

        assertFalse(locationAndNavigationService.isLNControlPointCharacteristicSupporeted());
    }

    @Test
    public void test_onDiscoverServiceSuccess_00106() {
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, new MockLocationAndNavigationServiceCallback(), null);
        BluetoothGattService bluetoothGattService = new BluetoothGattService(LOCATION_AND_NAVIGATION_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(LN_CONTROL_POINT_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_INDICATE, 0);
        bluetoothGattCharacteristic.addDescriptor(new BluetoothGattDescriptor(ENVIRONMENTAL_SENSING_CONFIGURATION_DESCRIPTOR, 0));
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);
        locationAndNavigationService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);

        assertFalse(locationAndNavigationService.isLNControlPointCharacteristicSupporeted());
    }

    @Test
    public void test_onDiscoverServiceSuccess_00107() {
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, new MockLocationAndNavigationServiceCallback(), null);
        BluetoothGattService bluetoothGattService = new BluetoothGattService(LOCATION_AND_NAVIGATION_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(LN_CONTROL_POINT_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_INDICATE, 0);
        bluetoothGattCharacteristic.addDescriptor(new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, 0));
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);
        locationAndNavigationService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);

        assertTrue(locationAndNavigationService.isLNControlPointCharacteristicSupporeted());
    }

    @Test
    public void test_onDiscoverServiceSuccess_00201() {
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, new MockLocationAndNavigationServiceCallback(), null);
        locationAndNavigationService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.<BluetoothGattService>emptyList(), null);

        assertFalse(locationAndNavigationService.isNavigationCharacteristicSupporeted());
    }

    @Test
    public void test_onDiscoverServiceSuccess_00202() {
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, new MockLocationAndNavigationServiceCallback(), null);
        BluetoothGattService bluetoothGattService = new BluetoothGattService(GENERIC_ACCESS_SERVICE, 0);
        locationAndNavigationService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);

        assertFalse(locationAndNavigationService.isNavigationCharacteristicSupporeted());
    }

    @Test
    public void test_onDiscoverServiceSuccess_00203() {
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, new MockLocationAndNavigationServiceCallback(), null);
        BluetoothGattService bluetoothGattService = new BluetoothGattService(LOCATION_AND_NAVIGATION_SERVICE, 0);
        locationAndNavigationService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);

        assertFalse(locationAndNavigationService.isNavigationCharacteristicSupporeted());
    }

    @Test
    public void test_onDiscoverServiceSuccess_00204() {
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, new MockLocationAndNavigationServiceCallback(), null);
        BluetoothGattService bluetoothGattService = new BluetoothGattService(LOCATION_AND_NAVIGATION_SERVICE, 0);
        bluetoothGattService.addCharacteristic(new BluetoothGattCharacteristic(NAVIGATION_CHARACTERISTIC, 0, 0));
        locationAndNavigationService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);

        assertFalse(locationAndNavigationService.isNavigationCharacteristicSupporeted());
    }

    @Test
    public void test_onDiscoverServiceSuccess_00205() {
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, new MockLocationAndNavigationServiceCallback(), null);
        BluetoothGattService bluetoothGattService = new BluetoothGattService(LOCATION_AND_NAVIGATION_SERVICE, 0);
        bluetoothGattService.addCharacteristic(new BluetoothGattCharacteristic(NAVIGATION_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_NOTIFY, 0));
        locationAndNavigationService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);

        assertFalse(locationAndNavigationService.isNavigationCharacteristicSupporeted());
    }

    @Test
    public void test_onDiscoverServiceSuccess_00206() {
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, new MockLocationAndNavigationServiceCallback(), null);
        BluetoothGattService bluetoothGattService = new BluetoothGattService(LOCATION_AND_NAVIGATION_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(NAVIGATION_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_NOTIFY, 0);
        bluetoothGattCharacteristic.addDescriptor(new BluetoothGattDescriptor(ENVIRONMENTAL_SENSING_CONFIGURATION_DESCRIPTOR, 0));
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);
        locationAndNavigationService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);

        assertFalse(locationAndNavigationService.isNavigationCharacteristicSupporeted());
    }

    @Test
    public void test_onDiscoverServiceSuccess_00207() {
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, new MockLocationAndNavigationServiceCallback(), null);
        BluetoothGattService bluetoothGattService = new BluetoothGattService(LOCATION_AND_NAVIGATION_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(NAVIGATION_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_NOTIFY, 0);
        bluetoothGattCharacteristic.addDescriptor(new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, 0));
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);
        locationAndNavigationService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);

        assertTrue(locationAndNavigationService.isNavigationCharacteristicSupporeted());
    }

    @Test
    public void test_onCharacteristicReadSuccess_00001() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_FEATURE_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final byte[] originalValues = new byte[]{4, 5, 6, 7};
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLNFeatureReadSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @NonNull Integer serviceInstanceId, @NonNull UUID characteristicUUID, @NonNull Integer characteristicInstanceId, @NonNull LNFeatureAndroid lnFeatureAndroid, @Nullable Bundle argument) {
                assertEquals(originalTaskId, taskId);
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertArrayEquals(originalValues, lnFeatureAndroid.getBytes());
                assertEquals(originalBundle, argument);
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onCharacteristicReadSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalValues, originalBundle);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onCharacteristicReadSuccess_00002() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ACCESS_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_FEATURE_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final byte[] originalValues = new byte[]{4, 5, 6, 7};
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLNFeatureReadSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @NonNull Integer serviceInstanceId, @NonNull UUID characteristicUUID, @NonNull Integer characteristicInstanceId, @NonNull LNFeatureAndroid lnFeatureAndroid, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onCharacteristicReadSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalValues, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onCharacteristicReadSuccess_00003() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LOCATION_AND_SPEED_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final byte[] originalValues = new byte[]{4, 5, 6, 7};
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLNFeatureReadSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @NonNull Integer serviceInstanceId, @NonNull UUID characteristicUUID, @NonNull Integer characteristicInstanceId, @NonNull LNFeatureAndroid lnFeatureAndroid, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onCharacteristicReadSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalValues, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onCharacteristicReadSuccess_00101() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = POSITION_QUALITY_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final byte[] originalValues = new byte[]{0, 0};
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onPositionQualityReadSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @NonNull Integer serviceInstanceId, @NonNull UUID characteristicUUID, @NonNull Integer characteristicInstanceId, @NonNull PositionQualityAndroid positionQualityAndroid, @Nullable Bundle argument) {
                assertEquals(originalTaskId, taskId);
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertArrayEquals(originalValues, positionQualityAndroid.getBytes());
                assertEquals(originalBundle, argument);
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onCharacteristicReadSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalValues, originalBundle);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onCharacteristicReadSuccess_00102() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ACCESS_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_FEATURE_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final byte[] originalValues = new byte[]{0, 0};
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onPositionQualityReadSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @NonNull Integer serviceInstanceId, @NonNull UUID characteristicUUID, @NonNull Integer characteristicInstanceId, @NonNull PositionQualityAndroid positionQualityAndroid, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onCharacteristicReadSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalValues, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onCharacteristicReadSuccess_00103() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LOCATION_AND_SPEED_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final byte[] originalValues = new byte[]{0, 0};
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onPositionQualityReadSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @NonNull Integer serviceInstanceId, @NonNull UUID characteristicUUID, @NonNull Integer characteristicInstanceId, @NonNull PositionQualityAndroid positionQualityAndroid, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onCharacteristicReadSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalValues, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onCharacteristicReadFailed_00001() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_FEATURE_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLNFeatureReadFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, int status, @Nullable Bundle argument) {
                assertEquals(originalTaskId, taskId);
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertEquals(originalStatus, status);
                assertEquals(originalBundle, argument);
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onCharacteristicReadFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalStatus, originalBundle);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onCharacteristicReadFailed_00002() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ACCESS_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_FEATURE_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLNFeatureReadFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, int status, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onCharacteristicReadFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalStatus, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onCharacteristicReadFailed_00003() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LOCATION_AND_SPEED_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLNFeatureReadFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, int status, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onCharacteristicReadFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalStatus, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onCharacteristicReadFailed_00101() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = POSITION_QUALITY_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onPositionQualityReadFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, int status, @Nullable Bundle argument) {
                assertEquals(originalTaskId, taskId);
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertEquals(originalStatus, status);
                assertEquals(originalBundle, argument);
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onCharacteristicReadFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalStatus, originalBundle);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onCharacteristicReadFailed_00102() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ACCESS_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = POSITION_QUALITY_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onPositionQualityReadFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, int status, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onCharacteristicReadFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalStatus, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onCharacteristicReadFailed_00103() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LOCATION_AND_SPEED_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onPositionQualityReadFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, int status, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onCharacteristicReadFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalStatus, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onCharacteristicReadTimeout_00001() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_FEATURE_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final long originalTimeout = 4;
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLNFeatureReadTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, long timeout, @Nullable Bundle argument) {
                assertEquals(originalTaskId, taskId);
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertEquals(originalTimeout, timeout);
                assertEquals(originalBundle, argument);
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onCharacteristicReadTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalTimeout, originalBundle);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onCharacteristicReadTimeout_00002() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ACCESS_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_FEATURE_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final long originalTimeout = 4;
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLNFeatureReadFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, int status, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onCharacteristicReadTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalTimeout, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onCharacteristicReadTimeout_00003() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LOCATION_AND_SPEED_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final long originalTimeout = 4;
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLNFeatureReadTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, long timeout, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onCharacteristicReadTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalTimeout, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onCharacteristicReadTimeout_00101() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = POSITION_QUALITY_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final long originalTimeout = 4;
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            public void onPositionQualityReadTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, long timeout, @Nullable Bundle argument) {
                assertEquals(originalTaskId, taskId);
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertEquals(originalTimeout, timeout);
                assertEquals(originalBundle, argument);
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onCharacteristicReadTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalTimeout, originalBundle);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onCharacteristicReadTimeout_00102() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ACCESS_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = POSITION_QUALITY_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final long originalTimeout = 4;
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onPositionQualityReadTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, long timeout, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onCharacteristicReadTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalTimeout, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onCharacteristicReadTimeout_00103() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LOCATION_AND_SPEED_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final long originalTimeout = 4;
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onPositionQualityReadTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, long timeout, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onCharacteristicReadTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalTimeout, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onCharacteristicWriteSuccess_00001() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_CONTROL_POINT_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final byte[] originalValues = new byte[]{0};
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLNControlPointWriteSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @NonNull Integer serviceInstanceId, @NonNull UUID characteristicUUID, @NonNull Integer characteristicInstanceId, @NonNull LNControlPointAndroid lnControlPointAndroid, @Nullable Bundle argument) {
                assertEquals(originalTaskId, taskId);
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertArrayEquals(originalValues, lnControlPointAndroid.getBytes());
                assertEquals(originalBundle, argument);
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onCharacteristicWriteSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalValues, originalBundle);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onCharacteristicWriteSuccess_00002() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ACCESS_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_CONTROL_POINT_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final byte[] originalValues = new byte[]{0};
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLNControlPointWriteSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @NonNull Integer serviceInstanceId, @NonNull UUID characteristicUUID, @NonNull Integer characteristicInstanceId, @NonNull LNControlPointAndroid lnControlPointAndroid, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onCharacteristicWriteSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalValues, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onCharacteristicWriteSuccess_00003() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LOCATION_AND_SPEED_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final byte[] originalValues = new byte[]{0};
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLNControlPointWriteSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @NonNull Integer serviceInstanceId, @NonNull UUID characteristicUUID, @NonNull Integer characteristicInstanceId, @NonNull LNControlPointAndroid lnControlPointAndroid, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onCharacteristicWriteSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalValues, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onCharacteristicWriteFailed_00001() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_CONTROL_POINT_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLNControlPointWriteFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, int status, @Nullable Bundle argument) {
                assertEquals(originalTaskId, taskId);
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertEquals(originalStatus, status);
                assertEquals(originalBundle, argument);
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onCharacteristicWriteFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalStatus, originalBundle);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onCharacteristicWriteFailed_00002() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ACCESS_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_CONTROL_POINT_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLNControlPointWriteFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, int status, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onCharacteristicWriteFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalStatus, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onCharacteristicWriteFailed_00003() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LOCATION_AND_SPEED_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLNControlPointWriteFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, int status, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onCharacteristicWriteFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalStatus, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onCharacteristicWriteTimeout_00001() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_CONTROL_POINT_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final long originalTimeout = 4;
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLNControlPointWriteTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, long timeout, @Nullable Bundle argument) {
                assertEquals(originalTaskId, taskId);
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertEquals(originalTimeout, timeout);
                assertEquals(originalBundle, argument);
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onCharacteristicWriteTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalTimeout, originalBundle);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onCharacteristicWriteTimeout_00002() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ACCESS_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_CONTROL_POINT_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final long originalTimeout = 4;
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLNControlPointWriteTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, long timeout, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onCharacteristicWriteTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalTimeout, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onCharacteristicWriteTimeout_00003() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LOCATION_AND_SPEED_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final long originalTimeout = 4;
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLNControlPointWriteTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, long timeout, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onCharacteristicWriteTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalTimeout, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorReadSuccess_00001() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LOCATION_AND_SPEED_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final byte[] originalValues = new byte[]{4, 5};
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLocationAndSpeedClientCharacteristicConfigurationReadSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @NonNull Integer serviceInstanceId, @NonNull UUID characteristicUUID, @NonNull Integer characteristicInstanceId, @NonNull ClientCharacteristicConfigurationAndroid clientCharacteristicConfigurationAndroid, @Nullable Bundle argument) {
                assertEquals(originalTaskId, taskId);
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertArrayEquals(originalValues, clientCharacteristicConfigurationAndroid.getBytes());
                assertEquals(originalBundle, argument);
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorReadSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalValues, originalBundle);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onDescriptorReadSuccess_00002() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ATTRIBUTE_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LOCATION_AND_SPEED_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final byte[] originalValues = new byte[]{4, 5};
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLocationAndSpeedClientCharacteristicConfigurationReadSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @NonNull Integer serviceInstanceId, @NonNull UUID characteristicUUID, @NonNull Integer characteristicInstanceId, @NonNull ClientCharacteristicConfigurationAndroid clientCharacteristicConfigurationAndroid, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorReadSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalValues, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorReadSuccess_00003() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_FEATURE_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final byte[] originalValues = new byte[]{4, 5};
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLocationAndSpeedClientCharacteristicConfigurationReadSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @NonNull Integer serviceInstanceId, @NonNull UUID characteristicUUID, @NonNull Integer characteristicInstanceId, @NonNull ClientCharacteristicConfigurationAndroid clientCharacteristicConfigurationAndroid, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorReadSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalValues, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorReadSuccess_00004() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LOCATION_AND_SPEED_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CHARACTERISTIC_PRESENTATION_FORMAT_DESCRIPTOR;
        final byte[] originalValues = new byte[]{4, 5};
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLocationAndSpeedClientCharacteristicConfigurationReadSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @NonNull Integer serviceInstanceId, @NonNull UUID characteristicUUID, @NonNull Integer characteristicInstanceId, @NonNull ClientCharacteristicConfigurationAndroid clientCharacteristicConfigurationAndroid, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorReadSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalValues, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorReadSuccess_00101() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_CONTROL_POINT_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final byte[] originalValues = new byte[]{4, 5};
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLNControlPointClientCharacteristicConfigurationReadSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @NonNull Integer serviceInstanceId, @NonNull UUID characteristicUUID, @NonNull Integer characteristicInstanceId, @NonNull ClientCharacteristicConfigurationAndroid clientCharacteristicConfigurationAndroid, @Nullable Bundle argument) {
                assertEquals(originalTaskId, taskId);
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertArrayEquals(originalValues, clientCharacteristicConfigurationAndroid.getBytes());
                assertEquals(originalBundle, argument);
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorReadSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalValues, originalBundle);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onDescriptorReadSuccess_00102() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ATTRIBUTE_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_CONTROL_POINT_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final byte[] originalValues = new byte[]{4, 5};
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLNControlPointClientCharacteristicConfigurationReadSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @NonNull Integer serviceInstanceId, @NonNull UUID characteristicUUID, @NonNull Integer characteristicInstanceId, @NonNull ClientCharacteristicConfigurationAndroid clientCharacteristicConfigurationAndroid, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorReadSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalValues, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorReadSuccess_00103() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_FEATURE_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final byte[] originalValues = new byte[]{4, 5};
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLNControlPointClientCharacteristicConfigurationReadSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @NonNull Integer serviceInstanceId, @NonNull UUID characteristicUUID, @NonNull Integer characteristicInstanceId, @NonNull ClientCharacteristicConfigurationAndroid clientCharacteristicConfigurationAndroid, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorReadSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalValues, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorReadSuccess_00104() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_CONTROL_POINT_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CHARACTERISTIC_PRESENTATION_FORMAT_DESCRIPTOR;
        final byte[] originalValues = new byte[]{4, 5};
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLNControlPointClientCharacteristicConfigurationReadSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @NonNull Integer serviceInstanceId, @NonNull UUID characteristicUUID, @NonNull Integer characteristicInstanceId, @NonNull ClientCharacteristicConfigurationAndroid clientCharacteristicConfigurationAndroid, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorReadSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalValues, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorReadSuccess_00201() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = NAVIGATION_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final byte[] originalValues = new byte[]{4, 5};
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onNavigationClientCharacteristicConfigurationReadSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @NonNull Integer serviceInstanceId, @NonNull UUID characteristicUUID, @NonNull Integer characteristicInstanceId, @NonNull ClientCharacteristicConfigurationAndroid clientCharacteristicConfigurationAndroid, @Nullable Bundle argument) {
                assertEquals(originalTaskId, taskId);
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertArrayEquals(originalValues, clientCharacteristicConfigurationAndroid.getBytes());
                assertEquals(originalBundle, argument);
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorReadSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalValues, originalBundle);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onDescriptorReadSuccess_00202() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ATTRIBUTE_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = NAVIGATION_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final byte[] originalValues = new byte[]{4, 5};
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onNavigationClientCharacteristicConfigurationReadSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @NonNull Integer serviceInstanceId, @NonNull UUID characteristicUUID, @NonNull Integer characteristicInstanceId, @NonNull ClientCharacteristicConfigurationAndroid clientCharacteristicConfigurationAndroid, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorReadSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalValues, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorReadSuccess_00203() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_FEATURE_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final byte[] originalValues = new byte[]{4, 5};
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onNavigationClientCharacteristicConfigurationReadSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @NonNull Integer serviceInstanceId, @NonNull UUID characteristicUUID, @NonNull Integer characteristicInstanceId, @NonNull ClientCharacteristicConfigurationAndroid clientCharacteristicConfigurationAndroid, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorReadSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalValues, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorReadSuccess_00204() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = NAVIGATION_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CHARACTERISTIC_PRESENTATION_FORMAT_DESCRIPTOR;
        final byte[] originalValues = new byte[]{4, 5};
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onNavigationClientCharacteristicConfigurationReadSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @NonNull Integer serviceInstanceId, @NonNull UUID characteristicUUID, @NonNull Integer characteristicInstanceId, @NonNull ClientCharacteristicConfigurationAndroid clientCharacteristicConfigurationAndroid, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorReadSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalValues, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorReadFailed_00001() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LOCATION_AND_SPEED_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLocationAndSpeedClientCharacteristicConfigurationReadFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, int status, @Nullable Bundle argument) {
                assertEquals(originalTaskId, taskId);
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertEquals(originalStatus, status);
                assertEquals(originalBundle, argument);
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorReadFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalStatus, originalBundle);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onDescriptorReadFailed_00002() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ATTRIBUTE_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LOCATION_AND_SPEED_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLocationAndSpeedClientCharacteristicConfigurationReadFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, int status, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorReadFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalStatus, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorReadFailed_00003() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_FEATURE_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLocationAndSpeedClientCharacteristicConfigurationReadFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, int status, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorReadFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalStatus, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorReadFailed_00004() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LOCATION_AND_SPEED_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CHARACTERISTIC_PRESENTATION_FORMAT_DESCRIPTOR;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLocationAndSpeedClientCharacteristicConfigurationReadFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, int status, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorReadFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalStatus, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorReadFailed_00101() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_CONTROL_POINT_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLNControlPointClientCharacteristicConfigurationReadFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, int status, @Nullable Bundle argument) {
                assertEquals(originalTaskId, taskId);
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertEquals(originalStatus, status);
                assertEquals(originalBundle, argument);
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorReadFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalStatus, originalBundle);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onDescriptorReadFailed_00102() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ATTRIBUTE_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_CONTROL_POINT_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLNControlPointClientCharacteristicConfigurationReadFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, int status, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorReadFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalStatus, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorReadFailed_00103() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_FEATURE_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLNControlPointClientCharacteristicConfigurationReadFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, int status, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorReadFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalStatus, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorReadFailed_00104() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_CONTROL_POINT_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CHARACTERISTIC_PRESENTATION_FORMAT_DESCRIPTOR;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLNControlPointClientCharacteristicConfigurationReadFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, int status, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorReadFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalStatus, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorReadFailed_00201() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = NAVIGATION_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onNavigationClientCharacteristicConfigurationReadFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, int status, @Nullable Bundle argument) {
                assertEquals(originalTaskId, taskId);
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertEquals(originalStatus, status);
                assertEquals(originalBundle, argument);
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorReadFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalStatus, originalBundle);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onDescriptorReadFailed_00202() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ATTRIBUTE_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = NAVIGATION_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onNavigationClientCharacteristicConfigurationReadFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, int status, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorReadFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalStatus, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorReadFailed_00203() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_FEATURE_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onNavigationClientCharacteristicConfigurationReadFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, int status, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorReadFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalStatus, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorReadFailed_00204() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = NAVIGATION_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CHARACTERISTIC_PRESENTATION_FORMAT_DESCRIPTOR;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onNavigationClientCharacteristicConfigurationReadFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, int status, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorReadFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalStatus, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorReadTimeout_00001() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LOCATION_AND_SPEED_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final long originalTimeout = 4;
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLocationAndSpeedClientCharacteristicConfigurationReadTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, long timeout, @Nullable Bundle argument) {
                assertEquals(originalTaskId, taskId);
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertEquals(originalTimeout, timeout);
                assertEquals(originalBundle, argument);
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorReadTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalTimeout, originalBundle);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onDescriptorReadTimeout_00002() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ATTRIBUTE_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LOCATION_AND_SPEED_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final long originalTimeout = 4;
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLocationAndSpeedClientCharacteristicConfigurationReadTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, long timeout, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorReadTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalTimeout, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorReadTimeout_00003() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_FEATURE_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final long originalTimeout = 4;
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLocationAndSpeedClientCharacteristicConfigurationReadTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, long timeout, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorReadTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalTimeout, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorReadTimeout_00004() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LOCATION_AND_SPEED_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CHARACTERISTIC_PRESENTATION_FORMAT_DESCRIPTOR;
        final long originalTimeout = 4;
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLocationAndSpeedClientCharacteristicConfigurationReadTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, long timeout, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorReadTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalTimeout, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorReadTimeout_00101() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_CONTROL_POINT_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final long originalTimeout = 4;
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLNControlPointClientCharacteristicConfigurationReadTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, long timeout, @Nullable Bundle argument) {
                assertEquals(originalTaskId, taskId);
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertEquals(originalTimeout, timeout);
                assertEquals(originalBundle, argument);
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorReadTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalTimeout, originalBundle);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onDescriptorReadTimeout_00102() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ATTRIBUTE_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_CONTROL_POINT_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final long originalTimeout = 4;
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLNControlPointClientCharacteristicConfigurationReadTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, long timeout, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorReadTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalTimeout, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorReadTimeout_00103() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_FEATURE_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final long originalTimeout = 4;
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLNControlPointClientCharacteristicConfigurationReadTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, long timeout, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorReadTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalTimeout, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorReadTimeout_00104() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_CONTROL_POINT_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CHARACTERISTIC_PRESENTATION_FORMAT_DESCRIPTOR;
        final long originalTimeout = 4;
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLNControlPointClientCharacteristicConfigurationReadTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, long timeout, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorReadTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalTimeout, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorReadTimeout_00201() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = NAVIGATION_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final long originalTimeout = 4;
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onNavigationClientCharacteristicConfigurationReadTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, long timeout, @Nullable Bundle argument) {
                assertEquals(originalTaskId, taskId);
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertEquals(originalTimeout, timeout);
                assertEquals(originalBundle, argument);
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorReadTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalTimeout, originalBundle);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onDescriptorReadTimeout_00202() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ATTRIBUTE_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = NAVIGATION_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final long originalTimeout = 4;
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onNavigationClientCharacteristicConfigurationReadTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, long timeout, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorReadTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalTimeout, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorReadTimeout_00203() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_FEATURE_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final long originalTimeout = 4;
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onNavigationClientCharacteristicConfigurationReadTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, long timeout, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorReadTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalTimeout, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorReadTimeout_00204() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = NAVIGATION_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CHARACTERISTIC_PRESENTATION_FORMAT_DESCRIPTOR;
        final long originalTimeout = 4;
        final Bundle originalBundle = new Bundle();
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onNavigationClientCharacteristicConfigurationReadTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, long timeout, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorReadTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalTimeout, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteSuccess_00001() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LOCATION_AND_SPEED_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final byte[] originalValues = new byte[]{4, 5};
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 0);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLocationAndSpeedNotifyStartSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Bundle argument) {
                assertEquals(originalTaskId, taskId);
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertEquals(originalBundle, argument);
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorWriteSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalValues, originalBundle);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteSuccess_00002() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ATTRIBUTE_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LOCATION_AND_SPEED_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final byte[] originalValues = new byte[]{4, 5};
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 0);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLocationAndSpeedNotifyStartSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorWriteSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalValues, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteSuccess_00003() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_FEATURE_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final byte[] originalValues = new byte[]{4, 5};
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 0);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLocationAndSpeedNotifyStartSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorReadSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalValues, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteSuccess_00004() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LOCATION_AND_SPEED_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CHARACTERISTIC_PRESENTATION_FORMAT_DESCRIPTOR;
        final byte[] originalValues = new byte[]{4, 5};
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 0);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLocationAndSpeedNotifyStartSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorWriteSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalValues, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteSuccess_00005() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LOCATION_AND_SPEED_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final byte[] originalValues = new byte[]{4, 5};
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 1);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLocationAndSpeedNotifyStopSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Bundle argument) {
                assertEquals(originalTaskId, taskId);
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertEquals(originalBundle, argument);
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorWriteSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalValues, originalBundle);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteSuccess_00006() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ATTRIBUTE_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LOCATION_AND_SPEED_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final byte[] originalValues = new byte[]{4, 5};
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 1);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLocationAndSpeedNotifyStopSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorWriteSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalValues, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteSuccess_00007() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_FEATURE_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final byte[] originalValues = new byte[]{4, 5};
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 1);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLocationAndSpeedNotifyStopSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorReadSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalValues, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteSuccess_00008() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LOCATION_AND_SPEED_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CHARACTERISTIC_PRESENTATION_FORMAT_DESCRIPTOR;
        final byte[] originalValues = new byte[]{4, 5};
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 1);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLocationAndSpeedNotifyStopSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorWriteSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalValues, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteSuccess_00101() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_CONTROL_POINT_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final byte[] originalValues = new byte[]{4, 5};
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 0);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLNControlPointIndicateStartSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Bundle argument) {
                assertEquals(originalTaskId, taskId);
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertEquals(originalBundle, argument);
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorWriteSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalValues, originalBundle);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteSuccess_00102() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ATTRIBUTE_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_CONTROL_POINT_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final byte[] originalValues = new byte[]{4, 5};
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 0);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLNControlPointIndicateStartSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorWriteSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalValues, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteSuccess_00103() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_FEATURE_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final byte[] originalValues = new byte[]{4, 5};
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 0);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLNControlPointIndicateStartSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorReadSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalValues, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteSuccess_00104() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_CONTROL_POINT_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CHARACTERISTIC_PRESENTATION_FORMAT_DESCRIPTOR;
        final byte[] originalValues = new byte[]{4, 5};
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 0);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLNControlPointIndicateStartSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorWriteSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalValues, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteSuccess_00105() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_CONTROL_POINT_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final byte[] originalValues = new byte[]{4, 5};
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 1);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLNControlPointIndicateStopSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Bundle argument) {
                assertEquals(originalTaskId, taskId);
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertEquals(originalBundle, argument);
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorWriteSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalValues, originalBundle);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteSuccess_00106() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ATTRIBUTE_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_CONTROL_POINT_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final byte[] originalValues = new byte[]{4, 5};
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 1);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLNControlPointIndicateStopSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorWriteSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalValues, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteSuccess_00107() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_FEATURE_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final byte[] originalValues = new byte[]{4, 5};
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 1);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLNControlPointIndicateStopSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorReadSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalValues, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteSuccess_00108() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_CONTROL_POINT_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CHARACTERISTIC_PRESENTATION_FORMAT_DESCRIPTOR;
        final byte[] originalValues = new byte[]{4, 5};
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 1);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLNControlPointIndicateStopSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorWriteSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalValues, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteSuccess_00201() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = NAVIGATION_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final byte[] originalValues = new byte[]{4, 5};
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 0);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onNavigationNotifyStartSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Bundle argument) {
                assertEquals(originalTaskId, taskId);
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertEquals(originalBundle, argument);
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorWriteSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalValues, originalBundle);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteSuccess_00202() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ATTRIBUTE_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = NAVIGATION_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final byte[] originalValues = new byte[]{4, 5};
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 0);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onNavigationNotifyStartSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorWriteSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalValues, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteSuccess_00203() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_FEATURE_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final byte[] originalValues = new byte[]{4, 5};
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 0);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onNavigationNotifyStartSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorReadSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalValues, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteSuccess_00204() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = NAVIGATION_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CHARACTERISTIC_PRESENTATION_FORMAT_DESCRIPTOR;
        final byte[] originalValues = new byte[]{4, 5};
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 0);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onNavigationNotifyStartSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorWriteSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalValues, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteSuccess_00205() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = NAVIGATION_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final byte[] originalValues = new byte[]{4, 5};
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 1);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onNavigationNotifyStopSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Bundle argument) {
                assertEquals(originalTaskId, taskId);
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertEquals(originalBundle, argument);
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorWriteSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalValues, originalBundle);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteSuccess_00206() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ATTRIBUTE_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = NAVIGATION_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final byte[] originalValues = new byte[]{4, 5};
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 1);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onNavigationNotifyStopSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorWriteSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalValues, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteSuccess_00207() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_FEATURE_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final byte[] originalValues = new byte[]{4, 5};
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 1);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onNavigationNotifyStopSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorReadSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalValues, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteSuccess_00208() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = NAVIGATION_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CHARACTERISTIC_PRESENTATION_FORMAT_DESCRIPTOR;
        final byte[] originalValues = new byte[]{4, 5};
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 1);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onNavigationNotifyStopSuccess(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorWriteSuccess(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalValues, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteFailed_00001() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LOCATION_AND_SPEED_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 0);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLocationAndSpeedNotifyStartFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, int status, @Nullable Bundle argument) {
                assertEquals(originalTaskId, taskId);
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertEquals(originalStatus, status);
                assertEquals(originalBundle, argument);
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorWriteFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalStatus, originalBundle);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteFailed_00002() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ATTRIBUTE_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LOCATION_AND_SPEED_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 0);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLocationAndSpeedNotifyStartFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, int status, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorWriteFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalStatus, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteFailed_00003() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_FEATURE_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 0);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLocationAndSpeedNotifyStartFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, int status, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorWriteFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalStatus, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteFailed_00004() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LOCATION_AND_SPEED_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CHARACTERISTIC_PRESENTATION_FORMAT_DESCRIPTOR;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 0);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLocationAndSpeedNotifyStartFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, int status, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorWriteFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalStatus, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteFailed_00005() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LOCATION_AND_SPEED_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 1);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLocationAndSpeedNotifyStopFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, int status, @Nullable Bundle argument) {
                assertEquals(originalTaskId, taskId);
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertEquals(originalStatus, status);
                assertEquals(originalBundle, argument);
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorWriteFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalStatus, originalBundle);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteFailed_00006() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ATTRIBUTE_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LOCATION_AND_SPEED_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 1);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLocationAndSpeedNotifyStopFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, int status, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorWriteFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalStatus, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteFailed_00007() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_FEATURE_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 1);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLocationAndSpeedNotifyStopFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, int status, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorWriteFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalStatus, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteFailed_00008() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LOCATION_AND_SPEED_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CHARACTERISTIC_PRESENTATION_FORMAT_DESCRIPTOR;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 1);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLocationAndSpeedNotifyStopFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, int status, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorWriteFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalStatus, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteFailed_00101() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_CONTROL_POINT_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 0);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLNControlPointIndicateStartFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, int status, @Nullable Bundle argument) {
                assertEquals(originalTaskId, taskId);
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertEquals(originalStatus, status);
                assertEquals(originalBundle, argument);
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorWriteFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalStatus, originalBundle);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteFailed_00102() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ATTRIBUTE_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_CONTROL_POINT_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 0);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLNControlPointIndicateStartFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, int status, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorWriteFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalStatus, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteFailed_00103() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_FEATURE_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 0);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLNControlPointIndicateStartFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, int status, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorWriteFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalStatus, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteFailed_00104() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_CONTROL_POINT_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CHARACTERISTIC_PRESENTATION_FORMAT_DESCRIPTOR;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 0);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLNControlPointIndicateStartFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, int status, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorWriteFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalStatus, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteFailed_00105() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_CONTROL_POINT_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 1);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLNControlPointIndicateStopFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, int status, @Nullable Bundle argument) {
                assertEquals(originalTaskId, taskId);
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertEquals(originalStatus, status);
                assertEquals(originalBundle, argument);
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorWriteFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalStatus, originalBundle);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteFailed_00106() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ATTRIBUTE_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_CONTROL_POINT_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 1);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLNControlPointIndicateStopFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, int status, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorWriteFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalStatus, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteFailed_00107() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_FEATURE_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 1);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLNControlPointIndicateStopFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, int status, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorWriteFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalStatus, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteFailed_00108() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_CONTROL_POINT_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CHARACTERISTIC_PRESENTATION_FORMAT_DESCRIPTOR;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 1);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLNControlPointIndicateStopFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, int status, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorWriteFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalStatus, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteFailed_00201() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = NAVIGATION_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 0);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onNavigationNotifyStartFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, int status, @Nullable Bundle argument) {
                assertEquals(originalTaskId, taskId);
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertEquals(originalStatus, status);
                assertEquals(originalBundle, argument);
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorWriteFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalStatus, originalBundle);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteFailed_00202() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ATTRIBUTE_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = NAVIGATION_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 0);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onNavigationNotifyStartFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, int status, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorWriteFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalStatus, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteFailed_00203() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_FEATURE_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 0);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onNavigationNotifyStartFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, int status, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorWriteFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalStatus, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteFailed_00204() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = NAVIGATION_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CHARACTERISTIC_PRESENTATION_FORMAT_DESCRIPTOR;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 0);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onNavigationNotifyStartFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, int status, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorWriteFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalStatus, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteFailed_00205() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = NAVIGATION_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 1);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onNavigationNotifyStopFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, int status, @Nullable Bundle argument) {
                assertEquals(originalTaskId, taskId);
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertEquals(originalStatus, status);
                assertEquals(originalBundle, argument);
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorWriteFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalStatus, originalBundle);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteFailed_00206() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ATTRIBUTE_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = NAVIGATION_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 1);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onNavigationNotifyStopFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, int status, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorWriteFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalStatus, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteFailed_00207() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_FEATURE_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 1);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onNavigationNotifyStopFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, int status, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorWriteFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalStatus, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteFailed_00208() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = NAVIGATION_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CHARACTERISTIC_PRESENTATION_FORMAT_DESCRIPTOR;
        final int originalStatus = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 1);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onNavigationNotifyStopFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, int status, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorWriteFailed(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalStatus, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteTimeout_00001() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LOCATION_AND_SPEED_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final long originalTimeout = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 0);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLocationAndSpeedNotifyStartTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, long timeout, @Nullable Bundle argument) {
                assertEquals(originalTaskId, taskId);
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertEquals(originalTimeout, timeout);
                assertEquals(originalBundle, argument);
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorWriteTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalTimeout, originalBundle);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteTimeout_00002() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ATTRIBUTE_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LOCATION_AND_SPEED_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final long originalTimeout = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 0);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLocationAndSpeedNotifyStartTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, long timeout, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorWriteTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalTimeout, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteTimeout_00003() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_FEATURE_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final long originalTimeout = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 0);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLocationAndSpeedNotifyStartTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, long timeout, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorWriteTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalTimeout, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteTimeout_00004() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LOCATION_AND_SPEED_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CHARACTERISTIC_PRESENTATION_FORMAT_DESCRIPTOR;
        final long originalTimeout = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 0);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLocationAndSpeedNotifyStartTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, long timeout, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorWriteTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalTimeout, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteTimeout_00005() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LOCATION_AND_SPEED_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final long originalTimeout = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 1);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLocationAndSpeedNotifyStopTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, long timeout, @Nullable Bundle argument) {
                assertEquals(originalTaskId, taskId);
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertEquals(originalTimeout, timeout);
                assertEquals(originalBundle, argument);
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorWriteTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalTimeout, originalBundle);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteTimeout_00006() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ATTRIBUTE_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LOCATION_AND_SPEED_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final long originalTimeout = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 1);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLocationAndSpeedNotifyStopTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, long timeout, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorWriteTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalTimeout, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteTimeout_00007() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_FEATURE_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final long originalTimeout = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 1);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLocationAndSpeedNotifyStopTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, long timeout, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorWriteTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalTimeout, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteTimeout_00008() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LOCATION_AND_SPEED_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CHARACTERISTIC_PRESENTATION_FORMAT_DESCRIPTOR;
        final long originalTimeout = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 1);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLocationAndSpeedNotifyStopTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, long timeout, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorWriteTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalTimeout, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteTimeout_00101() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_CONTROL_POINT_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final long originalTimeout = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 0);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLNControlPointIndicateStartTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, long timeout, @Nullable Bundle argument) {
                assertEquals(originalTaskId, taskId);
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertEquals(originalTimeout, timeout);
                assertEquals(originalBundle, argument);
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorWriteTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalTimeout, originalBundle);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteTimeout_00102() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ATTRIBUTE_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_CONTROL_POINT_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final long originalTimeout = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 0);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLNControlPointIndicateStartTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, long timeout, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorWriteTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalTimeout, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteTimeout_00103() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_FEATURE_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final long originalTimeout = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 0);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLNControlPointIndicateStartTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, long timeout, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorWriteTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalTimeout, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteTimeout_00104() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_CONTROL_POINT_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CHARACTERISTIC_PRESENTATION_FORMAT_DESCRIPTOR;
        final long originalTimeout = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 0);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLNControlPointIndicateStartTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, long timeout, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorWriteTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalTimeout, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteTimeout_00105() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_CONTROL_POINT_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final long originalTimeout = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 1);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLNControlPointIndicateStopTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, long timeout, @Nullable Bundle argument) {
                assertEquals(originalTaskId, taskId);
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertEquals(originalTimeout, timeout);
                assertEquals(originalBundle, argument);
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorWriteTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalTimeout, originalBundle);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteTimeout_00106() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ATTRIBUTE_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_CONTROL_POINT_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final long originalTimeout = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 1);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLNControlPointIndicateStopTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, long timeout, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorWriteTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalTimeout, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteTimeout_00107() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_FEATURE_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final long originalTimeout = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 1);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLNControlPointIndicateStopTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, long timeout, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorWriteTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalTimeout, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteTimeout_00108() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_CONTROL_POINT_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CHARACTERISTIC_PRESENTATION_FORMAT_DESCRIPTOR;
        final long originalTimeout = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 1);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLNControlPointIndicateStopTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, long timeout, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorWriteTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalTimeout, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteTimeout_00201() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = NAVIGATION_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final long originalTimeout = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 0);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onNavigationNotifyStartTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, long timeout, @Nullable Bundle argument) {
                assertEquals(originalTaskId, taskId);
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertEquals(originalTimeout, timeout);
                assertEquals(originalBundle, argument);
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorWriteTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalTimeout, originalBundle);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteTimeout_00202() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ATTRIBUTE_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = NAVIGATION_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final long originalTimeout = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 0);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onNavigationNotifyStartTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, long timeout, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorWriteTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalTimeout, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteTimeout_00203() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_FEATURE_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final long originalTimeout = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 0);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onNavigationNotifyStartTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, long timeout, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorWriteTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalTimeout, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteTimeout_00204() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = NAVIGATION_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CHARACTERISTIC_PRESENTATION_FORMAT_DESCRIPTOR;
        final long originalTimeout = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 0);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onNavigationNotifyStartTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, long timeout, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorWriteTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalTimeout, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteTimeout_00205() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = NAVIGATION_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final long originalTimeout = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 1);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onNavigationNotifyStopTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, long timeout, @Nullable Bundle argument) {
                assertEquals(originalTaskId, taskId);
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertEquals(originalTimeout, timeout);
                assertEquals(originalBundle, argument);
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorWriteTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalTimeout, originalBundle);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteTimeout_00206() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ATTRIBUTE_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = NAVIGATION_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final long originalTimeout = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 1);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onNavigationNotifyStopTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, long timeout, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorWriteTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalTimeout, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteTimeout_00207() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_FEATURE_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
        final long originalTimeout = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 1);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onNavigationNotifyStopTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, long timeout, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorWriteTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalTimeout, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onDescriptorWriteTimeout_00208() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final Integer originalTaskId = 1;
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = NAVIGATION_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final UUID originalDescriptorUUID = CHARACTERISTIC_PRESENTATION_FORMAT_DESCRIPTOR;
        final long originalTimeout = 4;
        final Bundle originalBundle = new Bundle();
        originalBundle.putInt("KEY_STATUS", 1);
        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onNavigationNotifyStopTimeout(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, long timeout, @Nullable Bundle argument) {
                isCalled.set(true);
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onDescriptorWriteTimeout(originalTaskId, originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalDescriptorUUID, originalTimeout, originalBundle);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onCharacteristicNotified_00001() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LOCATION_AND_SPEED_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final byte[] originalValues = new byte[]{0, 0};

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLocationAndSpeedNotified(@NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @NonNull Integer serviceInstanceId, @NonNull UUID characteristicUUID, @NonNull Integer characteristicInstanceId, @NonNull LocationAndSpeedAndroid locationAndSpeedAndroid) {
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertArrayEquals(originalValues, locationAndSpeedAndroid.getBytes());
                isCalled.set(true);
            }

        };

        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onCharacteristicNotified(originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalValues);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onCharacteristicNotified_00002() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final BluetoothDevice originalBluetoothDevice = BluetoothAdapter.getDefaultAdapter().getRemoteDevice("00:11:22:33:AA:CC");
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LOCATION_AND_SPEED_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final byte[] originalValues = new byte[]{0, 0};

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLocationAndSpeedNotified(@NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @NonNull Integer serviceInstanceId, @NonNull UUID characteristicUUID, @NonNull Integer characteristicInstanceId, @NonNull LocationAndSpeedAndroid locationAndSpeedAndroid) {
                isCalled.set(true);
            }

        };

        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onCharacteristicNotified(originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalValues);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onCharacteristicNotified_00003() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ATTRIBUTE_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LOCATION_AND_SPEED_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final byte[] originalValues = new byte[]{0, 0};

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLocationAndSpeedNotified(@NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @NonNull Integer serviceInstanceId, @NonNull UUID characteristicUUID, @NonNull Integer characteristicInstanceId, @NonNull LocationAndSpeedAndroid locationAndSpeedAndroid) {
                isCalled.set(true);
            }

        };

        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onCharacteristicNotified(originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalValues);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onCharacteristicNotified_00004() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_FEATURE_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final byte[] originalValues = new byte[]{0, 0};

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLocationAndSpeedNotified(@NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @NonNull Integer serviceInstanceId, @NonNull UUID characteristicUUID, @NonNull Integer characteristicInstanceId, @NonNull LocationAndSpeedAndroid locationAndSpeedAndroid) {
                isCalled.set(true);
            }

        };

        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onCharacteristicNotified(originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalValues);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onCharacteristicNotified_00101() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_CONTROL_POINT_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final byte[] originalValues = new byte[]{0};

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLNControlPointIndicated(@NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @NonNull Integer serviceInstanceId, @NonNull UUID characteristicUUID, @NonNull Integer characteristicInstanceId, @NonNull LNControlPointAndroid lnControlPointAndroid) {
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertArrayEquals(originalValues, lnControlPointAndroid.getBytes());
                isCalled.set(true);
            }

        };

        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onCharacteristicNotified(originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalValues);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onCharacteristicNotified_00102() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final BluetoothDevice originalBluetoothDevice = BluetoothAdapter.getDefaultAdapter().getRemoteDevice("00:11:22:33:AA:CC");
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_CONTROL_POINT_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final byte[] originalValues = new byte[]{0, 0};

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLNControlPointIndicated(@NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @NonNull Integer serviceInstanceId, @NonNull UUID characteristicUUID, @NonNull Integer characteristicInstanceId, @NonNull LNControlPointAndroid lnControlPointAndroid) {
                isCalled.set(true);
            }

        };

        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onCharacteristicNotified(originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalValues);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onCharacteristicNotified_00103() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ATTRIBUTE_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_CONTROL_POINT_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final byte[] originalValues = new byte[]{0, 0};

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLNControlPointIndicated(@NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @NonNull Integer serviceInstanceId, @NonNull UUID characteristicUUID, @NonNull Integer characteristicInstanceId, @NonNull LNControlPointAndroid lnControlPointAndroid) {
                isCalled.set(true);
            }

        };

        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onCharacteristicNotified(originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalValues);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onCharacteristicNotified_00104() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_FEATURE_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final byte[] originalValues = new byte[]{0, 0};

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onLNControlPointIndicated(@NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @NonNull Integer serviceInstanceId, @NonNull UUID characteristicUUID, @NonNull Integer characteristicInstanceId, @NonNull LNControlPointAndroid lnControlPointAndroid) {
                isCalled.set(true);
            }

        };

        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onCharacteristicNotified(originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalValues);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onCharacteristicNotified_00201() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = NAVIGATION_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final byte[] originalValues = new byte[]{0, 0, 0, 0, 0, 0};

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onNavigationNotified(@NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @NonNull Integer serviceInstanceId, @NonNull UUID characteristicUUID, @NonNull Integer characteristicInstanceId, @NonNull NavigationAndroid navigationAndroid) {
                assertEquals(originalBluetoothDevice, bluetoothDevice);
                assertEquals(originalServiceUUID, serviceUUID);
                assertEquals(originalServiceInstanceId, serviceInstanceId);
                assertEquals(originalCharacteristicUUID, characteristicUUID);
                assertEquals(originalCharacteristicInstanceId, characteristicInstanceId);
                assertArrayEquals(originalValues, navigationAndroid.getBytes());
                isCalled.set(true);
            }

        };

        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onCharacteristicNotified(originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalValues);

        assertTrue(isCalled.get());
    }

    @Test
    public void test_onCharacteristicNotified_00202() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final BluetoothDevice originalBluetoothDevice = BluetoothAdapter.getDefaultAdapter().getRemoteDevice("00:11:22:33:AA:CC");
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = NAVIGATION_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final byte[] originalValues = new byte[]{0, 0};

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onNavigationNotified(@NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @NonNull Integer serviceInstanceId, @NonNull UUID characteristicUUID, @NonNull Integer characteristicInstanceId, @NonNull NavigationAndroid navigationAndroid) {
                isCalled.set(true);
            }

        };

        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onCharacteristicNotified(originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalValues);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onCharacteristicNotified_00203() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = GENERIC_ATTRIBUTE_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = NAVIGATION_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final byte[] originalValues = new byte[]{0, 0};

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onNavigationNotified(@NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @NonNull Integer serviceInstanceId, @NonNull UUID characteristicUUID, @NonNull Integer characteristicInstanceId, @NonNull NavigationAndroid navigationAndroid) {
                isCalled.set(true);
            }

        };

        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onCharacteristicNotified(originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalValues);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_onCharacteristicNotified_00204() {
        final AtomicBoolean isCalled = new AtomicBoolean(false);
        final BluetoothDevice originalBluetoothDevice = MockBLEConnection.MOCK_DEVICE;
        final UUID originalServiceUUID = LOCATION_AND_NAVIGATION_SERVICE;
        final Integer originalServiceInstanceId = 2;
        final UUID originalCharacteristicUUID = LN_FEATURE_CHARACTERISTIC;
        final Integer originalCharacteristicInstanceId = 3;
        final byte[] originalValues = new byte[]{0, 0};

        MockBLEConnection mockBLEConnection = new MockBLEConnection();
        MockLocationAndNavigationServiceCallback mockLocationAndNavigationServiceCallback = new MockLocationAndNavigationServiceCallback() {

            @Override
            public void onNavigationNotified(@NonNull BluetoothDevice bluetoothDevice, @NonNull UUID serviceUUID, @NonNull Integer serviceInstanceId, @NonNull UUID characteristicUUID, @NonNull Integer characteristicInstanceId, @NonNull NavigationAndroid navigationAndroid) {
                isCalled.set(true);
            }

        };

        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, mockLocationAndNavigationServiceCallback, null);
        locationAndNavigationService.onCharacteristicNotified(originalBluetoothDevice, originalServiceUUID, originalServiceInstanceId, originalCharacteristicUUID, originalCharacteristicInstanceId, originalValues);

        assertFalse(isCalled.get());
    }

    @Test
    public void test_isPositionQualityCharacteristicSupporeted_00001() {
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(new MockBLEConnection(), new MockLocationAndNavigationServiceCallback(), null);

        assertFalse(locationAndNavigationService.isPositionQualityCharacteristicSupporeted());
    }

    @Test
    public void test_isPositionQualityCharacteristicSupporeted_00002() {
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(new MockBLEConnection(), new MockLocationAndNavigationServiceCallback(), null);
        BluetoothGattService bluetoothGattService = new BluetoothGattService(LOCATION_AND_NAVIGATION_SERVICE, 0);
        bluetoothGattService.addCharacteristic(new BluetoothGattCharacteristic(POSITION_QUALITY_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ, BluetoothGattCharacteristic.PERMISSION_READ));
        locationAndNavigationService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);

        assertTrue(locationAndNavigationService.isPositionQualityCharacteristicSupporeted());
    }

    @Test
    public void test_isPositionQualityCharacteristicSupporeted_00003() {
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(new MockBLEConnection(), new MockLocationAndNavigationServiceCallback(), null);
        BluetoothGattService bluetoothGattService = new BluetoothGattService(LOCATION_AND_NAVIGATION_SERVICE, 0);
        bluetoothGattService.addCharacteristic(new BluetoothGattCharacteristic(POSITION_QUALITY_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_READ, BluetoothGattCharacteristic.PERMISSION_READ));
        locationAndNavigationService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        locationAndNavigationService.onBLEDisconnected(1, MockBLEConnection.MOCK_DEVICE, 0, null);

        assertFalse(locationAndNavigationService.isPositionQualityCharacteristicSupporeted());
    }

    @Test
    public void test_isLNControlPointCharacteristicSupporeted_00001() {
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(new MockBLEConnection(), new MockLocationAndNavigationServiceCallback(), null);

        assertFalse(locationAndNavigationService.isLNControlPointCharacteristicSupporeted());
    }

    @Test
    public void test_isLNControlPointCharacteristicSupporeted_00002() {
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(new MockBLEConnection(), new MockLocationAndNavigationServiceCallback(), null);
        BluetoothGattService bluetoothGattService = new BluetoothGattService(LOCATION_AND_NAVIGATION_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(LN_CONTROL_POINT_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_INDICATE, 0);
        bluetoothGattCharacteristic.addDescriptor(new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE));
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);
        locationAndNavigationService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);

        assertTrue(locationAndNavigationService.isLNControlPointCharacteristicSupporeted());
    }

    @Test
    public void test_isLNControlPointCharacteristicSupporeted_00003() {
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(new MockBLEConnection(), new MockLocationAndNavigationServiceCallback(), null);
        BluetoothGattService bluetoothGattService = new BluetoothGattService(LOCATION_AND_NAVIGATION_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(LN_CONTROL_POINT_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_INDICATE, 0);
        bluetoothGattCharacteristic.addDescriptor(new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE));
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);
        locationAndNavigationService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        locationAndNavigationService.onBLEDisconnected(1, MockBLEConnection.MOCK_DEVICE, 0, null);

        assertFalse(locationAndNavigationService.isLNControlPointCharacteristicSupporeted());
    }

    @Test
    public void test_isNavigationCharacteristicSupporeted_00001() {
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(new MockBLEConnection(), new MockLocationAndNavigationServiceCallback(), null);

        assertFalse(locationAndNavigationService.isNavigationCharacteristicSupporeted());
    }

    @Test
    public void test_isNavigationCharacteristicSupporeted_00002() {
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(new MockBLEConnection(), new MockLocationAndNavigationServiceCallback(), null);
        BluetoothGattService bluetoothGattService = new BluetoothGattService(LOCATION_AND_NAVIGATION_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(NAVIGATION_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_NOTIFY, 0);
        bluetoothGattCharacteristic.addDescriptor(new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE));
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);
        locationAndNavigationService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);

        assertTrue(locationAndNavigationService.isNavigationCharacteristicSupporeted());
    }

    @Test
    public void test_isNavigationCharacteristicSupporeted_00003() {
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(new MockBLEConnection(), new MockLocationAndNavigationServiceCallback(), null);
        BluetoothGattService bluetoothGattService = new BluetoothGattService(LOCATION_AND_NAVIGATION_SERVICE, 0);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(NAVIGATION_CHARACTERISTIC, BluetoothGattCharacteristic.PROPERTY_NOTIFY, 0);
        bluetoothGattCharacteristic.addDescriptor(new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE));
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);
        locationAndNavigationService.onDiscoverServiceSuccess(1, MockBLEConnection.MOCK_DEVICE, Collections.singletonList(bluetoothGattService), null);
        locationAndNavigationService.onBLEDisconnected(1, MockBLEConnection.MOCK_DEVICE, 0, null);

        assertFalse(locationAndNavigationService.isNavigationCharacteristicSupporeted());
    }

    @Test
    public void test_getLNFeature_000001() {
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(new MockBLEConnection(), new MockLocationAndNavigationServiceCallback(), null);

        assertNull(locationAndNavigationService.getLNFeature());
    }

    @Test
    public void test_getLNFeature_000002() {
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(new MockBLEConnection(), new MockLocationAndNavigationServiceCallback(), null) {

            @Override
            public boolean isStarted() {
                return true;
            }

        };

        assertNull(locationAndNavigationService.getLNFeature());
    }

    @Test
    public void test_getLNFeature_000003() {
        final Integer originalTaskId = 1;
        MockBLEConnection mockBLEConnection = new MockBLEConnection() {

            @Override
            public synchronized Integer createReadCharacteristicTask(@NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, long timeout, @Nullable Bundle argument, @Nullable BLECallback bleCallback) {
                return originalTaskId;
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, new MockLocationAndNavigationServiceCallback(), null) {

            @Override
            public boolean isStarted() {
                return true;
            }

        };

        Integer taskId = locationAndNavigationService.getLNFeature();
        assertNotNull(taskId);
        assertEquals(originalTaskId, taskId);
    }

    @Test
    public void test_getLocationAndSpeedClientCharacteristicConfiguration_000001() {
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(new MockBLEConnection(), new MockLocationAndNavigationServiceCallback(), null);

        assertNull(locationAndNavigationService.getLocationAndSpeedClientCharacteristicConfiguration());
    }

    @Test
    public void test_getLocationAndSpeedClientCharacteristicConfiguration_000002() {
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(new MockBLEConnection(), new MockLocationAndNavigationServiceCallback(), null) {

            @Override
            public boolean isStarted() {
                return true;
            }

        };

        assertNull(locationAndNavigationService.getLocationAndSpeedClientCharacteristicConfiguration());
    }

    @Test
    public void test_getLocationAndSpeedClientCharacteristicConfiguration_000003() {
        final Integer originalTaskId = 1;
        MockBLEConnection mockBLEConnection = new MockBLEConnection() {

            @Override
            public synchronized Integer createReadDescriptorTask(@NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @NonNull UUID descriptorUUID, long timeout, @Nullable Bundle argument, @Nullable BLECallback bleCallback) {
                return originalTaskId;
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, new MockLocationAndNavigationServiceCallback(), null) {

            @Override
            public boolean isStarted() {
                return true;
            }

        };

        Integer taskId = locationAndNavigationService.getLocationAndSpeedClientCharacteristicConfiguration();
        assertNotNull(taskId);
        assertEquals(originalTaskId, taskId);
    }

    @Test
    public void test_startLocationAndSpeedNotification_000001() {
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(new MockBLEConnection(), new MockLocationAndNavigationServiceCallback(), null);

        assertNull(locationAndNavigationService.startLocationAndSpeedNotification());
    }

    @Test
    public void test_startLocationAndSpeedNotification_000002() {
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(new MockBLEConnection(), new MockLocationAndNavigationServiceCallback(), null) {

            @Override
            public boolean isStarted() {
                return true;
            }

        };

        assertNull(locationAndNavigationService.startLocationAndSpeedNotification());
    }

    @Test
    public void test_startLocationAndSpeedNotification_000003() {
        final Integer originalTaskId = 1;
        MockBLEConnection mockBLEConnection = new MockBLEConnection() {

            @Override
            public synchronized Integer createWriteDescriptorTask(@NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @NonNull UUID descriptorUUID, @NonNull ByteArrayInterface byteArrayInterface, long timeout, @Nullable Bundle argument, @Nullable BLECallback bleCallback) {
                return originalTaskId;
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, new MockLocationAndNavigationServiceCallback(), null) {

            @Override
            public boolean isStarted() {
                return true;
            }

        };

        Integer taskId = locationAndNavigationService.startLocationAndSpeedNotification();
        assertNotNull(taskId);
        assertEquals(originalTaskId, taskId);
    }

    @Test
    public void test_stopLocationAndSpeedNotification_000001() {
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(new MockBLEConnection(), new MockLocationAndNavigationServiceCallback(), null);

        assertNull(locationAndNavigationService.stopLocationAndSpeedNotification());
    }

    @Test
    public void test_stopLocationAndSpeedNotification_000002() {
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(new MockBLEConnection(), new MockLocationAndNavigationServiceCallback(), null) {

            @Override
            public boolean isStarted() {
                return true;
            }

        };

        assertNull(locationAndNavigationService.stopLocationAndSpeedNotification());
    }

    @Test
    public void test_stopLocationAndSpeedNotification_000003() {
        final Integer originalTaskId = 1;
        MockBLEConnection mockBLEConnection = new MockBLEConnection() {

            @Override
            public synchronized Integer createWriteDescriptorTask(@NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @NonNull UUID descriptorUUID, @NonNull ByteArrayInterface byteArrayInterface, long timeout, @Nullable Bundle argument, @Nullable BLECallback bleCallback) {
                return originalTaskId;
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, new MockLocationAndNavigationServiceCallback(), null) {

            @Override
            public boolean isStarted() {
                return true;
            }

        };

        Integer taskId = locationAndNavigationService.stopLocationAndSpeedNotification();
        assertNotNull(taskId);
        assertEquals(originalTaskId, taskId);
    }

    @Test
    public void test_getPositionQuality_000001() {
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(new MockBLEConnection(), new MockLocationAndNavigationServiceCallback(), null);

        assertNull(locationAndNavigationService.getPositionQuality());
    }

    @Test
    public void test_getPositionQuality_000002() {
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(new MockBLEConnection(), new MockLocationAndNavigationServiceCallback(), null) {

            @Override
            public boolean isStarted() {
                return true;
            }

        };

        assertNull(locationAndNavigationService.getPositionQuality());
    }

    @Test
    public void test_getPositionQuality_000003() {
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(new MockBLEConnection(), new MockLocationAndNavigationServiceCallback(), null) {

            @Override
            public boolean isPositionQualityCharacteristicSupporeted() {
                return true;
            }

            @Override
            public boolean isStarted() {
                return true;
            }

        };

        assertNull(locationAndNavigationService.getPositionQuality());
    }

    @Test
    public void test_getPositionQuality_000004() {
        final Integer originalTaskId = 1;
        MockBLEConnection mockBLEConnection = new MockBLEConnection() {

            @Override
            public synchronized Integer createReadCharacteristicTask(@NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, long timeout, @Nullable Bundle argument, @Nullable BLECallback bleCallback) {
                return originalTaskId;
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, new MockLocationAndNavigationServiceCallback(), null) {

            @Override
            public boolean isPositionQualityCharacteristicSupporeted() {
                return true;
            }

            @Override
            public boolean isStarted() {
                return true;
            }

        };

        Integer taskId = locationAndNavigationService.getPositionQuality();
        assertNotNull(taskId);
        assertEquals(originalTaskId, taskId);
    }

    @Test
    public void test_setLNControlPoint_000001() {
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(new MockBLEConnection(), new MockLocationAndNavigationServiceCallback(), null);
        LNControlPoint lnControlPoint = new LNControlPoint(new byte[]{0});

        assertNull(locationAndNavigationService.setLNControlPoint(lnControlPoint));
    }

    @Test
    public void test_setLNControlPoint_000002() {
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(new MockBLEConnection(), new MockLocationAndNavigationServiceCallback(), null) {

            @Override
            public boolean isStarted() {
                return true;
            }

        };
        LNControlPoint lnControlPoint = new LNControlPoint(new byte[]{0});

        assertNull(locationAndNavigationService.setLNControlPoint(lnControlPoint));
    }

    @Test
    public void test_setLNControlPoint_000003() {
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(new MockBLEConnection(), new MockLocationAndNavigationServiceCallback(), null) {

            @Override
            public boolean isLNControlPointCharacteristicSupporeted() {
                return true;
            }

            @Override
            public boolean isStarted() {
                return true;
            }

        };
        LNControlPoint lnControlPoint = new LNControlPoint(new byte[]{0});

        assertNull(locationAndNavigationService.setLNControlPoint(lnControlPoint));
    }

    @Test
    public void test_setLNControlPoint_000004() {
        final Integer originalTaskId = 1;
        MockBLEConnection mockBLEConnection = new MockBLEConnection() {

            @Override
            public synchronized Integer createWriteCharacteristicTask(@NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @NonNull ByteArrayInterface byteArrayInterface, int writeType, long timeout, @Nullable Bundle argument, @Nullable BLECallback bleCallback) {
                return originalTaskId;
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, new MockLocationAndNavigationServiceCallback(), null) {

            @Override
            public boolean isLNControlPointCharacteristicSupporeted() {
                return true;
            }

            @Override
            public boolean isStarted() {
                return true;
            }

        };
        LNControlPoint lnControlPoint = new LNControlPoint(new byte[]{0});

        Integer taskId = locationAndNavigationService.setLNControlPoint(lnControlPoint);
        assertNotNull(taskId);
        assertEquals(originalTaskId, taskId);
    }

    @Test
    public void test_getLNControlPointClientCharacteristicConfiguration_000001() {
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(new MockBLEConnection(), new MockLocationAndNavigationServiceCallback(), null);

        assertNull(locationAndNavigationService.getLNControlPointClientCharacteristicConfiguration());
    }

    @Test
    public void test_getLNControlPointClientCharacteristicConfiguration_000002() {
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(new MockBLEConnection(), new MockLocationAndNavigationServiceCallback(), null) {

            @Override
            public boolean isStarted() {
                return true;
            }

        };

        assertNull(locationAndNavigationService.getLNControlPointClientCharacteristicConfiguration());
    }

    @Test
    public void test_getLNControlPointClientCharacteristicConfiguration_000003() {
        final Integer originalTaskId = 1;
        MockBLEConnection mockBLEConnection = new MockBLEConnection() {

            @Override
            public synchronized Integer createReadDescriptorTask(@NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @NonNull UUID descriptorUUID, long timeout, @Nullable Bundle argument, @Nullable BLECallback bleCallback) {
                return originalTaskId;
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, new MockLocationAndNavigationServiceCallback(), null) {

            @Override
            public boolean isStarted() {
                return true;
            }

        };

        Integer taskId = locationAndNavigationService.getLNControlPointClientCharacteristicConfiguration();
        assertNotNull(taskId);
        assertEquals(originalTaskId, taskId);
    }

    @Test
    public void test_startLNControlPointIndication_000001() {
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(new MockBLEConnection(), new MockLocationAndNavigationServiceCallback(), null);

        assertNull(locationAndNavigationService.startLNControlPointIndication());
    }

    @Test
    public void test_startLNControlPointIndication_000002() {
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(new MockBLEConnection(), new MockLocationAndNavigationServiceCallback(), null) {

            @Override
            public boolean isStarted() {
                return true;
            }

        };

        assertNull(locationAndNavigationService.startLNControlPointIndication());
    }

    @Test
    public void test_startLNControlPointIndication_000003() {
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(new MockBLEConnection(), new MockLocationAndNavigationServiceCallback(), null) {

            @Override
            public boolean isLNControlPointCharacteristicSupporeted() {
                return true;
            }

            @Override
            public boolean isStarted() {
                return true;
            }

        };

        assertNull(locationAndNavigationService.startLNControlPointIndication());
    }

    @Test
    public void test_startLNControlPointIndication_000004() {
        final Integer originalTaskId = 1;
        MockBLEConnection mockBLEConnection = new MockBLEConnection() {

            @Override
            public synchronized Integer createWriteDescriptorTask(@NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @NonNull UUID descriptorUUID, @NonNull ByteArrayInterface byteArrayInterface, long timeout, @Nullable Bundle argument, @Nullable BLECallback bleCallback) {
                return originalTaskId;
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, new MockLocationAndNavigationServiceCallback(), null) {

            @Override
            public boolean isLNControlPointCharacteristicSupporeted() {
                return true;
            }

            @Override
            public boolean isStarted() {
                return true;
            }

        };

        Integer taskId = locationAndNavigationService.startLNControlPointIndication();
        assertNotNull(taskId);
        assertEquals(originalTaskId, taskId);
    }

    @Test
    public void test_stopLNControlPointIndication_000001() {
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(new MockBLEConnection(), new MockLocationAndNavigationServiceCallback(), null);

        assertNull(locationAndNavigationService.stopLNControlPointIndication());
    }

    @Test
    public void test_stopLNControlPointIndication_000002() {
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(new MockBLEConnection(), new MockLocationAndNavigationServiceCallback(), null) {

            @Override
            public boolean isStarted() {
                return true;
            }

        };

        assertNull(locationAndNavigationService.stopLNControlPointIndication());
    }

    @Test
    public void test_stopLNControlPointIndication_000003() {
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(new MockBLEConnection(), new MockLocationAndNavigationServiceCallback(), null) {

            @Override
            public boolean isLNControlPointCharacteristicSupporeted() {
                return true;
            }

            @Override
            public boolean isStarted() {
                return true;
            }

        };

        assertNull(locationAndNavigationService.stopLNControlPointIndication());
    }

    @Test
    public void test_stopLNControlPointIndication_000004() {
        final Integer originalTaskId = 1;
        MockBLEConnection mockBLEConnection = new MockBLEConnection() {

            @Override
            public synchronized Integer createWriteDescriptorTask(@NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @NonNull UUID descriptorUUID, @NonNull ByteArrayInterface byteArrayInterface, long timeout, @Nullable Bundle argument, @Nullable BLECallback bleCallback) {
                return originalTaskId;
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, new MockLocationAndNavigationServiceCallback(), null) {

            @Override
            public boolean isLNControlPointCharacteristicSupporeted() {
                return true;
            }

            @Override
            public boolean isStarted() {
                return true;
            }

        };

        Integer taskId = locationAndNavigationService.stopLNControlPointIndication();
        assertNotNull(taskId);
        assertEquals(originalTaskId, taskId);
    }

    @Test
    public void test_startNavigationNotification_000001() {
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(new MockBLEConnection(), new MockLocationAndNavigationServiceCallback(), null);

        assertNull(locationAndNavigationService.startNavigationNotification());
    }

    @Test
    public void test_startNavigationNotification_000002() {
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(new MockBLEConnection(), new MockLocationAndNavigationServiceCallback(), null) {

            @Override
            public boolean isStarted() {
                return true;
            }

        };

        assertNull(locationAndNavigationService.startNavigationNotification());
    }

    @Test
    public void test_startNavigationNotification_000003() {
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(new MockBLEConnection(), new MockLocationAndNavigationServiceCallback(), null) {

            @Override
            public boolean isNavigationCharacteristicSupporeted() {
                return true;
            }

            @Override
            public boolean isStarted() {
                return true;
            }

        };

        assertNull(locationAndNavigationService.startNavigationNotification());
    }

    @Test
    public void test_startNavigationNotification_000004() {
        final Integer originalTaskId = 1;
        MockBLEConnection mockBLEConnection = new MockBLEConnection() {

            @Override
            public synchronized Integer createWriteDescriptorTask(@NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @NonNull UUID descriptorUUID, @NonNull ByteArrayInterface byteArrayInterface, long timeout, @Nullable Bundle argument, @Nullable BLECallback bleCallback) {
                return originalTaskId;
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, new MockLocationAndNavigationServiceCallback(), null) {

            @Override
            public boolean isNavigationCharacteristicSupporeted() {
                return true;
            }

            @Override
            public boolean isStarted() {
                return true;
            }

        };

        Integer taskId = locationAndNavigationService.startNavigationNotification();
        assertNotNull(taskId);
        assertEquals(originalTaskId, taskId);
    }

    @Test
    public void test_stopNavigationNotification_000001() {
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(new MockBLEConnection(), new MockLocationAndNavigationServiceCallback(), null);

        assertNull(locationAndNavigationService.stopNavigationNotification());
    }

    @Test
    public void test_stopNavigationNotification_000002() {
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(new MockBLEConnection(), new MockLocationAndNavigationServiceCallback(), null) {

            @Override
            public boolean isStarted() {
                return true;
            }

        };

        assertNull(locationAndNavigationService.stopNavigationNotification());
    }

    @Test
    public void test_stopNavigationNotification_000003() {
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(new MockBLEConnection(), new MockLocationAndNavigationServiceCallback(), null) {

            @Override
            public boolean isNavigationCharacteristicSupporeted() {
                return true;
            }

            @Override
            public boolean isStarted() {
                return true;
            }

        };

        assertNull(locationAndNavigationService.stopNavigationNotification());
    }

    @Test
    public void test_stopNavigationNotification_000004() {
        final Integer originalTaskId = 1;
        MockBLEConnection mockBLEConnection = new MockBLEConnection() {

            @Override
            public synchronized Integer createWriteDescriptorTask(@NonNull UUID serviceUUID, @Nullable Integer serviceInstanceId, @NonNull UUID characteristicUUID, @Nullable Integer characteristicInstanceId, @NonNull UUID descriptorUUID, @NonNull ByteArrayInterface byteArrayInterface, long timeout, @Nullable Bundle argument, @Nullable BLECallback bleCallback) {
                return originalTaskId;
            }

        };
        LocationAndNavigationService locationAndNavigationService = new LocationAndNavigationService(mockBLEConnection, new MockLocationAndNavigationServiceCallback(), null) {

            @Override
            public boolean isNavigationCharacteristicSupporeted() {
                return true;
            }

            @Override
            public boolean isStarted() {
                return true;
            }

        };

        Integer taskId = locationAndNavigationService.stopNavigationNotification();
        assertNotNull(taskId);
        assertEquals(originalTaskId, taskId);
    }

}
