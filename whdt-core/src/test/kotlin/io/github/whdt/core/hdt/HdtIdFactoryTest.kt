package io.github.whdt.core.hdt

import io.github.whdt.core.hdt.interfaces.digital.DigitalInterface
import io.github.whdt.core.hdt.interfaces.digital.DigitalInterfaceName
import io.github.whdt.core.hdt.interfaces.digital.HttpDigitalInterface
import io.github.whdt.core.hdt.interfaces.digital.MqttDigitalInterface
import io.github.whdt.core.hdt.interfaces.physical.MqttPhysicalInterface
import io.github.whdt.core.hdt.interfaces.physical.PhysicalInterfaceName
import io.github.whdt.core.hdt.model.Model
import io.github.whdt.core.hdt.model.ModelDescription
import io.github.whdt.core.hdt.model.ModelId
import io.github.whdt.core.hdt.model.ModelName
import io.github.whdt.core.hdt.model.property.Property
import io.github.whdt.core.hdt.model.property.PropertyDescription
import io.github.whdt.core.hdt.model.property.PropertyName
import io.github.whdt.core.hdt.model.property.PropertyValue
import io.github.whdt.core.hdt.storage.Storage
import io.github.whdt.core.hdt.storage.StorageName
import io.github.whdt.core.hdt.storage.StorageType
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import kotlin.time.Clock

