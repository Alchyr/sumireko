package sumireko.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.actions.general.HandSelectAction;
import sumireko.powers.UnnecessaryPower;
import sumireko.util.CardInfo;

import java.util.ArrayList;

import static sumireko.SumirekoMod.makeID;

public class OneIsEnough extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "OneIsEnough",
            0,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.UNCOMMON);

    public static final String ID = makeID(cardInfo.cardName);


    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;


    public OneIsEnough() {
        super(cardInfo, false);

        setMagic(MAGIC, UPG_MAGIC);
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        HandSelectAction callback = new HandSelectAction(1, (c)->DrawCardAction.drawnCards.contains(c), cards -> {
            ArrayList<AbstractCard> notChosen = new ArrayList<>(DrawCardAction.drawnCards);
            notChosen.removeAll(cards);

            addToTop(new ApplyPowerAction(p, p, new UnnecessaryPower(p, notChosen), -1));
        }, null, cardStrings.EXTENDED_DESCRIPTION[0], true, false, false);

        addToBot(new DrawCardAction(this.magicNumber, callback));
    }

    @Override
    public AbstractCard makeCopy() {
        return new OneIsEnough();
    }
}