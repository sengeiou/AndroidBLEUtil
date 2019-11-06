package org.im97mori.ble.advertising;

import android.bluetooth.le.ScanRecord;
import android.os.Parcel;

import androidx.annotation.NonNull;

import org.im97mori.ble.ByteArrayCreater;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.im97mori.ble.BLEConstants.BASE_UUID;
import static org.im97mori.ble.advertising.AdvertisingDataConstants.AdvertisingDataTypes.DATA_TYPE_LIST_OF_16_BIT_SERVICE_SOLICITATION_UUIDS;

/**
 * <p>
 * List of 16-bit Service Solicitation UUIDs
 * <p>
 * https://www.bluetooth.com/specifications/assigned-numbers/generic-access-profile/
 * </p>
 */
public class ListOf16BitServiceSolicitationUUIDs extends AbstractAdvertisingData {

    /**
     * @see ByteArrayCreater
     */
    public static final ByteArrayCreater<ListOf16BitServiceSolicitationUUIDs> CREATOR = new ByteArrayCreater<ListOf16BitServiceSolicitationUUIDs>() {

        /**
         * {@inheritDoc}
         */
        @Override
        @NonNull
        public ListOf16BitServiceSolicitationUUIDs createFromParcel(@NonNull Parcel in) {
            return new ListOf16BitServiceSolicitationUUIDs(in);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        @NonNull
        public ListOf16BitServiceSolicitationUUIDs[] newArray(int size) {
            return new ListOf16BitServiceSolicitationUUIDs[size];
        }

        /**
         * {@inheritDoc}
         */
        @NonNull
        @Override
        public ListOf16BitServiceSolicitationUUIDs createFromByteArray(@NonNull byte[] values) {
            return new ListOf16BitServiceSolicitationUUIDs(values, 0, values.length - 1);
        }

    };

    /**
     * UUID list
     */
    private final List<UUID> mUuidList;

    /**
     * Constructor for Complete List of 16-bit Service Class UUIDs
     *
     * @param data   byte array from {@link ScanRecord#getBytes()}
     * @param offset data offset
     * @param length 1st octed of Advertising Data
     */
    public ListOf16BitServiceSolicitationUUIDs(@NonNull byte[] data
            , int offset
            , int length) {
        super(length);

        // combine with BASE UUID
        long lsb = BASE_UUID.getLeastSignificantBits();
        long msb = BASE_UUID.getMostSignificantBits();
        List<UUID> uuidList = new ArrayList<>();
        for (int i = offset + 2; i < offset + length + 1; i += 2) {
            long target = data[i] & 0xff;
            target |= (data[i + 1] & 0xff) << 8;
            target = target << 32;
            uuidList.add(new UUID(msb | target, lsb));
        }
        mUuidList = Collections.synchronizedList(Collections.unmodifiableList(uuidList));
    }

    /**
     * Constructor from {@link Parcel}
     *
     * @param in Parcel
     */
    private ListOf16BitServiceSolicitationUUIDs(Parcel in) {
        super(in.readInt());

        List<UUID> list = new ArrayList<>();
        in.readList(list, this.getClass().getClassLoader());
        mUuidList = Collections.synchronizedList(Collections.unmodifiableList(list));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(mLength);
        dest.writeList(mUuidList);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getDataType() {
        return DATA_TYPE_LIST_OF_16_BIT_SERVICE_SOLICITATION_UUIDS;
    }

    /**
     * @return UUID list
     */
    @NonNull
    public List<UUID> getUuidList() {
        return mUuidList;
    }

}