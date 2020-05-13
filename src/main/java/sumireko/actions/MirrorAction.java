package sumireko.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import sumireko.SealSystem;
import sumireko.abstracts.SealCard;
import sumireko.cards.rare.MirrorSeal;
import sumireko.effects.UltraFlashVfx;
import sumireko.util.KeywordWithProper;

public class MirrorAction extends AbstractGameAction {
    private MirrorSeal s;

    public MirrorAction(MirrorSeal s)
    {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.s = s;
    }

    @Override
    public void update() {
        if (SealSystem.nextIndex != 0)
        {
            s.flashVfx = new UltraFlashVfx(s, Color.WHITE.cpy());
            SealCard toCopy = SealSystem.aroundCards[SealSystem.nextIndex - 1];
            if (toCopy instanceof MirrorSeal)
            {
                toCopy = ((MirrorSeal) toCopy).copying;
            }
            SealCard copy = (SealCard)toCopy.makeStatEquivalentCopy();

            copy.rawDescription += KeywordWithProper.fragile;
            copy.initializeDescription();

            s.copying = copy;
        }

        this.isDone = true;
    }
}
