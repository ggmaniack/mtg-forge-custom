package forge.game.mulligan;

import forge.game.card.Card;
import forge.game.player.Player;

public class QuickFightMulligan extends AbstractMulligan {
    public QuickFightMulligan(Player p, boolean firstMullFree) {
        super(p, firstMullFree);
    }

    @Override
    public void keep() {
        super.timesMulliganed = 2;
        super.mulligan();
    }

    @Override
    public boolean canMulligan() {
        return !kept && timesMulliganed < player.getMaxHandSize();
    }

    @Override
    public int handSizeAfterNextMulligan() {
        return player.getMaxHandSize();
    }

    @Override
    public void mulliganDraw() {
        player.drawCards(handSizeAfterNextMulligan());
        int tuckingCards = tuckCardsAfterKeepHand();

        for (final Card c : player.getController().londonMulliganReturnCards(player, tuckingCards)) {
            player.getGame().getAction().moveToLibrary(c, -1, null);
        }
    }

    @Override
    public int tuckCardsAfterKeepHand() {
        if (timesMulliganed == 0) {
            return 0;
        }

        int extraCard = firstMulliganFree ? 1 : 0;
        return timesMulliganed - extraCard;
    }
}
