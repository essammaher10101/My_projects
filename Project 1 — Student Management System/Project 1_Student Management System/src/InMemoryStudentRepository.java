import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class InMemoryStudentRepository implements StudentRepository{
    private final List<Student> students = new ArrayList<>();

    @Override
    public void add(Student student) {
        boolean exists = students.stream()
                .anyMatch(s -> s.getId() == (student.getId()));
        if (exists) {
            throw new IllegalArgumentException("Student already exists!");
        }
        students.add(student);
    }

    @Override
    public Optional<Student> findById(int id) {
       return students.stream().filter(s -> s.getId() == id)
                .findFirst();
    }

    @Override
    public List<Student> getAll() {
        return new ArrayList<>(this.students);
    }
}
