// TODO remove file with 1.0.0
@file:Suppress("DEPRECATION", "TYPEALIAS_EXPANSION_DEPRECATION")

package ch.tutteli.atrium.api.cc.infix.en_GB

import ch.tutteli.atrium.api.infix.en_GB.*
import ch.tutteli.atrium.api.infix.en_GB.atLeast
import ch.tutteli.atrium.creating.Assert
import ch.tutteli.atrium.domain.builders.ExpectImpl
import ch.tutteli.atrium.domain.builders.migration.asAssert
import ch.tutteli.atrium.domain.builders.migration.asExpect
import ch.tutteli.atrium.domain.builders.migration.asSubExpect
import ch.tutteli.atrium.verbs.internal.AssertionVerbFactory
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.include
import kotlin.reflect.KFunction2

//TODO remove with 1.0.0, no need to migrate to Spek 2
class IterableAnyAssertionsSpec : Spek({
    include(PredicateSpec)
    include(BuilderSpec)
    include(ShortcutSpec)
    include(SequenceSpec)
}) {
    object PredicateSpec : ch.tutteli.atrium.spec.integration.IterableAnyAssertionsSpec(
        AssertionVerbFactory,
        getAnyPair(),
        getAnyNullablePair(),
        "◆ ",
        "[Atrium][Predicate] "
    )

    object BuilderSpec : ch.tutteli.atrium.spec.integration.IterableAnyAssertionsSpec(
        AssertionVerbFactory,
        getContainsPair(),
        getContainsNullablePair(),
        "◆ ",
        "[Atrium][Builder] "
    )

    object ShortcutSpec : ch.tutteli.atrium.spec.integration.IterableAnyAssertionsSpec(
        AssertionVerbFactory,
        getContainsShortcutPair(),
        getContainsNullableShortcutPair(),
        "◆ ",
        "[Atrium][Shortcut] "
    )

    object SequenceSpec : ch.tutteli.atrium.spec.integration.IterableAnyAssertionsSpec(
        AssertionVerbFactory,
        getContainsSequencePair(),
        getContainsNullableSequencePair(),
        "◆ ",
        "[Atrium][Sequence] "
    )


    companion object : IterableContainsSpecBase() {
        private val anyFun : KFunction2<Assert<Iterable<Double>>, Assert<Double>.() -> Unit, Assert<Iterable<Double>>> = Assert<Iterable<Double>>::any
        fun getAnyPair() = anyFun.name to Companion::any

        private fun any(plant: Assert<Iterable<Double>>, a: Assert<Double>.() -> Unit)
            = plant.asExpect().any(asSubExpect(a)).asAssert()

        private val anyNullableFun: KFunction2<Assert<Iterable<Double?>>, (Assert<Double>.() -> Unit)?, Assert<Iterable<Double?>>> = Assert<Iterable<Double?>>::any
        fun getAnyNullablePair() = anyNullableFun.name to Companion::anyNullable

        private fun anyNullable(plant: Assert<Iterable<Double?>>, a: (Assert<Double>.() -> Unit)?)
            = plant.asExpect().any(asSubExpect(a)).asAssert()


        fun getContainsPair()
            = "$toContain $inAnyOrder $atLeast 1 $inAnyOrderEntries" to Companion::containsInAnyOrderEntries

        private fun containsInAnyOrderEntries(plant: Assert<Iterable<Double>>, a: Assert<Double>.() -> Unit)
            = (plant.asExpect().contains(o) inAny order).atLeast(1) entry a

        fun getContainsNullablePair()
            = "$toContain $inAnyOrder $atLeast 1 $inAnyOrderEntries" to Companion::containsNullableEntries

        private fun containsNullableEntries(plant: Assert<Iterable<Double?>>, a: (Assert<Double>.() -> Unit)?)
            = plant.asExpect().contains(o) inAny order atLeast 1 entry a


        private val containsShortcutFun : KFunction2<Assert<Iterable<Double>>, Assert<Double>.() -> Unit, Assert<Iterable<Double>>> = Assert<Iterable<Double>>::contains
        fun getContainsShortcutPair() = containsShortcutFun.name to Companion::containsInAnyOrderEntriesShortcut

        private fun containsInAnyOrderEntriesShortcut(plant: Assert<Iterable<Double>>, a: Assert<Double>.() -> Unit)
            = plant.asExpect().contains(asSubExpect(a)).asAssert()

        private val containsShortcutNullableFun: KFunction2<Assert<Iterable<Double?>>, (Assert<Double>.() -> Unit)?, Assert<Iterable<Double?>>> = Assert<Iterable<Double?>>::contains
        fun getContainsNullableShortcutPair() = containsShortcutNullableFun.name to Companion::containsNullableEntriesShortcut

        private fun containsNullableEntriesShortcut(plant: Assert<Iterable<Double?>>, a: (Assert<Double>.() -> Unit)?)
            = plant.asExpect().contains(asSubExpect(a)).asAssert()


        private fun getContainsSequencePair()
            = "asSequence().${Sequence<*>::asIterable.name}().${containsShortcutFun.name}" to Companion::containsInAnyOrderEntriesSequence

        private fun containsInAnyOrderEntriesSequence(plant: Assert<Iterable<Double>>, a: Assert<Double>.() -> Unit)
            = ExpectImpl.changeSubject(plant).unreported { it.asSequence() }.asIterable().asExpect()
            .contains(asSubExpect(a)).asAssert()

        fun getContainsNullableSequencePair()
            = "asSequence().${Sequence<*>::asIterable.name}().${containsShortcutNullableFun.name}" to Companion::containsNullableEntriesSequence

        private fun containsNullableEntriesSequence(plant: Assert<Iterable<Double?>>, a: (Assert<Double>.() -> Unit)?)
            = ExpectImpl.changeSubject(plant).unreported { it.asSequence() }.asIterable().asExpect()
            .contains(asSubExpect(a)).asAssert()
    }
}
