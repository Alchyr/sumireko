package sumireko.cards.colorless;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.SealCard;
import sumireko.actions.general.DamageRandomConditionalEnemyAction;
import sumireko.cards.rare.ShockingSeal;
import sumireko.enums.CustomCardTags;
import sumireko.util.CardInfo;
import sumireko.util.HealthBarRender;
import sumireko.util.PretendMonster;
import sumireko.util.SealIntent;

import java.util.ArrayList;
import java.util.Map;

import static sumireko.SumirekoMod.makeID;

public class StoneSeal extends SealCard {
    private final static CardInfo cardInfo = new CardInfo(
            "StoneSeal",
            0,
            CardType.SKILL,
            CardTarget.ENEMY,
            CardRarity.SPECIAL);

    public static final String ID = makeID(cardInfo.cardName);


    private static final int SEAL = 8;
    private static final int UPG_SEAL = 3;

    public StoneSeal() {
        super(cardInfo, false, CardColor.COLORLESS);

        setSeal(SEAL, UPG_SEAL);
        selfRetain = true;

        tags.add(CustomCardTags.FRAGILE_SEAL);
    }

    /*@Override
    public ArrayList<AbstractGameAction> triggerSealEffect(AbstractMonster target) {
        ArrayList<AbstractGameAction> actions = new ArrayList<>();
        actions.add(new DamageRandomConditionalEnemyAction((m)->true, new DamageInfo(AbstractDungeon.player, this.sealValue, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.BLUNT_HEAVY, false));

        return actions;
    }

    @Override
    public HealthBarRender instantSealEffect(PretendMonster target, Map<AbstractMonster, PretendMonster> pretendMonsters) {
        if (this.sealValue > 0 && pretendMonsters.size() == 1)
        {
            //there should be only one. Otherwise, the damage is not predictable.
            for (Map.Entry<AbstractMonster, PretendMonster> monster : pretendMonsters.entrySet())
            {
                monster.getValue().sealDamage(new DamageInfo(AbstractDungeon.player, this.sealValue, DamageInfo.DamageType.THORNS), this);
            }
        }
        return null;
    }*/

    @Override
    public ArrayList<AbstractGameAction> triggerSealEffect(AbstractMonster target) {
        ArrayList<AbstractGameAction> actions = new ArrayList<>();

        if (this.sealValue > 0)
            actions.add(getDamageSingle(target, this.sealValue, DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.BLUNT_HEAVY));

        return actions;
    }

    @Override
    public HealthBarRender instantSealEffect(PretendMonster target, Map<AbstractMonster, PretendMonster> pretendMonsters) {
        if (target != null)
            if (this.sealValue > 0)
                target.sealDamage(new DamageInfo(AbstractDungeon.player, this.sealValue, DamageInfo.DamageType.THORNS), this);

        return null;
    }

    @Override
    public void getIntent(SealIntent i) {
        i.baseDamage(this.sealValue);
    }

    @Override
    public AbstractCard makeCopy() {
        return new StoneSeal();
    }
}