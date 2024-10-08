package com.bluelabs.innoventestech.screens

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bluelabs.innoventestech.R
import com.bluelabs.innoventestech.viewmodel.HomeViewModel


//@Preview(showSystemUi = true)
@Composable
fun HomeScreen(homeVM: HomeViewModel, modifier: Modifier) {

//    lateinit var homeVM: HomeViewModel

    var pan by remember { mutableStateOf("") }
    var day by remember { mutableStateOf("") }
    var mon by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }

    var isNextEnabled by remember { mutableStateOf(false) }

    LaunchedEffect(pan,day,mon,year) {
        isNextEnabled =
            (pan.length == 10) &&
                (day.length <= 2) &&
                (mon.length <= 2) &&
                (year.length == 4)

    }




    val context = LocalContext.current

    Column(modifier = Modifier.padding(16.dp)) {
        Image(painter = painterResource(R.drawable.logo), contentDescription = "S.")
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            "First of the few steps to set up your bank account",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(64.dp))
        Text("PAN NUMBER", fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = pan, onValueChange = { pan = it.uppercase() }, modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("BIRTHDATE", fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.weight(1f)) {
            OutlinedTextField(value = day, onValueChange = {
                if (it.length <= 2) day = it
            }, modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedTextField(
                value = mon, onValueChange = {
                    if (it.length <= 2) mon = it
                }, modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedTextField(
                value = year, onValueChange = {
                    if (it.length <= 4) year = it
                }, modifier = Modifier.weight(3f)
            )
        }


        val clickableText = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Light
                )
            ) {
                append("Providing PAN & Date of Birth helps us find and fetch your KYC from a central registry by the Government of India. ")
            }
            pushStringAnnotation(
                tag = "learn_more", annotation = "learn_more"
            )
            withStyle(
                style = SpanStyle(
                    color = colorResource(R.color.purple_500),
                )
            ) {
                append("Learn More")
            }
            pop()
        }


        ClickableText(
            text = clickableText,
        ) { offset ->
            clickableText.getStringAnnotations(
                tag = "learn_more", start = offset, end = offset
            )[0].let { annotation ->
                //do your stuff when it gets clicked
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://bluelabs.in/"))
                context.startActivity(browserIntent)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (!homeVM.validatePan(pan)){
                    Toast.makeText(context,"Please enter a valid PAN", Toast.LENGTH_LONG).show()
                }else if(!homeVM.validateDate(day,mon,year)){
                    Toast.makeText(context,"Please enter a valid DOB", Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(context,"Thanks for submitting your details", Toast.LENGTH_LONG).show()
                }
            },
            enabled = isNextEnabled,
            shape = RoundedCornerShape(4.dp), modifier = Modifier.fillMaxWidth()
        ) { Text("Next") }

        TextButton(onClick = {}, modifier = Modifier.fillMaxWidth()) { Text("I don't have a PAN") }
        Spacer(modifier = Modifier.height(16.dp))

    }


}