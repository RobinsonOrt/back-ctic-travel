package com.robinson.ctic_travel.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
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
@Table(name = "users", schema = "public")
public class Users {
    @Id
    @Column(name = "user_id", nullable = false, length = 60)
    private String userId;

    @NotBlank
    @Size(max = 60)
    @Column(name = "user_name", nullable = false, length = 60)
    private String userName;

    @NotBlank
    @Size(max = 60)
    @Column(name = "user_last_name", nullable = false, length = 60)
    private String userLastName;

    @NotBlank
    @Email
    @Size(max = 120)
    @Column(name = "user_email", nullable = false, unique = true, length = 120)
    private String userEmail;

    @NotBlank
    @Size(max = 120)
    @Column(name = "user_password", nullable = false, length = 120)
    private String userPassword;

    @Column(name = "user_created_date", nullable = false)
    private LocalDateTime userCreatedDate;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Roles role;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<PlansDestinationsUsers> plansDestinationsUsers;

    public Users(String userId) {
        this.userId = userId;
    }

    public Users(String userName, String userLastName, String userEmail, String userPassword) {
        this.userId = UUID.randomUUID().toString();
        this.userName = userName;
        this.userLastName = userLastName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userCreatedDate = LocalDateTime.now();
        this.role = new Roles("2");
    }
}
