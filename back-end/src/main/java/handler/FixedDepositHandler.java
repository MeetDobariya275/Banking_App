package handler;

import dao.FixedDepositDao;
import dto.FixedDepositDto;
import request.ParsedRequest;
import response.HttpResponseBuilder;

public class FixedDepositHandler implements BaseHandler {

    @Override
    public HttpResponseBuilder handleRequest(ParsedRequest request) {
        FixedDepositDao fdDao = FixedDepositDao.getInstance();

        try {
            // Parse request body to create FixedDepositDto
            FixedDepositDto fdDetails = FixedDepositDto.fromRequest(request);

            //Validation: Amount & DurationYears >0
            if (fdDetails.getAmount() > 0 && fdDetails.getDurationYears() > 0) {
                //Set interest rate = years
                int years = fdDetails.getDurationYears();
                double interestRate = (double) years;
                fdDetails.setInterestRate(interestRate);

                //Calculate CompoundInterest maturity amount
                double maturityAmount = calculateCompoundInterest(
                        fdDetails.getAmount(), years, interestRate
                );
                fdDetails.setMaturityAmount(maturityAmount);

                //Save details in database
                fdDao.createFixedDeposit(fdDetails);

                // Return success response with calculated MaturityAmount
                return new HttpResponseBuilder()
                        .setStatus("200 OK")
                        .setBody(String.format("Fixed Deposit Created Successfully. Maturity Amount: %.2f", maturityAmount));
            } else {
                //Handles invalid input
                return new HttpResponseBuilder()
                        .setStatus("400 Bad Request")
                        .setBody("Invalid deposit amount or duration");
            }
        } catch (Exception e) {
            // Logs error
            System.err.println("Error in FixedDepositHandler: " + e.getMessage());
            e.printStackTrace();

            //Return the response of error
            return new HttpResponseBuilder()
                    .setStatus("500 Internal Server Error")
                    .setBody("An error occurred while processing the request.");
        }
    }

    // Calculate CompoundInterest for n years
    private double calculateCompoundInterest(double principal, int years, double rate) {
        return principal * Math.pow(1 + rate / 100, years);
    }
}
