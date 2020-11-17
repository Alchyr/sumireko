package sumireko.util.mysteryupgrades;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.cards.rare.MysterySeal;
import sumireko.util.MysteryUpgrade;

public class DrawCardUpgrade extends MysteryUpgrade {
    private static final int MAGIC_UPGRADE = 2;

    public DrawCardUpgrade()
    {
        super(69, false, false);
    }

    @Override
    public void apply(MysterySeal c) {
        c.baseMagicNumber += MAGIC_UPGRADE;
        c.upgradedMagicNumber = true;
}

    @Override
    public void use(MysterySeal c, AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(c.magicNumber));
    }


    @Override
    public void addNormalEffect(StringBuilder description) {
        description.append(TEXT[9]);
    }
}
