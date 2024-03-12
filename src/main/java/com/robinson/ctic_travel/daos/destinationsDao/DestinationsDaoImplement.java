package com.robinson.ctic_travel.daos.destinationsDao;

import com.robinson.ctic_travel.models.Destinations;
import com.robinson.ctic_travel.models.Plans;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class DestinationsDaoImplement implements DestinationsDao{
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void createDestination(Destinations newDestination) {
        entityManager.persist(newDestination);
        entityManager.flush();
    }

    @Override
    public void updateDestination(Destinations newDestination) {
        Destinations destination = entityManager.find(Destinations.class, newDestination.getDestinationId());
        destination.setDestinationLocation(newDestination.getDestinationLocation());
        destination.setDestinationAttractions(newDestination.getDestinationAttractions());
        destination.setDestinationTag(newDestination.getDestinationTag());
        entityManager.flush();
    }

    @Override
    public void deleteDestination(String destinationId) {
        Destinations destination = entityManager.find(Destinations.class, destinationId);
        entityManager.remove(destination);
        entityManager.flush();
    }

    @Override
    public List<Destinations> getAllDestinations(int page) {
        return entityManager.createQuery("FROM Destinations", Destinations.class)
                .setFirstResult(page * 6)
                .setMaxResults(6)
                .getResultList();
    }

    @Override
    public int getDestinationsCount() {
        return entityManager.createQuery("SELECT COUNT(*) FROM Destinations", Long.class).getSingleResult().intValue();
    }

    @Override
    public Object getDestinations() {
        return entityManager.createQuery("FROM Destinations", Destinations.class).getResultList();
    }

    @Override
    public List<Destinations> searchDestinations(String search) {
        return entityManager.createQuery("FROM Destinations d WHERE lower(d.destinationLocation) LIKE lower(:search) OR lower(d.destinationAttractions) LIKE lower(:destinationAttractions)", Destinations.class)
                .setParameter("search", "%" + search + "%")
                .setParameter("destinationAttractions", "%" + search + "%")
                .getResultList();
    }

    @Override
    public Destinations getDestinationsByTag(String destinationTag) {
        return entityManager.createQuery("FROM Destinations d WHERE d.destinationTag = :destinationTag", Destinations.class)
                .setParameter("destinationTag", destinationTag)
                .getSingleResult();
    }

    @Override
    public void addPlanToDestination(String destinationId, String planId) {
        Destinations destination = entityManager.find(Destinations.class, destinationId);
        /*destination.getPlans().add(entityManager.find(Plans.class, planId));*/
        entityManager.flush();
    }
}
