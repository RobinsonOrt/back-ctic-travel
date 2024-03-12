package com.robinson.ctic_travel.daos.plansDestinationsUsersDao;

import com.robinson.ctic_travel.models.PlansDestinationsUsers;

public interface PlansDestinationsUsersDao {
    void setToDestinationPlan(PlansDestinationsUsers plansDestinationsUsers);
    void removeFromDestinationPlan(String userId, String destinationPlanId);
}
