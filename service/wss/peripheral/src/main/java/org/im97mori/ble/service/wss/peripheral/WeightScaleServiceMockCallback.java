package org.im97mori.ble.service.wss.peripheral;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattServer;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.le.AdvertiseSettings;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.im97mori.ble.BLEServerConnection;
import org.im97mori.ble.CharacteristicData;
import org.im97mori.ble.DescriptorData;
import org.im97mori.ble.MockData;
import org.im97mori.ble.ServiceData;
import org.im97mori.ble.characteristic.u2a9d.WeightMeasurement;
import org.im97mori.ble.characteristic.u2a9e.WeightScaleFeature;
import org.im97mori.ble.descriptor.u2902.ClientCharacteristicConfiguration;
import org.im97mori.ble.service.peripheral.AbstractServiceMockCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.im97mori.ble.BLEConstants.CharacteristicUUID.WEIGHT_MEASUREMENT_CHARACTERISTIC;
import static org.im97mori.ble.BLEConstants.CharacteristicUUID.WEIGHT_SCALE_FEATURE_CHARACTERISTIC;
import static org.im97mori.ble.BLEConstants.DescriptorUUID.CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR;
import static org.im97mori.ble.BLEConstants.ServiceUUID.WEIGHT_SCALE_SERVICE;

/**
 * Weight Scale Service (Service UUID: 0x181D) for Peripheral
 */
public class WeightScaleServiceMockCallback extends AbstractServiceMockCallback {

    /**
     * Builder for {@link WeightScaleServiceMockCallback}
     *
     * @param <T> subclass of {@link WeightScaleServiceMockCallback}
     */
    public static class Builder<T extends WeightScaleServiceMockCallback> extends AbstractServiceMockCallback.Builder<WeightScaleServiceMockCallback> {

        /**
         * Weight Scale Feature data
         */
        protected CharacteristicData mWeightScaleFeatureCharacteristicData;

        /**
         * Weight Measurement data
         */
        protected CharacteristicData mWeightMeasurementCharacteristicData;

        /**
         * @see #addWeightScaleFeature(byte[])
         */
        @NonNull
        public Builder<T> addWeightScaleFeature(@NonNull WeightScaleFeature weightScaleFeature) {
            return addWeightScaleFeature(weightScaleFeature.getBytes());
        }

        /**
         * @see #addWeightScaleFeature(int, long, byte[])
         */
        @NonNull
        public Builder<T> addWeightScaleFeature(@NonNull byte[] value) {
            return addWeightScaleFeature(BluetoothGatt.GATT_SUCCESS
                    , 0
                    , value);
        }

        /**
         * add Weight Scale Feature characteristic
         *
         * @param responceCode response code for {@link android.bluetooth.BluetoothGattServer#sendResponse(BluetoothDevice, int, int, int, byte[])} 3rd parameter
         * @param delay        response delay(millis)
         * @param value        data array for {@link android.bluetooth.BluetoothGattServer#sendResponse(BluetoothDevice, int, int, int, byte[])} 5th parameter
         * @return {@link Builder} instance
         */
        @NonNull
        public Builder<T> addWeightScaleFeature(int responceCode, long delay, @NonNull byte[] value) {
            mWeightScaleFeatureCharacteristicData = new CharacteristicData(WEIGHT_SCALE_FEATURE_CHARACTERISTIC
                    , BluetoothGattCharacteristic.PROPERTY_READ
                    , BluetoothGattCharacteristic.PERMISSION_READ
                    , Collections.<DescriptorData>emptyList()
                    , responceCode
                    , delay
                    , value
                    , 0);
            return this;
        }

        /**
         * remove Weight Scale Feature characteristic
         *
         * @return {@link Builder} instance
         */
        @NonNull
        public Builder<T> removeWeightScaleFeature() {
            mWeightScaleFeatureCharacteristicData = null;
            return this;
        }

        /**
         * @see #addWeightMeasurement(int, long, byte[], int, int, long, byte[])
         */
        @NonNull
        public Builder<T> addWeightMeasurement(@NonNull WeightMeasurement weightMeasurement, @NonNull ClientCharacteristicConfiguration clientCharacteristicConfiguration) {
            return addWeightMeasurement(BluetoothGatt.GATT_SUCCESS, 0, weightMeasurement.getBytes(), -1, BluetoothGatt.GATT_SUCCESS, 0, clientCharacteristicConfiguration.getBytes());
        }

