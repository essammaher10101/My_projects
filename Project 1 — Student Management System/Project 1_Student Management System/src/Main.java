import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // Repositories
        StudentRepository studentRepository = new InMemoryStudentRepository();
        CourseRepository courseRepository = new InMemoryCourseRepository();
        EnrollmentRepository enrollmentRepository = new InMemoryEnrollmentRepository();

        StudentService studentService = new StudentService(studentRepository, courseRepository, enrollmentRepository);

        // Students
        Student s1 = new Student(1, "Ahmed", 90, 100);
        Student s2 = new Student(2, "Sara", 60, 100);
        Student s3 = new Student(3, "Youssef", 95, 100);

        studentRepository.add(s1);
        studentRepository.add(s2);
        studentRepository.add(s3);

        // Courses
        Course math = new Course(101, "Math");
        Course physics = new Course(102, "Physics");

        courseRepository.add(math);
        courseRepository.add(physics);

        // Enrollments
        Enrollment e1 = new Enrollment(s1.getId(), math.getId());
        e1.addGrade(90);
        e1.addGrade(95);

        Enrollment e2 = new Enrollment(s1.getId(), physics.getId());
        e2.addGrade(70);

        Enrollment e3 = new Enrollment(s2.getId(), math.getId());
        e3.addGrade(50);
        e3.addGrade(55);

        Enrollment e4 = new Enrollment(s3.getId(), physics.getId());
        e4.addGrade(85);
        e4.addGrade(90);

        enrollmentRepository.add(e1);
        enrollmentRepository.add(e2);
        enrollmentRepository.add(e3);
        enrollmentRepository.add(e4);

        // --- Demonstrate the service methods ---

        System.out.println("--- Average grade per student ---");
        for (Student s : studentRepository.getAll()) {
            double avg = studentService.getAverageGradeForStudent(s.getId());
            System.out.println(s.getName() + ": " + avg);
        }

        System.out.println("\n--- Top 2 students ---");
        List<Student> top2 = studentService.getTopNStudents(2);
        top2.forEach(s -> System.out.println(s.getName()));

        System.out.println("\n--- Pass/Fail (passing = 60) ---");
        Map<Boolean, List<Student>> passFail = studentService.partitionByPassFail(60);
        System.out.println("Passed: " + passFail.get(true).stream().map(Student::getName).toList());
        System.out.println("Failed: " + passFail.get(false).stream().map(Student::getName).toList());

        System.out.println("\n--- Grade brackets ---");
        Map<String, List<Student>> brackets = studentService.groupByGradeBracket();
        brackets.forEach((bracket, students) ->
                System.out.println(bracket + ": " + students.stream().map(Student::getName).toList()));

        System.out.println("\n--- Students below 70% attendance ---");
        List<Student> lowAttendance = studentService.findStudentsBelowAttendance(70);
        lowAttendance.forEach(s -> System.out.println(s.getName()));

        System.out.println("\n--- Average grade per course ---");
        System.out.println("Math: " + studentService.getAverageGradeForCourse(math.getId()));
        System.out.println("Physics: " + studentService.getAverageGradeForCourse(physics.getId()));
    }
}