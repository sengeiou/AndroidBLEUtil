package org.im97mori.ble.profile.blp.peripheral;

import android.bluetooth.BluetoothGattDescriptor;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.test.core.app.ApplicationProvider;

import org.im97mori.ble.characteristic.core.IEEE_11073_20601_SFLOAT;
import org.im97mori.ble.characteristic.u2a24.ModelNumberString;
import org.im97mori.ble.characteristic.u2a29.ManufacturerNameString;
import org.im97mori.ble.characteristic.u2a35.BloodPressureMeasurement;
import org.im97mori.ble.characteristic.u2a36.IntermediateCuffPressure;
import org.im97mori.ble.characteristic.u2a49.BloodPressureFeature;
import org.im97mori.ble.descriptor.u2902.ClientCharacteristicConfiguration;
import org.im97mori.ble.service.bls.peripheral.BloodPressureServiceMockCallback;
import org.im97mori.ble.service.dis.peripheral.DeviceInformationServiceMockCallback;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class BloodPressureProfileMockCallbackBuilderTest {

    @Test
    public void test_constructor_00001() {
        Context context = ApplicationProvider.getApplicationContext();
        BaseBuilder baseBuilder = new BaseBuilder(context);

        assertEquals(context, baseBuilder.mContext);
        assertNotNull(baseBuilder.mDeviceInformationServiceMockCallbackBuilder);
        assertNotNull(baseBuilder.mBloodPressureServiceMockCallbackBuilder);
    }

    @Test
    public void test_constructor_00002() {
        Context context = ApplicationProvider.getApplicationContext();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        BloodPressureServiceMockCallback.Builder<BloodPressureServiceMockCallback> bloodPressureServiceMockCallbackBuilder = new BloodPressureServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context, deviceInformationServiceMockCallbackBuilder, bloodPressureServiceMockCallbackBuilder);

        assertEquals(context, baseBuilder.mContext);
        assertEquals(deviceInformationServiceMockCallbackBuilder, baseBuilder.mDeviceInformationServiceMockCallbackBuilder);
        assertEquals(bloodPressureServiceMockCallbackBuilder, baseBuilder.mBloodPressureServiceMockCallbackBuilder);
    }

    @Test
    public void test_addManufacturerNameString_00001() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        final String manufacturerName = "manufacturerName";

        Context context = ApplicationProvider.getApplicationContext();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback>() {
            @NonNull
            @Override
            public DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> addManufacturerNameString(int responceCode, long delay, @NonNull byte[] value) {
                assertArrayEquals(manufacturerName.getBytes(), value);
                atomicBoolean.set(true);
                return super.addManufacturerNameString(responceCode, delay, value);
            }
        };
        BloodPressureServiceMockCallback.Builder<BloodPressureServiceMockCallback> bloodPressureServiceMockCallbackBuilder = new BloodPressureServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context, deviceInformationServiceMockCallbackBuilder, bloodPressureServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.addManufacturerNameString(manufacturerName));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_addManufacturerNameString_00002() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        final String manufacturerName = "manufacturerName";
        ManufacturerNameString manufacturerNameString = new ManufacturerNameString(manufacturerName);

        Context context = ApplicationProvider.getApplicationContext();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback>() {
            @NonNull
            @Override
            public DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> addManufacturerNameString(int responceCode, long delay, @NonNull byte[] value) {
                assertArrayEquals(manufacturerName.getBytes(), value);
                atomicBoolean.set(true);
                return super.addManufacturerNameString(responceCode, delay, value);
            }
        };
        BloodPressureServiceMockCallback.Builder<BloodPressureServiceMockCallback> bloodPressureServiceMockCallbackBuilder = new BloodPressureServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context, deviceInformationServiceMockCallbackBuilder, bloodPressureServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.addManufacturerNameString(manufacturerNameString));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_addManufacturerNameString_00003() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        final byte[] originalValue = "manufacturerName".getBytes();

        Context context = ApplicationProvider.getApplicationContext();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback>() {
            @NonNull
            @Override
            public DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> addManufacturerNameString(int responceCode, long delay, @NonNull byte[] value) {
                assertArrayEquals(originalValue, value);
                atomicBoolean.set(true);
                return super.addManufacturerNameString(responceCode, delay, value);
            }
        };
        BloodPressureServiceMockCallback.Builder<BloodPressureServiceMockCallback> bloodPressureServiceMockCallbackBuilder = new BloodPressureServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context, deviceInformationServiceMockCallbackBuilder, bloodPressureServiceMockCallbackBuilder);
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
        BloodPressureServiceMockCallback.Builder<BloodPressureServiceMockCallback> bloodPressureServiceMockCallbackBuilder = new BloodPressureServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context, deviceInformationServiceMockCallbackBuilder, bloodPressureServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.addManufacturerNameString(originalResponceCode, originalDelay, originalValue));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_removeManufacturerNameString_00001() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        Context context = ApplicationProvider.getApplicationContext();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback>() {

            @NonNull
            @Override
            public DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> removeManufacturerNameString() {
                atomicBoolean.set(true);
                return super.removeManufacturerNameString();
            }
        };
        BloodPressureServiceMockCallback.Builder<BloodPressureServiceMockCallback> bloodPressureServiceMockCallbackBuilder = new BloodPressureServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context, deviceInformationServiceMockCallbackBuilder, bloodPressureServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.removeManufacturerNameString());

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_addModelNumberString_00001() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        final String modelNumber = "modelNumber";

        Context context = ApplicationProvider.getApplicationContext();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback>() {
            @NonNull
            @Override
            public DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> addModelNumberString(int responceCode, long delay, @NonNull byte[] value) {
                assertArrayEquals(modelNumber.getBytes(), value);
                atomicBoolean.set(true);
                return super.addModelNumberString(responceCode, delay, value);
            }
        };
        BloodPressureServiceMockCallback.Builder<BloodPressureServiceMockCallback> bloodPressureServiceMockCallbackBuilder = new BloodPressureServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context, deviceInformationServiceMockCallbackBuilder, bloodPressureServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.addModelNumberString(modelNumber));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_addModelNumberString_00002() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        final String modelNumber = "modelNumber";
        ModelNumberString modelNumberString = new ModelNumberString(modelNumber);

        Context context = ApplicationProvider.getApplicationContext();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback>() {
            @NonNull
            @Override
            public DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> addModelNumberString(int responceCode, long delay, @NonNull byte[] value) {
                assertArrayEquals(modelNumber.getBytes(), value);
                atomicBoolean.set(true);
                return super.addModelNumberString(responceCode, delay, value);
            }
        };
        BloodPressureServiceMockCallback.Builder<BloodPressureServiceMockCallback> bloodPressureServiceMockCallbackBuilder = new BloodPressureServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context, deviceInformationServiceMockCallbackBuilder, bloodPressureServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.addModelNumberString(modelNumberString));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_addModelNumberString_00003() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        final byte[] originalValue = "modelNumber".getBytes();

        Context context = ApplicationProvider.getApplicationContext();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback>() {
            @NonNull
            @Override
            public DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> addModelNumberString(int responceCode, long delay, @NonNull byte[] value) {
                assertArrayEquals(originalValue, value);
                atomicBoolean.set(true);
                return super.addModelNumberString(responceCode, delay, value);
            }
        };
        BloodPressureServiceMockCallback.Builder<BloodPressureServiceMockCallback> bloodPressureServiceMockCallbackBuilder = new BloodPressureServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context, deviceInformationServiceMockCallbackBuilder, bloodPressureServiceMockCallbackBuilder);
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
        BloodPressureServiceMockCallback.Builder<BloodPressureServiceMockCallback> bloodPressureServiceMockCallbackBuilder = new BloodPressureServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context, deviceInformationServiceMockCallbackBuilder, bloodPressureServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.addModelNumberString(originalResponceCode, originalDelay, originalValue));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_removeModelNumberString_00001() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        Context context = ApplicationProvider.getApplicationContext();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback>() {

            @NonNull
            @Override
            public DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> removeModelNumberString() {
                atomicBoolean.set(true);
                return super.removeModelNumberString();
            }
        };
        BloodPressureServiceMockCallback.Builder<BloodPressureServiceMockCallback> bloodPressureServiceMockCallbackBuilder = new BloodPressureServiceMockCallback.Builder<>();
        BaseBuilder baseBuilder = new BaseBuilder(context, deviceInformationServiceMockCallbackBuilder, bloodPressureServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.removeModelNumberString());

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_addBloodPressureMeasurement_00001() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        int bpmflags = 1;
        IEEE_11073_20601_SFLOAT bpmbloodPressureMeasurementCompoundValueSystolicMmhg = new IEEE_11073_20601_SFLOAT(new byte[]{2, 3, 4, 5}, 0);
        IEEE_11073_20601_SFLOAT bpmbloodPressureMeasurementCompoundValueDiastolicMmhg = new IEEE_11073_20601_SFLOAT(new byte[]{6, 7, 8, 9}, 0);
        IEEE_11073_20601_SFLOAT bpmbloodPressureMeasurementCompoundValueMeanArterialPressureMmhg = new IEEE_11073_20601_SFLOAT(new byte[]{10, 11, 12, 13}, 0);
        IEEE_11073_20601_SFLOAT bpmbloodPressureMeasurementCompoundValueSystolicKpa = new IEEE_11073_20601_SFLOAT(new byte[]{14, 15, 16, 17}, 0);
        IEEE_11073_20601_SFLOAT bpmbloodPressureMeasurementCompoundValueDiastolicKpa = new IEEE_11073_20601_SFLOAT(new byte[]{18, 19, 20, 21}, 0);
        IEEE_11073_20601_SFLOAT bpmbloodPressureMeasurementCompoundValueMeanArterialPressureKpa = new IEEE_11073_20601_SFLOAT(new byte[]{22, 23, 24, 25}, 0);
        int bpmyear = 26;
        int bpmmonth = 27;
        int bpmday = 28;
        int bpmhours = 29;
        int bpmminutes = 30;
        int bpmseconds = 31;
        IEEE_11073_20601_SFLOAT bpmpulseRate = new IEEE_11073_20601_SFLOAT(new byte[]{32, 33, 34, 35}, 0);
        int bpmuserId = 36;
        byte[] bpmmeasurementStatus = new byte[]{37};
        final BloodPressureMeasurement bloodPressureMeasurement = new BloodPressureMeasurement(bpmflags, bpmbloodPressureMeasurementCompoundValueSystolicMmhg, bpmbloodPressureMeasurementCompoundValueDiastolicMmhg, bpmbloodPressureMeasurementCompoundValueMeanArterialPressureMmhg, bpmbloodPressureMeasurementCompoundValueSystolicKpa, bpmbloodPressureMeasurementCompoundValueDiastolicKpa, bpmbloodPressureMeasurementCompoundValueMeanArterialPressureKpa, bpmyear, bpmmonth, bpmday, bpmhours, bpmminutes, bpmseconds, bpmpulseRate, bpmuserId, bpmmeasurementStatus);

        byte[] bpmdescriptorValue = BluetoothGattDescriptor.ENABLE_INDICATION_VALUE;
        final ClientCharacteristicConfiguration bpmclientCharacteristicConfiguration = new ClientCharacteristicConfiguration(bpmdescriptorValue);

        Context context = ApplicationProvider.getApplicationContext();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        BloodPressureServiceMockCallback.Builder<BloodPressureServiceMockCallback> bloodPressureServiceMockCallbackBuilder = new BloodPressureServiceMockCallback.Builder<BloodPressureServiceMockCallback>() {
            @NonNull
            @Override
            public BloodPressureServiceMockCallback.Builder<BloodPressureServiceMockCallback> addBloodPressureMeasurement(int characteristicResponceCode, long characteristicDelay, @NonNull byte[] characteristicValue, int notificationCount, int descriptorResponceCode, long descriptorDelay, @NonNull byte[] descriptorValue) {
                assertArrayEquals(bloodPressureMeasurement.getBytes(), characteristicValue);
                assertArrayEquals(bpmclientCharacteristicConfiguration.getBytes(), descriptorValue);
                atomicBoolean.set(true);
                return super.addBloodPressureMeasurement(characteristicResponceCode, characteristicDelay, characteristicValue, notificationCount, descriptorResponceCode, descriptorDelay, descriptorValue);
            }

        };
        BaseBuilder baseBuilder = new BaseBuilder(context, deviceInformationServiceMockCallbackBuilder, bloodPressureServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.addBloodPressureMeasurement(bloodPressureMeasurement, bpmclientCharacteristicConfiguration));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_addBloodPressureMeasurement_00002() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        int bpmflags = 1;
        IEEE_11073_20601_SFLOAT bpmbloodPressureMeasurementCompoundValueSystolicMmhg = new IEEE_11073_20601_SFLOAT(new byte[]{2, 3, 4, 5}, 0);
        IEEE_11073_20601_SFLOAT bpmbloodPressureMeasurementCompoundValueDiastolicMmhg = new IEEE_11073_20601_SFLOAT(new byte[]{6, 7, 8, 9}, 0);
        IEEE_11073_20601_SFLOAT bpmbloodPressureMeasurementCompoundValueMeanArterialPressureMmhg = new IEEE_11073_20601_SFLOAT(new byte[]{10, 11, 12, 13}, 0);
        IEEE_11073_20601_SFLOAT bpmbloodPressureMeasurementCompoundValueSystolicKpa = new IEEE_11073_20601_SFLOAT(new byte[]{14, 15, 16, 17}, 0);
        IEEE_11073_20601_SFLOAT bpmbloodPressureMeasurementCompoundValueDiastolicKpa = new IEEE_11073_20601_SFLOAT(new byte[]{18, 19, 20, 21}, 0);
        IEEE_11073_20601_SFLOAT bpmbloodPressureMeasurementCompoundValueMeanArterialPressureKpa = new IEEE_11073_20601_SFLOAT(new byte[]{22, 23, 24, 25}, 0);
        int bpmyear = 26;
        int bpmmonth = 27;
        int bpmday = 28;
        int bpmhours = 29;
        int bpmminutes = 30;
        int bpmseconds = 31;
        IEEE_11073_20601_SFLOAT bpmpulseRate = new IEEE_11073_20601_SFLOAT(new byte[]{32, 33, 34, 35}, 0);
        int bpmuserId = 36;
        byte[] bpmmeasurementStatus = new byte[]{37};
        final BloodPressureMeasurement bloodPressureMeasurement = new BloodPressureMeasurement(bpmflags, bpmbloodPressureMeasurementCompoundValueSystolicMmhg, bpmbloodPressureMeasurementCompoundValueDiastolicMmhg, bpmbloodPressureMeasurementCompoundValueMeanArterialPressureMmhg, bpmbloodPressureMeasurementCompoundValueSystolicKpa, bpmbloodPressureMeasurementCompoundValueDiastolicKpa, bpmbloodPressureMeasurementCompoundValueMeanArterialPressureKpa, bpmyear, bpmmonth, bpmday, bpmhours, bpmminutes, bpmseconds, bpmpulseRate, bpmuserId, bpmmeasurementStatus);

        byte[] bpmdescriptorValue = BluetoothGattDescriptor.ENABLE_INDICATION_VALUE;
        final ClientCharacteristicConfiguration bpmclientCharacteristicConfiguration = new ClientCharacteristicConfiguration(bpmdescriptorValue);

        final int originalCharacteristicResponseCode = 38;
        final long originalCharacteristicDelay = 39;
        final int originalDescriptorResponseCode = 40;
        final long originalDescriptorDelay = 41;

        Context context = ApplicationProvider.getApplicationContext();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        BloodPressureServiceMockCallback.Builder<BloodPressureServiceMockCallback> bloodPressureServiceMockCallbackBuilder = new BloodPressureServiceMockCallback.Builder<BloodPressureServiceMockCallback>() {
            @NonNull
            @Override
            public BloodPressureServiceMockCallback.Builder<BloodPressureServiceMockCallback> addBloodPressureMeasurement(int characteristicResponceCode, long characteristicDelay, @NonNull byte[] characteristicValue, int notificationCount, int descriptorResponceCode, long descriptorDelay, @NonNull byte[] descriptorValue) {
                assertArrayEquals(bloodPressureMeasurement.getBytes(), characteristicValue);
                assertArrayEquals(bpmclientCharacteristicConfiguration.getBytes(), descriptorValue);
                assertEquals(originalCharacteristicResponseCode, characteristicResponceCode);
                assertEquals(originalCharacteristicDelay, characteristicDelay);
                assertEquals(originalDescriptorResponseCode, descriptorResponceCode);
                assertEquals(originalDescriptorDelay, descriptorDelay);
                atomicBoolean.set(true);
                return super.addBloodPressureMeasurement(characteristicResponceCode, characteristicDelay, characteristicValue, notificationCount, descriptorResponceCode, descriptorDelay, descriptorValue);
            }

        };
        BaseBuilder baseBuilder = new BaseBuilder(context, deviceInformationServiceMockCallbackBuilder, bloodPressureServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.addBloodPressureMeasurement(originalCharacteristicResponseCode, originalCharacteristicDelay, bloodPressureMeasurement.getBytes(), originalDescriptorResponseCode, originalDescriptorResponseCode, originalDescriptorDelay, bpmclientCharacteristicConfiguration.getBytes()));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_removeBloodPressureMeasurement_00001() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        Context context = ApplicationProvider.getApplicationContext();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        BloodPressureServiceMockCallback.Builder<BloodPressureServiceMockCallback> bloodPressureServiceMockCallbackBuilder = new BloodPressureServiceMockCallback.Builder<BloodPressureServiceMockCallback>() {

            @NonNull
            @Override
            public BloodPressureServiceMockCallback.Builder<BloodPressureServiceMockCallback> removeBloodPressureMeasurement() {
                atomicBoolean.set(true);
                return super.removeBloodPressureMeasurement();
            }
        };
        BaseBuilder baseBuilder = new BaseBuilder(context, deviceInformationServiceMockCallbackBuilder, bloodPressureServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.removeBloodPressureMeasurement());

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_addIntermediateCuffPressure_00001() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        int icpflags = 38;
        IEEE_11073_20601_SFLOAT icpbloodPressureMeasurementCompoundValueSystolicMmhg = new IEEE_11073_20601_SFLOAT(new byte[]{39, 40, 41, 42}, 0);
        IEEE_11073_20601_SFLOAT icpbloodPressureMeasurementCompoundValueDiastolicMmhg = new IEEE_11073_20601_SFLOAT(new byte[]{43, 44, 45, 46}, 0);
        IEEE_11073_20601_SFLOAT icpbloodPressureMeasurementCompoundValueMeanArterialPressureMmhg = new IEEE_11073_20601_SFLOAT(new byte[]{47, 48, 49, 50}, 0);
        IEEE_11073_20601_SFLOAT icpbloodPressureMeasurementCompoundValueSystolicKpa = new IEEE_11073_20601_SFLOAT(new byte[]{51, 52, 53, 54}, 0);
        IEEE_11073_20601_SFLOAT icpbloodPressureMeasurementCompoundValueDiastolicKpa = new IEEE_11073_20601_SFLOAT(new byte[]{55, 56, 57, 58}, 0);
        IEEE_11073_20601_SFLOAT icpbloodPressureMeasurementCompoundValueMeanArterialPressureKpa = new IEEE_11073_20601_SFLOAT(new byte[]{59, 60, 61, 62}, 0);
        int icpyear = 63;
        int icpmonth = 64;
        int icpday = 65;
        int icphours = 66;
        int icpminutes = 67;
        int icpseconds = 68;
        IEEE_11073_20601_SFLOAT icppulseRate = new IEEE_11073_20601_SFLOAT(new byte[]{69, 70, 71, 72}, 0);
        int icpuserId = 73;
        byte[] icpmeasurementStatus = new byte[]{74};
        final IntermediateCuffPressure intermediateCuffPressure = new IntermediateCuffPressure(icpflags, icpbloodPressureMeasurementCompoundValueSystolicMmhg, icpbloodPressureMeasurementCompoundValueDiastolicMmhg, icpbloodPressureMeasurementCompoundValueMeanArterialPressureMmhg, icpbloodPressureMeasurementCompoundValueSystolicKpa, icpbloodPressureMeasurementCompoundValueDiastolicKpa, icpbloodPressureMeasurementCompoundValueMeanArterialPressureKpa, icpyear, icpmonth, icpday, icphours, icpminutes, icpseconds, icppulseRate, icpuserId, icpmeasurementStatus);

        byte[] icpescriptorValue = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE;
        final ClientCharacteristicConfiguration icpclientCharacteristicConfiguration = new ClientCharacteristicConfiguration(icpescriptorValue);

        Context context = ApplicationProvider.getApplicationContext();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        BloodPressureServiceMockCallback.Builder<BloodPressureServiceMockCallback> bloodPressureServiceMockCallbackBuilder = new BloodPressureServiceMockCallback.Builder<BloodPressureServiceMockCallback>() {

            @NonNull
            @Override
            public BloodPressureServiceMockCallback.Builder<BloodPressureServiceMockCallback> addIntermediateCuffPressure(int characteristicResponceCode, long characteristicDelay, @NonNull byte[] characteristicValue, int notificationCount, int descriptorResponceCode, long descriptorDelay, @NonNull byte[] descriptorValue) {
                assertArrayEquals(intermediateCuffPressure.getBytes(), characteristicValue);
                assertArrayEquals(icpclientCharacteristicConfiguration.getBytes(), descriptorValue);
                atomicBoolean.set(true);
                return super.addIntermediateCuffPressure(characteristicResponceCode, characteristicDelay, characteristicValue, notificationCount, descriptorResponceCode, descriptorDelay, descriptorValue);
            }

        };
        BaseBuilder baseBuilder = new BaseBuilder(context, deviceInformationServiceMockCallbackBuilder, bloodPressureServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.addIntermediateCuffPressure(intermediateCuffPressure, icpclientCharacteristicConfiguration));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_addIntermediateCuffPressure_00002() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        int icpflags = 38;
        IEEE_11073_20601_SFLOAT icpbloodPressureMeasurementCompoundValueSystolicMmhg = new IEEE_11073_20601_SFLOAT(new byte[]{39, 40, 41, 42}, 0);
        IEEE_11073_20601_SFLOAT icpbloodPressureMeasurementCompoundValueDiastolicMmhg = new IEEE_11073_20601_SFLOAT(new byte[]{43, 44, 45, 46}, 0);
        IEEE_11073_20601_SFLOAT icpbloodPressureMeasurementCompoundValueMeanArterialPressureMmhg = new IEEE_11073_20601_SFLOAT(new byte[]{47, 48, 49, 50}, 0);
        IEEE_11073_20601_SFLOAT icpbloodPressureMeasurementCompoundValueSystolicKpa = new IEEE_11073_20601_SFLOAT(new byte[]{51, 52, 53, 54}, 0);
        IEEE_11073_20601_SFLOAT icpbloodPressureMeasurementCompoundValueDiastolicKpa = new IEEE_11073_20601_SFLOAT(new byte[]{55, 56, 57, 58}, 0);
        IEEE_11073_20601_SFLOAT icpbloodPressureMeasurementCompoundValueMeanArterialPressureKpa = new IEEE_11073_20601_SFLOAT(new byte[]{59, 60, 61, 62}, 0);
        int icpyear = 63;
        int icpmonth = 64;
        int icpday = 65;
        int icphours = 66;
        int icpminutes = 67;
        int icpseconds = 68;
        IEEE_11073_20601_SFLOAT icppulseRate = new IEEE_11073_20601_SFLOAT(new byte[]{69, 70, 71, 72}, 0);
        int icpuserId = 73;
        byte[] icpmeasurementStatus = new byte[]{74};
        final IntermediateCuffPressure intermediateCuffPressure = new IntermediateCuffPressure(icpflags, icpbloodPressureMeasurementCompoundValueSystolicMmhg, icpbloodPressureMeasurementCompoundValueDiastolicMmhg, icpbloodPressureMeasurementCompoundValueMeanArterialPressureMmhg, icpbloodPressureMeasurementCompoundValueSystolicKpa, icpbloodPressureMeasurementCompoundValueDiastolicKpa, icpbloodPressureMeasurementCompoundValueMeanArterialPressureKpa, icpyear, icpmonth, icpday, icphours, icpminutes, icpseconds, icppulseRate, icpuserId, icpmeasurementStatus);

        byte[] icpescriptorValue = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE;
        final ClientCharacteristicConfiguration icpclientCharacteristicConfiguration = new ClientCharacteristicConfiguration(icpescriptorValue);

        final int originalCharacteristicResponseCode = 75;
        final long originalCharacteristicDelay = 76;
        final int originalDescriptorResponseCode = 77;
        final long originalDescriptorDelay = 78;

        Context context = ApplicationProvider.getApplicationContext();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        BloodPressureServiceMockCallback.Builder<BloodPressureServiceMockCallback> bloodPressureServiceMockCallbackBuilder = new BloodPressureServiceMockCallback.Builder<BloodPressureServiceMockCallback>() {
            @NonNull
            @Override
            public BloodPressureServiceMockCallback.Builder<BloodPressureServiceMockCallback> addIntermediateCuffPressure(int characteristicResponceCode, long characteristicDelay, @NonNull byte[] characteristicValue, int notificationCount, int descriptorResponceCode, long descriptorDelay, @NonNull byte[] descriptorValue) {
                assertArrayEquals(intermediateCuffPressure.getBytes(), characteristicValue);
                assertArrayEquals(icpclientCharacteristicConfiguration.getBytes(), descriptorValue);
                assertEquals(originalCharacteristicResponseCode, characteristicResponceCode);
                assertEquals(originalCharacteristicDelay, characteristicDelay);
                assertEquals(originalDescriptorResponseCode, descriptorResponceCode);
                assertEquals(originalDescriptorDelay, descriptorDelay);
                atomicBoolean.set(true);
                return super.addIntermediateCuffPressure(characteristicResponceCode, characteristicDelay, characteristicValue, notificationCount, descriptorResponceCode, descriptorDelay, descriptorValue);
            }

        };
        BaseBuilder baseBuilder = new BaseBuilder(context, deviceInformationServiceMockCallbackBuilder, bloodPressureServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.addIntermediateCuffPressure(originalCharacteristicResponseCode, originalCharacteristicDelay, intermediateCuffPressure.getBytes(), originalDescriptorResponseCode, originalDescriptorResponseCode, originalDescriptorDelay, icpclientCharacteristicConfiguration.getBytes()));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_removeIntermediateCuffPressure_00001() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        Context context = ApplicationProvider.getApplicationContext();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        BloodPressureServiceMockCallback.Builder<BloodPressureServiceMockCallback> bloodPressureServiceMockCallbackBuilder = new BloodPressureServiceMockCallback.Builder<BloodPressureServiceMockCallback>() {
            @NonNull
            @Override
            public BloodPressureServiceMockCallback.Builder<BloodPressureServiceMockCallback> removeIntermediateCuffPressure() {
                atomicBoolean.set(true);
                return super.removeIntermediateCuffPressure();
            }
        };
        BaseBuilder baseBuilder = new BaseBuilder(context, deviceInformationServiceMockCallbackBuilder, bloodPressureServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.removeIntermediateCuffPressure());

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_addBloodPressureFeature_00001() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        boolean isBodyMovementDetectionFeatureSupported = false;
        boolean isCuffFitDetectionSupported = false;
        boolean isIrregularPulseDetectionSupported = false;
        boolean isPulseRateRangeDetectionSupported = false;
        boolean isMeasurementPositionDetectionSupported = false;
        boolean isMultipleBondDetectionSupported = false;
        final BloodPressureFeature bloodPressureFeature = new BloodPressureFeature(isBodyMovementDetectionFeatureSupported, isCuffFitDetectionSupported, isIrregularPulseDetectionSupported, isPulseRateRangeDetectionSupported, isMeasurementPositionDetectionSupported, isMultipleBondDetectionSupported);

        Context context = ApplicationProvider.getApplicationContext();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        BloodPressureServiceMockCallback.Builder<BloodPressureServiceMockCallback> bloodPressureServiceMockCallbackBuilder = new BloodPressureServiceMockCallback.Builder<BloodPressureServiceMockCallback>() {

            @NonNull
            @Override
            public BloodPressureServiceMockCallback.Builder<BloodPressureServiceMockCallback> addBloodPressureFeature(int responceCode, long delay, @NonNull byte[] value) {
                assertArrayEquals(bloodPressureFeature.getBytes(), value);
                atomicBoolean.set(true);
                return super.addBloodPressureFeature(responceCode, delay, value);
            }

        };
        BaseBuilder baseBuilder = new BaseBuilder(context, deviceInformationServiceMockCallbackBuilder, bloodPressureServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.addBloodPressureFeature(bloodPressureFeature));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_addBloodPressureFeature_00002() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        boolean isBodyMovementDetectionFeatureSupported = false;
        boolean isCuffFitDetectionSupported = false;
        boolean isIrregularPulseDetectionSupported = false;
        boolean isPulseRateRangeDetectionSupported = false;
        boolean isMeasurementPositionDetectionSupported = false;
        boolean isMultipleBondDetectionSupported = false;
        final BloodPressureFeature bloodPressureFeature = new BloodPressureFeature(isBodyMovementDetectionFeatureSupported, isCuffFitDetectionSupported, isIrregularPulseDetectionSupported, isPulseRateRangeDetectionSupported, isMeasurementPositionDetectionSupported, isMultipleBondDetectionSupported);

        final int originalResponseCode = 1;
        final long originalDelay = 2;

        Context context = ApplicationProvider.getApplicationContext();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        BloodPressureServiceMockCallback.Builder<BloodPressureServiceMockCallback> bloodPressureServiceMockCallbackBuilder = new BloodPressureServiceMockCallback.Builder<BloodPressureServiceMockCallback>() {

            @NonNull
            @Override
            public BloodPressureServiceMockCallback.Builder<BloodPressureServiceMockCallback> addBloodPressureFeature(int responceCode, long delay, @NonNull byte[] value) {
                assertArrayEquals(bloodPressureFeature.getBytes(), value);
                assertEquals(originalResponseCode, responceCode);
                assertEquals(originalDelay, delay);
                atomicBoolean.set(true);
                return super.addBloodPressureFeature(responceCode, delay, value);
            }

        };
        BaseBuilder baseBuilder = new BaseBuilder(context, deviceInformationServiceMockCallbackBuilder, bloodPressureServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.addBloodPressureFeature(originalResponseCode, originalDelay, bloodPressureFeature.getBytes()));

        assertTrue(atomicBoolean.get());
    }

    @Test
    public void test_removeBloodPressureFeature_00001() {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        Context context = ApplicationProvider.getApplicationContext();
        DeviceInformationServiceMockCallback.Builder<DeviceInformationServiceMockCallback> deviceInformationServiceMockCallbackBuilder = new DeviceInformationServiceMockCallback.Builder<>();
        BloodPressureServiceMockCallback.Builder<BloodPressureServiceMockCallback> bloodPressureServiceMockCallbackBuilder = new BloodPressureServiceMockCallback.Builder<BloodPressureServiceMockCallback>() {
            @NonNull
            @Override
            public BloodPressureServiceMockCallback.Builder<BloodPressureServiceMockCallback> removeBloodPressureFeature() {
                atomicBoolean.set(true);
                return super.removeBloodPressureFeature();
            }
        };
        BaseBuilder baseBuilder = new BaseBuilder(context, deviceInformationServiceMockCallbackBuilder, bloodPressureServiceMockCallbackBuilder);
        assertEquals(baseBuilder, baseBuilder.removeBloodPressureFeature());

        assertTrue(atomicBoolean.get());
    }

}