class HdtIdFactoryTest : FunSpec({

    // -------------------------------------------------------------------------
    // Helpers (parameter names differ from outer vals to avoid shadowing)
    // -------------------------------------------------------------------------

    fun prop(name: String, mId: ModelId) = Property(
        modelId = mId,
        name = PropertyName(name),
        description = PropertyDescription(""),
        timestamp = Clock.System.now(),
        value = PropertyValue.StringPropertyValue("x"),
    )

    fun buildModel(
        name: String,
        hId: HdtId,
        propNames: List<String> = listOf("heart-rate"),
    ): Model {
        val mName = ModelName(name)
        val mId = HdtIdFactory.modelId(hId, mName)
        return Model(
            hdtId = hId,
            name = mName,
            description = ModelDescription(""),
            properties = propNames.map { prop(it, mId) },
        )
    }

    fun buildHdt(
        id: HdtId,
        models: List<Model>,
        storages: List<Storage> = listOf(Storage.default(id)),
        physicalInterfaces: List<MqttPhysicalInterface> = emptyList(),
        digitalInterfaces: List<DigitalInterface> = emptyList(),
    ) = HumanDigitalTwin(
        hdtId = id,
        models = models,
        storages = storages,
        physicalInterfaces = physicalInterfaces,
        digitalInterfaces = digitalInterfaces,
    )

    val hdtId = HdtId("dt-1")

    // -------------------------------------------------------------------------
    // Factory methods — ID format
    // -------------------------------------------------------------------------

    context("HdtIdFactory — factory methods") {

        test("hdtId wraps the raw string") {
            HdtIdFactory.hdtId("alice").id shouldBe "alice"
        }

        test("modelId format is hdtId:modelName") {
            HdtIdFactory.modelId(HdtId("dt-1"), ModelName("vitals")).value shouldBe "dt-1:vitals"
        }

        test("propertyId format is hdtId:modelName:propertyName") {
            val mId = HdtIdFactory.modelId(HdtId("dt-1"), ModelName("vitals"))
            HdtIdFactory.propertyId(mId, PropertyName("heart-rate")).value shouldBe "dt-1:vitals:heart-rate"
        }

        test("storageId format is hdtId:storageName") {
            HdtIdFactory.storageId(HdtId("dt-1"), StorageName("mem")).value shouldBe "dt-1:mem"
        }

        test("digitalInterfaceId format is hdtId:interfaceName") {
            HdtIdFactory.digitalInterfaceId(HdtId("dt-1"), DigitalInterfaceName("http-di")).value shouldBe "dt-1:http-di"
        }

        test("physicalInterfaceId format is hdtId:interfaceName") {
            HdtIdFactory.physicalInterfaceId(HdtId("dt-1"), PhysicalInterfaceName("mqtt-pi")).value shouldBe "dt-1:mqtt-pi"
        }

        test("SEPARATOR constant is ':'") {
            HdtIdFactory.SEPARATOR shouldBe ':'
        }
    }

    // -------------------------------------------------------------------------
    // Format validation — name value classes
    // -------------------------------------------------------------------------

    context("Format validation") {

        context("HdtId") {
            test("accepts a valid id") {
                HdtId("Mimosa1").id shouldBe "Mimosa1"
            }

            test("rejects blank") {
                shouldThrow<IllegalArgumentException> { HdtId("") }
                    .message shouldContain "HdtId"
            }

            test("rejects whitespace-only") {
                shouldThrow<IllegalArgumentException> { HdtId("   ") }
                    .message shouldContain "HdtId"
            }

            test("rejects id containing ':'") {
                shouldThrow<IllegalArgumentException> { HdtId("dt:1") }
                    .message shouldContain "':'"
            }
        }

        context("ModelName") {
            test("accepts a valid name") {
                ModelName("vitals").value shouldBe "vitals"
            }

            test("rejects blank") {
                shouldThrow<IllegalArgumentException> { ModelName("") }
                    .message shouldContain "ModelName"
            }

            test("rejects name containing ':'") {
                shouldThrow<IllegalArgumentException> { ModelName("vit:als") }
                    .message shouldContain "':'"
            }
        }

        context("PropertyName") {
            test("accepts a valid name") {
                PropertyName("heart-rate").value shouldBe "heart-rate"
            }

            test("rejects blank") {
                shouldThrow<IllegalArgumentException> { PropertyName("") }
                    .message shouldContain "PropertyName"
            }

            test("rejects name containing ':'") {
                shouldThrow<IllegalArgumentException> { PropertyName("heart:rate") }
                    .message shouldContain "':'"
            }
        }

        context("StorageName") {
            test("accepts a valid name") {
                StorageName("mem-store").value shouldBe "mem-store"
            }

            test("rejects blank") {
                shouldThrow<IllegalArgumentException> { StorageName("") }
                    .message shouldContain "StorageName"
            }

            test("rejects name containing ':'") {
                shouldThrow<IllegalArgumentException> { StorageName("mem:store") }
                    .message shouldContain "':'"
            }
        }

        context("DigitalInterfaceName") {
            test("accepts a valid name") {
                DigitalInterfaceName("http-di").value shouldBe "http-di"
            }

            test("rejects blank") {
                shouldThrow<IllegalArgumentException> { DigitalInterfaceName("") }
                    .message shouldContain "DigitalInterfaceName"
            }

            test("rejects name containing ':'") {
                shouldThrow<IllegalArgumentException> { DigitalInterfaceName("http:di") }
                    .message shouldContain "':'"
            }
        }

        context("PhysicalInterfaceName") {
            test("accepts a valid name") {
                PhysicalInterfaceName("mqtt-pi").value shouldBe "mqtt-pi"
            }

            test("rejects blank") {
                shouldThrow<IllegalArgumentException> { PhysicalInterfaceName("") }
                    .message shouldContain "PhysicalInterfaceName"
            }

            test("rejects name containing ':'") {
                shouldThrow<IllegalArgumentException> { PhysicalInterfaceName("mqtt:pi") }
                    .message shouldContain "':'"
            }
        }
    }

    // -------------------------------------------------------------------------
    // Collision detection — Model
    // -------------------------------------------------------------------------

    context("Model — collision detection") {

        test("accepts properties with unique names") {
            buildModel("vitals", hdtId, listOf("heart-rate", "temperature"))
        }

        test("rejects duplicate property names") {
            val mId = HdtIdFactory.modelId(hdtId, ModelName("vitals"))
            shouldThrow<IllegalArgumentException> {
                Model(
                    hdtId = hdtId,
                    name = ModelName("vitals"),
                    description = ModelDescription(""),
                    properties = listOf(prop("heart-rate", mId), prop("heart-rate", mId)),
                )
            }.message shouldContain "Duplicate property IDs"
        }

        test("rejects property referencing a different model") {
            val foreignModelId = HdtIdFactory.modelId(HdtId("other"), ModelName("vitals"))
            shouldThrow<IllegalArgumentException> {
                Model(
                    hdtId = hdtId,
                    name = ModelName("vitals"),
                    description = ModelDescription(""),
                    properties = listOf(prop("heart-rate", foreignModelId)),
                )
            }.message shouldContain "reference model"
        }
    }

    // -------------------------------------------------------------------------
    // Collision detection — HumanDigitalTwin
    // -------------------------------------------------------------------------

    context("HumanDigitalTwin — collision detection") {

        test("accepts child entities with unique IDs") {
            buildHdt(hdtId, listOf(buildModel("vitals", hdtId)))
        }

        test("rejects duplicate model names") {
            shouldThrow<IllegalArgumentException> {
                buildHdt(hdtId, listOf(buildModel("vitals", hdtId), buildModel("vitals", hdtId)))
            }.message shouldContain "Duplicate model IDs"
        }

        test("rejects model referencing a different HDT") {
            val foreignModel = buildModel("vitals", HdtId("other"))
            shouldThrow<IllegalArgumentException> {
                HumanDigitalTwin(hdtId = hdtId, models = listOf(foreignModel))
            }.message shouldContain "reference HDT"
        }

        test("rejects duplicate storage names") {
            val s1 = Storage(hdtId, StorageName("mem"), StorageType.IN_MEMORY)
            val s2 = Storage(hdtId, StorageName("mem"), StorageType.IN_MEMORY)
            shouldThrow<IllegalArgumentException> {
                HumanDigitalTwin(hdtId = hdtId, models = emptyList(), storages = listOf(s1, s2))
            }.message shouldContain "Duplicate storage IDs"
        }

        test("rejects storage referencing a different HDT") {
            val foreignStorage = Storage(HdtId("other"), StorageName("mem"), StorageType.IN_MEMORY)
            shouldThrow<IllegalArgumentException> {
                HumanDigitalTwin(hdtId = hdtId, models = emptyList(), storages = listOf(foreignStorage))
            }.message shouldContain "reference HDT"
        }

        test("rejects duplicate physical interface names") {
            val pi1 = MqttPhysicalInterface(hdtId, PhysicalInterfaceName("mqtt-pi"))
            val pi2 = MqttPhysicalInterface(hdtId, PhysicalInterfaceName("mqtt-pi"))
            shouldThrow<IllegalArgumentException> {
                buildHdt(hdtId, emptyList(), physicalInterfaces = listOf(pi1, pi2))
            }.message shouldContain "Duplicate physical interface IDs"
        }

        test("rejects physical interface referencing a different HDT") {
            val foreignPi = MqttPhysicalInterface(HdtId("other"), PhysicalInterfaceName("mqtt-pi"))
            shouldThrow<IllegalArgumentException> {
                buildHdt(hdtId, emptyList(), physicalInterfaces = listOf(foreignPi))
            }.message shouldContain "reference HDT"
        }

        test("rejects duplicate digital interface names") {
            val di1 = MqttDigitalInterface(hdtId, DigitalInterfaceName("di"))
            val di2 = HttpDigitalInterface(hdtId, DigitalInterfaceName("di"))
            shouldThrow<IllegalArgumentException> {
                buildHdt(hdtId, emptyList(), digitalInterfaces = listOf(di1, di2))
            }.message shouldContain "Duplicate digital interface IDs"
        }

        test("rejects digital interface referencing a different HDT") {
            val foreignDi = MqttDigitalInterface(HdtId("other"), DigitalInterfaceName("di"))
            shouldThrow<IllegalArgumentException> {
                buildHdt(hdtId, emptyList(), digitalInterfaces = listOf(foreignDi))
            }.message shouldContain "reference HDT"
        }
    }

    // -------------------------------------------------------------------------
    // Rename cascade — Model.rename
    // -------------------------------------------------------------------------

    context("Model.rename") {
        val original = buildModel("vitals", hdtId, listOf("heart-rate", "spo2"))

        test("updates the model id") {
            val renamed = original.rename(ModelName("biometrics"))
            renamed.id shouldBe HdtIdFactory.modelId(hdtId, ModelName("biometrics"))
        }

        test("preserves the hdtId") {
            val renamed = original.rename(ModelName("biometrics"))
            renamed.hdtId shouldBe hdtId
        }

        test("cascades modelId to every property") {
            val renamed = original.rename(ModelName("biometrics"))
            val expectedModelId = HdtIdFactory.modelId(hdtId, ModelName("biometrics"))
            renamed.properties.forEach { it.modelId shouldBe expectedModelId }
        }

        test("property ids update to reflect the new model name") {
            val renamed = original.rename(ModelName("biometrics"))
            val expectedModelId = HdtIdFactory.modelId(hdtId, ModelName("biometrics"))
            renamed.properties.forEach { p ->
                p.id shouldBe HdtIdFactory.propertyId(expectedModelId, p.name)
            }
        }

        test("preserves property names and values") {
            val renamed = original.rename(ModelName("biometrics"))
            renamed.properties.map { it.name.value }.toSet() shouldBe setOf("heart-rate", "spo2")
        }

        test("result is accepted by HumanDigitalTwin") {
            val renamed = original.rename(ModelName("biometrics"))
            buildHdt(hdtId, listOf(renamed))
        }

        test("original is unmodified") {
            original.rename(ModelName("biometrics"))
            original.name.value shouldBe "vitals"
            original.id shouldBe HdtIdFactory.modelId(hdtId, ModelName("vitals"))
            original.properties.forEach { it.modelId shouldBe original.id }
        }
    }

    // -------------------------------------------------------------------------
    // Rename cascade — HumanDigitalTwin.renameModel
    // -------------------------------------------------------------------------

    context("HumanDigitalTwin.renameModel") {
        val vitals = buildModel("vitals", hdtId, listOf("heart-rate"))
        val activity = buildModel("activity", hdtId, listOf("steps"))
        val base = buildHdt(hdtId, listOf(vitals, activity))

        test("renames the target model") {
            val updated = base.renameModel(ModelName("vitals"), ModelName("biometrics"))
            updated.models.map { it.name.value }.toSet() shouldBe setOf("biometrics", "activity")
        }

        test("leaves untargeted models unchanged") {
            val updated = base.renameModel(ModelName("vitals"), ModelName("biometrics"))
            val activityModel = updated.models.first { it.name.value == "activity" }
            activityModel.id shouldBe HdtIdFactory.modelId(hdtId, ModelName("activity"))
            activityModel.properties.forEach { it.modelId shouldBe activityModel.id }
        }

        test("cascades modelId to the renamed model's properties") {
            val updated = base.renameModel(ModelName("vitals"), ModelName("biometrics"))
            val biometrics = updated.models.first { it.name.value == "biometrics" }
            biometrics.properties.forEach { it.modelId shouldBe biometrics.id }
        }

        test("throws when the model name does not exist") {
            shouldThrow<IllegalArgumentException> {
                base.renameModel(ModelName("unknown"), ModelName("x"))
            }.message shouldContain "unknown"
        }
    }

    // -------------------------------------------------------------------------
    // Rename cascade — HumanDigitalTwin.rename
    // -------------------------------------------------------------------------

    context("HumanDigitalTwin.rename") {
        val origId = HdtId("dt-1")
        val newId = HdtId("dt-2")
        val pi = MqttPhysicalInterface(origId, PhysicalInterfaceName("mqtt-pi"))
        val mqttDi = MqttDigitalInterface(origId, DigitalInterfaceName("mqtt-di"))
        val httpDi = HttpDigitalInterface(origId, DigitalInterfaceName("http-di"))
        val base = HumanDigitalTwin(
            hdtId = origId,
            models = listOf(buildModel("vitals", origId, listOf("heart-rate", "spo2"))),
            storages = listOf(Storage.default(origId)),
            physicalInterfaces = listOf(pi),
            digitalInterfaces = listOf(mqttDi, httpDi),
        )
        val renamed = base.rename(newId)

        test("updates the hdtId") {
            renamed.hdtId shouldBe newId
        }

        test("cascades hdtId to models") {
            renamed.models.forEach { it.hdtId shouldBe newId }
        }

        test("cascades hdtId to model IDs") {
            renamed.models.forEach { m ->
                m.id shouldBe HdtIdFactory.modelId(newId, m.name)
            }
        }

        test("cascades modelId to properties, including their IDs") {
            renamed.models.forEach { m ->
                m.properties.forEach { p ->
                    p.modelId shouldBe m.id
                    p.id shouldBe HdtIdFactory.propertyId(m.id, p.name)
                }
            }
        }

        test("cascades hdtId to storages") {
            renamed.storages.forEach { it.hdtId shouldBe newId }
        }

        test("cascades hdtId to storage IDs") {
            renamed.storages.forEach { s ->
                s.id shouldBe HdtIdFactory.storageId(newId, s.name)
            }
        }

        test("cascades hdtId to MqttPhysicalInterface") {
            val updatedPi = renamed.physicalInterfaces.filterIsInstance<MqttPhysicalInterface>().first()
            updatedPi.hdtId shouldBe newId
            updatedPi.id shouldBe HdtIdFactory.physicalInterfaceId(newId, updatedPi.name)
        }

        test("cascades hdtId to MqttDigitalInterface") {
            val updatedMqtt = renamed.digitalInterfaces.filterIsInstance<MqttDigitalInterface>().first()
            updatedMqtt.hdtId shouldBe newId
            updatedMqtt.id shouldBe HdtIdFactory.digitalInterfaceId(newId, updatedMqtt.name)
        }

        test("cascades hdtId to HttpDigitalInterface") {
            val updatedHttp = renamed.digitalInterfaces.filterIsInstance<HttpDigitalInterface>().first()
            updatedHttp.hdtId shouldBe newId
            updatedHttp.id shouldBe HdtIdFactory.digitalInterfaceId(newId, updatedHttp.name)
        }

        test("original HDT is unmodified") {
            base.hdtId shouldBe origId
            base.models.forEach { it.hdtId shouldBe origId }
            base.storages.forEach { it.hdtId shouldBe origId }
            base.physicalInterfaces.forEach { it.hdtId shouldBe origId }
            base.digitalInterfaces.forEach { it.hdtId shouldBe origId }
        }
    }
})