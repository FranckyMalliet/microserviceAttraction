package microserviceAttraction.models;

import java.util.Objects;
import java.util.UUID;

public class Attraction{
    private String attractionName;
    private String city;
    private String state;
    private UUID attractionId;
    private double latitude;
    private double longitude;

    public Attraction(String attractionName, String city, String state, double latitude, double longitude) {
        Objects.requireNonNull(attractionName);
        Objects.requireNonNull(city);
        Objects.requireNonNull(state);
        Objects.requireNonNull(latitude);
        Objects.requireNonNull(longitude);

        this.attractionName = attractionName;
        this.city = city;
        this.state = state;
        this.attractionId = UUID.randomUUID();
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getAttractionName() {
        return attractionName;
    }

    public void setAttractionName(String attractionName) {
        this.attractionName = attractionName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public UUID getAttractionId() {
        return attractionId;
    }

    public void setAttractionId(UUID attractionId) {
        this.attractionId = attractionId;
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
