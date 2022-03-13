package uz.pdp.pdpspring6thlessonhomework.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.pdpspring6thlessonhomework.entity.enums.CardEnum;

import javax.persistence.Column;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddCardDto {

    private Long cardNumber;

    @Size(max = 3)
    private Integer cvv;
    private Double debit;

    private String cardUserFirstName;

    private String cardUserSecondName;

    private LocalDate expiredDate;
    private Integer password;
    private CardEnum cardEnum;
}
