package org.im97mori.ble.task;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.im97mori.ble.BLECallbackDistributer;
import org.im97mori.ble.BaseBLECallback;
import org.im97mori.ble.MockBLEConnection;
import org.im97mori.ble.TaskHandler;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("ConstantConditions")
public class RequestMtuTaskTest {

    @Test
    public void test_createInitialMessage_00001() {
        int mtu = new Random().nextInt();
        RequestMtuTask task = new RequestMtuTask(null, null, null, mtu, RequestMtuTask.TIMEOUT_MILLIS, null);
        Message message = task.createInitialMessage();

        assertNotNull(message);
        Bundle bundle = message.getData();
        assertNotNull(bundle);
        assertTrue(bundle.containsKey(AbstractBLETask.KEY_NEXT_PROGRESS));
        assertEquals(AbstractBLETask.PROGRESS_REQUEST_MTU_START, bundle.getInt(AbstractBLETask.KEY_NEXT_PROGRESS));
        assertEquals(task, message.obj);
    }

    @Test
    public void test_createServiceDiscoverSuccessMessage_00001() {
        int mtu = new Random().nextInt();
        Message message = RequestMtuTask.createRequestMtuSuccessMessage(mtu);

        assertNotNull(message);
        Bundle bundle = message.getData();
        assertNotNull(bundle);
        assertTrue(bundle.containsKey(AbstractBLETask.KEY_NEXT_PROGRESS));
        assertEquals(AbstractBLETask.PROGRESS_REQUEST_MTU_SUCCESS, bundle.getInt(AbstractBLETask.KEY_NEXT_PROGRESS));
        assertTrue(bundle.containsKey(AbstractBLETask.KEY_MTU));
        assertEquals(mtu, bundle.getInt(AbstractBLETask.KEY_MTU));
    }

    @Test
    public void test_createServiceDiscoverErrorMessage_00001() {
        Object object = new Object();
        int status = new Random().nextInt();
        Message message = RequestMtuTask.createRequestMtuErrorMessage(object, status);

        assertNotNull(message);
        Bundle bundle = message.getData();
        assertNotNull(bundle);
        assertTrue(bundle.containsKey(AbstractBLETask.KEY_NEXT_PROGRESS));
        assertEquals(AbstractBLETask.PROGRESS_DISCOVER_SERVICE_ERROR, bundle.getInt(AbstractBLETask.KEY_NEXT_PROGRESS));
        assertTrue(bundle.containsKey(AbstractBLETask.KEY_STATUS));
        assertEquals(status, bundle.getInt(AbstractBLETask.KEY_STATUS));
        assertEquals(object, message.obj);
    }

    @Test
    public void test_doProcess_00001() {
        int mtu = new Random().nextInt();
        RequestMtuTask task = new RequestMtuTask(null, null, null, mtu, RequestMtuTask.TIMEOUT_MILLIS, null);
        assertFalse(task.doProcess(new Message()));
    }

    @Test
    public void test_cancel_00001() {
        Looper looper = null;
        try {
            HandlerThread thread = new HandlerThread(this.getClass().getSimpleName());
            thread.start();
            looper = thread.getLooper();
            TaskHandler mockTaskHandler = new TaskHandler(looper);
            Message message = Message.obtain();
            message.setData(Bundle.EMPTY);

            RequestMtuTask task = new RequestMtuTask(new MockBLEConnection(), null, mockTaskHandler, 0, RequestMtuTask.TIMEOUT_MILLIS, BLECallbackDistributer.wrapArgument(null, null));
            task.cancel();
            assertTrue(task.doProcess(message));
        } finally {
            if (looper != null) {
                looper.quit();
            }
        }
    }

    @Test
    public void test_cancel_00002() {
        MockBLEConnection mockBleConnection = new MockBLEConnection();
        Looper looper = null;
        try {
            mockBleConnection.start();

            BaseBLECallback callback = new BaseBLECallback() {

                @Override
                public void onRequestMtuFailed(@NonNull Integer taskId, @NonNull BluetoothDevice bluetoothDevice, int status, @Nullable Bundle argument) {
                    result.set(true);
                }
            };
            mockBleConnection.attach(callback);

            HandlerThread thread = new HandlerThread(this.getClass().getSimpleName());
            thread.start();
            looper = thread.getLooper();
            TaskHandler mockTaskHandler = new TaskHandler(looper);
            Message message = Message.obtain();
            message.setData(Bundle.EMPTY);

            RequestMtuTask task = new RequestMtuTask(mockBleConnection, null, mockTaskHandler, 0, RequestMtuTask.TIMEOUT_MILLIS, BLECallbackDistributer.wrapArgument(null, null));
            task.cancel();
            assertTrue(task.doProcess(message));
            assertTrue(callback.result.get());
        } finally {
            if (looper != null) {
                looper.quit();
            }
        }
    }

}
