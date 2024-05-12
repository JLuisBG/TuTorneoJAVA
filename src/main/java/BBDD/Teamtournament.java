package BBDD;

import jakarta.persistence.*;

@Entity
@Table(name = "teamtournament")
public class Teamtournament {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tournamentid")
    private Tournament tournamentid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teamid")
    private Team teamid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Tournament getTournamentid() {
        return tournamentid;
    }

    public void setTournamentid(Tournament tournamentid) {
        this.tournamentid = tournamentid;
    }

    public Team getTeamid() {
        return teamid;
    }

    public void setTeamid(Team teamid) {
        this.teamid = teamid;
    }

}