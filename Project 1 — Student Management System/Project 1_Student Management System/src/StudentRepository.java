import java.util.List;
import java.util.Optional;

public interface StudentRepository {
    void add(Student student);
    Optional<Student> findById(int id);
    List<Student> getAll();
}
