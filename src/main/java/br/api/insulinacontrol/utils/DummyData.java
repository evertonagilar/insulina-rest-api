package br.api.insulinacontrol.utils;

import br.api.insulinacontrol.enums.RoleName;
import br.api.insulinacontrol.models.RoleModel;
import br.api.insulinacontrol.models.UserModel;
import br.api.insulinacontrol.repositories.RoleRepository;
import br.api.insulinacontrol.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.UUID;

@Transactional
@Component
public class DummyData {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    //@PostConstruct
    public void init() throws Exception {
        cadastraRoles();
        cadastraUsers();
        cadastraUserRoles();
    }


    public void cadastraUsers() {
        var listUsers = new ArrayList<UserModel>();

        var joao = new UserModel();
        joao.setUserId(UUID.randomUUID());
        joao.setUsername("joao");
        joao.setPassword(passwordEncoder.encode("joao123"));
        listUsers.add(joao);

        var maria = new UserModel();
        maria.setUserId(UUID.randomUUID());
        maria.setUsername("maria");
        maria.setPassword(passwordEncoder.encode("maria123"));
        listUsers.add(maria);

        var admin = new UserModel();
        admin.setUserId(UUID.randomUUID());
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin"));
        listUsers.add(admin);

        userRepository.saveAll(listUsers);
    }

    public void cadastraRoles() {
        var adminRole = new RoleModel();
        adminRole.setRoleId(UUID.randomUUID());
        adminRole.setRoleName(RoleName.ROLE_ADMIN);
        roleRepository.save(adminRole);

        var userRole = new RoleModel();
        userRole.setRoleId(UUID.randomUUID());
        userRole.setRoleName(RoleName.ROLE_USER);
        roleRepository.save(userRole);
    }

    public void cadastraUserRoles() throws Exception {
        RoleModel adminRole = roleRepository.findByRoleName(RoleName.ROLE_ADMIN).orElseThrow(() -> new Exception("Role admin não encontrada"));
        RoleModel userRole = roleRepository.findByRoleName(RoleName.ROLE_USER).orElseThrow(() -> new Exception("Role user não encontrada"));

        UserModel joao = userRepository.findByUsername("joao").orElseThrow(() -> new Exception("Joao não cadastrado"));
        joao.getRoles().add(userRole);
        userRepository.save(joao);

        UserModel maria = userRepository.findByUsername("joao").orElseThrow(() -> new Exception("maria não cadastrado"));
        maria.getRoles().add(userRole);
        userRepository.save(maria);

        UserModel admin = userRepository.findByUsername("joao").orElseThrow(() -> new Exception("Admin não cadastrado"));
        admin.getRoles().add(adminRole);
        userRepository.save(admin);
    }

}
