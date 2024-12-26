package handler;

import dao.TransactionDao;
import dao.UserDao;
import dto.TransactionDto;
import dto.TransactionType;
import dto.UserDto;
import org.bson.Document;
import request.ParsedRequest;
import response.HttpResponseBuilder;
import response.RestApiAppResponse;

import java.util.List;

public class CreateDepositHandler implements BaseHandler {

  @Override
  public HttpResponseBuilder handleRequest(ParsedRequest request) {
    TransactionDto transactionDto = GsonTool.GSON.fromJson(request.getBody(), TransactionDto.class);
    TransactionDao transactionDao = TransactionDao.getInstance();
    UserDao userDao = UserDao.getInstance();
    AuthFilter.AuthResult authResult = AuthFilter.doFilter(request);
    if (!authResult.isLoggedIn) {
      return new HttpResponseBuilder().setStatus(StatusCodes.UNAUTHORIZED);
    } else {
      UserDto user = userDao.query(new Document("userName", authResult.userName)).get(0);
      transactionDto.setTransactionType(TransactionType.Deposit);
      transactionDto.setUserId(authResult.userName);
      transactionDao.put(transactionDto);
      user.setBalance(user.getBalance() + transactionDto.getAmount());
      userDao.updateOne(new Document("userName", user.getUserName()), new Document("$set", new Document("balance", user.getBalance())));
      RestApiAppResponse<TransactionDto> res = new RestApiAppResponse(true, List.of(transactionDto), (String) null);
      return (new HttpResponseBuilder()).setStatus(StatusCodes.OK).setBody(res);
    }
  }
}
