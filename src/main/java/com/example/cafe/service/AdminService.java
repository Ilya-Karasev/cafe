package com.example.cafe.service;

import com.example.cafe.model.Admin;
import com.example.cafe.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    public Admin getAdminById(Long id) {
        return adminRepository.findById(id).orElse(null);
    }

    public Admin createAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    public Admin editAdmin(Long id, Admin new_admin) {
        Optional<Admin> old = adminRepository.findById(id);
        Admin old_admin = old.get();
        old_admin.setAction(new_admin.getAction());
        old_admin.setTimeStamp(new_admin.getTimeStamp());
        old_admin.setUser(new_admin.getUser());
        return adminRepository.save(old_admin);
    }

    public void deleteAdmin(Long id) {
        adminRepository.deleteById(id);
    }
}
