package sumireko.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.SealSystem;
import sumireko.abstracts.SealCard;
import sumireko.cards.uncommon.MirrorSeal;
import sumireko.effects.UltraFlashVfx;
import sumireko.enums.CustomCardTags;
import sumireko.util.KeywordWithProper;

public class MirrorAction extends AbstractGameAction {
    private MirrorSeal s;
    public AbstractMonster m;

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
            m = SealSystem.targets.get(toCopy); //The target, is used by SealAction.
            if (toCopy instanceof MirrorSeal)
            {
                toCopy = ((MirrorSeal) toCopy).copying;
            }

            if (toCopy != null)
            {
                SealCard copy = (SealCard)toCopy.makeStatEquivalentCopy();

                if (!s.upgraded && !copy.hasTag(CustomCardTags.FRAGILE_SEAL))
                {
                    copy.tags.add(CustomCardTags.FRAGILE_SEAL);
                    copy.rawDescription += KeywordWithProper.fragile;
                    copy.initializeDescription();
                }

                s.copying = copy;
                s.cardID = copy.cardID;
            }
        }

        this.isDone = true;
    }
}
