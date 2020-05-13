package sumireko.cards.uncommon;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import sumireko.abstracts.SealCard;
import sumireko.actions.LoseStrengthThisTurnAction;
import sumireko.enums.CustomCardTags;
import sumireko.util.CardInfo;
import sumireko.util.PretendMonster;

import java.util.Map;

import static sumireko.SumirekoMod.makeID;

public class RestrainingSeal extends SealCard {
    private final static CardInfo cardInfo = new CardInfo(
            "RestrainingSeal",
            0,
            CardType.SKILL,
            CardTarget.ALL_ENEMY,
            CardRarity.UNCOMMON);
    // skill

    public static final String ID = makeID(cardInfo.cardName);


    private static final int SEAL = 4;
    private static final int UPG_SEAL = 2;

    public RestrainingSeal() {
        super(cardInfo, false);

        setSeal(SEAL, UPG_SEAL);

        tags.add(CustomCardTags.FRAGILE_SEAL);
    }

    @Override
    public void triggerSealEffect(AbstractMonster target) {
        if (this.sealValue > 0)
        {
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                addToBot(new LoseStrengthThisTurnAction(mo, AbstractDungeon.player, this.sealValue));
            }
        }
    }

    @Override
    public void instantSealEffect(PretendMonster target, Map<AbstractMonster, PretendMonster> pretendMonsters) {
        if (this.sealValue > 0)
        {
            for (Map.Entry<AbstractMonster, PretendMonster> m : pretendMonsters.entrySet()) {
                pretendApplyPower(m.getValue(), new StrengthPower(m.getValue(), -this.sealValue), -this.sealValue);
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new RestrainingSeal();
    }
}