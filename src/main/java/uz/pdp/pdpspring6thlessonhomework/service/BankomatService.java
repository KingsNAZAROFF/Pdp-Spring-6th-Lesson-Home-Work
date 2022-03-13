package uz.pdp.pdpspring6thlessonhomework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import uz.pdp.pdpspring6thlessonhomework.entity.Bankomat;
import uz.pdp.pdpspring6thlessonhomework.entity.BankomatHistory;
import uz.pdp.pdpspring6thlessonhomework.entity.Card;
import uz.pdp.pdpspring6thlessonhomework.entity.User;
import uz.pdp.pdpspring6thlessonhomework.payload.ApiResponse;
import uz.pdp.pdpspring6thlessonhomework.payload.BankomatDto;
import uz.pdp.pdpspring6thlessonhomework.payload.CardDto;
import uz.pdp.pdpspring6thlessonhomework.repository.BankomatHistoryRepository;
import uz.pdp.pdpspring6thlessonhomework.repository.BankomatRepository;
import uz.pdp.pdpspring6thlessonhomework.repository.CardRepository;
import uz.pdp.pdpspring6thlessonhomework.repository.UserRepository;

import java.util.Optional;

@Service
public class BankomatService {

    @Autowired
    BankomatRepository bankomatRepository;
    @Autowired
    BankomatHistoryRepository bankomatHistoryRepository;
    @Autowired
    CardRepository cardRepository;

    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    UserRepository userRepository;


    public ApiResponse addBankomat(BankomatDto bankomatDto){
        Optional<User> optionalUser = userRepository.findById(bankomatDto.getUserId());
        if (optionalUser.isEmpty()){
            return new ApiResponse("Bunday User mavjud emas",false);
        }
        Bankomat bankomat = new Bankomat();
        bankomat.setName(bankomatDto.getName());
        bankomat.setUser(optionalUser.get());

        bankomat.setNumberOf1000(bankomatDto.getNumberOf1000());
        bankomat.setNumberOf1000(bankomatDto.getNumberOf5000());
        bankomat.setNumberOf1000(bankomatDto.getNumberOf10000());
        bankomat.setNumberOf1000(bankomatDto.getNumberOf50000());
        bankomat.setNumberOf1000(bankomatDto.getNumberOf100000()); Double debit = (double) (bankomatDto.getNumberOf1000() * 1000
                + bankomatDto.getNumberOf5000() * 5000
                + bankomatDto.getNumberOf10000() * 10000
                + bankomatDto.getNumberOf50000() * 50000
                + bankomatDto.getNumberOf100000() * 100000);

        bankomat.setDebit(debit);
        bankomatRepository.save(bankomat);
        return new ApiResponse("Bankomat qo'shildi",true);

    }
    public ApiResponse addMoney(BankomatDto bankomatDto){
        Optional<Bankomat> optionalBankomat = bankomatRepository.findById(bankomatDto.getId());
        if (optionalBankomat.isEmpty()){
            return new ApiResponse("Bunday bankomat mavjud emas",false);
        }
        Bankomat addingMoney = optionalBankomat.get();
        BankomatHistory bankomatHistory = new BankomatHistory();
        bankomatHistory.setBankomat(addingMoney);
        bankomatHistory.setNumberOf1000(bankomatDto.getNumberOf1000());
        bankomatHistory.setNumberOf1000(bankomatDto.getNumberOf5000());
        bankomatHistory.setNumberOf1000(bankomatDto.getNumberOf10000());
        bankomatHistory.setNumberOf1000(bankomatDto.getNumberOf50000());
        bankomatHistory.setNumberOf1000(bankomatDto.getNumberOf100000());
        Double debit = (double) (bankomatDto.getNumberOf1000() * 1000
                + bankomatDto.getNumberOf5000() * 5000
                + bankomatDto.getNumberOf10000() * 10000
                + bankomatDto.getNumberOf50000() * 50000
                + bankomatDto.getNumberOf100000() * 100000);
        int s1000 = addingMoney.getNumberOf1000()+bankomatDto.getNumberOf1000();
        int s5000 = addingMoney.getNumberOf5000()+bankomatDto.getNumberOf5000();
        int s10000 = addingMoney.getNumberOf10000()+bankomatDto.getNumberOf10000();
        int s50000 = addingMoney.getNumberOf50000()+bankomatDto.getNumberOf50000();
        int s100000 = addingMoney.getNumberOf100000()+bankomatDto.getNumberOf100000();

        bankomatHistoryRepository.save(bankomatHistory);
        Double debit1 = addingMoney.getDebit();
        addingMoney.setNumberOf1000(s1000);
        addingMoney.setNumberOf5000(s5000);
        addingMoney.setNumberOf10000(s10000);
        addingMoney.setNumberOf50000(s50000);
        addingMoney.setNumberOf100000(s100000);
        addingMoney.setDebit(debit1+debit);
        bankomatRepository.save(addingMoney);
        return new ApiResponse("Bankomatga pul qo'shildi",true);
    }

