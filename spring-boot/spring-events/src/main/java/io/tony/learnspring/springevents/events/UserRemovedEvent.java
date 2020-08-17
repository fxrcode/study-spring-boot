package io.tony.learnspring.springevents.events;

/**
 * Since Spring Framework 4.2, don't need to extend ApplicationEvent, if we ...
 */
public class UserRemovedEvent {
    private String name;

    public UserRemovedEvent(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
