package entity;
import org.bson.types.ObjectId;
import resource.RentableResource;

public class ResourceEntity {
    private ObjectId id;
    private String name;
    private int deposit;
    private boolean inStock;
    private String type;

    private int rentalPeriod;

    public int getRentalPeriod() { return rentalPeriod; }
    public void setRentalPeriod(int rentalPeriod) { this.rentalPeriod = rentalPeriod; }


    public ResourceEntity() {}

    public ResourceEntity(String name, int deposit) {
        this.name = name;
        this.deposit = deposit;
        this.inStock = true;
    }

    // 필드 기반 getter/setter
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getDeposit() { return deposit; }
    public void setDeposit(int deposit) { this.deposit = deposit; }

    public boolean isInStock() { return inStock; }
    public void setInStock(boolean inStock) { this.inStock = inStock; }


}
