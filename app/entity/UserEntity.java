package entity;
import org.bson.types.ObjectId;

//사용 X
public class UserEntity {

    private ObjectId id;     // MongoDB 기본 ObjectId

    private String studentId;
    private String name;

    public UserEntity() {}

    public UserEntity(String studentId, String name) {
        this.studentId = studentId;
        this.name = name;
    }

    public String getStudentId() { return studentId; }
    public String getName() { return name; }

    public void setStudentId(String studentId) { this.studentId = studentId; }
    public void setName(String name) { this.name = name; }
}