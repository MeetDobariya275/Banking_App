package handler;

import request.ParsedRequest;

public class HandlerFactory {
  // routes based on the path. Add your custom handlers here
  public static BaseHandler getHandler(ParsedRequest request) {
      System.out.println("Received request for: " + request.getPath());
      return switch (request.getPath()) {
          case "/createUser" -> new CreateUserHandler();
          case "/deleteUser" -> new DeleteUserHandler();
          case "/login" -> new LoginHandler();
          case "/getTransactions" -> new GetTransactionsHandler();
          case "/createDeposit" -> new CreateDepositHandler();
          case "/withdraw" -> new WithdrawHandler();
          case  "/transfer" -> new TransferHandler();
          case "/fixedDeposit" -> new FixedDepositHandler();
          case "/getTransactionHistory" -> new TransactionHistoryHandler();
          case "/currencyConversion" -> new CurrencyConversionHandler();
          default -> new FallbackHandler();
      };

  }
}
