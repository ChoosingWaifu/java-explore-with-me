package explorewithme.event;

import explorewithme.compilation.Compilation;
import explorewithme.event.dto.EventState;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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
    @Column(name = "confirmed_requests")
    private Long confirmedRequests;
    @Column(name = "created_on")
    @CreationTimestamp
    private LocalDateTime createdOn;
    @NotNull
    @Min(20)
    @Max(7000)
    private String description;
    @Column(name = "event_date")
    @NotNull
    private LocalDateTime eventDate;//"yyyy-MM-dd HH:mm:ss"
    @NotNull
    private Long initiator;
    @NotNull
    private Float lat;
    @NotNull
    private Float lon;
    private Boolean paid;
    @Column(name = "participant_limit")
    private Long participantLimit;
    @Column(name = "published_on")
    private LocalDateTime publishedOn;
    @Column(name = "request_moderation")
    private Boolean requestModeration;
    @Enumerated(EnumType.STRING)
    private EventState state;
    @NotNull
    @Min(3)
    @Max(120)
    private String title;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "compilations_events",
            joinColumns = { @JoinColumn(name = "compilation_id") },
            inverseJoinColumns = { @JoinColumn(name = "event_id")   }
    )
    private Set<Compilation> compilations = new HashSet<>();
}

