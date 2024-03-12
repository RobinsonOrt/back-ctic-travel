package com.robinson.ctic_travel.controllers;

import com.robinson.ctic_travel.daos.destinationsDao.DestinationsDao;
import com.robinson.ctic_travel.daos.plansDestionationsDao.PlansDestinationsDao;
import com.robinson.ctic_travel.models.Destinations;
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
@RequestMapping("/destinations")
public class DestinationsController {
    @Autowired
    private DestinationsDao destinationsDao;

    @Autowired
    private PlansDestinationsDao plansDestinationsDao;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> createDestination(@Valid @RequestBody Destinations destination){
        JSONObject response = new JSONObject().appendField("error", true);
        try {
            Destinations newDestination = new Destinations(destination.getDestinationLocation(), destination.getDestinationAttractions());
            destinationsDao.createDestination(newDestination);

            response.put("error", false);
            response.put("response", "Se ha registrado el destino correctamente");
            return ResponseEntity.ok().body(response);
        } catch (DataIntegrityViolationException e){
            response.put("response", "El nombre del destino ya está en uso");
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e){
            response.put("response", "Ha ocurrido un error al registrar el destino");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{destinationId}")
    public ResponseEntity<?> updateDestination(@PathVariable String destinationId, @Valid @RequestBody Destinations destination){
        JSONObject response = new JSONObject().appendField("error", true);
        try {
            Destinations newDestination = new Destinations(destinationId, destination.getDestinationLocation(), destination.getDestinationAttractions());
            destinationsDao.updateDestination(newDestination);

            response.put("error", false);
            response.put("response", "Se ha actualizado el destino correctamente");
            return ResponseEntity.ok().body(response);
        } catch (NullPointerException e) {
            response.put("response", "El destino seleccionado no existe");
            return ResponseEntity.badRequest().body(response);
        } catch (DataIntegrityViolationException e){
            response.put("response", "El nombre del destino ya está en uso");
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e){
            response.put("response", "Ha ocurrido un error al actualizar el destino");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{destinationId}")
    public ResponseEntity<?> deleteDestination(@PathVariable String destinationId){
        JSONObject response = new JSONObject().appendField("error", true);
        try {
            destinationsDao.deleteDestination(destinationId);

            response.put("error", false);
            response.put("response", "Se ha eliminado el destino correctamente");
            return ResponseEntity.ok().body(response);
        } catch (InvalidDataAccessApiUsageException e) {
            response.put("response", "El destino seleccionado no existe");
            return ResponseEntity.badRequest().body(response);
        } catch (DataIntegrityViolationException e) {
            response.put("response", "El destino no se puede eliminar porque tiene planes asociados");
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e){
            response.put("response", "Ha ocurrido un error al eliminar el destino");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/get-all/{page}")
    public ResponseEntity<?> getAllDestinations(@PathVariable int page){
        JSONObject response = new JSONObject().appendField("error", true);
        try {
            response.put("response", destinationsDao.getAllDestinations(page));
            response.put("error", false);
            response.put("maxPage", Math.floorDiv(destinationsDao.getDestinationsCount() - 1, 6));
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            response.put("response", "Ha ocurrido un error al obtener los destinos");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get-destinations")
    public ResponseEntity<?> getDestinations(){
        JSONObject response = new JSONObject().appendField("error", true);
        try {
            response.put("response", destinationsDao.getDestinations());
            response.put("error", false);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            response.put("response", "Ha ocurrido un error al obtener los destinos");
            return ResponseEntity.internalServerError().body(response);
        }
    }


    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/search/{search}")
    public ResponseEntity<?> searchDestinations(@PathVariable String search){
        JSONObject response = new JSONObject().appendField("error", true);
        try {
            response.put("response", destinationsDao.searchDestinations(search));
            response.put("error", false);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            response.put("response", "Ha ocurrido un error al buscar los destinos");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/get-by-tag/{destinationTag}")
    public ResponseEntity<?> getDestinationsByTag(@PathVariable String destinationTag){
        JSONObject response = new JSONObject().appendField("error", true);
        try {
            response.put("response", destinationsDao.getDestinationsByTag(destinationTag));
            response.put("error", false);
            return ResponseEntity.ok().body(response);
        } catch (EmptyResultDataAccessException e) {
            response.put("response", "El destino seleccionado no existe");
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("response", "Ha ocurrido un error al obtener el destino");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/add-plan/{destinationTag}/{planTag}")
    public ResponseEntity<?> addPlanToDestination(@PathVariable String destinationTag, @PathVariable String planTag){
        JSONObject response = new JSONObject().appendField("error", true);
        try {
            plansDestinationsDao.addPlanToDestination(destinationTag, planTag);

            response.put("error", false);
            response.put("response", "Se ha añadido el plan al destino correctamente");
            return ResponseEntity.ok().body(response);
        } catch (EmptyResultDataAccessException e) {
            response.put("response", "El destino o el plan seleccionado no existe");
            return ResponseEntity.badRequest().body(response);
        } catch (DataIntegrityViolationException e) {
            response.put("response", "El plan ya está añadido al destino");
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("response", "Ha ocurrido un error al añadir el plan al destino");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/remove-plan/{destinationTag}/{planTag}")
    public ResponseEntity<?> removePlanFromDestination(@PathVariable String destinationTag, @PathVariable String planTag){
        JSONObject response = new JSONObject().appendField("error", true);
        try {
            plansDestinationsDao.removePlanFromDestination(destinationTag, planTag);

            response.put("error", false);
            response.put("response", "Se ha eliminado el plan del destino correctamente");
            return ResponseEntity.ok().body(response);
        } catch (EmptyResultDataAccessException e) {
            response.put("response", "El plan seleccionado no se encuentra asignado al destino");
            return ResponseEntity.badRequest().body(response);
        } catch (DataIntegrityViolationException e) {
            response.put("response", "El plan seleccionado no puede ser eliminado porque tiene usuarios inscritos");
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            response.put("response", "Ha ocurrido un error al eliminar el plan del destino");
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
