import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryCourseRepository implements CourseRepository {
    private final List<Course> courses = new ArrayList<>();

    @Override
    public void add(Course course) {
        boolean exists = courses.stream()
                .anyMatch(c -> c.getId() == course.getId());
        if (exists) {
            throw new IllegalArgumentException("Course already exists!");
        }
        courses.add(course);
    }

    @Override
    public Optional<Course> findById(int id) {
        return courses.stream()
                .filter(c -> c.getId() == id)
                .findFirst();
    }

    @Override
    public List<Course> getAll() {
        return new ArrayList<>(this.courses);
    }
}