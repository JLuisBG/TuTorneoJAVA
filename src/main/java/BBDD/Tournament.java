package BBDD;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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