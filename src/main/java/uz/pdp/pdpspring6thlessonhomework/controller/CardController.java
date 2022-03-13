package uz.pdp.pdpspring6thlessonhomework.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.pdpspring6thlessonhomework.payload.AddCardDto;
import uz.pdp.pdpspring6thlessonhomework.payload.ApiResponse;
import uz.pdp.pdpspring6thlessonhomework.payload.UnblockCardDto;
import uz.pdp.pdpspring6thlessonhomework.service.CardService;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    @Autowired
    CardService cardService;
    @PostMapping("/uzCard")
    public ResponseEntity<?> addCardUzcard(@RequestBody AddCardDto cardDto){
        ApiResponse response = cardService.addCardUzcard(cardDto);
        return ResponseEntity.status(response.isSuccess()?200:409).body(response);
    }
    @PostMapping("/Visa")
    public ResponseEntity<?> addCardVisa(@RequestBody  AddCardDto cardDto){
        ApiResponse response = cardService.addCardVisa(cardDto);
        return ResponseEntity.status(response.isSuccess()?200:409).body(response);
    }
    @PostMapping("/Humo")
    public ResponseEntity<?> addCardHumo(@RequestBody AddCardDto cardDto){
        ApiResponse response = cardService.addCardHumo(cardDto);
        return ResponseEntity.status(response.isSuccess()?200:409).body(response);
    }
    @PatchMapping("/unblock")
    public ResponseEntity<?> unblock(@RequestBody  UnblockCardDto unblockCardDto){
        ApiResponse response = cardService.unblockCard(unblockCardDto);
        return ResponseEntity.status(response.isSuccess()?200:409).body(response);

    }
}
