package sumireko.cards.rare;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.SealCard;
import sumireko.actions.DamageRandomConditionalEnemyAction;
import sumireko.enums.CustomCardTags;
import sumireko.util.CardInfo;
import sumireko.util.HealthBarRender;
import sumireko.util.PretendMonster;
import sumireko.util.SealIntent;

import java.util.Map;

import static sumireko.SumirekoMod.makeID;

public class ShockingSeal extends SealCard {
    private final static CardInfo cardInfo = new CardInfo(
            "ShockingSeal",
            1,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.RARE);
    // skill

    public static final String ID = makeID(cardInfo.cardName);

    private static final int UPG_COST = 0;

    private static final int SEAL = 1;

    public ShockingSeal() {
        super(cardInfo, false);

        setCostUpgrade(UPG_COST);
        setSeal(SEAL);

        tags.add(CustomCardTags.FRAGILE_SEAL);
    }

    @Override
    public void triggerSealEffect(AbstractMonster target) {
        if (this.sealValue > 0)
        {
            // :)
            addToBot(new DamageRandomConditionalEnemyAction((m)->true, new DamageInfo(AbstractDungeon.player, this.sealValue, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.LIGHTNING, false));
            addToBot(new DamageRandomConditionalEnemyAction((m)->true, new DamageInfo(AbstractDungeon.player, this.sealValue, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.LIGHTNING, false));
            addToBot(new DamageRandomConditionalEnemyAction((m)->true, new DamageInfo(AbstractDungeon.player, this.sealValue, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.LIGHTNING, false));
            addToBot(new DamageRandomConditionalEnemyAction((m)->true, new DamageInfo(AbstractDungeon.player, this.sealValue, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.LIGHTNING, false));
            addToBot(new DamageRandomConditionalEnemyAction((m)->true, new DamageInfo(AbstractDungeon.player, this.sealValue, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.LIGHTNING, false));
            addToBot(new DamageRandomConditionalEnemyAction((m)->true, new DamageInfo(AbstractDungeon.player, this.sealValue, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.LIGHTNING, false));
            addToBot(new DamageRandomConditionalEnemyAction((m)->true, new DamageInfo(AbstractDungeon.player, this.sealValue, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.LIGHTNING, false));
        }
    }

    @Override
    public HealthBarRender instantSealEffect(PretendMonster target, Map<AbstractMonster, PretendMonster> pretendMonsters) {
        if (this.sealValue > 0)
        {
            if (pretendMonsters.size() == 1)
            {
                for (Map.Entry<AbstractMonster, PretendMonster> monster : pretendMonsters.entrySet())
                {
                    //this should be the only one.
                    monster.getValue().damage(new DamageInfo(AbstractDungeon.player, this.sealValue, DamageInfo.DamageType.THORNS));
                    monster.getValue().damage(new DamageInfo(AbstractDungeon.player, this.sealValue, DamageInfo.DamageType.THORNS));
                    monster.getValue().damage(new DamageInfo(AbstractDungeon.player, this.sealValue, DamageInfo.DamageType.THORNS));
                    monster.getValue().damage(new DamageInfo(AbstractDungeon.player, this.sealValue, DamageInfo.DamageType.THORNS));
                    monster.getValue().damage(new DamageInfo(AbstractDungeon.player, this.sealValue, DamageInfo.DamageType.THORNS));
                    monster.getValue().damage(new DamageInfo(AbstractDungeon.player, this.sealValue, DamageInfo.DamageType.THORNS));
                    monster.getValue().damage(new DamageInfo(AbstractDungeon.player, this.sealValue, DamageInfo.DamageType.THORNS));
                }
            }
        }
        return null;
    }

    @Override
    public void getIntent(SealIntent i) {
        i.intent = AbstractMonster.Intent.ATTACK;
        i.multihit(7);
    }

    @Override
    public AbstractCard makeCopy() {
        return new ShockingSeal();
    }
}