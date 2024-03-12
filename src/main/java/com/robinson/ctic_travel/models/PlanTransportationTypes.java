package com.robinson.ctic_travel.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "plan_transportation_types", schema = "public")
public class PlanTransportationTypes {
    @Id
    @Column(name = "plan_transportation_type_id", nullable = false, length = 2)
    private String planTransportationTypeId;

    @Column(name = "plan_transportation_type_name", nullable = false, length = 60)
    private String planTransportationTypeName;

    @OneToMany(mappedBy = "planTransportationType")
    @JsonIgnore
    private List<Plans> plans;
}
