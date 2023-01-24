package com.lab.server.payload;

public class JwtResponse {

    private String jwt;
    private String type = "Bearer";
    private Long id;
    private String username;


    public JwtResponse(String jwt, String username) {
        this.username = username;
        this.jwt = jwt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
