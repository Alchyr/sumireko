/*package sumireko.util.mysteryupgrades;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.cards.rare.MysterySeal;
import sumireko.util.MysteryUpgrade;

public class BlockUpgrade extends MysteryUpgrade {
    private static final int BLOCK_UPGRADE = 7;

    public BlockUpgrade()
    {
        super(-420, false);
    }

    @Override
    public void apply(MysterySeal c) {
        if (c.target == AbstractCard.CardTarget.NONE)
        {
            c.target = AbstractCard.CardTarget.SELF;
        }
        else if (c.target == AbstractCard.CardTarget.ENEMY)
        {
            c.target = AbstractCard.CardTarget.SELF_AND_ENEMY;
        }
        else if (c.target == AbstractCard.CardTarget.ALL_ENEMY)
        {
            c.target = AbstractCard.CardTarget.ALL;
        }

        c.baseBlock += BLOCK_UPGRADE;
        c.block = c.baseBlock;
        c.upgradedBlock = true;
    }

    @Override
    public void use(MysterySeal c, AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, c.block));
    }

    @Override
    public void addNormalEffect(StringBuilder description) {
        description.insert(0, TEXT[1]);
    }
}*/
