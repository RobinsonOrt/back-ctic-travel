package com.robinson.ctic_travel.daos.destinationsDao;

import com.robinson.ctic_travel.models.Destinations;

import java.util.List;

public interface DestinationsDao {
    void createDestination(Destinations newDestination);
    void updateDestination(Destinations newDestination);
    void deleteDestination(String destinationId);
    List<Destinations> getAllDestinations(int page);
    int getDestinationsCount();
    Object getDestinations();
    List<Destinations> searchDestinations(String search);
    Destinations getDestinationsByTag(String destinationTag);
    void addPlanToDestination(String destinationId, String planId);
}
