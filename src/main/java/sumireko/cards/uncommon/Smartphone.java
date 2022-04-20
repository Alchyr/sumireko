package sumireko.cards.uncommon;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.actions.general.AllEnemyStrengthDownAction;
import sumireko.actions.general.DrawSpecificCardAction;
import sumireko.interfaces.SleevedCard;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class Smartphone extends BaseCard implements SleevedCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Smartphone",
            0,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.UNCOMMON);

    public static final String ID = makeID(cardInfo.cardName);


    private static final int MAGIC = 5;
    private static final int UPG_MAGIC = 3;


    public Smartphone() {
        super(cardInfo, false);

        setMagic(MAGIC, UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void sleevedPlayCard(UseCardAction a, AbstractCard played, AbstractCreature target) {
        if (played.type == CardType.POWER) {
            addToTop(new DrawSpecificCardAction(this));
            addToBot(new AllEnemyStrengthDownAction(AbstractDungeon.player, this.magicNumber));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Smartphone();
    }
}