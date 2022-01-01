package sumireko.interfaces;

import com.megacrit.cardcrawl.cards.DamageInfo;

public interface DamageModifierCard {
    float modifyDamage(DamageInfo.DamageType type, float damage);
}
