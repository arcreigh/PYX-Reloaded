package net.socialgamer.cah.handlers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.undertow.server.HttpServerExchange;
import net.socialgamer.cah.Constants.AjaxOperation;
import net.socialgamer.cah.Constants.AjaxRequest;
import net.socialgamer.cah.Constants.ErrorCode;
import net.socialgamer.cah.data.Game;
import net.socialgamer.cah.data.GameManager;
import net.socialgamer.cah.data.User;
import net.socialgamer.cah.servlets.Annotations;
import net.socialgamer.cah.servlets.BaseCahHandler;
import net.socialgamer.cah.servlets.Parameters;
import org.apache.commons.lang3.StringEscapeUtils;

public class PlayCardHandler extends GameWithPlayerHandler {
    public static final String OP = AjaxOperation.PLAY_CARD.toString();

    public PlayCardHandler(@Annotations.GameManager GameManager gameManager) {
        super(gameManager);
    }

    @Override
    public JsonElement handleWithUserInGame(User user, Game game, Parameters params, HttpServerExchange exchange) throws BaseCahHandler.CahException {
        String cardIdStr = params.get(AjaxRequest.CARD_ID);
        if (cardIdStr == null || cardIdStr.isEmpty())
            throw new BaseCahHandler.CahException(ErrorCode.NO_CARD_SPECIFIED);

        int cardId;
        try {
            cardId = Integer.parseInt(cardIdStr);
        } catch (NumberFormatException ex) {
            throw new BaseCahHandler.CahException(ErrorCode.INVALID_CARD, ex);
        }

        String text = params.get(AjaxRequest.MESSAGE);
        if (text != null && text.contains("<")) text = StringEscapeUtils.escapeXml11(text);

        game.playCard(user, cardId, text);
        return new JsonObject();
    }
}
