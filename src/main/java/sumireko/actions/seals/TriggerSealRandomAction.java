package sumireko.actions.seals;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.SealCard;

public class TriggerSealRandomAction extends AbstractGameAction {
    private SealCard c;

    public TriggerSealRandomAction(SealCard c)
    {
        this.c = c;
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    @Override
    public void update() {
        c.superFlash(Color.GOLD.cpy());

        AbstractMonster target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        c.triggerSealEffect(target);

        this.isDone = true;
    }
}
