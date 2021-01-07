/*package sumireko.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.util.CardInfo;

import java.util.Iterator;

import static sumireko.SumirekoMod.makeID;

public class IllegalDumping extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "IllegalDumping",
            2,
            CardType.ATTACK,
            CardTarget.ENEMY,
            CardRarity.UNCOMMON);

    public static final String ID = makeID(cardInfo.cardName);


    private static final int DAMAGE = 30;
    private static final int UPG_DAMAGE = 8;

    private static final int MAGIC = 8;

    public IllegalDumping() {
        super(cardInfo, false);

        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(MAGIC);
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        } else {
            int totalCost = 0;

            for (AbstractCard c : p.hand.group)
            {
                totalCost += Math.max(c.costForTurn, 0);
            }

            if (totalCost < this.magicNumber)
            {
                canUse = false;
                this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0] + totalCost + cardStrings.EXTENDED_DESCRIPTION[1];
            }

            return canUse;
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //garbage pile
        damageSingle(m, AbstractGameAction.AttackEffect.SMASH);
    }

    @Override
    public AbstractCard makeCopy() {
        return new IllegalDumping();
    }
}*/