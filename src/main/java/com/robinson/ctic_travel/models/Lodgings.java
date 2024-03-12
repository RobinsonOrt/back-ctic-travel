package com.robinson.ctic_travel.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "lodgings", schema = "public")
public class Lodgings {
    @Id
    @Column(name = "lodging_id", nullable = false, length = 60)
    private String lodgingId;

    @NotBlank
    @Size(max = 120)
    @Column(name = "lodging_name", nullable = false, unique = true, length = 120)
    private String lodgingName;

    @Max(99)
    @Column(name = "lodging_rooms")
    private int lodgingRooms;

    @NotNull
    @Column(name = "lodging_check_in", nullable = false)
    private LocalTime lodgingCheckIn;

    @NotNull
    @Column(name = "lodging_check_out", nullable = false)
    private LocalTime lodgingCheckOut;

    @Column(name = "lodging_tag", nullable = false, unique = true, length = 140)
    private String lodgingTag;

    @Column(name = "lodging_created_date", nullable = false)
    private LocalDateTime lodgingCreatedDate;

    @ManyToOne
    @JoinColumn(name = "lodging_type_id", nullable = false)
    private LodgingTypes lodgingType;

    @ManyToOne
    @JoinColumn(name = "destination_id", nullable = false)
    @JsonIgnore
    private Destinations destination;

    public Lodgings(String lodgingName, int lodgingRooms, LocalTime lodgingCheckIn, LocalTime lodgingCheckOut, LodgingTypes lodgingType, String destinationId) {
        this.lodgingId = UUID.randomUUID().toString();
        this.lodgingName = lodgingName;
        this.lodgingRooms = lodgingRooms;
        this.lodgingCheckIn = lodgingCheckIn;
        this.lodgingCheckOut = lodgingCheckOut;
        this.lodgingTag = Tags.generateItemTag(lodgingName);
        this.lodgingCreatedDate = LocalDateTime.now();
        this.lodgingType = lodgingType;
        this.destination = new Destinations(destinationId);
    }

    public Lodgings(String lodgingId, String lodgingName, int lodgingRooms, LocalTime lodgingCheckIn, LocalTime lodgingCheckOut) {
        this.lodgingId = lodgingId;
        this.lodgingName = lodgingName;
        this.lodgingRooms = lodgingRooms;
        this.lodgingCheckIn = lodgingCheckIn;
        this.lodgingCheckOut = lodgingCheckOut;
        this.lodgingTag = Tags.generateItemTag(lodgingName);
    }
}
