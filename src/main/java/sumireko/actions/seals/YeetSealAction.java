package sumireko.actions.seals;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.ShowCardAndPoofAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import sumireko.SealSystem;

public class YeetSealAction extends AbstractGameAction {
    private AbstractCard c;
    private int index;
    private boolean isCenterCard;

    public YeetSealAction(AbstractCard c, int index)
    {
        this.c = c;
        this.index = index;
        this.isCenterCard = false;

        this.actionType = ActionType.DAMAGE; //to avoid being cleared by clearPostCombatActions
    }
    public YeetSealAction(AbstractCard c, boolean isCenterCard)
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
            SealSystem.sealIntents[4].refresh();
        }

        addToTop(new ShowCardAndPoofAction(this.c));
        SealSystem.nextIndex -= 1;

        this.isDone = true;
    }
}