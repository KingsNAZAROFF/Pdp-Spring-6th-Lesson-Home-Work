package uz.pdp.pdpspring6thlessonhomework.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnblockCardDto {
    private Long carNumber;
}
