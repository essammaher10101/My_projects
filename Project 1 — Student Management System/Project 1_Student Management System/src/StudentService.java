import java.util.*;
import java.util.stream.Collectors;

public class StudentService {
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    public StudentService(StudentRepository studentRepository,
                          CourseRepository courseRepository,
                          EnrollmentRepository enrollmentRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    // Average grade for one student, across all their enrollments
    public double getAverageGradeForStudent(int studentId) {
        List<Enrollment> enrollments = enrollmentRepository.findByStudentId(studentId);
        return enrollments.stream()
                .mapToDouble(Enrollment::getAverageGrade)
                .average()
                .orElse(0.0);
    }

    // Top N students by average grade, highest first
    public List<Student> getTopNStudents(int n) {
        return studentRepository.getAll().stream()
                .sorted(Comparator.comparingDouble(
                        (Student s) -> getAverageGradeForStudent(s.getId())
                ).reversed())
                .limit(n)
                .collect(Collectors.toList());
    }

    // Split students into pass/fail based on a threshold
    public Map<Boolean, List<Student>> partitionByPassFail(int passingGrade) {
        return studentRepository.getAll().stream()
                .collect(Collectors.partitioningBy(
                        s -> getAverageGradeForStudent(s.getId()) >= passingGrade
                ));
    }

    // Group students into letter-grade brackets based on average grade
    public Map<String, List<Student>> groupByGradeBracket() {
        return studentRepository.getAll().stream()
                .collect(Collectors.groupingBy(s -> {
                    double avg = getAverageGradeForStudent(s.getId());
                    if (avg >= 90) return "A";
                    else if (avg >= 80) return "B";
                    else if (avg >= 70) return "C";
                    else if (avg >= 60) return "D";
                    else return "F";
                }));
    }

    // Students whose attendance percentage is below a threshold
    public List<Student> findStudentsBelowAttendance(double minPercentage) {
        return studentRepository.getAll().stream()
                .filter(s -> {
                    if (s.getTotalDays() == 0) return false; // avoid divide-by-zero
                    double attendancePct = (s.getDaysPresent() * 100.0) / s.getTotalDays();
                    return attendancePct < minPercentage;
                })
                .collect(Collectors.toList());
    }

    // Average grade for a course, across every student enrolled in it
    public double getAverageGradeForCourse(int courseId) {
        List<Enrollment> enrollments = enrollmentRepository.findByCourseId(courseId);
        return enrollments.stream()
                .mapToDouble(Enrollment::getAverageGrade)
                .average()
                .orElse(0.0);
    }
}