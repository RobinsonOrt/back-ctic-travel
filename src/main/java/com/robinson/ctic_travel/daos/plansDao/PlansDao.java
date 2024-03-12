package com.robinson.ctic_travel.daos.plansDao;

import com.robinson.ctic_travel.models.Plans;

import java.util.List;

public interface PlansDao {
    void createPlan(Plans newPlan);
    void updatePlan(Plans newPlan);
    void deletePlan(String planId);
    List<Plans> getAllPlans(int page);
    int getPlansCount();
    Plans getPlanByTag(String planTag);
    List<Plans> getPlansToDestination(String destinationTag);
    List<Plans> getPlansByDestination(String destinationTag);
}
