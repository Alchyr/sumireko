package sumireko.actions.general;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import sumireko.effects.CardFadeEffect;

public class PurgeHandAction extends AbstractGameAction {
    public PurgeHandAction()
    {

    }

    @Override
    public void update() {
        for (AbstractCard c : AbstractDungeon.player.hand.group)
        {
            AbstractDungeon.effectList.add(new CardFadeEffect(c));
        }
        AbstractDungeon.player.hand.clear();

        this.isDone = true;
    }
}
