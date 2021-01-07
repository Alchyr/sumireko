package sumireko.cards.common;

import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.abstracts.SealCard;
import sumireko.actions.general.HandSelectAction;
import sumireko.actions.seals.TriggerSealRandomAction;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class Toss extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Toss",
            0,
            CardType.SKILL,
            CardTarget.SELF,
            CardRarity.COMMON);

    public static final String ID = makeID(cardInfo.cardName);

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("DiscardAction");


    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;


    public Toss() {
        super(cardInfo, false);

        setMagic(MAGIC, UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new HandSelectAction(this.magicNumber, (c)->true, (cards) -> {
            for (AbstractCard c : cards)
            {
                addToTop(new DiscardSpecificCardAction(c, AbstractDungeon.player.hand));
                //looks gross

                if (c instanceof SealCard)
                {
                    addToTop(new TriggerSealRandomAction((SealCard) c));
                }
            }
        }, null, uiStrings.TEXT[0], false, true, true));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Toss();
    }
}