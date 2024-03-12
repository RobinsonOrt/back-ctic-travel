package com.robinson.ctic_travel.controllers;

import com.robinson.ctic_travel.daos.plansDestinationsUsersDao.PlansDestinationsUsersDao;
import com.robinson.ctic_travel.daos.usersDao.UsersDao;
import com.robinson.ctic_travel.models.PlansDestinationsUsers;
import com.robinson.ctic_travel.models.Users;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UsersDao usersDao;

    @Autowired
    private PlansDestinationsUsersDao plansDestinationsUsersDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/ping")
    public ResponseEntity<?> ping(){
        return ResponseEntity.ok().body("Pong");
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Users user){
        JSONObject response = new JSONObject().appendField("error", true);
        try {
            Users newUser = new Users(user.getUserName(), user.getUserLastName(), user.getUserEmail(), passwordEncoder.encode(user.getUserPassword()));
            usersDao.registerUser(newUser);

            response.put("error", false);
            response.put("response", "Se ha registrado el usuario correctamente");
            return ResponseEntity.ok().body(response);
        } catch (DataIntegrityViolationException e){
            response.put("response", "El usuario ya está registrado");
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e){
            response.put("response", "Ha ocurrido un error al registrar el usuario");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/set-to-destination-plan/{userId}/{destinationPlanId}")
    public ResponseEntity<?> setToDestinationPlan(@PathVariable String userId, @PathVariable String destinationPlanId){
        JSONObject response = new JSONObject().appendField("error", true);
        try {
            PlansDestinationsUsers plansDestinationsUsers = new PlansDestinationsUsers(userId, destinationPlanId);
            plansDestinationsUsersDao.setToDestinationPlan(plansDestinationsUsers);

            response.put("error", false);
            response.put("response", "Se ha registrado en el plan correctamente");
            return ResponseEntity.ok().body(response);
        } catch (InvalidDataAccessApiUsageException e) {
            response.put("response", "No se ha encontrado el usuario o el plan seleccionado");
            return ResponseEntity.badRequest().body(response);
        } catch (DataIntegrityViolationException e){
            response.put("response", "El usuario ya se encuentra registrado en el plan seleccionado");
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e){
            response.put("response", "Ha ocurrido un error al registrar el usuario en el plan");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/remove-from-destination-plan/{userId}/{destinationPlanId}")
    public ResponseEntity<?> removeFromDestinationPlan(@PathVariable String userId, @PathVariable String destinationPlanId){
        JSONObject response = new JSONObject().appendField("error", true);
        try {
            plansDestinationsUsersDao.removeFromDestinationPlan(userId, destinationPlanId);

            response.put("error", false);
            response.put("response", "Se ha eliminado el registro del plan correctamente");
            return ResponseEntity.ok().body(response);
        } catch (EmptyResultDataAccessException e) {
            response.put("response", "El usuario no se encuentra registrado en el plan seleccionado");
            return ResponseEntity.badRequest().body(response);
        } catch (InvalidDataAccessApiUsageException e) {
            response.put("response", "No se ha encontrado el usuario o el plan seleccionado");
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e){
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            response.put("response", "Ha ocurrido un error al eliminar el registro del plan");
            return ResponseEntity.internalServerError().body(response);
        }
    }

}
