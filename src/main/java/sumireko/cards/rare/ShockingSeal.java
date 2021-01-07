package sumireko.cards.rare;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.SealCard;
import sumireko.actions.general.DamageRandomConditionalEnemyAction;
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

    private static final int SEAL = 3;

    public ShockingSeal() {
        super(cardInfo, true);

        setSeal(SEAL);

        tags.add(CustomCardTags.FRAGILE_SEAL);
    }

    @Override
    public void upgrade() {
        super.upgrade();
        tags.remove(CustomCardTags.FRAGILE_SEAL);
    }

    @Override
    public void triggerSealEffect(AbstractMonster target) {
        for (int i = 0; i < this.sealValue; ++i)
        {
            addToBot(new DamageRandomConditionalEnemyAction((m)->true, new DamageInfo(AbstractDungeon.player, this.sealValue, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.LIGHTNING, false));
        }
    }

    @Override
    public HealthBarRender instantSealEffect(PretendMonster target, Map<AbstractMonster, PretendMonster> pretendMonsters) {
        if (this.sealValue > 0 && pretendMonsters.size() == 1)
        {
            for (Map.Entry<AbstractMonster, PretendMonster> monster : pretendMonsters.entrySet())
            {
                //there should be only one. Otherwise, the damage is not predictable.
                for (int i = 0; i < this.sealValue; ++i)
                {
                    monster.getValue().damage(new DamageInfo(AbstractDungeon.player, this.sealValue, DamageInfo.DamageType.THORNS));
                }
            }
        }
        return null;
    }

    @Override
    public void getIntent(SealIntent i) {
        i.intent = AbstractMonster.Intent.ATTACK;
        i.multihit(this.sealValue);
    }

    @Override
    public AbstractCard makeCopy() {
        return new ShockingSeal();
    }
}