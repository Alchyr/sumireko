package sumireko.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.SealCard;
import sumireko.util.CardInfo;
import sumireko.util.HealthBarRender;
import sumireko.util.PretendMonster;
import sumireko.util.SealIntent;

import java.util.ArrayList;
import java.util.Map;

import static sumireko.SumirekoMod.makeID;

public class FireburstSeal extends SealCard {
    private final static CardInfo cardInfo = new CardInfo(
            "FireburstSeal",
            2,
            CardType.ATTACK,
            CardTarget.ENEMY,
            CardRarity.COMMON);

    public static final String ID = makeID(cardInfo.cardName);


    private static final int DAMAGE = 4;
    private static final int UPG_DAMAGE = 2;

    private static final int SEAL = 4;
    private static final int UPG_SEAL = 2;

    private static final int HITS = 3; //this is displayed as "three", so this is never going to change.

    public FireburstSeal() {
        super(cardInfo, false);

        setDamage(DAMAGE, UPG_DAMAGE);
        setSeal(SEAL, UPG_SEAL);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        damageSingle(m, AbstractGameAction.AttackEffect.FIRE);

        super.use(p, m);
    }

    @Override
    public ArrayList<AbstractGameAction> triggerSealEffect(AbstractMonster target) {
        ArrayList<AbstractGameAction> actions = new ArrayList<>();
        if (this.sealValue > 0)
            for (int i = 0; i < HITS; ++i)
            {
                //TODO: add some nice poofy bursts of fire particles (in the shape of a fire kanji? :thonk:)
                actions.add(getDamageSingle(target, this.sealValue, DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
            }

        return actions;
    }

    @Override
    public HealthBarRender instantSealEffect(PretendMonster target, Map<AbstractMonster, PretendMonster> pretendMonsters) {
        if (target != null)
            if (this.sealValue > 0)
                for (int i = 0; i < HITS; ++i)
                    target.sealDamage(new DamageInfo(AbstractDungeon.player, this.sealValue, DamageInfo.DamageType.THORNS), this);
        return null;
    }

    @Override
    public void getIntent(SealIntent i) {
        i.baseDamage(this.sealValue, HITS);
    }

    @Override
    public AbstractCard makeCopy() {
        return new FireburstSeal();
    }
}