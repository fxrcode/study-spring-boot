package io.tony.learnspring.springevents.listener;

import io.tony.learnspring.springevents.events.UserCreatedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class AsyncListener {
    private boolean hitAsync = false;

    @Async
    @EventListener
    public void handleAsyncEvent(UserCreatedEvent event) {
        System.out.println(String.format("Async event received: %s", event.getName()));
        hitAsync = true;
    }

    public boolean isHitAsync() {
        return hitAsync;
    }
}
