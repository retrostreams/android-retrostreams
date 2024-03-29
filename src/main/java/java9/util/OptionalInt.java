/*
 * Copyright (c) 2012, 2021, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package java9.util;

import java.util.NoSuchElementException;

import java9.util.function.IntConsumer;
import java9.util.function.IntSupplier;
import java9.util.function.Supplier;
import java9.util.stream.IntStream;

/**
 * A container object which may or may not contain an {@code int} value.
 * If a value is present, {@code isPresent()} returns {@code true}. If no
 * value is present, the object is considered <i>empty</i> and
 * {@code isPresent()} returns {@code false}.
 *
 * <p>Additional methods that depend on the presence or absence of a contained
 * value are provided, such as {@link #orElse(int) orElse()}
 * (returns a default value if no value is present) and
 * {@link #ifPresent(IntConsumer) ifPresent()} (performs an
 * action if a value is present).
 *
 * <p>This is a <a href="../lang/package-summary.html#Value-based-Classes">value-based</a>
 * class; use of identity-sensitive operations (including reference equality
 * ({@code ==}), identity hash code, or synchronization) on instances of
 * {@code OptionalInt} may have unpredictable results and should be avoided.
 *
 * <p><b>API Note:</b><br>
 * {@code OptionalInt} is primarily intended for use as a method return type where
 * there is a clear need to represent "no result". A variable whose type is
 * {@code OptionalInt} should never itself be {@code null}; it should always point
 * to an {@code OptionalInt} instance.
 *
 * @since 1.8
 */
public final class OptionalInt {
    /**
     * Common instance for {@code empty()}.
     */
    private static final OptionalInt EMPTY = new OptionalInt();

    /**
     * If true then the value is present, otherwise indicates no value is present
     */
    private final boolean isPresent;
    private final int value;

    private static final class OICache {
        private OICache() {}

        static final OptionalInt cache[] = new OptionalInt[-(-128) + 127 + 1];

        static {
            for (int i = 0; i < cache.length; i++) {
                cache[i] = new OptionalInt(i - 128);
            }
        }
    }

    /**
     * Construct an empty instance.
     *
     * <p><b>Implementation Note:</b><br> Generally only one empty instance, {@link OptionalInt#EMPTY},
     * should exist per VM.
     */
    private OptionalInt() {
        this.isPresent = false;
        this.value = 0;
    }

    /**
     * Returns an empty {@code OptionalInt} instance.  No value is present for
     * this OptionalInt.
     *
     * <p><b>API Note:</b><br>
     * Though it may be tempting to do so, avoid testing if an object is empty
     * by comparing with {@code ==} or {@code !=} against instances returned by
     * {@code OptionalInt.empty()}.  There is no guarantee that it is a singleton.
     * Instead, use {@link #isEmpty()} or {@link #isPresent()}.
     *
     *  @return an empty {@code OptionalInt}
     */
    public static OptionalInt empty() {
        return EMPTY;
    }

    /**
     * Construct an instance with the described value.
     *
     * @param value the int value to describe
     */
    OptionalInt(int value) {
        this.isPresent = true;
        this.value = value;
    }

    /**
     * Returns an {@code OptionalInt} describing the given value.
     *
     * @param value the value to describe
     * @return an {@code OptionalInt} with the value present
     */
    public static OptionalInt of(int value) {
        int offset = 128;
        if (value >= -128 && value <= 127) { // will cache
            return OICache.cache[value + offset];
        }
        return new OptionalInt(value);
    }

    /**
     * If a value is present, returns the value, otherwise throws
     * {@code NoSuchElementException}.
     *
     * <p><b>API Note:</b><br>
     * The preferred alternative to this method is {@link #orElseThrow()}.
     *
     * @return the value described by this {@code OptionalInt}
     * @throws NoSuchElementException if no value is present
     */
    public int getAsInt() {
        return orElseThrow();
    }

    /**
     * If a value is present, returns {@code true}, otherwise {@code false}.
     *
     * @return {@code true} if a value is present, otherwise {@code false}
     */
    public boolean isPresent() {
        return isPresent;
    }

    /**
     * If a value is not present, returns {@code true}, otherwise
     * {@code false}.
     *
     * @return {@code true} if a value is not present, otherwise {@code false}
     * @since 11
     */
    public boolean isEmpty() {
        return !isPresent;
    }

    /**
     * If a value is present, performs the given action with the value,
     * otherwise does nothing.
     *
     * @param action the action to be performed, if a value is present
     * @throws NullPointerException if a value is present and the given action is
     * {@code null}
     */
    public void ifPresent(IntConsumer action) {
        if (isPresent) {
            action.accept(value);
        }
    }

