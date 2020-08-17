package io.tony.learnspring.springevents.events;

import org.springframework.context.ApplicationEvent;

/**
 * Prior Spring Framework 4.2 style
 */

public class UserCreatedEvent extends ApplicationEvent {
    private String name;

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public UserCreatedEvent(Object source, String name) {
        super(source);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
