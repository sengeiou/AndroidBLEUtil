package org.im97mori.ble.advertising.filter;

import org.im97mori.ble.advertising.AdvertisingDataParser;
import org.im97mori.ble.advertising.PublicTargetAddressAndroid;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

import static org.im97mori.ble.advertising.AdvertisingDataConstants.AdvertisingDataTypes.DATA_TYPE_PUBLIC_TARGET_ADDRESS;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PublicTargetAddressFilterTest {

    @Test
    public void test_001() {
        byte[] actualData = new byte[0];

        AdvertisingDataParser parser = new AdvertisingDataParser.Builder(true).build();
        AdvertisingDataParser.AdvertisingDataParseResult result = parser.parse(actualData);
        AdvertisingDataFilter<AdvertisingDataParser.AdvertisingDataParseResult> filter = new PublicTargetAddressFilter(null, null);
        assertTrue(filter.isMatched(result));
    }

    @Test
    public void test_002() {
        byte[] address = new byte[6];
        address[0] = 1;
        address[1] = 2;
        address[2] = 3;
        address[3] = 4;
        address[4] = 5;
        address[5] = 6;

        byte[] expectData = new byte[8];
        expectData[0] = 7;
        expectData[1] = DATA_TYPE_PUBLIC_TARGET_ADDRESS;
        System.arraycopy(address, 0, expectData, 2, address.length);

        byte[] actualData = new byte[0];

        AdvertisingDataParser parser = new AdvertisingDataParser.Builder(true).build();
        AdvertisingDataParser.AdvertisingDataParseResult result = parser.parse(actualData);
        AdvertisingDataFilter<AdvertisingDataParser.AdvertisingDataParseResult> filter = new PublicTargetAddressFilter(PublicTargetAddressAndroid.CREATOR.createFromByteArray(expectData), null);
        assertFalse(filter.isMatched(result));
    }

    @Test
    public void test_003() {
        byte[] address = new byte[6];
        address[0] = 1;
        address[1] = 2;
        address[2] = 3;
        address[3] = 4;
        address[4] = 5;
        address[5] = 6;

        byte[] actualData = new byte[8];
        actualData[0] = 7;
        actualData[1] = DATA_TYPE_PUBLIC_TARGET_ADDRESS;
        System.arraycopy(address, 0, actualData, 2, address.length);

        AdvertisingDataParser parser = new AdvertisingDataParser.Builder(true).build();
        AdvertisingDataParser.AdvertisingDataParseResult result = parser.parse(actualData);
        AdvertisingDataFilter<AdvertisingDataParser.AdvertisingDataParseResult> filter = new PublicTargetAddressFilter(null, null);
        assertFalse(filter.isMatched(result));
    }

    @Test
    public void test_004() {
        byte[] address = new byte[6];
        address[0] = 1;
        address[1] = 2;
        address[2] = 3;
        address[3] = 4;
        address[4] = 5;
        address[5] = 6;

        byte[] expectData = new byte[8];
        expectData[0] = 7;
        expectData[1] = DATA_TYPE_PUBLIC_TARGET_ADDRESS;
        System.arraycopy(address, 0, expectData, 2, address.length);

        byte[] actualData = Arrays.copyOf(expectData, expectData.length);

        AdvertisingDataParser parser = new AdvertisingDataParser.Builder(true).build();
        AdvertisingDataParser.AdvertisingDataParseResult result = parser.parse(actualData);
        AdvertisingDataFilter<AdvertisingDataParser.AdvertisingDataParseResult> filter = new PublicTargetAddressFilter(PublicTargetAddressAndroid.CREATOR.createFromByteArray(expectData), null);
        assertTrue(filter.isMatched(result));
    }

    @Test
    public void test_201() {
        byte[] address = new byte[12];
        address[0] = 1;
        address[1] = 2;
        address[2] = 3;
        address[3] = 4;
        address[4] = 5;
        address[5] = 6;
        address[6] = 7;
        address[7] = 8;
        address[8] = 9;
        address[9] = 10;
        address[10] = 11;
        address[11] = 12;

        byte[] expectData = new byte[14];
        expectData[0] = 13;
        expectData[1] = DATA_TYPE_PUBLIC_TARGET_ADDRESS;
        System.arraycopy(address, 0, expectData, 2, address.length);

        byte[] actualData = new byte[8];
        actualData[0] = 7;
        actualData[1] = DATA_TYPE_PUBLIC_TARGET_ADDRESS;
        System.arraycopy(address, 0, expectData, 2, address.length / 2);

        AdvertisingDataParser parser = new AdvertisingDataParser.Builder(true).build();
        AdvertisingDataParser.AdvertisingDataParseResult result = parser.parse(actualData);
        AdvertisingDataFilter<AdvertisingDataParser.AdvertisingDataParseResult> filter = new PublicTargetAddressFilter(new PublicTargetAddressAndroid(expectData, 0, expectData.length - 1), null);
        assertFalse(filter.isMatched(result));
    }

    @Test
    public void test_202() {
        byte[] address = new byte[12];
        address[0] = 1;
        address[1] = 2;
        address[2] = 3;
        address[3] = 4;
        address[4] = 5;
        address[5] = 6;
        address[6] = 7;
        address[7] = 8;
        address[8] = 9;
        address[9] = 10;
        address[10] = 11;
        address[11] = 12;

        byte[] actualData = new byte[14];
        actualData[0] = 13;
        actualData[1] = DATA_TYPE_PUBLIC_TARGET_ADDRESS;
        System.arraycopy(address, 0, actualData, 2, address.length);

        byte[] expectData = new byte[8];
        expectData[0] = 7;
        expectData[1] = DATA_TYPE_PUBLIC_TARGET_ADDRESS;
        System.arraycopy(address, 0, expectData, 2, address.length / 2);

        AdvertisingDataParser parser = new AdvertisingDataParser.Builder(true).build();
        AdvertisingDataParser.AdvertisingDataParseResult result = parser.parse(actualData);
        AdvertisingDataFilter<AdvertisingDataParser.AdvertisingDataParseResult> filter = new PublicTargetAddressFilter(new PublicTargetAddressAndroid(expectData, 0, expectData.length - 1), null);
        assertFalse(filter.isMatched(result));
    }

    @Test
    public void test_203() {
        byte[] address = new byte[12];
        address[0] = 1;
        address[1] = 2;
        address[2] = 3;
        address[3] = 4;
        address[4] = 5;
        address[5] = 6;
        address[6] = 7;
        address[7] = 8;
        address[8] = 9;
        address[9] = 10;
        address[10] = 11;
        address[11] = 12;

        byte[] expectData = new byte[14];
        expectData[0] = 13;
        expectData[1] = DATA_TYPE_PUBLIC_TARGET_ADDRESS;
        System.arraycopy(address, 0, expectData, 2, address.length);

        byte[] actualData = Arrays.copyOf(expectData, expectData.length);

        AdvertisingDataParser parser = new AdvertisingDataParser.Builder(true).build();
        AdvertisingDataParser.AdvertisingDataParseResult result = parser.parse(actualData);
        AdvertisingDataFilter<AdvertisingDataParser.AdvertisingDataParseResult> filter = new PublicTargetAddressFilter(new PublicTargetAddressAndroid(expectData, 0, expectData.length - 1), null);
        assertTrue(filter.isMatched(result));
    }

    @Test
    public void test_301() {
        byte[] address = new byte[6];
        address[0] = 1;
        address[1] = 2;
        address[2] = 3;
        address[3] = 4;
        address[4] = 5;
        address[5] = 6;

        byte[] expectData = new byte[8];
        expectData[0] = 7;
        expectData[1] = DATA_TYPE_PUBLIC_TARGET_ADDRESS;
        System.arraycopy(address, 0, expectData, 2, address.length);

        byte[] actualData = Arrays.copyOf(expectData, expectData.length);

        AdvertisingDataParser parser = new AdvertisingDataParser.Builder(true).build();
        AdvertisingDataParser.AdvertisingDataParseResult result = parser.parse(actualData);
        AdvertisingDataFilter<AdvertisingDataParser.AdvertisingDataParseResult> filter = new PublicTargetAddressFilter(PublicTargetAddressAndroid.CREATOR.createFromByteArray(expectData), new LinkedList<byte[]>());
        assertFalse(filter.isMatched(result));
    }

    @Test
    public void test_302() {
        byte[] address = new byte[6];
        address[0] = 1;
        address[1] = 2;
        address[2] = 3;
        address[3] = 4;
        address[4] = 5;
        address[5] = 6;

        byte[] expectData = new byte[8];
        expectData[0] = 7;
        expectData[1] = DATA_TYPE_PUBLIC_TARGET_ADDRESS;
        System.arraycopy(address, 0, expectData, 2, address.length);

        byte[] actualData = Arrays.copyOf(expectData, expectData.length);

        AdvertisingDataParser parser = new AdvertisingDataParser.Builder(true).build();
        AdvertisingDataParser.AdvertisingDataParseResult result = parser.parse(actualData);
        AdvertisingDataFilter<AdvertisingDataParser.AdvertisingDataParseResult> filter = new PublicTargetAddressFilter(PublicTargetAddressAndroid.CREATOR.createFromByteArray(expectData), Collections.singletonList(new byte[0]));
        assertFalse(filter.isMatched(result));
    }

    @Test
    public void test_303() {
        byte[] address = new byte[6];
        address[0] = 1;
        address[1] = 2;
        address[2] = 3;
        address[3] = 4;
        address[4] = 5;
        address[5] = 6;

        byte[] expectData = new byte[8];
        expectData[0] = 7;
        expectData[1] = DATA_TYPE_PUBLIC_TARGET_ADDRESS;
        System.arraycopy(address, 0, expectData, 2, address.length);

        byte[] actualData = Arrays.copyOf(expectData, expectData.length);

        byte[] bitmask = new byte[6];

        AdvertisingDataParser parser = new AdvertisingDataParser.Builder(true).build();
        AdvertisingDataParser.AdvertisingDataParseResult result = parser.parse(actualData);
        AdvertisingDataFilter<AdvertisingDataParser.AdvertisingDataParseResult> filter = new PublicTargetAddressFilter(PublicTargetAddressAndroid.CREATOR.createFromByteArray(expectData), Collections.singletonList(bitmask));
        assertTrue(filter.isMatched(result));
    }

    @Test
    public void test_304() {
        byte[] address = new byte[6];
        address[0] = 1;
        address[1] = 2;
        address[2] = 3;
        address[3] = 4;
        address[4] = 5;
        address[5] = 6;

        byte[] expectData = new byte[8];
        expectData[0] = 7;
        expectData[1] = DATA_TYPE_PUBLIC_TARGET_ADDRESS;
        System.arraycopy(address, 0, expectData, 2, address.length);

        byte[] actualData = Arrays.copyOf(expectData, expectData.length);

        expectData[2] = 7;

        byte[] bitmask = new byte[6];

        AdvertisingDataParser parser = new AdvertisingDataParser.Builder(true).build();
        AdvertisingDataParser.AdvertisingDataParseResult result = parser.parse(actualData);
        AdvertisingDataFilter<AdvertisingDataParser.AdvertisingDataParseResult> filter = new PublicTargetAddressFilter(PublicTargetAddressAndroid.CREATOR.createFromByteArray(expectData), Collections.singletonList(bitmask));
        assertTrue(filter.isMatched(result));
    }

    @Test
    public void test_305() {
        byte[] address = new byte[6];
        address[0] = 1;
        address[1] = 2;
        address[2] = 3;
        address[3] = 4;
        address[4] = 5;
        address[5] = 6;

        byte[] expectData = new byte[8];
        expectData[0] = 7;
        expectData[1] = DATA_TYPE_PUBLIC_TARGET_ADDRESS;
        System.arraycopy(address, 0, expectData, 2, address.length);

        byte[] actualData = Arrays.copyOf(expectData, expectData.length);

        byte[] bitmask = new byte[6];
        Arrays.fill(bitmask, (byte) 0xff);

        AdvertisingDataParser parser = new AdvertisingDataParser.Builder(true).build();
        AdvertisingDataParser.AdvertisingDataParseResult result = parser.parse(actualData);
        AdvertisingDataFilter<AdvertisingDataParser.AdvertisingDataParseResult> filter = new PublicTargetAddressFilter(PublicTargetAddressAndroid.CREATOR.createFromByteArray(expectData), Collections.singletonList(bitmask));
        assertTrue(filter.isMatched(result));
    }

    @Test
    public void test_306() {
        byte[] address = new byte[6];
        address[0] = 1;
        address[1] = 2;
        address[2] = 3;
        address[3] = 4;
        address[4] = 5;
        address[5] = 6;

        byte[] expectData = new byte[8];
        expectData[0] = 7;
        expectData[1] = DATA_TYPE_PUBLIC_TARGET_ADDRESS;
        System.arraycopy(address, 0, expectData, 2, address.length);

        byte[] actualData = Arrays.copyOf(expectData, expectData.length);

        expectData[2] = 7;

        byte[] bitmask = new byte[6];
        Arrays.fill(bitmask, (byte) 0xff);

        AdvertisingDataParser parser = new AdvertisingDataParser.Builder(true).build();
        AdvertisingDataParser.AdvertisingDataParseResult result = parser.parse(actualData);
        AdvertisingDataFilter<AdvertisingDataParser.AdvertisingDataParseResult> filter = new PublicTargetAddressFilter(PublicTargetAddressAndroid.CREATOR.createFromByteArray(expectData), Collections.singletonList(bitmask));
        assertFalse(filter.isMatched(result));
    }

    @Test
    public void test_401() {
        byte[] address = new byte[12];
        address[0] = 1;
        address[1] = 2;
        address[2] = 3;
        address[3] = 4;
        address[4] = 5;
        address[5] = 6;
        address[6] = 7;
        address[7] = 8;
        address[8] = 9;
        address[9] = 10;
        address[10] = 11;
        address[11] = 12;

        byte[] expectData = new byte[14];
        expectData[0] = 13;
        expectData[1] = DATA_TYPE_PUBLIC_TARGET_ADDRESS;
        System.arraycopy(address, 0, expectData, 2, address.length);

        byte[] actualData = Arrays.copyOf(expectData, expectData.length);

        byte[] bitmask = new byte[6];

        AdvertisingDataParser parser = new AdvertisingDataParser.Builder(true).build();
        AdvertisingDataParser.AdvertisingDataParseResult result = parser.parse(actualData);
        AdvertisingDataFilter<AdvertisingDataParser.AdvertisingDataParseResult> filter = new PublicTargetAddressFilter(new PublicTargetAddressAndroid(expectData, 0, expectData.length - 1), Collections.singletonList(bitmask));
        assertFalse(filter.isMatched(result));
    }

    @Test
    public void test_402() {
        byte[] address = new byte[12];
        address[0] = 1;
        address[1] = 2;
        address[2] = 3;
        address[3] = 4;
        address[4] = 5;
        address[5] = 6;
        address[6] = 7;
        address[7] = 8;
        address[8] = 9;
        address[9] = 10;
        address[10] = 11;
        address[11] = 12;

        byte[] expectData = new byte[14];
        expectData[0] = 13;
        expectData[1] = DATA_TYPE_PUBLIC_TARGET_ADDRESS;
        System.arraycopy(address, 0, expectData, 2, address.length);

        byte[] actualData = Arrays.copyOf(expectData, expectData.length);

        byte[] bitmask = new byte[6];

        AdvertisingDataParser parser = new AdvertisingDataParser.Builder(true).build();
        AdvertisingDataParser.AdvertisingDataParseResult result = parser.parse(actualData);
        AdvertisingDataFilter<AdvertisingDataParser.AdvertisingDataParseResult> filter = new PublicTargetAddressFilter(new PublicTargetAddressAndroid(expectData, 0, expectData.length - 1), Arrays.asList(bitmask, bitmask));
        assertTrue(filter.isMatched(result));
    }

    @Test
    public void test_403() {
        byte[] address = new byte[12];
        address[0] = 1;
        address[1] = 2;
        address[2] = 3;
        address[3] = 4;
        address[4] = 5;
        address[5] = 6;
        address[6] = 7;
        address[7] = 8;
        address[8] = 9;
        address[9] = 10;
        address[10] = 11;
        address[11] = 12;

        byte[] expectData = new byte[14];
        expectData[0] = 13;
        expectData[1] = DATA_TYPE_PUBLIC_TARGET_ADDRESS;
        System.arraycopy(address, 0, expectData, 2, address.length);

        byte[] actualData = Arrays.copyOf(expectData, expectData.length);

        expectData[2] = 13;

        byte[] bitmask = new byte[6];
        Arrays.fill(bitmask, (byte) 0xff);

        AdvertisingDataParser parser = new AdvertisingDataParser.Builder(true).build();
        AdvertisingDataParser.AdvertisingDataParseResult result = parser.parse(actualData);
        AdvertisingDataFilter<AdvertisingDataParser.AdvertisingDataParseResult> filter = new PublicTargetAddressFilter(new PublicTargetAddressAndroid(expectData, 0, expectData.length - 1), Arrays.asList(bitmask, bitmask));
        assertFalse(filter.isMatched(result));
    }
}
