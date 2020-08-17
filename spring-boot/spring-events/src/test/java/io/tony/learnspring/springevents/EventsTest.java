package io.tony.learnspring.springevents;

import io.tony.learnspring.springevents.listener.AsyncListener;
import io.tony.learnspring.springevents.listener.UserCreatedListener;
import io.tony.learnspring.springevents.listener.UserRemovedListener;
import io.tony.learnspring.springevents.service.Publisher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.util.Assert.isTrue;

/**
 * learned from https://stackoverflow.com/questions/19896870/why-is-my-spring-autowired-field-null
 *  simply use SpringBootTest, ow, config is wrong and autowired are NULL!
 */
@SpringBootTest
public class EventsTest {

    @Autowired
    Publisher publisher;
    @Autowired
    UserCreatedListener ucListener;
    @Autowired
    UserRemovedListener urListener;
    @Autowired
    AsyncListener asyncListener;

    /**
     * learned from https://github.com/eugenp/tutorials/blob/master/spring-core-2/src/test/java/com/baeldung/springevents/synchronous/SynchronousCustomSpringEventsIntegrationTest.java
     */
//    @Test
//    public void testSyncCreatedEvents() {
//        isTrue(!ucListener.isHitUCE(), "Should be false");
//        publisher.publishEvent("fxrc");
//        isTrue(ucListener.isHitUCE(), "handled so be true");
//    }
//
//    @Test
//    public void testSyncRemovedEvents() {
//        isTrue(!urListener.isHitURE(), "Should be false");
//        publisher.publishEvent("fxrc");
//        isTrue(urListener.isHitURE(), "handled so be true");
//
//        isTrue(!urListener.isHitConditional(), "false");
//        publisher.publishEvent("reflectoring");
//        isTrue(urListener.isHitConditional(), "true");
//    }
//
//    @Test
//    public void testAsyncEvents() throws InterruptedException {
//        isTrue(!asyncListener.isHitAsync(), "false");
//        publisher.publishAsyncEvent("ping async");
//        // async listener will not block till react, so I add small pause.
//        Thread.sleep(100);
//        isTrue(asyncListener.isHitAsync(), "true");
//    }

//    @Test
//    public void testTransactionEvents() {
//        isTrue(!urListener.isHitAfterCommit(), "false");
//        publisher.publishTransactionEvent("alibaba");
//        isTrue(urListener.isHitConditional(), "true");
//    }
}
