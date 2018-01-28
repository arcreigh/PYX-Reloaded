package net.socialgamer.cah.handlers;

import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.CookieImpl;
import net.socialgamer.cah.Constants;
import net.socialgamer.cah.Constants.AjaxOperation;
import net.socialgamer.cah.Constants.AjaxRequest;
import net.socialgamer.cah.Constants.AjaxResponse;
import net.socialgamer.cah.Constants.ErrorCode;
import net.socialgamer.cah.JsonWrapper;
import net.socialgamer.cah.data.ConnectedUsers;
import net.socialgamer.cah.data.User;
import net.socialgamer.cah.servlets.*;

import java.util.regex.Pattern;

public class RegisterHandler extends BaseHandler {
    public static final String OP = AjaxOperation.REGISTER.toString();
    private static final String VALID_NAME_PATTERN = "[a-zA-Z_][a-zA-Z0-9_]{2,29}";
    private final ConnectedUsers users;

    public RegisterHandler(@Annotations.ConnectedUsers ConnectedUsers users) {
        this.users = users;
    }

    @Override
    public JsonWrapper handle(User user, Parameters params, HttpServerExchange exchange) throws BaseJsonHandler.StatusException {
        if (BanList.contains(exchange.getHostName())) throw new BaseCahHandler.CahException(ErrorCode.BANNED);

        String nickname = params.get(AjaxRequest.NICKNAME);
        if (nickname == null) throw new BaseCahHandler.CahException(ErrorCode.NO_NICK_SPECIFIED);
        if (!Pattern.matches(VALID_NAME_PATTERN, nickname))
            throw new BaseCahHandler.CahException(ErrorCode.INVALID_NICK);
        if (nickname.equalsIgnoreCase("xyzzy"))
            throw new BaseCahHandler.CahException(ErrorCode.RESERVED_NICK);

        boolean admin;
        String adminToken = params.get(Constants.AjaxRequest.ADMIN_TOKEN);
        admin = adminToken != null && adminToken.length() == AdminToken.TOKEN_LENGTH && AdminToken.current().equals(adminToken);

        user = new User(nickname, exchange.getHostName(), Sessions.generateNewId(), admin);
        users.checkAndAdd(user);
        exchange.setResponseCookie(new CookieImpl("PYX-Session", Sessions.add(user)));

        return new JsonWrapper()
                .add(AjaxResponse.NICKNAME, nickname)
                .add(AjaxResponse.IS_ADMIN, admin);
    }
}
