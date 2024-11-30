package kr.toxicity.hud.layout

import kr.toxicity.hud.api.yaml.YamlObject
import kr.toxicity.hud.element.HudElement
import kr.toxicity.hud.placeholder.ConditionSource
import kr.toxicity.hud.location.PixelLocation
import kr.toxicity.hud.shader.RenderScale
import kr.toxicity.hud.shader.ShaderProperty

interface HudLayout<T : HudElement> : ConditionSource {
    val source: T
    val outline: Boolean
    val layer: Int
    val property: Int
    val follow: String?
    val location: PixelLocation
    val cancelIfFollowerNotExists: Boolean
    val renderScale: RenderScale

    class Impl<T : HudElement>(
        override val source: T,
        group: LayoutGroup,
        originalLoc: PixelLocation,
        yaml: YamlObject
    ) : HudLayout<T>, ConditionSource by ConditionSource.Impl(source, yaml) + group {
        override val outline: Boolean = yaml.getAsBoolean("outline", false)
        override val layer: Int = yaml.getAsInt("layer", 0)
        override val property: Int = ShaderProperty.properties(yaml["properties"]?.asArray())
        override val follow: String? = yaml["follow"]?.asString()
        override val location: PixelLocation = PixelLocation(yaml) + originalLoc + PixelLocation.hotBarHeight
        override val cancelIfFollowerNotExists: Boolean = yaml.getAsBoolean("cancel-if-follower-not-exists", true)
        override val renderScale = RenderScale.fromConfig(location, yaml)
    }
}