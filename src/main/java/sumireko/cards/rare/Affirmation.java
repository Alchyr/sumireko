/*package sumireko.cards.rare;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.unique.DamagePerAttackPlayedAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.actions.cards.AffirmationAction;
import sumireko.enums.CustomCardTags;
import sumireko.util.CardInfo;

import java.util.Iterator;

import static sumireko.SumirekoMod.makeID;

public class Affirmation extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Affirmation",
            0,
            CardType.ATTACK,
            CardTarget.SELF,
            CardRarity.RARE);

    public static final String ID = makeID(cardInfo.cardName);


    private static final int DAMAGE = 4;
    private static final int BLOCK = 4;

    public Affirmation() {
        super(cardInfo, false);

        setDamage(DAMAGE);
        setBlock(BLOCK);
        tags.add(CustomCardTags.FINAL);
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new AffirmationAction(this, p, this.block));

        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    public void applyPowers() {
        super.applyPowers();

        int count = 0;

        for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisTurn) {
            if (!c.upgraded) {
                ++count;
            }
        }

        this.rawDescription = cardStrings.DESCRIPTION;
        this.rawDescription = this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[0] + count;
        if (count == 1) {
            this.rawDescription = this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[1];
        } else {
            this.rawDescription = this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[2];
        }

        this.initializeDescription();
    }

    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public AbstractCard makeCopy() {
        return new Affirmation();
    }
}*/