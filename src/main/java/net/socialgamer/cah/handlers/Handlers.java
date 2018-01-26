package net.socialgamer.cah.handlers;

import java.util.HashMap;
import java.util.Map;

public class Handlers {
    public final static Map<String, Class<? extends BaseHandler>> LIST;

    static {
        LIST = new HashMap<>();
        LIST.put(BanHandler.OP, BanHandler.class);
        LIST.put(CardcastAddCardsetHandler.OP, CardcastAddCardsetHandler.class);
        LIST.put(CardcastListCardsetsHandler.OP, CardcastListCardsetsHandler.class);
        LIST.put(CardcastRemoveCardsetHandler.OP, CardcastRemoveCardsetHandler.class);
        LIST.put(ChangeGameOptionHandler.OP, ChangeGameOptionHandler.class);
        LIST.put(ChatHandler.OP, ChatHandler.class);
        LIST.put(CreateGameHandler.OP, CreateGameHandler.class);
        LIST.put(FirstLoadHandler.OP, FirstLoadHandler.class);
        LIST.put(GameChatHandler.OP, GameChatHandler.class);
        LIST.put(GameListHandler.OP, GameListHandler.class);
        LIST.put(GetCardsHandler.OP, GetCardsHandler.class);
        LIST.put(GetGameInfoHandler.OP, GetGameInfoHandler.class);
        LIST.put(JoinGameHandler.OP, JoinGameHandler.class);
        LIST.put(JudgeSelectHandler.OP, JudgeSelectHandler.class);
        LIST.put(KickHandler.OP, KickHandler.class);
        LIST.put(LeaveGameHandler.OP, LeaveGameHandler.class);
        LIST.put(LogoutHandler.OP, LogoutHandler.class);
        LIST.put(NamesHandler.OP, NamesHandler.class);
        LIST.put(PlayCardHandler.OP, PlayCardHandler.class);
        LIST.put(RegisterHandler.OP, RegisterHandler.class);
        LIST.put(ScoreHandler.OP, ScoreHandler.class);
        LIST.put(SpectateGameHandler.OP, SpectateGameHandler.class);
        LIST.put(StartGameHandler.OP, StartGameHandler.class);
        LIST.put(StopGameHandler.OP, StopGameHandler.class);
        LIST.put(LikeGameHandler.OP, LikeGameHandler.class);
        LIST.put(DislikeGameHandler.OP, DislikeGameHandler.class);
        LIST.put(GetMeHandler.OP, GetMeHandler.class);
    }
}
