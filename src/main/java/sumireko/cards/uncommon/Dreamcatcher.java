package sumireko.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.abstracts.SealCard;
import sumireko.interfaces.SleevedCard;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class Dreamcatcher extends BaseCard implements SleevedCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Dreamcatcher",
            0,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.UNCOMMON);

    public static final String ID = makeID(cardInfo.cardName);


    private static final int MAGIC = 0;
    private static final int UPG_MAGIC = 1;


    public Dreamcatcher() {
        super(cardInfo, true);

        setMagic(MAGIC, UPG_MAGIC);
    }

    @Override
    public void upgrade() {
        super.upgrade();
        this.target = CardTarget.ENEMY;
    }

    @Override
    public void sleevedPlayCard(UseCardAction a, AbstractCard played, AbstractCreature target) {
        if (played instanceof SealCard) {
            if (upgraded) {
                addToTop(new AbstractGameAction() {
                    @Override
                    public void update() {
                        this.isDone = true;
                        if (AbstractDungeon.player.drawPile.contains(Dreamcatcher.this))
                            AbstractDungeon.player.drawPile.moveToDiscardPile(Dreamcatcher.this);
                    }
                });
            }
            else {
                if (AbstractDungeon.player.drawPile.contains(Dreamcatcher.this))
                    addToTop(new ExhaustSpecificCardAction(this, AbstractDungeon.player.drawPile));
            }
            addToBot(new MakeTempCardInHandAction(played, 1));
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null && upgraded && this.magicNumber > 0) {
            applySingle(m, getWeak(m, this.magicNumber));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Dreamcatcher();
    }
}