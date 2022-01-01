/*package sumireko.cards.uncommon;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.SealCard;
import sumireko.enums.CustomCardTags;
import sumireko.util.CardInfo;
import sumireko.util.SealIntent;

import static sumireko.SumirekoMod.makeID;

public class EmptySeal extends SealCard {
    private final static CardInfo cardInfo = new CardInfo(
            "EmptySeal",
            0,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.UNCOMMON);

    public static final String ID = makeID(cardInfo.cardName);



    public EmptySeal() {
        super(cardInfo, false);

        tags.add(CustomCardTags.FINAL);
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        drawCards(1);
        super.use(p, m);
    }

    @Override
    public AbstractCard makeCopy() {
        return new EmptySeal();
    }

    @Override
    public void getIntent(SealIntent i) {
        i.intent = null;
    }
}*/