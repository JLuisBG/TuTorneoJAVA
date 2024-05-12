package BBDD;

import jakarta.persistence.*;

@Entity
@Table(name = "teammatch")
public class Teammatch {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "matchid", nullable = false)
    private Matchup matchid;

    @Column(name = "score", nullable = false)
    private Integer score;

    @Column(name = "parentmatchupid")
    private Integer parentmatchupid;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "teamcompetingid", nullable = false)
    private Team teamcompetingid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Matchup getMatchid() {
        return matchid;
    }

    public void setMatchid(Matchup matchid) {
        this.matchid = matchid;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getParentmatchupid() {
        return parentmatchupid;
    }

    public void setParentmatchupid(Integer parentmatchupid) {
        this.parentmatchupid = parentmatchupid;
    }

    public Team getTeamcompetingid() {
        return teamcompetingid;
    }

    public void setTeamcompetingid(Team teamcompetingid) {
        this.teamcompetingid = teamcompetingid;
    }

}