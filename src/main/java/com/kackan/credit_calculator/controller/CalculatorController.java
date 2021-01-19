package com.kackan.credit_calculator.controller;
import com.kackan.credit_calculator.service.CalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.math.BigDecimal;
import java.util.Map;

@Controller
public class CalculatorController {

    public CalculatorService calculatorService;

    @Autowired
    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @GetMapping
    public String welcomePage()
    {
        return "index";
    }

    @PostMapping("/calculator")
    public String getCalculator(@RequestParam Map<String,String> params, Model model)
    {
        double amount = Double.parseDouble(params.get("amount"));
        int months = Integer.parseInt(params.get("month"));
        double percent=Double.parseDouble(params.get("percent"));
        double provisionPercent=Double.parseDouble(params.get("provisionPercent"));
        double payment = calculatorService.calculationCredit(amount, months, percent);
        BigDecimal provision=calculatorService.calculationProvision(amount,provisionPercent);
        BigDecimal[] payments = calculatorService.calculationCreditDecreasingLoanPayment(amount, months, percent);
        double sumOfDecreasingInstalments=calculatorService.sumOfDecreasingInstalments(payments,provision);
        double sumOfInstalments=calculatorService.sumOfInstalments(payment,months,provision);

        model.addAttribute("payment",payment);
        model.addAttribute("payments",payments);
        model.addAttribute("provision",provision.doubleValue());
        model.addAttribute("sumOfDecreasingInstalments",sumOfDecreasingInstalments);
        model.addAttribute("sumOfInstalments",sumOfInstalments);
        model.addAttribute("amount",amount);



        return "calculator";
    }
}
