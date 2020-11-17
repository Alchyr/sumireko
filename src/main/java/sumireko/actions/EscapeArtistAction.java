package sumireko.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import sumireko.enums.CustomCardTags;
import sumireko.patches.occult.OccultFields;

public class EscapeArtistAction extends AbstractGameAction {
    public EscapeArtistAction() {
        this.actionType = ActionType.WAIT;
    }

    public void update() {
        for (AbstractCard c : DrawCardAction.drawnCards) {
            if (!c.canUse(AbstractDungeon.player, null) && !c.hasTag(CustomCardTags.FINAL))
            {
                OccultFields.isOccult.set(c, true);
                c.initializeDescription();
                c.superFlash(Color.PURPLE);
            }
        }

        this.isDone = true;
    }
}
