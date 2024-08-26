package com.challenge.challengePlants.Model;

import com.challenge.challengePlants.Model.User.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "plants")
public class Plant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String country;

    @OneToMany(mappedBy = "plant",cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<SensorSummary> sensorSummaries;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
