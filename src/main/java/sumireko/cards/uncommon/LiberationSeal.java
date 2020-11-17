package sumireko.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.SealCard;
import sumireko.actions.RandomOccultCardsAction;
import sumireko.enums.CustomCardTags;
import sumireko.util.CardInfo;
import sumireko.util.PretendMonster;
import sumireko.util.SealIntent;

import java.util.Map;

import static sumireko.SumirekoMod.makeID;

public class LiberationSeal extends SealCard {
    private final static CardInfo cardInfo = new CardInfo(
            "LiberationSeal",
            0,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.UNCOMMON);
    // skill

    public static final String ID = makeID(cardInfo.cardName);


    private static final int SEAL = 2;
    private static final int UPG_SEAL = 1;

    public LiberationSeal() {
        super(cardInfo, false);

        setSeal(SEAL, UPG_SEAL);

        tags.add(CustomCardTags.FRAGILE_SEAL);
    }

    @Override
    public void triggerSealEffect(AbstractMonster target) {
        if (this.sealValue > 0)
            addToBot(new RandomOccultCardsAction(this.sealValue));

        addToBot(new AbstractGameAction() {
            @Override
            public void update() {

            }
        });
    }

    @Override
    public void instantSealEffect(PretendMonster target, Map<AbstractMonster, PretendMonster> pretendMonsters) {

    }

    @Override
    public void getIntent(SealIntent i) {
        i.intent = AbstractMonster.Intent.UNKNOWN;
    }

    @Override
    public AbstractCard makeCopy() {
        return new LiberationSeal();
    }
}