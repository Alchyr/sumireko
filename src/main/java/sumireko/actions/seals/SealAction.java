package sumireko.actions.seals;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
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

        SealSystem.addSeal(seal, this.m);
        seal.flash(Color.VIOLET);

        if (seal.purgeOnUse)
        {
            seal.tags.add(CustomCardTags.ULTRA_FRAGILE_SEAL);
            seal.triggerOnGlowCheck();
        }
        this.isDone = true;
    }
}
