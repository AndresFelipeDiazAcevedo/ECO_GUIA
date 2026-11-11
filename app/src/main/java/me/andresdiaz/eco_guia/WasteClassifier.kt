
package me.andresdiaz.eco_guia

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import com.google.ai.edge.litert.Accelerator
import com.google.ai.edge.litert.CompiledModel

class WasteClassifier(context: Context) {

    private val labels = listOf(
        "Cartón",
        "Vidrio",
        "Metal",
        "Papel",
        "Plástico",
        "Basura"
    )

    private val model = CompiledModel.create(
        context.assets,
        "eco_guia_model.tflite",
        CompiledModel.Options(Accelerator.CPU)
    )

    private val inputBuffers = model.createInputBuffers()
    private val outputBuffers = model.createOutputBuffers()

    fun classify(bitmap: Bitmap): Pair<String, Float> {

        val resizedBitmap = Bitmap.createScaledBitmap(
            bitmap,
            124,
            124,
            true
        )

        val inputData = FloatArray(124 * 124 * 3)

        var index = 0

        for (y in 0 until 124) {
            for (x in 0 until 124) {

                val pixel = resizedBitmap.getPixel(x, y)

                inputData[index++] = Color.red(pixel).toFloat()
                inputData[index++] = Color.green(pixel).toFloat()
                inputData[index++] = Color.blue(pixel).toFloat()
            }
        }

        inputBuffers[0].writeFloat(inputData)

        model.run(inputBuffers, outputBuffers)

        val predictions = outputBuffers[0].readFloat()

        val bestIndex = predictions.indices.maxByOrNull {
            predictions[it]
        } ?: 0

        return Pair(
            labels[bestIndex],
            predictions[bestIndex]
        )
    }

    fun close() {
        inputBuffers.forEach { it.close() }
        outputBuffers.forEach { it.close() }
        model.close()
    }
}
