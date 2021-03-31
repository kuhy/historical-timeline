package cz.muni.fi.timeline.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class TimelineComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
