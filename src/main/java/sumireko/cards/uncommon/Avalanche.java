package sumireko.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.actions.general.XCostAction;
import sumireko.actions.seals.SealAction;
import sumireko.cards.colorless.StoneSeal;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class Avalanche extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Avalanche",
            -1,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.UNCOMMON);

    public static final String ID = makeID(cardInfo.cardName);


    public Avalanche() {
        super(cardInfo, true);

        cardsToPreview = new StoneSeal();
    }

    @Override
    public void upgrade() {
        super.upgrade();

        cardsToPreview.upgrade();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new XCostAction(this,
                (x, params)->{
                    StoneSeal copy = new StoneSeal();

                    for (int i = 0; i < params[0]; ++i)
                        copy.upgrade();

                    addToTop(new MakeTempCardInHandAction(copy, x));

                    /*for (int i = 0; i < x; ++i)
                    {
                        copy = new StoneSeal();
                        copy.target_x = copy.current_x = Settings.WIDTH / 2.0f;
                        copy.target_y = copy.current_y = Settings.HEIGHT / 2.0f;

                        p.limbo.addToTop(copy);

                        addToTop(new SealAction(copy, (AbstractMonster) null));
                    }*/
                    return true;
                }, this.timesUpgraded));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Avalanche();
    }
}