package uz.pdp.pdpspring6thlessonhomework.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.pdp.pdpspring6thlessonhomework.entity.Bankomat;
import uz.pdp.pdpspring6thlessonhomework.entity.User;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class BankomatHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int numberOf1000;
    private int numberOf5000;
    private int numberOf10000;
    private int numberOf50000;
    private int numberOf100000;

    @ManyToOne
    private Bankomat bankomat;

    @CreationTimestamp
    private Timestamp time;


    @CreatedBy
    private Long createdBy;

    
}
