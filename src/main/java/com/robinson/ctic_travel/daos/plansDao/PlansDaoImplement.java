package com.robinson.ctic_travel.daos.plansDao;

import com.robinson.ctic_travel.models.Plans;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class PlansDaoImplement implements PlansDao{
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void createPlan(Plans newPlan) {
        entityManager.persist(newPlan);
        entityManager.flush();
    }

    @Override
    public void updatePlan(Plans newPlan) {
        Plans plan = entityManager.find(Plans.class, newPlan.getPlanId());
        plan.setPlanName(newPlan.getPlanName());
        plan.setPlanPrice(newPlan.getPlanPrice());
        plan.setPlanDuration(newPlan.getPlanDuration());
        plan.setPlanMaxPeople(newPlan.getPlanMaxPeople());
        plan.setPlanTag(newPlan.getPlanTag());
        entityManager.flush();
    }

    @Override
    public void deletePlan(String planId) {
        Plans plan = entityManager.find(Plans.class, planId);
        entityManager.remove(plan);
        entityManager.flush();
    }

    @Override
    public List<Plans> getAllPlans(int page) {
        return entityManager.createQuery("FROM Plans", Plans.class)
                .setFirstResult(page * 6)
                .setMaxResults(6)
                .getResultList();
    }

    @Override
    public int getPlansCount() {
        return entityManager.createQuery("SELECT COUNT(*) FROM Plans", Long.class).getSingleResult().intValue();
    }

    @Override
    public Plans getPlanByTag(String planTag) {
        return entityManager.createQuery("FROM Plans p WHERE p.planTag = :planTag", Plans.class)
                .setParameter("planTag", planTag)
                .getSingleResult();
    }

    @Override
    public List<Plans> getPlansToDestination(String destinationTag) {
        return entityManager.createQuery("FROM Plans p WHERE p.planId NOT IN (SELECT pd.plan.planId FROM PlansDestinations pd WHERE pd.destination.destinationTag = :destinationTag)", Plans.class)
                .setParameter("destinationTag", destinationTag)
                .getResultList();
    }

    @Override
    public List<Plans> getPlansByDestination(String destinationTag) {
        return entityManager.createQuery("SELECT DISTINCT p FROM Plans p JOIN p.plansDestinations pd WHERE pd.destination.destinationTag = :destinationTag", Plans.class)
                .setParameter("destinationTag", destinationTag)
                .getResultList();
    }
}
