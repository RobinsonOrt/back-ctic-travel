package com.robinson.ctic_travel.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "destinations", schema = "public")
public class Destinations {
    @Id
    @Column(name = "destination_id", nullable = false, length = 60)
    private String destinationId;

    @NotBlank
    @Size(max = 120)
    @Column(name = "destination_location", nullable = false, unique = true, length = 120)
    private String destinationLocation;

    @NotBlank
    @Size(max = 360)
    @Column(name = "destination_attractions", nullable = false, columnDefinition = "TEXT")
    private String destinationAttractions;

    @Column(name = "destination_tag", nullable = false, unique = true, length = 140)
    private String destinationTag;

    @Column(name = "destination_created_date", nullable = false)
    private LocalDateTime destinationCreatedDate;

    @OneToMany(mappedBy = "destination", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Lodgings> lodgings;

    @OneToMany(mappedBy = "destination")
    @JsonIgnore
    private List<PlansDestinations> plansDestinations;

    public Destinations(String destinationId) {
        this.destinationId = destinationId;
    }

    public Destinations(String destinationLocation, String destinationAttractions) {
        this.destinationId = UUID.randomUUID().toString();
        this.destinationLocation = destinationLocation;
        this.destinationAttractions = destinationAttractions;
        this.destinationTag = Tags.generateItemTag(destinationLocation);
        this.destinationCreatedDate = LocalDateTime.now();
    }

    public Destinations(String destinationId, String destinationLocation, String destinationAttractions) {
        this.destinationId = destinationId;
        this.destinationLocation = destinationLocation;
        this.destinationAttractions = destinationAttractions;
        this.destinationTag = Tags.generateItemTag(destinationLocation);
    }
}
