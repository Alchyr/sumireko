package sumireko.actions.seals;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import sumireko.SealSystem;

public class ReturnSealAction extends AbstractGameAction {
    private AbstractCard c;
    private int index;
    private boolean isCenterCard;

    public ReturnSealAction(AbstractCard c, int index)
    {
        this.c = c;
        this.index = index;
        this.isCenterCard = false;

        this.actionType = ActionType.DAMAGE; //to avoid being cleared by clearPostCombatActions
    }
    public ReturnSealAction(AbstractCard c, boolean isCenterCard)
    {
        this.c = c;
        this.index = -1;
        this.isCenterCard = isCenterCard;

        this.actionType = ActionType.DAMAGE; //to avoid being cleared by clearPostCombatActions
    }

    @Override
    public void update() {
        if (isCenterCard)
        {
            SealSystem.centerCard = null;
            SealSystem.sealIntents[4].refresh();
        }
        else if (index >= 0 && index < 4)
        {
            SealSystem.aroundCards[index] = null;
            SealSystem.sealIntents[index].refresh();
        }

        AbstractDungeon.player.hand.addToHand(c);
        //c.retain = true;
        SealSystem.nextIndex -= 1;

        this.isDone = true;
    }
}