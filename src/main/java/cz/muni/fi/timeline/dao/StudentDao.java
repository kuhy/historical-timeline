package cz.muni.fi.timeline.dao;

import cz.muni.fi.timeline.entity.Student;

import java.util.List;
import java.util.Optional;

/**
 * DAO interface for entity Student
 * @author Matej Macák
 */
public interface StudentDao {

    /**
     * Adds Student into database
     * @param student entity that is added to the database
     */
    void create(Student student);

    /**
     * Returns all Students from database
     * @return all students from database
     */
    List<Student> findAll();

    /**
     * Finds Student based on ID
     * @param id id of Student
     * @return Student with id
     */
    Optional<Student> findById(Long id);

    /**
     * Finds Student based on ID
     * @param username username of looking Student
     * @return Student with given username
     */
    Optional<Student> findByUserName(String username);

    /**
     * Updates Student in the database
     * @param student is the updated entity
     */
    void update(Student student);

    /**
     * Removes Student from the database
     * @param student that is removed
     */
    void remove(Student student);
}
