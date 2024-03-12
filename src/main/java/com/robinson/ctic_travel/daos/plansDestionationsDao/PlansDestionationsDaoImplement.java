package com.robinson.ctic_travel.daos.plansDestionationsDao;

import com.robinson.ctic_travel.models.Destinations;
import com.robinson.ctic_travel.models.Plans;
import com.robinson.ctic_travel.models.PlansDestinations;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class PlansDestionationsDaoImplement implements PlansDestinationsDao{
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void addPlanToDestination(String destinationTag, String planTag) {
        Destinations destination = entityManager.createQuery("FROM Destinations d WHERE d.destinationTag = :destinationTag", Destinations.class)
                .setParameter("destinationTag", destinationTag)
                .getSingleResult();
        Plans plan = entityManager.createQuery("FROM Plans p WHERE p.planTag = :planTag", Plans.class)
                .setParameter("planTag", planTag)
                .getSingleResult();
        entityManager.persist(new PlansDestinations(destination, plan));
        entityManager.flush();
    }

    @Override
    public void removePlanFromDestination(String destinationTag, String planTag) {
        PlansDestinations plansDestinations = entityManager.createQuery("FROM PlansDestinations pd WHERE pd.destination.destinationTag = :destinationTag AND pd.plan.planTag = :planTag", PlansDestinations.class)
                .setParameter("destinationTag", destinationTag)
                .setParameter("planTag", planTag)
                .getSingleResult();
        entityManager.remove(plansDestinations);
        entityManager.flush();
    }

    @Override
    public List<PlansDestinations> getToUser(String userId, String destinationTag) {
        return entityManager.createQuery("FROM PlansDestinations pd WHERE pd.plan.planId NOT IN (SELECT pdu.planDestination.plan.planId FROM PlansDestinationsUsers pdu WHERE pdu.user.userId = :userId AND pdu.planDestination.destination.destinationTag = :destinationTag) AND pd.destination.destinationTag = :destinationTag AND pd.planActualPeople < pd.plan.planMaxPeople", PlansDestinations.class)
                .setParameter("userId", userId)
                .setParameter("destinationTag", destinationTag)
                .getResultList();
    }

    @Override
    public List<PlansDestinations> getByUser(String userId) {
        return entityManager.createQuery("SELECT DISTINCT pd FROM PlansDestinations pd JOIN pd.plansDestinationsUsers pdu WHERE pdu.user.userId = :userId", PlansDestinations.class)
                .setParameter("userId", userId)
                .getResultList();
    }
}
