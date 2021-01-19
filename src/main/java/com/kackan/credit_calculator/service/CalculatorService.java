package com.kackan.credit_calculator.service;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;



@Service
public class CalculatorService {

    public double calculationCredit(double amount, int m, double percent)
    {
        BigDecimal p = BigDecimal.valueOf(percent/100);
        BigDecimal q=BigDecimal.ONE.add(p.divide(BigDecimal.valueOf(12),4, RoundingMode.HALF_UP));
        BigDecimal up= BigDecimal.valueOf(amount).multiply(q.pow(m)).multiply(q.subtract(BigDecimal.ONE));
        BigDecimal down=(q.pow(m)).subtract(BigDecimal.ONE);
        BigDecimal loan=up.divide(down,2, RoundingMode.HALF_UP);
        return loan.doubleValue();
    }

    public BigDecimal[] calculationCreditDecreasingLoanPayment(double amount, int quantityOfLoanPayment, double p){
        BigDecimal [] loanPayments= new BigDecimal[quantityOfLoanPayment];
        BigDecimal capital= (BigDecimal.valueOf(amount).divide(BigDecimal.valueOf(quantityOfLoanPayment),2,RoundingMode.HALF_UP));
        BigDecimal deduction=BigDecimal.ZERO;
        for(int i=0; i<loanPayments.length; i++){
            BigDecimal interest =calculationLoanPayment((amount-deduction.doubleValue()),p/100,12);
            loanPayments[i]=capital.add(interest);
            deduction=deduction.add(capital).add(interest);
        }

        return loanPayments;
    }

    public BigDecimal calculationLoanPayment(double amount, double p, int m){
        return BigDecimal.valueOf(amount).multiply(BigDecimal.valueOf(p/m)).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calculationProvision(double amount, double p){
        return BigDecimal.valueOf(amount).multiply(BigDecimal.valueOf(p/100)).setScale(2,RoundingMode.HALF_UP);
    }

    public double sumOfDecreasingInstalments(BigDecimal[]arr, BigDecimal provision){
        BigDecimal sum=BigDecimal.ZERO;

        for(BigDecimal a: arr){
            sum=sum.add(a);
        }
        sum=sum.add(provision);
        return sum.doubleValue();
    }

    public double sumOfInstalments(double instalment, int months, BigDecimal provision){
        return BigDecimal.valueOf(instalment*months).add(provision).doubleValue();
    }

}
