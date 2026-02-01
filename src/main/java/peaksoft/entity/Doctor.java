package peaksoft.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "doctors")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"hospital", "departmentsList", "appointmentsList"})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank
    @Column(nullable = false)
    String firstName;

    @NotBlank
    @Column(nullable = false)
    String lastName;

    @NotBlank
    @Column(nullable = false)
    String position;

    @NotBlank
    @Column(nullable = false, unique = true)
    String email;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "hospital_id", nullable = false)
    Hospital hospital;

    @ManyToMany(mappedBy = "doctorsList")
    List<Department> departmentsList;

    @OneToMany(mappedBy = "doctor")
    List<Appointment> appointmentsList;
}