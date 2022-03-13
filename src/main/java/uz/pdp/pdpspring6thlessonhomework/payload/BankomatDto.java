package uz.pdp.pdpspring6thlessonhomework.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.pdpspring6thlessonhomework.entity.User;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class BankomatDto {

    private Integer id;
    private String name;

    private int numberOf1000;
    private int numberOf5000;
    private int numberOf10000;
    private int numberOf50000;
    private int numberOf100000;


    private Long userId;


    public BankomatDto(Integer id, int numberOf1000, int numberOf5000, int numberOf10000, int numberOf50000, int numberOf100000, Long userId) {
        this.id = id;
        this.numberOf1000 = numberOf1000;
        this.numberOf5000 = numberOf5000;
        this.numberOf10000 = numberOf10000;
        this.numberOf50000 = numberOf50000;
        this.numberOf100000 = numberOf100000;
    }
}
