package uz.pdp.pdpspring6thlessonhomework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.pdpspring6thlessonhomework.entity.Card;
import uz.pdp.pdpspring6thlessonhomework.entity.enums.CardEnum;
import uz.pdp.pdpspring6thlessonhomework.payload.AddCardDto;
import uz.pdp.pdpspring6thlessonhomework.payload.ApiResponse;
import uz.pdp.pdpspring6thlessonhomework.payload.UnblockCardDto;
import uz.pdp.pdpspring6thlessonhomework.repository.CardRepository;
import uz.pdp.pdpspring6thlessonhomework.security.JwtProvider;

import java.util.Date;
import java.util.Optional;

@Service
public class CardService {

    long year4 = 31536000000L;

    @Autowired
    CardRepository cardRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtProvider jwtProvider;


    public ApiResponse addCardUzcard(AddCardDto cardDto){

        Optional<Card> cardNumber = cardRepository.findByCardNumber(cardDto.getCardNumber());

        if (cardNumber.isPresent()){
            return new ApiResponse("Bunday Numberlik plastik mavjud ",false);
        }
        Card card = new Card();
        card.setCardUserFirstName(cardDto.getCardUserFirstName());
        card.setCardUserSecondName(cardDto.getCardUserSecondName());
        card.setExpiredDate(new Date(System.currentTimeMillis()+year4));
        card.setCardNumber(cardDto.getCardNumber());
        card.setCvv(cardDto.getCvv());
        card.setPassword(cardDto.getPassword());
        card.setCardEnum(CardEnum.UZCARD);
        cardRepository.save(card);
        return new ApiResponse("Card tayyor",true);
    }
    public ApiResponse addCardVisa(AddCardDto cardDto){
        Optional<Card> cardNumber = cardRepository.findByCardNumber(cardDto.getCardNumber());

        if (cardNumber.isPresent()){
            return new ApiResponse("Bunday Numberlik plastik mavjud ",false);
        }
        Card card = new Card();
        card.setCardUserFirstName(cardDto.getCardUserFirstName());
        card.setCardUserSecondName(cardDto.getCardUserSecondName());
        card.setExpiredDate(new Date(System.currentTimeMillis()+year4));
        card.setCardNumber(cardDto.getCardNumber());
        card.setCvv(cardDto.getCvv());
        card.setPassword(cardDto.getPassword());
        card.setCardEnum(CardEnum.VISA);
        cardRepository.save(card);
        return new ApiResponse("Card tayyor",true);
    }

    public ApiResponse addCardHumo(AddCardDto cardDto){
        Optional<Card> cardNumber = cardRepository.findByCardNumber(cardDto.getCardNumber());

        if (cardNumber.isPresent()){
            return new ApiResponse("Bunday Numberlik plastik mavjud ",false);
        }
        Card card = new Card();
        card.setCardUserFirstName(cardDto.getCardUserFirstName());
        card.setCardUserSecondName(cardDto.getCardUserSecondName());
        card.setExpiredDate(new Date(System.currentTimeMillis()+year4));
        card.setCardNumber(cardDto.getCardNumber());
        card.setCvv(cardDto.getCvv());
        card.setPassword(cardDto.getPassword());
        card.setCardEnum(CardEnum.HUMO);

        cardRepository.save(card);
        return new ApiResponse("Card tayyor",true);
    }
    public ApiResponse unblockCard(UnblockCardDto unblockCardDto){
        Optional<Card> optionalCard = cardRepository.findByCardNumber(unblockCardDto.getCarNumber());
        if (optionalCard.isEmpty()){
            return new ApiResponse("Bunday carta mavjud emas",false);
        }
        Card editingCard = optionalCard.get();
        editingCard.setEnabled(true);
        cardRepository.save(editingCard);
        return new ApiResponse("Carta blockdan ochildi",true);
    }
}
