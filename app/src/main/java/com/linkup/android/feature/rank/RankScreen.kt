package com.linkup.android.feature.rank

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.linkup.android.R
import com.linkup.android.network.rank.RankResponse
import com.linkup.android.ui.components.TopBar
import com.linkup.android.ui.theme.MainColor

@Composable
fun RankScreen(
    navController: NavController,
    innerPadding: PaddingValues ,
    viewModel: RankViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    LaunchedEffect(Unit) {
        viewModel.getRank()
    }

    Column (
        Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(Color.White)
    ){
        TopBar(navController)
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (state.error != null) {
                Text(
                    text = state.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                RankingContent(state.ranking)
            }
        }
    }
}

@Composable
fun RankingContent(rankingList: List<RankResponse>) {
    if (rankingList.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("No ranking data available.")
        }
        return
    }

    val top3 = rankingList.take(3)
    val rest = rankingList.drop(3)

    Column(
        Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (top3.isNotEmpty()) {
            TopCard(top3)
        }
        if (rest.isNotEmpty()) {
            BottomCard(rest)
        }
    }
}

@Composable
fun TopCard(toplist: List<RankResponse>) {
    Card(
        modifier = Modifier.padding(vertical = 20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            Modifier
                .padding(horizontal = 32.dp)
                .padding(top = 20.dp)
                .padding(bottom = 12.dp),
        ) {
            Text(
                "🏆 답변자 랭킹",
                Modifier
                    .padding(horizontal = 36.dp)
                    .padding(bottom = 24.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )

            if (toplist.isNotEmpty()) {
                Row(Modifier.padding(bottom = 10.dp), verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.rank_1st),
                        contentDescription = "1st",
                        modifier = Modifier.width(50.dp).height(50.dp)
                    )
                    Text("1st", Modifier.padding(6.dp), color = MainColor, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                    Text(toplist[0].username, color = MainColor, fontWeight = FontWeight.Bold, fontSize = 28.sp)
                    Text("${toplist[0].point}P", Modifier.padding(6.dp), color = Color.Black, fontWeight = FontWeight.Medium, fontSize = 16.sp)
                }
            }

            if (toplist.size >= 2) {
                Row(Modifier.padding(bottom = 10.dp), verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.rank_2nd),
                        contentDescription = "2nd",
                        modifier = Modifier.width(40.dp).height(40.dp)
                    )
                    Text("2nd", Modifier.padding(6.dp), color = Color.Black, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                    Text(toplist[1].username, color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 24.sp)
                    Text("${toplist[1].point}P", Modifier.padding(6.dp), color = Color.Black, fontWeight = FontWeight.Medium, fontSize = 12.sp)
                }
            }

            if (toplist.size >= 3) {
                Row(Modifier.padding(bottom = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.rank_3rd),
                        contentDescription = "3rd",
                        modifier = Modifier.width(40.dp).height(40.dp)
                    )
                    Text("3rd", Modifier.padding(6.dp), color = Color.Black, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                    Text(toplist[2].username, color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 24.sp)
                    Text("${toplist[2].point}P", Modifier.padding(6.dp), color = Color.Black, fontWeight = FontWeight.Medium, fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
fun BottomCard(bottomlist: List<RankResponse>) {
    Column(
        Modifier
            .shadow(3.dp, RoundedCornerShape(12.dp))
            .background(Color.White)
            .fillMaxWidth()
    ) {
        bottomlist.forEachIndexed { index, item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp).padding(top = 8.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Text("${index + 4}등", Modifier.padding(3.dp), color = MainColor, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                Text(item.username, Modifier.padding(3.dp), color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text("${item.point}P", Modifier.padding(5.dp), color = Color.Black, fontWeight = FontWeight.Medium, fontSize = 17.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RankingPreview() {
    val dummyData = listOf(
        RankResponse(username = "Alice", point = 1200, rank = 1),
        RankResponse(username = "Bob", point = 1100, rank = 2),
        RankResponse(username = "Charlie", point = 1000, rank = 3),
        RankResponse(username = "Dave", point = 900, rank = 4),
        RankResponse(username = "Eve", point = 800, rank = 5)
    )
    RankingContent(dummyData)
}
