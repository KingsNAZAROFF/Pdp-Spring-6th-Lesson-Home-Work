package uz.pdp.pdpspring6thlessonhomework.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.pdpspring6thlessonhomework.payload.ApiResponse;
import uz.pdp.pdpspring6thlessonhomework.payload.BankomatDto;
import uz.pdp.pdpspring6thlessonhomework.payload.CardDto;
import uz.pdp.pdpspring6thlessonhomework.service.BankomatService;

@RestController
@RequestMapping("/api/bankomat")
public class BankomatController {

    @Autowired
    BankomatService bankomatService;


    @PostMapping("/addBankomat")
    public ResponseEntity<?> addBankomat(BankomatDto bankomatDto){
        ApiResponse response = bankomatService.addBankomat(bankomatDto);
        return ResponseEntity.status(response.isSuccess()?200:409).body(response);
    }
    @PostMapping("/addMoney")
    public ResponseEntity<?> addMoney(BankomatDto bankomatDto){
        ApiResponse response = bankomatService.addMoney(bankomatDto);
        return ResponseEntity.status(response.isSuccess()?200:409).body(response);
    }
    @PostMapping("/takeMoney")
    public ResponseEntity<?> takeMoney(CardDto cardDto){
        ApiResponse response = bankomatService.takeMoney(cardDto);
        return ResponseEntity.status(response.isSuccess()?200:409).body(response);
    }
    @GetMapping("/details/{id}")
    public ResponseEntity<?> seeMoney(@PathVariable int id){
        ApiResponse response = bankomatService.seeMoney(id);
        return ResponseEntity.status(response.isSuccess()?200:409).body(response);
    }
}
