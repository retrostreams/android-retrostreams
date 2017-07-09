/*
 * Written by Stefan Zobel and released to the
 * public domain, as explained at
 * http://creativecommons.org/publicdomain/zero/1.0/
 */
package java9.util;

import java9.util.function.Consumer;
import java9.util.function.DoubleConsumer;
import java9.util.function.IntConsumer;
import java9.util.function.LongConsumer;

final class RefConsumer {

    static IntConsumer toIntConsumer(Consumer<? super Integer> action) {
        return (IntConsumer) action::accept;
    }

    static LongConsumer toLongConsumer(Consumer<? super Long> action) {
        return (LongConsumer) action::accept;
    }

    static DoubleConsumer toDoubleConsumer(Consumer<? super Double> action) {
        return (DoubleConsumer) action::accept;
    }

    private RefConsumer() {
        // no instances
    }
}
