package com.example.demo.model;

public class Sight {
    private String SightName;
    private String Zone;
    private String Category;
    private String PhotoURL;
    private String Description;
    private String Address;

    public Sight(){}

    public static Sight of(String sightName, String zone, String category, String photoURL, String description, String address) {
        Sight sight = new Sight();
        sight.setSightName(sightName);
        sight.setZone(zone);
        sight.setCategory(category);
        sight.setPhotoURL(photoURL);
        sight.setDescription(description);
        sight.setAddress(address);
        return sight;
    }

    @Override
    public String toString() {
        return "SightName: " + SightName + '\n' +
                "Zone: " + Zone + '\n' +
                "Category: " + Category + '\n' +
                "PhotoURL: " + PhotoURL + '\n' +
                "Description: " + Description + '\n' +
                "Address: " + Address + '\n';
    }

    public String getSightName() {return SightName;}
    public void setSightName(String sightName) {
        SightName = sightName;
    }
    public String getZone() {
        return Zone;
    }
    public void setZone(String zone) {
        Zone = zone;
    }
    public String getCategory() {
        return Category;
    }
    public void setCategory(String category) {
        Category = category;
    }
    public String getPhotoURL() {
        return PhotoURL;
    }
    public void setPhotoURL(String photoURL) {
        PhotoURL = photoURL;
    }
    public String getDescription() {
        return Description;
    }
    public void setDescription(String description) {
        Description = description;
    }
    public String getAddress() {
        return Address;
    }
    public void setAddress(String address) {
        Address = address;
    }
}
