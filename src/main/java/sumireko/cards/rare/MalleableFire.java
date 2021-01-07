package sumireko.cards.rare;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.actions.general.XCostAction;
import sumireko.cards.colorless.MoldedFire;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class MalleableFire extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "MalleableFire",
            -1,
            CardType.ATTACK,
            CardTarget.ENEMY,
            CardRarity.RARE);

    public static final String ID = makeID(cardInfo.cardName);


    private static final int DAMAGE = 5;


    public MalleableFire() {
        super(cardInfo, true);

        setDamage(DAMAGE);
        setExhaust(true);
        this.cardsToPreview = new MoldedFire();
    }

    @Override
    public void upgrade() {
        super.upgrade();
        this.cardsToPreview.upgrade();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new XCostAction(this,
                (x, params)->{
                    MoldedFire fire = new MoldedFire(x);
                    for (int i = 0; i < params[0]; ++i)
                        if (fire.canUpgrade()) //no clue how infinite journal works, but maybe this'll work :)
                            fire.upgrade();

                    addToTop(new MakeTempCardInDrawPileAction(fire, 1, true, true));

                    for (int i = 0; i < x; ++i)
                    {
                        addToTop(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.FIRE, true));
                    }
                    return true;
                }, timesUpgraded));
    }

    @Override
    public AbstractCard makeCopy() {
        return new MalleableFire();
    }
}