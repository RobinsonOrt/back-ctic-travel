package com.robinson.ctic_travel.controllers;

import com.robinson.ctic_travel.daos.lodgingsDao.LodgingsDao;
import com.robinson.ctic_travel.models.Lodgings;
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
@RequestMapping("/lodgings")
public class LodgingsController {
    @Autowired
    private LodgingsDao lodgingsDao;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create/{destinationId}")
    public ResponseEntity<?> createLodging(@PathVariable String destinationId, @Valid @RequestBody Lodgings lodging){
        JSONObject response = new JSONObject().appendField("error", true);
        try {
            Lodgings newLodging = new Lodgings(lodging.getLodgingName(), lodging.getLodgingRooms(), lodging.getLodgingCheckIn(), lodging.getLodgingCheckOut(), lodging.getLodgingType(), destinationId);
            lodgingsDao.createLodging(newLodging);

            response.put("error", false);
            response.put("response", "Se ha registrado el hospedaje correctamente");
            return ResponseEntity.ok().body(response);
        } catch (InvalidDataAccessApiUsageException e){
            response.put("response", "El tipo de hospedaje o el destino seleccionado es inválido");
            return ResponseEntity.badRequest().body(response);
        } catch (DataIntegrityViolationException e) {
            response.put("response", "El nombre del hospedaje ya está en uso");
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e){
            response.put("response", "Ha ocurrido un error al registrar el hospedaje");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{lodgingId}")
    public ResponseEntity<?> updateLodging(@PathVariable String lodgingId, @Valid @RequestBody Lodgings lodging){
        JSONObject response = new JSONObject().appendField("error", true);
        try {
            Lodgings newLodging = new Lodgings(lodgingId, lodging.getLodgingName(), lodging.getLodgingRooms(), lodging.getLodgingCheckIn(), lodging.getLodgingCheckOut());
            lodgingsDao.updateLodging(newLodging);

            response.put("error", false);
            response.put("response", "Se ha actualizado el hospedaje correctamente");
            return ResponseEntity.ok().body(response);
        } catch (NullPointerException e) {
            response.put("response", "El alojamiento seleccionado no existe");
            return ResponseEntity.badRequest().body(response);
        } catch (DataIntegrityViolationException e){
            response.put("response", "El nombre del hospedaje ya está en uso");
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e){
            response.put("response", "Ha ocurrido un error al actualizar el hospedaje");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{lodgingId}")
    public ResponseEntity<?> deleteLodging(@PathVariable String lodgingId){
        JSONObject response = new JSONObject().appendField("error", true);
        try {
            lodgingsDao.deleteLodging(lodgingId);

            response.put("error", false);
            response.put("response", "Se ha eliminado el hospedaje correctamente");
            return ResponseEntity.ok().body(response);
        } catch (InvalidDataAccessApiUsageException e) {
            response.put("response", "El hospedaje seleccionado no existe");
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e){
            response.put("response", "Ha ocurrido un error al eliminar el hospedaje");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get-all/{page}")
    public ResponseEntity<?> getAllLodgings(@PathVariable int page){
        JSONObject response = new JSONObject().appendField("error", true);
        try {
            response.put("response", lodgingsDao.getAllLodgings(page));
            response.put("maxPage", Math.floorDiv(lodgingsDao.getLodgingsCount() - 1, 6));
            response.put("error", false);
            return ResponseEntity.ok().body(response);
        } catch (Exception e){
            response.put("response", "Ha ocurrido un error al obtener los hospedajes");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/get-by-tag/{lodgingTag}")
    public ResponseEntity<?> getLodgingByTag(@PathVariable String lodgingTag){
        JSONObject response = new JSONObject().appendField("error", true);
        try {
            response.put("response", lodgingsDao.getLodgingByTag(lodgingTag));
            response.put("error", false);
            return ResponseEntity.ok().body(response);
        } catch (EmptyResultDataAccessException e) {
            response.put("response", "El hospedaje seleccionado no existe");
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e){
            response.put("response", "Ha ocurrido un error al obtener el hospedaje");
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
