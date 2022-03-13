package uz.pdp.pdpspring6thlessonhomework.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Bankomat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private Double maxDebit = 2000000D;
    private Double debit;

    private int numberOf1000;
    private int numberOf5000;
    private int numberOf10000;
    private int numberOf50000;
    private int numberOf100000;

    @OneToOne
    private User user;



}
