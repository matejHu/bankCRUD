package org.example;

import org.example.db.HikariCPDataSource;
import org.example.service.Manager;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        Manager manager = new Manager();
        manager.start();
    }
}