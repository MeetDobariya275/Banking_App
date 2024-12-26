package handler;

import dao.TransactionDao;
import dao.UserDao;
import dto.TransactionDto;
import org.bson.Document;
import request.ParsedRequest;
import response.HttpResponseBuilder;
import response.RestApiAppResponse;

import java.util.List;
import java.util.ArrayList;

public class TransactionHistoryHandler implements BaseHandler {

    @Override
    public HttpResponseBuilder handleRequest(ParsedRequest request) {
        TransactionDao transactionDao = TransactionDao.getInstance();

        // Authenticate user
        AuthFilter.AuthResult authResult = AuthFilter.doFilter(request);
        if (!authResult.isLoggedIn) {
            return new HttpResponseBuilder()
                    .setStatus(StatusCodes.UNAUTHORIZED)
                    .setBody(new RestApiAppResponse<>(false, null, "Unauthorized access"));
        }

        try {
            String userName = authResult.userName;

            // Filters for different transaction types
            Document sentFilter = new Document("userId", userName); // Transactions sent (Transfers/Withdraw)
            Document receivedFilter = new Document("toId", userName); // Transfers received
            Document depositFilter = new Document("transactionType", "Deposit")
                    .append("userId", userName); // Deposits for the user

            // Query transactions
            List<TransactionDto> sentTransactions = transactionDao.query(sentFilter);
            List<TransactionDto> receivedTransactions = transactionDao.query(receivedFilter);
            List<TransactionDto> depositTransactions = transactionDao.query(depositFilter);

            // Combine all transactions
            List<TransactionDto> allTransactions = new ArrayList<>();
            allTransactions.addAll(sentTransactions);
            allTransactions.addAll(receivedTransactions);
            allTransactions.addAll(depositTransactions);

            // Return response
            return new HttpResponseBuilder()
                    .setStatus(StatusCodes.OK)
                    .setBody(new RestApiAppResponse<>(true, allTransactions, "Transaction history retrieved successfully"));
        } catch (Exception e) {
            return new HttpResponseBuilder()
                    .setStatus(StatusCodes.SERVER_ERROR)
                    .setBody(new RestApiAppResponse<>(false, null, "Error retrieving transaction history: " + e.getMessage()));
        }
    }
}
