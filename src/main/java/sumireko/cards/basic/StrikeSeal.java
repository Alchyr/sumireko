package sumireko.cards.basic;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.SealCard;
import sumireko.actions.SealAction;
import sumireko.util.CardInfo;
import sumireko.util.PretendMonster;

import java.util.Map;

import static sumireko.SumirekoMod.makeID;

public class StrikeSeal extends SealCard {
    private final static CardInfo cardInfo = new CardInfo(
            "StrikeSeal",
            1,
            CardType.SKILL,
            CardTarget.ENEMY,
            CardRarity.BASIC);
    // skill

    public static final String ID = makeID(cardInfo.cardName);


    private static final int SEAL = 5;
    private static final int UPG_SEAL = 2;

    public StrikeSeal() {
        super(cardInfo, false);

        setSeal(SEAL, UPG_SEAL);
    }

    @Override
    public AbstractCard makeCopy() {
        return new StrikeSeal();
    }

    @Override
    public void triggerSealEffect(AbstractMonster target) {
        if (this.sealValue > 0)
            damageSingle(target, this.sealValue, DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE);
    }

    @Override
    public void instantSealEffect(PretendMonster target, Map<AbstractMonster, PretendMonster> pretendMonsters) {
        if (target != null)
            if (this.sealValue > 0)
                target.damage(new DamageInfo(AbstractDungeon.player, this.sealValue, DamageInfo.DamageType.THORNS));
    }
}