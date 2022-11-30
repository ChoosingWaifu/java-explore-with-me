package explorewithme.dto;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class NewHit {

    private String app;

    private String uri;

    private String ip;

}
