package net.socialgamer.cah.handlers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.inject.Inject;
import fi.iki.elonen.NanoHTTPD;
import net.socialgamer.cah.Constants.AjaxOperation;
import net.socialgamer.cah.Constants.AjaxRequest;
import net.socialgamer.cah.Constants.ErrorCode;
import net.socialgamer.cah.data.Game;
import net.socialgamer.cah.data.Game.TooManySpectatorsException;
import net.socialgamer.cah.data.GameManager;
import net.socialgamer.cah.data.User;
import net.socialgamer.cah.servlets.CahResponder;
import net.socialgamer.cah.servlets.Parameters;

public class SpectateGameHandler extends GameHandler {
    public static final String OP = AjaxOperation.SPECTATE_GAME.toString();

    @Inject
    public SpectateGameHandler(final GameManager gameManager) {
        super(gameManager);
    }

    @Override
    public JsonElement handle(User user, Game game, Parameters params, NanoHTTPD.IHTTPSession session) throws CahResponder.CahException {
        if (!game.isPasswordCorrect(params.getFirst(AjaxRequest.PASSWORD)))
            throw new CahResponder.CahException(ErrorCode.WRONG_PASSWORD);

        try {
            game.addSpectator(user);
        } catch (IllegalStateException ex) {
            throw new CahResponder.CahException(ErrorCode.CANNOT_JOIN_ANOTHER_GAME, ex);
        } catch (TooManySpectatorsException ex) {
            throw new CahResponder.CahException(ErrorCode.GAME_FULL, ex);
        }

        return new JsonObject();
    }
}
