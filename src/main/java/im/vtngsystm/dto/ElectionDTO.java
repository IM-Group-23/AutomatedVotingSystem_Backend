package im.vtngsystm.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ElectionDTO extends SuperDTO {

    private int id;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String electionType;
    private List<ElectionContestantDTO> candidates;

    public ElectionDTO() {
    }

    public ElectionDTO(int id, LocalDate date, LocalTime startTime, LocalTime endTime, String electionType, List<ElectionContestantDTO> candidates) {
        this.id = id;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.electionType = electionType;
        this.candidates = candidates;
    }

    public ElectionDTO(LocalDate date, LocalTime startTime, LocalTime endTime, String electionType, List<ElectionContestantDTO> candidates) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.electionType = electionType;
        this.candidates = candidates;
    }

    public String getElectionType() {
        return electionType;
    }

    public void setElectionType(String electionType) {
        this.electionType = electionType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public List<ElectionContestantDTO> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<ElectionContestantDTO> candidates) {
        this.candidates = candidates;
    }
}
