package handler;

import dao.UserDao;
import dto.UserDto;
import org.apache.commons.codec.digest.DigestUtils;
import org.bson.Document;
import request.ParsedRequest;
import response.HttpResponseBuilder;
import response.RestApiAppResponse;

public class CreateUserHandler implements BaseHandler{

  @Override
  public HttpResponseBuilder handleRequest(ParsedRequest request) {
    Document userDoc = Document.parse(request.getBody());
    userDoc.replace("password", DigestUtils.sha256Hex(userDoc.getString("password")));

    RestApiAppResponse res;

    if (!UserDao.getInstance().query(new Document().append("userName", userDoc.getString("userName"))).isEmpty()){
      res = new RestApiAppResponse(false, null, "Username already taken");
    } else  {
      UserDao.getInstance().put(UserDto.fromDocument(userDoc));
      res = new RestApiAppResponse(true, null, "User Created");
    }
    return new HttpResponseBuilder()
            .setStatus(StatusCodes.OK)
            .setBody(res);
  }
}
