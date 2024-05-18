package BBDD;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "tournament")
public class Tournament {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "tournamentname", nullable = false, length = 50)
    private String tournamentname;

    @Column(name = "entryfee", nullable = false)
    private Float entryfee;

    @Column(name = "prizeid")
    private Integer prizeid;

    @OneToMany(mappedBy = "tournamentid")
    private Set<Teamtournament> teamtournaments = new LinkedHashSet<>();

    public Set<Teamtournament> getTeamtournaments() {
        return teamtournaments;
    }

    public void setTeamtournaments(Set<Teamtournament> teamtournaments) {
        this.teamtournaments = teamtournaments;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTournamentname() {
        return tournamentname;
    }

    public void setTournamentname(String tournamentname) {
        this.tournamentname = tournamentname;
    }

    public Float getEntryfee() {
        return entryfee;
    }

    public void setEntryfee(Float entryfee) {
        this.entryfee = entryfee;
    }

    public Integer getPrizeid() {
        return prizeid;
    }

    public void setPrizeid(Integer prizeid) {
        this.prizeid = prizeid;
    }

}