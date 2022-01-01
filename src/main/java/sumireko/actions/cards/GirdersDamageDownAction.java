package sumireko.actions.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;

import java.util.UUID;

public class GirdersDamageDownAction extends AbstractGameAction {
    private UUID uuid;

    public GirdersDamageDownAction(UUID targetUUID) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.uuid = targetUUID;
    }

    public void update() {
        for (AbstractCard c : GetAllInBattleInstances.get(this.uuid)) {
            c.baseDamage -= 1;
            //No floor.
        }

        this.isDone = true;
    }
}
