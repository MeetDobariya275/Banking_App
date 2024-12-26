package handler;

import dao.TransactionDao;
import dao.UserDao;
import dto.*;
import org.bson.Document;
import request.ParsedRequest;
import response.HttpResponseBuilder;
import response.RestApiAppResponse;

import java.util.List;

public class TransferHandler implements BaseHandler {

    @Override
    public HttpResponseBuilder handleRequest(ParsedRequest request) {
        UserDao userDao = UserDao.getInstance();
        TransferRequestDto transferRequestDto = GsonTool.GSON.fromJson(request.getBody(), TransferRequestDto.class);
        AuthFilter.AuthResult authResult = AuthFilter.doFilter(request);

        if (!authResult.isLoggedIn){
            return new HttpResponseBuilder().setStatus(StatusCodes.UNAUTHORIZED);
        } else {
            UserDto sender = userDao.query(new Document("userName", authResult.userName)).get(0);

            if (sender == null) {
                RestApiAppResponse<BaseDto> response = new RestApiAppResponse<>(false, null, "Invalid sender");
                return new HttpResponseBuilder().setStatus(StatusCodes.SERVER_ERROR).setBody(response);
            } else {
                UserDto receiver = userDao.query(new Document("userName", transferRequestDto.toId.replaceAll("\\s",""))).isEmpty() ? null :
                        userDao.query(new Document("userName", transferRequestDto.toId.replaceAll("\\s",""))).get(0) ;

                if (receiver == null) {
                    RestApiAppResponse<BaseDto> response = new RestApiAppResponse<>(false, null, "Invalid receiver");
                    return new HttpResponseBuilder().setStatus(StatusCodes.SERVER_ERROR).setBody(response);
                } else {
                    if (Math.ceil(sender.getBalance()) < transferRequestDto.amount) {
                        RestApiAppResponse<BaseDto> response = new RestApiAppResponse<>(false, null, "Insufficient balance");
                        return new HttpResponseBuilder().setStatus(StatusCodes.SERVER_ERROR).setBody(response);
                    } else {
                        sender.setBalance(sender.getBalance() - transferRequestDto.amount);
                        receiver.setBalance(receiver.getBalance() + transferRequestDto.amount);
                        userDao.updateOne(new Document("userName", sender.getUserName()),
                                new Document("$set", new Document("balance", sender.getBalance() < 0 ? 0 : sender.getBalance())));
                        userDao.updateOne(new Document("userName", receiver.getUserName()),
                                new Document("$set", new Document("balance", receiver.getBalance())));
                        TransactionDao transactionDao = TransactionDao.getInstance();
                        TransactionDto transaction = new TransactionDto();
                        transaction.setTransactionType(TransactionType.Transfer);
                        transaction.setAmount(transferRequestDto.amount);
                        transaction.setToId(transferRequestDto.toId);
                        transaction.setUserId(authResult.userName);
                        transactionDao.put(transaction);
                        RestApiAppResponse<UserDto> res = new RestApiAppResponse<>(true, List.of(sender, receiver), "Transferred to "+ receiver.getUserName());
                        return (new HttpResponseBuilder()).setStatus(StatusCodes.OK).setBody(res);
                    }
                }

            }
        }
    }
}