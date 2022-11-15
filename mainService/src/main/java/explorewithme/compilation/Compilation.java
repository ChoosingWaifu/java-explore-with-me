package explorewithme.compilation;

import explorewithme.event.Event;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "compilations")
public class Compilation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private Boolean pinned;
    @NotNull
    private String title;

    @ManyToMany(mappedBy = "compilations")
    private Set<Event> events = new HashSet<>();
}