        /**
         * add Weight Measurement characteristic
         *
         * @param characteristicResponceCode characteristic response code for {@link BluetoothGattServer#sendResponse(BluetoothDevice, int, int, int, byte[])} 3rd parameter
         * @param characteristicDelay        characteristic response delay(millis)
         * @param characteristicValue        characteristic data array for {@link BluetoothGattServer#sendResponse(BluetoothDevice, int, int, int, byte[])} 5th parameter
         * @param notificationCount          notification / indication count
         * @param descriptorResponceCode     descritptor response code for {@link BluetoothGattServer#sendResponse(BluetoothDevice, int, int, int, byte[])} 3rd parameter
         * @param descriptorDelay            descritptor response delay(millis)
         * @param descriptorValue            descriptor data array for {@link BluetoothGattServer#sendResponse(BluetoothDevice, int, int, int, byte[])} 5th parameter
         * @return {@link Builder} instance
         */
        @NonNull
        public Builder<T> addWeightMeasurement(int characteristicResponceCode, long characteristicDelay, @NonNull byte[] characteristicValue, int notificationCount, int descriptorResponceCode, long descriptorDelay, @NonNull byte[] descriptorValue) {
            mWeightMeasurementCharacteristicData = new CharacteristicData(WEIGHT_MEASUREMENT_CHARACTERISTIC
                    , BluetoothGattCharacteristic.PROPERTY_INDICATE
                    , 0
                    , Collections.singletonList(new DescriptorData(CLIENT_CHARACTERISTIC_CONFIGURATION_DESCRIPTOR
                    , BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE
                    , descriptorResponceCode
                    , descriptorDelay
                    , descriptorValue))
                    , characteristicResponceCode
                    , characteristicDelay
                    , characteristicValue
                    , notificationCount);
            return this;
        }

        /**
         * remove Weight Measurement characteristic
         *
         * @return {@link Builder} instance
         */
        @NonNull
        public Builder<T> removeWeightMeasurement() {
            mWeightMeasurementCharacteristicData = null;
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        @NonNull
        public MockData createMockData() {
            List<CharacteristicData> characteristicList = new ArrayList<>();

            if (mWeightScaleFeatureCharacteristicData == null) {
                throw new RuntimeException("no Weight Scale Feature data");
            } else {
                characteristicList.add(mWeightScaleFeatureCharacteristicData);
            }

            if (mWeightMeasurementCharacteristicData == null) {
                throw new RuntimeException("no Weight Measurement data");
            } else {
                WeightScaleFeature weightScaleFeature = new WeightScaleFeature(mWeightScaleFeatureCharacteristicData.data);
                WeightMeasurement weightMeasurement = new WeightMeasurement(mWeightMeasurementCharacteristicData.data);
                if (weightScaleFeature.isWeightScaleFeatureTimeStampNotSupported() && weightMeasurement.isFlagsTimeStampPresent()) {
                    throw new RuntimeException("Time Stamp not Supported");
                } else if (weightScaleFeature.isWeightScaleFeatureMultipleUsersNotSupported() && weightMeasurement.isFlagsUserIdPresent()) {
                    throw new RuntimeException("Multiple Users not Supported");
                } else if (weightScaleFeature.isWeightScaleFeatureBmiNotSupported() && weightMeasurement.isFlagsBmiAndHeightPresent()) {
                    throw new RuntimeException("BMI not Supported");
                } else {
                    characteristicList.add(mWeightMeasurementCharacteristicData);
                }
            }

            ServiceData serviceData = new ServiceData(WEIGHT_SCALE_SERVICE, BluetoothGattService.SERVICE_TYPE_PRIMARY, characteristicList);
            return new MockData(Collections.singletonList(serviceData));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        @NonNull
        public WeightScaleServiceMockCallback build() {
            return new WeightScaleServiceMockCallback(createMockData(), false);
        }

    }

    /**
     * @param mockData   {@link MockData} instance
     * @param isFallback fallback flag
     */
    public WeightScaleServiceMockCallback(@NonNull MockData mockData, boolean isFallback) {
        super(mockData, isFallback);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onServiceAddFailed(@NonNull Integer taskId, @NonNull BLEServerConnection bleServerConnection, @NonNull BluetoothGattService bluetoothGattService, int status, @Nullable Bundle argument) {
        // do nothing
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onServiceAddTimeout(@NonNull Integer taskId, @NonNull BLEServerConnection bleServerConnection, @NonNull BluetoothGattService bluetoothGattService, long timeout, @Nullable Bundle argument) {
        // do nothing
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onServiceRemoveFailed(@NonNull Integer taskId, @NonNull BLEServerConnection bleServerConnection, @NonNull BluetoothGattService bluetoothGattService, int status, @Nullable Bundle argument) {
        // do nothing
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onServiceRemoveTimeout(@NonNull Integer taskId, @NonNull BLEServerConnection bleServerConnection, @NonNull BluetoothGattService bluetoothGattService, long timeout, @Nullable Bundle argument) {
        // do nothing
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onAdvertisingStartSuccess(@NonNull AdvertiseSettings advertiseSettings) {
        // do nothing
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onAdvertisingStartFailed(@Nullable Integer errorCode) {
        // do nothing
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onAdvertisingFinished() {
        // do nothing
    }

}
