package io.swagslash.settlersofcatan;

import android.view.View;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Unit Tests of the Resource Discard Class
 */
@RunWith(RobolectricTestRunner.class)
public class ResourceDiscardTest {

    @Test
    public void TestOnCreate() {
        try {
            //execute code that you expect not to throw Exceptions.
            //ResourceDiscardActivity r = new ResourceDiscardActivity();
            ResourceDiscardActivity activity = Robolectric.setupActivity(ResourceDiscardActivity.class);
            View viewById = activity.findViewById(R.id.textViewTitel);
            assertThat(viewById, notNullValue());
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    @Ignore
    public void Test2() {
        try {
            //execute code that you expect not to throw Exceptions.
            ResourceDiscardActivity r = new ResourceDiscardActivity();
            View viewById = r.findViewById(R.layout.activity_resource_discard);
            r.printCountToDiscard();

            //When no exception Test bestanden
            assertTrue(true);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    @Ignore
    public void TestConfirmDiscard(){
        try{
            //execute code that you expect not to throw Exceptions.
            ResourceDiscardActivity r = new ResourceDiscardActivity();
            View viewById = r.findViewById(R.layout.activity_resource_discard);
            r.confirmDiscard();

            //When no exception Test bestanden
            assertTrue(true);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    @Ignore
    public void TestRandomDiscard() {
        try {
            //execute code that you expect not to throw Exceptions.
            ResourceDiscardActivity r = new ResourceDiscardActivity();
            View viewById = r.findViewById(R.layout.activity_resource_discard);
            r.randomDiscard();
            //When no exception Test bestanden
            assertTrue(true);
        } catch (Exception e) {
        }
    }

    @Test
    @Ignore
    public void TestCountdown() {
        try {
            //execute code that you expect not to throw Exceptions.
            ResourceDiscardActivity r = new ResourceDiscardActivity();
            View viewById = r.findViewById(R.layout.activity_resource_discard);
            r.countdown25to0();
            //When no exception Test bestanden
            assertTrue(true);
        } catch (Exception e) {
        }
    }

    @Test
    @Ignore
    public void TestResource() {
        try {
            //execute code that you expect not to throw Exceptions.
            ResourceDiscardActivity r = new ResourceDiscardActivity();
            View viewById = r.findViewById(R.layout.activity_resource_discard);
            int sumResource = r.erz + r.wolle + r.getreide + r.holz + r.lehm;
            r.randomDiscard();
            int sumResourceAfterRandomDiscard = r.erz + r.wolle + r.getreide + r.holz + r.lehm;
            Assert.assertNotEquals(sumResource, sumResourceAfterRandomDiscard);

        } catch (Exception e) {
        }
    }

}
