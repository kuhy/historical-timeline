package cz.muni.fi.timeline.data;


/**
 * Interface for classes that populates the database with the sample data via services.
 *
 * @author Ondřej Kuhejda
 */
public interface TimelineDataLoader {

    /**
     * Method that is responsible for populating database.
     */
    void loadData();
}
