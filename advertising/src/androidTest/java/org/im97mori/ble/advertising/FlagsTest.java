package org.im97mori.ble.advertising;

import android.os.Parcel;

import org.junit.Test;

import static org.im97mori.ble.advertising.AdvertisingDataConstants.AdvertisingDataTypes.DATA_TYPE_FLAGS;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("unused")
public class FlagsTest {

    //@formatter:off
    private static final byte[] data_00001;
    static {
        byte[] data = new byte[3];
        data[0] = 2;
        data[1] = DATA_TYPE_FLAGS;
        data[2] = 0b00000001;
        data_00001 = data;
    }

    private static final byte[] data_00002;
    static {
        byte[] data = new byte[3];
        data[0] = 2;
        data[1] = DATA_TYPE_FLAGS;
        data[2] = 0b00000010;
        data_00002 = data;
    }

    private static final byte[] data_00003;
    static {
        byte[] data = new byte[3];
        data[0] = 2;
        data[1] = DATA_TYPE_FLAGS;
        data[2] = 0b00000100;
        data_00003 = data;
    }

    private static final byte[] data_00004;
    static {
        byte[] data = new byte[3];
        data[0] = 2;
        data[1] = DATA_TYPE_FLAGS;
        data[2] = 0b00001000;
        data_00004 = data;
    }

    private static final byte[] data_00005;
    static {
        byte[] data = new byte[3];
        data[0] = 2;
        data[1] = DATA_TYPE_FLAGS;
        data[2] = 0b00010000;
        data_00005 = data;
    }

    private static final byte[] data_00006;
    static {
        byte[] data = new byte[3];
        data[0] = 2;
        data[1] = DATA_TYPE_FLAGS;
        data[2] = (byte) 0b11111111;
        data_00006 = data;
    }

    private static final byte[] data_00007;
    static {
        byte[] data = new byte[2];
        data[0] = 1;
        data[1] = DATA_TYPE_FLAGS;
        data_00007 = data;
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

        FlagsAndroid result1 = new FlagsAndroid(data, 0, data[0]);
        assertEquals(2, result1.getLength());
        assertEquals(DATA_TYPE_FLAGS, result1.getDataType());
        assertEquals(1, result1.getFlagsList().size());
        assertEquals(0b00000001, result1.getFlagsList().get(0).intValue());
        assertTrue(result1.isLeLimitedDiscoverableMode());
        assertFalse(result1.isLeGeneralDiscoverableMode());
        assertFalse(result1.isBrEdrNotSupported());
        assertFalse(result1.isSimultaneousController());
        assertFalse(result1.isSimultaneousHost());
    }

    @Test
    public void test_constructor_00002() {
        byte[] data = getData();

        FlagsAndroid result1 = new FlagsAndroid(data, 0, data[0]);
        assertEquals(2, result1.getLength());
        assertEquals(DATA_TYPE_FLAGS, result1.getDataType());
        assertEquals(1, result1.getFlagsList().size());
        assertEquals(0b00000010, result1.getFlagsList().get(0).intValue());
        assertFalse(result1.isLeLimitedDiscoverableMode());
        assertTrue(result1.isLeGeneralDiscoverableMode());
        assertFalse(result1.isBrEdrNotSupported());
        assertFalse(result1.isSimultaneousController());
        assertFalse(result1.isSimultaneousHost());
    }

    @Test
    public void test_constructor_00003() {
        byte[] data = getData();

        FlagsAndroid result1 = new FlagsAndroid(data, 0, data[0]);
        assertEquals(2, result1.getLength());
        assertEquals(DATA_TYPE_FLAGS, result1.getDataType());
        assertEquals(1, result1.getFlagsList().size());
        assertEquals(0b00000100, result1.getFlagsList().get(0).intValue());
        assertFalse(result1.isLeLimitedDiscoverableMode());
        assertFalse(result1.isLeGeneralDiscoverableMode());
        assertTrue(result1.isBrEdrNotSupported());
        assertFalse(result1.isSimultaneousController());
        assertFalse(result1.isSimultaneousHost());
    }

    @Test
    public void test_constructor_00004() {
        byte[] data = getData();

        FlagsAndroid result1 = new FlagsAndroid(data, 0, data[0]);
        assertEquals(2, result1.getLength());
        assertEquals(DATA_TYPE_FLAGS, result1.getDataType());
        assertEquals(1, result1.getFlagsList().size());
        assertEquals(0b00001000, result1.getFlagsList().get(0).intValue());
        assertFalse(result1.isLeLimitedDiscoverableMode());
        assertFalse(result1.isLeGeneralDiscoverableMode());
        assertFalse(result1.isBrEdrNotSupported());
        assertTrue(result1.isSimultaneousController());
        assertFalse(result1.isSimultaneousHost());
    }

