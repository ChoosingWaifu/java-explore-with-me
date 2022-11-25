package explorewithme.event.dto;

public enum EventState {
    PENDING,
    PUBLISHED,
    REJECTED,
    CANCELED;

    public static EventState fromStr(String state) {
        if (state == null) {
            return null;
        }
        for (EventState eventState : values()) {
            if (eventState.name().equalsIgnoreCase(state)) {
                return eventState;
            }
        }
        return null;
    }

}
