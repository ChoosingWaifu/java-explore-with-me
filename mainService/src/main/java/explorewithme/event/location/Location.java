package explorewithme.event.location;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class Location {

    private Number lat;
    private Number lon;
}