import android.content.Context
import android.util.Log
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.FileInputStream
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class TFLiteModel(context: Context) {

    private var interpreter: Interpreter? = null

    // Replace these with the actual mean and std values from your scaler
    private val ageMean = 30.252636f
    private val ageStd = 17.594414f
    private val heightMean = 88.74001f
    private val heightStd = 17.29022f

    init {
        interpreter = Interpreter(loadModelFile(context, "testmodel.tflite"))
    }

    @Throws(IOException::class)
    private fun loadModelFile(context: Context, modelName: String): MappedByteBuffer {
        val fileDescriptor = context.assets.openFd(modelName)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    fun runInference( age: Int, gender: Int, height: Float): Int {
        // Normalize input values
        val normalizedAge = (age - ageMean) / ageStd
        val normalizedHeight = (height - heightMean) / heightStd
//        val input = floatArrayOf(gender.toFloat(), normalizedAge, normalizedHeight)
        val input = floatArrayOf(normalizedAge, gender.toFloat(), normalizedHeight)

        // Log input values for debugging
        Log.d("TFLiteModel", "Input values: ${input.joinToString()}")

        val byteBuffer = ByteBuffer.allocateDirect(3 * 4).apply {
            order(ByteOrder.nativeOrder())
            for (value in input) {
                putFloat(value)
            }
        }

        // Create an output buffer to store the inference result
        val outputBuffer = ByteBuffer.allocateDirect(4 * 4).order(ByteOrder.nativeOrder())

        // Run the model
        interpreter?.run(byteBuffer, outputBuffer.rewind())

        // Extract the integer result from the output buffer
        outputBuffer.rewind() // Ensure the buffer is at the start
        val outputArray = FloatArray(4)
        outputBuffer.asFloatBuffer().get(outputArray)

        // Log output values for debugging
        Log.d("TFLiteModel", "Output values: ${outputArray.joinToString()}")

        // Find the index of the maximum value
        val maxIndex = outputArray.indices.maxByOrNull { outputArray[it] } ?: -1
        Log.d("TFLiteModel", "Max index: $maxIndex")

        return maxIndex
    }

    fun close() {
        interpreter?.close()
    }
}
