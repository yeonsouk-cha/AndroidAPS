package info.nightscout.androidaps.utils.serialisation

import info.nightscout.androidaps.database.entities.XXXValueWithUnit
import org.junit.Assert
import org.junit.Test

internal class ValueWithUnitSerialiserTest {

    @Test
    fun testSerialisationDeserization() {

        val list = listOf<XXXValueWithUnit>(
            XXXValueWithUnit.SimpleString("hello"),
            XXXValueWithUnit.SimpleInt(5),
            XXXValueWithUnit.UNKNOWN
        )

        val serialized = ValueWithUnitSerialiser.toSealedClassJson(list)
        val deserialized = ValueWithUnitSerialiser.fromJson(serialized)

        Assert.assertEquals(3, list.size)
        Assert.assertEquals(list, deserialized)
    }

    @Test
    fun testSerialisationDeserizationNested() {

        val list = listOf<XXXValueWithUnit>(
            XXXValueWithUnit.SimpleString("hello"),
            XXXValueWithUnit.SimpleInt(5),
            XXXValueWithUnit.UNKNOWN,
            XXXValueWithUnit.StringResource(3,
                listOf(
                    XXXValueWithUnit.SimpleString("hello"),
                    XXXValueWithUnit.SimpleInt(5)
                )
            )
        )

        val serialized = ValueWithUnitSerialiser.toSealedClassJson(list)
        val deserialized = ValueWithUnitSerialiser.fromJson(serialized)

        Assert.assertEquals(4, list.size)
        Assert.assertTrue(deserialized[3] is XXXValueWithUnit.StringResource)
        Assert.assertEquals(XXXValueWithUnit.SimpleString("hello"), (deserialized[3] as XXXValueWithUnit.StringResource).params[0])
        Assert.assertEquals(XXXValueWithUnit.SimpleInt(5), (deserialized[3] as XXXValueWithUnit.StringResource).params[1])

    }

    @Test
    fun testEmptyList() {

        val list = listOf<XXXValueWithUnit>()

        val serialized = ValueWithUnitSerialiser.toSealedClassJson(list)
        val deserialized = ValueWithUnitSerialiser.fromJson(serialized)

        Assert.assertEquals(0, list.size)
      }
}