public class Student {
    String name;
    String ID;
    public Student(String name, String ID) {
        this.name = name;
        this.ID = ID;
    }
    public String getName() {
        return name;
    }
    public String getID() {
        return ID;
    }
    
        @Override
        public String toString() {
            return "Student Name: " + name + ", ID: " + ID;
        }
    }
