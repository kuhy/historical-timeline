package cz.muni.fi.timeline.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


/**
 * Entity class that represents historical timeline.
 * Historical events can be added and removed from this timeline.
 * Also comments can be added and removed from this timeline.
 *
 * @author Ondřej Kuhejda
 */
@Entity
public class HistoricalTimeline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @NotNull
    @Getter
    @Setter
    private String name;

    @OneToMany
    @JoinColumn
    private final Set<HistoricalEvent> historicalEvents = new HashSet<>();

    @OneToMany
    @JoinColumn
    private final Set<TimelineComment> timelineComments = new HashSet<>();

    /**
     * Returns all events in this timeline.
     */
    public Set<HistoricalEvent> getHistoricalEvents() {
        return Collections.unmodifiableSet(historicalEvents);
    }

    /**
     * Adds new event to the timeline.
     */
    public void addHistoricalEvent(HistoricalEvent event) {
        historicalEvents.add(event);
    }

    /**
     * Removes event from the timeline.
     */
    public void removeHistoricalEvent(HistoricalEvent event) {
        historicalEvents.remove(event);
    }

    /**
     * Returns all comments in this timeline.
     */
    public Set<TimelineComment> getTimelineComments() {
        return Collections.unmodifiableSet(timelineComments);
    }

    /**
     * Adds new comment to the timeline.
     */
    public void addTimelineComment(TimelineComment comment) {
        timelineComments.add(comment);
    }

    /**
     * Removes comment from the timeline.
     */
    public void removeTimelineComment(TimelineComment comment) {
        timelineComments.remove(comment);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HistoricalTimeline)) return false;
        HistoricalTimeline that = (HistoricalTimeline) o;
        return Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
