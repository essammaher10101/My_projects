import java.util.List;
import java.util.Optional;

public interface CourseRepository {
    void add(Course course);
    Optional<Course> findById(int id);
    List<Course> getAll();
}