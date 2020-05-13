package sumireko.cards.common;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.SealCard;
import sumireko.util.CardInfo;
import sumireko.util.PretendMonster;

import java.util.Map;

import static sumireko.SumirekoMod.makeID;

public class RepressionSeal extends SealCard {
    private final static CardInfo cardInfo = new CardInfo(
            "RepressionSeal",
            0,
            CardType.SKILL,
            CardTarget.ENEMY,
            CardRarity.COMMON);
    // skill

    public static final String ID = makeID(cardInfo.cardName);


    private static final int SEAL = 1;

    public RepressionSeal() {
        super(cardInfo, true);

        setSeal(SEAL);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded)
            drawCards(1);

        super.use(p, m);
    }

    @Override
    public void triggerSealEffect(AbstractMonster target) {
        if (this.sealValue > 0)
            applySingle(target, getWeak(target, this.sealValue));
    }

    @Override
    public void instantSealEffect(PretendMonster target, Map<AbstractMonster, PretendMonster> pretendMonsters) {
        if (target != null && this.sealValue > 0)
            pretendApplyPower(target, getWeak(target, this.sealValue), this.sealValue);
    }

    @Override
    public AbstractCard makeCopy() {
        return new RepressionSeal();
    }
}