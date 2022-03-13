package uz.pdp.pdpspring6thlessonhomework.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardLoginDto {
    private Integer password;
}
