package BBDD;

import jakarta.persistence.*;

@Entity
@Table(name = "playerteam")
public class Playerteam {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "playerid", nullable = false)
    private Player playerid;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "teamid", nullable = false)
    private Team teamid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Player getPlayerid() {
        return playerid;
    }

    public void setPlayerid(Player playerid) {
        this.playerid = playerid;
    }

    public Team getTeamid() {
        return teamid;
    }

    public void setTeamid(Team teamid) {
        this.teamid = teamid;
    }

}