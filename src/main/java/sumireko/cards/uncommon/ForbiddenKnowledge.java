package sumireko.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.actions.cards.ForbiddenKnowledgeAction;
import sumireko.actions.cards.OldForbiddenKnowledgeAction;
import sumireko.patches.occult.OccultFields;
import sumireko.util.CardInfo;

import java.util.Iterator;

import static sumireko.SumirekoMod.makeID;

public class ForbiddenKnowledge extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "ForbiddenKnowledge",
            1,
            CardType.ATTACK,
            CardTarget.ENEMY,
            CardRarity.UNCOMMON);

    public static final String ID = makeID(cardInfo.cardName);


    private static final int DAMAGE = 7;
    private static final int UPG_DAMAGE = 2;


    public ForbiddenKnowledge() {
        super(cardInfo, false);

        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(0);
    }

    @Override
    public float getTitleFontSize() {
        return 18.0f;
    }

    /*public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        } else {
            if (p.drawPile.size() >= this.magicNumber)
            {
                canUse = false;
                this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
            }

            return canUse;
        }
    }*/

    public void applyPowers() {
        super.applyPowers();
        int[] count = new int[] { 0 };

        forEachCard((c)->{
            if (OccultFields.isOccult.get(c)) { ++count[0]; }
        });

        this.magicNumber = this.baseMagicNumber = count[0];
        this.rawDescription = cardStrings.DESCRIPTION;
        this.rawDescription = this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[0] + count[0];
        if (count[0] == 1) {
            this.rawDescription = this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[1];
        } else {
            this.rawDescription = this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[2];
        }

        this.initializeDescription();
    }

    public void onMoveToDiscard() {
        this.magicNumber = this.baseMagicNumber = 0;
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ForbiddenKnowledgeAction(m, this.magicNumber, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
        this.magicNumber = this.baseMagicNumber = 0;
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public AbstractCard makeCopy() {
        return new ForbiddenKnowledge();
    }
}