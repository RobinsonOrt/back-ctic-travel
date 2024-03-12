package com.robinson.ctic_travel.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "plans", schema = "public")
public class Plans {
    @Id
    @Column(name = "plan_id", nullable = false, length = 60)
    private String planId;

    @NotBlank
    @Size(max = 120)
    @Column(name = "plan_name", nullable = false, length = 120)
    private String planName;

    @NotNull
    @Column(name = "plan_price", nullable = false)
    private double planPrice;

    @NotBlank
    @Size(max = 60)
    @Column(name = "plan_duration", nullable = false, length = 60)
    private String planDuration;

    @NotNull
    @Column(name = "plan_max_people", nullable = false)
    private int planMaxPeople;

    @Column(name = "plan_tag", nullable = false, unique = true, length = 140)
    private String planTag;

    @Column(name = "plan_created_date", nullable = false)
    private LocalDateTime planCreatedDate;

    @ManyToOne
    @JoinColumn(name = "plan_transportation_type_id", nullable = false)
    private PlanTransportationTypes planTransportationType;

    @OneToMany(mappedBy = "plan")
    @JsonIgnore
    private List<PlansDestinations> plansDestinations;

    public Plans(String planId) {
        this.planId = planId;
    }

    public Plans(String planName, double planPrice, String planDuration, int planMaxPeople, PlanTransportationTypes planTransportationType) {
        this.planId = UUID.randomUUID().toString();
        this.planName = planName;
        this.planPrice = planPrice;
        this.planDuration = planDuration;
        this.planMaxPeople = planMaxPeople;
        this.planTag = Tags.generateItemTag(UUID.randomUUID().toString().substring(0, 2) + " " + planName);
        this.planCreatedDate = LocalDateTime.now();
        this.planTransportationType = planTransportationType;
    }

    public Plans(String planId, String planName, double planPrice, String planDuration, int planMaxPeople) {
        this.planId = planId;
        this.planName = planName;
        this.planPrice = planPrice;
        this.planDuration = planDuration;
        this.planMaxPeople = planMaxPeople;
        this.planTag = Tags.generateItemTag(UUID.randomUUID().toString().substring(0, 2) + " " + planName);
    }
}
