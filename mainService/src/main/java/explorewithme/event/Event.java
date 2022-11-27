package explorewithme.event;

import explorewithme.category.Category;
import explorewithme.event.dto.EventState;
import explorewithme.user.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

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
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
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
    private LocalDateTime eventDate;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "initiator_id")
    private User initiator;
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

}

