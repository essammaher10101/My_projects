import java.util.Objects;
public class Student {
    private final int id;
    private final String name;
    private int daysPresent;
    private int totalDays;


    public Student(int id, String name, int daysPresent, int totalDays){
        this.id = id;
        this.name = name;
        this.daysPresent = daysPresent;
        this.totalDays = totalDays;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public int getDaysPresent() {
        return daysPresent;
    }
    public void setDaysPresent(int daysPresent) {
        this.daysPresent = daysPresent;
    }


    public int getTotalDays() {
        return totalDays;
    }
    public void setTotalDays(int totalDays) {
        this.totalDays = totalDays;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dayPresent=" + daysPresent +
                ", totalDays=" + totalDays +
                '}';
    }
}
