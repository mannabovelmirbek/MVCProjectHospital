package peaksoft.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
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
    @JsonIgnore  // ✅ Игнорируем hospital
    Hospital hospital;

    @ManyToMany
    @JoinTable(
            name = "department_doctors",
            joinColumns = @JoinColumn(name = "department_id"),
            inverseJoinColumns = @JoinColumn(name = "doctor_id")
    )
    @JsonIgnoreProperties({"departmentsList", "hospital", "appointmentsList"})
    List<Doctor> doctorsList = new ArrayList<>();
}