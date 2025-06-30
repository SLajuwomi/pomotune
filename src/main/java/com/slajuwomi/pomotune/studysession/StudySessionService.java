package com.slajuwomi.pomotune.studysession;

import com.slajuwomi.pomotune.user.User;
import com.slajuwomi.pomotune.user.UserService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class StudySessionService {

    private final UserService userService;
    private Validator validator;

    private final StudySessionRepository studySessionRepository;

    public StudySessionService(StudySessionRepository studySessionRepository, UserService userService) {
        this.studySessionRepository = studySessionRepository;
        this.userService = userService;
    }

    public StudySession createStudySession(StudySession newStudySession, Long userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }
        newStudySession.setUser(user);
        Set<ConstraintViolation<StudySession>> violations = validator.validate(newStudySession);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<StudySession> violation : violations) {
                sb.append(violation.getMessage()).append(" ");
            }

            throw new IllegalArgumentException("Invalid study session data: " + sb.toString());
        }

        System.out.println("Saving study session");
        return studySessionRepository.save(newStudySession);
    }

    public List<StudySession> getStudySessionByUser(Long userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        return studySessionRepository.findStudySessionByUser(user);
    }

    public long getTotalStudyMinutesForUser(Long userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        List<StudySession> sessions = studySessionRepository.findStudySessionByUser(user);

        return sessions.stream().mapToLong(session -> Duration.between(session.getStartTime(), session.getEndTime()).toMinutes()).sum();
    }
}
