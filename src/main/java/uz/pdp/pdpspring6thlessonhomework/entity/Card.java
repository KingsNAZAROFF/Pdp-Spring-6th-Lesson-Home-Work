package uz.pdp.pdpspring6thlessonhomework.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.pdp.pdpspring6thlessonhomework.entity.enums.CardEnum;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Card implements UserDetails {
    @Id
    private UUID id;

    @Column(unique = true)
    private Long cardNumber;

    @Size(max = 3)
    private Integer cvv;
    private Double debit;

    private String cardUserFirstName;

    private String cardUserSecondName;

    private Date expiredDate;
    private Integer password;
    private CardEnum cardEnum;
    @Transient
    private Integer count;

    private boolean cardNonExpired=true;
    private boolean cardNonLocked=true;
    private boolean credentialsNonExpired=true;
    private boolean enabled=true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return cardNumber.toString();
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.cardNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.cardNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        if (count>=3){
            return this.enabled=false;
        }
        return this.enabled;
    }
}