    @Test
    public void test_constructor_00005() {
        byte[] data = getData();

        FlagsAndroid result1 = new FlagsAndroid(data, 0, data[0]);
        assertEquals(2, result1.getLength());
        assertEquals(DATA_TYPE_FLAGS, result1.getDataType());
        assertEquals(1, result1.getFlagsList().size());
        assertEquals(0b00010000, result1.getFlagsList().get(0).intValue());
        assertFalse(result1.isLeLimitedDiscoverableMode());
        assertFalse(result1.isLeGeneralDiscoverableMode());
        assertFalse(result1.isBrEdrNotSupported());
        assertFalse(result1.isSimultaneousController());
        assertTrue(result1.isSimultaneousHost());
    }

    @Test
    public void test_constructor_00006() {
        byte[] data = getData();

        FlagsAndroid result1 = new FlagsAndroid(data, 0, data[0]);
        assertEquals(2, result1.getLength());
        assertEquals(DATA_TYPE_FLAGS, result1.getDataType());
        assertEquals(1, result1.getFlagsList().size());
        assertEquals(0b11111111, result1.getFlagsList().get(0).intValue());
        assertTrue(result1.isLeLimitedDiscoverableMode());
        assertTrue(result1.isLeGeneralDiscoverableMode());
        assertTrue(result1.isBrEdrNotSupported());
        assertTrue(result1.isSimultaneousController());
        assertTrue(result1.isSimultaneousHost());
    }

    @Test
    public void test_constructor_00007() {
        byte[] data = getData();

        FlagsAndroid result1 = new FlagsAndroid(data, 0, data[0]);
        assertEquals(1, result1.getLength());
        assertEquals(DATA_TYPE_FLAGS, result1.getDataType());
        assertEquals(0, result1.getFlagsList().size());
        assertFalse(result1.isLeLimitedDiscoverableMode());
        assertFalse(result1.isLeGeneralDiscoverableMode());
        assertFalse(result1.isBrEdrNotSupported());
        assertFalse(result1.isSimultaneousController());
        assertFalse(result1.isSimultaneousHost());
    }

