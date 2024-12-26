package handler;

import dao.UserDao;
import dto.UserDto;
import org.bson.Document;
import request.ParsedRequest;
import response.HttpResponseBuilder;

public class DeleteUserHandler implements BaseHandler {
    @Override
    public HttpResponseBuilder handleRequest(ParsedRequest request) {
        UserDao userDao = UserDao.getInstance();
        AuthFilter.AuthResult authResult = AuthFilter.doFilter(request);

        // Validate the authResult
        if (authResult == null || authResult.userName == null || !authResult.isLoggedIn) {
            return new HttpResponseBuilder()
                    .setStatus(StatusCodes.UNAUTHORIZED)
                    .setBody("Unauthorized access");
        }

        // Fetch the user and validate existence
        var users = userDao.query(new Document().append("userName", authResult.userName));
        if (users.isEmpty()) {
            return new HttpResponseBuilder()
                    .setStatus(StatusCodes.NOT_FOUND)
                    .setBody("User not found");
        }

        // Delete the user
        userDao.deleteUser(authResult.userName, null);

        return new HttpResponseBuilder()
                .setStatus(StatusCodes.OK)
                .setBody("User Deleted Successfully");
    }
}
