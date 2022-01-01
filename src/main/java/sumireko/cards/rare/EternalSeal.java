package sumireko.cards.rare;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.GoldenSlashEffect;
import sumireko.abstracts.SealCard;
import sumireko.enums.CustomCardTags;
import sumireko.util.CardInfo;
import sumireko.util.HealthBarRender;
import sumireko.util.PretendMonster;
import sumireko.util.SealIntent;

import java.util.ArrayList;
import java.util.Map;

import static sumireko.SumirekoMod.makeID;

public class EternalSeal extends SealCard {
    private final static CardInfo cardInfo = new CardInfo(
            "EternalSeal",
            3,
            CardType.ATTACK,
            CardTarget.ENEMY,
            CardRarity.RARE);

    public static final String ID = makeID(cardInfo.cardName);


    private static final int DAMAGE = 25;
    private static final int UPG_DAMAGE = 7;

    public EternalSeal() {
        super(cardInfo, false);

        setDamage(DAMAGE, UPG_DAMAGE);
        tags.add(CustomCardTags.RETURNING_SEAL);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null)
            addToBot(new VFXAction(new GoldenSlashEffect(m.hb.cX, m.hb.cY, true)));

        damageSingle(m, AbstractGameAction.AttackEffect.NONE);

        super.use(p, m);
    }

    @Override
    public void getIntent(SealIntent i) {

    }

    @Override
    public HealthBarRender instantSealEffect(PretendMonster target, Map<AbstractMonster, PretendMonster> pretendMonsters) {
        return null;
    }

    @Override
    public AbstractCard makeCopy() {
        return new EternalSeal();
    }
}