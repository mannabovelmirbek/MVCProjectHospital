package peaksoft.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "appointments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"hospital", "department", "doctor", "patient"})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull
    @Column(nullable = false)
    LocalDate date;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "hospital_id", nullable = false)
    Hospital hospital;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    Department department;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    Doctor doctor;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    Patient patient;
}