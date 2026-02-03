package peaksoft.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotNull;
import peaksoft.enums.Gender;

import java.util.List;

@Entity
@Table(name = "patients")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"hospital", "appointmentsList"})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Patient {
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
    @Pattern(regexp = "^\\+996\\d{9}$")
    @Column(nullable = false)
    String phoneNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    Gender gender;

    @NotBlank
    @Column(nullable = false)
    String email;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "hospital_id", nullable = false)
    @JsonIgnore  // ✅ Игнорируем hospital при сериализации
    Hospital hospital;

    @OneToMany(mappedBy = "patient")
    @JsonIgnore  // ✅ Игнорируем appointments при сериализации
    List<Appointment> appointmentsList;
}