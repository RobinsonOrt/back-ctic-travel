package com.robinson.ctic_travel.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "plans_destinations",
        schema = "public",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"plan_id", "destination_id"})
        }
)
public class PlansDestinations {
    @Id
    @Column(name = "plan_destination_id", nullable = false, length = 60)
    private String planDestinationId;

    @Column(name = "plan_actual_people", nullable = false)
    private int planActualPeople;

    @ManyToOne
    @JoinColumn(name = "plan_id", nullable = false)
    private Plans plan;

    @ManyToOne
    @JoinColumn(name = "destination_id", nullable = false)
    private Destinations destination;

    @OneToMany(mappedBy = "planDestination")
    @JsonIgnore
    private List<PlansDestinationsUsers> plansDestinationsUsers;

    public PlansDestinations(String planDestinationId) {
        this.planDestinationId = planDestinationId;
    }

    public PlansDestinations(Destinations destination, Plans plan) {
        this.planDestinationId = UUID.randomUUID().toString();
        this.planActualPeople = 0;
        this.destination = destination;
        this.plan = plan;
    }

    public PlansDestinations(String destinationId, String planId) {
        this.planDestinationId = UUID.randomUUID().toString();
        this.planActualPeople = 0;
        this.destination = new Destinations(destinationId);
        this.plan = new Plans(planId);
    }
}
