package net.socialgamer.cah.handlers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.undertow.server.HttpServerExchange;
import net.socialgamer.cah.Constants.AjaxOperation;
import net.socialgamer.cah.Constants.AjaxRequest;
import net.socialgamer.cah.Constants.ErrorCode;
import net.socialgamer.cah.data.Game;
import net.socialgamer.cah.data.Game.TooManySpectatorsException;
import net.socialgamer.cah.data.GameManager;
import net.socialgamer.cah.data.User;
import net.socialgamer.cah.servlets.Annotations;
import net.socialgamer.cah.servlets.BaseCahHandler;
import net.socialgamer.cah.servlets.Parameters;

public class SpectateGameHandler extends GameHandler {
    public static final String OP = AjaxOperation.SPECTATE_GAME.toString();

    public SpectateGameHandler(@Annotations.GameManager GameManager gameManager) {
        super(gameManager);
    }

    @Override
    public JsonElement handle(User user, Game game, Parameters params, HttpServerExchange exchange) throws BaseCahHandler.CahException {
        if (!game.isPasswordCorrect(params.get(AjaxRequest.PASSWORD)))
            throw new BaseCahHandler.CahException(ErrorCode.WRONG_PASSWORD);

        try {
            game.addSpectator(user);
        } catch (IllegalStateException ex) {
            throw new BaseCahHandler.CahException(ErrorCode.CANNOT_JOIN_ANOTHER_GAME, ex);
        } catch (TooManySpectatorsException ex) {
            throw new BaseCahHandler.CahException(ErrorCode.GAME_FULL, ex);
        }

        return new JsonObject();
    }
}
