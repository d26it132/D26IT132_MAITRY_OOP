import java.io.*;

class Student implements Serializable {
    int id;
    String name;
    transient String password;  

    Student(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    void display() {
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Password: " + password);
        System.out.println();
    }
}

public class SaveLoad {
    public static void main(String[] args) {

        Student[] students = {
            new Student(1, "Maitry", "abc123"),
            new Student(2, "Rahul", "xyz456"),
            new Student(3, "Priya", "pqr789")
        };

        try {
            ObjectOutputStream out =
                new ObjectOutputStream(new FileOutputStream("students.dat"));

            out.writeObject(students);
            out.close();

            System.out.println("Objects saved successfully.\n");

        } catch (IOException e) {
            System.out.println("Error while saving: " + e);
        }

        try {
            ObjectInputStream in =
                new ObjectInputStream(new FileInputStream("students.dat"));

            Student[] loadedStudents = (Student[]) in.readObject();
            in.close();

            System.out.println("Objects loaded successfully.\n");

            for (Student s : loadedStudents) {
                s.display();
            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error while loading: " + e);
        }
    }
}