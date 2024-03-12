package com.robinson.ctic_travel.controllers;

import com.robinson.ctic_travel.daos.plansDestionationsDao.PlansDestinationsDao;
import com.robinson.ctic_travel.models.PlansDestinations;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/plans-destinations")
public class PlansDestinationsController {
    @Autowired
    private PlansDestinationsDao plansDestinationsDao;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/get-to-user/{userId}/{destinationTag}")
    public ResponseEntity<?> getToUser(@PathVariable String userId, @PathVariable String destinationTag){
        JSONObject response = new JSONObject().appendField("error", true);
        try {
            List<PlansDestinations> plansDestinations = plansDestinationsDao.getToUser(userId, destinationTag);
            response.put("error", false);
            response.put("response", plansDestinations);
            return ResponseEntity.ok().body(response);
        } catch (Exception e){
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            response.put("response", "Ha ocurrido un error al obtener los planes para el usuario");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/get-by-user/{userId}")
    public ResponseEntity<?> getByUser(@PathVariable String userId){
        JSONObject response = new JSONObject().appendField("error", true);
        try {
            List<PlansDestinations> plansDestinations = plansDestinationsDao.getByUser(userId);
            response.put("error", false);
            response.put("response", plansDestinations);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            response.put("response", "Ha ocurrido un error al obtener los planes del usuario");
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
