package BBDD;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "prize")
public class Prize {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "prizenumber")
    private Integer prizenumber;

    @Column(name = "prizename", length = 50)
    private String prizename;

    @Column(name = "prizeamount")
    private Float prizeamount;

    @Column(name = "prizepercentage", nullable = false)
    private Float prizepercentage;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPrizenumber() {
        return prizenumber;
    }

    public void setPrizenumber(Integer prizenumber) {
        this.prizenumber = prizenumber;
    }

    public String getPrizename() {
        return prizename;
    }

    public void setPrizename(String prizename) {
        this.prizename = prizename;
    }

    public Float getPrizeamount() {
        return prizeamount;
    }

    public void setPrizeamount(Float prizeamount) {
        this.prizeamount = prizeamount;
    }

    public Float getPrizepercentage() {
        return prizepercentage;
    }

    public void setPrizepercentage(Float prizepercentage) {
        this.prizepercentage = prizepercentage;
    }

}