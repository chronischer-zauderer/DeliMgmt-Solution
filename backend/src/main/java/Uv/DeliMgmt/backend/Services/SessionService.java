package Uv.DeliMgmt.backend.Services;

import Uv.DeliMgmt.backend.Models.Session;
import Uv.DeliMgmt.backend.Repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;

    @Autowired
    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    // Create
    public void createSession(Session session) {
        sessionRepository.save(session);
    }

    // Get all
    public List<Session> getAllSessions() {
        return sessionRepository.findAll();
    }

    // Get one by ID
    public Optional<Session> getSessionById(Long id) {
        return sessionRepository.findById(id);
    }

    // Update
    public void updateSession(Session updatedSession) {
        Optional<Session> existingSessionOpt = sessionRepository.findById(updatedSession.getSessionId());

        if (existingSessionOpt.isPresent()) {
            Session existingSession = existingSessionOpt.get();
            existingSession.setUser(updatedSession.getUser());
            existingSession.setSessionToken(updatedSession.getSessionToken());
            existingSession.setExpiresAt(updatedSession.getExpiresAt());
            sessionRepository.save(existingSession);
        } else {
            throw new RuntimeException("Session not found with id: " + updatedSession.getSessionId());
        }
    }

    // Delete
    public void deleteSession(Long id) {
        sessionRepository.deleteById(id);
    }
}
