package sumireko.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
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

public class FrostSeal extends SealCard {
    private final static CardInfo cardInfo = new CardInfo(
            "FrostSeal",
            1,
            CardType.SKILL,
            CardTarget.ALL,
            CardRarity.COMMON);

    public static final String ID = makeID(cardInfo.cardName);


    private static final int SEAL = 5;
    private static final int UPG_SEAL = 2;

    public FrostSeal() {
        super(cardInfo, false);

        setSeal(SEAL, UPG_SEAL);
    }

    @Override
    public ArrayList<AbstractGameAction> triggerSealEffect(AbstractMonster target) {
        ArrayList<AbstractGameAction> actions = new ArrayList<>();
        if (this.sealValue > 0)
        {
            actions.add(getBlockAction(this.sealValue));
            //TODO: Frost attack effect using frost orbs atlas regions or something
            actions.add(getDamageAll(this.sealValue, DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.NONE));
        }

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
    public void getIntent(SealIntent i) {
        i.baseDamage(this.sealValue);
        i.addIntent(SealIntent.DEFEND);
    }

    @Override
    public int getSealBlock() {
        return sealValue;
    }

    @Override
    public AbstractCard makeCopy() {
        return new FrostSeal();
    }
}