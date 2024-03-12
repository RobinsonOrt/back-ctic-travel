package com.robinson.ctic_travel.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "roles", schema = "public")
public class Roles {
    @Id
    @Column(name = "role_id", nullable = false, length = 2)
    private String roleId;

    @Column(name = "role_name", nullable = false, length = 60)
    private String roleName;

    @OneToMany(mappedBy = "role")
    @JsonIgnore
    private List<Users> users;

    public Roles(String roleId) {
        this.roleId = roleId;
    }
}
