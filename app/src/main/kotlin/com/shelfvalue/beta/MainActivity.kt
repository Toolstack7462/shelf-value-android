package com.shelfvalue.beta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val colors = if (isSystemInDarkTheme()) darkColorScheme(primary=Color(0xFF8BD7C5), secondary=Color(0xFFB3CCC3))
                else lightColorScheme(primary=Color(0xFF006B59), secondary=Color(0xFF4C635C))
            MaterialTheme(colorScheme = colors) { Surface(Modifier.fillMaxSize().safeDrawingPadding()) { ShelfScreen() } }
        }
    }
}

@Composable
private fun unitText(unit: ComparisonEngine.Unit): String = when (unit) {
    ComparisonEngine.Unit.G -> stringResource(R.string.gram)
    ComparisonEngine.Unit.KG -> stringResource(R.string.kilogram)
    ComparisonEngine.Unit.ML -> stringResource(R.string.millilitre)
    ComparisonEngine.Unit.L -> stringResource(R.string.litre)
    ComparisonEngine.Unit.ITEM -> stringResource(R.string.item)
}

@Composable
private fun OfferCard(
    id: String, title: String, price: String, quantity: String, packs: String, unit: String,
    setPrice: (String) -> Unit, setQuantity: (String) -> Unit, setPacks: (String) -> Unit, setUnit: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    Card(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            OutlinedTextField(value = price, onValueChange = setPrice, label = { Text(stringResource(R.string.price)) },
                modifier = Modifier.fillMaxWidth().testTag("price_$id"), singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal))
            OutlinedTextField(value = quantity, onValueChange = setQuantity, label = { Text(stringResource(R.string.quantity)) },
                modifier = Modifier.fillMaxWidth().testTag("quantity_$id"), singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal))
            OutlinedTextField(value = packs, onValueChange = setPacks, label = { Text(stringResource(R.string.packs)) },
                modifier = Modifier.fillMaxWidth().testTag("packs_$id"), singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
            Box {
                OutlinedButton(onClick = { expanded = true }, modifier = Modifier.testTag("unit_$id")) {
                    Text(stringResource(R.string.unit) + ": " + unitText(ComparisonEngine.Unit.valueOf(unit)))
                }
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    ComparisonEngine.Unit.values().forEach { choice ->
                        DropdownMenuItem(text = { Text(unitText(choice)) }, onClick = { setUnit(choice.name); expanded = false })
                    }
                }
            }
        }
    }
}

@Composable
fun ShelfScreen() {
    var priceA by rememberSaveable { mutableStateOf("") }; var quantityA by rememberSaveable { mutableStateOf("") }
    var packsA by rememberSaveable { mutableStateOf("1") }; var unitA by rememberSaveable { mutableStateOf("G") }
    var priceB by rememberSaveable { mutableStateOf("") }; var quantityB by rememberSaveable { mutableStateOf("") }
    var packsB by rememberSaveable { mutableStateOf("1") }; var unitB by rememberSaveable { mutableStateOf("G") }
    var currency by rememberSaveable { mutableStateOf("Rs") }
    var showResult by rememberSaveable { mutableStateOf(false) }
    var error by rememberSaveable { mutableStateOf("") }
    fun changed() { showResult = false; error = "" }
    fun reset() { priceA=""; quantityA=""; packsA="1"; unitA="G"; priceB=""; quantityB=""; packsB="1"; unitB="G"; currency="Rs"; changed() }
    val result = if (showResult) try {
        ComparisonEngine.compare(ComparisonEngine.Offer(priceA,quantityA,ComparisonEngine.Unit.valueOf(unitA),packsA),
            ComparisonEngine.Offer(priceB,quantityB,ComparisonEngine.Unit.valueOf(unitB),packsB))
    } catch (_: IllegalArgumentException) { null } else null

    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(stringResource(R.string.app_name), style=MaterialTheme.typography.headlineMedium, fontWeight=FontWeight.Bold)
        Text(stringResource(R.string.subtitle), style=MaterialTheme.typography.bodyLarge)
        Text(stringResource(R.string.beta), style=MaterialTheme.typography.labelMedium)
        OutlinedTextField(currency, { currency=it.take(8); changed() }, label={ Text(stringResource(R.string.currency)) },
            modifier=Modifier.fillMaxWidth().testTag("currency"), singleLine=true)
        OfferCard("A",stringResource(R.string.product_a),priceA,quantityA,packsA,unitA,
            { priceA=it;changed() },{ quantityA=it;changed() },{ packsA=it;changed() },{ unitA=it;changed() })
        OfferCard("B",stringResource(R.string.product_b),priceB,quantityB,packsB,unitB,
            { priceB=it;changed() },{ quantityB=it;changed() },{ packsB=it;changed() },{ unitB=it;changed() })
        Row(horizontalArrangement=Arrangement.spacedBy(12.dp)) {
            Button(onClick={
                try {
                    ComparisonEngine.compare(ComparisonEngine.Offer(priceA,quantityA,ComparisonEngine.Unit.valueOf(unitA),packsA),
                        ComparisonEngine.Offer(priceB,quantityB,ComparisonEngine.Unit.valueOf(unitB),packsB))
                    error="";showResult=true
                } catch (e: IllegalArgumentException) { showResult=false;error=if(e.message=="INCOMPATIBLE") "INCOMPATIBLE" else "INVALID" }
            }, modifier=Modifier.testTag("compare")) { Text(stringResource(R.string.compare)) }
            OutlinedButton(onClick={ reset() },modifier=Modifier.testTag("reset")) { Text(stringResource(R.string.reset)) }
        }
        Card(Modifier.fillMaxWidth().semantics { liveRegion=LiveRegionMode.Polite }.testTag("result")) {
            Column(Modifier.padding(16.dp), verticalArrangement=Arrangement.spacedBy(8.dp)) {
                if(error.isNotEmpty()) Text(stringResource(if(error=="INCOMPATIBLE") R.string.incompatible else R.string.invalid),color=MaterialTheme.colorScheme.error)
                else if(result==null) Text(stringResource(R.string.prompt))
                else {
                    val per = unitText(when(result.per){"kg" -> ComparisonEngine.Unit.KG; "L" -> ComparisonEngine.Unit.L; else -> ComparisonEngine.Unit.ITEM})
                    Text(stringResource(when(result.winner){"A" -> R.string.winner_a; "B" -> R.string.winner_b; else -> R.string.tie}),
                        style=MaterialTheme.typography.titleLarge,fontWeight=FontWeight.Bold)
                    Text(stringResource(R.string.prices,currency,result.unitPriceA,per,result.unitPriceB))
                    if(result.winner!="TIE") Text(stringResource(R.string.savings,currency,result.savingsPerUnit,per,result.savingsPercent))
                    if(result.winner!="TIE" && result.unitPriceA==result.unitPriceB) Text(stringResource(R.string.near_tie))
                    Text(stringResource(R.string.note),style=MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}
