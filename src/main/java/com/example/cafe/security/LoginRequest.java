package com.example.cafe.security;

public class LoginRequest {
    private String username;
    private String password;

    // Конструктор без параметров для сериализации и десериализации
    public LoginRequest() {}

    // Конструктор с параметрами для удобства
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Геттеры и сеттеры
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
