package cz.muni.fi.timeline.service;

/**
 * Exception for handling adding the same student into group
 * @author Matej Macák
 */
public class AlreadyInStudyGroup extends Exception {

    public AlreadyInStudyGroup() {
        super();
    }

    public AlreadyInStudyGroup(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyInStudyGroup(String message) {
        super(message);
    }

    public AlreadyInStudyGroup(Throwable cause) {
        super(cause);
    }

}
