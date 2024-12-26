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

public class WithdrawHandler  implements BaseHandler {

    @Override
    public HttpResponseBuilder handleRequest(ParsedRequest request) {
        AuthFilter.AuthResult authResult = AuthFilter.doFilter(request);
        TransactionDto transactionDto = GsonTool.GSON.fromJson(request.getBody(), TransactionDto.class);
        TransactionDao transactionDao = TransactionDao.getInstance();
        UserDao userDao = UserDao.getInstance();

        if (!authResult.isLoggedIn) {
            return new HttpResponseBuilder().setStatus(StatusCodes.UNAUTHORIZED);
        } else {
            UserDto user = userDao.query(new Document("userName", authResult.userName)).get(0);
            transactionDto.setTransactionType(TransactionType.Withdraw);
            transactionDto.setUserId(authResult.userName);

            RestApiAppResponse res;
            if (Math.ceil(user.getBalance())>= transactionDto.getAmount()){
                user.setBalance(user.getBalance() - transactionDto.getAmount());
                userDao.updateOne(new Document("userName", user.getUserName()),
                        new Document("$set", new Document("balance", user.getBalance() < 0 ? 0 : user.getBalance())));
                transactionDao.put(transactionDto);
                res = new RestApiAppResponse<>(true, List.of(transactionDto), "Withdrawal Successful!");
            } else {
                res = new RestApiAppResponse<>(false, null, "Not Enough Balance!");
            }
            return new HttpResponseBuilder().setStatus(StatusCodes.OK).setBody(res);
        }
    }
}
