package sumireko.actions.interfaces;

import com.megacrit.cardcrawl.core.AbstractCreature;

import java.util.function.Consumer;

public interface WrappedDamage {
    void useWrapper(Consumer<AbstractCreature> onUnblocked);
}
