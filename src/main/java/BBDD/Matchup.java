package BBDD;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "matchup")
public class Matchup {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "matchupfase", nullable = false)
    private Integer matchupfase;

    @Column(name = "winnerid", nullable = false)
    private Integer winnerid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMatchupfase() {
        return matchupfase;
    }

    public void setMatchupfase(Integer matchupfase) {
        this.matchupfase = matchupfase;
    }

    public Integer getWinnerid() {
        return winnerid;
    }

    public void setWinnerid(Integer winnerid) {
        this.winnerid = winnerid;
    }

}