public class TeacherAssistant {
    private String name;
    private String ID;
    
    public TeacherAssistant(String name, String ID) {
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
            return "Doctor Name: " + name + ", ID: " + ID;
        }
    }
