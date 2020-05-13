package sumireko.cards.basic;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.SealCard;
import sumireko.enums.CustomCardTags;
import sumireko.patches.occult.OccultFields;
import sumireko.util.CardInfo;
import sumireko.util.PretendMonster;

import java.util.Map;

import static sumireko.SumirekoMod.makeID;

public class OccultSeal extends SealCard {
    private final static CardInfo cardInfo = new CardInfo(
            "OccultSeal",
            1,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.BASIC);
    // skill

    public static final String ID = makeID(cardInfo.cardName);


    private static final int SEAL = 2;
    private static final int UPG_SEAL = 2;

    public OccultSeal() {
        super(cardInfo, false);

        OccultFields.isOccult.set(this, true);
        tags.add(CustomCardTags.BUFF_SEAL);
        setSeal(SEAL, UPG_SEAL);
    }

    @Override
    public AbstractCard makeCopy() {
        return new OccultSeal();
    }

    @Override
    public void applyBaseAdjacencyEffect(SealCard c) {
        c.modifySealValue(this.baseSealValue);
    }

    @Override
    public void applyAdjacencyEffect(SealCard c) {
        c.modifySealValue(this.sealValue);
    }

    @Override
    public void triggerSealEffect(AbstractMonster target) {
    }

    @Override
    public void instantSealEffect(PretendMonster target, Map<AbstractMonster, PretendMonster> pretendMonsters) {
    }
}