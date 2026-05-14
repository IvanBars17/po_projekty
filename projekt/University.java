public class University {
    private String nameOfUniversity;
    private int numberOfStudents;

    public University(String nameOfUniversity, int numberOfStudents) {
        this.nameOfUniversity = nameOfUniversity;
        this.numberOfStudents = numberOfStudents;
    }

    public String getNameOfUniversity() {
        return nameOfUniversity;
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }
}
