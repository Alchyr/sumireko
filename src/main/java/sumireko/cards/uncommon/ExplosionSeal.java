package sumireko.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.SealCard;
import sumireko.util.CardInfo;
import sumireko.util.HealthBarRender;
import sumireko.util.PretendMonster;
import sumireko.util.SealIntent;

import java.util.ArrayList;
import java.util.Map;

import static sumireko.SumirekoMod.makeID;

public class ExplosionSeal extends SealCard {
    private final static CardInfo cardInfo = new CardInfo(
            "ExplosionSeal",
            1,
            CardType.SKILL,
            CardTarget.ALL_ENEMY,
            CardRarity.UNCOMMON);
    // skill

    public static final String ID = makeID(cardInfo.cardName);


    private static final int SEAL = 10;
    private static final int UPG_SEAL = 3;

    public ExplosionSeal() {
        super(cardInfo, false);

        setSeal(SEAL, UPG_SEAL);
    }

    @Override
    public ArrayList<AbstractGameAction> triggerSealEffect(AbstractMonster target) {
        ArrayList<AbstractGameAction> actions = new ArrayList<>();
        if (this.sealValue > 0)
            actions.add(getDamageAll(this.sealValue, DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));

        return actions;
    }

    @Override
    public HealthBarRender instantSealEffect(PretendMonster target, Map<AbstractMonster, PretendMonster> pretendMonsters) {
        if (this.sealValue > 0)
        {
            for (Map.Entry<AbstractMonster, PretendMonster> monster : pretendMonsters.entrySet())
            {
                monster.getValue().sealDamage(new DamageInfo(AbstractDungeon.player, this.sealValue, DamageInfo.DamageType.THORNS), this);
            }
        }
        return null;
    }

    @Override
    public void centerMultiplier() {
        multiplySealValue(2);
    }

    @Override
    public void getIntent(SealIntent i) {
        i.baseDamage(this.sealValue);
    }

    @Override
    public AbstractCard makeCopy() {
        return new ExplosionSeal();
    }
}