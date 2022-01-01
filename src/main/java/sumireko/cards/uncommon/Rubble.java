package sumireko.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.enums.CustomCardTags;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class Rubble extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Rubble",
            2,
            CardType.ATTACK,
            CardTarget.ALL_ENEMY,
            CardRarity.UNCOMMON);
    // attack

    public static final String ID = makeID(cardInfo.cardName);


    private static final int DAMAGE = 6;
    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 1;

    public Rubble() {
        super(cardInfo, false);

        setDamage(DAMAGE);
        setMagic(MAGIC, UPG_MAGIC);

        tags.add(CustomCardTags.FINAL);
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < this.magicNumber; ++i) //TODO: Add VFX of some kind of rubble being thrown at high speed
        {
            AttackDamageRandomEnemyAction dmg = new AttackDamageRandomEnemyAction(this, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
            addToBot(dmg);
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    applySingle(dmg.target, getVuln(dmg.target, 1), true);
                    this.isDone = true;
                }
            });
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Rubble();
    }
}