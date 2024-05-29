package org.example.springbootsecurity.service;

import org.example.springbootsecurity.model.Role;
import org.example.springbootsecurity.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleService {
    @Autowired
    private RoleRepository repository;

    public Role findById(long id) {
        return repository.findById(id).get();
    }

    public void saveRole(Role role) {
        repository.save(role);
    }

    public void deleteRole(Role role) {
        repository.delete(role);
    }
}
