package sumireko.cards.common;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.actions.EscapeArtistAction;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class EscapeArtist extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "EscapeArtist",
            1,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.COMMON);
    // skill

    public static final String ID = makeID(cardInfo.cardName);

    private static final int UPG_COST = 0;


    public EscapeArtist() {
        super(cardInfo, false);

        setCostUpgrade(UPG_COST);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(1, new EscapeArtistAction()));
    }

    @Override
    public AbstractCard makeCopy() {
        return new EscapeArtist();
    }
}