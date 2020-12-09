package sumireko.cards.uncommon;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.SealCard;
import sumireko.actions.MirrorAction;
import sumireko.actions.SealAction;
import sumireko.enums.CustomCardTags;
import sumireko.util.CardInfo;
import sumireko.util.PretendMonster;
import sumireko.util.SealIntent;

import java.util.Map;

import static sumireko.SumirekoMod.makeID;

public class MirrorSeal extends SealCard {
    private final static CardInfo cardInfo = new CardInfo(
            "MirrorSeal",
            1,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.UNCOMMON);
    // skill

    public static final String ID = makeID(cardInfo.cardName);


    public SealCard copying;


    public MirrorSeal() {
        super(cardInfo, true);

        copying = null;

        this.tags.add(CustomCardTags.FRAGILE_SEAL);
    }

    @Override
    public void upgrade() {
        super.upgrade();
        this.tags.remove(CustomCardTags.FRAGILE_SEAL);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        MirrorAction mirror = new MirrorAction(this);
        addToBot(mirror);
        addToBot(new SealAction(this, mirror));
    }

    @Override
    public void triggerOnExhaust() {
        copying = null;
        this.cardID = ID;
    }

    @Override
    public void onMoveToDiscard() {
        copying = null;
        this.cardID = ID;
    }

    @Override
    public boolean hasTag(CardTags tagToCheck) {
        if (copying != null)
        {
            return copying.hasTag(tagToCheck);
        }
        return super.hasTag(tagToCheck);
    }

    @Override
    public void resetSealValue() {
        super.resetSealValue();
        if (copying != null)
            copying.resetSealValue();
    }

    @Override
    public void triggerSealEffect(AbstractMonster target) {
        if (copying != null)
            copying.triggerSealEffect(target);
    }

    @Override
    public void instantSealEffect(PretendMonster target, Map<AbstractMonster, PretendMonster> pretendMonsters) {
        if (copying != null)
            copying.instantSealEffect(target, pretendMonsters);
    }

    @Override
    public void negateSeal() {
        super.negateSeal();
        if (copying != null)
            copying.negateSeal();
    }

    @Override
    public void modifySealValue(int amount) {
        super.modifySealValue(amount);
        if (copying != null)
            copying.modifySealValue(amount);
    }

    @Override
    public void multiplySealValue(int amount) {
        super.multiplySealValue(amount);
        if (copying != null)
            copying.multiplySealValue(amount);
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        if (copying != null)
            copying.applyPowers();
    }

    public void applyBaseAdjacencyEffect(SealCard c)
    {
        if (copying != null)
            copying.applyBaseAdjacencyEffect(c);
    }
    public void applyAdjacencyEffect(SealCard c)
    {
        if (copying != null)
            copying.applyAdjacencyEffect(c);
    }
    public void applyFinalBaseAdjacencyEffect(SealCard c)
    {
        if (copying != null)
            copying.applyFinalBaseAdjacencyEffect(c);
    }
    public void applyFinalAdjacencyEffect(SealCard c)
    {
        if (copying != null)
            copying.applyFinalAdjacencyEffect(c);
    }

    @Override
    public void getIntent(SealIntent i) {
        if (copying != null)
        {
            copying.getIntent(i);
            i.amount = copying.sealValue;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        if (copying != null)
        {
            copying.current_x = this.current_x;
            copying.current_y = this.current_y;
            copying.drawScale = this.drawScale;
            copying.render(sb);
        }
        else
        {
            super.render(sb);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new MirrorSeal();
    }
}