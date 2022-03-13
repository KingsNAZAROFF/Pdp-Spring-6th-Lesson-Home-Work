package uz.pdp.pdpspring6thlessonhomework.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardDto {
    private Long cardNumber;
    private Integer password;

    private Double debit;
    private Integer bankomatId;

}
