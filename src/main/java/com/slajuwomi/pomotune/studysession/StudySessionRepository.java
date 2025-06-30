package com.slajuwomi.pomotune.studysession;

import com.slajuwomi.pomotune.user.User;
import com.slajuwomi.pomotune.studysession.PomodoroType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface StudySessionRepository extends JpaRepository<StudySession, Long> {
    List<StudySession> findStudySessionByUser(User user);

    List<StudySession> findStudySessionByStartTimeAfter(LocalDateTime startTimeAfter);

    List<StudySession> findStudySessionByEndTimeBefore(LocalDateTime endTimeBefore);

    List<StudySession> findStudySessionByStartTimeBetween(LocalDateTime startTimeAfter, LocalDateTime startTimeBefore);

    List<StudySession> findStudySessionByEndTimeBetween(LocalDateTime endTimeAfter, LocalDateTime endTimeBefore);

    List<StudySession> findStudySessionByPomodoroType(PomodoroType pomodoroType);

    List<StudySession> findByUserAndStartTimeBetween(User user, LocalDateTime start, LocalDateTime end);

    List<StudySession> findByUserOrderByStartTimeDesc(User user);

    long countByUserAndStartTimeBetween(User user, LocalDateTime startTimeAfter, LocalDateTime startTimeBefore);
}
