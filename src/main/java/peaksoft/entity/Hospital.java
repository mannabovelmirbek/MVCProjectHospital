package peaksoft.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "hospitals")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"doctorsList", "departmentList", "patientsList"})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank
    @Column(nullable = false)
    String name;

    @NotBlank
    @Column(nullable = false)
    String address;

    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL)
    List<Doctor> doctorsList;

    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL)
    List<Department> departmentList;

    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL)
    List<Patient> patientsList;
}