    /**
     * If a value is present, performs the given action with the value,
     * otherwise performs the given empty-based action.
     *
     * @param action the action to be performed, if a value is present
     * @param emptyAction the empty-based action to be performed, if no value is
     * present
     * @throws NullPointerException if a value is present and the given action
     * is {@code null}, or no value is present and the given empty-based action
     * is {@code null}.
     * @since 9
     */
    public void ifPresentOrElse(IntConsumer action, Runnable emptyAction) {
        if (isPresent) {
            action.accept(value);
        } else {
            emptyAction.run();
        }
    }

    /**
     * If a value is present, returns a sequential {@link IntStream} containing
     * only that value, otherwise returns an empty {@code IntStream}.
     *
     * <p><b>API Note:</b><br>
     * This method can be used to transform a {@code Stream} of optional
     * integers to an {@code IntStream} of present integers:
     *
     * <pre>{@code
     *     Stream<OptionalInt> os = ..
     *     IntStream s = os.flatMapToInt(OptionalInt::stream)
     * }</pre>
     *
     * @return the optional value as an {@code IntStream}
     * @since 9
     */
    public IntStream stream() {
        if (isPresent) {
            return IntStream.of(value);
        } else {
            return IntStream.empty();
        }
    }

    /**
     * If a value is present, returns the value, otherwise returns
     * {@code other}.
     *
     * @param other the value to be returned, if no value is present
     * @return the value, if present, otherwise {@code other}
     */
    public int orElse(int other) {
        return isPresent ? value : other;
    }

    /**
     * If a value is present, returns the value, otherwise returns the result
     * produced by the supplying function.
     *
     * @param supplier the supplying function that produces a value to be returned
     * @return the value, if present, otherwise the result produced by the
     * supplying function
     * @throws NullPointerException if no value is present and the supplying
     * function is {@code null}
     */
    public int orElseGet(IntSupplier supplier) {
        return isPresent ? value : supplier.getAsInt();
    }

    /**
     * If a value is present, returns the value, otherwise throws
     * {@code NoSuchElementException}.
     *
     * @return the value described by this {@code OptionalInt}
     * @throws NoSuchElementException if no value is present
     * @since 10
     */
    public int orElseThrow() {
        if (!isPresent) {
            throw new NoSuchElementException("No value present");
        }
        return value;
    }

    /**
     * If a value is present, returns the value, otherwise throws an exception
     * produced by the exception supplying function.
     *
     * <p><b>API Note:</b><br>
     * A method reference to the exception constructor with an empty argument
     * list can be used as the supplier.  For example,
     * {@code IllegalStateException::new}
     *
     * @param <X> Type of the exception to be thrown
     * @param exceptionSupplier the supplying function that produces an
     * exception to be thrown
     * @return the value, if present
     * @throws X if no value is present
     * @throws NullPointerException if no value is present and the exception
     * supplying function is {@code null}
     */
    public <X extends Throwable> int orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        if (isPresent) {
            return value;
        } else {
            throw exceptionSupplier.get();
        }
    }

    /**
     * Indicates whether some other object is "equal to" this
     * {@code OptionalInt}.  The other object is considered equal if:
     * <ul>
     * <li>it is also an {@code OptionalInt} and;
     * <li>both instances have no value present or;
     * <li>the present values are "equal to" each other via {@code ==}.
     * </ul>
     *
     * @param obj an object to be tested for equality
     * @return {@code true} if the other object is "equal to" this object
     * otherwise {@code false}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof OptionalInt)) {
            return false;
        }

        OptionalInt other = (OptionalInt) obj;
        return (isPresent && other.isPresent)
                ? value == other.value
                : isPresent == other.isPresent;
    }

    /**
     * Returns the hash code of the value, if present, otherwise {@code 0}
     * (zero) if no value is present.
     *
     * @return hash code value of the present value or {@code 0} if no value is
     * present
     */
    @Override
    public int hashCode() {
        return isPresent ? value : 0;
    }

    /**
     * Returns a non-empty string representation of this {@code OptionalInt}
     * suitable for debugging.  The exact presentation format is unspecified and
     * may vary between implementations and versions.
     *
     * <p><b>Implementation Requirements:</b><br>
     * If a value is present the result must include its string representation
     * in the result.  Empty and present {@code OptionalInt}s must be
     * unambiguously differentiable.
     *
     * @return the string representation of this instance
     */
    @Override
    public String toString() {
        return isPresent
                ? ("OptionalInt[" + value + "]")
                : "OptionalInt.empty";
    }
}
