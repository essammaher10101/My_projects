import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class InMemoryEnrollmentRepository implements EnrollmentRepository {
    private final List<Enrollment> enrollments = new ArrayList<>();

    @Override
    public void add(Enrollment enrollment) {
        boolean exists = enrollments.contains(enrollment);
        if (exists){
           throw new IllegalArgumentException("Enrollment already exists!");
       }
        enrollments.add(enrollment);
    }

    @Override
    public Optional<Enrollment> findByStudentAndCourse(int studentId, int courseId) {
       return enrollments.stream().filter(e -> e.getCourseId() == courseId && e.getStudentId() == studentId).
                findFirst();
    }

    @Override
    public List<Enrollment> findByStudentId(int studentId) {
        return enrollments.stream().filter(e -> e.getStudentId() == studentId)
                .collect(Collectors.toList());
    }

    @Override
    public List<Enrollment> findByCourseId(int courseId) {
        return enrollments.stream().filter(e -> e.getCourseId() == courseId)
                .collect(Collectors.toList());
    }

    @Override
    public List<Enrollment> getAll() {
        return new ArrayList<>(this.enrollments);
    }
}