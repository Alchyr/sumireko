package sumireko.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.SealCard;
import sumireko.util.CardInfo;
import sumireko.util.SealIntent;

import java.util.ArrayList;

import static sumireko.SumirekoMod.makeID;

public class StormSeal extends SealCard {
    private final static CardInfo cardInfo = new CardInfo(
            "StormSeal",
            1,
            CardType.ATTACK,
            CardTarget.ENEMY,
            CardRarity.UNCOMMON);

    public static final String ID = makeID(cardInfo.cardName);


    private static final int DAMAGE = 8;
    private static final int UPG_DAMAGE = 2;

    private static final int SEAL = 8;
    private static final int UPG_SEAL = 2;

    public StormSeal() {
        super(cardInfo, false);

        setDamage(DAMAGE, UPG_DAMAGE);
        setSeal(SEAL, UPG_SEAL);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        damageSingle(m, AbstractGameAction.AttackEffect.LIGHTNING);

        super.use(p, m);
    }

    @Override
    public ArrayList<AbstractGameAction> triggerSealEffect(AbstractMonster target) {
        ArrayList<AbstractGameAction> actions = new ArrayList<>();

        actions.add(new ModifyDamageAction(this.uuid, this.sealValue));

        return actions;
    }

    @Override
    public void getIntent(SealIntent i) {
        i.addIntent(SealIntent.MAGIC);
        i.bonusEffect(String.valueOf(this.sealValue));
    }

    @Override
    public AbstractCard makeCopy() {
        return new StormSeal();
    }
}