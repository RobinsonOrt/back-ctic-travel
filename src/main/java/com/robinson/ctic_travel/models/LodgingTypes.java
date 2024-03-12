package com.robinson.ctic_travel.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "lodging_types", schema = "public")
public class LodgingTypes {
    @Id
    @Column(name = "lodging_type_id", nullable = false, length = 2)
    private String lodgingTypeId;

    @Column(name = "lodging_type_name", nullable = false, length = 60)
    private String lodgingTypeName;

    @OneToMany(mappedBy = "lodgingType")
    @JsonIgnore
    private List<Lodgings> lodgings;
}