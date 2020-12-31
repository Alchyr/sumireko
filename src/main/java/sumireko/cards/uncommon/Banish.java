package sumireko.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import sumireko.abstracts.BaseCard;
import sumireko.actions.HandSelectAction;
import sumireko.util.CardInfo;

import java.util.ArrayList;
import java.util.function.Consumer;

import static sumireko.SumirekoMod.makeID;

public class Banish extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Banish",
            0,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.UNCOMMON);
    // skill

    public static final String ID = makeID(cardInfo.cardName);


    private static final int MAGIC = 0;
    private static final int UPG_MAGIC = 1;


    public Banish() {
        super(cardInfo, true);

        setMagic(MAGIC, UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new HandSelectAction(1, (c) -> true, cards -> {
            for (AbstractCard c : cards)
            {
                addToTop(new DrawCardAction((this.upgraded ? this.magicNumber : 0) + (c.freeToPlay() ? 0 : (c.costForTurn >= 0 ? c.costForTurn : (c.costForTurn == -1 ? EnergyPanel.getCurrentEnergy() : 0)))));
                addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
            }
        }, cardStrings.EXTENDED_DESCRIPTION[0]));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Banish();
    }
}