package cz.muni.fi.timeline.dao;

import cz.muni.fi.timeline.entity.Teacher;

import java.util.List;
import java.util.Optional;

/**
 * DAO interface for manipulation with Teacher entities.
 *
 * @author Ondřej Kuhejda
 */
public interface TeacherDao {

    /**
     * Adds new teacher to the database.
     */
    void create(Teacher teacher);

    /**
     * Returns all teachers from the database.
     */
    List<Teacher> findAll();

    /**
     * Finds teacher based on id.
     */
    Optional<Teacher> findById(Long id);

    /**
     * Finds teacher based on username.
     */
    Optional<Teacher> findByUsername(String username);

    /**
     * Updates values for some teacher in database.
     */
    void update(Teacher teacher);

    /**
     * Removes teacher from the database.
     */
    void remove(Teacher teacher);
}
