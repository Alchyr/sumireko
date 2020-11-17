package sumireko.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.SealSystem;
import sumireko.abstracts.SealCard;
import sumireko.util.CardInfo;
import sumireko.util.PretendMonster;
import sumireko.util.SealIntent;

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


    private static final int SEAL = 6;
    private static final int UPG_SEAL = 2;

    public ExplosionSeal() {
        super(cardInfo, false);

        setSeal(SEAL, UPG_SEAL);
    }

    @Override
    public void applyFinalBaseAdjacencyEffect(SealCard c) { //Seals that negate other seals are not "buff" seals
        //Buff seals will be modified first, including by non-buff seals. So, this will negate all neighboring buff seals.
        c.negateSeal();
    }
    @Override
    public void applyFinalAdjacencyEffect(SealCard c) { //Seals that negate other seals are not "buff" seals
        //Buff seals will be modified first, including by non-buff seals. So, this will negate all neighboring buff seals.
        c.negateSeal();
    }

    @Override
    public void triggerSealEffect(AbstractMonster target) {
        if (this.sealValue > 0)
            for (int i = 0; i < SealSystem.nextIndex; ++i)
                damageAll(this.sealValue, DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE);
    }

    @Override
    public void instantSealEffect(PretendMonster target, Map<AbstractMonster, PretendMonster> pretendMonsters) {
        if (this.sealValue > 0)
        {
            for (Map.Entry<AbstractMonster, PretendMonster> monster : pretendMonsters.entrySet())
            {
                monster.getValue().damage(new DamageInfo(AbstractDungeon.player, this.sealValue, DamageInfo.DamageType.THORNS));
            }
        }
    }

    @Override
    public void getIntent(SealIntent i) {
        i.amount = -1;
        i.intent = AbstractMonster.Intent.MAGIC;
    }

    @Override
    public AbstractCard makeCopy() {
        return new ExplosionSeal();
    }
}