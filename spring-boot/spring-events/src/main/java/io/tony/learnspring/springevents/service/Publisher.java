package io.tony.learnspring.springevents.service;

import io.tony.learnspring.springevents.events.UserCreatedEvent;
import io.tony.learnspring.springevents.events.UserRemovedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class Publisher {
    @Autowired
    ApplicationEventPublisher publisher;

    public void publishEvent(final String name) {
        // Publish event creaated by extending ApplicationEvent
        publisher.publishEvent(new UserCreatedEvent(this, name));
        // When the object we’re publishing is not an ApplicationEvent, Spring will automatically wrap
        //  it in a PayloadApplicationEvent for us.
        publisher.publishEvent(new UserRemovedEvent(name));
    }

    public void publishAsyncEvent(final String name) {
        publisher.publishEvent(new UserCreatedEvent(this, name));
    }

    public void publishTransactionEvent(final String name) {
        publisher.publishEvent(new UserRemovedEvent("transaction-"+name));
    }
}
