package io.tony.learnspring.springevents.listener;

import io.tony.learnspring.springevents.events.UserCreatedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class UserCreatedListener implements ApplicationListener<UserCreatedEvent> {

    private boolean hitUCE = false;

    @Override
    public void onApplicationEvent(@NonNull final UserCreatedEvent event) {
        System.out.println(String.format("User created: %s", event.getName()));
        hitUCE = true;
    }

    public boolean isHitUCE() {
        return hitUCE;
    }
}
