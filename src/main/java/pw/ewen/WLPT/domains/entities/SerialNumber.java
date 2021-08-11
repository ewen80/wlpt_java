package pw.ewen.WLPT.domains.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * created by wenliang on 2021-07-28
 * 序列号
 */
@Entity
public class SerialNumber {

    @Id
    @GeneratedValue
    private long id;
    private String name;
    /**
     * 格式：AABB{3} 。AABB代表依据字符串 {3}代表3位流水号。{}位置可以在一句字符串任何位置。
     */
    @Column(nullable = false)
    private String basis;
    private int number = 0;

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBasis() {
        return basis;
    }

    public void setBasis(String basis) {
        this.basis = basis;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
