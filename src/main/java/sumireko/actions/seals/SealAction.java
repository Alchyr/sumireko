package sumireko.actions.seals;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import sumireko.SealSystem;
import sumireko.abstracts.SealCard;
import sumireko.enums.CustomCardTags;

public class SealAction extends AbstractGameAction {
    private SealCard seal;
    private MirrorAction source;
    private AbstractMonster m;

    public SealAction(SealCard c, AbstractMonster target)
    {
        this.source = null;
        this.m = target;
        this.seal = c;
    }
    public SealAction(SealCard c, MirrorAction source)
    {
        this.source = source;
        this.m = null;
        this.seal = c;
    }

    @Override
    public void update() {
        if (this.source != null)
        {
            this.m = this.source.m;
        }

        if (SealSystem.addSeal(seal, this.m)) {
            seal.flash(Color.VIOLET.cpy());
            seal.beginGlowing();
            AbstractDungeon.player.limbo.removeCard(seal); //ensure it doesn't stay in limbo if it comes from limbo
        }
        else {
            seal.flash(Color.RED.cpy());
            if (seal.hasTag(CustomCardTags.FRAGILE_SEAL)) {
                AbstractDungeon.effectList.add(new ExhaustCardEffect(seal));
                AbstractDungeon.player.limbo.moveToExhaustPile(seal);
            }
            else {
                AbstractDungeon.player.limbo.removeCard(seal);
            }
        }

        if (seal.purgeOnUse)
        {
            seal.tags.add(CustomCardTags.ULTRA_FRAGILE_SEAL);
            seal.triggerOnGlowCheck();
        }
        this.isDone = true;
    }
}
