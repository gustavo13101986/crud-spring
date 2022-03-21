package com.examplecrud.crudspring.controllers;

import com.examplecrud.crudspring.controllers.dto.CreateUserInputDTO;
import com.examplecrud.crudspring.controllers.dto.UserOutputDTO;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user") // recurso
public class UserController {

    private List<UserOutputDTO> users = new ArrayList<>(); // prueba

    @PostMapping
    public UserOutputDTO createUser(@RequestBody CreateUserInputDTO input){

        if(this.users.size() > 0){
            for (UserOutputDTO user: this.users){
                if(user.getEmail().equals(input.getEmail())){
                    String responseIfUserAlreadyExist = "El correo: "+input.getEmail()+" ya esta registrado";
                    UserOutputDTO userAlreadyExist = new UserOutputDTO(responseIfUserAlreadyExist, responseIfUserAlreadyExist, responseIfUserAlreadyExist);
                    userAlreadyExist.setName(responseIfUserAlreadyExist);
                    return userAlreadyExist;
                }
            }
        }
        String id = UUID.randomUUID().toString();
        UserOutputDTO userToAdd = new UserOutputDTO(id, input.getName(), input.getEmail());
        this.users.add(userToAdd);
        return userToAdd;

    }

    @GetMapping
    public List<UserOutputDTO> getUsers(){

        return this.users;

        // Dominar bien las listas: ArrayList.
        // investigar stateless
        // queryParamts, buscar por nombre y correo
    }

    @GetMapping("/{id}")
    public UserOutputDTO getUserById(@PathVariable("id") String id){
        for (UserOutputDTO user: this.users){
            if(user.getId().equals(id)){
                return user;
            }
        }
        String response = "El usuario con id: "+ id + " no fue encontrado";
        UserOutputDTO userNotFound = new UserOutputDTO(response, response, response);
        return userNotFound;
    }
}

