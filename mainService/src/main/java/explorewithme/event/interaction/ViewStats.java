package explorewithme.event.interaction;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class ViewStats {

    private String app;

    private String uri;

    private Long hits;

}