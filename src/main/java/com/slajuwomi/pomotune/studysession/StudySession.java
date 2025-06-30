package com.slajuwomi.pomotune.studysession;

import com.slajuwomi.pomotune.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "studysession")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class StudySession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // How many 25 min cycles completed
    @Positive
    private Integer cyclesCompleted;

    // Work session or break session
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Type of session must be defined")
    private PomodoroType pomodoroType;

    @NotNull(message = "Start time is required.")
    @Column(name = "startTime", updatable = false)
    private LocalDateTime startTime;

    @NotNull(message = "End time is required.")
    @Column(name = "endTime", updatable = false)
    private LocalDateTime endTime;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
