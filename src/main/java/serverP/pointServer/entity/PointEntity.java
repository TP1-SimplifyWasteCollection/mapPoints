package serverP.pointServer.entity;

import jakarta.persistence.*;
import java.util.List;

// Сущность, представляющая точку сбора
@Entity
@Table(name = "garbage_points")
public class PointEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    
    private String address;
    
    private String phone;

    @Column(name = "opening_hour")
    private String openingHour;

    @Column(name = "closing_hour")
    private String closingHour;

    @Column(name = "recycling_items")
    @ElementCollection
    private List<String> recyclingItems;

    private double latitude;

    private double longitude;

    // Конструкторы
    public PointEntity() {}

    public PointEntity(String name, String address, String phone, String openingHour, String closingHour, List<String> recyclingItems, double latitude, double longitude) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.openingHour = openingHour;
        this.closingHour = closingHour;
        this.recyclingItems = recyclingItems;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOpeningHour() {
        return openingHour;
    }

    public void setOpeningHour(String openingHour) {
        this.openingHour = openingHour;
    }

    public String getClosingHour() {
        return closingHour;
    }

    public void setClosingHour(String closingHour) {
        this.closingHour = closingHour;
    }

    public List<String> getRecyclingItems() {
        return recyclingItems;
    }

    public void setRecyclingItems(List<String> recyclingItems) {
        this.recyclingItems = recyclingItems;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
