package sumireko.cards.uncommon;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.SealCard;
import sumireko.actions.RandomOccultCardsAction;
import sumireko.enums.CustomCardTags;
import sumireko.util.CardInfo;
import sumireko.util.PretendMonster;

import java.util.Map;

import static sumireko.SumirekoMod.makeID;

public class ReleaseSeal extends SealCard {
    private final static CardInfo cardInfo = new CardInfo(
            "ReleaseSeal",
            1,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.UNCOMMON);
    // skill

    public static final String ID = makeID(cardInfo.cardName);


    private static final int SEAL = 1;
    private static final int UPG_SEAL = 1;

    public ReleaseSeal() {
        super(cardInfo, false);

        setSeal(SEAL, UPG_SEAL);

        tags.add(CustomCardTags.FRAGILE_SEAL);
    }

    @Override
    public void triggerSealEffect(AbstractMonster target) {
        if (this.sealValue > 0)
            addToBot(new RandomOccultCardsAction(this.sealValue));
    }

    @Override
    public void instantSealEffect(PretendMonster target, Map<AbstractMonster, PretendMonster> pretendMonsters) {

    }

    @Override
    public AbstractCard makeCopy() {
        return new ReleaseSeal();
    }
}