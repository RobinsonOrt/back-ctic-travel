package com.robinson.ctic_travel.daos.plansDestinationsUsersDao;

import com.robinson.ctic_travel.models.PlansDestinations;
import com.robinson.ctic_travel.models.PlansDestinationsUsers;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
@Transactional
public class PlansDestinationsUsersDaoImplement implements PlansDestinationsUsersDao{
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void setToDestinationPlan(PlansDestinationsUsers plansDestinationsUsers) {
        PlansDestinations plansDestinations = entityManager.find(PlansDestinations.class, plansDestinationsUsers.getPlanDestination().getPlanDestinationId());
        plansDestinations.setPlanActualPeople(plansDestinations.getPlanActualPeople() + 1);
        entityManager.persist(plansDestinationsUsers);
        entityManager.flush();
    }

    @Override
    public void removeFromDestinationPlan(String userId, String destinationPlanId) {
        PlansDestinationsUsers plansDestinationsUsers = entityManager.createQuery("FROM PlansDestinationsUsers pdu WHERE pdu.user.userId = :userId AND pdu.planDestination.planDestinationId = :destinationPlanId", PlansDestinationsUsers.class)
                .setParameter("userId", userId)
                .setParameter("destinationPlanId", destinationPlanId)
                .getSingleResult();
        PlansDestinations plansDestinations = entityManager.find(PlansDestinations.class, plansDestinationsUsers.getPlanDestination().getPlanDestinationId());
        plansDestinations.setPlanActualPeople(plansDestinations.getPlanActualPeople() - 1);
        entityManager.remove(plansDestinationsUsers);
        entityManager.flush();
    }
}
