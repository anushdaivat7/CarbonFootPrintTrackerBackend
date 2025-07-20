package com.example.carbontracker;

public class UserPublicDTO {
    public String id;
    public String fullName;
    public String email;

    public UserPublicDTO(String id, String fullName, String email) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
    }
}
