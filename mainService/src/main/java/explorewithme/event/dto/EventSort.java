package explorewithme.event.dto;

public enum EventSort {
    EVENT_DATE,
    VIEWS;

    public static EventSort from(String sort) {
        if (sort == null) {
            return null;
        }
        for (EventSort eventSort : values()) {
            if (eventSort.name().equalsIgnoreCase(sort)) {
                return eventSort;
            }
        }
        return null;
    }
}
