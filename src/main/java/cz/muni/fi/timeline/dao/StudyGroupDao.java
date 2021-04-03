package cz.muni.fi.timeline.dao;

import cz.muni.fi.timeline.entity.StudyGroup;

import java.util.List;
import java.util.Optional;

/**
 * DAO interface for entity StudyGroup
 * @author Matej Macák
 */
public interface StudyGroupDao {

    /**
     * Adds StudyGroup into database
     * @param studyGroup entity that is added to the database
     */
    void create(StudyGroup studyGroup);

    /**
     * Returns all StudyGroups from the database
     * @return all StudyGroups from database
     */
    List<StudyGroup> findAll();

    /**
     * Finds StudyGroup based on ID
     * @param id id of the StudyGroup
     * @return StudyGroup with id
     */
    Optional<StudyGroup> findById(Long id);

    /**
     * Finds StudyGroup based on name
     * @param name name of looking StudyGroup
     * @return StudyGroup with given name
     */
    Optional<StudyGroup> findByName(String name);

    /**
     * Updates StudyGroup in the database
     * @param studyGroup is the updated entity
     */
    void update(StudyGroup studyGroup);

    /**
     * Removes StudyGroup from the database
     * @param studyGroup that is removed
     */
    void remove(StudyGroup studyGroup);
}
