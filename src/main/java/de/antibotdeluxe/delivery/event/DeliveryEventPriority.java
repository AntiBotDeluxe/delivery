package de.antibotdeluxe.delivery.event;

public enum DeliveryEventPriority {

    LOW(0),
    MEDIUM(1),
    HIGH(2);

    private int priority;

    DeliveryEventPriority(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return this.priority;
    }
}
