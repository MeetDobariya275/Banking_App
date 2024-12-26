package handler;

import com.google.gson.JsonObject;
import dto.CurrencyConversionDto;
import request.ParsedRequest;
import response.HttpResponseBuilder;
import response.RestApiAppResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Handles currency conversion requests by converting a given USD amount
 * into various predefined currencies based on fixed conversion rates.
 */
public class CurrencyConversionHandler implements BaseHandler {

    /**
     * Processes the incoming request to perform currency conversion.
     *
     * @param request ParsedRequest containing the input data.
     * @return HttpResponseBuilder object with the conversion results or an error response.
     */

    @Override
    public HttpResponseBuilder handleRequest(ParsedRequest request) {
        try {
            // Parse input value (USD) from the request body
            JsonObject input = GsonTool.GSON.fromJson(request.getBody(), JsonObject.class);
          
            // Check if the input JSON contains the required "amount" field
            if (!input.has("amount")) {
                return new HttpResponseBuilder().setStatus(StatusCodes.NOT_FOUND)
                        .setBody(new RestApiAppResponse<>(false, null, "Input amount is required."));
            }

            // Extract the amount in USD from the request body
            double usdAmount = input.get("amount").getAsDouble();

            // Define conversion rates (fixed rates)
            Map<String, Double> conversionRates = Map.of(
                    "INR", 83.00,
                    "GBP", 0.79,
                    "EUR", 0.92,
                    "CNY", 7.12
            );

            // Perform conversions conversions
            Map<String, String> convertedValues = new HashMap<>();
            for (Map.Entry<String, Double> rate : conversionRates.entrySet()) {
                // Convert the USD amount to the target currency using the conversion rate
                double convertedAmount = usdAmount * rate.getValue();
                // Format the converted value to two decimal places and store in the result map
                convertedValues.put(rate.getKey(), String.format("%.2f", convertedAmount));
            }
            
            // Add the original USD amount to the result map for reference
            convertedValues.put("USD", String.format("%.2f", usdAmount)); // Include original USD amount

            // Wrap in DTO
            CurrencyConversionDto dto = new CurrencyConversionDto(convertedValues);

            // Build response
            RestApiAppResponse<CurrencyConversionDto> response = new RestApiAppResponse<>(
                    true,
                    List.of(dto),  // Wrap DTO in a list to maintain consistency with response structure
                    "Currency conversion successful."
            );

            // Return the success response with HTTP status
            return new HttpResponseBuilder().setStatus(StatusCodes.OK).setBody(response);

        } catch (Exception e) {
            // Handle any exceptions and return a server error response
            return new HttpResponseBuilder().setStatus(StatusCodes.SERVER_ERROR)
                    .setBody(new RestApiAppResponse<>(false, null, "Invalid input or server error."));
        }
    }
}
