package com.examplecrud.crudspring.controllers;

import com.examplecrud.crudspring.controllers.dto.CreateUserInputDTO;
import com.examplecrud.crudspring.controllers.dto.UserOutputDTO;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/user") // recurso
public class UserController {

    private List<UserOutputDTO> users = new ArrayList<>(); // prueba

    @PostMapping
    public UserOutputDTO createUser(@RequestBody CreateUserInputDTO input){
        // Agregar campos user y password para el registro.
        // validar que el nombre, el usuario, el correo y el password son OBLIGATORIOS, no esten sin informacion  (isEmpty())
        if(input.getName().isEmpty() || input.getUser().isEmpty() || input.getEmail().isEmpty() || input.getPassword().isEmpty()){
            String response = "Los campos son obligatorios";
            UserOutputDTO responseIfFildIsEmpty = new UserOutputDTO(response, response, response, response, response);
            return responseIfFildIsEmpty;
        }

        // validar que el email del nuevo usuario no haya sido creado. (size())
        if(this.users.size() > 0){
            for (UserOutputDTO user: this.users){
                if(user.getEmail().equals(input.getEmail())){
                    String responseIfUserAlreadyExist = "El correo: "+input.getEmail()+" ya esta registrado";
                    UserOutputDTO userAlreadyExist = new UserOutputDTO(responseIfUserAlreadyExist, responseIfUserAlreadyExist, responseIfUserAlreadyExist, responseIfUserAlreadyExist, responseIfUserAlreadyExist);
                    return userAlreadyExist;
                }
            }
        }

        // 

        String id = UUID.randomUUID().toString();
        UserOutputDTO userToAdd = new UserOutputDTO(id, input.getName(), input.getUser(), input.getEmail(), input.getPassword());
        this.users.add(userToAdd);
        return userToAdd;

    }

   /* @GetMapping
    public List<UserOutputDTO> getUsers(){

        return this.users;

        // Dominar bien las listas: ArrayList.
        // investigar stateless
        // queryParamts, buscar por nombre y correo
    }*/
    /* Declaración de un Map (un HashMap) con clave "Integer" y Valor "String". Las claves pueden ser de cualquier tipo de objetos, aunque los más utilizados como clave son los objetos predefinidos de Java como String, Integer, Double ... !!!!CUIDADO los Map no permiten datos atómicos
    Map<Integer, String> nombreMap = new HashMap<Integer, String>();
    nombreMap.size(); // Devuelve el numero de elementos del Map
    nombreMap.isEmpty(); // Devuelve true si no hay elementos en el Map y false si si los hay
    nombreMap.put(K clave, V valor); // Añade un elemento al Map
    nombreMap.get(K clave); // Devuelve el valor de la clave que se le pasa como parámetro o 'null' si la clave no existe
    nombreMap.clear(); // Borra todos los componentes del Map
    nombreMap.remove(K clave); // Borra el par clave/valor de la clave que se le pasa como parámetro
    nombreMap.containsKey(K clave); // Devuelve true si en el map hay una clave que coincide con K
    nombreMap.containsValue(V valor); // Devuelve true si en el map hay un Valor que coincide con V
    nombreMap.values(); // Devuelve una "Collection" con los valores del Map */


    @GetMapping
    public List<UserOutputDTO> getUsers(@RequestParam Map<String, String> queryFields) {
        // UserOutputDTO output =new UserOutputDTO(queryFields.entrySet());
        System.out.println(queryFields.entrySet());
        System.out.println(queryFields.values());
        System.out.println(queryFields.containsKey("id"));
        System.out.println(queryFields.get("id"));
        // [id=27817b24-3c3d-4b36-b9ef-4daf7bb29676, name=Andres]

        if (!queryFields.containsKey("id") || !queryFields.containsKey("email")) {
            String response = "Los valores de claves no son correctos";
            UserOutputDTO userNotFound = new UserOutputDTO(response, response, response, response, response);
            List<UserOutputDTO> pathNotFound = new ArrayList<>();
            pathNotFound.add(userNotFound);
            return pathNotFound;
        }

             for (UserOutputDTO user : this.users) {
                if (user.getId().equals(queryFields.get("id"))) {
                    return this.users;
                }
            }
        return this.users;
    }

    @GetMapping("/{id}")
    public UserOutputDTO getUserById(@PathVariable("id") String id){
        for (UserOutputDTO user: this.users){
            if(user.getId().equals(id)){
                return user;
            }
        }
        String response = "El usuario con id: "+ id + " no fue encontrado";
        UserOutputDTO userNotFound = new UserOutputDTO(response, response, response, response, response);
        return userNotFound;
    }


    @PutMapping("/{id}")
    public UserOutputDTO updateUser(@PathVariable("id") String id, @RequestBody CreateUserInputDTO input){
        int position = 0;
        for (UserOutputDTO user: this.users){
            if(user.getId().equals(id)){

                UserOutputDTO userToUpdate = new UserOutputDTO(id, input.getName(), input.getUser(), input.getEmail(), input.getPassword());
                this.users.set(position, userToUpdate);
                return userToUpdate;
            }
            position += 1;
        }

        String response = "El usuario con id: "+ id + " no fue encontrado";
        UserOutputDTO userNotFound = new UserOutputDTO(response, response, response, response, response);
        return userNotFound;
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") String id){
        int position = 0;
        for (UserOutputDTO user: this.users){
            if(user.getId().equals(id)){

                this.users.remove(position);
                return "Usuario eliminado";
            }
            position += 1;
        }

        String response = "El usuario con id: "+ id + " no fue encontrado";
        return response;
    }
}

