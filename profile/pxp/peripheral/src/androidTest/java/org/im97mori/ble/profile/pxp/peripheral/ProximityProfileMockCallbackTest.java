package org.im97mori.ble.profile.pxp.peripheral;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Test;

import static org.im97mori.ble.BLEConstants.ServiceUUID.LINK_LOSS_SERVICE;
import static org.junit.Assert.assertEquals;

@SuppressWarnings("ConstantConditions")
public class ProximityProfileMockCallbackTest {

    @Test
    public void test_getServiceUUID_00001() {
        assertEquals(LINK_LOSS_SERVICE, new ProximityProfileMockCallback(ApplicationProvider.getApplicationContext(), null, null, null).getServiceUUID());
    }

}
