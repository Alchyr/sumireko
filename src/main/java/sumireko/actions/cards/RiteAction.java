package sumireko.actions.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import sumireko.SealSystem;

public class RiteAction extends AbstractGameAction {
    private final AbstractCard c;

    public RiteAction(AbstractCard c)
    {
        this.actionType = ActionType.DAMAGE;
        this.amount = SealSystem.nextIndex;
        this.c = c;
    }

    @Override
    public void update() {
        for (int i = 0; i < this.amount; ++i)
            addToTop(new AttackDamageRandomEnemyAction(c, AttackEffect.LIGHTNING));

        this.isDone = true;
    }
}
