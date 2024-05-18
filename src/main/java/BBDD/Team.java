package BBDD;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "team")
public class Team {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "logo", nullable = false)
    private byte[] logo;

    @OneToMany(mappedBy = "teamid")
    private Set<Playerteam> playerteams = new LinkedHashSet<>();

    @OneToMany(mappedBy = "teamid")
    private Set<Teamtournament> teamtournaments = new LinkedHashSet<>();

    public Set<Teamtournament> getTeamtournaments() {
        return teamtournaments;
    }

    public void setTeamtournaments(Set<Teamtournament> teamtournaments) {
        this.teamtournaments = teamtournaments;
    }

    public Set<Playerteam> getPlayerteams() {
        return playerteams;
    }

    public void setPlayerteams(Set<Playerteam> playerteams) {
        this.playerteams = playerteams;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

}