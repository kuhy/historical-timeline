package cz.muni.fi.timeline.entity;

import javax.persistence.Lob;

public class HistoricalEvent {

    @Lob
    private byte[] image;
}
