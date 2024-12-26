package handler;

import dao.AuthDao;
import dao.UserDao;
import dto.AuthDto;
import java.time.Instant;
import java.util.Map;

import dto.UserDto;
import org.apache.commons.codec.digest.DigestUtils;
import org.bson.Document;
import org.bson.types.ObjectId;
import request.ParsedRequest;
import response.CustomHttpResponse;
import response.HttpResponseBuilder;

class LoginDto{
  String userName;
  String password;
}

public class LoginHandler implements BaseHandler{

  @Override
  public HttpResponseBuilder handleRequest(ParsedRequest request) {
    LoginDto dto = (LoginDto) GsonTool.GSON.fromJson(request.getBody(), LoginDto.class);

    UserDto userDto = UserDao.getInstance()
            .query(new Document().append("userName", dto.userName).append("password", dto.password))
            .get(0);

    if (userDto != null){

      AuthDto authDto = new AuthDto();
      authDto.setExpireTime(Instant.now().getEpochSecond() + 60000L);
      String hash = DigestUtils.sha256Hex(dto.userName + authDto.getExpireTime());
      authDto.setHash(hash);
      authDto.setUserName(dto.userName);

      AuthDao.getInstance().put(authDto);
      return new HttpResponseBuilder()
              .setBody(userDto.toDocument().toString())
              .setStatus(StatusCodes.OK)
              .setHeader("Set-Cookie","auth=" + DigestUtils.sha256Hex(dto.userName + authDto.getExpireTime()));
    } else {
      return new HttpResponseBuilder()
              .setStatus(StatusCodes.SERVER_ERROR);
    }
  }
}
