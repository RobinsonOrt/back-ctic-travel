package com.robinson.ctic_travel.daos.lodgingsDao;

import com.robinson.ctic_travel.models.Lodgings;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class LodgingsDaoImplement implements LodgingsDao{
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void createLodging(Lodgings newLodging) {
        entityManager.persist(newLodging);
        entityManager.flush();
    }

    @Override
    public void updateLodging(Lodgings newLodging) {
        Lodgings lodging = entityManager.find(Lodgings.class, newLodging.getLodgingId());
        lodging.setLodgingName(newLodging.getLodgingName());
        lodging.setLodgingRooms(newLodging.getLodgingRooms());
        lodging.setLodgingCheckIn(newLodging.getLodgingCheckIn());
        lodging.setLodgingCheckOut(newLodging.getLodgingCheckOut());
        lodging.setLodgingTag(newLodging.getLodgingTag());
        entityManager.flush();
    }

    @Override
    public void deleteLodging(String lodgingId) {
        Lodgings lodging = entityManager.find(Lodgings.class, lodgingId);
        entityManager.remove(lodging);
        entityManager.flush();
    }

    @Override
    public List<Lodgings> getAllLodgings(int page) {
        return entityManager.createQuery("FROM Lodgings", Lodgings.class)
                .setFirstResult(page * 6)
                .setMaxResults(6)
                .getResultList();
    }

    @Override
    public int getLodgingsCount() {
        return entityManager.createQuery("SELECT COUNT(*) FROM Lodgings", Long.class).getSingleResult().intValue();
    }

    @Override
    public Lodgings getLodgingByTag(String lodgingTag) {
        return entityManager.createQuery("FROM Lodgings l WHERE l.lodgingTag = :lodgingTag", Lodgings.class)
                .setParameter("lodgingTag", lodgingTag)
                .getSingleResult();
    }
}
