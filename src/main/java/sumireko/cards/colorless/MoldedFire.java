package sumireko.cards.colorless;

import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.evacipated.cardcrawl.modthespire.lib.SpireSuper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import sumireko.SumirekoMod;
import sumireko.abstracts.BaseCard;
import sumireko.patches.occult.OccultFields;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class MoldedFire extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "MoldedFire",
            -1,
            CardType.ATTACK,
            CardTarget.ENEMY,
            CardRarity.SPECIAL);

    public static final String ID = makeID(cardInfo.cardName);


    private static final int DAMAGE = 8;
    private static final int UPG_DAMAGE = 4;


    public MoldedFire() {
        super(cardInfo, false, CardColor.COLORLESS);

        setDamage(DAMAGE, UPG_DAMAGE);

        OccultFields.isOccult.set(this, true);
    }

    public MoldedFire(int cost) {
        super(cardInfo.cardName, cost, cardInfo.cardType, cardInfo.cardTarget, cardInfo.cardRarity, false, BaseCard.COLOR);

        setDamage(DAMAGE, UPG_DAMAGE);

        OccultFields.isOccult.set(this, true);
    }

    @SpireOverride
    protected String getCost()
    {
        if (this.cost == -1) {
            return "?";
        } else {
            return SpireSuper.call();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int gap = this.cost - EnergyPanel.totalCount; //energy is spent after the use method is called.
        for (int i = 0; i < gap; ++i)
        {
            damageSingle(m, AbstractGameAction.AttackEffect.NONE, true);
            addToBot(new VFXAction(p, new InflameEffect(p), 0.2F));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new MoldedFire();
    }
}