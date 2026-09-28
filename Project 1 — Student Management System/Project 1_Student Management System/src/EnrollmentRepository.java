import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository {
    Optional<Enrollment> findByStudentAndCourse(int studentId, int courseId);
    List<Enrollment> findByStudentId(int studentId);
    List<Enrollment> findByCourseId(int courseId);
    void add(Enrollment enrollment);
    List<Enrollment> getAll();
}
