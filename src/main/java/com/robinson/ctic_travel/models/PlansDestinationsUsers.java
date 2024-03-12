package com.robinson.ctic_travel.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "plans_destinations_users",
        schema = "public",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"plan_destination_id", "user_id"})
        }
)
public class PlansDestinationsUsers {
    @Id
    @Column(name = "plan_destination_user_id", nullable = false, length = 60)
    private String planDestinationUserId;

    @ManyToOne
    @JoinColumn(name = "plan_destination_id", nullable = false)
    private PlansDestinations planDestination;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private Users user;

    public PlansDestinationsUsers(String userId, String planDestinationId) {
        this.planDestinationUserId = UUID.randomUUID().toString();
        this.user = new Users(userId);
        this.planDestination = new PlansDestinations(planDestinationId);
    }
}
