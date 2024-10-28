package com.safronov.apex_udf.example.interesting_fact_about_number.ui.composable.number_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.safronov.apex_udf.R
import com.safronov.apex_udf.example.interesting_fact_about_number.ui.composable.number_details.NumberDetailsContract.State

@Composable
fun NumberDetailsScreen(
    modifier: Modifier = Modifier,
    state: State
) {
    Scaffold(
        modifier = Modifier
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp
            ),
        topBar = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(R.drawable.arrrow_left),
                    contentDescription = "Navigate Up"
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = state.number,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.DarkGray,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    ) {
        Column(
            modifier = modifier
                .padding(top = 16.dp)
                .padding(it)
                .verticalScroll(rememberScrollState())
            ,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = state.description,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Gray
            )
        }
    }
}

@Composable
@Preview
fun NumberDetailsPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        NumberDetailsScreen(
            state = State(
                number = "3",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus ornare magna ac faucibus sollicitudin. Ut commodo lectus ac nulla faucibus, mattis commodo risus molestie. In neque ligula, rutrum at tellus id, auctor cursus sapien. Integer in lorem pellentesque, bibendum nulla vitae, semper augue. Proin placerat lobortis accumsan. Sed id aliquam ante, id euismod magna. Curabitur a lacinia metus, in dapibus nunc. Quisque posuere sapien nisi, mollis dictum nunc consequat eget. Maecenas dictum velit non dolor efficitur, non porta turpis malesuada. Maecenas congue pellentesque augue, a pulvinar elit aliquet et. Donec leo mi, gravida a felis in, egestas varius eros.\n" +
                        "\n" +
                        "Cras nisl nulla, pulvinar ut varius a, maximus id ligula. In eleifend vel massa eu eleifend. Aliquam iaculis ex dignissim felis scelerisque, nec finibus magna aliquet. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec ex erat, posuere nec libero ut, pharetra dictum turpis. Fusce at magna gravida, consectetur dolor eu, tempor metus. Cras varius vulputate tempor. Donec dictum turpis odio, nec lobortis dui laoreet sed. Morbi a neque ut ligula imperdiet porttitor. Donec nec volutpat massa. Sed in eros venenatis dolor porttitor fringilla vitae posuere ligula. Duis nec vehicula nunc. Etiam suscipit dictum nisl id consequat. Sed vitae bibendum magna. Vivamus euismod ultrices neque, vel ornare nulla ultrices at.\n" +
                        "\n" +
                        "In hac habitasse platea dictumst. Nunc blandit eget diam id dictum. Nam blandit lectus eu nibh dignissim eleifend. Maecenas condimentum nisi leo, eu aliquam ante rutrum id. Suspendisse non tempus elit. Proin feugiat urna in ex rutrum, et vestibulum eros scelerisque. Nunc elementum eros eget lacus mattis lacinia. Phasellus maximus pretium enim, a facilisis ex mattis sed. Sed porta risus felis, ac malesuada nunc euismod nec. Nullam ac magna mollis, commodo ipsum et, feugiat nibh. Phasellus sem nibh, facilisis non est sit amet, finibus egestas lorem. Donec volutpat, mauris id iaculis bibendum, quam sapien auctor sapien, et pulvinar lorem ipsum quis ipsum. Donec condimentum risus sit amet leo finibus aliquam. Nulla facilisis purus ut purus semper cursus.\n" +
                        "\n" +
                        "Etiam faucibus eget nulla id mattis. Aliquam nec condimentum magna. Phasellus tortor magna, bibendum non magna eu, viverra rutrum ex. Ut ut mattis lorem, id mattis neque. Donec iaculis lectus sed metus vestibulum facilisis. Ut sit amet fringilla elit. Sed finibus magna risus, id volutpat arcu sodales quis. Integer condimentum in dolor sit amet cursus. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Fusce molestie, erat id maximus lacinia, mauris ante scelerisque justo, sed iaculis eros odio non eros. In ornare odio in pulvinar ultricies. Aliquam in ex tincidunt, ornare diam sed, mattis erat. Proin ullamcorper, tortor non convallis commodo, nulla enim pretium lectus, eu fringilla lectus arcu ultricies nisi. Etiam nec erat ultrices, condimentum ex quis, finibus tortor. Nam sodales tempus velit vitae vestibulum.\n" +
                        "\n" +
                        "Curabitur aliquam nulla ut semper imperdiet. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Curabitur nec dui nec mauris consequat luctus. Etiam vitae luctus eros, ut pellentesque sapien. Aenean et luctus justo, eu vehicula erat. In dapibus augue ac urna dictum, sed semper neque venenatis. Nullam massa est, venenatis tincidunt erat in, volutpat ultricies nisl. Sed eget ullamcorper tellus. Nulla leo tellus, laoreet in metus sit amet, aliquam ornare velit. Suspendisse potenti."
            )
        )
    }
}