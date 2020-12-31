package sumireko.cards.basic;

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

import java.util.Map;

import static sumireko.SumirekoMod.makeID;

public class StrikeSeal extends SealCard {
    private final static CardInfo cardInfo = new CardInfo(
            "StrikeSeal",
            1,
            CardType.ATTACK,
            CardTarget.ENEMY,
            CardRarity.BASIC);
    // skill

    public static final String ID = makeID(cardInfo.cardName);


    private static final int DAMAGE = 3;

    private static final int SEAL = 3;
    private static final int UPG_SEAL = 3;

    public StrikeSeal() {
        super(cardInfo, false);

        setDamage(DAMAGE);
        setSeal(SEAL, UPG_SEAL);

        tags.add(CardTags.STRIKE);
        tags.add(CardTags.STARTER_STRIKE);
    }

    @Override
    public AbstractCard makeCopy() {
        return new StrikeSeal();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        damageSingle(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);

        super.use(p, m);
    }

    @Override
    public void triggerSealEffect(AbstractMonster target) {
        if (this.sealValue > 0)
            damageSingle(target, this.sealValue, DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE);
    }

    @Override
    public HealthBarRender instantSealEffect(PretendMonster target, Map<AbstractMonster, PretendMonster> pretendMonsters) {
        if (target != null)
            if (this.sealValue > 0)
                target.damage(new DamageInfo(AbstractDungeon.player, this.sealValue, DamageInfo.DamageType.THORNS));

        return null;
    }

    @Override
    public void getIntent(SealIntent i) {
        i.intent = AbstractMonster.Intent.ATTACK;
    }
}