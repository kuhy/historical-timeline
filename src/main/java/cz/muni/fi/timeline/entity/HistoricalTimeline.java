package cz.muni.fi.timeline.entity;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


/**
 * Entity class that represents historical timeline.
 * Historical events can be added and removed from this timeline.
 *
 * @author Ondřej Kuhejda
 */
@Entity
public class HistoricalTimeline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany
    @JoinColumn
    private final Set<HistoricalEvent> historicalEvents = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HistoricalTimeline)) return false;
        HistoricalTimeline that = (HistoricalTimeline) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
