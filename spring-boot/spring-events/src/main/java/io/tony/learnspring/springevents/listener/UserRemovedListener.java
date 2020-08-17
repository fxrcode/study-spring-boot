package io.tony.learnspring.springevents.listener;

import io.tony.learnspring.springevents.events.ReturnedEvent;
import io.tony.learnspring.springevents.events.UserRemovedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class UserRemovedListener {
    private boolean hitURE = false;
    private boolean hitReturnedEvent = false;
    private boolean hitConditional = false;
    private boolean hitAfterCommit = false;

    @EventListener
    public ReturnedEvent handleUserRemovedEvent(UserRemovedEvent event) {
        return new ReturnedEvent();
    }

    @EventListener
    public void handleReturnedEvent(ReturnedEvent event) {
        System.out.println("ReturnedEvent received.");
        hitURE = true;
    }

    @EventListener(condition = "#event.name eq 'reflectoring'")
    public void handleConditionalListener(UserRemovedEvent event) {
        System.out.println(String.format("User removeed (Conditional): %s", event.getName()));
        hitConditional = true;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleAfterUserRemoved(UserRemovedEvent event) {
        System.out.println(String.format("Handle userRemovedEvent will only be invoked after current commit done"));
        hitAfterCommit = true;
    }

    public boolean isHitURE() {
        return hitURE;
    }

    public boolean isHitReturnedEvent() {
        return hitReturnedEvent;
    }

    public boolean isHitConditional() {
        return hitConditional;
    }

    public boolean isHitAfterCommit() {
        return hitAfterCommit;
    }
}
