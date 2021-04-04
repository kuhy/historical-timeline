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
     *
     * @param teacher Teacher that will be created.
     */
    void create(Teacher teacher);

    /**
     * Returns all teachers from the database.
     *
     * @return All teachers.
     */
    List<Teacher> findAll();

    /**
     * Finds teacher based on id.
     *
     * @param id Id that is used for finding teachers.
     * @return Optional of teacher with given id.
     */
    Optional<Teacher> findById(Long id);

    /**
     * Finds teacher based on username.
     *
     * @param username Username that is used for finding teachers.
     * @return Optional of teacher with given username.
     */
    Optional<Teacher> findByUsername(String username);

    /**
     * Updates values for some teacher in database.
     *
     * @param teacher Detached teacher that will be updated.
     */
    void update(Teacher teacher);

    /**
     * Removes teacher from the database.
     *
     * @param teacher Teacher that will be removed.
     */
    void remove(Teacher teacher);
}
