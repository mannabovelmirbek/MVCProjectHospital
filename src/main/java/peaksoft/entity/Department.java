package peaksoft.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "departments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"hospital", "doctorsList"})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank
    @Column(nullable = false, unique = true)
    String name;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "hospital_id", nullable = false)
    Hospital hospital;

    @ManyToMany
    @JoinTable(
            name = "department_doctors",
            joinColumns = @JoinColumn(name = "department_id"),
            inverseJoinColumns = @JoinColumn(name = "doctor_id")
    )
    List<Doctor> doctorsList;
}