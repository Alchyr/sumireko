package sumireko.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.SealSystem;
import sumireko.abstracts.SealCard;
import sumireko.enums.CustomCardTags;

public class SealAction extends AbstractGameAction {
    private SealCard seal;
    private AbstractMonster m;

    public SealAction(SealCard c, AbstractMonster target)
    {
        this.m = target;
        this.seal = c;
    }

    @Override
    public void update() {
        SealSystem.addSeal(seal, this.m);
        seal.flash(Color.VIOLET);

        if (seal.purgeOnUse)
        {
            seal.tags.add(CustomCardTags.ULTRA_FRAGILE_SEAL);
        }
        this.isDone = true;
    }
}