    public ApiResponse takeMoney(CardDto cardDto){
        Optional<Card> optionalCard = cardRepository.findByCardNumber(cardDto.getCardNumber());
        Optional<Bankomat> optionalBankomat = bankomatRepository.findById(cardDto.getBankomatId());
        Bankomat bankomat = optionalBankomat.get();
        if (optionalCard.isEmpty()){
            return new ApiResponse("Bunday card mavjud emas",false);
        }
        Card card = optionalCard.get();
        if (!card.isCardNonExpired()){
            return new ApiResponse("Card ni amal qilish muddati tugagan",false);
        }

        if (!card.isEnabled()){
            return new ApiResponse("Card blocklangan",false);
        }
        if (cardDto.getPassword()!=card.getPassword()){

            int count = card.getCount()+1;
            card.setCount(count);
            if (card.getCount()>=3){
                card.setEnabled(false);
            }
            return new ApiResponse("Parol hato ! QOlgan urinishlar soni : " + (3-count),false);

        }
        if (card.getDebit()< cardDto.getDebit()){
            card.setCount(0);
            card.setEnabled(true);
            return new ApiResponse("Xisobingizda yetarli mablag' mavjud emas",false);
        }
        if (cardDto.getDebit()>bankomat.getMaxDebit()){
            card.setCount(0);
            card.setEnabled(true);
            return new ApiResponse("Bankomatdan yechish mumkun bo'lgan max pul : "+bankomat.getMaxDebit(),false);
        }
        if (cardDto.getDebit()%1000!=0){
            card.setCount(0);
            card.setEnabled(true);
            return new ApiResponse("Bunday pul yechib bo'lmaydi ",false);
        }
        card.setCount(0);
        card.setEnabled(true);
        Double debit =card.getDebit();
        card.setDebit(debit-cardDto.getDebit());
        int s100000 = (int) (cardDto.getDebit()/100000);
        int s50000 = (int) ((cardDto.getDebit()-s100000*100000)/50000);
        int s10000 = (int) ((cardDto.getDebit()-s100000*100000-s50000*50000)/10000);
        int s5000 = (int) ((cardDto.getDebit()-s100000*100000-s50000*50000-s10000*10000)/5000);
        int s1000 = (int) ((cardDto.getDebit()-s100000*100000-s50000*50000-s10000*10000-s5000*5000)/1000);
        if (bankomat.getNumberOf100000()<s100000){
            s50000+=s100000/2;
        }
        if (bankomat.getNumberOf50000()<s50000){
            s10000+=s50000/5;
        }
        if (bankomat.getNumberOf10000()<s10000){
            s5000+=s10000/2;
        }
        int b1000= bankomat.getNumberOf1000();
        int b5000= bankomat.getNumberOf1000();
        int b10000= bankomat.getNumberOf1000();
        int b50000= bankomat.getNumberOf1000();
        int b100000= bankomat.getNumberOf1000();
        bankomat.setNumberOf10000(b1000-s1000);
        bankomat.setNumberOf50000(b5000-s5000);
        bankomat.setNumberOf100000(b10000-s10000);
        bankomat.setNumberOf50000(b50000-s50000);
        bankomat.setNumberOf100000(b100000-s100000);
        Double BDebit = bankomat.getDebit();
        bankomat.setDebit(BDebit-cardDto.getDebit());
        bankomatRepository.save(bankomat);
        if (bankomat.getDebit()<10000000){
            sendEMail(bankomat.getUser().getEmail());
        }
        BankomatHistory bankomatHistory = new BankomatHistory();
        bankomatHistory.setNumberOf1000(s1000);
        bankomatHistory.setNumberOf5000(s5000);
        bankomatHistory.setNumberOf10000(s10000);
        bankomatHistory.setNumberOf50000(s50000);
        bankomatHistory.setNumberOf100000(s100000);
        bankomatHistory.setCreatedBy(cardDto.getCardNumber());
        bankomatHistoryRepository.save(bankomatHistory);
        cardRepository.save(card);
        return new ApiResponse("Cartadan pul yechildi",true);


    }
    public  ApiResponse seeMoney(int id){
        Optional<Bankomat> optionalBankomat = bankomatRepository.findById(id);
        if (optionalBankomat.isEmpty()){
            return new ApiResponse("Bunday bankomat topilmadi",false);
        }
        return new ApiResponse("Bankomat haqida malumot : ",true,optionalBankomat.get());
    }
    public Boolean sendEMail(String sendingEmail){
        try {


            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("testpdp@mail.com");
            message.setTo(sendingEmail);
            message.setSubject("Bankomat");
            message.setText("Bankomatda pul 10mln dan oz qoldi ");
            javaMailSender.send(message);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}

