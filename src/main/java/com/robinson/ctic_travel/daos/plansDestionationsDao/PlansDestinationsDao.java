package com.robinson.ctic_travel.daos.plansDestionationsDao;

import com.robinson.ctic_travel.models.PlansDestinations;

import java.util.List;

public interface PlansDestinationsDao {
    void addPlanToDestination(String destinationTag, String planTag);
    void removePlanFromDestination(String destinationTag, String planTag);
    List<PlansDestinations> getToUser(String userId, String destinationTag);
    List<PlansDestinations> getByUser(String userId);
}
