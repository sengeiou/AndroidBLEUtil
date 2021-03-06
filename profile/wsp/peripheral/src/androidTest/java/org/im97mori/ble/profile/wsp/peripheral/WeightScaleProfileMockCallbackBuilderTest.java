package org.im97mori.ble.profile.wsp.peripheral;

import android.bluetooth.BluetoothGattDescriptor;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.test.core.app.ApplicationProvider;

import org.im97mori.ble.characteristic.u2a0f.LocalTimeInformation;
import org.im97mori.ble.characteristic.u2a14.ReferenceTimeInformation;
import org.im97mori.ble.characteristic.u2a19.BatteryLevel;
import org.im97mori.ble.characteristic.u2a23.SystemId;
import org.im97mori.ble.characteristic.u2a24.ModelNumberString;
import org.im97mori.ble.characteristic.u2a29.ManufacturerNameString;
import org.im97mori.ble.characteristic.u2a2b.CurrentTime;
import org.im97mori.ble.characteristic.u2a80.Age;
import org.im97mori.ble.characteristic.u2a85.DateOfBirth;
import org.im97mori.ble.characteristic.u2a8a.FirstName;
import org.im97mori.ble.characteristic.u2a8c.Gender;
import org.im97mori.ble.characteristic.u2a8e.Height;
import org.im97mori.ble.characteristic.u2a9d.WeightMeasurement;
import org.im97mori.ble.characteristic.u2a9e.WeightScaleFeature;
import org.im97mori.ble.descriptor.u2902.ClientCharacteristicConfiguration;
import org.im97mori.ble.descriptor.u2904.CharacteristicPresentationFormat;
import org.im97mori.ble.service.bas.peripheral.BatteryServiceMockCallback;
import org.im97mori.ble.service.cts.peripheral.CurrentTimeServiceMockCallback;
import org.im97mori.ble.service.dis.peripheral.DeviceInformationServiceMockCallback;
import org.im97mori.ble.service.uds.peripheral.UserDataServiceMockCallback;
import org.im97mori.ble.service.wss.peripheral.WeightScaleServiceMockCallback;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class WeightScaleProfileMockCallbackBuilderTest {

    @Test
    public void test_constructor_00001() {
        Context context = ApplicationProvider.getApplicationContext();
        WspWeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        WspUserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> wspUserDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<>();
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context, weightScaleServiceMockCallbackBuilder, deviceInformationServiceMockCallbackBuilder, wspUserDataServiceMockCallbackBuilder, batteryServiceMockCallbackBuilder, currentTimeServiceMockCallbackBuilder);

        assertEquals(context, baseBuilder.mContext);
        assertEquals(weightScaleServiceMockCallbackBuilder, baseBuilder.mWeightScaleServiceMockCallbackBuilder);
        assertEquals(deviceInformationServiceMockCallbackBuilder, baseBuilder.mDeviceInformationServiceMockCallbackBuilder);
        assertEquals(wspUserDataServiceMockCallbackBuilder, baseBuilder.mUserDataServiceMockCallbackBuilder);
        assertEquals(batteryServiceMockCallbackBuilder, baseBuilder.mBatteryServiceMockCallbackBuilder);
        assertEquals(currentTimeServiceMockCallbackBuilder, baseBuilder.mCurrentTimeServiceMockCallbackBuilder);
    }

    @Test
    public void test_addWeightScaleFeature_00001() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        final byte[] originalValue = new byte[]{1, 2, 3, 4};
        final WeightScaleFeature weightScaleFeature = new WeightScaleFeature(originalValue);

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback>() {
            @NonNull
            @Override
            public WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> addWeightScaleFeature(int responceCode, long delay, @NonNull byte[] value) {
                assertArrayEquals(originalValue, value);
                atomicBoolean.set(true);
                return super.addWeightScaleFeature(responceCode, delay, value);
            }
        };
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<>();
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.addWeightScaleFeature(weightScaleFeature));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_addWeightScaleFeature_00002() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        final byte[] originalValue = new byte[]{1, 2, 3, 4};

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback>() {
            @NonNull
            @Override
            public WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> addWeightScaleFeature(int responceCode, long delay, @NonNull byte[] value) {
                assertArrayEquals(originalValue, value);
                atomicBoolean.set(true);
                return super.addWeightScaleFeature(responceCode, delay, value);
            }
        };
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<>();
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.addWeightScaleFeature(originalValue));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_addWeightScaleFeature_00003() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        final int originalResponceCode = 1;
        final long originalDelay = 2;
        final byte[] originalValue = new byte[]{1, 2, 3, 4};

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback>() {
            @NonNull
            @Override
            public WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> addWeightScaleFeature(int responceCode, long delay, @NonNull byte[] value) {
                assertEquals(originalResponceCode, responceCode);
                assertEquals(originalDelay, delay);
                assertArrayEquals(originalValue, value);
                atomicBoolean.set(true);
                return super.addWeightScaleFeature(responceCode, delay, value);
            }
        };
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<>();
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.addWeightScaleFeature(originalResponceCode, originalDelay, originalValue));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_removeWeightScaleFeature_00001() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback>() {

            @NonNull
            @Override
            public WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> removeWeightScaleFeature() {
                atomicBoolean.set(true);
                return super.removeWeightScaleFeature();
            }
        };
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<>();
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.removeWeightScaleFeature());

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_addWeightMeasurement_00001() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        final byte[] originalValue = new byte[]{0, 1, 2};
        final WeightMeasurement weightMeasurement = new WeightMeasurement(originalValue);
        final byte[] originalDescriptorValue = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE;
        final ClientCharacteristicConfiguration clientCharacteristicConfiguration = new ClientCharacteristicConfiguration(originalDescriptorValue);

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback>() {

            @NonNull
            @Override
            public WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> removeWeightScaleFeature() {
                atomicBoolean.set(true);
                return super.removeWeightScaleFeature();
            }

            @NonNull
            @Override
            public WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> addWeightMeasurement(int characteristicResponceCode, long characteristicDelay, @NonNull byte[] characteristicValue, int notificationCount, int descriptorResponceCode, long descriptorDelay, @NonNull byte[] descriptorValue) {
                assertArrayEquals(originalValue, characteristicValue);
                assertArrayEquals(originalDescriptorValue, descriptorValue);
                atomicBoolean.set(true);
                return super.addWeightMeasurement(characteristicResponceCode, characteristicDelay, characteristicValue, notificationCount, descriptorResponceCode, descriptorDelay, descriptorValue);
            }
        };
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<>();
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.addWeightMeasurement(weightMeasurement, clientCharacteristicConfiguration));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_addWeightMeasurement_00002() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        final byte[] originalValue = new byte[]{0, 1, 2};
        final byte[] originalDescriptorValue = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE;

        final int originalCharacteristicResponseCode = 1;
        final long originalCharacteristicDelay = 2;
        final int originalNotificationCount = 3;
        final int originalDescriptorResponseCode = 4;
        final long originalDescriptorDelay = 5;

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback>() {

            @NonNull
            @Override
            public WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> removeWeightScaleFeature() {
                atomicBoolean.set(true);
                return super.removeWeightScaleFeature();
            }

            @NonNull
            @Override
            public WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> addWeightMeasurement(int characteristicResponceCode, long characteristicDelay, @NonNull byte[] characteristicValue, int notificationCount, int descriptorResponceCode, long descriptorDelay, @NonNull byte[] descriptorValue) {
                assertEquals(originalCharacteristicResponseCode, characteristicResponceCode);
                assertEquals(originalCharacteristicDelay, characteristicDelay);
                assertArrayEquals(originalValue, characteristicValue);
                assertEquals(originalNotificationCount, notificationCount);
                assertEquals(originalDescriptorResponseCode, descriptorResponceCode);
                assertEquals(originalDescriptorDelay, descriptorDelay);
                assertArrayEquals(originalDescriptorValue, descriptorValue);
                atomicBoolean.set(true);
                return super.addWeightMeasurement(characteristicResponceCode, characteristicDelay, characteristicValue, notificationCount, descriptorResponceCode, descriptorDelay, descriptorValue);
            }
        };
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<>();
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.addWeightMeasurement(originalCharacteristicResponseCode, originalCharacteristicDelay, originalValue, originalNotificationCount, originalDescriptorResponseCode, originalDescriptorDelay, originalDescriptorValue));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_removeWeightMeasurement_00001() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback>() {

            @NonNull
            @Override
            public WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> removeWeightMeasurement() {
                atomicBoolean.set(true);
                return super.removeWeightMeasurement();
            }
        };
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<>();
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.removeWeightMeasurement());

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_addManufacturerNameString_00001() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        final String manufacturerName = "manufacturerName";

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback>() {
            @NonNull
            @Override
            public DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> addManufacturerNameString(int responceCode, long delay, @NonNull byte[] value) {
                assertArrayEquals(manufacturerName.getBytes(), value);
                atomicBoolean.set(true);
                return super.addManufacturerNameString(responceCode, delay, value);
            }
        };
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<>();
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.addManufacturerNameString(manufacturerName));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_addManufacturerNameString_00002() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        final String manufacturerName = "manufacturerName";
        ManufacturerNameString manufacturerNameString = new ManufacturerNameString(manufacturerName);

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback>() {
            @NonNull
            @Override
            public DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> addManufacturerNameString(int responceCode, long delay, @NonNull byte[] value) {
                assertArrayEquals(manufacturerName.getBytes(), value);
                atomicBoolean.set(true);
                return super.addManufacturerNameString(responceCode, delay, value);
            }
        };
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<>();
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.addManufacturerNameString(manufacturerNameString));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_addManufacturerNameString_00003() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        final byte[] originalValue = "manufacturerName".getBytes();

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback>() {
            @NonNull
            @Override
            public DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> addManufacturerNameString(int responceCode, long delay, @NonNull byte[] value) {
                assertArrayEquals(originalValue, value);
                atomicBoolean.set(true);
                return super.addManufacturerNameString(responceCode, delay, value);
            }
        };
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<>();
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.addManufacturerNameString(originalValue));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_addManufacturerNameString_00004() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        final int originalResponceCode = 1;
        final long originalDelay = 2;
        final byte[] originalValue = "manufacturerName".getBytes();

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback>() {
            @NonNull
            @Override
            public DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> addManufacturerNameString(int responceCode, long delay, @NonNull byte[] value) {
                assertEquals(originalResponceCode, responceCode);
                assertEquals(originalDelay, delay);
                assertArrayEquals(originalValue, value);
                atomicBoolean.set(true);
                return super.addManufacturerNameString(responceCode, delay, value);
            }
        };
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<>();
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.addManufacturerNameString(originalResponceCode, originalDelay, originalValue));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_removeManufacturerNameString_00001() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback>() {
            @NonNull
            @Override
            public DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> removeManufacturerNameString() {
                atomicBoolean.set(true);
                return super.removeManufacturerNameString();
            }
        };
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<>();
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.removeManufacturerNameString());

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_addModelNumberString_00001() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        final String modelNumber = "modelNumber";

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback>() {
            @NonNull
            @Override
            public DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> addModelNumberString(int responceCode, long delay, @NonNull byte[] value) {
                assertArrayEquals(modelNumber.getBytes(), value);
                atomicBoolean.set(true);
                return super.addModelNumberString(responceCode, delay, value);
            }
        };
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<>();
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.addModelNumberString(modelNumber));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_addModelNumberString_00002() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        final String modelNumber = "modelNumber";
        ModelNumberString modelNumberString = new ModelNumberString(modelNumber);

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback>() {
            @NonNull
            @Override
            public DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> addModelNumberString(int responceCode, long delay, @NonNull byte[] value) {
                assertArrayEquals(modelNumber.getBytes(), value);
                atomicBoolean.set(true);
                return super.addModelNumberString(responceCode, delay, value);
            }
        };
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<>();
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.addModelNumberString(modelNumberString));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_addModelNumberString_00003() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        final byte[] originalValue = "modelNumber".getBytes();

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback>() {
            @NonNull
            @Override
            public DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> addModelNumberString(int responceCode, long delay, @NonNull byte[] value) {
                assertArrayEquals(originalValue, value);
                atomicBoolean.set(true);
                return super.addModelNumberString(responceCode, delay, value);
            }
        };
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<>();
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.addModelNumberString(originalValue));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_addModelNumberString_00004() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        final int originalResponceCode = 1;
        final long originalDelay = 2;
        final byte[] originalValue = "modelNumber".getBytes();

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback>() {
            @NonNull
            @Override
            public DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> addModelNumberString(int responceCode, long delay, @NonNull byte[] value) {
                assertEquals(originalResponceCode, responceCode);
                assertEquals(originalDelay, delay);
                assertArrayEquals(originalValue, value);
                atomicBoolean.set(true);
                return super.addModelNumberString(responceCode, delay, value);
            }
        };
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<>();
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.addModelNumberString(originalResponceCode, originalDelay, originalValue));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_removeModelNumberString_00001() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback>() {
            @NonNull
            @Override
            public DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> removeModelNumberString() {
                atomicBoolean.set(true);
                return super.removeModelNumberString();
            }
        };
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<>();
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.removeModelNumberString());

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_addSystemId_00001() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        final long originalManufacturerIdentifier = 1;
        final int originalOrganizationallyUniqueIdentifier = 2;
        final SystemId originalSystemId = new SystemId(originalManufacturerIdentifier, originalOrganizationallyUniqueIdentifier);

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback>() {
            @Override
            public DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> addSystemId(int responceCode, long delay, @NonNull byte[] value) {
                assertArrayEquals(originalSystemId.getBytes(), value);
                atomicBoolean.set(true);
                return super.addSystemId(responceCode, delay, value);
            }
        };
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<>();
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.addSystemId(originalManufacturerIdentifier, originalOrganizationallyUniqueIdentifier));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_addSystemId_00002() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        final long originalManufacturerIdentifier = 1;
        final int originalOrganizationallyUniqueIdentifier = 2;
        final SystemId originalSystemId = new SystemId(originalManufacturerIdentifier, originalOrganizationallyUniqueIdentifier);

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback>() {
            @Override
            public DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> addSystemId(int responceCode, long delay, @NonNull byte[] value) {
                assertArrayEquals(originalSystemId.getBytes(), value);
                atomicBoolean.set(true);
                return super.addSystemId(responceCode, delay, value);
            }
        };
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<>();
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.addSystemId(originalSystemId));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_addSystemId_00003() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        final long originalManufacturerIdentifier = 1;
        final int originalOrganizationallyUniqueIdentifier = 2;
        final SystemId originalSystemId = new SystemId(originalManufacturerIdentifier, originalOrganizationallyUniqueIdentifier);

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback>() {
            @Override
            public DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> addSystemId(int responceCode, long delay, @NonNull byte[] value) {
                assertArrayEquals(originalSystemId.getBytes(), value);
                atomicBoolean.set(true);
                return super.addSystemId(responceCode, delay, value);
            }
        };
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<>();
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.addSystemId(originalSystemId.getBytes()));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_addSystemId_00004() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        final int originalResponceCode = 1;
        final long originalDelay = 2;
        final long originalManufacturerIdentifier = 1;
        final int originalOrganizationallyUniqueIdentifier = 2;
        final SystemId originalSystemId = new SystemId(originalManufacturerIdentifier, originalOrganizationallyUniqueIdentifier);

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback>() {
            @NonNull
            @Override
            public DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> addSystemId(int responceCode, long delay, @NonNull byte[] value) {
                assertEquals(originalResponceCode, responceCode);
                assertEquals(originalDelay, delay);
                assertArrayEquals(originalSystemId.getBytes(), value);
                atomicBoolean.set(true);
                return super.addSystemId(responceCode, delay, value);
            }
        };
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<>();
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.addSystemId(originalResponceCode, originalDelay, originalSystemId.getBytes()));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_removeSystemId_00001() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback>() {
            @NonNull
            @Override
            public DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> removeSystemId() {
                atomicBoolean.set(true);
                return super.removeSystemId();
            }
        };
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<>();
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.removeSystemId());

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_addFirstName_00001() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        final String firstNameString = "firstNameString";
        final FirstName firstName = new FirstName(firstNameString);

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback>() {
            @NonNull
            @Override
            public UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> addFirstName(int responceCode, long delay, @NonNull byte[] value) {
                assertArrayEquals(firstName.getBytes(), value);
                atomicBoolean.set(true);
                return super.addFirstName(responceCode, delay, value);
            }
        };
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.addFirstName(firstNameString));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_addFirstName_00002() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        final String firstNameString = "firstNameString";
        final FirstName firstName = new FirstName(firstNameString);

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback>() {
            @NonNull
            @Override
            public UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> addFirstName(int responceCode, long delay, @NonNull byte[] value) {
                assertArrayEquals(firstName.getBytes(), value);
                atomicBoolean.set(true);
                return super.addFirstName(responceCode, delay, value);
            }
        };
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.addFirstName(firstName));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_addFirstName_00003() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        final String firstNameString = "firstNameString";
        final FirstName firstName = new FirstName(firstNameString);

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback>() {
            @NonNull
            @Override
            public UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> addFirstName(int responceCode, long delay, @NonNull byte[] value) {
                assertArrayEquals(firstName.getBytes(), value);
                atomicBoolean.set(true);
                return super.addFirstName(responceCode, delay, value);
            }
        };
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.addFirstName(firstName.getBytes()));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_addFirstName_00004() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        final int originalResponceCode = 1;
        final long originalDelay = 2;
        final String firstNameString = "firstNameString";
        final FirstName firstName = new FirstName(firstNameString);

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback>() {
            @NonNull
            @Override
            public UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> addFirstName(int responceCode, long delay, @NonNull byte[] value) {
                assertEquals(originalResponceCode, responceCode);
                assertEquals(originalDelay, delay);
                assertArrayEquals(firstName.getBytes(), value);
                atomicBoolean.set(true);
                return super.addFirstName(responceCode, delay, value);
            }
        };
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.addFirstName(originalResponceCode, originalDelay, firstName.getBytes()));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_removeFirstName_00001() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback>() {

            @NonNull
            @Override
            public UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> removeFirstName() {
                atomicBoolean.set(true);
                return super.removeFirstName();
            }
        };
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.removeFirstName());

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_addAge_00001() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        final int originalValue = 1;
        final Age age = new Age(originalValue);

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback>() {

            @NonNull
            @Override
            public UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> addAge(int responceCode, long delay, @NonNull byte[] value) {
                assertArrayEquals(age.getBytes(), value);
                atomicBoolean.set(true);
                return super.addAge(responceCode, delay, value);
            }
        };
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.addAge(originalValue));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_addAge_00002() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        final int originalValue = 1;
        final Age age = new Age(originalValue);

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback>() {
            @NonNull
            @Override
            public UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> addAge(int responceCode, long delay, @NonNull byte[] value) {
                assertArrayEquals(age.getBytes(), value);
                atomicBoolean.set(true);
                return super.addAge(responceCode, delay, value);
            }
        };
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.addAge(age));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_addAge_00003() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        final int originalValue = 1;
        final Age age = new Age(originalValue);

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback>() {
            @NonNull
            @Override
            public UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> addAge(int responceCode, long delay, @NonNull byte[] value) {
                assertArrayEquals(age.getBytes(), value);
                atomicBoolean.set(true);
                return super.addAge(responceCode, delay, value);
            }
        };
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.addAge(age.getBytes()));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_addAge_00004() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        final int originalResponceCode = 1;
        final long originalDelay = 2;
        final int originalValue = 3;
        final Age age = new Age(originalValue);

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback>() {
            @NonNull
            @Override
            public UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> addAge(int responceCode, long delay, @NonNull byte[] value) {
                assertEquals(originalResponceCode, responceCode);
                assertEquals(originalDelay, delay);
                assertArrayEquals(age.getBytes(), value);
                atomicBoolean.set(true);
                return super.addAge(responceCode, delay, value);
            }
        };
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.addAge(originalResponceCode, originalDelay, age.getBytes()));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_removeAge_00001() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback>() {

            @NonNull
            @Override
            public UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> removeAge() {
                atomicBoolean.set(true);
                return super.removeAge();
            }
        };
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.removeAge());

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_addDateOfBirth_00001() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        final int originalYear = 1;
        final int originalMonth = 2;
        final int originalDay = 3;
        final DateOfBirth dateOfBirth = new DateOfBirth(originalYear, originalMonth, originalDay);

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback>() {

            @NonNull
            @Override
            public UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> addDateOfBirth(int responceCode, long delay, @NonNull byte[] value) {
                assertArrayEquals(dateOfBirth.getBytes(), value);
                atomicBoolean.set(true);
                return super.addDateOfBirth(responceCode, delay, value);
            }
        };
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.addDateOfBirth(originalYear, originalMonth, originalDay));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_addDateOfBirth_00002() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        final int originalYear = 1;
        final int originalMonth = 2;
        final int originalDay = 3;
        final DateOfBirth dateOfBirth = new DateOfBirth(originalYear, originalMonth, originalDay);

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback>() {

            @NonNull
            @Override
            public UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> addDateOfBirth(int responceCode, long delay, @NonNull byte[] value) {
                assertArrayEquals(dateOfBirth.getBytes(), value);
                atomicBoolean.set(true);
                return super.addDateOfBirth(responceCode, delay, value);
            }
        };
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.addDateOfBirth(dateOfBirth));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_addDateOfBirth_00003() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        final int originalYear = 1;
        final int originalMonth = 2;
        final int originalDay = 3;
        final DateOfBirth dateOfBirth = new DateOfBirth(originalYear, originalMonth, originalDay);

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback>() {

            @NonNull
            @Override
            public UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> addDateOfBirth(int responceCode, long delay, @NonNull byte[] value) {
                assertArrayEquals(dateOfBirth.getBytes(), value);
                atomicBoolean.set(true);
                return super.addDateOfBirth(responceCode, delay, value);
            }
        };
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.addDateOfBirth(dateOfBirth.getBytes()));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_addDateOfBirth_00004() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        final int originalResponceCode = 1;
        final long originalDelay = 2;
        final int originalYear = 3;
        final int originalMonth = 4;
        final int originalDay = 5;
        final DateOfBirth dateOfBirth = new DateOfBirth(originalYear, originalMonth, originalDay);

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback>() {

            @NonNull
            @Override
            public UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> addDateOfBirth(int responceCode, long delay, @NonNull byte[] value) {
                assertEquals(originalResponceCode, responceCode);
                assertEquals(originalDelay, delay);
                assertArrayEquals(dateOfBirth.getBytes(), value);
                atomicBoolean.set(true);
                return super.addDateOfBirth(responceCode, delay, value);
            }
        };
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.addDateOfBirth(originalResponceCode, originalDelay, dateOfBirth.getBytes()));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_removeDateOfBirth_00001() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback>() {

            @NonNull
            @Override
            public UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> removeDateOfBirth() {
                atomicBoolean.set(true);
                return super.removeDateOfBirth();
            }
        };
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.removeDateOfBirth());

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_addGender_00001() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        final int originalGender = 1;
        final Gender gender = new Gender(originalGender);

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback>() {

            @NonNull
            @Override
            public UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> addGender(int responceCode, long delay, @NonNull byte[] value) {
                assertArrayEquals(gender.getBytes(), value);
                atomicBoolean.set(true);
                return super.addGender(responceCode, delay, value);
            }
        };
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.addGender(originalGender));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_addGender_00002() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        final int originalGender = 1;
        final Gender gender = new Gender(originalGender);

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback>() {

            @NonNull
            @Override
            public UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> addGender(int responceCode, long delay, @NonNull byte[] value) {
                assertArrayEquals(gender.getBytes(), value);
                atomicBoolean.set(true);
                return super.addGender(responceCode, delay, value);
            }
        };
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.addGender(gender));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_addGender_00003() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        final int originalGender = 1;
        final Gender gender = new Gender(originalGender);

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback>() {

            @NonNull
            @Override
            public UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> addGender(int responceCode, long delay, @NonNull byte[] value) {
                assertArrayEquals(gender.getBytes(), value);
                atomicBoolean.set(true);
                return super.addGender(responceCode, delay, value);
            }
        };
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.addGender(gender.getBytes()));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_addGender_00004() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        final int originalResponceCode = 1;
        final long originalDelay = 2;
        final int originalGender = 3;
        final Gender gender = new Gender(originalGender);

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback>() {

            @NonNull
            @Override
            public UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> addGender(int responceCode, long delay, @NonNull byte[] value) {
                assertEquals(originalResponceCode, responceCode);
                assertEquals(originalDelay, delay);
                assertArrayEquals(gender.getBytes(), value);
                atomicBoolean.set(true);
                return super.addGender(responceCode, delay, value);
            }
        };
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.addGender(originalResponceCode, originalDelay, gender.getBytes()));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_removeGender_00001() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback>() {

            @NonNull
            @Override
            public UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> removeGender() {
                atomicBoolean.set(true);
                return super.removeGender();
            }
        };
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.removeGender());

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_addHeight_00001() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        final int originalHeight = 1;
        final Height height = new Height(originalHeight);

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback>() {

            @NonNull
            @Override
            public UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> addHeight(int responceCode, long delay, @NonNull byte[] value) {
                assertArrayEquals(height.getBytes(), value);
                atomicBoolean.set(true);
                return super.addGender(responceCode, delay, value);
            }

        };
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.addHeight(originalHeight));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_addHeight_00002() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        final int originalHeight = 1;
        final Height height = new Height(originalHeight);

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback>() {

            @NonNull
            @Override
            public UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> addHeight(int responceCode, long delay, @NonNull byte[] value) {
                assertArrayEquals(height.getBytes(), value);
                atomicBoolean.set(true);
                return super.addGender(responceCode, delay, value);
            }

        };
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.addHeight(height));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_addHeight_00003() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        final int originalHeight = 1;
        final Height height = new Height(originalHeight);

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback>() {

            @NonNull
            @Override
            public UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> addHeight(int responceCode, long delay, @NonNull byte[] value) {
                assertArrayEquals(height.getBytes(), value);
                atomicBoolean.set(true);
                return super.addGender(responceCode, delay, value);
            }

        };
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.addHeight(height.getBytes()));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_addHeight_00004() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        final int originalResponceCode = 1;
        final long originalDelay = 2;
        final int originalHeight = 3;
        final Height height = new Height(originalHeight);

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback>() {

            @NonNull
            @Override
            public UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> addHeight(int responceCode, long delay, @NonNull byte[] value) {
                assertEquals(originalResponceCode, responceCode);
                assertEquals(originalDelay, delay);
                assertArrayEquals(height.getBytes(), value);
                atomicBoolean.set(true);
                return super.addGender(responceCode, delay, value);
            }

        };
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.addHeight(originalResponceCode, originalDelay, height.getBytes()));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_removeHeight_00001() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback>() {

            @NonNull
            @Override
            public UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> removeHeight() {
                atomicBoolean.set(true);
                return super.removeGender();
            }
        };
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.removeHeight());

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_addDatabaseChangeIncrement_00001() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        final byte[] originalDescriptorValue = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE;
        final ClientCharacteristicConfiguration clientCharacteristicConfiguration = new ClientCharacteristicConfiguration(originalDescriptorValue);

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback>() {

            @NonNull
            @Override
            public UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> addDatabaseChangeIncrement(int characteristicResponceCode, long characteristicDelay, boolean isNotificatable, int descriptorResponceCode, long descriptorDelay, @NonNull byte[] descriptorValue) {
                assertArrayEquals(originalDescriptorValue, descriptorValue);
                atomicBoolean.set(true);
                return super.addDatabaseChangeIncrement(characteristicResponceCode, characteristicDelay, isNotificatable, descriptorResponceCode, descriptorDelay, descriptorValue);
            }

        };
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.addDatabaseChangeIncrement(clientCharacteristicConfiguration));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_addDatabaseChangeIncrement_00002() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        final byte[] originalDescriptorValue = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE;

        final int originalCharacteristicResponseCode = 1;
        final long originalCharacteristicDelay = 2;
        final boolean originalIsNotificatable = false;
        final int originalDescriptorResponseCode = 4;
        final long originalDescriptorDelay = 5;

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback>() {

            @NonNull
            @Override
            public UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> addDatabaseChangeIncrement(int characteristicResponceCode, long characteristicDelay, boolean isNotificatable, int descriptorResponceCode, long descriptorDelay, @NonNull byte[] descriptorValue) {
                assertEquals(originalCharacteristicResponseCode, characteristicResponceCode);
                assertEquals(originalCharacteristicDelay, characteristicDelay);
                assertEquals(originalIsNotificatable, isNotificatable);
                assertEquals(originalDescriptorResponseCode, descriptorResponceCode);
                assertEquals(originalDescriptorDelay, descriptorDelay);
                assertArrayEquals(originalDescriptorValue, descriptorValue);
                atomicBoolean.set(true);
                return super.addDatabaseChangeIncrement(characteristicResponceCode, characteristicDelay, isNotificatable, descriptorResponceCode, descriptorDelay, descriptorValue);
            }

        };
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.addDatabaseChangeIncrement(originalCharacteristicResponseCode, originalCharacteristicDelay, originalIsNotificatable, originalDescriptorResponseCode, originalDescriptorDelay, originalDescriptorValue));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_removeDatabaseChangeIncrement_00001() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback>() {

            @NonNull
            @Override
            public UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> removeDatabaseChangeIncrement() {
                atomicBoolean.set(true);
                return super.removeDatabaseChangeIncrement();
            }
        };
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.removeDatabaseChangeIncrement());

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_addUserIndex_00001() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback>() {

            @NonNull
            @Override
            public UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> addUserIndex(int responceCode, long delay) {
                atomicBoolean.set(true);
                return super.addUserIndex(responceCode, delay);
            }

        };
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.addUserIndex());

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_addUserIndex_00002() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        final int originalResponceCode = 1;
        final long originalDelay = 2;

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback>() {

            @NonNull
            @Override
            public UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> addUserIndex(int responceCode, long delay) {
                assertEquals(originalResponceCode, responceCode);
                assertEquals(originalDelay, delay);
                atomicBoolean.set(true);
                return super.addUserIndex(responceCode, delay);
            }

        };
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.addUserIndex(originalResponceCode, originalDelay));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_removeUserIndex_00001() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback>() {

            @NonNull
            @Override
            public UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> removeUserIndex() {
                atomicBoolean.set(true);
                return super.removeUserIndex();
            }
        };
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.removeUserIndex());

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_addRegisteredUser_00001() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        final byte[] originalDescriptorValue = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE;
        final ClientCharacteristicConfiguration clientCharacteristicConfiguration = new ClientCharacteristicConfiguration(originalDescriptorValue);

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback>() {

            @NonNull
            @Override
            public UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> addRegisteredUser(int characteristicResponceCode, long characteristicDelay, int descriptorResponceCode, long descriptorDelay, @NonNull byte[] descriptorValue) {
                assertArrayEquals(originalDescriptorValue, descriptorValue);
                atomicBoolean.set(true);
                return super.addRegisteredUser(characteristicResponceCode, characteristicDelay, descriptorResponceCode, descriptorDelay, descriptorValue);
            }

        };
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.addRegisteredUser(clientCharacteristicConfiguration));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_addRegisteredUser_00002() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        final byte[] originalDescriptorValue = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE;

        final int originalCharacteristicResponseCode = 1;
        final long originalCharacteristicDelay = 2;
        final int originalDescriptorResponseCode = 4;
        final long originalDescriptorDelay = 5;

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback>() {

            @NonNull
            @Override
            public UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> addRegisteredUser(int characteristicResponceCode, long characteristicDelay, int descriptorResponceCode, long descriptorDelay, @NonNull byte[] descriptorValue) {
                assertEquals(originalCharacteristicResponseCode, characteristicResponceCode);
                assertEquals(originalCharacteristicDelay, characteristicDelay);
                assertEquals(originalDescriptorResponseCode, descriptorResponceCode);
                assertEquals(originalDescriptorDelay, descriptorDelay);
                assertArrayEquals(originalDescriptorValue, descriptorValue);
                atomicBoolean.set(true);
                return super.addRegisteredUser(characteristicResponceCode, characteristicDelay, descriptorResponceCode, descriptorDelay, descriptorValue);
            }

        };
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.addRegisteredUser(originalCharacteristicResponseCode, originalCharacteristicDelay, originalDescriptorResponseCode, originalDescriptorDelay, originalDescriptorValue));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_removeRegisteredUser00001() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback>() {

            @NonNull
            @Override
            public UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> removeRegisteredUser() {
                atomicBoolean.set(true);
                return super.removeUserIndex();
            }
        };
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.removeRegisteredUser());

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_addUserControlPoint_00001() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        final long originalCharacteristicDelay = 1;
        final int originalRegisterNewUserResponceValue = 2;
        final int originalConsentResponceValue = 3;
        final int originalDeleteUserDataResponceValue = 4;
        final int originalListAllUsersResponceValue = 5;
        final int originalDeleteUsersResponceValue = 6;
        final int originalDescriptorResponceCode = 7;
        final long originalDescriptorDelay = 8;
        final byte[] originalDescriptorValue = BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE;
        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback>() {

            @NonNull
            @Override
            public UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> addUserControlPoint(long characteristicDelay, int registerNewUserResponceValue, int consentResponceValue, int deleteUserDataResponceValue, int listAllUsersResponceValue, int deleteUsersResponceValue, int descriptorResponceCode, long descriptorDelay, @NonNull byte[] descriptorValue) {
                assertEquals(originalCharacteristicDelay, characteristicDelay);
                assertEquals(originalRegisterNewUserResponceValue, registerNewUserResponceValue);
                assertEquals(originalConsentResponceValue, consentResponceValue);
                assertEquals(originalDeleteUserDataResponceValue, deleteUserDataResponceValue);
                assertEquals(originalListAllUsersResponceValue, listAllUsersResponceValue);
                assertEquals(originalDeleteUsersResponceValue, deleteUsersResponceValue);
                assertEquals(originalDescriptorResponceCode, descriptorResponceCode);
                assertEquals(originalDescriptorDelay, descriptorDelay);
                assertArrayEquals(originalDescriptorValue, descriptorValue);
                atomicBoolean.set(true);
                return super.addUserControlPoint(characteristicDelay, registerNewUserResponceValue, consentResponceValue, deleteUserDataResponceValue, listAllUsersResponceValue, deleteUsersResponceValue, descriptorResponceCode, descriptorDelay, descriptorValue);
            }

        };
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.addUserControlPoint(originalCharacteristicDelay
                , originalRegisterNewUserResponceValue
                , originalConsentResponceValue
                , originalDeleteUserDataResponceValue
                , originalListAllUsersResponceValue
                , originalDeleteUsersResponceValue
                , originalDescriptorResponceCode
                , originalDescriptorDelay
                , originalDescriptorValue));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_removeUserControlPoint_00001() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback>() {

            @NonNull
            @Override
            public UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> removeUserControlPoint() {
                atomicBoolean.set(true);
                return super.removeUserIndex();
            }
        };
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.removeUserControlPoint());

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_addBatteryLevel_00001() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        final int originalIndex = 1;
        final int originalBatteryLevel = 2;
        final BatteryLevel batteryLevel = new BatteryLevel(originalBatteryLevel);

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<>();
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<BatteryServiceMockCallback>() {

            @NonNull
            @Override
            public BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> addBatteryLevel(int index, int property, int responceCode, long delay, @NonNull byte[] value, int notificationCount) {
                assertEquals(originalIndex, index);
                assertArrayEquals(batteryLevel.getBytes(), value);
                atomicBoolean.set(true);
                return super.addBatteryLevel(index, property, responceCode, delay, value, notificationCount);
            }
        };
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.addBatteryLevel(originalIndex, batteryLevel));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_addBatteryLevel_00002() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        final int originalProperty = 1;
        final int originalResponceCode = 2;
        final long originalDelay = 3;
        final int originalNotificationCount = 4;
        final int originalIndex = 5;
        final int originalBatteryLevel = 6;
        final BatteryLevel batteryLevel = new BatteryLevel(originalBatteryLevel);

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<>();
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<BatteryServiceMockCallback>() {

            @NonNull
            @Override
            public BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> addBatteryLevel(int index, int property, int responceCode, long delay, @NonNull byte[] value, int notificationCount) {
                assertEquals(originalProperty, property);
                assertEquals(originalResponceCode, responceCode);
                assertEquals(originalDelay, delay);
                assertEquals(originalNotificationCount, notificationCount);
                assertEquals(originalIndex, index);
                assertArrayEquals(batteryLevel.getBytes(), value);
                atomicBoolean.set(true);
                return super.addBatteryLevel(index, property, responceCode, delay, value, notificationCount);
            }
        };
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.addBatteryLevel(originalIndex, originalProperty, originalResponceCode, originalDelay, batteryLevel.getBytes(), originalNotificationCount));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_removeBatteryLevel_00001() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        final int originalIndex = 1;

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<>();
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<BatteryServiceMockCallback>() {

            @NonNull
            @Override
            public BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> removeBatteryLevel(int index) {
                assertEquals(originalIndex, index);
                atomicBoolean.set(true);
                return super.removeBatteryLevel(index);
            }
        };
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.removeBatteryLevel(originalIndex));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_setBatteryLevelCharacteristicPresentationFormat_00001() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        final int originalIndex = 1;
        final byte[] originalValue = new byte[]{2, 3, 4, 5, 6, 7, 8};
        final CharacteristicPresentationFormat characteristicPresentationFormat = new CharacteristicPresentationFormat(originalValue);

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<>();
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<BatteryServiceMockCallback>() {

            @NonNull
            @Override
            public BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> setBatteryLevelCharacteristicPresentationFormat(int index, int responceCode, long delay, @NonNull byte[] value) {
                assertEquals(originalIndex, index);
                assertArrayEquals(originalValue, value);
                atomicBoolean.set(true);
                return super.setBatteryLevelCharacteristicPresentationFormat(index, responceCode, delay, value);
            }
        };
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.setBatteryLevelCharacteristicPresentationFormat(originalIndex, characteristicPresentationFormat));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_setBatteryLevelCharacteristicPresentationFormat_00002() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        final int originalResponceCode = 1;
        final long originalDelay = 2;
        final int originalIndex = 3;
        final byte[] originalValue = new byte[]{4, 5, 6, 7, 8, 9, 10};

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<>();
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<BatteryServiceMockCallback>() {

            @NonNull
            @Override
            public BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> setBatteryLevelCharacteristicPresentationFormat(int index, int responceCode, long delay, @NonNull byte[] value) {
                assertEquals(originalResponceCode, responceCode);
                assertEquals(originalDelay, delay);
                assertEquals(originalIndex, index);
                assertArrayEquals(originalValue, value);
                atomicBoolean.set(true);
                return super.setBatteryLevelCharacteristicPresentationFormat(index, responceCode, delay, value);
            }
        };
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.setBatteryLevelCharacteristicPresentationFormat(originalIndex, originalResponceCode, originalDelay, originalValue));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_removeBatteryLevelCharacteristicPresentationFormat_00001() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        final int originalIndex = 1;

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<>();
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<BatteryServiceMockCallback>() {

            @NonNull
            @Override
            public BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> removeBatteryLevelCharacteristicPresentationFormat(int index) {
                assertEquals(originalIndex, index);
                atomicBoolean.set(true);
                return super.removeBatteryLevelCharacteristicPresentationFormat(index);
            }
        };
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.removeBatteryLevelCharacteristicPresentationFormat(originalIndex));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_setBatteryLevelClientCharacteristicConfiguration_00001() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        final int originalIndex = 1;
        final byte[] originalValue = BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE;
        final ClientCharacteristicConfiguration clientCharacteristicConfiguration = new ClientCharacteristicConfiguration(originalValue);

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<>();
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<BatteryServiceMockCallback>() {

            @NonNull
            @Override
            public BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> setBatteryLevelClientCharacteristicConfiguration(int index, int responceCode, long delay, @NonNull byte[] value) {
                assertEquals(originalIndex, index);
                assertArrayEquals(originalValue, value);
                atomicBoolean.set(true);
                return super.setBatteryLevelClientCharacteristicConfiguration(index, responceCode, delay, value);
            }
        };
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.setBatteryLevelClientCharacteristicConfiguration(originalIndex, clientCharacteristicConfiguration));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_setBatteryLevelClientCharacteristicConfiguration_00002() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        final int originalResponceCode = 1;
        final long originalDelay = 2;
        final int originalIndex = 3;
        final byte[] originalValue = BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE;

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<>();
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<BatteryServiceMockCallback>() {

            @NonNull
            @Override
            public BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> setBatteryLevelClientCharacteristicConfiguration(int index, int responceCode, long delay, @NonNull byte[] value) {
                assertEquals(originalResponceCode, responceCode);
                assertEquals(originalDelay, delay);
                assertEquals(originalIndex, index);
                assertArrayEquals(originalValue, value);
                atomicBoolean.set(true);
                return super.setBatteryLevelClientCharacteristicConfiguration(index, responceCode, delay, value);
            }
        };
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.setBatteryLevelClientCharacteristicConfiguration(originalIndex, originalResponceCode, originalDelay, originalValue));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_removeBatteryLevelClientCharacteristicConfiguration_00001() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        final int originalIndex = 1;

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<>();
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<BatteryServiceMockCallback>() {

            @NonNull
            @Override
            public BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> removeBatteryLevelClientCharacteristicConfiguration(int index) {
                assertEquals(originalIndex, index);
                atomicBoolean.set(true);
                return super.removeBatteryLevelClientCharacteristicConfiguration(index);
            }
        };
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.removeBatteryLevelClientCharacteristicConfiguration(originalIndex));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_addCurrentTime_00001() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        final byte[] originalCharacteristicValue = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        final CurrentTime currentTime = new CurrentTime(originalCharacteristicValue);
        final byte[] originalDescriptorValue = BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE;
        final ClientCharacteristicConfiguration clientCharacteristicConfiguration = new ClientCharacteristicConfiguration(originalDescriptorValue);

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<>();
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback>() {

            @NonNull
            @Override
            public CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> addCurrentTime(boolean isWritable, int characteristicResponceCode, long characteristicDelay, @NonNull byte[] characteristicValue, int notificationCount, int descriptorResponceCode, long descriptorDelay, @NonNull byte[] descriptorValue) {
                assertArrayEquals(originalCharacteristicValue, characteristicValue);
                assertArrayEquals(originalDescriptorValue, descriptorValue);
                atomicBoolean.set(true);
                return super.addCurrentTime(isWritable, characteristicResponceCode, characteristicDelay, characteristicValue, notificationCount, descriptorResponceCode, descriptorDelay, descriptorValue);
            }
        };
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.addCurrentTime(currentTime, clientCharacteristicConfiguration));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_addCurrentTime_00002() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        final boolean originalIsWritable = false;
        final int originalCharacteristicResponseCode = 1;
        final long originalCharacteristicDelay = 2;
        final int originalNotificationCount = 3;
        final int originalDescriptorResponseCode = 4;
        final long originalDescriptorDelay = 5;
        final byte[] originalCharacteristicValue = new byte[]{6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
        final byte[] originalDescriptorValue = BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE;

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<>();
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback>() {

            @NonNull
            @Override
            public CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> addCurrentTime(boolean isWritable, int characteristicResponceCode, long characteristicDelay, @NonNull byte[] characteristicValue, int notificationCount, int descriptorResponceCode, long descriptorDelay, @NonNull byte[] descriptorValue) {
                assertEquals(originalIsWritable, isWritable);
                assertEquals(originalCharacteristicResponseCode, characteristicResponceCode);
                assertEquals(originalCharacteristicDelay, characteristicDelay);
                assertArrayEquals(originalCharacteristicValue, characteristicValue);
                assertEquals(originalNotificationCount, notificationCount);
                assertEquals(originalDescriptorResponseCode, descriptorResponceCode);
                assertEquals(originalDescriptorDelay, descriptorDelay);
                assertArrayEquals(originalDescriptorValue, descriptorValue);
                atomicBoolean.set(true);
                return super.addCurrentTime(isWritable, characteristicResponceCode, characteristicDelay, characteristicValue, notificationCount, descriptorResponceCode, descriptorDelay, descriptorValue);
            }
        };
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.addCurrentTime(originalIsWritable, originalCharacteristicResponseCode, originalCharacteristicDelay, originalCharacteristicValue, originalNotificationCount, originalDescriptorResponseCode, originalDescriptorDelay, originalDescriptorValue));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_removeCurrentTime_00001() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<>();
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback>() {

            @NonNull
            @Override
            public CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> removeCurrentTime() {
                atomicBoolean.set(true);
                return super.removeCurrentTime();
            }
        };
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.removeCurrentTime());

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_addLocalTimeInformation_00001() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        final byte[] originalValue = new byte[]{1, 2};
        final LocalTimeInformation localTimeInformation = new LocalTimeInformation(originalValue);

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<>();
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback>() {

            @NonNull
            @Override
            public CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> addLocalTimeInformation(boolean isWritable, int responceCode, long delay, @NonNull byte[] value) {
                assertArrayEquals(originalValue, value);
                atomicBoolean.set(true);
                return super.addLocalTimeInformation(isWritable, responceCode, delay, value);
            }
        };
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.addLocalTimeInformation(localTimeInformation));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_addLocalTimeInformation_00002() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        final byte[] originalValue = new byte[]{1, 2};

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<>();
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback>() {

            @NonNull
            @Override
            public CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> addLocalTimeInformation(boolean isWritable, int responceCode, long delay, @NonNull byte[] value) {
                assertArrayEquals(originalValue, value);
                atomicBoolean.set(true);
                return super.addLocalTimeInformation(isWritable, responceCode, delay, value);
            }
        };
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.addLocalTimeInformation(originalValue));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_addLocalTimeInformation_00003() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        final boolean originalIsWritable = false;
        final int originalResponseCode = 0;
        final long originalDelay = 1;
        final byte[] originalValue = new byte[]{2, 3};

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<>();
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback>() {

            @NonNull
            @Override
            public CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> addLocalTimeInformation(boolean isWritable, int responceCode, long delay, @NonNull byte[] value) {
                assertEquals(originalIsWritable, isWritable);
                assertEquals(originalResponseCode, responceCode);
                assertEquals(originalDelay, delay);
                assertArrayEquals(originalValue, value);
                atomicBoolean.set(true);
                return super.addLocalTimeInformation(isWritable, responceCode, delay, value);
            }
        };
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.addLocalTimeInformation(originalIsWritable, originalResponseCode, originalDelay, originalValue));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_removeLocalTimeInformation_00001() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<>();
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback>() {

            @NonNull
            @Override
            public CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> removeLocalTimeInformation() {
                atomicBoolean.set(true);
                return super.removeLocalTimeInformation();
            }
        };
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.removeLocalTimeInformation());

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_addReferenceTimeInformation_00001() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        final byte[] originalValue = new byte[]{1, 2, 3, 4};
        final ReferenceTimeInformation referenceTimeInformation = new ReferenceTimeInformation(originalValue);

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<>();
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback>() {

            @NonNull
            @Override
            public CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> addReferenceTimeInformation(int responceCode, long delay, @NonNull byte[] value) {
                assertArrayEquals(originalValue, value);
                atomicBoolean.set(true);
                return super.addReferenceTimeInformation(responceCode, delay, value);
            }
        };
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.addReferenceTimeInformation(referenceTimeInformation));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_addReferenceTimeInformation_00002() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        final byte[] originalValue = new byte[]{1, 2, 3, 4};

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<>();
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback>() {

            @NonNull
            @Override
            public CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> addReferenceTimeInformation(int responceCode, long delay, @NonNull byte[] value) {
                assertArrayEquals(originalValue, value);
                atomicBoolean.set(true);
                return super.addReferenceTimeInformation(responceCode, delay, value);
            }
        };
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.addReferenceTimeInformation(originalValue));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_addReferenceTimeInformation_00003() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        final int originalResponseCode = 0;
        final long originalDelay = 1;
        final byte[] originalValue = new byte[]{2, 3, 4, 5};

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<>();
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback>() {

            @NonNull
            @Override
            public CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> addReferenceTimeInformation(int responceCode, long delay, @NonNull byte[] value) {
                assertEquals(originalResponseCode, responceCode);
                assertEquals(originalDelay, delay);
                assertArrayEquals(originalValue, value);
                atomicBoolean.set(true);
                return super.addReferenceTimeInformation(responceCode, delay, value);
            }
        };
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.addReferenceTimeInformation(originalResponseCode, originalDelay, originalValue));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_removeReferenceTimeInformation_00001() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        Context context = ApplicationProvider.getApplicationContext();
        WeightScaleServiceMockCallback.Builder<WspWeightScaleServiceMockCallback> weightScaleServiceMockCallbackBuilder = new WspWeightScaleServiceMockCallback.Builder<>();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        UserDataServiceMockCallback.Builder<WspUserDataServiceMockCallback> userDataServiceMockCallbackBuilder = new WspUserDataServiceMockCallback.Builder<>();
        BatteryServiceMockCallback.Builder<BatteryServiceMockCallback> batteryServiceMockCallbackBuilder = new BatteryServiceMockCallback.Builder<>();
        CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> currentTimeServiceMockCallbackBuilder = new CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback>() {

            @NonNull
            @Override
            public CurrentTimeServiceMockCallback.Builder<CurrentTimeServiceMockCallback> removeReferenceTimeInformation() {
                atomicBoolean.set(true);
                return super.removeReferenceTimeInformation();
            }
        };
        BaseBuilder baseBuilder = new BaseBuilder(context
                , weightScaleServiceMockCallbackBuilder
                , deviceInformationServiceMockCallbackBuilder
                , userDataServiceMockCallbackBuilder
                , batteryServiceMockCallbackBuilder
                , currentTimeServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.removeReferenceTimeInformation());

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_build_00001() {
        Exception exception = null;
        try {
            new BaseBuilder(ApplicationProvider.getApplicationContext()
                    , new WeightScaleServiceMockCallback.Builder<>()
                    , new DeviceInformationServiceMockCallback.Builder<>()
                    , new UserDataServiceMockCallback.Builder<>()
                    , new BatteryServiceMockCallback.Builder<>()
                    , new CurrentTimeServiceMockCallback.Builder<>())
                    .build();
        } catch (Exception e) {
            exception = e;
        }

        assertNotNull(exception);
        assertEquals("no Weight Scale Feature data", exception.getMessage());
    }

    @Test
    public void test_build_00101() {
        Exception exception = null;
        try {
            new BaseBuilder(ApplicationProvider.getApplicationContext()
                    , new WeightScaleServiceMockCallback.Builder<>()
                    , new DeviceInformationServiceMockCallback.Builder<>()
                    , null
                    , new BatteryServiceMockCallback.Builder<>()
                    , new CurrentTimeServiceMockCallback.Builder<>())
                    .addWeightScaleFeature(new WeightScaleFeature(false, true, false, WeightScaleFeature.WEIGHT_SCALE_FEATURE_WEIGHT_MEASUREMENT_RESOLUTION_NOT_SPECIFIED, WeightScaleFeature.WEIGHT_SCALE_FEATURE_HEIGHT_MEASUREMENT_RESOLUTION_NOT_SPECIFIED))
                    .build();
        } catch (Exception e) {
            exception = e;
        }

        assertNotNull(exception);
        assertEquals("no User Data Service", exception.getMessage());
    }

    @Test
    public void test_build_00102() {
        Exception exception = null;
        try {
            new BaseBuilder(ApplicationProvider.getApplicationContext()
                    , new WeightScaleServiceMockCallback.Builder<>()
                    , new DeviceInformationServiceMockCallback.Builder<>()
                    , null
                    , new BatteryServiceMockCallback.Builder<>()
                    , new CurrentTimeServiceMockCallback.Builder<>())
                    .addWeightScaleFeature(new WeightScaleFeature(false, true, false, WeightScaleFeature.WEIGHT_SCALE_FEATURE_WEIGHT_MEASUREMENT_RESOLUTION_NOT_SPECIFIED, WeightScaleFeature.WEIGHT_SCALE_FEATURE_HEIGHT_MEASUREMENT_RESOLUTION_NOT_SPECIFIED).getBytes())
                    .build();
        } catch (Exception e) {
            exception = e;
        }

        assertNotNull(exception);
        assertEquals("no User Data Service", exception.getMessage());
    }

    @Test
    public void test_build_00103() {
        Exception exception = null;
        try {
            new BaseBuilder(ApplicationProvider.getApplicationContext()
                    , new WeightScaleServiceMockCallback.Builder<>()
                    , new DeviceInformationServiceMockCallback.Builder<>()
                    , null
                    , new BatteryServiceMockCallback.Builder<>()
                    , new CurrentTimeServiceMockCallback.Builder<>())
                    .addWeightScaleFeature(0, 1, new WeightScaleFeature(false, true, false, WeightScaleFeature.WEIGHT_SCALE_FEATURE_WEIGHT_MEASUREMENT_RESOLUTION_NOT_SPECIFIED, WeightScaleFeature.WEIGHT_SCALE_FEATURE_HEIGHT_MEASUREMENT_RESOLUTION_NOT_SPECIFIED).getBytes())
                    .build();
        } catch (Exception e) {
            exception = e;
        }

        assertNotNull(exception);
        assertEquals("no User Data Service", exception.getMessage());
    }

    @Test
    public void test_build_00201() {
        Exception exception = null;
        try {
            new BaseBuilder(ApplicationProvider.getApplicationContext()
                    , new WeightScaleServiceMockCallback.Builder<>()
                    , new DeviceInformationServiceMockCallback.Builder<>()
                    , null
                    , new BatteryServiceMockCallback.Builder<>()
                    , new CurrentTimeServiceMockCallback.Builder<>())
                    .addWeightScaleFeature(new WeightScaleFeature(false, false, false, WeightScaleFeature.WEIGHT_SCALE_FEATURE_WEIGHT_MEASUREMENT_RESOLUTION_NOT_SPECIFIED, WeightScaleFeature.WEIGHT_SCALE_FEATURE_HEIGHT_MEASUREMENT_RESOLUTION_NOT_SPECIFIED))
                    .build();
        } catch (Exception e) {
            exception = e;
        }

        assertNotNull(exception);
        assertEquals("no Manufacturer Name String data", exception.getMessage());
    }

    @Test
    public void test_build_00301() {
        Exception exception = null;
        try {
            new BaseBuilder(ApplicationProvider.getApplicationContext()
                    , new WeightScaleServiceMockCallback.Builder<>()
                    , new DeviceInformationServiceMockCallback.Builder<>()
                    , null
                    , new BatteryServiceMockCallback.Builder<>()
                    , new CurrentTimeServiceMockCallback.Builder<>())
                    .addWeightScaleFeature(new WeightScaleFeature(false, false, false, WeightScaleFeature.WEIGHT_SCALE_FEATURE_WEIGHT_MEASUREMENT_RESOLUTION_NOT_SPECIFIED, WeightScaleFeature.WEIGHT_SCALE_FEATURE_HEIGHT_MEASUREMENT_RESOLUTION_NOT_SPECIFIED))
                    .addManufacturerNameString("ManufacturerNameString")
                    .build();
        } catch (Exception e) {
            exception = e;
        }

        assertNotNull(exception);
        assertEquals("no Model Number String data", exception.getMessage());
    }

    @Test
    public void test_build_00302() {
        Exception exception = null;
        try {
            new BaseBuilder(ApplicationProvider.getApplicationContext()
                    , new WeightScaleServiceMockCallback.Builder<>()
                    , new DeviceInformationServiceMockCallback.Builder<>()
                    , null
                    , new BatteryServiceMockCallback.Builder<>()
                    , new CurrentTimeServiceMockCallback.Builder<>())
                    .addWeightScaleFeature(new WeightScaleFeature(false, false, false, WeightScaleFeature.WEIGHT_SCALE_FEATURE_WEIGHT_MEASUREMENT_RESOLUTION_NOT_SPECIFIED, WeightScaleFeature.WEIGHT_SCALE_FEATURE_HEIGHT_MEASUREMENT_RESOLUTION_NOT_SPECIFIED))
                    .addManufacturerNameString(new ManufacturerNameString("ManufacturerNameString"))
                    .build();
        } catch (Exception e) {
            exception = e;
        }

        assertNotNull(exception);
        assertEquals("no Model Number String data", exception.getMessage());
    }

    @Test
    public void test_build_00303() {
        Exception exception = null;
        try {
            new BaseBuilder(ApplicationProvider.getApplicationContext()
                    , new WeightScaleServiceMockCallback.Builder<>()
                    , new DeviceInformationServiceMockCallback.Builder<>()
                    , null
                    , new BatteryServiceMockCallback.Builder<>()
                    , new CurrentTimeServiceMockCallback.Builder<>())
                    .addWeightScaleFeature(new WeightScaleFeature(false, false, false, WeightScaleFeature.WEIGHT_SCALE_FEATURE_WEIGHT_MEASUREMENT_RESOLUTION_NOT_SPECIFIED, WeightScaleFeature.WEIGHT_SCALE_FEATURE_HEIGHT_MEASUREMENT_RESOLUTION_NOT_SPECIFIED))
                    .addManufacturerNameString(new ManufacturerNameString("ManufacturerNameString").getBytes())
                    .build();
        } catch (Exception e) {
            exception = e;
        }

        assertNotNull(exception);
        assertEquals("no Model Number String data", exception.getMessage());
    }

    @Test
    public void test_build_00304() {
        Exception exception = null;
        try {
            new BaseBuilder(ApplicationProvider.getApplicationContext()
                    , new WeightScaleServiceMockCallback.Builder<>()
                    , new DeviceInformationServiceMockCallback.Builder<>()
                    , null
                    , new BatteryServiceMockCallback.Builder<>()
                    , new CurrentTimeServiceMockCallback.Builder<>())
                    .addWeightScaleFeature(new WeightScaleFeature(false, false, false, WeightScaleFeature.WEIGHT_SCALE_FEATURE_WEIGHT_MEASUREMENT_RESOLUTION_NOT_SPECIFIED, WeightScaleFeature.WEIGHT_SCALE_FEATURE_HEIGHT_MEASUREMENT_RESOLUTION_NOT_SPECIFIED))
                    .addManufacturerNameString(0, 1, new ManufacturerNameString("ManufacturerNameString").getBytes())
                    .build();
        } catch (Exception e) {
            exception = e;
        }

        assertNotNull(exception);
        assertEquals("no Model Number String data", exception.getMessage());
    }

    @Test
    public void test_build_00401() {
        Exception exception = null;
        try {
            new BaseBuilder(ApplicationProvider.getApplicationContext()
                    , new WeightScaleServiceMockCallback.Builder<>()
                    , new DeviceInformationServiceMockCallback.Builder<>()
                    , null
                    , new BatteryServiceMockCallback.Builder<>()
                    , new CurrentTimeServiceMockCallback.Builder<>())
                    .addWeightScaleFeature(new WeightScaleFeature(false, false, false, WeightScaleFeature.WEIGHT_SCALE_FEATURE_WEIGHT_MEASUREMENT_RESOLUTION_NOT_SPECIFIED, WeightScaleFeature.WEIGHT_SCALE_FEATURE_HEIGHT_MEASUREMENT_RESOLUTION_NOT_SPECIFIED))
                    .addWeightMeasurement(new WeightMeasurement(new byte[3]), new ClientCharacteristicConfiguration(BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE))
                    .addManufacturerNameString("ManufacturerNameString")
                    .addModelNumberString("ModelNumberString")
                    .addCurrentTime(new CurrentTime(new byte[10]), new ClientCharacteristicConfiguration(BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE))
                    .build();
        } catch (Exception e) {
            exception = e;
        }

        assertNull(exception);
    }

    @Test
    public void test_build_00402() {
        Exception exception = null;
        try {
            new BaseBuilder(ApplicationProvider.getApplicationContext()
                    , new WeightScaleServiceMockCallback.Builder<>()
                    , new DeviceInformationServiceMockCallback.Builder<>()
                    , null
                    , new BatteryServiceMockCallback.Builder<>()
                    , new CurrentTimeServiceMockCallback.Builder<>())
                    .addWeightScaleFeature(new WeightScaleFeature(false, false, false, WeightScaleFeature.WEIGHT_SCALE_FEATURE_WEIGHT_MEASUREMENT_RESOLUTION_NOT_SPECIFIED, WeightScaleFeature.WEIGHT_SCALE_FEATURE_HEIGHT_MEASUREMENT_RESOLUTION_NOT_SPECIFIED))
                    .addWeightMeasurement(new WeightMeasurement(new byte[3]), new ClientCharacteristicConfiguration(BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE))
                    .addManufacturerNameString("ManufacturerNameString")
                    .addModelNumberString(new ModelNumberString("ModelNumberString"))
                    .addCurrentTime(new CurrentTime(new byte[10]), new ClientCharacteristicConfiguration(BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE))
                    .build();
        } catch (Exception e) {
            exception = e;
        }

        assertNull(exception);
    }

    @Test
    public void test_build_00403() {
        Exception exception = null;
        try {
            new BaseBuilder(ApplicationProvider.getApplicationContext()
                    , new WeightScaleServiceMockCallback.Builder<>()
                    , new DeviceInformationServiceMockCallback.Builder<>()
                    , null
                    , new BatteryServiceMockCallback.Builder<>()
                    , new CurrentTimeServiceMockCallback.Builder<>())
                    .addWeightScaleFeature(new WeightScaleFeature(false, false, false, WeightScaleFeature.WEIGHT_SCALE_FEATURE_WEIGHT_MEASUREMENT_RESOLUTION_NOT_SPECIFIED, WeightScaleFeature.WEIGHT_SCALE_FEATURE_HEIGHT_MEASUREMENT_RESOLUTION_NOT_SPECIFIED))
                    .addWeightMeasurement(new WeightMeasurement(new byte[3]), new ClientCharacteristicConfiguration(BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE))
                    .addManufacturerNameString("ManufacturerNameString")
                    .addModelNumberString(new ModelNumberString("ModelNumberString").getBytes())
                    .addCurrentTime(new CurrentTime(new byte[10]), new ClientCharacteristicConfiguration(BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE))
                    .build();
        } catch (Exception e) {
            exception = e;
        }

        assertNull(exception);
    }

    @Test
    public void test_build_00404() {
        Exception exception = null;
        try {
            new BaseBuilder(ApplicationProvider.getApplicationContext()
                    , new WeightScaleServiceMockCallback.Builder<>()
                    , new DeviceInformationServiceMockCallback.Builder<>()
                    , null
                    , new BatteryServiceMockCallback.Builder<>()
                    , new CurrentTimeServiceMockCallback.Builder<>())
                    .addWeightScaleFeature(new WeightScaleFeature(false, false, false, WeightScaleFeature.WEIGHT_SCALE_FEATURE_WEIGHT_MEASUREMENT_RESOLUTION_NOT_SPECIFIED, WeightScaleFeature.WEIGHT_SCALE_FEATURE_HEIGHT_MEASUREMENT_RESOLUTION_NOT_SPECIFIED))
                    .addWeightMeasurement(new WeightMeasurement(new byte[3]), new ClientCharacteristicConfiguration(BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE))
                    .addManufacturerNameString("ManufacturerNameString")
                    .addModelNumberString(0, 1, new ModelNumberString("ModelNumberString").getBytes())
                    .addCurrentTime(new CurrentTime(new byte[10]), new ClientCharacteristicConfiguration(BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE))
                    .build();
        } catch (Exception e) {
            exception = e;
        }

        assertNull(exception);
    }

}
