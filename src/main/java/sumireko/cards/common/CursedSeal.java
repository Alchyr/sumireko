package sumireko.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.SealSystem;
import sumireko.abstracts.BaseCard;
import sumireko.abstracts.SealCard;
import sumireko.actions.AllEnemyLoseHPAction;
import sumireko.util.CardInfo;
import sumireko.util.PretendMonster;

import java.util.Map;

import static sumireko.SumirekoMod.makeID;

public class CursedSeal extends SealCard {
    private final static CardInfo cardInfo = new CardInfo(
            "CursedSeal",
            1,
            CardType.SKILL,
            CardTarget.ALL_ENEMY,
            CardRarity.COMMON);
    // skill

    public static final String ID = makeID(cardInfo.cardName);


    private static final int MAGIC = 12;
    private static final int UPG_MAGIC = 4;


    public CursedSeal() {
        super(cardInfo, false);

        setMagic(MAGIC, UPG_MAGIC);
        setSeal(0, 0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AllEnemyLoseHPAction(p, this.magicNumber, AbstractGameAction.AttackEffect.POISON));
        super.use(p, m);
    }

    @Override
    public void applyFinalBaseAdjacencyEffect(SealCard c) { //Seals that negate other seals are not "buff" seals
        //Buff seals will be modified first, including by non-buff seals. So, this will negate all neighboring buff seals.
        c.sealValue = 0;
        if (c.sealValue != c.baseSealValue)
            c.isSealModified = true;
    }
    @Override
    public void applyFinalAdjacencyEffect(SealCard c) { //Seals that negate other seals are not "buff" seals
        //Buff seals will be modified first, including by non-buff seals. So, this will negate all neighboring buff seals.
        c.sealValue = 0;
        if (c.sealValue != c.baseSealValue)
            c.isSealModified = true;
    }

    @Override
    public void triggerSealEffect(AbstractMonster target) {
    }

    @Override
    public void instantSealEffect(PretendMonster target, Map<AbstractMonster, PretendMonster> pretendMonsters) {
    }

    @Override
    public AbstractCard makeCopy() {
        return new CursedSeal();
    }
}