    @Test
    public void test_parcelable_1_00001() {
        byte[] data = getData();

        FlagsAndroid result1 = new FlagsAndroid(data, 0, data[0]);
        Parcel parcel = Parcel.obtain();
        result1.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);
        FlagsAndroid result2 = FlagsAndroid.CREATOR.createFromParcel(parcel);
        assertEquals(result1.getLength(), result2.getLength());
        assertEquals(result1.getDataType(), result2.getDataType());
        assertArrayEquals(result1.getFlagsList().toArray(), result1.getFlagsList().toArray());
    }

    @Test
    public void test_parcelable_1_00002() {
        byte[] data = getData();

        FlagsAndroid result1 = new FlagsAndroid(data, 0, data[0]);
        Parcel parcel = Parcel.obtain();
        result1.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);
        FlagsAndroid result2 = FlagsAndroid.CREATOR.createFromParcel(parcel);
        assertEquals(result1.getLength(), result2.getLength());
        assertEquals(result1.getDataType(), result2.getDataType());
        assertArrayEquals(result1.getFlagsList().toArray(), result1.getFlagsList().toArray());
    }

    @Test
    public void test_parcelable_1_00003() {
        byte[] data = getData();

        FlagsAndroid result1 = new FlagsAndroid(data, 0, data[0]);
        Parcel parcel = Parcel.obtain();
        result1.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);
        FlagsAndroid result2 = FlagsAndroid.CREATOR.createFromParcel(parcel);
        assertEquals(result1.getLength(), result2.getLength());
        assertEquals(result1.getDataType(), result2.getDataType());
        assertArrayEquals(result1.getFlagsList().toArray(), result1.getFlagsList().toArray());
    }

    @Test
    public void test_parcelable_1_00004() {
        byte[] data = getData();

        FlagsAndroid result1 = new FlagsAndroid(data, 0, data[0]);
        Parcel parcel = Parcel.obtain();
        result1.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);
        FlagsAndroid result2 = FlagsAndroid.CREATOR.createFromParcel(parcel);
        assertEquals(result1.getLength(), result2.getLength());
        assertEquals(result1.getDataType(), result2.getDataType());
        assertArrayEquals(result1.getFlagsList().toArray(), result1.getFlagsList().toArray());
    }

    @Test
    public void test_parcelable_1_00005() {
        byte[] data = getData();

        FlagsAndroid result1 = new FlagsAndroid(data, 0, data[0]);
        Parcel parcel = Parcel.obtain();
        result1.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);
        FlagsAndroid result2 = FlagsAndroid.CREATOR.createFromParcel(parcel);
        assertEquals(result1.getLength(), result2.getLength());
        assertEquals(result1.getDataType(), result2.getDataType());
        assertArrayEquals(result1.getFlagsList().toArray(), result1.getFlagsList().toArray());
    }

    @Test
    public void test_parcelable_1_00006() {
        byte[] data = getData();

        FlagsAndroid result1 = new FlagsAndroid(data, 0, data[0]);
        Parcel parcel = Parcel.obtain();
        result1.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);
        FlagsAndroid result2 = FlagsAndroid.CREATOR.createFromParcel(parcel);
        assertEquals(result1.getLength(), result2.getLength());
        assertEquals(result1.getDataType(), result2.getDataType());
        assertArrayEquals(result1.getFlagsList().toArray(), result1.getFlagsList().toArray());
    }

    @Test
    public void test_parcelable_1_00007() {
        byte[] data = getData();

        FlagsAndroid result1 = new FlagsAndroid(data, 0, data[0]);
        Parcel parcel = Parcel.obtain();
        result1.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);
        FlagsAndroid result2 = FlagsAndroid.CREATOR.createFromParcel(parcel);
        assertEquals(result1.getLength(), result2.getLength());
        assertEquals(result1.getDataType(), result2.getDataType());
        assertArrayEquals(result1.getFlagsList().toArray(), result1.getFlagsList().toArray());
    }

    @Test
    public void test_parcelable_2_00001() {
        byte[] data = getData();

        FlagsAndroid result1 = new FlagsAndroid(data, 0, data[0]);
        assertArrayEquals(data, result1.getBytes());
    }

    @Test
    public void test_parcelable_2_00002() {
        byte[] data = getData();

        FlagsAndroid result1 = new FlagsAndroid(data, 0, data[0]);
        assertArrayEquals(data, result1.getBytes());
    }

    @Test
    public void test_parcelable_2_00003() {
        byte[] data = getData();

        FlagsAndroid result1 = new FlagsAndroid(data, 0, data[0]);
        assertArrayEquals(data, result1.getBytes());
    }

    @Test
    public void test_parcelable_2_00004() {
        byte[] data = getData();

        FlagsAndroid result1 = new FlagsAndroid(data, 0, data[0]);
        assertArrayEquals(data, result1.getBytes());
    }

    @Test
    public void test_parcelable_2_00005() {
        byte[] data = getData();

        FlagsAndroid result1 = new FlagsAndroid(data, 0, data[0]);
        assertArrayEquals(data, result1.getBytes());
    }

    @Test
    public void test_parcelable_2_00006() {
        byte[] data = getData();

        FlagsAndroid result1 = new FlagsAndroid(data, 0, data[0]);
        assertArrayEquals(data, result1.getBytes());
    }

    @Test
    public void test_parcelable_2_00007() {
        byte[] data = getData();

        FlagsAndroid result1 = new FlagsAndroid(data, 0, data[0]);
        assertArrayEquals(data, result1.getBytes());
    }

    @Test
    public void test_parcelable_3_00001() {
        byte[] data = getData();

        FlagsAndroid result1 = new FlagsAndroid(data, 0, data[0]);
        FlagsAndroid result2 = FlagsAndroid.CREATOR.createFromByteArray(data);
        assertArrayEquals(result1.getBytes(), result2.getBytes());
    }

    @Test
    public void test_parcelable_3_00002() {
        byte[] data = getData();

        FlagsAndroid result1 = new FlagsAndroid(data, 0, data[0]);
        FlagsAndroid result2 = FlagsAndroid.CREATOR.createFromByteArray(data);
        assertArrayEquals(result1.getBytes(), result2.getBytes());
    }

    @Test
    public void test_parcelable_3_00003() {
        byte[] data = getData();

        FlagsAndroid result1 = new FlagsAndroid(data, 0, data[0]);
        FlagsAndroid result2 = FlagsAndroid.CREATOR.createFromByteArray(data);
        assertArrayEquals(result1.getBytes(), result2.getBytes());
    }

    @Test
    public void test_parcelable_3_00004() {
        byte[] data = getData();

        FlagsAndroid result1 = new FlagsAndroid(data, 0, data[0]);
        FlagsAndroid result2 = FlagsAndroid.CREATOR.createFromByteArray(data);
        assertArrayEquals(result1.getBytes(), result2.getBytes());
    }

    @Test
    public void test_parcelable_3_00005() {
        byte[] data = getData();

        FlagsAndroid result1 = new FlagsAndroid(data, 0, data[0]);
        FlagsAndroid result2 = FlagsAndroid.CREATOR.createFromByteArray(data);
        assertArrayEquals(result1.getBytes(), result2.getBytes());
    }

    @Test
    public void test_parcelable_3_00006() {
        byte[] data = getData();

        FlagsAndroid result1 = new FlagsAndroid(data, 0, data[0]);
        FlagsAndroid result2 = FlagsAndroid.CREATOR.createFromByteArray(data);
        assertArrayEquals(result1.getBytes(), result2.getBytes());
    }

    @Test
    public void test_parcelable_3_00007() {
        byte[] data = getData();

        FlagsAndroid result1 = new FlagsAndroid(data, 0, data[0]);
        FlagsAndroid result2 = FlagsAndroid.CREATOR.createFromByteArray(data);
        assertArrayEquals(result1.getBytes(), result2.getBytes());
    }

}
