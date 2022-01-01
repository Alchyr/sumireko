/*package sumireko.cards.rare;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.LockingCard;
import sumireko.abstracts.SealCard;
import sumireko.enums.CustomCardTags;
import sumireko.interfaces.LockingCardInterface;
import sumireko.util.CardInfo;
import sumireko.util.PretendMonster;
import sumireko.util.SealIntent;

import java.util.Map;

import static sumireko.SumirekoMod.makeID;

public class ExtensionSeal extends SealCard implements LockingCardInterface {
    private final static CardInfo cardInfo = new CardInfo(
            "ExtensionSeal",
            2,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.RARE);
    // skill

    private boolean locked;

    public static final String ID = makeID(cardInfo.cardName);

    private static final int SEAL = 2;

    public ExtensionSeal() {
        super(cardInfo, true);

        setSeal(SEAL);
        locked = false;
    }

    @Override
    public void upgrade() {
        super.upgrade();
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (!upgraded)
            return false;

        if (locked)
            return false;

        return super.canUse(p, m);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded) {
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    lockCard();
                    this.isDone = true;
                }
            });
        }

        super.use(p, m);
    }

    @Override
    public void applyFinalBaseAdjacencyEffect(SealCard c) {
        c.multiplySealValue(this.tempSealValue);
    }

    @Override
    public void applyFinalAdjacencyEffect(SealCard c) {
        c.multiplySealValue(this.sealValue);
    }

    @Override
    public void getIntent(SealIntent i) {
        i.intent = AbstractMonster.Intent.BUFF;
        i.multihit(this.sealValue);
        i.amount = -1;
    }

    @Override
    public AbstractCard makeCopy() {
        return new ExtensionSeal();
    }


    @Override
    public AbstractCard makeStatEquivalentCopy() {
        AbstractCard copy = super.makeStatEquivalentCopy();
        if (copy instanceof LockingCardInterface)
        {
            if (this.locked) {
                ((LockingCardInterface) copy).lockCard();
            }
            else {
                ((LockingCardInterface) copy).unlockCard();
            }
        }
        return copy;
    }

    @Override
    public boolean isLocked() {
        return locked;
    }

    @Override
    public void lockCard() {
        if (upgraded) {
            this.locked = true;

            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
            this.initializeDescription();
        }
    }

    @Override
    public void unlockCard() {
        if (upgraded) {
            this.locked = false;

            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}*/