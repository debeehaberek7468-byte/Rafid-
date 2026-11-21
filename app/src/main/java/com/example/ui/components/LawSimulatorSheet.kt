package com.example.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.data.CurriculumRepository
import com.example.data.SimulationResult
import com.example.model.LawSimulatorType
import com.example.model.PhysicsLaw
import com.example.ui.theme.CalmGold
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.EmeraldSuccess
import com.example.ui.theme.RoseError
import kotlin.math.abs

private data class SimulatorFieldSpec(
    val label: String,
    val defaultValue: String
)

private fun getFieldSpecs(type: LawSimulatorType): List<SimulatorFieldSpec> = when (type) {
    LawSimulatorType.COULOMB -> listOf(
        SimulatorFieldSpec("الشحنة الأولى q₁ (µC - مايكروكولوم)", "4"),
        SimulatorFieldSpec("الشحنة الثانية q₂ (µC - مايكروكولوم)", "9"),
        SimulatorFieldSpec("البعد بين الشحنتين r (cm - سنتيمتر)", "6")
    )
    LawSimulatorType.ELECTRIC_FIELD -> listOf(
        SimulatorFieldSpec("القوة الكهربائية المؤثرة F (N)", "0.02"),
        SimulatorFieldSpec("الشحنة الاختبارية q' (µC)", "2")
    )
    LawSimulatorType.CURRENT -> listOf(
        SimulatorFieldSpec("كمية الشحنة الكهربائية q (C)", "12"),
        SimulatorFieldSpec("الزمن المستغرق t (ثانية)", "4")
    )
    LawSimulatorType.OHM_LAW -> listOf(
        SimulatorFieldSpec("فرق الجهد V (فولت)", "12"),
        SimulatorFieldSpec("التيار الكهربائي I (أمبير)", "3")
    )
    LawSimulatorType.SERIES_RESISTORS -> listOf(
        SimulatorFieldSpec("المقاومة الأولى R₁ (Ω)", "4"),
        SimulatorFieldSpec("المقاومة الثانية R₂ (Ω)", "8"),
        SimulatorFieldSpec("فرق الجهد الكلي V (فولت)", "24")
    )
    LawSimulatorType.PARALLEL_RESISTORS -> listOf(
        SimulatorFieldSpec("المقاومة الأولى R₁ (Ω)", "6"),
        SimulatorFieldSpec("المقاومة الثانية R₂ (Ω)", "3"),
        SimulatorFieldSpec("فرق الجهد الكلي V (فولت)", "12")
    )
    LawSimulatorType.EMF -> listOf(
        SimulatorFieldSpec("الشغل أو الطاقة المكتسبة W (J)", "20"),
        SimulatorFieldSpec("كمية الشحنة الكهربائية q (C)", "10")
    )
    LawSimulatorType.ELECTRIC_POWER -> listOf(
        SimulatorFieldSpec("التيار الكهربائي I (A)", "2"),
        SimulatorFieldSpec("فرق الجهد V (V)", "220")
    )
    LawSimulatorType.ELECTRIC_ENERGY_COST -> listOf(
        SimulatorFieldSpec("قدرة الجهاز بالواط P (W)", "1000"),
        SimulatorFieldSpec("زمن التشغيل بالدقائق t (min)", "30"),
        SimulatorFieldSpec("ثمن الوحدة الواحدة (دينار)", "100")
    )
    LawSimulatorType.TRANSFORMER_RATIO -> listOf(
        SimulatorFieldSpec("فولتية الملف الابتدائي V₁ (V)", "240"),
        SimulatorFieldSpec("عدد لفات الابتدائي N₁", "500"),
        SimulatorFieldSpec("عدد لفات الثانوي N₂", "25")
    )
    LawSimulatorType.TRANSFORMER_EFFICIENCY -> listOf(
        SimulatorFieldSpec("القدرة الخارجة P₂ (W)", "450"),
        SimulatorFieldSpec("القدرة الداخلة P₁ (W)", "500")
    )
    LawSimulatorType.JOULE_HEAT -> listOf(
        SimulatorFieldSpec("التيار المار I (A)", "3"),
        SimulatorFieldSpec("المقاومة R (Ω)", "10"),
        SimulatorFieldSpec("الزمن t (ثانية)", "60")
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LawSimulatorSheet(
    law: PhysicsLaw,
    onDismiss: () -> Unit
) {
    val simType = law.simulatorType ?: return
    val specs = remember(simType) { getFieldSpecs(simType) }
    val inputs = remember(simType) {
        mutableStateListOf<String>().apply {
            specs.forEach { add(it.defaultValue) }
        }
    }
    var studentGuess by remember(simType) { mutableStateOf("") }
    var result by remember(simType) { mutableStateOf<SimulationResult?>(null) }
    var verificationMsg by remember(simType) { mutableStateOf<Pair<Boolean, String>?>(null) }
    var inputError by remember(simType) { mutableStateOf<String?>(null) }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "محاكي: ${law.name}",
                        style = MaterialTheme.typography.titleLarge,
                        color = ElectricBlue
                    )
                    Text(
                        text = law.formula,
                        style = MaterialTheme.typography.titleSmall,
                        color = CalmGold
                    )
                }
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Filled.Close, contentDescription = "إغلاق")
                }
            }

            specs.forEachIndexed { index, spec ->
                OutlinedTextField(
                    value = inputs[index],
                    onValueChange = { inputs[index] = it },
                    label = { Text(spec.label) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("sim_input_$index"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = ElectricBlue,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
                    )
                )
            }

            OutlinedTextField(
                value = studentGuess,
                onValueChange = { studentGuess = it },
                label = { Text("أدخل ناتجك للتحقق من حلك (اختياري)") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("sim_student_guess"),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = CalmGold,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
                )
            )

            if (inputError != null) {
                Text(
                    text = inputError!!,
                    color = RoseError,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Button(
                onClick = {
                    val parsed = inputs.map { it.trim().toDoubleOrNull() }
                    if (parsed.any { it == null }) {
                        inputError = "يرجى إدخال أرقام صحيحة أو عشرية في جميع الحقول."
                        return@Button
                    }
                    inputError = null
                    val simResult = CurriculumRepository.solveLaw(simType, parsed.filterNotNull())
                    result = simResult

                    val guessVal = studentGuess.trim().toDoubleOrNull()
                    verificationMsg = if (guessVal != null) {
                        val expected = simResult.primaryValue
                        val tolerance = (abs(expected) * 0.025).coerceAtLeast(0.01)
                        if (abs(guessVal - expected) <= tolerance) {
                            true to "أحسنت! ناتجك مطابق للحل النموذجي في المنهج."
                        } else {
                            false to "الناتج المدخل يختلف عن القيمة الدقيقة؛ راجع التحويلات والخطوات أدناه."
                        }
                    } else null
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .testTag("calculate_law_button"),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ElectricBlue,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Icon(Icons.Filled.Calculate, contentDescription = null)
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = "احسب الناتج وتحقق",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            }

            verificationMsg?.let { (isCorrect, msg) ->
                GlassCard(isGoldAccent = !isCorrect) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = if (isCorrect) Icons.Filled.CheckCircle else Icons.Filled.ErrorOutline,
                            contentDescription = null,
                            tint = if (isCorrect) EmeraldSuccess else RoseError
                        )
                        Text(
                            text = msg,
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (isCorrect) EmeraldSuccess else RoseError
                        )
                    }
                }
            }

            result?.let { sim ->
                GlassCard(isGoldAccent = true) {
                    Text(
                        text = "الناتج النهائي:",
                        style = MaterialTheme.typography.labelLarge,
                        color = CalmGold
                    )
                    Text(
                        text = sim.formattedAnswer,
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "خطوات الحل:",
                        style = MaterialTheme.typography.labelLarge,
                        color = ElectricBlue
                    )
                    sim.steps.forEach { step ->
                        Text(
                            text = "• $step",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
