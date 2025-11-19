package entity;

public class LectureEntity {

    private String name;
    private int deposit;
    private boolean available;

    public LectureEntity() {
        // POJO Codec이 반드시 필요함
    }

    public LectureEntity(String name, int deposit, boolean available) {
        this.name = name;
        this.deposit = deposit;
        this.available = available;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getDeposit() { return deposit; }
    public void setDeposit(int deposit) { this.deposit = deposit; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
}
