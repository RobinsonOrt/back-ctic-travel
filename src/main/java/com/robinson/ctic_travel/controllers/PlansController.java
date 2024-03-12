package com.robinson.ctic_travel.controllers;

import com.robinson.ctic_travel.daos.plansDao.PlansDao;
import com.robinson.ctic_travel.models.Plans;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/plans")
public class PlansController {
    @Autowired
    private PlansDao plansDao;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> createPlan(@Valid @RequestBody Plans plan){
        JSONObject response = new JSONObject().appendField("error", true);
        try {
            Plans newPlan = new Plans(plan.getPlanName(), plan.getPlanPrice(), plan.getPlanDuration(), plan.getPlanMaxPeople(), plan.getPlanTransportationType());
            plansDao.createPlan(newPlan);

            response.put("error", false);
            response.put("response", "Se ha registrado el plan correctamente");
            return ResponseEntity.ok().body(response);
        } catch (InvalidDataAccessApiUsageException e){
            response.put("response", "El tipo de transporte es inválido");
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e){
            response.put("response", "Ha ocurrido un error al registrar el plan");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{planId}")
    public ResponseEntity<?> updatePlan(@PathVariable String planId, @Valid @RequestBody Plans plan){
        JSONObject response = new JSONObject().appendField("error", true);
        try {
            Plans newPlan = new Plans(planId, plan.getPlanName(), plan.getPlanPrice(), plan.getPlanDuration(), plan.getPlanMaxPeople());
            plansDao.updatePlan(newPlan);

            response.put("error", false);
            response.put("response", "Se ha actualizado el plan correctamente");
            return ResponseEntity.ok().body(response);
        } catch (NullPointerException e) {
            response.put("response", "El plan seleccionado no existe");
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e){
            response.put("response", "Ha ocurrido un error al actualizar el plan");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{planId}")
    public ResponseEntity<?> deletePlan(@PathVariable String planId){
        JSONObject response = new JSONObject().appendField("error", true);
        try {
            plansDao.deletePlan(planId);

            response.put("error", false);
            response.put("response", "Se ha eliminado el plan correctamente");
            return ResponseEntity.ok().body(response);
        } catch (InvalidDataAccessApiUsageException e) {
            response.put("response", "El plan seleccionado no existe");
            return ResponseEntity.badRequest().body(response);
        } catch (DataIntegrityViolationException e) {
            response.put("response", "El plan seleccionado no puede ser eliminado porque está asociado a un destino");
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e){
            System.out.println(e.getClass());
            response.put("response", "Ha ocurrido un error al eliminar el plan");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get-all/{page}")
    public ResponseEntity<?> getAllPlans(@PathVariable int page){
        JSONObject response = new JSONObject().appendField("error", true);
        try {
            response.put("response", plansDao.getAllPlans(page));
            response.put("maxPage", Math.floorDiv(plansDao.getPlansCount() - 1, 6));
            response.put("error", false);
            return ResponseEntity.ok().body(response);
        } catch (Exception e){
            response.put("response", "Ha ocurrido un error al obtener los planes");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/get-by-tag/{planTag}")
    public ResponseEntity<?> getPlanByTag(@PathVariable String planTag){
        JSONObject response = new JSONObject().appendField("error", true);
        try {
            response.put("response", plansDao.getPlanByTag(planTag));
            response.put("error", false);
            return ResponseEntity.ok().body(response);
        } catch (EmptyResultDataAccessException e) {
            response.put("response", "El plan seleccionado no existe");
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e){
            response.put("response", "Ha ocurrido un error al obtener el plan");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get-to-destination/{destinationTag}")
    public ResponseEntity<?> getPlansToDestination(@PathVariable String destinationTag){
        JSONObject response = new JSONObject().appendField("error", true);
        try {
            response.put("response", plansDao.getPlansToDestination(destinationTag));
            response.put("error", false);
            return ResponseEntity.ok().body(response);
        } catch (Exception e){
            response.put("response", "Ha ocurrido un error al obtener los planes");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/get-by-destination/{destinationTag}")
    public ResponseEntity<?> getPlansByDestination(@PathVariable String destinationTag){
        JSONObject response = new JSONObject().appendField("error", true);
        try {
            response.put("response", plansDao.getPlansByDestination(destinationTag));
            response.put("error", false);
            return ResponseEntity.ok().body(response);
        } catch (EmptyResultDataAccessException e) {
            response.put("response", "No hay planes asignados en el destino seleccionado");
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e){
            response.put("response", "Ha ocurrido un error al obtener los planes");
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
