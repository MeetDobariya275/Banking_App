package handler;

import dao.TransactionDao;
import dto.TransactionDto;
import handler.AuthFilter.AuthResult;
import org.bson.Document;
import request.ParsedRequest;
import response.HttpResponseBuilder;
import response.RestApiAppResponse;

import java.util.ArrayList;
import java.util.List;

public class GetTransactionsHandler implements BaseHandler {

  @Override
  public HttpResponseBuilder handleRequest(ParsedRequest request) {
    TransactionDao transactionDao = TransactionDao.getInstance();
    AuthFilter.AuthResult authResult = AuthFilter.doFilter(request);

    if (!authResult.isLoggedIn){
      return new HttpResponseBuilder().setStatus(StatusCodes.UNAUTHORIZED);
    } else {
      List<Document> filterList = new ArrayList();
      filterList.add(new Document("userId", authResult.userName));
      filterList.add(new Document("toId", authResult.userName));
      Document orFilter = new Document("$or", filterList);
      RestApiAppResponse<TransactionDto> res = new RestApiAppResponse(true, transactionDao.query(orFilter), (String)null);
      return new HttpResponseBuilder().setStatus(StatusCodes.OK).setBody(res);
    }
  }
}
