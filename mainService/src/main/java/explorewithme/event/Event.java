package explorewithme.event;

import explorewithme.compilation.Compilation;
import explorewithme.event.dto.EventState;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Min(20)
    @Max(2000)
    private String annotation;
    @NotNull
    private Long category;
    private Long confirmedRequests;
    @NotNull
    private String createdOn;
    @NotNull
    @Min(20)
    @Max(7000)
    private String description;
    @NotNull
    private LocalDateTime eventDate;//"yyyy-MM-dd HH:mm:ss"
    @NotNull
    private Long initiator;
    @NotNull
    private Number lat;
    @NotNull
    private Number lon;
    private Boolean paid;//default false
    private Long participantLimit;//default(0)
    @NotNull
    private LocalDateTime publishedOn;
    private Boolean requestModeration;//default??
    @Enumerated(EnumType.STRING)
    private EventState state;
    @NotNull
    @Min(3)
    @Max(120)
    private String title;
    private Long views;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "compilations_events",
            joinColumns = { @JoinColumn(name = "compilation_id") },
            inverseJoinColumns = { @JoinColumn(name = "event_id")   }
    )
    private Set<Compilation> compilations = new HashSet<>();
}

